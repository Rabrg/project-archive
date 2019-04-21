package me.rabrg.power;

import java.awt.EventQueue;
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

public class RabrgPowerFrame extends JFrame {

	private static final long serialVersionUID = -8497694536663039777L;

	private JButton startButton;
    private JCheckBox dontMoveCheckBox;
    private JComboBox<String> skillComboBox;
    private JComboBox<String> modeComboBox;

	public RabrgPowerFrame() {
        initComponents();
    }
                       
    private void initComponents() {

        skillComboBox = new JComboBox<String>();
        dontMoveCheckBox = new JCheckBox();
        modeComboBox = new JComboBox<String>();
        startButton = new JButton();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        skillComboBox.setModel(new DefaultComboBoxModel<String>(new String[] { "Fishing", "Woodcutting", "Mining" }));
        skillComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent evt) {
            	final String item = (String) skillComboBox.getSelectedItem();
                if (item.equals("Woodcutting")) {
                	modeComboBox.setModel(new DefaultComboBoxModel<String>(new String[] { "Normal", "Oak", "Willow" }));
                } else if (item.equals("Fishing")) {
                	modeComboBox.setModel(new DefaultComboBoxModel<String>(new String[] { "Net", "Fly", "Lobster pot", "Harpoon" }));
                } else if (item.equals("Mining")) {
                	modeComboBox.setModel(new DefaultComboBoxModel<String>(new String[] { "Iron" }));
                }
            }
        });

        dontMoveCheckBox.setText("Don't move");

        modeComboBox.setModel(new DefaultComboBoxModel<String>(new String[] { "Net", "Fly", "Lobster pot", "Harpoon" }));

        startButton.setText("Start");

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(skillComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(modeComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(dontMoveCheckBox)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(startButton)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(skillComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(dontMoveCheckBox)
                    .addComponent(modeComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(startButton))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }                  

    public static void main(String args[]) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RabrgPowerFrame().setVisible(true);
            }
        });
    }
}
