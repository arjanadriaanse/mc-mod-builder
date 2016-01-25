package twintro.minecraft.modbuilder.editor.interfaces.editors;

import java.awt.BorderLayout;
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

	private static final String hungerRefillTooltip = "<html>The amount of hunger points that will be restored when the user eats the food<br>"
			+ "Two hunger points refill one chicken wing</html>";
	private static final String saturationTooltip = "<html>The amount of saturation that the user gains by eating the food<br>"
				+ "As long as the user has saturation, he will not lose any hunger<br>"
				+ "The amount of saturation the user has drains over time, the speed at which this happens varies based on activity</html>";
	private static final String feedToWolvesTooltip = "Whether or not the food can be fed to a wolf";
	private static final String alwaysEdibleTooltip = "<html>Whether or not the food can be eaten with a full hunger bar<br>"
				+ "This may be useful, because the user will still gain saturation and potion effects</html>";
	public static final String effectTypeTooltip = "The effect type";
	public static final String effectDurationTooltip = "<html>The duration of the effect in ticks<br>"
				+ "One second is equal to twenty ticks</html>";
	public static final String effectAmplifierTooltip = "The amplifier of the effect.";
	public static final String removeEffectTooltip = "Remove this effect";
	private static final String addEffectTooltip = "Add a potion effect that will occur to the user when he eats the food";
	
	public FoodItemEditor(String name, ObjectRunnable runnable, ObjectRunnable closeHandler) {
		super(name, runnable, closeHandler);
		
		creativeTabsComboBox.setSelectedItem("Food");
		
		setTitle("Edit Food: " + this.name);
		saveButton.setText("Save Food");
		
		labelHungerRefill = label("Hunger Refill", hungerRefillTooltip, labelPanel);
		hungerRefillSpinner = spinner(hungerRefillTooltip, interactionPanel);
		hungerRefillSpinner.setModel(new SpinnerNumberModel(new Integer(6), new Integer(0), new Integer(20), new Integer(1)));
		
		labelSaturation = label("Saturation", saturationTooltip, labelPanel);
		saturationSpinner = spinner(saturationTooltip, interactionPanel);
		saturationSpinner.setModel(new SpinnerNumberModel(new Float(6), new Float(0), null, new Float(1)));
		
		labelProperties = new JLabel("Properties");
		labelPanel.add(labelProperties);
		
		propertiesPanel = new JPanel();
		propertiesPanel.setLayout(new GridLayout(0, 2, 0, 0));
		interactionPanel.add(propertiesPanel);
		
		alwaysEdibleCheckbox = checkbox("Always Edible", alwaysEdibleTooltip);
		alwaysEdiblePanel = panel(alwaysEdibleCheckbox);
		propertiesPanel.add(alwaysEdiblePanel);
		
		feedToWolvesCheckbox = checkbox("Feed To Wolves", feedToWolvesTooltip);
		feedToWolvesPanel = panel(feedToWolvesCheckbox);
		propertiesPanel.add(feedToWolvesPanel);
		
		effectsListPanel = new JPanel();
		effectsListPanel.setLayout(new GridLayout(0, 1, 0, 5));
		effectsListPanel.add(new JLabel());
		mainPanel.add(effectsListPanel, BorderLayout.SOUTH);
		
		effectsListTopPanel = new JPanel();
		effectsListTopPanel.setLayout(new GridLayout(0, 4, 5, 0));
		effectsListPanel.add(effectsListTopPanel);

		labelEffect = label("Effect", effectTypeTooltip, effectsListTopPanel);
		labelDuration = label("Duration", effectTypeTooltip, effectsListTopPanel);
		labelAmplifier = label("Amplifier", effectTypeTooltip, effectsListTopPanel);
		addEffectButton = button("Add Effect", addEffectTooltip, effectsListTopPanel);
		addEffectButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addEffect();
			}
		});
		
		effectPanels = new EffectPanel[0];
		
		setSize(500);
	}
	
	public FoodItemEditor(ItemElement item, ObjectRunnable runnable, ObjectRunnable closeHandler) {
		this(item.name, runnable, closeHandler);
		creativeTabsLabel.setText("");
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
		updateSize();
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
		updateSize();
	}
}
