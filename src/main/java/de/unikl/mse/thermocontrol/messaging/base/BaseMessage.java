package de.unikl.mse.thermocontrol.messaging.base;

import java.io.Serializable;

import de.unikl.mse.thermocontrol.messaging.MessageType;

public class BaseMessage<T> implements Serializable
{
	private static final long serialVersionUID = 4523540219821556541L;
	
	private MessageType messageType;
	
	private T value;
	
	public BaseMessage(MessageType messageType)
	{
		this(messageType, null);
	}
	
	public BaseMessage(MessageType messageType, T value)
	{
		this.messageType = messageType;
		this.value = value;
	}
	
	public T getValue()
	{
		return value;
	}
	
	public MessageType getMessageType()
	{
		return messageType;
	}
}
