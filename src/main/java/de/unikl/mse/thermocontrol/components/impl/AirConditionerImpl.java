package de.unikl.mse.thermocontrol.components.impl;

import de.unikl.mse.thermocontrol.components.spec.AirConditioner;
import de.unikl.mse.thermocontrol.messaging.ACMessage;
import de.unikl.mse.thermocontrol.messaging.BaseMessageConsumer;

public class AirConditionerImpl extends BaseMessageConsumer<ACMessage> implements AirConditioner
{
	private ACMode mode = ACMode.OFF;
	
	private boolean terminated = false;
	
	@Override
	public void run()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void terminate()
	{
		// TODO Auto-generated method stub
		terminated = true;
	}

	@Override
	public synchronized ACMode getMode()
	{
		return mode;
	}
}
