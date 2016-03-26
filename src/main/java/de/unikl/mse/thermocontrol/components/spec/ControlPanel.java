package de.unikl.mse.thermocontrol.components.spec;

import de.unikl.mse.thermocontrol.messaging.ControlPanelMessage;

public interface ControlPanel extends ACComponent<ControlPanelMessage>
{
	void setThermostat(Thermostat thermostat);
}
