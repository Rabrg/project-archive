package me.rabrg.plankpicker;

import org.dreambot.api.methods.MethodProvider;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;

/**
 * Created by Ryan Greene on 4/17/2016.
 */
public class PlankPickerGUIForm extends JFrame {

    private final PlankPicker script;

    public PlankPickerGUIForm(PlankPicker script) throws HeadlessException {
        this.script = script;
        textArea1.setText(script.loadWorlds());
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (muleCheckBox.isSelected())
                    script.setup(true, null, textArea1.getText(), Integer.parseInt(textField1.getText()), Integer.parseInt(textField2.getText()));
                else {
                    final String[] lines = textArea1.getText().split("\n");
                    int[] worlds = null;
                    for (final String line : lines) {
                        if (line.toLowerCase().contains(script.getLocalPlayer().getName().toLowerCase())) {
                            final String[] worldsRaw
                                    = line.split(script.getLocalPlayer().getName().toLowerCase() + ":")[1].split(",");
                            worlds = new int[worldsRaw.length];
                            for (int i = 0; i < worldsRaw.length; i++)
                                worlds[i] = Integer.parseInt(worldsRaw[i]);
                            break;
                        }
                    }
                    MethodProvider.log(Arrays.toString(worlds));
                    script.setup(false, worlds, textArea1.getText(), 0, 0);
                }
                dispose();
            }
        });
        textField1.setEnabled(false);
        textField2.setEnabled(false);
        muleCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (muleCheckBox.isSelected()) {
                    textField1.setEnabled(true);
                    textField2.setEnabled(true);
                } else {
                    textField1.setEnabled(false);
                    textField2.setEnabled(false);
                }
            }
        });
    }

    private JTextArea textArea1;
    private JPanel panel;
    private JCheckBox muleCheckBox;
    private JButton startButton;
    private JTextField textField1;
    private JTextField textField2;

    void start() {
        setContentPane(panel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(final WindowEvent e) {
                script.stop();
                dispose();
            }
        });
    }
}
