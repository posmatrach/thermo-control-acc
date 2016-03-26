package de.unikl.mse.thermocontrol.components.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.unikl.mse.thermocontrol.components.spec.AirConditioner;
import de.unikl.mse.thermocontrol.components.spec.ControlPanel;
import de.unikl.mse.thermocontrol.components.spec.ThermoSensor;
import de.unikl.mse.thermocontrol.components.spec.Thermostat;
import de.unikl.mse.thermocontrol.messaging.BaseMessageConsumer;
import de.unikl.mse.thermocontrol.messaging.TemperatureMessage;

public class ThermostatImpl extends BaseMessageConsumer<TemperatureMessage> implements Thermostat
{	
	static private final Logger L = LoggerFactory.getLogger(ThermostatImpl.class);
	
	private ThermoSensor thermoSensor;
	
	private ControlPanel controlPanel;
	
	private AirConditioner acUnit;
	
	private volatile boolean isTerminated;
	
	@Override
	public void run()
	{
		if(null == thermoSensor || null == controlPanel || null == acUnit)
		{
			L.warn("One or more message consumers not registered! Terminating...");
			return;
		}
		int currentTemperature;
		boolean shouldNotify = false;
		
		while(!isTerminated)
		{
			
		}
		
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
	
	public void terminate()
	{
		isTerminated = true;
	}
}
