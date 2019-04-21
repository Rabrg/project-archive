package me.rabrg.aiomake;

import me.rabrg.aiomake.nodes.GrandExchangeTaskNode;
import me.rabrg.aiomake.nodes.cooking.BankPizzaPotatoTaskNode;
import me.rabrg.aiomake.nodes.cooking.PizzaPotatoTaskNode;
import me.rabrg.aiomake.nodes.fletching.BankCutLogTaskNode;
import me.rabrg.aiomake.nodes.fletching.BankStringBowTaskNode;
import me.rabrg.aiomake.nodes.fletching.CutLogTaskNode;
import me.rabrg.aiomake.nodes.fletching.StringBowTaskNode;
import me.rabrg.aiomake.nodes.herblore.BankCleanHerbTaskNode;
import me.rabrg.aiomake.nodes.herblore.BankUnfinishedPotionTaskNode;
import me.rabrg.aiomake.nodes.herblore.CleanHerbTaskNode;
import me.rabrg.aiomake.nodes.herblore.UnfinishedPotionTaskNode;
import me.rabrg.aiomake.nodes.mule.GiveMuleTaskNode;
import me.rabrg.aiomake.nodes.mule.MuleTaskNode;
import me.rabrg.aiomake.nodes.process.BankProcessTaskNode;
import me.rabrg.aiomake.nodes.process.ProcessTaskNode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static me.rabrg.aiomake.AIOMake.*;

public class AIOMakeGUI extends JFrame {

    private final AIOMake script;

    public AIOMakeGUI(AIOMake script) throws HeadlessException {
        this.script = script;
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cutLogsRadioButton.isSelected()) {
                    switch (String.valueOf(logsComboBox.getSelectedItem())) {
                        case "Longbow":
                            buy = new GEItem[] { GEItem.LOGS };
                            sell = GEItem.NORMAL_LONGBOW_U;
                            bowWidget = "Long Bow";
                            buyUsed = new int[1];
                            break;
                        case "Oak longbow":
                            buy = new GEItem[] { GEItem.OAK_LOGS };
                            sell = GEItem.OAK_LONGBOW_U;
                            buyUsed = new int[1];
                            bowWidget = "Oak Long Bow";
                            break;
                        case "Maple longbow":
                            buy = new GEItem[] { GEItem.MAPLE_LOGS };
                            sell = GEItem.MAPLE_LONGBOW_U;
                            bowWidget = "Maple Long Bow";
                            buyUsed = new int[1];
                            break;
                        case "Willow longbow":
                            buy = new GEItem[] { GEItem.WILLOW_LOGS };
                            sell = GEItem.WILLOW_LONGBOW_U;
                            bowWidget = "Willow Long Bow";
                            buyUsed = new int[1];
                            break;
                        case "Yew longbow":
                            buy = new GEItem[] { GEItem.YEW_LOGS };
                            sell = GEItem.YEW_LONGBOW_U;
                            bowWidget = "Yew Long Bow";
                            buyUsed = new int[1];
                            break;
                        case "Magic longbow":
                            buy = new GEItem[] { GEItem.MAGIC_LOGS };
                            sell = GEItem.MAGIC_LONGBOW_U;
                            bowWidget = "Magic Long Bow";
                            buyUsed = new int[1];
                            break;
                    }
                    script.addNodes(new GrandExchangeTaskNode(), new GiveMuleTaskNode(), new CutLogTaskNode(), new BankCutLogTaskNode());
                } else if (stringBowsRadioButton.isSelected()) {
                    switch (String.valueOf(logsComboBox.getSelectedItem())) {
                        case "Longbow":
                            buy = new GEItem[] { GEItem.NORMAL_LONGBOW_U, GEItem.BOW_STRING };
                            sell = GEItem.NORMAL_LONGBOW;
                            buyUsed = new int[2];
                            break;
                        case "Oak longbow":
                            buy = new GEItem[] { GEItem.OAK_LONGBOW_U, GEItem.BOW_STRING };
                            sell = GEItem.OAK_LONGBOW;
                            buyUsed = new int[2];
                            break;
                        case "Maple longbow":
                            buy = new GEItem[] { GEItem.MAPLE_LONGBOW_U, GEItem.BOW_STRING };
                            sell = GEItem.MAPLE_LONGBOW;
                            buyUsed = new int[2];
                            break;
                        case "Willow longbow":
                            buy = new GEItem[] { GEItem.WILLOW_LONGBOW_U, GEItem.BOW_STRING };
                            sell = GEItem.WILLOW_LONGBOW;
                            buyUsed = new int[2];
                            break;
                        case "Yew longbow":
                            buy = new GEItem[] { GEItem.YEW_LONGBOW_U, GEItem.BOW_STRING };
                            sell = GEItem.YEW_LONGBOW;
                            buyUsed = new int[2];
                            break;
                        case "Magic longbow":
                            buy = new GEItem[] { GEItem.MAGIC_LONGBOW_U, GEItem.BOW_STRING };
                            sell = GEItem.MAGIC_LONGBOW;
                            buyUsed = new int[2];
                            break;
                    }
                    script.addNodes(new GrandExchangeTaskNode(), new GiveMuleTaskNode(), new StringBowTaskNode(), new BankStringBowTaskNode());
                } else if (cleanHerbsRadioButton.isSelected()) {
                    switch (String.valueOf(herbComboBox.getSelectedItem())) {
                        case "Guam":
                            buy = new GEItem[] { GEItem.GRIMY_GUAM };
                            sell = GEItem.GUAM;
                            break;
                        case "Marrentil":
                            buy = new GEItem[] { GEItem.GRIMY_MARRENTILL };
                            sell = GEItem.MARRENTILL;
                            break;
                        case "Tarromin":
                            buy = new GEItem[] { GEItem.GRIMY_TARROMIN };
                            sell = GEItem.TARROMIN;
                            break;
                        case "Harralander":
                            buy = new GEItem[] { GEItem.GRIMY_HARRALANDER };
                            sell = GEItem.HARRALANDER;
                            break;
                        case "Ranarr weed":
                            buy = new GEItem[] { GEItem.GRIMY_RANARR_WEED };
                            sell = GEItem.RANARR_WEED;
                            break;
                        case "Toadflax":
                            buy = new GEItem[] { GEItem.GRIMY_TOADFLAX };
                            sell = GEItem.TOADFLAX;
                            break;
                        case "Irit leaf":
                            buy = new GEItem[] { GEItem.GRIMY_IRIT_LEAF };
                            sell = GEItem.IRIT_LEAF;
                            break;
                        case "Avantoe":
                            buy = new GEItem[] { GEItem.GRIMY_AVANTOE };
                            sell = GEItem.AVANTOE;
                            break;
                        case "Kwuarm":
                            buy = new GEItem[] { GEItem.GRIMY_KWUARM };
                            sell = GEItem.KWUARM;
                            break;
                        case "Snapdragon":
                            buy = new GEItem[] { GEItem.GRIMY_SNAPDRAGON };
                            sell = GEItem.SNAPDRAGON;
                            break;
                        case "Cadantine":
                            buy = new GEItem[] { GEItem.GRIMY_CADANTINE };
                            sell = GEItem.CADANTINE;
                            break;
                        case "Lantadyme":
                            buy = new GEItem[] { GEItem.GRIMY_LANTADYME };
                            sell = GEItem.LANTADYME;
                            break;
                        case "Dwarf weed":
                            buy = new GEItem[] { GEItem.GRIMY_DWARF_WEED };
                            sell = GEItem.DWARF_WEED;
                            break;
                        case "Torstol":
                            buy = new GEItem[] { GEItem.GRIMY_TORSTOL };
                            sell = GEItem.TORSTOL;
                            break;
                    }
                    script.addNodes(new GrandExchangeTaskNode(), new GiveMuleTaskNode(), new CleanHerbTaskNode(), new BankCleanHerbTaskNode());
                } else if (unfinishedPotionsRadioButton.isSelected()) {
                    switch (String.valueOf(herbComboBox.getSelectedItem())) {
                        case "Guam":
                            buy = new GEItem[] { GEItem.GUAM, GEItem.VIAL_OF_WATER };
                            sell = GEItem.GUAM_POTION_UNF;
                            break;
                        case "Marrentil":
                            buy = new GEItem[] { GEItem.MARRENTILL, GEItem.VIAL_OF_WATER };
                            sell = GEItem.MARRENTILL_POTION_UNF;
                            break;
                        case "Tarromin":
                            buy = new GEItem[] { GEItem.TARROMIN, GEItem.VIAL_OF_WATER };
                            sell = GEItem.TARROMIN_POTION_UNF;
                            break;
                        case "Harralander":
                            buy = new GEItem[] { GEItem.HARRALANDER, GEItem.VIAL_OF_WATER };
                            sell = GEItem.HARRALANDER_POTION_UNF;
                            break;
                        case "Ranarr weed":
                            buy = new GEItem[] { GEItem.RANARR_WEED, GEItem.VIAL_OF_WATER };
                            sell = GEItem.RANARR_WEED_POTION_UNF;
                            break;
                        case "Toadflax":
                            buy = new GEItem[] { GEItem.TOADFLAX, GEItem.VIAL_OF_WATER };
                            sell = GEItem.TOADFLAX_POTION_UNF;
                            break;
                        case "Irit leaf":
                            buy = new GEItem[] { GEItem.IRIT_LEAF, GEItem.VIAL_OF_WATER };
                            sell = GEItem.IRIT_LEAF_POTION_UNF;
                            break;
                        case "Avantoe":
                            buy = new GEItem[] { GEItem.AVANTOE, GEItem.VIAL_OF_WATER };
                            sell = GEItem.AVANTOE_POTION_UNF;
                            break;
                        case "Kwuarm":
                            buy = new GEItem[] { GEItem.KWUARM, GEItem.VIAL_OF_WATER };
                            sell = GEItem.KWUARM_POTION_UNF;
                            break;
                        case "Snapdragon":
                            buy = new GEItem[] { GEItem.SNAPDRAGON, GEItem.VIAL_OF_WATER };
                            sell = GEItem.SNAPDRAGON_POTION_UNF;
                            break;
                        case "Cadantine":
                            buy = new GEItem[] { GEItem.CADANTINE, GEItem.VIAL_OF_WATER };
                            sell = GEItem.CADANTINE_POTION_UNF;
                            break;
                        case "Lantadyme":
                            buy = new GEItem[] { GEItem.LANTADYME, GEItem.VIAL_OF_WATER };
                            sell = GEItem.LANTADYME_POTION_UNF;
                            break;
                        case "Dwarf weed":
                            buy = new GEItem[] { GEItem.DWARF_WEED, GEItem.VIAL_OF_WATER };
                            sell = GEItem.DWARF_WEED_POTION_UNF;
                            break;
                        case "Torstol":
                            buy = new GEItem[] { GEItem.TORSTOL, GEItem.VIAL_OF_WATER };
                            sell = GEItem.TORSTOL_POTION_UNF;
                            break;
                    }
                    script.addNodes(new GrandExchangeTaskNode(), new GiveMuleTaskNode(), new UnfinishedPotionTaskNode(), new BankUnfinishedPotionTaskNode());
                } else if (processRadioButton.isSelected()) {
                    switch (String.valueOf(processComboBox.getSelectedItem())) {
                        case "Chocolate dust":
                            buy = new GEItem[] { GEItem.CHOCOLATE_BAR };
                            sell = GEItem.CHOCOLATE_DUST;
                            break;
                        case "Unicorn horn dust":
                            buy = new GEItem[] { GEItem.UNICORN_HORN };
                            sell = GEItem.UNICORN_DUST;
                            break;
                    }
                    script.addNodes(new GrandExchangeTaskNode(), new GiveMuleTaskNode(), new ProcessTaskNode(), new BankProcessTaskNode());
                } else if (pizzasRadioButton.isSelected()) {
                    switch (String.valueOf(pizzasComboBox.getSelectedItem())) {
                        case "Meat pizza":
                            buy = new GEItem[] { GEItem.PLAIN_PIZZA, GEItem.COOKED_MEAT };
                            sell = GEItem.MEAT_PIZZA;
                            break;
                        case "Anchovy pizza":
                            buy = new GEItem[] { GEItem.PLAIN_PIZZA, GEItem.ANCHOVIES };
                            sell = GEItem.ANCHOVY_PIZZA;
                            break;
                        case "Pineapple pizza":
                            buy = new GEItem[] { GEItem.PLAIN_PIZZA, GEItem.PINEAPPLE_CHUNKS };
                            sell = GEItem.PINEAPPLE_PIZZA;
                            break;
                    }
                    script.addNodes(new GrandExchangeTaskNode(), new GiveMuleTaskNode(), new PizzaPotatoTaskNode(), new BankPizzaPotatoTaskNode());
                } else if (piesRadioButton.isSelected()) {
                    return;
                } else if (muleRadioButton.isSelected()) {
                    script.addNodes(new MuleTaskNode());
                    hide = true;
                }
                if (!hide && amountTextField.getText() != null) {
                    amount = Integer.parseInt(amountTextField.getText());
                    goldKeep = Integer.parseInt(gpKeepTextField.getText());
                }
                dispose();
            }
        });
    }

    private JPanel panel1;
    private JRadioButton stringBowsRadioButton;
    private JRadioButton cutLogsRadioButton;
    private JComboBox logsComboBox;
    private JRadioButton cleanHerbsRadioButton;
    private JRadioButton unfinishedPotionsRadioButton;
    private JComboBox herbComboBox;
    private JRadioButton processRadioButton;
    private JComboBox processComboBox;
    private JRadioButton pizzasRadioButton;
    private JRadioButton potatosRadioButton;
    private JComboBox potatosComboBox;
    private JComboBox pizzasComboBox;
    private JButton startButton;
    private JTextField amountTextField;
    private JRadioButton muleRadioButton;
    private JRadioButton piesRadioButton;
    private JComboBox comboBox1;
    private JTextField baseTimeTextField;
    private JTextField randomTimeTextField;
    private JTextField gpKeepTextField;

    void start() {
        setContentPane(panel1);
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
