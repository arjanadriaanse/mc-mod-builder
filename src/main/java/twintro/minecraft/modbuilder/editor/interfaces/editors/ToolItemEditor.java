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
import twintro.minecraft.modbuilder.editor.interfaces.choosewindows.MaterialChooseWindow;
import twintro.minecraft.modbuilder.editor.interfaces.choosewindows.ObjectRunnable;
import twintro.minecraft.modbuilder.editor.resources.ItemElement;
import twintro.minecraft.modbuilder.editor.resources.MaterialResources;

public class ToolItemEditor extends RegularItemEditor {
	private JPanel affectedBlocksPanel;
	private JPanel affectedBlocksSubPanel;
	private JPanel repairMaterialPanel;
	private JPanel repairMaterialSubPanel;
	private JLabel labelDurability;
	private JLabel labelEfficiency;
	private JLabel labelDamage;
	private JLabel labelHarvestLevel;
	private JLabel labelEnchantibility;
	private JLabel labelAffectedBlocks;
	private JLabel labelRepairMaterial;
	private JLabel affectedBlocksLabel;
	private JLabel repairMaterialLabel;
	private JButton affectedBlocksButton;
	private JButton affectedBlocksResetButton;
	private JButton repairMaterialChooseButton;
	private JSpinner durabilitySpinner;
	private JSpinner efficiencySpinner;
	private JSpinner damageSpinner;
	private JSpinner harvestLevelSpinner;
	private JSpinner enchantibilitySpinner;
	private JCheckBox repairMaterialCheckbox;

	private static final String durabilityTooltip = "The amount of blocks that the tool can mine until the tool breaks";
	private static final String efficiencyTooltip = "<html>The speed at which the tool mines<br>"
			+ "Wood tools have efficiency 2, stone 4, iron 6, diamond 8</html>";
	private static final String damageTooltip = "<html>The amount of health points of damage the tool deals when used as a weapon<br>"
			+ "One heart of health is two health points</html>";
	private static final String harvestLevelTooltip = "Idk what this does"; //TODO
	private static final String enchantibilityTooltip = "Idk what this does"; //TODO
	private static final String affectedBlocksTooltip = "The blocks that are mined faster with the tool";
	private static final String repairMaterialTooltip = "The material that is required to repair the tool in an anvil";

	public ToolItemEditor(String name, ObjectRunnable runnable, ObjectRunnable closeHandler) {
		super(name, runnable, closeHandler);
		setTitle("Edit Tool: " + this.name);
		
		saveButton.setText("Save Tool");
		
		labelDurability = new JLabel("Durability");
		labelDurability.setToolTipText(durabilityTooltip);
		labelPanel.add(labelDurability);
		
		durabilitySpinner = new JSpinner();
		durabilitySpinner.setToolTipText(durabilityTooltip);
		durabilitySpinner.addChangeListener(changeListener);
		durabilitySpinner.setModel(new SpinnerNumberModel(new Integer(128), new Integer(1), null, new Integer(1)));
		interactionPanel.add(durabilitySpinner);
		
		labelEfficiency = new JLabel("Efficiency");
		labelEfficiency.setToolTipText(efficiencyTooltip);
		labelPanel.add(labelEfficiency);
		
		efficiencySpinner = new JSpinner();
		efficiencySpinner.setToolTipText(efficiencyTooltip);
		efficiencySpinner.addChangeListener(changeListener);
		efficiencySpinner.setModel(new SpinnerNumberModel(new Float(2), new Float(2), null, new Float(2)));
		interactionPanel.add(efficiencySpinner);
		
		labelDamage = new JLabel("Damage");
		labelDamage.setToolTipText(damageTooltip);
		labelPanel.add(labelDamage);
		
		damageSpinner = new JSpinner();
		damageSpinner.setToolTipText(damageTooltip);
		damageSpinner.addChangeListener(changeListener);
		damageSpinner.setModel(new SpinnerNumberModel(new Float(2), new Float(1), null, new Float(1)));
		interactionPanel.add(damageSpinner);
		
		labelHarvestLevel = new JLabel("Harvest Level");
		labelHarvestLevel.setToolTipText(harvestLevelTooltip);
		labelPanel.add(labelHarvestLevel);
		
		harvestLevelSpinner = new JSpinner();
		harvestLevelSpinner.setToolTipText(harvestLevelTooltip);
		harvestLevelSpinner.addChangeListener(changeListener);
		harvestLevelSpinner.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		interactionPanel.add(harvestLevelSpinner);
		
		labelEnchantibility = new JLabel("Enchantibility");
		labelEnchantibility.setToolTipText(enchantibilityTooltip);
		labelPanel.add(labelEnchantibility);
		
		enchantibilitySpinner = new JSpinner();
		enchantibilitySpinner.setToolTipText(enchantibilityTooltip);
		enchantibilitySpinner.addChangeListener(changeListener);
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
		repairMaterialCheckbox.addActionListener(actionListener);
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
	
	public ToolItemEditor(ItemElement item, ObjectRunnable runnable, ObjectRunnable closeHandler) {
		this(item.name, runnable, closeHandler);
		regularSetup(item);
		toolSetup(item);

		changed = false;
	}
	
	private void toolSetup(ItemElement item){
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
	
	@Override
	public boolean save() {
		if (textureLabel.getText().length() > 0){
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
				if (MaterialResources.isItem(repairMaterialLabel.getText()))
					base.repairitem = repairMaterialLabel.getText();
				else
					base.repairblock = repairMaterialLabel.getText();
			}
			
			ItemElement item = writeItem(base);
			runnable.run(item);
			dispose();
		}
		else{
			int selected = JOptionPane.showConfirmDialog(this, "You haven't given the item a texture yet.", 
					"Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
			if (selected == JOptionPane.OK_OPTION)
				return false;
		}
		return true;
	}
	
	protected void addBlock(){
		new MaterialChooseWindow(MaterialChooseWindow.BLOCKS_ONLY, new ObjectRunnable() {
			@Override
			public void run(Object obj) {
				change();
				if (affectedBlocksLabel.getText().length() > 0) affectedBlocksLabel.setText(affectedBlocksLabel.getText() + ",");
				affectedBlocksLabel.setText(affectedBlocksLabel.getText() + (String) obj);
			}
		});
	}
	
	protected void resetBlocks(){
		change();
		affectedBlocksLabel.setText("");
	}
	
	protected void repairMaterialUse(){
		boolean use = repairMaterialCheckbox.isSelected();
		repairMaterialChooseButton.setEnabled(use);
		repairMaterialLabel.setEnabled(use);
		labelRepairMaterial.setEnabled(use);
	}
	
	protected void repairMaterialChoose(){
		new MaterialChooseWindow(MaterialChooseWindow.ITEMS_AND_BLOCKS, new ObjectRunnable() {
			@Override
			public void run(Object obj) {
				change();
				repairMaterialLabel.setText((String) obj);
			}
		});
	}
}
