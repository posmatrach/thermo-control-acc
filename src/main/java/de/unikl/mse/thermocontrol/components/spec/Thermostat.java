package de.unikl.mse.thermocontrol.components.spec;

import de.unikl.mse.thermocontrol.components.spec.base.ACComponent;
import de.unikl.mse.thermocontrol.messaging.TemperatureMessage;

public interface Thermostat extends ACComponent<TemperatureMessage>
{
	void setThermoSensor(ThermoSensor thermoSensor);
	void setACUnit(AirConditioner acUnit);
	void setControlPanel(ControlPanel controlPanel);
}
