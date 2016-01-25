package twintro.minecraft.modbuilder.editor.interfaces.helperclasses;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import twintro.minecraft.modbuilder.editor.interfaces.choosewindows.ObjectRunnable;
import twintro.minecraft.modbuilder.editor.interfaces.editors.FoodItemEditor;
import twintro.minecraft.modbuilder.editor.interfaces.editors.PropertiesEditor;

public class EffectPanel extends JPanel {
	private JComboBox effectComboBox;
	private JSpinner durationSpinner;
	private JSpinner amplifierSpinner;
	private JButton removeButton;
	
	private ObjectRunnable runnable;
	private int id;
	
	public EffectPanel(ObjectRunnable runnable, PropertiesEditor main){
		this.runnable = runnable;
		setLayout(new GridLayout(0, 4, 5, 0));
		
		effectComboBox = main.combobox(FoodItemEditor.effectTypeTooltip, this);
		effectComboBox.setModel(new DefaultComboBoxModel(new String[] {"Effect", "Speed", "Slowness", "Haste", "Mining Fatigue", "Instant Health",
				"Instant Damage", "Jump Boost", "Nausea", "Regeneration", "Resistance", "Fire Resistance", "Water Breathing", "Invisibility",
				"Blindness", "Night Vision", "Hunger", "Weakness", "Poison", "Wither", "Health Boost", "Absorption", "Saturation"}));
		
		durationSpinner = main.spinner(FoodItemEditor.effectDurationTooltip, this);
		durationSpinner.setModel(new SpinnerNumberModel(new Integer(20), new Integer(0), null, new Integer(20)));
		
		amplifierSpinner = main.spinner(FoodItemEditor.effectAmplifierTooltip, this);
		amplifierSpinner.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		
		removeButton = main.button("Remove", FoodItemEditor.removeEffectTooltip, this);
		removeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}
	
	public EffectPanel(ObjectRunnable runnable, PropertiesEditor main, Integer[] effect){
		this(runnable, main);
		
		if (effect.length == 3){
			effectComboBox.setSelectedIndex(effect[0]);
			durationSpinner.setValue(effect[1]);
			amplifierSpinner.setValue(effect[2]);
		}
	}
	
	public boolean isProperEffect(){
		return effectComboBox.getSelectedIndex() > 0;
	}
	
	public Integer[] getEffect(){
		return new Integer[]{effectComboBox.getSelectedIndex(), (Integer) durationSpinner.getValue(), (Integer) amplifierSpinner.getValue()};
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	private void dispose(){
		runnable.run(id);
	}
}
