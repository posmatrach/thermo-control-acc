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
		thermoSensor.addSubscriber(thermostat);
		ControlPanel controlPanel = new ControlPanelImpl();
		AirConditioner acUnit = new AirConditionerImpl();
		
		thermostat.setACUnit(acUnit);
		thermostat.setControlPanel(controlPanel);
		thermostat.setThermoSensor(thermoSensor);
		
		EnvironmentSim sim = new EnvironmentSim(acUnit, thermoSensor);
		
		sim.start();
	}
}
