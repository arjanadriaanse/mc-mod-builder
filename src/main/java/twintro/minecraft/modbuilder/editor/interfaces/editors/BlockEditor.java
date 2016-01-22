package twintro.minecraft.modbuilder.editor.interfaces.editors;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
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
import javax.swing.SwingUtilities;
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
	private JPanel propertiesPanel;
	private JPanel replacablePanel;
	private JPanel requiresToolPanel;
	private JPanel solidPanel;
	private JPanel unbreakablePanel;
	private JPanel flammablePanel;
	private JPanel flammableSubPanel;
	private JPanel flammabilityPanel;
	private JPanel fireSpreadSpeedPanel;
	private JPanel harvestRestrictionsPanel;
	private JPanel harvestRestrictionsSubPanel;
	private JPanel harvestTypePanel;
	private JPanel harvestLevelPanel;
	private JLabel labelCreativeTab;
	private JLabel labelModel;
	private JLabel labelDrops;
	private JLabel labelLightness;
	private JLabel labelOpacity;
	private JLabel labelSlipperiness;
	private JLabel labelHardness;
	private JLabel labelResistance;
	private JLabel labelBurntime;
	private JLabel labelHarvestRestrictions;
	private JLabel labelHarvestLevel;
	private JLabel labelHarvestType;
	private JLabel labelUnbreakable;
	private JLabel labelMapColor;
	private JLabel labelMobility;
	private JLabel labelProperties;
	private JLabel labelFlammable;
	private JLabel labelFlammability;
	private JLabel labelFireSpreadSpeed;
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
	private JSpinner burntimeSpinner;
	private JSpinner flammabilitySpinner;
	private JSpinner fireSpreadSpeedSpinner;
	private JComboBox creativeTabComboBox;
	private JComboBox harvestTypeComboBox;
	private JComboBox mapColorComboBox;
	private JComboBox mobilityCombobox;
	private JComboBox harvestLevelComboBox;
	private JCheckBox dropsCheckBox;
	private JCheckBox unbreakableCheckBox;
	private JCheckBox replacableCheckBox;
	private JCheckBox requiresToolCheckBox;
	private JCheckBox solidCheckBox;
	private JCheckBox flammableCheckBox;
	private JCheckBox harvestRestrictionsCheckBox;
	
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
								"A second is 20 ticks, and one item takes 10 seconds (or 200 ticks) to cook or smelt</html>";//TODO 0=null
	private static final String unbreakableTooltip = "Set to true to make the block unbreakable in survival mode, like bedrock or barrier block";
	private static final String harvestTypeTooltip = "Which type of tool is required to mine the block.";
	private static final String harvestRestrictionsTooltip = ""; //TODO
	private static final String mapColorTooltip = ""; //TODO
	private static final String mobilityTooltip = ""; //TODO
	private static final String replacableTooltip = ""; //TODO
	private static final String requiresToolTooltip = ""; //TODO
	private static final String solidTooltip = ""; //TODO
	private static final String flammableTooltip = ""; //TODO
	private static final String flammabilityTooltip = ""; //TODO
	private static final String fireSpreadSpeedTooltip = ""; //TODO

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
		dropsLabel = tooltipLabel("", dropsTooltip);
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
		
		labelFlammability = label("Flammability", flammabilityTooltip);
		flammabilitySpinner = spinner(flammabilityTooltip);
		flammabilityPanel = new JPanel();
		flammabilityPanel.setLayout(new BorderLayout(5, 0));
		flammabilityPanel.add(labelFlammability, BorderLayout.WEST);
		flammabilityPanel.add(flammabilitySpinner, BorderLayout.CENTER);
		flammabilitySpinner.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), new Integer(300), new Integer(1)));
		
		labelFireSpreadSpeed = label("Fire Spread Speed", fireSpreadSpeedTooltip);
		fireSpreadSpeedSpinner = spinner(fireSpreadSpeedTooltip);
		fireSpreadSpeedPanel = new JPanel();
		fireSpreadSpeedPanel.setLayout(new BorderLayout(5, 0));
		fireSpreadSpeedPanel.add(labelFireSpreadSpeed, BorderLayout.WEST);
		fireSpreadSpeedPanel.add(fireSpreadSpeedSpinner, BorderLayout.CENTER);
		fireSpreadSpeedSpinner.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), new Integer(300), new Integer(1)));
		
		labelFlammable = label("Flammable", flammableTooltip, labelPanel);
		flammableCheckBox = checkbox("Use", flammableTooltip);
		flammableSubPanel = new JPanel();
		flammableSubPanel.setLayout(new GridLayout(0, 2, 5, 0));
		flammableSubPanel.add(flammabilityPanel);
		flammableSubPanel.add(fireSpreadSpeedPanel);
		flammablePanel = panel(flammableSubPanel, flammableCheckBox, interactionPanel);
		flammableCheckBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				useFlammable();
			}
		});

		labelHarvestType = label("Harvest Type", harvestTypeTooltip);
		harvestTypeComboBox = combobox(harvestTypeTooltip);
		harvestTypePanel = new JPanel();
		harvestTypePanel.setLayout(new BorderLayout(5, 0));
		harvestTypePanel.add(labelHarvestType, BorderLayout.WEST);
		harvestTypePanel.add(harvestTypeComboBox, BorderLayout.CENTER);
		harvestTypeComboBox.setModel(new DefaultComboBoxModel(new String[] {"Pickaxe", "Shovel", "Axe"}));
		
		labelHarvestLevel = label("Harvest Level", harvestLevelTooltip);
		harvestLevelComboBox = combobox(harvestLevelTooltip);
		harvestLevelPanel = new JPanel();
		harvestLevelPanel.setLayout(new BorderLayout(5, 0));
		harvestLevelPanel.add(labelHarvestLevel, BorderLayout.WEST);
		harvestLevelPanel.add(harvestLevelComboBox, BorderLayout.CENTER);
		harvestLevelComboBox.setModel(new DefaultComboBoxModel(new String[] {"Wood", "Stone", "Iron", "Diamond"}));
		
		labelHarvestRestrictions = label("Harvest Restrictions", harvestRestrictionsTooltip, labelPanel);
		harvestRestrictionsCheckBox = checkbox("Use", harvestRestrictionsTooltip);
		harvestRestrictionsSubPanel = new JPanel();
		harvestRestrictionsSubPanel.setLayout(new GridLayout(0, 2, 5, 0));
		harvestRestrictionsSubPanel.add(harvestTypePanel);
		harvestRestrictionsSubPanel.add(harvestLevelPanel);
		harvestRestrictionsPanel = panel(harvestRestrictionsSubPanel, harvestRestrictionsCheckBox, interactionPanel);
		harvestRestrictionsCheckBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				useHarvestRestrictions();
			}
		});
		
		labelProperties = new JLabel("Properties");
		labelPanel.add(labelProperties);
		
		propertiesPanel = new JPanel();
		propertiesPanel.setLayout(new GridLayout(0, 4, 0, 0));
		interactionPanel.add(propertiesPanel);
		
		solidCheckBox = checkbox("Solid", solidTooltip);
		solidCheckBox.setSelected(true);
		solidPanel = panel(solidCheckBox);
		propertiesPanel.add(solidPanel);
		solidCheckBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				useSolid();
			}
		});
		
		unbreakableCheckBox = checkbox("Unbreakable", unbreakableTooltip);
		unbreakablePanel = panel(unbreakableCheckBox);
		propertiesPanel.add(unbreakablePanel);
		
		requiresToolCheckBox = checkbox("Requires Tool", requiresToolTooltip);
		requiresToolPanel = panel(requiresToolCheckBox);
		propertiesPanel.add(requiresToolPanel);
		
		replacableCheckBox = checkbox("Replacable", replacableTooltip);
		replacablePanel = panel(replacableCheckBox);
		propertiesPanel.add(replacablePanel);
		
		labelMapColor =  label("Map Color", mapColorTooltip, labelPanel);
		mapColorComboBox = combobox(mapColorTooltip, interactionPanel);
		mapColorComboBox.setModel(new DefaultComboBoxModel(ColorListCellRenderer.mapColors));
		mapColorComboBox.setRenderer(new ColorListCellRenderer());
		
		labelCreativeTab = label("Creative Tab", creativeTabTooltip, labelPanel);
		creativeTabComboBox = combobox(creativeTabTooltip, interactionPanel);
		creativeTabComboBox.setModel(new DefaultComboBoxModel(new String[] {"Block", "Decorations", "Redstone", "Transport", "Misc", 
				"Food", "Tools", "Combat", "Brewing", "Materials", "Inventory"}));
		
		labelMobility = label("Mobility", mobilityTooltip, labelPanel);
		mobilityCombobox = combobox(mobilityTooltip, interactionPanel);
		mobilityCombobox.setModel(new DefaultComboBoxModel(new String[]{
				"Normal piston push behaviour", "Break when pushed by piston", "Not pushable by pistons"}));
		
		labelSlipperiness = label("Slipperiness", slipperinessTooltip, labelPanel);
		slipperinessSpinner = spinner(slipperinessTooltip, interactionPanel);
		slipperinessSpinner.setModel(new SpinnerNumberModel(new Float(0.6F), new Float(0), null, new Float(0.1F)));
		
		labelBurntime = label("Burn time", burntimeTooltip, labelPanel);
		burntimeSpinner = spinner(burntimeTooltip, interactionPanel);
		burntimeSpinner.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		
		labelLightness = label("Lightness", lightnessTooltip, labelPanel);
		lightnessSpinner = spinner(lightnessTooltip, interactionPanel);
		lightnessSpinner.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), new Integer(15), new Integer(1)));
		
		labelOpacity = label("Opacity", opacityTooltip, labelPanel);
		opacitySpinner = spinner(opacityTooltip, interactionPanel);
		opacitySpinner.setModel(new SpinnerNumberModel(new Integer(15), new Integer(0), new Integer(15), new Integer(1)));
		
		labelHardness = label("Hardness", hardnessTooltip, labelPanel);
		hardnessSpinner = spinner(hardnessTooltip, interactionPanel);
		hardnessSpinner.setModel(new SpinnerNumberModel(new Float(0.5F), new Float(0), null, new Float(0.1F)));
		
		labelResistance = label("Resistance", resistanceTooltip, labelPanel);
		resistanceSpinner = spinner(resistanceTooltip, interactionPanel);
		resistanceSpinner.setModel(new SpinnerNumberModel(new Float(2.5F), new Float(0), null, new Float(1)));
		
		useDrops();
		useFlammable();
		useHarvestRestrictions();
		useSolid();
		
		setSize(500);
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
		if (base.mapcolor != null)
			mapColorComboBox.setSelectedIndex(base.mapcolor);
		if (base.mobility != null)
			mobilityCombobox.setSelectedIndex(base.mobility);
		if (base.replaceable != null)
			replacableCheckBox.setSelected(base.replaceable);
		if (base.requirestool != null)
			requiresToolCheckBox.setSelected(base.requirestool);
		if (base.solid != null)
			solidCheckBox.setSelected(base.solid);
		useSolid();
		if (base.tab != null)
			creativeTabComboBox.setSelectedItem(base.tab.name().substring(0,1).toUpperCase() + base.tab.name().substring(1));
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
		if (base.harvesttype != null || base.harvestlevel != null){
			if (base.harvesttype != null)
				harvestTypeComboBox.setSelectedItem(base.harvesttype.substring(0,1).toUpperCase() + base.harvesttype.substring(1));
			if (base.harvestlevel != null)
				harvestLevelComboBox.setSelectedIndex(base.harvestlevel);
			harvestRestrictionsCheckBox.setSelected(true);
			useHarvestRestrictions();
		}
		if (base.burntime != null)
			burntimeSpinner.setValue(base.burntime);
		if (base.flammability != null || base.firespreadspeed != null){
			if (base.flammability != null)
				flammabilitySpinner.setValue(base.flammability);
			if (base.firespreadspeed != null)
				fireSpreadSpeedSpinner.setValue(base.firespreadspeed);
			flammableCheckBox.setSelected(true);
			useFlammable();
		}
		setIconImage(block.getImage().getImage());
		
		changed = false;
	}

	@Override
	public boolean save(){
		if (model != null){
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
			base.mapcolor = mapColorComboBox.getSelectedIndex();
			base.mobility = mobilityCombobox.getSelectedIndex();
			base.replaceable = replacableCheckBox.isSelected();
			base.requirestool = requiresToolCheckBox.isSelected();
			base.solid = solidCheckBox.isSelected();
			if (dropsCheckBox.isSelected())
				base.drops = drops;
			else
				base.drops = thisDrops;
			base.model = "modbuilder:" + name;
			base.tab = TabResource.valueOf(((String) creativeTabComboBox.getSelectedItem()).toLowerCase());
			base.light = (Integer) lightnessSpinner.getValue();
			if (solidCheckBox.isSelected())
				base.opacity = (Integer) opacitySpinner.getValue();
			if (solidCheckBox.isSelected())
				base.slipperiness = (Float) slipperinessSpinner.getValue();
			base.hardness = (Float) hardnessSpinner.getValue();
			base.resistance = (Float) resistanceSpinner.getValue();
			base.unbreakable = unbreakableCheckBox.isSelected();
			if (harvestRestrictionsCheckBox.isSelected()){
				base.harvesttype = ((String) harvestTypeComboBox.getSelectedItem()).toLowerCase();
				base.harvestlevel = (Integer) harvestLevelComboBox.getSelectedIndex();
			}
			if ((Integer) burntimeSpinner.getValue() > 0) 
				base.burntime = (Integer) burntimeSpinner.getValue();
			if (flammableCheckBox.isSelected()){
				base.flammability = (Integer) flammabilitySpinner.getValue();
				base.firespreadspeed = (Integer) fireSpreadSpeedSpinner.getValue();
			}
			block.block = base;
			block.setOpaqueAndCutout();
			
			runnable.run(block);
			dispose();
		}
		else{
			int selected = JOptionPane.showConfirmDialog(this, "You haven't given the block a model yet.", 
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
		if (model.parent == "block/cross") modelLabel.setText("Cross Model");
		else modelLabel.setText("Block Model");
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
		
		if (dropsLabel.getText().length() > 0) dropsLabel.setText(dropsLabel.getText() + ", ");
		dropsLabel.setText(dropsLabel.getText() + amount + " " + material);
	}
	
	private void resetDrops(){
		change();
		drops.clear();
		dropsLabel.setText("");
	}
	
	private void useFlammable(){
		boolean use = flammableCheckBox.isSelected();
		labelFlammable.setEnabled(use);
		labelFlammability.setEnabled(use);
		labelFireSpreadSpeed.setEnabled(use);
		flammabilitySpinner.setEnabled(use);
		fireSpreadSpeedSpinner.setEnabled(use);
	}
	
	private void useHarvestRestrictions(){
		boolean use = harvestRestrictionsCheckBox.isSelected();
		labelHarvestRestrictions.setEnabled(use);
		labelHarvestType.setEnabled(use);
		labelHarvestLevel.setEnabled(use);
		harvestTypeComboBox.setEnabled(use);
		harvestLevelComboBox.setEnabled(use);
	}
	
	private void useSolid(){
		boolean use = solidCheckBox.isSelected();
		labelOpacity.setEnabled(use);
		labelSlipperiness.setEnabled(use);
		slipperinessSpinner.setEnabled(use);
		opacitySpinner.setEnabled(use);
	}
}

