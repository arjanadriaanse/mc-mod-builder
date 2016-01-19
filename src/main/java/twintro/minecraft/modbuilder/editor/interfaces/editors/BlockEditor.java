package twintro.minecraft.modbuilder.editor.interfaces.editors;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.imageio.ImageIO;
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
import twintro.minecraft.modbuilder.data.resources.recipes.ItemStackResource;
import twintro.minecraft.modbuilder.editor.generator.ResourcePackIO;
import twintro.minecraft.modbuilder.editor.interfaces.activitypanels.BlocksActivityPanel;
import twintro.minecraft.modbuilder.editor.interfaces.choosewindows.BlockModelChooseWindow;
import twintro.minecraft.modbuilder.editor.interfaces.choosewindows.DropChooseWindow;
import twintro.minecraft.modbuilder.editor.interfaces.choosewindows.ObjectRunnable;
import twintro.minecraft.modbuilder.editor.interfaces.helperclasses.ColorListCellRenderer;
import twintro.minecraft.modbuilder.editor.interfaces.helperclasses.WindowClosingVerifierListener;
import twintro.minecraft.modbuilder.editor.interfaces.helperclasses.WindowClosingVerifierUser;
import twintro.minecraft.modbuilder.editor.resources.BlockElement;
import twintro.minecraft.modbuilder.editor.resources.MaterialResources;

public class BlockEditor extends PropertiesEditor {
	private JPanel modelPanel;
	private JPanel dropsPanel;
	private JPanel dropsSubPanel;
	private JPanel dropsSubSubPanel;
	private JPanel propertiesPanelA;
	private JPanel propertiesPanelB;
	private JPanel flammablePanel;
	private JPanel replacablePanel;
	private JPanel requiresToolPanel;
	private JPanel solidPanel;
	private JPanel opaquePanel;
	private JLabel labelCreativeTab;
	private JLabel labelModel;
	private JLabel labelDrops;
	private JLabel labelLightness;
	private JLabel labelOpacity;
	private JLabel labelSlipperiness;
	private JLabel labelHardness;
	private JLabel labelResistance;
	private JLabel labelHarvestLevel;
	private JLabel labelBurntime;
	private JLabel labelHarvestType;
	private JLabel labelMaterial;
	private JLabel labelUnbreakable;
	private JLabel labelMapColor;
	private JLabel labelMobility;
	private JLabel labelProperties;
	private JLabel labelEmpty;
	private JLabel modelLabel;
	private JLabel dropsLabel;
	private JButton modelChooseButton;
	private JButton addDropButton;
	private JButton resetDropsButton;
	private JSpinner lightnessSpinner;
	private JSpinner opacitySpinner;
	private JSpinner slipperinessSpinner;
	private JSpinner hardnessSpinner;
	private JSpinner resistanceSpinner;
	private JSpinner harvestLevelSpinner;
	private JSpinner burntimeSpinner;
	private JComboBox creativeTabComboBox;
	private JComboBox harvestTypeComboBox;
	private JComboBox materialComboBox;
	private JComboBox mapColorComboBox;
	private JComboBox mobilityCombobox;
	private JCheckBox dropsCheckBox;
	private JCheckBox unbreakableCheckBox;
	private JCheckBox flammableCheckBox;
	private JCheckBox replacableCheckBox;
	private JCheckBox requiresToolCheckBox;
	private JCheckBox solidCheckBox;
	private JCheckBox opaqueCheckBox;
	
	private BlockModelResource model;
	private List<ItemStackResource> drops;
	private ItemStackResource thisDrop;
	private List<ItemStackResource> thisDrops;

	private static final String modelTooltip = "The model determines what the block looks like in game";
	private static final String dropsTooltip = ""; //TODO
	private static final String creativeTabTooltip = "The creative tab the block will be in";
	private static final String lightnessTooltip = "The amount of light the block emits";
	private static final String opacityTooltip = "<html>The number indicating how much the light level will decrease when passing through this block.<br>" + 
								"For most blocks this is 15 (the maximum value), but for some blocks, like water or glass, it is less.</html>";
	private static final String slipperinessTooltip = "How slippery the block is. For example, ice has a high slipperiness value";
	private static final String hardnessTooltip = "How long it takes you to break the block";
	private static final String resistanceTooltip = "How resistant the block is to explosions";
	private static final String harvestLevelTooltip = "<html> How good the tool needs to be to harvest the block.<br>"+
								"A harvestlevel of 0 means a wooden/golden tool is good enough, 1 means you need at least stone, 2 is iron and 3 is diamond</html>";
	private static final String burntimeTooltip = "<html>Burn time is the amount of ticks the block will burn if used as a fuel.<br>" + 
								"A second is 20 ticks, and one item takes 10 seconds (or 200 ticks) to cook or smelt</html>";
	private static final String unbreakableTooltip = "Set to true to make the block unbreakable in survival mode, like bedrock or barrier block";
	private static final String harvestTypeTooltip = "Which type of tool is required to mine the block.";
	private static final String materialTooltip = "OUTDATED AND DOES NOT WORK ANYMORE MIKE CHANGE PLS"; //TODO this will change
	private static final String mapColorTooltip = ""; //TODO
	private static final String mobilityTooltip = ""; //TODO
	private static final String flammableTooltip = ""; //TODO
	private static final String replacableTooltip = ""; //TODO
	private static final String requiresToolTooltip = ""; //TODO
	private static final String solidTooltip = ""; //TODO
	private static final String opaqueTooltip = ""; //TODO

	private final ObjectRunnable modelChooser = new ObjectRunnable(){
		@Override
		public void run(Object obj){
			setModel((BlockModelResource) obj);
		}
	};
	private final ObjectRunnable dropChooser = new ObjectRunnable(){
		@Override
		public void run(Object obj){
			addDrop((ItemStackResource) obj);
		}
	};
	
	public BlockEditor(String name, ObjectRunnable runnable, ObjectRunnable closeHandler) {
		super(name, runnable, closeHandler);
		setBounds(100, 100, 500, 570);
		setTitle("Edit Block: " + this.name);
		saveButton.setText("Save Block");
		
		thisDrop = new ItemStackResource();
		thisDrop.block = "modbuilder:" + name;
		thisDrop.amount = 1;
		thisDrop.amountincrease = 0;
		thisDrops = new ArrayList<ItemStackResource>();
		thisDrops.add(thisDrop);
		
		labelModel = label("Model", modelTooltip, labelPanel);
		modelChooseButton = button("Choose", modelTooltip);
		modelLabel = label("No Model", modelTooltip);
		modelPanel = panel(modelLabel, modelChooseButton, interactionPanel);
		modelChooseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chooseModel();
			}
		});
		
		labelDrops = label("Drops", dropsTooltip, labelPanel);
		dropsCheckBox = checkbox("Use", dropsTooltip);
		addDropButton = button("Add", dropsTooltip);
		resetDropsButton = button("Reset", dropsTooltip);
		dropsLabel = label("", dropsTooltip);
		dropsSubSubPanel = panel(dropsLabel, resetDropsButton);
		dropsSubPanel = panel(dropsSubSubPanel, addDropButton);
		dropsPanel = panel(dropsSubPanel, dropsCheckBox, interactionPanel);
		dropsCheckBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				useDrops();
			}
		});
		addDropButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addDrop();
			}
		});
		resetDropsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetDrops();
			}
		});
		drops = new ArrayList<ItemStackResource>();
		useDrops();
		
		labelCreativeTab = label("Creative tab", creativeTabTooltip, labelPanel);
		creativeTabComboBox = combobox(creativeTabTooltip, interactionPanel);
		creativeTabComboBox.setModel(new DefaultComboBoxModel(new String[] {"block", "decorations", "redstone", "transport", "misc", 
				"food", "tools", "combat", "brewing", "materials", "inventory"}));
		
		labelLightness = label("Lightness", lightnessTooltip, labelPanel);
		lightnessSpinner = spinner(lightnessTooltip, interactionPanel);
		lightnessSpinner.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), new Integer(15), new Integer(1)));
		
		labelOpacity = label("Opacity", opacityTooltip, labelPanel);
		opacitySpinner = spinner(opacityTooltip, interactionPanel);
		opacitySpinner.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), new Integer(255), new Integer(1)));
		
		labelSlipperiness = label("Slipperiness", slipperinessTooltip, labelPanel);
		slipperinessSpinner = spinner(slipperinessTooltip, interactionPanel);
		slipperinessSpinner.setModel(new SpinnerNumberModel(new Float(0.6F), new Float(0), null, new Float(0.1F)));
		
		labelHardness = label("Hardness", hardnessTooltip, labelPanel);
		hardnessSpinner = spinner(hardnessTooltip, interactionPanel);
		hardnessSpinner.setModel(new SpinnerNumberModel(new Float(0), new Float(0), null, new Float(1)));
		
		labelResistance = label("Resistance", resistanceTooltip, labelPanel);
		resistanceSpinner = spinner(resistanceTooltip, interactionPanel);
		resistanceSpinner.setModel(new SpinnerNumberModel(new Float(0), new Float(0), null, new Float(1)));
		
		labelHarvestLevel = label("Harvest level", harvestLevelTooltip, labelPanel);
		harvestLevelSpinner = spinner(harvestLevelTooltip, interactionPanel);
		harvestLevelSpinner.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		
		labelBurntime = label("Burn time", burntimeTooltip, labelPanel);
		burntimeSpinner = spinner(burntimeTooltip, interactionPanel);
		burntimeSpinner.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		
		labelUnbreakable = label("Unbreakable", unbreakableTooltip, labelPanel);
		unbreakableCheckBox = checkbox("", unbreakableTooltip, interactionPanel);
		unbreakableCheckBox.setHorizontalAlignment(SwingConstants.RIGHT);
		
		labelHarvestType = label("Harvest type", harvestTypeTooltip, labelPanel);
		harvestTypeComboBox = combobox(harvestTypeTooltip, interactionPanel);
		harvestTypeComboBox.setModel(new DefaultComboBoxModel(new String[] {"none", "pickaxe", "shovel", "axe"}));
		
		labelMaterial = label("Material", materialTooltip, labelPanel);
		materialComboBox = combobox(materialTooltip, interactionPanel);
		materialComboBox.setModel(new DefaultComboBoxModel(new String[] {"Material", "custom", "air", "grass", "ground", "wood", "rock", "iron", 
				"anvil", "water", "lava", "leaves", "plants", "vine", "sponge", "cloth", "fire", "sand", "circuits", "carpet", "glass", 
				"redstone_light", "tnt", "coral", "ice", "packed_ice", "snow", "crafted_snow", "cactus", "clay", "gourd", "dragon_egg", "portal", 
				"cake", "web", "piston", "barrier"}));
		materialComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				customMaterial();
			}
		});
		
		labelMapColor =  label("Map color", mapColorTooltip, labelPanel);
		mapColorComboBox = combobox(mapColorTooltip, interactionPanel);
		mapColorComboBox.setModel(new DefaultComboBoxModel(ColorListCellRenderer.mapColors));
		mapColorComboBox.setRenderer(new ColorListCellRenderer());
		
		labelMobility = label("Mobility", mobilityTooltip, labelPanel);
		mobilityCombobox = combobox(mobilityTooltip, interactionPanel);
		mobilityCombobox.setModel(new DefaultComboBoxModel(new String[]{
				"Normal piston push behaviour", "Break when pushed by piston", "Not pushable by pistons"}));
		
		labelProperties = new JLabel("Properties");
		labelPanel.add(labelProperties);

		labelEmpty = new JLabel("");
		labelPanel.add(labelEmpty);
		
		propertiesPanelA = new JPanel();
		propertiesPanelA.setLayout(new GridLayout(0, 3, 0, 0));
		interactionPanel.add(propertiesPanelA);
		
		flammableCheckBox = checkbox("Flammable", flammableTooltip);
		flammablePanel = panel(flammableCheckBox);
		propertiesPanelA.add(flammablePanel);
		
		replacableCheckBox = checkbox("Replacable", replacableTooltip);
		replacablePanel = panel(replacableCheckBox);
		propertiesPanelA.add(replacablePanel);
		
		requiresToolCheckBox = checkbox("Requires tool", requiresToolTooltip);
		requiresToolPanel = panel(requiresToolCheckBox);
		propertiesPanelA.add(requiresToolPanel);
		
		propertiesPanelB = new JPanel();
		propertiesPanelB.setLayout(new GridLayout(0, 3, 0, 0));
		interactionPanel.add(propertiesPanelB);
		
		solidCheckBox = checkbox("Solid", solidTooltip);
		solidPanel = panel(solidCheckBox);
		propertiesPanelB.add(solidPanel);
		
		opaqueCheckBox = checkbox("Opaque", opaqueTooltip);
		opaquePanel = panel(opaqueCheckBox);
		propertiesPanelB.add(opaquePanel);
		
		customMaterial();
	}
	
	public BlockEditor(BlockElement block, ObjectRunnable runnable, ObjectRunnable closeHandler){
		this(block.name, runnable, closeHandler);
		
		BaseBlockResource base = block.block;
		
		if (block.blockModel != null)
			setModel(block.blockModel);
		if (base.drops != null){
			if (!(base.drops.size() == 1 && base.drops.get(0).block.equals(thisDrop.block) 
					&& base.drops.get(0).amount == thisDrop.amount && base.drops.get(0).amountincrease == thisDrop.amountincrease)){
				dropsCheckBox.setSelected(true);
				useDrops();
				for (ItemStackResource drop : base.drops)
					addDrop(drop);
			}
		}
		if (base.material != null)
			materialComboBox.setSelectedItem(base.material.name());
		else if (base.flammable != null || base.replaceable != null || base.requirestool != null || base.solid != null || base.opaque != null 
				|| base.mapcolor != null || base.mobility != null){
			materialComboBox.setSelectedItem("custom");
			customMaterial();
			if (base.mapcolor != null)
				mapColorComboBox.setSelectedIndex(base.mapcolor);
			if (base.mobility != null)
				mobilityCombobox.setSelectedIndex(base.mobility);
			if (base.flammable != null)
				flammableCheckBox.setSelected(base.flammable);
			if (base.replaceable != null)
				replacableCheckBox.setSelected(base.replaceable);
			if (base.requirestool != null)
				requiresToolCheckBox.setSelected(base.requirestool);
			if (base.solid != null)
				solidCheckBox.setSelected(base.solid);
			if (base.opaque != null)
				opaqueCheckBox.setSelected(base.opaque);
		}
		if (base.tab != null)
			creativeTabComboBox.setSelectedItem(base.tab.name());
		if (base.light != null)
			lightnessSpinner.setValue(base.light);
		if (base.opacity != null)
			opacitySpinner.setValue(base.opacity);
		if (base.slipperiness != null)
			slipperinessSpinner.setValue(base.slipperiness);
		if (base.hardness != null)
			hardnessSpinner.setValue(base.hardness);
		if (base.resistance != null)
			resistanceSpinner.setValue(base.resistance);
		if (base.unbreakable != null)
			unbreakableCheckBox.setSelected(base.unbreakable);
		if (base.harvesttype != null)
			harvestTypeComboBox.setSelectedItem(base.harvesttype);
		if (base.harvestlevel != null)
			harvestLevelSpinner.setValue(base.harvestlevel);
		if (base.burntime != null)
			burntimeSpinner.setValue(base.burntime);
		setIconImage(block.getImage().getImage());
		
		changed = false;
	}

	@Override
	public boolean save(){
		if (model != null && materialComboBox.getSelectedIndex() != 0){
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
			if (materialComboBox.getSelectedItem() == "custom"){
				base.mapcolor = mapColorComboBox.getSelectedIndex();
				base.mobility = mobilityCombobox.getSelectedIndex();
				base.flammable = flammableCheckBox.isSelected();
				base.replaceable = replacableCheckBox.isSelected();
				base.requirestool = requiresToolCheckBox.isSelected();
				base.solid = solidCheckBox.isSelected();
				base.opaque = opaqueCheckBox.isSelected();
			}
			else
				base.material = MaterialResource.valueOf((String) materialComboBox.getSelectedItem());
			if (dropsCheckBox.isSelected())
				base.drops = drops;
			else
				base.drops = thisDrops;
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
			
			runnable.run(block);
			dispose();
		}
		else{
			String errorMessage = "You haven't given the block a model yet.";
			if (model != null) errorMessage = "You haven't given the block a material yet.";
			int selected = JOptionPane.showConfirmDialog(this, errorMessage, 
					"Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
			if (selected == JOptionPane.OK_OPTION)
				return false;
		}
		return true;
	}
	
	private void chooseModel() {
		if (model == null)
			new BlockModelChooseWindow(modelChooser);
		else
			new BlockModelChooseWindow(modelChooser, model);
	}

	private void setModel(BlockModelResource model) {
		change();
		this.model = model;
		if (model.parent == "block/cross") modelLabel.setText("Cross model");
		else modelLabel.setText("Block model");
		BlockElement block = new BlockElement();
		block.blockModel = model;
		setIconImage(block.getImage().getImage());
	}
	
	private void useDrops(){
		boolean use = dropsCheckBox.isSelected();
		labelDrops.setEnabled(use);
		dropsLabel.setEnabled(use);
		addDropButton.setEnabled(use);
		resetDropsButton.setEnabled(use);
	}
	
	private void addDrop(){
		new DropChooseWindow(dropChooser);
	}
	
	private void addDrop(ItemStackResource item){
		change();
		
		drops.add(item);
		
		String material;
		if (item.item != null)
			material = MaterialResources.simplifyItemStackName(item.item);
		else
			material = MaterialResources.simplifyItemStackName(item.block);
		
		String amount;
		if (item.amountincrease == 0)
			amount = item.amount + "";
		else
			amount = item.amount + "-" + (item.amountincrease + item.amount);
		
		if (dropsLabel.getText().length() > 0) dropsLabel.setText(dropsLabel.getText() + ",");
		dropsLabel.setText(dropsLabel.getText() + amount + " " + material);
	}
	
	private void resetDrops(){
		change();
		drops.clear();
		dropsLabel.setText("");
	}
	
	private void customMaterial(){
		boolean use = materialComboBox.getSelectedItem() == "custom";
		labelMapColor.setVisible(use);
		labelMobility.setVisible(use);
		labelProperties.setVisible(use);
		labelEmpty.setVisible(use);
		mapColorComboBox.setVisible(use);
		mobilityCombobox.setVisible(use);
		propertiesPanelA.setVisible(use);
		propertiesPanelB.setVisible(use);
	}
}

