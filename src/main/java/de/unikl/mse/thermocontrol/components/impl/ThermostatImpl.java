package de.unikl.mse.thermocontrol.components.impl;

import de.unikl.mse.thermocontrol.components.spec.ThermoSensor;
import de.unikl.mse.thermocontrol.components.spec.Thermostat;
import de.unikl.mse.thermocontrol.messaging.TemperatureMessage;

public class ThermostatImpl implements Runnable, Thermostat
{
	private ThermoSensor thermoSensor;
	
	
	public ThermostatImpl()
	{
		this.thermoSensor = new ThermoSensorImpl();
	}
	
	@Override
	public void sendMessage(TemperatureMessage m)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run()
	{
		// TODO Auto-generated method stub
	}

}
