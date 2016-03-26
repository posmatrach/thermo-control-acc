package de.unikl.mse.thermocontrol.components.spec;

import de.unikl.mse.thermocontrol.messaging.MessageConsumer;
import de.unikl.mse.thermocontrol.messaging.TemperatureMessage;
import de.unikl.mse.thermocontrol.messaging.ThermoSensorMessage;

public interface ThermoSensor extends ACComponent<ThermoSensorMessage>
{
	void modifyTemperature(double newTemperature);
	void addSubscriber(MessageConsumer<TemperatureMessage> subscriber);
}
