package de.unikl.mse.thermocontrol.components.impl;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.unikl.mse.thermocontrol.components.spec.ControlPanel;
import de.unikl.mse.thermocontrol.components.spec.Thermostat;
import de.unikl.mse.thermocontrol.messaging.ControlPanelMessage;
import de.unikl.mse.thermocontrol.messaging.MessageType;
import de.unikl.mse.thermocontrol.messaging.TemperatureMessage;
import de.unikl.mse.thermocontrol.messaging.base.BaseMessageConsumer;

public class ControlPanelImpl extends BaseMessageConsumer<ControlPanelMessage> implements ControlPanel {
	
	static private final Logger L = LoggerFactory.getLogger(ControlPanelImpl.class);
	
	private Thermostat thermostat;
	
	private final JFrame frame;
	private final JLabel measuredTempLbl;
	private final JLabel desiredTempLbl;
	private final JLabel controlLampLbl;
	private final JButton incTempBtn;
	private final JButton decTempBtn;
	private final GroupLayout layout;
	
	private volatile boolean terminated = false;

	public ControlPanelImpl() 
	{

		// GUI code taken from MTC example
		frame = new JFrame("AC Control");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		layout = new GroupLayout(this.frame.getContentPane());
		frame.setLayout(this.layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		final GroupLayout.SequentialGroup vGroup = this.layout.createSequentialGroup();
		final GroupLayout.ParallelGroup hGroup = this.layout.createParallelGroup();

		measuredTempLbl = new JLabel();
		measuredTempLbl.setBorder(BorderFactory.createTitledBorder("Measured Temperature"));
		measuredTempLbl.setMinimumSize(new Dimension(200, 0));
		measuredTempLbl.setText("-- °C");
		hGroup.addComponent(this.measuredTempLbl);
		vGroup.addComponent(this.measuredTempLbl);

		desiredTempLbl = new JLabel();
		desiredTempLbl.setBorder(BorderFactory.createTitledBorder("Desired Temperature"));
		desiredTempLbl.setMinimumSize(new Dimension(200, 0));
		desiredTempLbl.setText("-- °C");
		hGroup.addComponent(this.desiredTempLbl);
		vGroup.addComponent(this.desiredTempLbl);

		controlLampLbl = new JLabel();
		controlLampLbl.setBorder(BorderFactory.createTitledBorder("Air Conditioner"));
		controlLampLbl.setMinimumSize(new Dimension(200, 0));
		controlLampLbl.setText("OFF");
		hGroup.addComponent(this.controlLampLbl);
		vGroup.addComponent(this.controlLampLbl);

		layout.setHorizontalGroup(hGroup);
		layout.setVerticalGroup(vGroup);

		final JPanel btnPanel = new JPanel();

		decTempBtn = new JButton("-");
		incTempBtn = new JButton("+");

		incTempBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				thermostat.sendMessage(new TemperatureMessage(MessageType.SET_TEMPERATURE, new Integer(1)));
			}
		});

		decTempBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				thermostat.sendMessage(new TemperatureMessage(MessageType.SET_TEMPERATURE, new Integer(-1)));
			}
		});

		btnPanel.add(this.incTempBtn);
		btnPanel.add(this.decTempBtn);

		hGroup.addComponent(btnPanel);
		vGroup.addComponent(btnPanel);
	}

	@Override
	public void run()
	{
		// If thermostat is not registered, it doesn't make any sense to continue.
		if(null == thermostat)
		{
			L.error("No thermostat registered, terminating...");
			return;
		}
		
		// Gracefully shutdown the processing loop if the window is closed.
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e)
			{
				terminate();
				super.windowClosing(e);
			}
		});
		
		frame.pack();
		frame.setLocation(500, 300);

		this.frame.setVisible(true);
		
		while(!terminated)
		{
			try
			{
				ControlPanelMessage cpMessage = messageQueue.take();
				ACStatus status = cpMessage.getValue();
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run()
					{
						measuredTempLbl.setText(status.getCurrentTemperature() + " °C");
						desiredTempLbl.setText(status.getDesiredTemperature() + " °C");
						controlLampLbl.setText(status.getACMode().toString());
					}
				});
			}
			catch(InterruptedException e)
			{
				terminate();
				L.warn("Caught interrupted exception, terminatig...");
			}		
		}
		L.info("Terminatig Control Panel processing loop...");
	}

	@Override
	public void terminate()
	{
		terminated = true;
		if(frame.isEnabled())
			frame.dispose();
	}

	@Override
	public void setThermostat(Thermostat thermostat)
	{
		this.thermostat = thermostat;
	}
}
