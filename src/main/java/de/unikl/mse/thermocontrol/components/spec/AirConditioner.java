package de.unikl.mse.thermocontrol.components.spec;

import de.unikl.mse.thermocontrol.components.impl.ACMode;
import de.unikl.mse.thermocontrol.components.spec.base.ACComponent;
import de.unikl.mse.thermocontrol.messaging.ACMessage;

public interface AirConditioner extends ACComponent<ACMessage>
{
	ACMode getMode();
}
