package twintro.minecraft.modbuilder.editor.interfaces;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.AbstractButton;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import twintro.minecraft.modbuilder.editor.interfaces.helperclasses.WindowClosingVerifierListener;
import twintro.minecraft.modbuilder.editor.resources.BlockElement;

public class BlockEditor extends JFrame {
	protected JPanel buttonPanel;
	protected JPanel mainPanel;
	protected JPanel labelPanel;
	protected JPanel interactionPanel;
	protected JPanel modelPanel;
	protected JPanel creativeTabsPanel;
	protected JPanel creativeTabsSubPanel;
	protected JLabel labelCreativeTabs;
	protected JLabel labelModel;
	protected JLabel labelLightness;
	protected JLabel labelOpacity;
	protected JLabel labelSlipperiness;
	protected JLabel labelHardness;
	protected JLabel labelResistance;
	protected JLabel labelHarvestLevel;
	protected JLabel labelBurntime;
	protected JLabel labelHarvestType;
	protected JLabel labelUnbreakable;
	protected JLabel modelLabel;
	protected JLabel creativeTabsLabel;
	protected JButton renameButton;
	protected JButton saveBlockButton;
	protected JButton cancelButton;
	protected JButton modelChooseButton;
	protected JButton creativeTabsResetButton;
	protected JSpinner lightnessSpinner;
	protected JSpinner opacitySpinner;
	protected JSpinner slipperinessSpinner;
	protected JSpinner hardnessSpinner;
	protected JSpinner resistanceSpinner;
	protected JSpinner harvestLevelSpinner;
	protected JSpinner burntimeSpinner;
	protected JComboBox creativeTabsComboBox;
	protected JComboBox harvestTypeComboBox;
	protected JCheckBox unbreakableCheckBox;
	
	protected boolean modelChooserIsOpen = false;
	protected String name;
	protected BlocksActivityPanel main;
	
	public BlockEditor(String name, BlocksActivityPanel main) {
		this.name = name;
		this.main = main;

		setBounds(100, 100, 500, 500);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setTitle("Edit Block: " + this.name);
		addWindowListener(new WindowClosingVerifierListener());
		
		//TODO toolips
		
		mainPanel = new JPanel();
		getContentPane().add(mainPanel, BorderLayout.NORTH);
		mainPanel.setLayout(new BorderLayout(5, 5));
		
		labelPanel = new JPanel();
		mainPanel.add(labelPanel, BorderLayout.WEST);
		labelPanel.setLayout(new GridLayout(0, 1, 0, 5));
		
		interactionPanel = new JPanel();
		mainPanel.add(interactionPanel, BorderLayout.CENTER);
		interactionPanel.setLayout(new GridLayout(0, 1, 0, 5));
		
		labelModel = new JLabel("Model");
		labelPanel.add(labelModel);
		
		modelPanel = new JPanel();
		interactionPanel.add(modelPanel);
		modelPanel.setLayout(new BorderLayout(0, 0));
		
		modelChooseButton = new JButton("Choose");
		modelPanel.add(modelChooseButton, BorderLayout.EAST);
		modelChooseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chooseModel();
			}
		});
		
		modelLabel = new JLabel("");
		modelLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		modelPanel.add(modelLabel, BorderLayout.CENTER);
		
		labelCreativeTabs = new JLabel("Creative tabs");
		labelPanel.add(labelCreativeTabs);
		
		creativeTabsPanel = new JPanel();
		interactionPanel.add(creativeTabsPanel);
		creativeTabsPanel.setLayout(new BorderLayout(0, 0));
		
		creativeTabsComboBox = new JComboBox();
		creativeTabsComboBox.setModel(new DefaultComboBoxModel(new String[] {"Add", "block", "decorations", "redstone", "transport", "misc", 
				"food", "tools", "combat", "brewing", "materials", "inventory"}));
		creativeTabsComboBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == e.SELECTED){
					if (e.getItem() != "Add"){
						creativeTabsChoose((String) e.getItem());
						((JComboBox) e.getSource()).setSelectedIndex(0);
					}
				}
			}
		});
		creativeTabsPanel.add(creativeTabsComboBox, BorderLayout.EAST);
		
		creativeTabsSubPanel = new JPanel();
		creativeTabsPanel.add(creativeTabsSubPanel, BorderLayout.CENTER);
		creativeTabsSubPanel.setLayout(new BorderLayout(0, 0));
		
		creativeTabsResetButton = new JButton("Reset");
		creativeTabsResetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetTab();
			}
		});
		creativeTabsSubPanel.add(creativeTabsResetButton, BorderLayout.EAST);
		
		creativeTabsLabel = new JLabel("");
		creativeTabsSubPanel.add(creativeTabsLabel, BorderLayout.CENTER);
		
		labelLightness = new JLabel("Lightness");
		labelLightness.setToolTipText("Amount of light the block gives off (0-15)");
		labelPanel.add(labelLightness);
		
		lightnessSpinner = new JSpinner();
		lightnessSpinner.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		interactionPanel.add(lightnessSpinner);
		
		labelOpacity = new JLabel("Opacity");
		labelPanel.add(labelOpacity);
		
		opacitySpinner = new JSpinner();
		opacitySpinner.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), new Integer(255), new Integer(1)));
		interactionPanel.add(opacitySpinner);
		
		labelSlipperiness = new JLabel("Slipperiness");
		labelSlipperiness.setToolTipText("Reccomended values below 1");
		labelPanel.add(labelSlipperiness);
		
		slipperinessSpinner = new JSpinner();
		slipperinessSpinner.setModel(new SpinnerNumberModel(new Float(0.6F), new Float(0), null, new Float(1)));
		interactionPanel.add(slipperinessSpinner);
		
		labelHardness = new JLabel("Hardness");
		labelPanel.add(labelHardness);
		
		hardnessSpinner = new JSpinner();
		hardnessSpinner.setModel(new SpinnerNumberModel(new Float(0), new Float(0), null, new Float(1)));
		interactionPanel.add(hardnessSpinner);
		
		labelResistance = new JLabel("Resistance");
		labelResistance.setToolTipText("resistance to explosions");
		labelPanel.add(labelResistance);
		
		resistanceSpinner = new JSpinner();
		resistanceSpinner.setModel(new SpinnerNumberModel(new Float(0), new Float(0), null, new Float(1)));
		interactionPanel.add(resistanceSpinner);
		
		labelHarvestLevel = new JLabel("Harvest level");
		labelHarvestLevel.setToolTipText("(0=wood, 1=stone, etc)");
		labelPanel.add(labelHarvestLevel);
		
		harvestLevelSpinner = new JSpinner();
		harvestLevelSpinner.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		interactionPanel.add(harvestLevelSpinner);
		
		labelBurntime = new JLabel("Burn time");
		labelPanel.add(labelBurntime);
		
		burntimeSpinner = new JSpinner();
		burntimeSpinner.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		interactionPanel.add(burntimeSpinner);
		
		labelHarvestType = new JLabel("Harvest type");
		labelPanel.add(labelHarvestType);
		
		harvestTypeComboBox = new JComboBox();
		harvestTypeComboBox.setModel(new DefaultComboBoxModel(new String[] {"HarvestType"}));
		interactionPanel.add(harvestTypeComboBox);
		
		labelUnbreakable = new JLabel("Unbreakable");
		labelPanel.add(labelUnbreakable);
		
		unbreakableCheckBox = new JCheckBox("");
		interactionPanel.add(unbreakableCheckBox);
		unbreakableCheckBox.setHorizontalAlignment(SwingConstants.RIGHT);
		
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		
		renameButton = new JButton("Rename");
		renameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rename();
			}
		});
		buttonPanel.add(renameButton);
		
		saveBlockButton = new JButton("Save Block");
		saveBlockButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});
		buttonPanel.add(saveBlockButton);
		
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancel();
			}
		});
		buttonPanel.add(cancelButton);
		
		setVisible(true);
	}
	
	public BlockEditor(BlocksActivityPanel main, BlockElement block){
		this(block.name, main);
		
		//TODO read block and setup all values
	}
	
	protected void cancel(){
		WindowClosingVerifierListener.close(this);
	}

	protected void save(){
		if (!modelChooserIsOpen && modelLabel.getText().length() > 0){
			//TODO saving blocks
		}
	}
	
	protected void rename(){
		String name = JOptionPane.showInputDialog("Block name:");
		this.name = name;
		setTitle("Edit Block: " + this.name);
		//TODO refractor
	}
	
	protected void chooseModel() {
		if (!modelChooserIsOpen){
			new BlockModelChooseWindow(main.main.TexturePanel.elements, null);//TODO null is niet de bedoeling?? :) :O :D :P
			modelChooserIsOpen = true;
		}
	}

	protected void creativeTabsChoose(String tab){
		if (creativeTabsLabel.getText().length() > 0) creativeTabsLabel.setText(creativeTabsLabel.getText() + ",");
		creativeTabsLabel.setText(creativeTabsLabel.getText() + tab);
	}
	
	protected void resetTab(){
		creativeTabsLabel.setText("");
	}
	
	@Override
	public void dispose() {
		//TODO main.closeEditor(name);
		super.dispose();
	}
}

