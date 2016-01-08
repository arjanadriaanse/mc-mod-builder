package twintro.minecraft.modbuilder.editor.interfaces;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import twintro.minecraft.modbuilder.data.resources.TabResource;
import twintro.minecraft.modbuilder.data.resources.items.ItemResource;
import twintro.minecraft.modbuilder.data.resources.items.ToolItemResource;
import twintro.minecraft.modbuilder.editor.resources.ItemElement;

public class ToolItemEditor extends RegularItemEditor {
	protected JPanel affectedBlocksPanel;
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
	protected JButton repairMaterialChooseButton;
	protected JSpinner durabilitySpinner;
	protected JSpinner efficiencySpinner;
	protected JSpinner damageSpinner;
	protected JSpinner harvestLevelSpinner;
	protected JSpinner enchantibilitySpinner;
	protected JCheckBox repairMaterialCheckbox;

	public ToolItemEditor(String name, ItemsActivityPanel main) {
		super(name, main);
		setTitle("Edit Tool: " + this.name);
		
		labelDurability = new JLabel("Durability");
		labelDurability.setToolTipText("The amount of blocks that the tool can mine until the tool breaks");
		labelPanel.add(labelDurability);
		
		durabilitySpinner = new JSpinner();
		durabilitySpinner.setToolTipText("The amount of blocks that the tool can mine until the tool breaks");
		durabilitySpinner.setModel(new SpinnerNumberModel(new Integer(128), new Integer(1), null, new Integer(1)));
		interactionPanel.add(durabilitySpinner);
		
		labelEfficiency = new JLabel("Efficiency");
		labelEfficiency.setToolTipText("<html>The speed at which the tool mines<br>"
				+ "Wood tools have efficiency 2, stone 4, iron 6, diamond 8</html>");
		labelPanel.add(labelEfficiency);
		
		efficiencySpinner = new JSpinner();
		efficiencySpinner.setToolTipText("<html>The speed at which the tool mines<br>"
				+ "Wood tools have efficiency 2, stone 4, iron 6, diamond 8</html>");
		efficiencySpinner.setModel(new SpinnerNumberModel(new Float(2), new Float(2), null, new Float(2)));
		interactionPanel.add(efficiencySpinner);
		
		labelDamage = new JLabel("Damage");
		labelDamage.setToolTipText("<html>The amount of health points of damage the tool deals when used as a weapon<br>"
				+ "One heart of health is two health points</html>");
		labelPanel.add(labelDamage);
		
		damageSpinner = new JSpinner();
		damageSpinner.setToolTipText("<html>The amount of health points of damage the tool deals when used as a weapon<br>"
				+ "One heart of health is two health points</html>");
		damageSpinner.setModel(new SpinnerNumberModel(new Float(2), new Float(1), null, new Float(1)));
		interactionPanel.add(damageSpinner);
		
		labelHarvestLevel = new JLabel("Harvest Level");
		labelHarvestLevel.setToolTipText("Idk what this does");//TODO
		labelPanel.add(labelHarvestLevel);
		
		harvestLevelSpinner = new JSpinner();
		harvestLevelSpinner.setToolTipText("Idk what this does");//TODO
		harvestLevelSpinner.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		interactionPanel.add(harvestLevelSpinner);
		
		labelEnchantibility = new JLabel("Enchantibility");
		labelEnchantibility.setToolTipText("Idk what this does");//TODO
		labelPanel.add(labelEnchantibility);
		
		enchantibilitySpinner = new JSpinner();
		enchantibilitySpinner.setToolTipText("Idk what this does");//TODO
		enchantibilitySpinner.setModel(new SpinnerNumberModel(new Integer(10), new Integer(1), null, new Integer(1)));
		interactionPanel.add(enchantibilitySpinner);
		
		labelAffectedBlocks = new JLabel("Affected Blocks");
		labelAffectedBlocks.setToolTipText("The blocks that are mined faster with the tool");
		labelPanel.add(labelAffectedBlocks);
		
		affectedBlocksPanel = new JPanel();
		interactionPanel.add(affectedBlocksPanel);
		affectedBlocksPanel.setLayout(new BorderLayout(0, 0));
		
		affectedBlocksButton = new JButton("Add Block");
		affectedBlocksButton.setToolTipText("The blocks that are mined faster with the tool");
		affectedBlocksButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addBlock();
			}
		});
		affectedBlocksPanel.add(affectedBlocksButton, BorderLayout.EAST);
		
		affectedBlocksLabel = new JLabel("");
		affectedBlocksPanel.setToolTipText("The blocks that are mined faster with the tool");
		affectedBlocksPanel.add(affectedBlocksLabel, BorderLayout.CENTER);
		
		labelRepairMaterial = new JLabel("Repair Material");
		labelRepairMaterial.setToolTipText("The material that is required to repair the tool in an anvil");
		labelRepairMaterial.setEnabled(false);
		labelPanel.add(labelRepairMaterial);
		
		repairMaterialPanel = new JPanel();
		interactionPanel.add(repairMaterialPanel);
		repairMaterialPanel.setLayout(new BorderLayout(0, 0));
		
		repairMaterialCheckbox = new JCheckBox("Use");
		repairMaterialCheckbox.setToolTipText("The material that is required to repair the tool in an anvil");
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
		repairMaterialChooseButton.setToolTipText("The material that is required to repair the tool in an anvil");
		repairMaterialChooseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				repairMaterialChoose();
			}
		});
		repairMaterialChooseButton.setEnabled(false);
		repairMaterialSubPanel.add(repairMaterialChooseButton, BorderLayout.EAST);
		
		repairMaterialLabel = new JLabel("");
		repairMaterialLabel.setToolTipText("The material that is required to repair the tool in an anvil");
		repairMaterialLabel.setEnabled(false);
		containerSubPanel.add(repairMaterialLabel, BorderLayout.CENTER);
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
			//TODO read resource.blocks
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
				//TODO base.repairblock of base.repairitem = repairMaterialLabel.getText();
			}
			
			ItemElement item = writeItem(base);
			main.addItem(item);
		}
	}
	
	protected void addBlock(){
		//TODO material list
	}
	
	protected void repairMaterialUse(){
		boolean use = repairMaterialCheckbox.isSelected();
		repairMaterialChooseButton.setEnabled(use);
		repairMaterialLabel.setEnabled(use);
		labelRepairMaterial.setEnabled(use);
	}
	
	protected void repairMaterialChoose(){
		//TODO material list
	}
}
