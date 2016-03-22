package de.unikl.mse.thermocontrol.messaging;

public class ControlPanelMessage extends BaseMessage<ACStatus>
{
	private static final long serialVersionUID = 7622880328434983313L;

	public ControlPanelMessage(ACStatus value)
	{
		super(MessageType.CONTROL_PANEL_MESSAGE, value);
	}
}
