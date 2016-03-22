package de.unikl.mse.thermocontrol.messaging;

import de.unikl.mse.thermocontrol.components.impl.ACMode;

/**
 * Simple DTO class for transporting Thermostat information
 * to the Control Panel.
 * 
 * @author Nenad Natosevic <nenad.natoshevic@gmail.com>
 *
 */
public class ACStatus
{
	private final int currentTemperature;
	private final int desiredTemperature;
	private final ACMode acMode;
	
	public ACStatus(int currentTemperature, int desiredTemperature, ACMode acMode)
	{
		this.currentTemperature = currentTemperature;
		this.desiredTemperature = desiredTemperature;
		this.acMode = acMode;
	}

	public int getCurrentTemperature()
	{
		return currentTemperature;
	}

	public int getDesiredTemperature()
	{
		return desiredTemperature;
	}

	public ACMode getACMode()
	{
		return acMode;
	}
}
