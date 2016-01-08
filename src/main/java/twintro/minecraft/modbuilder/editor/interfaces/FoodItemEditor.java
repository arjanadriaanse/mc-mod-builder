package twintro.minecraft.modbuilder.editor.interfaces;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.HashSet;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import twintro.minecraft.modbuilder.data.resources.items.FoodItemResource;
import twintro.minecraft.modbuilder.editor.resources.ItemElement;

public class FoodItemEditor extends RegularItemEditor {
	protected JPanel propertiesPanel;
	protected JPanel feedToWolvesPanel;
	protected JPanel alwaysEdiblePanel;
	protected JLabel labelHungerRefill;
	protected JLabel labelSaturation;
	protected JLabel labelProperties;
	protected JSpinner hungerRefillSpinner;
	protected JSpinner saturationSpinner;
	protected JCheckBox feedToWolvesCheckbox;
	protected JCheckBox alwaysEdibleCheckbox;
	
	public FoodItemEditor(String name, ItemsActivityPanel itemsActivityPanel) {
		super(name, itemsActivityPanel);
		setTitle("Edit Food: " + this.name);
		
		//TODO tooltips for some things
		
		labelHungerRefill = new JLabel("Hunger Refill");
		labelPanel.add(labelHungerRefill);
		
		hungerRefillSpinner = new JSpinner();
		hungerRefillSpinner.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		interactionPanel.add(hungerRefillSpinner);
		
		labelSaturation = new JLabel("Saturation");
		labelPanel.add(labelSaturation);
		
		saturationSpinner = new JSpinner();
		saturationSpinner.setModel(new SpinnerNumberModel(new Float(0), new Float(0), null, new Float(1)));
		interactionPanel.add(saturationSpinner);
		
		labelProperties = new JLabel("Properties");
		labelPanel.add(labelProperties);
		
		propertiesPanel = new JPanel();
		propertiesPanel.setLayout(new GridLayout(0, 2, 0, 0));
		interactionPanel.add(propertiesPanel);
		
		feedToWolvesPanel = new JPanel();
		feedToWolvesPanel.setLayout(new BorderLayout(0, 0));
		propertiesPanel.add(feedToWolvesPanel);
		
		feedToWolvesCheckbox = new JCheckBox("Feed to wolves");
		feedToWolvesPanel.add(feedToWolvesCheckbox);
		
		alwaysEdiblePanel = new JPanel();
		alwaysEdiblePanel.setLayout(new BorderLayout(0, 0));
		propertiesPanel.add(alwaysEdiblePanel);
		
		alwaysEdibleCheckbox = new JCheckBox("Always edible");
		alwaysEdiblePanel.add(alwaysEdibleCheckbox);
		
		//TODO effects
	}
	
	public FoodItemEditor(ItemsActivityPanel main, ItemElement item) {
		this(item.name, main);
		regularSetup(main, item);
		foodSetup(item);
	}
	
	protected void foodSetup(ItemElement item){
		FoodItemResource resource = (FoodItemResource) item.item;
		
		hungerRefillSpinner.setValue(resource.amount);
		if (resource.saturation != null)
			saturationSpinner.setValue(resource.saturation);
		if (resource.wolf != null)
			feedToWolvesCheckbox.setSelected(resource.wolf);
		if (resource.alwaysedible != null)
			alwaysEdibleCheckbox.setSelected(resource.alwaysedible);
		
		//TODO effects
	}

	protected void save() {
		if (!textureChooserIsOpen && textureLabel.getText().length() > 0){
			FoodItemResource base = new FoodItemResource();
			base.amount = (Integer) hungerRefillSpinner.getValue();
			base.saturation = (Float) saturationSpinner.getValue();
			base.wolf = feedToWolvesCheckbox.isSelected();
			base.alwaysedible = alwaysEdibleCheckbox.isSelected();
			
			//TODO base.effects
			
			ItemElement item = writeItem(base);
			main.addItem(item);
		}
	}
}
