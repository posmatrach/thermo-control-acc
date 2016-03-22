package de.unikl.mse.thermocontrol.components.impl;

import de.unikl.mse.thermocontrol.components.spec.ThermoSensor;
import de.unikl.mse.thermocontrol.components.spec.Thermostat;
import de.unikl.mse.thermocontrol.messaging.BaseMessageConsumer;
import de.unikl.mse.thermocontrol.messaging.TemperatureMessage;

public class ThermostatImpl extends BaseMessageConsumer<TemperatureMessage> implements Runnable, Thermostat
{
	private ThermoSensor thermoSensor;
	
	public ThermostatImpl(ThermoSensor thermoSensor)
	{
		this.thermoSensor = new ThermoSensorImpl();
	}
	
	@Override
	public void run()
	{
		// TODO Auto-generated method stub
	}

}
