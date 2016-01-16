package twintro.minecraft.modbuilder.editor.interfaces.editors;

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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import twintro.minecraft.modbuilder.data.resources.items.FoodItemResource;
import twintro.minecraft.modbuilder.data.resources.models.BlockModelResource;
import twintro.minecraft.modbuilder.editor.interfaces.activitypanels.ItemsActivityPanel;
import twintro.minecraft.modbuilder.editor.interfaces.choosewindows.ObjectRunnable;
import twintro.minecraft.modbuilder.editor.interfaces.helperclasses.EffectPanel;
import twintro.minecraft.modbuilder.editor.resources.ItemElement;

public class FoodItemEditor extends RegularItemEditor {
	private JPanel propertiesPanel;
	private JPanel feedToWolvesPanel;
	private JPanel alwaysEdiblePanel;
	private JPanel effectsListPanel;
	private JPanel effectsListTopPanel;
	private JLabel labelHungerRefill;
	private JLabel labelSaturation;
	private JLabel labelProperties;
	private JLabel labelEffects;
	private JLabel labelEffect;
	private JLabel labelDuration;
	private JLabel labelAmplifier;
	private JSpinner hungerRefillSpinner;
	private JSpinner saturationSpinner;
	private JCheckBox feedToWolvesCheckbox;
	private JCheckBox alwaysEdibleCheckbox;
	private JButton addEffectButton;
	private EffectPanel[] effectPanels;
	
	private final ObjectRunnable closeHandler = new ObjectRunnable(){
		@Override
		public void run(Object obj){
			removeEffect((Integer) obj);
		}
	};

	private static final String hungerRefillTooltip = "<html>The amount of hunger points that will be refilled when the user eats the food<br>"
			+ "Two hunger points refill one chicken wing</html>";
	private static final String saturationTooltip = "<html>The amount of saturation that the user gains by eating the food<br>"
				+ "As long as the user has saturation, he will not lose any hunger<br>"
				+ "The amount of saturation the user has drains over time, the speed at which this happens varies based on activity</html>";
	private static final String feedToWolvesTooltip = "Determine whether or not the food can be fed to a wolf";
	private static final String alwaysEdibleTooltip = "<html>Determine whether or not the food can be eaten, even with a full hunger bar<br>"
				+ "This may be useful, because the user will still gain saturation</html>";
	public static final String effectTypeTooltip = "The effect type";
	public static final String effectDurationTooltip = "<html>The duration of the effect in ticks<br>"
				+ "One second is equal to twenty ticks</html>";
	public static final String effectAmplifierTooltip = "<html>The amplifier for the effect<br>"
			+ "An amplifier of zero means level one, an amplifier of one means level two, this pattern continues</html>";
	public static final String removeEffectTooltip = "Remove this effect";
	private static final String addEffectTooltip = "Add potion effects that will occur to the user when he eats the food";
	
	public FoodItemEditor(String name, ObjectRunnable runnable, ObjectRunnable closeHandler) {
		super(name, runnable, closeHandler);
		setTitle("Edit Food: " + this.name);
		
		saveButton.setText("Save Food");
		
		labelHungerRefill = new JLabel("Hunger Refill");
		labelHungerRefill.setToolTipText(hungerRefillTooltip);
		labelPanel.add(labelHungerRefill);
		
		hungerRefillSpinner = new JSpinner();
		hungerRefillSpinner.setToolTipText(hungerRefillTooltip);
		hungerRefillSpinner.addChangeListener(changeListener);
		hungerRefillSpinner.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
		interactionPanel.add(hungerRefillSpinner);
		
		labelSaturation = new JLabel("Saturation");
		labelSaturation.setToolTipText(saturationTooltip);
		labelPanel.add(labelSaturation);
		
		saturationSpinner = new JSpinner();
		saturationSpinner.setToolTipText(saturationTooltip);
		saturationSpinner.addChangeListener(changeListener);
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
		feedToWolvesCheckbox.setToolTipText(feedToWolvesTooltip);
		feedToWolvesCheckbox.addActionListener(actionListener);
		feedToWolvesPanel.add(feedToWolvesCheckbox);
		
		alwaysEdiblePanel = new JPanel();
		alwaysEdiblePanel.setLayout(new BorderLayout(0, 0));
		propertiesPanel.add(alwaysEdiblePanel);
		
		alwaysEdibleCheckbox = new JCheckBox("Always edible");
		alwaysEdibleCheckbox.setToolTipText(alwaysEdibleTooltip);
		alwaysEdibleCheckbox.addActionListener(actionListener);
		alwaysEdiblePanel.add(alwaysEdibleCheckbox);
		
		effectsListPanel = new JPanel();
		effectsListPanel.setLayout(new GridLayout(0, 1, 0, 5));
		effectsListPanel.add(new JLabel());
		mainPanel.add(effectsListPanel, BorderLayout.SOUTH);
		
		effectsListTopPanel = new JPanel();
		effectsListTopPanel.setLayout(new GridLayout(0, 4, 0, 5));
		effectsListPanel.add(effectsListTopPanel);
		
		labelEffect = new JLabel("Effect");
		labelEffect.setToolTipText(effectTypeTooltip);
		effectsListTopPanel.add(labelEffect);
		
		labelDuration = new JLabel("Duration");
		labelDuration.setToolTipText(effectDurationTooltip);
		effectsListTopPanel.add(labelDuration);
		
		labelAmplifier = new JLabel("Amplifier");
		labelAmplifier.setToolTipText(effectAmplifierTooltip);
		effectsListTopPanel.add(labelAmplifier);
		
		addEffectButton = new JButton("Add Effect");
		addEffectButton.setToolTipText(addEffectTooltip);
		addEffectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addEffect();
			}
		});
		effectsListTopPanel.add(addEffectButton);
		
		effectPanels = new EffectPanel[0];
	}
	
	public FoodItemEditor(ItemElement item, ObjectRunnable runnable, ObjectRunnable closeHandler) {
		this(item.name, runnable, closeHandler);
		regularSetup(item);
		if (item.item.stacksize != null)
			maxStackSizeSpinner.setValue(item.item.stacksize);
		foodSetup(item);
		
		changed = false;
	}
	
	private void foodSetup(ItemElement item){
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
				addEffect(new EffectPanel(closeHandler, this, effect));
	}
	
	@Override
	public boolean save() {
		if (textureLabel.getText().length() > 0){
			FoodItemResource base = new FoodItemResource();
			base.amount = (Integer) hungerRefillSpinner.getValue();
			base.saturation = (Float) saturationSpinner.getValue();
			base.wolf = feedToWolvesCheckbox.isSelected();
			base.alwaysedible = alwaysEdibleCheckbox.isSelected();
			
			Set<Integer[]> effects = new HashSet<Integer[]>();
			for (EffectPanel effect : effectPanels)
				if (effect.isProperEffect())
					effects.add(effect.getEffect());
			base.effects = effects;
			
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
	
	private void addEffect(){
		change();
		addEffect(new EffectPanel(closeHandler, this));
	}
	
	private void addEffect(EffectPanel effect){
		EffectPanel[] newEffectPanels = new EffectPanel[effectPanels.length + 1];
		for (int i = 0; i < effectPanels.length; i++)
			newEffectPanels[i] = effectPanels[i];
		newEffectPanels[effectPanels.length] = effect;
		effect.setId(effectPanels.length);
		effectPanels = newEffectPanels;
		
		effectsListPanel.add(effect);
		paintAll(getGraphics());
	}
	
	private void removeEffect(int id){
		effectsListPanel.remove(effectPanels[id]);

		EffectPanel[] newEffectPanels = new EffectPanel[effectPanels.length - 1];
		for (int i = 0; i < id; i++)
			newEffectPanels[i] = effectPanels[i];
		for (int i = id + 1; i < effectPanels.length; i++){
			newEffectPanels[i - 1] = effectPanels[i];
			effectPanels[i].setId(i - 1);
		}
		effectPanels = newEffectPanels;

		paintAll(getGraphics());
	}
}
