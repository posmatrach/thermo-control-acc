package de.unikl.mse.thermocontrol.components.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.unikl.mse.thermocontrol.components.spec.ThermoSensor;
import de.unikl.mse.thermocontrol.messaging.BaseMessage;
import de.unikl.mse.thermocontrol.messaging.BaseMessageConsumer;
import de.unikl.mse.thermocontrol.messaging.MessageConsumer;
import de.unikl.mse.thermocontrol.messaging.MessageType;
import de.unikl.mse.thermocontrol.messaging.TemperatureMessage;
import de.unikl.mse.thermocontrol.messaging.ThermoSensorMessage;

public class ThermoSensorImpl extends BaseMessageConsumer<ThermoSensorMessage> implements Runnable, ThermoSensor
{	
	static private final Logger L = LoggerFactory.getLogger(ThermoSensorImpl.class);
	
	private final List<MessageConsumer<TemperatureMessage>> subscribers;
	
	private volatile int currentTemperature;
	
	private volatile boolean isTerminated = false;
	
	public ThermoSensorImpl()
	{
		this.subscribers = Collections.synchronizedList(new ArrayList<>());
		this.currentTemperature = 18;
	}
	
	@Override
	public void run()
	{
		L.info("* Thermo sensor: running...");
		while(!isTerminated)
		{
			try
			{
				// We don't expect any value, and the type shields us from receiving bogus
				// messages, so we can safely just remove it from queue.
				// BlockingQueue#take is blocking call, so the rest won't execute, unless there is a message available.
				messageQueue.take();
				BaseMessage<Integer> currentTemperatureMessage = createMessage();
				
				// We notify all the subscribers about current temperature.
				for (MessageConsumer s: subscribers)
					s.sendMessage(currentTemperatureMessage);
			} catch (InterruptedException e)
			{
				L.warn("Caught exception: " + e.getMessage());
			}
		}
		L.info("* Thermal sensor: terminating...");
	}
	
	public void setCurrentTemperature(int newTemperature)
	{
		this.currentTemperature = newTemperature;
	}
	
	public void addSubscriber(MessageConsumer<TemperatureMessage> subscriber)
	{
		subscribers.add(subscriber);
	}
	
	public void stop()
	{
		this.isTerminated = true;
	}
	
	private BaseMessage<Integer> createMessage()
	{
		return new BaseMessage<>(MessageType.CURRENT_TEMPERATURE, new Integer(currentTemperature));
	
	}
}
