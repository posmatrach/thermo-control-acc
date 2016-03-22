package de.unikl.mse.thermocontrol.components.spec;

import de.unikl.mse.thermocontrol.messaging.MessageConsumer;
import de.unikl.mse.thermocontrol.messaging.TemperatureMessage;

public interface Thermostat extends MessageConsumer<TemperatureMessage>
{
	// No additional methods
}
