package de.unikl.mse.thermocontrol.components.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.unikl.mse.thermocontrol.components.spec.ThermoSensor;
import de.unikl.mse.thermocontrol.messaging.MessageType;
import de.unikl.mse.thermocontrol.messaging.TemperatureMessage;
import de.unikl.mse.thermocontrol.messaging.ThermoSensorMessage;
import de.unikl.mse.thermocontrol.messaging.base.BaseMessageConsumer;
import de.unikl.mse.thermocontrol.messaging.base.MessageConsumer;

public class ThermoSensorImpl extends BaseMessageConsumer<ThermoSensorMessage> implements ThermoSensor
{	
	static private final Logger L = LoggerFactory.getLogger(ThermoSensorImpl.class);
	
	static private TemperatureMessage createMessage(double temperature)
	{
		return new TemperatureMessage(MessageType.CURRENT_TEMPERATURE, new Integer((int) temperature));
	}
	
	private final List<MessageConsumer<TemperatureMessage>> subscribers = Collections.synchronizedList(new ArrayList<>());
	
	private volatile double currentTemperature = 16.0;
	
	private volatile boolean terminated = false;
	
	@Override
	public void run()
	{
		L.info("* Thermo sensor: running...");
		while(!terminated)
		{
			try
			{
				// We don't expect any value, and the type shields us from receiving bogus
				// messages, so we can safely just remove it from queue.
				// BlockingQueue#take is blocking call, so the rest won't execute, unless there is a message available.
				messageQueue.take();
				TemperatureMessage currentTemperatureMessage = createMessage(currentTemperature);
				
				// We notify all the subscribers about current temperature.
				for (MessageConsumer<TemperatureMessage> s: subscribers)
					s.sendMessage(currentTemperatureMessage);
			} catch (InterruptedException e)
			{
				L.warn("Caught exception: " + e.getMessage());
			}
		}
		L.info("* Thermal sensor: terminating...");
	}
	
	@Override
	public synchronized void modifyTemperature(double deltaTemperature)
	{
		currentTemperature += deltaTemperature;
		L.info(String.format("Measured temperature: %.2f", currentTemperature));
	}
	
	@Override
	public void addSubscriber(MessageConsumer<TemperatureMessage> subscriber)
	{
		subscribers.add(subscriber);
	}

	@Override
	public void terminate()
	{
		this.terminated = true;
	}
}
