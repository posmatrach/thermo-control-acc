package de.unikl.mse.thermocontrol.components.impl;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import de.unikl.mse.thermocontrol.components.spec.ControlPanel;
import de.unikl.mse.thermocontrol.components.spec.Thermostat;
import de.unikl.mse.thermocontrol.messaging.BaseMessageConsumer;
import de.unikl.mse.thermocontrol.messaging.ControlPanelMessage;
import de.unikl.mse.thermocontrol.messaging.MessageType;
import de.unikl.mse.thermocontrol.messaging.TemperatureMessage;

public class ControlPanelImpl extends BaseMessageConsumer<ControlPanelMessage> implements ControlPanel {
	
	private Thermostat thermostat;
	
	private final JFrame frame;
	private final JLabel measuredTempLbl;
	private final JLabel desiredTempLbl;
	private final JLabel controlLampLbl;
	private final JButton incTempBtn;
	private final JButton decTempBtn;
	private final GroupLayout layout;

	public ControlPanelImpl() 
	{

		// GUI code taken from MTC example
		this.frame = new JFrame("AC Control");
		this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.layout = new GroupLayout(this.frame.getContentPane());
		this.frame.setLayout(this.layout);
		this.layout.setAutoCreateGaps(true);
		this.layout.setAutoCreateContainerGaps(true);

		final GroupLayout.SequentialGroup vGroup = this.layout.createSequentialGroup();
		final GroupLayout.ParallelGroup hGroup = this.layout.createParallelGroup();

		this.measuredTempLbl = new JLabel();
		this.measuredTempLbl.setBorder(BorderFactory.createTitledBorder("Measured Temperature"));
		this.measuredTempLbl.setMinimumSize(new Dimension(200, 0));
		this.measuredTempLbl.setText("-- °C");
		hGroup.addComponent(this.measuredTempLbl);
		vGroup.addComponent(this.measuredTempLbl);

		this.desiredTempLbl = new JLabel();
		this.desiredTempLbl.setBorder(BorderFactory.createTitledBorder("Desired Temperature"));
		this.desiredTempLbl.setMinimumSize(new Dimension(200, 0));
		this.desiredTempLbl.setText("-- °C");
		hGroup.addComponent(this.desiredTempLbl);
		vGroup.addComponent(this.desiredTempLbl);

		this.controlLampLbl = new JLabel();
		this.controlLampLbl.setBorder(BorderFactory.createTitledBorder("Air Conditioner"));
		this.controlLampLbl.setMinimumSize(new Dimension(200, 0));
		this.controlLampLbl.setText("OFF");
		hGroup.addComponent(this.controlLampLbl);
		vGroup.addComponent(this.controlLampLbl);

		this.layout.setHorizontalGroup(hGroup);
		this.layout.setVerticalGroup(vGroup);

		final JPanel btnPanel = new JPanel();

		this.decTempBtn = new JButton("-");
		this.incTempBtn = new JButton("+");

		this.incTempBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				thermostat.sendMessage(new TemperatureMessage(MessageType.SET_TEMPERATURE, new Integer(1)));
			}
		});

		this.decTempBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				thermostat.sendMessage(new TemperatureMessage(MessageType.SET_TEMPERATURE, new Integer(-1)));
			}
		});

		btnPanel.add(this.incTempBtn);
		btnPanel.add(this.decTempBtn);

		hGroup.addComponent(btnPanel);
		vGroup.addComponent(btnPanel);

		this.frame.pack();
		this.frame.setLocation(500, 300);

		this.frame.setVisible(true);
	}

	@Override
	public void run()
	{
		// TODO Auto-generated method stub	
	}

	@Override
	public void terminate()
	{
		// TODO Auto-generated method stub	
	}

	@Override
	public void setThermostat(Thermostat thermostat)
	{
		this.thermostat = thermostat;
	}
}
