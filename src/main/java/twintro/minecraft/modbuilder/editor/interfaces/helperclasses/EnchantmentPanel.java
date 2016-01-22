package twintro.minecraft.modbuilder.editor.interfaces.helperclasses;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.AbstractMap.SimpleEntry;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import twintro.minecraft.modbuilder.editor.interfaces.choosewindows.ItemStackChooseWindow;
import twintro.minecraft.modbuilder.editor.interfaces.choosewindows.ObjectRunnable;
import twintro.minecraft.modbuilder.editor.interfaces.editors.FoodItemEditor;
import twintro.minecraft.modbuilder.editor.interfaces.editors.PropertiesEditor;

public class EnchantmentPanel extends JPanel {
	private JComboBox enchantmentComboBox;
	private JSpinner amplifierSpinner;
	private JButton removeButton;
	
	private ObjectRunnable runnable;
	private int id;
	
	public EnchantmentPanel(ObjectRunnable runnable){
		this.runnable = runnable;
		setLayout(new GridLayout(0, 3, 5, 0));
		
		enchantmentComboBox = new JComboBox();
		enchantmentComboBox.setToolTipText(ItemStackChooseWindow.enchantmentTypeTooltip);
		add(enchantmentComboBox);
		enchantmentComboBox.setModel(new DefaultComboBoxModel(new String[] {"Enchantment", "Protection", "Fire protection", "Feather falling",
				"Blast protection", "Projectile protection", "Respiration", "Aqua infinity", "Thorns", "Depth strider", "Sharpness", "Smite",
				"Bane of arthropods", "Knockback", "Fire aspect", "Looting", "Efficiency", "Silk touch", "Unbreaking", "Fortune", "Power", "Punch",
				"Flame", "Infinity", "Luck of the sea"}));
		
		amplifierSpinner = new JSpinner();
		amplifierSpinner.setToolTipText(ItemStackChooseWindow.amplifierTooltip);
		add(amplifierSpinner);
		amplifierSpinner.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		
		removeButton = new JButton("Remove");
		removeButton.setToolTipText(ItemStackChooseWindow.removeEnchantmentTooltip);
		add(removeButton);
		removeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}
	
	public EnchantmentPanel(ObjectRunnable runnable, SimpleEntry<String, Integer> enchantment){
		this(runnable);
		
		enchantmentComboBox.setSelectedItem(
				enchantment.getKey().substring(0, 1).toUpperCase() + enchantment.getKey().substring(1).replaceAll("_", " "));
		amplifierSpinner.setValue(enchantment.getValue());
	}
	
	public boolean isProperEnchantment(){
		return enchantmentComboBox.getSelectedIndex() > 0;
	}
	
	public SimpleEntry<String, Integer> getEnchantment(){
		return new SimpleEntry(((String) enchantmentComboBox.getSelectedItem()).toLowerCase().replaceAll(" ", "_"), 
				(Integer) amplifierSpinner.getValue());
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	private void dispose(){
		runnable.run(id);
	}
}
