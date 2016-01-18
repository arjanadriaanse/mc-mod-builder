package twintro.minecraft.modbuilder.editor.interfaces.editors;

import java.awt.BorderLayout;
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
import java.util.HashMap;

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
import twintro.minecraft.modbuilder.editor.generator.ResourcePackIO;
import twintro.minecraft.modbuilder.editor.interfaces.activitypanels.BlocksActivityPanel;
import twintro.minecraft.modbuilder.editor.interfaces.choosewindows.BlockModelChooseWindow;
import twintro.minecraft.modbuilder.editor.interfaces.choosewindows.ObjectRunnable;
import twintro.minecraft.modbuilder.editor.interfaces.helperclasses.WindowClosingVerifierListener;
import twintro.minecraft.modbuilder.editor.interfaces.helperclasses.WindowClosingVerifierUser;
import twintro.minecraft.modbuilder.editor.resources.BlockElement;

public class BlockEditor extends WindowClosingVerifierUser {
	private JPanel buttonPanel;
	private JPanel mainPanel;
	private JPanel labelPanel;
	private JPanel interactionPanel;
	private JPanel modelPanel;
	private JLabel labelCreativeTab;
	private JLabel labelModel;
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
	private JLabel modelLabel;
	private JButton renameButton;
	private JButton saveBlockButton;
	private JButton cancelButton;
	private JButton modelChooseButton;
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
	private JCheckBox unbreakableCheckBox;
	
	private String name;
	private BlockModelResource model;
	private ObjectRunnable runnable;
	private ObjectRunnable closeHandler;
	
	private static final String modelTooltip = "The model determines what the block looks like in game";
	private static final String creativeTabTooltip = "The creative tab the block will be in";
	private static final String lightnessTooltip = "The amount of light the block emits";
	private static final String opacityTooltip = "The number indicating how much the light level will decrease when passing through this block. For most blocks this is 15 (the maximum value), but for some blocks, like water or glass, it is less.";
	private static final String slipperinessTooltip = "How slippery the block is. For example, ice has a high slipperiness value";
	private static final String hardnessTooltip = "How long it takes you to break the block";
	private static final String resistanceTooltip = "How resistant the block is to explosions";
	private static final String harvestLevelTooltip = "How good the tool needs to be to harvest the block. A harvestlevel of 0 means a wooden/golden tool is good enough, 1 means you need at least stone, 2 is iron and 3 is diamond";
	private static final String burntimeTooltip = "Burn time is the amount of ticks the block will burn if used as a fuel. A second is 20 ticks, and one item takes 10 seconds (or 200 ticks) to cook or smelt";
	private static final String harvestTypeTooltip = "Which type of tool is required to mine the block.";
	private static final String materialTooltip = "OUTDATED AND DOES NOT WORK ANYMORE MIKE CHANGE PLS"; //TODO this will change
	private static final String unbreakableTooltip = "Set to true to make the block unbreakable in survival mode, like bedrock or barrier block";
	
	private final ObjectRunnable modelChooser = new ObjectRunnable(){
		@Override
		public void run(Object obj){
			setModel((BlockModelResource) obj);
		}
	};
	
	public BlockEditor(String name, ObjectRunnable runnable, ObjectRunnable closeHandler) {
		this.name = name;
		this.runnable = runnable;
		this.closeHandler = closeHandler;

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
		creativeTabComboBox.addActionListener(actionListener);
		creativeTabComboBox.setModel(new DefaultComboBoxModel(new String[] {"block", "decorations", "redstone", "transport", "misc", 
				"food", "tools", "combat", "brewing", "materials", "inventory"}));
		interactionPanel.add(creativeTabComboBox);
		
		labelLightness = new JLabel("Lightness");
		labelLightness.setToolTipText(lightnessTooltip);
		labelPanel.add(labelLightness);
		
		lightnessSpinner = new JSpinner();
		lightnessSpinner.setToolTipText(lightnessTooltip);
		lightnessSpinner.addChangeListener(changeListener);
		lightnessSpinner.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), new Integer(15), new Integer(1)));
		interactionPanel.add(lightnessSpinner);
		
		labelOpacity = new JLabel("Opacity");
		labelOpacity.setToolTipText(opacityTooltip);
		labelPanel.add(labelOpacity);
		
		opacitySpinner = new JSpinner();
		opacitySpinner.setToolTipText(opacityTooltip);
		opacitySpinner.addChangeListener(changeListener);
		opacitySpinner.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), new Integer(255), new Integer(1)));
		interactionPanel.add(opacitySpinner);
		
		labelSlipperiness = new JLabel("Slipperiness");
		labelSlipperiness.setToolTipText(slipperinessTooltip);
		labelPanel.add(labelSlipperiness);
		
		slipperinessSpinner = new JSpinner();
		slipperinessSpinner.setToolTipText(slipperinessTooltip);
		slipperinessSpinner.addChangeListener(changeListener);
		slipperinessSpinner.setModel(new SpinnerNumberModel(new Float(0.6F), new Float(0), null, new Float(0.1F)));
		interactionPanel.add(slipperinessSpinner);
		
		labelHardness = new JLabel("Hardness");
		labelHardness.setToolTipText(hardnessTooltip);
		labelPanel.add(labelHardness);
		
		hardnessSpinner = new JSpinner();
		hardnessSpinner.setToolTipText(hardnessTooltip);
		hardnessSpinner.addChangeListener(changeListener);
		hardnessSpinner.setModel(new SpinnerNumberModel(new Float(0), new Float(0), null, new Float(1)));
		interactionPanel.add(hardnessSpinner);
		
		labelResistance = new JLabel("Resistance");
		labelResistance.setToolTipText(resistanceTooltip);
		labelPanel.add(labelResistance);
		
		resistanceSpinner = new JSpinner();
		resistanceSpinner.setToolTipText(resistanceTooltip);
		resistanceSpinner.addChangeListener(changeListener);
		resistanceSpinner.setModel(new SpinnerNumberModel(new Float(0), new Float(0), null, new Float(1)));
		interactionPanel.add(resistanceSpinner);
		
		labelHarvestLevel = new JLabel("Harvest level");
		labelHarvestLevel.setToolTipText(harvestLevelTooltip);
		labelPanel.add(labelHarvestLevel);
		
		harvestLevelSpinner = new JSpinner();
		harvestLevelSpinner.setToolTipText(harvestLevelTooltip);
		harvestLevelSpinner.addChangeListener(changeListener);
		harvestLevelSpinner.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		interactionPanel.add(harvestLevelSpinner);
		
		labelBurntime = new JLabel("Burn time");
		labelBurntime.setToolTipText(burntimeTooltip);
		labelPanel.add(labelBurntime);
		
		burntimeSpinner = new JSpinner();
		burntimeSpinner.setToolTipText(burntimeTooltip);
		burntimeSpinner.addChangeListener(changeListener);
		burntimeSpinner.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		interactionPanel.add(burntimeSpinner);
		
		labelHarvestType = new JLabel("Harvest type");
		labelHarvestType.setToolTipText(harvestTypeTooltip);
		labelPanel.add(labelHarvestType);
		
		harvestTypeComboBox = new JComboBox();
		harvestTypeComboBox.setToolTipText(harvestTypeTooltip);
		harvestTypeComboBox.addActionListener(actionListener);
		harvestTypeComboBox.setModel(new DefaultComboBoxModel(new String[] {"none", "pickaxe", "shovel", "axe"}));
		interactionPanel.add(harvestTypeComboBox);
		
		labelMaterial = new JLabel("Material");
		labelMaterial.setToolTipText(materialTooltip);
		labelPanel.add(labelMaterial);
		
		materialComboBox = new JComboBox();
		materialComboBox.setToolTipText(materialTooltip);
		materialComboBox.addActionListener(actionListener);
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
		unbreakableCheckBox.addActionListener(actionListener);
		interactionPanel.add(unbreakableCheckBox);
		unbreakableCheckBox.setHorizontalAlignment(SwingConstants.RIGHT);
		
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		
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
	
	public BlockEditor(BlockElement block, ObjectRunnable runnable, ObjectRunnable closeHandler){
		this(block.name, runnable, closeHandler);
		
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
	
	private void cancel(){
		for (WindowListener listener : getWindowListeners()){
			listener.windowClosing(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		}
	}
	
	@Override
	public void dispose() {
		closeHandler.run(name);
		super.dispose();
	}
}

