package de.unikl.mse.thermocontrol.messaging;

public interface MessageConsumer<T extends BaseMessage<?>> 
{
	void sendMessage(T m);
}
