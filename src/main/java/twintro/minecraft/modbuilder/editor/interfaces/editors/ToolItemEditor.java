package twintro.minecraft.modbuilder.editor.interfaces.editors;

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

import twintro.minecraft.modbuilder.data.resources.items.ToolItemResource;
import twintro.minecraft.modbuilder.editor.interfaces.choosewindows.MaterialChooseWindow;
import twintro.minecraft.modbuilder.editor.interfaces.choosewindows.ObjectRunnable;
import twintro.minecraft.modbuilder.editor.interfaces.helperclasses.MaterialLabel;
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
	private JLabel labelEnchantibility;
	private JLabel labelAffectedBlocks;
	private JLabel labelRepairMaterial;
	private JLabel affectedBlocksLabel;
	private MaterialLabel repairMaterialLabel;
	private JButton affectedBlocksButton;
	private JButton affectedBlocksResetButton;
	private JButton repairMaterialChooseButton;
	private JSpinner durabilitySpinner;
	private JSpinner efficiencySpinner;
	private JSpinner damageSpinner;
	private JSpinner enchantibilitySpinner;
	private JCheckBox repairMaterialCheckbox;
	
	private String affectedBlocksString;

	private static final String durabilityTooltip = "The amount of blocks that the tool can mine until it breaks";
	private static final String efficiencyTooltip = "<html>The speed at which the tool mines<br>"
			+ "For example, wooden tools have efficiency 2, stone 4, iron 6 and diamond 8<br>"
			+ "The actual breaking time is calculated using this value and the hardness of the block</html>";
	private static final String damageTooltip = "<html>The amount of damage points the tool deals when used as a weapon<br>"
			+ "One heart of health is two health points</html>";
	private static final String enchantibilityTooltip = "How good enchantments on the tool will be";
	private static final String affectedBlocksTooltip = "The blocks that are mined faster with the tool";
	private static final String repairMaterialTooltip = "The block or item required to repair the tool in an anvil";

	public ToolItemEditor(String name, ObjectRunnable runnable, ObjectRunnable closeHandler) {
		super(name, runnable, closeHandler);
		
		setTitle("Edit Tool: " + this.name);
		saveButton.setText("Save Tool");
		
		creativeTabsComboBox.setSelectedItem("Tools");
		
		labelRepairMaterial = label("Repair Material", repairMaterialTooltip, labelPanel);
		repairMaterialCheckbox = checkbox("Use", repairMaterialTooltip);
		repairMaterialChooseButton = button("Choose", repairMaterialTooltip);
		repairMaterialLabel = materialLabel("", repairMaterialTooltip);
		repairMaterialSubPanel = panel(repairMaterialLabel, repairMaterialChooseButton);
		repairMaterialPanel = panel(repairMaterialSubPanel, repairMaterialCheckbox, interactionPanel);
		repairMaterialCheckbox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				repairMaterialUse();
			}
		});
		repairMaterialChooseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				repairMaterialChoose();
			}
		});
		repairMaterialUse();
		
		labelDurability = label("Durability", durabilityTooltip, labelPanel);
		durabilitySpinner = spinner(durabilityTooltip, interactionPanel);
		durabilitySpinner.setModel(new SpinnerNumberModel(new Integer(250), new Integer(1), null, new Integer(1)));
		
		labelEfficiency = label("Efficiency", efficiencyTooltip, labelPanel);
		efficiencySpinner = spinner(efficiencyTooltip, interactionPanel);
		efficiencySpinner.setModel(new SpinnerNumberModel(new Float(6), new Float(0), null, new Float(1)));
		
		labelDamage = label("Damage", damageTooltip, labelPanel);
		damageSpinner = spinner(damageTooltip, interactionPanel);
		damageSpinner.setModel(new SpinnerNumberModel(new Float(5), new Float(0), null, new Float(1)));
		
		labelEnchantibility = label("Enchantibility", enchantibilityTooltip, labelPanel);
		enchantibilitySpinner = spinner(enchantibilityTooltip, interactionPanel);
		enchantibilitySpinner.setModel(new SpinnerNumberModel(new Integer(10), new Integer(0), null, new Integer(1)));
		
		setSize(400);
	}
	
	public ToolItemEditor(ItemElement item, ObjectRunnable runnable, ObjectRunnable closeHandler) {
		this(item.name, runnable, closeHandler);
		creativeTabsLabel.setText("");
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
		if (resource.enchantability != null)
			enchantibilitySpinner.setValue(resource.enchantability);
		if (resource.blocks != null){
			for (String block : resource.blocks){
				addBlock(block);
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
	protected void addAffectedBlocks() {
		labelAffectedBlocks = label("Affected Blocks", affectedBlocksTooltip, labelPanel);
		affectedBlocksButton = button("Add Block", affectedBlocksTooltip);
		affectedBlocksResetButton = button("Reset", affectedBlocksTooltip);
		affectedBlocksLabel = tooltipLabel("", affectedBlocksTooltip);
		affectedBlocksString = "";
		affectedBlocksSubPanel = panel(affectedBlocksLabel, affectedBlocksResetButton);
		affectedBlocksPanel = panel(affectedBlocksSubPanel, affectedBlocksButton, interactionPanel);
		affectedBlocksButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addBlock();
			}
		});
		affectedBlocksResetButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				resetBlocks();
			}
		});
	}
	
	@Override
	public boolean save() {
		if (textureLabel.getText().length() > 0){
			ToolItemResource base = new ToolItemResource();
			base.durability = (Integer) durabilitySpinner.getValue();
			base.efficiency = (Float) efficiencySpinner.getValue();
			base.damage = (Float) damageSpinner.getValue();
			base.enchantability = (Integer) enchantibilitySpinner.getValue();
			base.blocks = new HashSet<String>();
			if (affectedBlocksString.length() > 0)
				for (String s : affectedBlocksString.split(", "))
					base.blocks.add(s);
			if (repairMaterialCheckbox.isSelected()){
				if (MaterialResources.isItem(repairMaterialLabel.getMaterial()))
					base.repairitem = repairMaterialLabel.getMaterial();
				else
					base.repairblock = repairMaterialLabel.getMaterial();
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
		new MaterialChooseWindow(MaterialChooseWindow.BLOCKS_ONLY_METALESS, new ObjectRunnable() {
			@Override
			public void run(Object obj) {
				change();
				addBlock(((String) obj).replace("#0", ""));
			}
		});
	}
	
	protected void addBlock(String block){
		if (affectedBlocksString.length() > 0) affectedBlocksString += ", ";
		affectedBlocksString += block;
		if (affectedBlocksLabel.getText().length() > 0) affectedBlocksLabel.setText(affectedBlocksLabel.getText() + ", ");
		affectedBlocksLabel.setText(affectedBlocksLabel.getText() + MaterialResources.simplifyItemStackName(block));
	}
	
	protected void resetBlocks(){
		change();
		affectedBlocksLabel.setText("");
		affectedBlocksString = "";
	}
	
	protected void repairMaterialUse(){
		boolean use = repairMaterialCheckbox.isSelected();
		repairMaterialChooseButton.setEnabled(use);
		repairMaterialLabel.setEnabled(use);
		labelRepairMaterial.setEnabled(use);
	}
	
	protected void repairMaterialChoose(){
		new MaterialChooseWindow(MaterialChooseWindow.BLOCKS_ONLY, new ObjectRunnable() {
			@Override
			public void run(Object obj) {
				change();
				repairMaterialLabel.setText((String) obj);
			}
		});
	}
}
