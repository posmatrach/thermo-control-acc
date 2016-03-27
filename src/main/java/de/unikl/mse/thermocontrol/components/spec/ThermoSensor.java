package de.unikl.mse.thermocontrol.components.spec;

import de.unikl.mse.thermocontrol.components.spec.base.ACComponent;
import de.unikl.mse.thermocontrol.messaging.TemperatureMessage;
import de.unikl.mse.thermocontrol.messaging.ThermoSensorMessage;
import de.unikl.mse.thermocontrol.messaging.base.MessageConsumer;

public interface ThermoSensor extends ACComponent<ThermoSensorMessage>
{
	void modifyTemperature(double newTemperature);
	void addSubscriber(MessageConsumer<TemperatureMessage> subscriber);
}
