package de.unikl.mse.thermocontrol.messaging;

import de.unikl.mse.thermocontrol.components.impl.ACMode;

public class ACMessage extends BaseMessage<ACMode>
{
	private static final long serialVersionUID = -4866790507640684050L;

	public ACMessage(ACMode value)
	{
		super(MessageType.AC_MODE, value);
	}
}
