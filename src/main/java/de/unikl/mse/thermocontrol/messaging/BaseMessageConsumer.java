package de.unikl.mse.thermocontrol.messaging;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public abstract class BaseMessageConsumer<T extends BaseMessage<?>> implements MessageConsumer<T>
{
	protected BlockingQueue<T> messageQueue = new LinkedBlockingQueue<>();

	@Override
	public void sendMessage(T m)
	{
		this.messageQueue.add(m);
	}
}
