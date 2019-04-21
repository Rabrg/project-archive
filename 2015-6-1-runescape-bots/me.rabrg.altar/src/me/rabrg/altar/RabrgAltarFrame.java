package me.rabrg.altar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.LayoutStyle;
import javax.swing.WindowConstants;

public class RabrgAltarFrame extends JFrame {

	private static final long serialVersionUID = -1446295543670647063L;

	private final JComboBox<String> bonesComboBox;
	private final JCheckBox staminaPotionsCheckBox;
	private final JButton startButton;

	public RabrgAltarFrame(final RabrgAltar rabrgAltar) {
		setTitle(rabrgAltar.getManifest().name());

		bonesComboBox = new JComboBox<String>();
		staminaPotionsCheckBox = new JCheckBox();
		startButton = new JButton();

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		bonesComboBox.setModel(new DefaultComboBoxModel<String>(new String[] { "Bones", "Big bones", "Babydragon bones", "Dragon bones" }));

		staminaPotionsCheckBox.setText("Stamina potions");

		startButton.setText("Start");
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent evt) {
				dispose();
				rabrgAltar.actualStart((String) bonesComboBox.getSelectedItem(), staminaPotionsCheckBox.isSelected());
			}
		});

		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
			layout.createParallelGroup(GroupLayout.Alignment.LEADING)
			.addGroup(layout.createSequentialGroup()
				.addContainerGap()
				.addComponent(bonesComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
				.addComponent(staminaPotionsCheckBox)
				.addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
				.addComponent(startButton)
				.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		layout.setVerticalGroup(
			layout.createParallelGroup(GroupLayout.Alignment.LEADING)
			.addGroup(layout.createSequentialGroup()
				.addContainerGap()
				.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
					.addComponent(bonesComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addComponent(staminaPotionsCheckBox)
					.addComponent(startButton))
				.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		
		setLocationRelativeTo(null);

		pack();
	}	   
}
