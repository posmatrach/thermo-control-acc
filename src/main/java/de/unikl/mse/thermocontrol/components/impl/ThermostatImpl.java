package de.unikl.mse.thermocontrol.components.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.unikl.mse.thermocontrol.components.spec.AirConditioner;
import de.unikl.mse.thermocontrol.components.spec.ControlPanel;
import de.unikl.mse.thermocontrol.components.spec.ThermoSensor;
import de.unikl.mse.thermocontrol.components.spec.Thermostat;
import de.unikl.mse.thermocontrol.messaging.ACMessage;
import de.unikl.mse.thermocontrol.messaging.ControlPanelMessage;
import de.unikl.mse.thermocontrol.messaging.MessageType;
import de.unikl.mse.thermocontrol.messaging.TemperatureMessage;
import de.unikl.mse.thermocontrol.messaging.ThermoSensorMessage;
import de.unikl.mse.thermocontrol.messaging.base.BaseMessageConsumer;

public class ThermostatImpl extends BaseMessageConsumer<TemperatureMessage> implements Thermostat
{	
	static private final Logger L = LoggerFactory.getLogger(ThermostatImpl.class);
	
	static private final Map<Integer, ACMode> DIFF_TO_AC_MODE;
	
	/**
	 * Helper method for getting comparator-like difference in temperature.
	 * Used to get the proper ACMode from the DIFF_TO_AC_MODE map.
	 * 
	 * @param currentTemperature
	 * @param desiredTemperature
	 * @return Integer [-1, 0, 1]
	 */
	static private Integer getDelta(int currentTemperature, int desiredTemperature)
	{
		int res = currentTemperature - desiredTemperature;
		return res < 0 ? new Integer(-1) : res > 0 ? new Integer(1) : new Integer(0); 
	}
	
	static
	{
		DIFF_TO_AC_MODE = new HashMap<>();
		DIFF_TO_AC_MODE.put(new Integer(-1), ACMode.HEATING);
		DIFF_TO_AC_MODE.put(new Integer(0), ACMode.OFF);
		DIFF_TO_AC_MODE.put(new Integer(1), ACMode.COOLING);
	}
	
	private ThermoSensor thermoSensor;
	
	private ControlPanel controlPanel;
	
	private AirConditioner acUnit;
	
	private volatile boolean terminated;
	
	private int desiredTemperature = 20;
	
	private ACMode currentACMode = ACMode.OFF;
	
	@Override
	public void run()
	{
		// We don't start processing, unless we have all the components registered.
		if(null == thermoSensor || null == controlPanel || null == acUnit)
		{
			L.warn("One or more message consumers not registered! Terminating...");
			return;
		}
		
		int currentTemperature = 0;
		boolean shouldNotify = false;
		
		while(!terminated)
		{
			thermoSensor.sendMessage(new ThermoSensorMessage());
			
			try
			{
				TemperatureMessage receivedMessage = messageQueue.take();
				
				while(receivedMessage.getMessageType() == MessageType.SET_TEMPERATURE)
				{
					desiredTemperature += receivedMessage.getValue().intValue();
					shouldNotify = true;
					receivedMessage = messageQueue.take();
				}
				
				if(receivedMessage.getMessageType() == MessageType.CURRENT_TEMPERATURE)
				{
					int newTemperature = receivedMessage.getValue().intValue();
					if(newTemperature != currentTemperature)
					{
						currentTemperature = newTemperature;
						shouldNotify = true;
					}
					
					Integer tempDiff = getDelta(currentTemperature, desiredTemperature);
					ACMode newMode = DIFF_TO_AC_MODE.get(tempDiff);
					
					if(newMode != currentACMode)
					{
						acUnit.sendMessage(new ACMessage(newMode));
						currentACMode = newMode;
						shouldNotify = true;
					}					
				}
				
				if(shouldNotify)
				{
					controlPanel.sendMessage(new ControlPanelMessage(new ACStatus(currentTemperature, desiredTemperature, currentACMode)));
					shouldNotify = false;
				}
				
				Thread.sleep(1000);
				
			} catch (InterruptedException e)
			{
				L.warn("Caught InterruptedException, terminating...");
				terminate();
			}
		}
		L.info("Terminating Thermostat process loop...");
	}
	
	@Override
	public void setThermoSensor(ThermoSensor thermoSensor)
	{
		this.thermoSensor = thermoSensor;
	}

	@Override
	public void setControlPanel(ControlPanel controlPanel)
	{
		this.controlPanel = controlPanel;
	}

	@Override
	public void setACUnit(AirConditioner acUnit)
	{
		this.acUnit = acUnit;
	}
	
	@Override
	public void terminate()
	{
		terminated = true;
	}
}
