package de.unikl.mse.thermocontrol.components.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.unikl.mse.thermocontrol.components.spec.AirConditioner;
import de.unikl.mse.thermocontrol.messaging.ACMessage;
import de.unikl.mse.thermocontrol.messaging.base.BaseMessageConsumer;

public class AirConditionerImpl extends BaseMessageConsumer<ACMessage> implements AirConditioner
{
	static private final Logger L = LoggerFactory.getLogger(AirConditionerImpl.class);
	
	private volatile ACMode mode = ACMode.OFF;
	
	private boolean terminated = false;
	
	@Override
	public void run()
	{
		while(!terminated)
		{
			try
			{
				ACMode newMode = messageQueue.take().getValue();
				if(newMode != mode)
					mode = newMode;
				L.info("Switching to " + mode.toString());
			} catch (InterruptedException e)
			{
				L.warn("Caught InterruptedException, terminatig...");
				terminate();
			}
		}
		L.info("Terminated Air Conditioner process loop.");
	}

	@Override
	public void terminate()
	{
		terminated = true;
	}

	@Override
	public ACMode getMode()
	{
		return mode;
	}
}
