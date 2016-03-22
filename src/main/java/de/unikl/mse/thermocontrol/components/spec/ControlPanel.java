package de.unikl.mse.thermocontrol.components.spec;

import de.unikl.mse.thermocontrol.messaging.MessageConsumer;
import de.unikl.mse.thermocontrol.messaging.ControlPanelMessage;

public interface ControlPanel extends MessageConsumer<ControlPanelMessage>
{
	// No additional methods
}
