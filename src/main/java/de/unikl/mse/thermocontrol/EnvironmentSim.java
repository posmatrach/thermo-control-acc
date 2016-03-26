package de.unikl.mse.thermocontrol;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.unikl.mse.thermocontrol.components.spec.AirConditioner;
import de.unikl.mse.thermocontrol.components.spec.ThermoSensor;

/**
 * Simple class for simulating the environment temperature fluctuation.
 * 
 * @author Nenad Natosevic <nenad.natoshevic@gmail.com>
 *
 */
public class EnvironmentSim
{
	static private final Logger L = LoggerFactory.getLogger(EnvironmentSim.class);
	
	static private final Random R = new Random();
	
	static private final double OFFSET = 0.1;
	
	static private  final int UPDATE_RATE = 250;
	
	private final AirConditioner acUnit;
	
	private final ThermoSensor thermoSensor;
	
	private boolean terminated = false;
	
	public EnvironmentSim(AirConditioner acUnit, ThermoSensor thermoSensor)
	{
		this.acUnit = acUnit;
		this.thermoSensor = thermoSensor;
	}
	
	public void start() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (!terminated) 
				{
					try 
					{
						Thread.sleep(UPDATE_RATE);
					} 
					catch (final InterruptedException e) 
					{
						L.warn("Caught Interrupted Exception.");
						terminated = true;
					}
					
					switch (acUnit.getMode()) 
					{
						case HEATING:
							thermoSensor.modifyTemperature(OFFSET);
							break;
						case COOLING:
							thermoSensor.modifyTemperature(-OFFSET);
							break;
						case OFF:
							thermoSensor.modifyTemperature((2 - R.nextInt(5)) * OFFSET);
							break;
					}
				}
			}
		}).start();
	}
}
