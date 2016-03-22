package de.unikl.mse.thermocontrol.messaging;

import de.unikl.mse.thermocontrol.components.spec.ThermoSensor;

/**
 * Message sent to {@link ThermoSensor} in order to trigger
 * reading of the temperature.
 * 
 * @author Nenad Natosevic <nenad.natoshevic@gmail.com>
 *
 */
public class ThermoSensorMessage extends BaseMessage<Object>
{
	private static final long serialVersionUID = 8536350920228590840L;

	public ThermoSensorMessage()
	{
		super(MessageType.GET_TEMPERATURE);
	}
}
