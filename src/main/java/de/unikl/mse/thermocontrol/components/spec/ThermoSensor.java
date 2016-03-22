package de.unikl.mse.thermocontrol.components.spec;

import de.unikl.mse.thermocontrol.messaging.MessageConsumer;
import de.unikl.mse.thermocontrol.messaging.ThermoSensorMessage;

public interface ThermoSensor extends MessageConsumer<ThermoSensorMessage>
{
	// No additional methods.
}
