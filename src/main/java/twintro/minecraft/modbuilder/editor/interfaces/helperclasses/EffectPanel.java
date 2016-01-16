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

public class EffectPanel extends JPanel {
	private JComboBox effectComboBox;
	private JSpinner durationSpinner;
	private JSpinner amplifierSpinner;
	private JButton removeButton;
	
	private ObjectRunnable runnable;
	private int id;
	
	public EffectPanel(ObjectRunnable runnable, WindowClosingVerifierUser main){
		this.runnable = runnable;
		
		setLayout(new GridLayout(0, 4, 5, 0));
		
		effectComboBox = new JComboBox();
		effectComboBox.setToolTipText(FoodItemEditor.effectTypeTooltip);
		effectComboBox.addActionListener(main.actionListener);
		effectComboBox.setModel(new DefaultComboBoxModel(new String[] {"Effect", "Speed", "Slowness", "Haste", "Mining Fatigue", "Instant Health",
				"Instant Damage", "Jump Boost", "Nausea", "Regeneration", "Resistance", "Fire Resistance", "Water Breathing", "Invisibility",
				"Blindness", "Night Vision", "Hunger", "Weakness", "Poison", "Wither", "Health Boost", "Absorption", "Saturation"}));
		add(effectComboBox);
		
		durationSpinner = new JSpinner();
		durationSpinner.setToolTipText(FoodItemEditor.effectDurationTooltip);
		durationSpinner.addChangeListener(main.changeListener);
		durationSpinner.setModel(new SpinnerNumberModel(new Integer(20), new Integer(0), null, new Integer(20)));
		add(durationSpinner);
		
		amplifierSpinner = new JSpinner();
		amplifierSpinner.setToolTipText(FoodItemEditor.effectAmplifierTooltip);
		amplifierSpinner.addChangeListener(main.changeListener);
		amplifierSpinner.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		add(amplifierSpinner);
		
		removeButton = new JButton("Remove");
		removeButton.setToolTipText(FoodItemEditor.removeEffectTooltip);
		removeButton.addActionListener(main.actionListener);
		removeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		add(removeButton);
	}
	
	public EffectPanel(ObjectRunnable runnable, WindowClosingVerifierUser main, Integer[] effect){
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
