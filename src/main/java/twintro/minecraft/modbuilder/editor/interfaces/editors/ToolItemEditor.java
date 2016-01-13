package twintro.minecraft.modbuilder.editor.interfaces.editors;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import twintro.minecraft.modbuilder.data.resources.TabResource;
import twintro.minecraft.modbuilder.data.resources.items.ItemResource;
import twintro.minecraft.modbuilder.data.resources.items.ToolItemResource;
import twintro.minecraft.modbuilder.editor.interfaces.activitypanels.ItemsActivityPanel;
import twintro.minecraft.modbuilder.editor.interfaces.helperclasses.MaterialChooseWindow;
import twintro.minecraft.modbuilder.editor.interfaces.helperclasses.MaterialRunnable;
import twintro.minecraft.modbuilder.editor.resources.ItemElement;
import twintro.minecraft.modbuilder.editor.resources.VanillaElements;

public class ToolItemEditor extends RegularItemEditor {
	protected JPanel affectedBlocksPanel;
	protected JPanel affectedBlocksSubPanel;
	protected JPanel repairMaterialPanel;
	protected JPanel repairMaterialSubPanel;
	protected JLabel labelDurability;
	protected JLabel labelEfficiency;
	protected JLabel labelDamage;
	protected JLabel labelHarvestLevel;
	protected JLabel labelEnchantibility;
	protected JLabel labelAffectedBlocks;
	protected JLabel labelRepairMaterial;
	protected JLabel affectedBlocksLabel;
	protected JLabel repairMaterialLabel;
	protected JButton affectedBlocksButton;
	protected JButton affectedBlocksResetButton;
	protected JButton repairMaterialChooseButton;
	protected JSpinner durabilitySpinner;
	protected JSpinner efficiencySpinner;
	protected JSpinner damageSpinner;
	protected JSpinner harvestLevelSpinner;
	protected JSpinner enchantibilitySpinner;
	protected JCheckBox repairMaterialCheckbox;

	private static final String durabilityTooltip = "The amount of blocks that the tool can mine until the tool breaks";
	private static final String efficiencyTooltip = "<html>The speed at which the tool mines<br>"
			+ "Wood tools have efficiency 2, stone 4, iron 6, diamond 8</html>";
	private static final String damageTooltip = "<html>The amount of health points of damage the tool deals when used as a weapon<br>"
			+ "One heart of health is two health points</html>";
	private static final String harvestLevelTooltip = "Idk what this does"; //TODO
	private static final String enchantibilityTooltip = "Idk what this does"; //TODO
	private static final String affectedBlocksTooltip = "The blocks that are mined faster with the tool";
	private static final String repairMaterialTooltip = "The material that is required to repair the tool in an anvil";

	public ToolItemEditor(String name, ItemsActivityPanel main) {
		super(name, main);
		setTitle("Edit Tool: " + this.name);
		
		saveButton.setText("Save Tool");
		
		labelDurability = new JLabel("Durability");
		labelDurability.setToolTipText(durabilityTooltip);
		labelPanel.add(labelDurability);
		
		durabilitySpinner = new JSpinner();
		durabilitySpinner.setToolTipText(durabilityTooltip);
		durabilitySpinner.setModel(new SpinnerNumberModel(new Integer(128), new Integer(1), null, new Integer(1)));
		interactionPanel.add(durabilitySpinner);
		
		labelEfficiency = new JLabel("Efficiency");
		labelEfficiency.setToolTipText(efficiencyTooltip);
		labelPanel.add(labelEfficiency);
		
		efficiencySpinner = new JSpinner();
		efficiencySpinner.setToolTipText(efficiencyTooltip);
		efficiencySpinner.setModel(new SpinnerNumberModel(new Float(2), new Float(2), null, new Float(2)));
		interactionPanel.add(efficiencySpinner);
		
		labelDamage = new JLabel("Damage");
		labelDamage.setToolTipText(damageTooltip);
		labelPanel.add(labelDamage);
		
		damageSpinner = new JSpinner();
		damageSpinner.setToolTipText(damageTooltip);
		damageSpinner.setModel(new SpinnerNumberModel(new Float(2), new Float(1), null, new Float(1)));
		interactionPanel.add(damageSpinner);
		
		labelHarvestLevel = new JLabel("Harvest Level");
		labelHarvestLevel.setToolTipText(harvestLevelTooltip);
		labelPanel.add(labelHarvestLevel);
		
		harvestLevelSpinner = new JSpinner();
		harvestLevelSpinner.setToolTipText(harvestLevelTooltip);
		harvestLevelSpinner.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		interactionPanel.add(harvestLevelSpinner);
		
		labelEnchantibility = new JLabel("Enchantibility");
		labelEnchantibility.setToolTipText(enchantibilityTooltip);
		labelPanel.add(labelEnchantibility);
		
		enchantibilitySpinner = new JSpinner();
		enchantibilitySpinner.setToolTipText(enchantibilityTooltip);
		enchantibilitySpinner.setModel(new SpinnerNumberModel(new Integer(10), new Integer(1), null, new Integer(1)));
		interactionPanel.add(enchantibilitySpinner);
		
		labelAffectedBlocks = new JLabel("Affected Blocks");
		labelAffectedBlocks.setToolTipText(affectedBlocksTooltip);
		labelPanel.add(labelAffectedBlocks);
		
		affectedBlocksPanel = new JPanel();
		interactionPanel.add(affectedBlocksPanel);
		affectedBlocksPanel.setLayout(new BorderLayout(0, 0));
		
		affectedBlocksButton = new JButton("Add Block");
		affectedBlocksButton.setToolTipText(affectedBlocksTooltip);
		affectedBlocksButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addBlock();
			}
		});
		affectedBlocksPanel.add(affectedBlocksButton, BorderLayout.EAST);
		
		affectedBlocksSubPanel = new JPanel();
		affectedBlocksPanel.add(affectedBlocksSubPanel, BorderLayout.CENTER);
		affectedBlocksSubPanel.setLayout(new BorderLayout(0, 0));
		
		affectedBlocksResetButton = new JButton("Reset");
		affectedBlocksResetButton.setToolTipText(affectedBlocksTooltip);
		affectedBlocksResetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetBlocks();
			}
		});
		affectedBlocksSubPanel.add(affectedBlocksResetButton, BorderLayout.EAST);
		
		affectedBlocksLabel = new JLabel("");
		affectedBlocksLabel.setToolTipText(affectedBlocksTooltip);
		affectedBlocksSubPanel.add(affectedBlocksLabel, BorderLayout.CENTER);
		
		labelRepairMaterial = new JLabel("Repair Material");
		labelRepairMaterial.setToolTipText(repairMaterialTooltip);
		labelRepairMaterial.setEnabled(false);
		labelPanel.add(labelRepairMaterial);
		
		repairMaterialPanel = new JPanel();
		interactionPanel.add(repairMaterialPanel);
		repairMaterialPanel.setLayout(new BorderLayout(0, 0));
		
		repairMaterialCheckbox = new JCheckBox("Use");
		repairMaterialCheckbox.setToolTipText(repairMaterialTooltip);
		repairMaterialCheckbox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				repairMaterialUse();
			}
		});
		repairMaterialPanel.add(repairMaterialCheckbox, BorderLayout.EAST);
		
		repairMaterialSubPanel = new JPanel();
		repairMaterialPanel.add(repairMaterialSubPanel, BorderLayout.CENTER);
		repairMaterialSubPanel.setLayout(new BorderLayout(0, 0));
		
		repairMaterialChooseButton = new JButton("Choose");
		repairMaterialChooseButton.setToolTipText(repairMaterialTooltip);
		repairMaterialChooseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				repairMaterialChoose();
			}
		});
		repairMaterialChooseButton.setEnabled(false);
		repairMaterialSubPanel.add(repairMaterialChooseButton, BorderLayout.EAST);
		
		repairMaterialLabel = new JLabel("");
		repairMaterialLabel.setToolTipText(repairMaterialTooltip);
		repairMaterialLabel.setEnabled(false);
		repairMaterialSubPanel.add(repairMaterialLabel, BorderLayout.CENTER);
	}
	
	public ToolItemEditor(ItemsActivityPanel main, ItemElement item) {
		this(item.name, main);
		regularSetup(main, item);
		toolSetup(item);
	}
	
	protected void toolSetup(ItemElement item){
		ToolItemResource resource = (ToolItemResource) item.item;
		
		if (resource.durability != null)
			durabilitySpinner.setValue(resource.durability);
		if (resource.efficiency != null)
			efficiencySpinner.setValue(resource.efficiency);
		if (resource.damage != null)
			damageSpinner.setValue(resource.damage);
		if (resource.harvestlevel != null)
			harvestLevelSpinner.setValue(resource.harvestlevel);
		if (resource.enchantability != null)
			enchantibilitySpinner.setValue(resource.enchantability);
		if (resource.blocks != null){
			for (String block : resource.blocks){
				if (affectedBlocksLabel.getText().length() > 0) affectedBlocksLabel.setText(affectedBlocksLabel.getText() + ",");
				affectedBlocksLabel.setText(affectedBlocksLabel.getText() + block);
			}
		}
		if (resource.repairblock != null){
			repairMaterialLabel.setText(resource.repairblock);
			repairMaterialCheckbox.setSelected(true);
			repairMaterialUse();
		}
		else if (resource.repairitem != null){
			repairMaterialLabel.setText(resource.repairitem);
			repairMaterialCheckbox.setSelected(true);
			repairMaterialUse();
		}
	}

	protected void save() {
		if (!textureChooserIsOpen && textureLabel.getText().length() > 0){
			ToolItemResource base = new ToolItemResource();
			base.durability = (Integer) durabilitySpinner.getValue();
			base.efficiency = (Float) efficiencySpinner.getValue();
			base.damage = (Float) damageSpinner.getValue();
			base.harvestlevel = (Integer) harvestLevelSpinner.getValue();
			base.enchantability = (Integer) enchantibilitySpinner.getValue();
			base.blocks = new HashSet<String>();
			if (affectedBlocksLabel.getText().length() > 0)
				for (String s : affectedBlocksLabel.getText().split(","))
					base.blocks.add(s);
			if (repairMaterialCheckbox.isSelected()){
				if (VanillaElements.isItem(repairMaterialLabel.getText()) || 
						main.main.ItemPanel.elements.keySet().contains(repairMaterialLabel.getText()))
					base.repairitem = repairMaterialLabel.getText();
				else
					base.repairblock = repairMaterialLabel.getText();
			}
			
			ItemElement item = writeItem(base);
			main.addItem(item);
		}
		else{
			JOptionPane.showMessageDialog(this, "Not all required properties are given a value yet.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	protected void addBlock(){
		if (!materialChooserIsOpen){
			new MaterialChooseWindow(MaterialChooseWindow.BLOCKS_ONLY, main.main, new MaterialRunnable() {
				@Override
				public void chooseMaterial(String material) {
					if (affectedBlocksLabel.getText().length() > 0) affectedBlocksLabel.setText(affectedBlocksLabel.getText() + ",");
					affectedBlocksLabel.setText(affectedBlocksLabel.getText() + material);
				}

				@Override
				public void materialChooserDispose() {
					materialChooserIsOpen = false;
				}
			});
			materialChooserIsOpen = true;
		}
	}
	
	protected void resetBlocks(){
		affectedBlocksLabel.setText("");
	}
	
	protected void repairMaterialUse(){
		boolean use = repairMaterialCheckbox.isSelected();
		repairMaterialChooseButton.setEnabled(use);
		repairMaterialLabel.setEnabled(use);
		labelRepairMaterial.setEnabled(use);
	}
	
	protected void repairMaterialChoose(){
		if (!materialChooserIsOpen){
			new MaterialChooseWindow(MaterialChooseWindow.ITEMS_AND_BLOCKS, main.main, new MaterialRunnable() {
				@Override
				public void chooseMaterial(String material) {
					repairMaterialLabel.setText(material);
				}

				@Override
				public void materialChooserDispose() {
					materialChooserIsOpen = false;
				}
			});
			materialChooserIsOpen = true;
		}
	}
}
