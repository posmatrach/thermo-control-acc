package de.unikl.mse.thermocontrol.messaging.base;

public interface MessageConsumer<T extends BaseMessage<?>> 
{
	void sendMessage(T m);
}
