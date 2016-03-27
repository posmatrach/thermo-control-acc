package de.unikl.mse.thermocontrol;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.unikl.mse.thermocontrol.components.impl.AirConditionerImpl;
import de.unikl.mse.thermocontrol.components.impl.ControlPanelImpl;
import de.unikl.mse.thermocontrol.components.impl.ThermoSensorImpl;
import de.unikl.mse.thermocontrol.components.impl.ThermostatImpl;
import de.unikl.mse.thermocontrol.components.spec.AirConditioner;
import de.unikl.mse.thermocontrol.components.spec.ControlPanel;
import de.unikl.mse.thermocontrol.components.spec.ThermoSensor;
import de.unikl.mse.thermocontrol.components.spec.Thermostat;

public class Application 
{
	static public final Logger L = LoggerFactory.getLogger(Application.class);
	
	public static void main(String[] args)
	{		
		L.info("Application Thermo Control running...");
		
		ThermoSensor thermoSensor = new ThermoSensorImpl();
		Thermostat thermostat = new ThermostatImpl();
		ControlPanel controlPanel = new ControlPanelImpl();
		AirConditioner acUnit = new AirConditionerImpl();
		
		thermoSensor.addSubscriber(thermostat);
		thermostat.setACUnit(acUnit);
		thermostat.setControlPanel(controlPanel);
		thermostat.setThermoSensor(thermoSensor);
		controlPanel.setThermostat(thermostat);
		
		EnvironmentSim sim = new EnvironmentSim(acUnit, thermoSensor);
		
		sim.start();
		Thread sensorThread = new Thread(thermoSensor);
		Thread thermostatThread = new Thread(thermostat);
		Thread acThread = new Thread(acUnit);
		Thread cpThread = new Thread(controlPanel);
		
		sensorThread.start();
		acThread.start();
		thermostatThread.start();
		cpThread.start();
	}
}
