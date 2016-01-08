package twintro.minecraft.modbuilder.editor.interfaces;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;
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
	protected JPanel effectsPanel;
	protected JPanel effectsLabelPanel;
	protected JPanel effectsListPanel;
	protected JPanel effectsListTopPanel;
	protected JLabel labelHungerRefill;
	protected JLabel labelSaturation;
	protected JLabel labelProperties;
	protected JLabel labelEffects;
	protected JLabel labelEffect;
	protected JLabel labelDuration;
	protected JLabel labelAmplifier;
	protected JSpinner hungerRefillSpinner;
	protected JSpinner saturationSpinner;
	protected JCheckBox feedToWolvesCheckbox;
	protected JCheckBox alwaysEdibleCheckbox;
	protected JButton addEffectButton;
	protected EffectPanel[] effectPanels;
	
	public FoodItemEditor(String name, ItemsActivityPanel itemsActivityPanel) {
		super(name, itemsActivityPanel);
		setTitle("Edit Food: " + this.name);
		
		labelHungerRefill = new JLabel("Hunger Refill");
		labelHungerRefill.setToolTipText("<html>The amount of hunger points that will be refilled when the user eats the food<br>"
				+ "Two hunger points refill one chicken wing</html>");
		labelPanel.add(labelHungerRefill);
		
		hungerRefillSpinner = new JSpinner();
		hungerRefillSpinner.setToolTipText("<html>The amount of hunger points that will be refilled when the user eats the food<br>"
				+ "Two hunger points refill one chicken wing</html>");
		hungerRefillSpinner.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		interactionPanel.add(hungerRefillSpinner);
		
		labelSaturation = new JLabel("Saturation");
		labelSaturation.setToolTipText("<html>The amount of saturation that the user gains by eating the food<br>"
				+ "As long as the user has saturation, he will not lose any hunger<br>"
				+ "The amount of saturation the user has drains over time, the speed at which this happens varies based on activity</html>");
		labelPanel.add(labelSaturation);
		
		saturationSpinner = new JSpinner();
		saturationSpinner.setToolTipText("<html>The amount of saturation that the user gains by eating the food<br>"
				+ "As long as the user has saturation, he will not lose any hunger<br>"
				+ "The amount of saturation the user has drains over time, the speed at which this happens varies based on activity</html>");
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
		feedToWolvesCheckbox.setToolTipText("Determine whether or not the food can be fed to a wolf");
		feedToWolvesPanel.add(feedToWolvesCheckbox);
		
		alwaysEdiblePanel = new JPanel();
		alwaysEdiblePanel.setLayout(new BorderLayout(0, 0));
		propertiesPanel.add(alwaysEdiblePanel);
		
		alwaysEdibleCheckbox = new JCheckBox("Always edible");
		alwaysEdibleCheckbox.setToolTipText("<html>Determine whether or not the food can be eaten, even with a full hunger bar<br>"
				+ "This may be useful, because the user will still gain saturation</html>");
		alwaysEdiblePanel.add(alwaysEdibleCheckbox);
		
		effectsListPanel = new JPanel();
		effectsListPanel.setLayout(new GridLayout(0, 1, 0, 5));
		effectsListPanel.add(new JLabel());
		mainPanel.add(effectsListPanel, BorderLayout.SOUTH);
		
		effectsListTopPanel = new JPanel();
		effectsListTopPanel.setLayout(new GridLayout(0, 4, 0, 5));
		effectsListPanel.add(effectsListTopPanel);
		
		labelEffect = new JLabel("Effect");
		labelEffect.setToolTipText("The effect type");
		effectsListTopPanel.add(labelEffect);
		
		labelDuration = new JLabel("Duration");
		labelDuration.setToolTipText("<html>The duration of the effect in ticks<br>"
				+ "One second is equal to twenty ticks</html>");
		effectsListTopPanel.add(labelDuration);
		
		labelAmplifier = new JLabel("Amplifier");
		labelAmplifier.setToolTipText("<html>The amplifier for the effect<br>"
				+ "An amplifier of zero means level one, an amplifier of one means level two, this pattern continues</html>");
		effectsListTopPanel.add(labelAmplifier);
		
		addEffectButton = new JButton("Add Effect");
		addEffectButton.setToolTipText("Add potion effects that will occur to the user when he eats the food");
		addEffectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addEffect();
			}
		});
		effectsListTopPanel.add(addEffectButton);
		
		effectPanels = new EffectPanel[0];
	}
	
	public FoodItemEditor(ItemsActivityPanel main, ItemElement item) {
		this(item.name, main);
		regularSetup(main, item);
		if (item.item.stacksize != null)
			maxStackSizeSpinner.setValue(item.item.stacksize);
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
		if (resource.effects != null)
			for (Integer[] effect : resource.effects)
				addEffect(new EffectPanel(this, effect));
	}

	protected void save() {
		if (!textureChooserIsOpen && textureLabel.getText().length() > 0){
			FoodItemResource base = new FoodItemResource();
			base.amount = (Integer) hungerRefillSpinner.getValue();
			base.saturation = (Float) saturationSpinner.getValue();
			base.wolf = feedToWolvesCheckbox.isSelected();
			base.alwaysedible = alwaysEdibleCheckbox.isSelected();
			
			Set<Integer[]> effects = new HashSet<Integer[]>();
			for (EffectPanel effect : effectPanels)
				effects.add(new Integer[]{effect.effectComboBox.getSelectedIndex(), 
						(Integer) effect.durationSpinner.getValue(), (Integer) effect.amplifierSpinner.getValue()});
			base.effects = effects;
			
			ItemElement item = writeItem(base);
			main.addItem(item);
		}
	}
	
	protected void addEffect(){
		addEffect(new EffectPanel(this));
	}
	
	protected void addEffect(EffectPanel effect){
		EffectPanel[] newEffectPanels = new EffectPanel[effectPanels.length + 1];
		for (int i = 0; i < effectPanels.length; i++)
			newEffectPanels[i] = effectPanels[i];
		newEffectPanels[effectPanels.length] = effect;
		effect.id = effectPanels.length;
		effectPanels = newEffectPanels;
		
		effectsListPanel.add(effect);
		paintAll(getGraphics());
	}
	
	public void removeEffect(int id){
		effectsListPanel.remove(effectPanels[id]);

		EffectPanel[] newEffectPanels = new EffectPanel[effectPanels.length - 1];
		for (int i = 0; i < id; i++)
			newEffectPanels[i] = effectPanels[i];
		for (int i = id + 1; i < effectPanels.length; i++){
			newEffectPanels[i - 1] = effectPanels[i];
			effectPanels[i].id = i - 1;
		}
		effectPanels = newEffectPanels;

		paintAll(getGraphics());
	}
}
