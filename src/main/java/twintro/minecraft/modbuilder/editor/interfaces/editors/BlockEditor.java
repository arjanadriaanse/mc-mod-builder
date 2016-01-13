package twintro.minecraft.modbuilder.editor.interfaces.editors;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;

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

import twintro.minecraft.modbuilder.data.resources.MaterialResource;
import twintro.minecraft.modbuilder.data.resources.TabResource;
import twintro.minecraft.modbuilder.data.resources.blocks.BaseBlockResource;
import twintro.minecraft.modbuilder.data.resources.blocks.BlockResource;
import twintro.minecraft.modbuilder.data.resources.models.BlockModelResource;
import twintro.minecraft.modbuilder.data.resources.models.BlockstateResource;
import twintro.minecraft.modbuilder.data.resources.models.BlockstateResource.Variant;
import twintro.minecraft.modbuilder.data.resources.models.ItemModelResource;
import twintro.minecraft.modbuilder.data.resources.models.ItemModelResource.Display;
import twintro.minecraft.modbuilder.editor.interfaces.activitypanels.BlocksActivityPanel;
import twintro.minecraft.modbuilder.editor.interfaces.choosewindows.BlockModelChooseWindow;
import twintro.minecraft.modbuilder.editor.interfaces.choosewindows.BlockModelRunnable;
import twintro.minecraft.modbuilder.editor.interfaces.helperclasses.WindowClosingVerifierListener;
import twintro.minecraft.modbuilder.editor.resources.BlockElement;

public class BlockEditor extends JFrame implements BlockModelRunnable {
	protected JPanel buttonPanel;
	protected JPanel mainPanel;
	protected JPanel labelPanel;
	protected JPanel interactionPanel;
	protected JPanel modelPanel;
	protected JLabel labelCreativeTab;
	protected JLabel labelModel;
	protected JLabel labelLightness;
	protected JLabel labelOpacity;
	protected JLabel labelSlipperiness;
	protected JLabel labelHardness;
	protected JLabel labelResistance;
	protected JLabel labelHarvestLevel;
	protected JLabel labelBurntime;
	protected JLabel labelHarvestType;
	protected JLabel labelMaterial;
	protected JLabel labelUnbreakable;
	protected JLabel modelLabel;
	protected JButton renameButton;
	protected JButton saveBlockButton;
	protected JButton cancelButton;
	protected JButton modelChooseButton;
	protected JSpinner lightnessSpinner;
	protected JSpinner opacitySpinner;
	protected JSpinner slipperinessSpinner;
	protected JSpinner hardnessSpinner;
	protected JSpinner resistanceSpinner;
	protected JSpinner harvestLevelSpinner;
	protected JSpinner burntimeSpinner;
	protected JComboBox creativeTabComboBox;
	protected JComboBox harvestTypeComboBox;
	protected JComboBox materialComboBox;
	protected JCheckBox unbreakableCheckBox;
	
	protected boolean modelChooserIsOpen = false;
	protected String name;
	protected BlocksActivityPanel main;
	protected BlockModelResource model;

	private static final String modelTooltip = "The model determines what the block looks like in the game";
	private static final String creativeTabTooltip = ""; //TODO
	private static final String lightnessTooltip = ""; //TODO
	private static final String opacityTooltip = ""; //TODO
	private static final String slipperinessTooltip = ""; //TODO
	private static final String hardnessTooltip = ""; //TODO
	private static final String resistanceTooltip = ""; //TODO
	private static final String harvestLevelTooltip = ""; //TODO
	private static final String burntimeTooltip = ""; //TODO
	private static final String harvestTypeTooltip = ""; //TODO
	private static final String materialTooltip = ""; //TODO
	private static final String unbreakableTooltip = ""; //TODO
	
	public BlockEditor(String name, BlocksActivityPanel main) {
		this.name = name;
		this.main = main;

		setBounds(100, 100, 500, 500);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setTitle("Edit Block: " + this.name);
		addWindowListener(new WindowClosingVerifierListener());
		
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
		labelModel.setToolTipText(modelTooltip);
		labelPanel.add(labelModel);
		
		modelPanel = new JPanel();
		interactionPanel.add(modelPanel);
		modelPanel.setLayout(new BorderLayout(0, 0));
		
		modelChooseButton = new JButton("Choose");
		modelChooseButton.setToolTipText(modelTooltip);
		modelPanel.add(modelChooseButton, BorderLayout.EAST);
		modelChooseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chooseModel();
			}
		});
		
		modelLabel = new JLabel("No Model");
		modelLabel.setToolTipText(modelTooltip);
		modelLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		modelPanel.add(modelLabel, BorderLayout.CENTER);
		
		labelCreativeTab = new JLabel("Creative tab");
		labelCreativeTab.setToolTipText(creativeTabTooltip);
		labelPanel.add(labelCreativeTab);
		
		creativeTabComboBox = new JComboBox();
		creativeTabComboBox.setToolTipText(creativeTabTooltip);
		creativeTabComboBox.setModel(new DefaultComboBoxModel(new String[] {"block", "decorations", "redstone", "transport", "misc", 
				"food", "tools", "combat", "brewing", "materials", "inventory"}));
		interactionPanel.add(creativeTabComboBox);
		
		labelLightness = new JLabel("Lightness");
		labelLightness.setToolTipText(lightnessTooltip);
		labelPanel.add(labelLightness);
		
		lightnessSpinner = new JSpinner();
		lightnessSpinner.setToolTipText(lightnessTooltip);
		lightnessSpinner.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), new Integer(15), new Integer(1)));
		interactionPanel.add(lightnessSpinner);
		
		labelOpacity = new JLabel("Opacity");
		labelOpacity.setToolTipText(opacityTooltip);
		labelPanel.add(labelOpacity);
		
		opacitySpinner = new JSpinner();
		opacitySpinner.setToolTipText(opacityTooltip);
		opacitySpinner.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), new Integer(255), new Integer(1)));
		interactionPanel.add(opacitySpinner);
		
		labelSlipperiness = new JLabel("Slipperiness");
		labelSlipperiness.setToolTipText(slipperinessTooltip);
		labelPanel.add(labelSlipperiness);
		
		slipperinessSpinner = new JSpinner();
		slipperinessSpinner.setToolTipText(slipperinessTooltip);
		slipperinessSpinner.setModel(new SpinnerNumberModel(new Float(0.6F), new Float(0), null, new Float(0.1F)));
		interactionPanel.add(slipperinessSpinner);
		
		labelHardness = new JLabel("Hardness");
		labelHardness.setToolTipText(hardnessTooltip);
		labelPanel.add(labelHardness);
		
		hardnessSpinner = new JSpinner();
		hardnessSpinner.setToolTipText(hardnessTooltip);
		hardnessSpinner.setModel(new SpinnerNumberModel(new Float(0), new Float(0), null, new Float(1)));
		interactionPanel.add(hardnessSpinner);
		
		labelResistance = new JLabel("Resistance");
		labelResistance.setToolTipText(resistanceTooltip);
		labelPanel.add(labelResistance);
		
		resistanceSpinner = new JSpinner();
		resistanceSpinner.setToolTipText(resistanceTooltip);
		resistanceSpinner.setModel(new SpinnerNumberModel(new Float(0), new Float(0), null, new Float(1)));
		interactionPanel.add(resistanceSpinner);
		
		labelHarvestLevel = new JLabel("Harvest level");
		labelHarvestLevel.setToolTipText(harvestLevelTooltip);
		labelPanel.add(labelHarvestLevel);
		
		harvestLevelSpinner = new JSpinner();
		harvestLevelSpinner.setToolTipText(harvestLevelTooltip);
		harvestLevelSpinner.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		interactionPanel.add(harvestLevelSpinner);
		
		labelBurntime = new JLabel("Burn time");
		labelBurntime.setToolTipText(burntimeTooltip);
		labelPanel.add(labelBurntime);
		
		burntimeSpinner = new JSpinner();
		burntimeSpinner.setToolTipText(burntimeTooltip);
		burntimeSpinner.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		interactionPanel.add(burntimeSpinner);
		
		labelHarvestType = new JLabel("Harvest type");
		labelHarvestType.setToolTipText(harvestTypeTooltip);
		labelPanel.add(labelHarvestType);
		
		harvestTypeComboBox = new JComboBox();
		harvestTypeComboBox.setToolTipText(harvestTypeTooltip);
		harvestTypeComboBox.setModel(new DefaultComboBoxModel(new String[] {"none", "pickaxe", "shovel", "axe"}));
		interactionPanel.add(harvestTypeComboBox);
		
		labelMaterial = new JLabel("Material");
		labelMaterial.setToolTipText(materialTooltip);
		labelPanel.add(labelMaterial);
		
		materialComboBox = new JComboBox();
		materialComboBox.setToolTipText(materialTooltip);
		materialComboBox.setModel(new DefaultComboBoxModel(new String[] {"Material", "air", "grass", "ground", "wood", "rock", "iron", "anvil", 
				"water", "lava", "leaves", "plants", "vine", "sponge", "cloth", "fire", "sand", "circuits", "carpet", "glass", "redstone_light", 
				"tnt", "coral", "ice", "packed_ice", "snow", "crafted_snow", "cactus", "clay", "gourd", "dragon_egg", "portal", "cake", "web", 
				"piston", "barrier"}));
		interactionPanel.add(materialComboBox);
		
		labelUnbreakable = new JLabel("Unbreakable");
		labelUnbreakable.setToolTipText(unbreakableTooltip);
		labelPanel.add(labelUnbreakable);
		
		unbreakableCheckBox = new JCheckBox("");
		unbreakableCheckBox.setToolTipText(unbreakableTooltip);
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
		
		if (block.blockModel != null)
			setModel(block.blockModel);
		if (block.block.material != null)
			materialComboBox.setSelectedItem(block.block.material.name());
		if (block.block.tab != null)
			creativeTabComboBox.setSelectedItem(block.block.tab.name());
		if (block.block.light != null)
			lightnessSpinner.setValue(block.block.light);
		if (block.block.opacity != null)
			opacitySpinner.setValue(block.block.opacity);
		if (block.block.slipperiness != null)
			slipperinessSpinner.setValue(block.block.slipperiness);
		if (block.block.hardness != null)
			hardnessSpinner.setValue(block.block.hardness);
		if (block.block.resistance != null)
			resistanceSpinner.setValue(block.block.resistance);
		if (block.block.unbreakable != null)
			unbreakableCheckBox.setSelected(block.block.unbreakable);
		if (block.block.harvesttype != null)
			harvestTypeComboBox.setSelectedItem(block.block.harvesttype);
		if (block.block.harvestlevel != null)
			harvestLevelSpinner.setValue(block.block.harvestlevel);
		if (block.block.burntime != null)
			burntimeSpinner.setValue(block.block.burntime);
	}
	
	protected void cancel(){
		WindowClosingVerifierListener.close(this);
	}

	protected void save(){
		if (!modelChooserIsOpen && !(model == null) && materialComboBox.getSelectedIndex() != 0){
			BlockElement block = new BlockElement();
			block.name = name;
			block.blockModel = model;
			
			ItemModelResource itemModel = new ItemModelResource();
			itemModel.parent = "modbuilder:block/" + name;
			itemModel.display = Display.block();
			block.itemModel = itemModel;
			
			BlockstateResource blockstate = new BlockstateResource();
			blockstate.variants = new HashMap<String, Variant>();
			Variant variant = blockstate.new Variant();
			variant.model = "modbuilder:" + name;
			blockstate.variants.put("normal", variant);
			block.blockstate = blockstate;
			
			BaseBlockResource base = new BlockResource();
			base.material = MaterialResource.valueOf((String) materialComboBox.getSelectedItem());
			base.model = "modbuilder:" + name;
			base.tab = TabResource.valueOf((String) creativeTabComboBox.getSelectedItem());
			base.light = (Integer) lightnessSpinner.getValue();
			base.opacity = (Integer) opacitySpinner.getValue();
			base.slipperiness = (Float) slipperinessSpinner.getValue();
			base.hardness = (Float) hardnessSpinner.getValue();
			base.resistance = (Float) resistanceSpinner.getValue();
			base.unbreakable = unbreakableCheckBox.isSelected();
			if (harvestTypeComboBox.getSelectedIndex() != 0) 
				base.harvesttype = (String) harvestTypeComboBox.getSelectedItem();
			base.harvestlevel = (Integer) harvestLevelSpinner.getValue();
			base.burntime = (Integer) burntimeSpinner.getValue();
			block.block = base;
			
			main.addBlock(block);
		}
		else{
			JOptionPane.showMessageDialog(this, "Not all required properties are given a value yet.", "Error", JOptionPane.ERROR_MESSAGE);
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
			new BlockModelChooseWindow(main.main.TexturePanel.elements, this);
			modelChooserIsOpen = true;
		}
	}
	
	@Override
	public void dispose() {
		main.closeEditor(name);
		super.dispose();
	}

	@Override
	public BlockModelResource getModel() {
		return model;
	}

	@Override
	public void setModel(BlockModelResource model) {
		this.model = model;
		if (model.parent == "block/cross") modelLabel.setText("Cross model");
		else modelLabel.setText("Block model");
	}

	@Override
	public void blockModelChooserDispose() {
		modelChooserIsOpen = false;
	}
}

