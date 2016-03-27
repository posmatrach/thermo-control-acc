package de.unikl.mse.thermocontrol.messaging;

import de.unikl.mse.thermocontrol.components.impl.ACStatus;
import de.unikl.mse.thermocontrol.messaging.base.BaseMessage;

public class ControlPanelMessage extends BaseMessage<ACStatus>
{
	private static final long serialVersionUID = 7622880328434983313L;

	public ControlPanelMessage(ACStatus value)
	{
		super(MessageType.CONTROL_PANEL_MESSAGE, value);
	}
}
