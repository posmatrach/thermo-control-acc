package de.unikl.mse.thermocontrol.messaging;

public class UIMessage extends BaseMessage<UINotification>
{
	private static final long serialVersionUID = 7622880328434983313L;

	public UIMessage(UINotification value)
	{
		super(MessageType.UI_MESSAGE, value);
	}
}
