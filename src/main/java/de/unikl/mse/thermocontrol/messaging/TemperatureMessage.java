package de.unikl.mse.thermocontrol.messaging;

import de.unikl.mse.thermocontrol.messaging.base.BaseMessage;

public class TemperatureMessage extends BaseMessage<Integer>
{
	private static final long serialVersionUID = -6812639528866398669L;

	public TemperatureMessage(MessageType type, Integer value)
	{
		super(type, value);
	}
}
