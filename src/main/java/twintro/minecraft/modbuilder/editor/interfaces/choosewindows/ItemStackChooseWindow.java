package twintro.minecraft.modbuilder.editor.interfaces.choosewindows;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import joptsimple.util.KeyValuePair;
import twintro.minecraft.modbuilder.data.resources.recipes.ItemStackResource;
import twintro.minecraft.modbuilder.editor.Editor;
import twintro.minecraft.modbuilder.editor.interfaces.helperclasses.EffectPanel;
import twintro.minecraft.modbuilder.editor.interfaces.helperclasses.EnchantmentPanel;
import twintro.minecraft.modbuilder.editor.interfaces.helperclasses.IconDialog;
import twintro.minecraft.modbuilder.editor.interfaces.helperclasses.IconFrame;
import twintro.minecraft.modbuilder.editor.interfaces.helperclasses.MaterialLabel;
import twintro.minecraft.modbuilder.editor.interfaces.helperclasses.TooltipLabel;
import twintro.minecraft.modbuilder.editor.resources.MaterialResources;

public class ItemStackChooseWindow extends IconDialog {
	private JPanel mainPanel;
	private JPanel buttonPanel;
	private JPanel labelPanel;
	private JPanel interactionPanel;
	private JPanel materialPanel;
	private JPanel containerPanel;
	private JPanel containerSubPanel;
	private JPanel enchantmentListPanel;
	private JPanel enchantmentsTopPanel;
	private JLabel labelMaterial;
	private JLabel labelContainer;
	private JLabel labelStackSize;
	private JLabel labelEnchantment;
	private JLabel labelAmplifier;
	private MaterialLabel materialLabel;
	private MaterialLabel containerLabel;
	private JButton materialChooseButton;
	private JButton containerChooseButton;
	private JButton addEnchantmentButton;
	private JButton saveButton;
	private JButton cancelButton;
	private JSpinner stackSizeSpinner;
	private JCheckBox containerCheckBox;
	private EnchantmentPanel[] enchantmentPanels;
	
	private boolean isProduct;
	private ObjectRunnable runnable;

	private static final String materialProductTooltip = "The material of the product";
	private static final String materialIngredientTooltip = "The material of the ingredient";
	private static final String containerTooltip = "<html>The material of the container<br>"
			+ "The container is the item or block that will be left behind after crafting</html>";
	private static final String stackSizeTooltip = "The amount of the item or block that will be crafted";
	public static final String enchantmentTypeTooltip = ""; //TODO
	public static final String amplifierTooltip = ""; //TODO
	public static final String removeEnchantmentTooltip = ""; //TODO
	private static final String addEnchantmentTooltip = ""; //TODO
	
	private final ObjectRunnable closeHandler = new ObjectRunnable(){
		@Override
		public void run(Object obj){
			removeEnchantment((Integer) obj);
		}
	};
	
	private WindowListener windowListener = new WindowListener() {
		@Override
		public void windowActivated(WindowEvent arg0) {}
		@Override
		public void windowClosed(WindowEvent arg0) {}
		@Override
		public void windowClosing(WindowEvent arg0) {}
		@Override
		public void windowDeactivated(WindowEvent arg0) {}
		@Override
		public void windowDeiconified(WindowEvent arg0) {}
		@Override
		public void windowIconified(WindowEvent arg0) {}
		@Override
		public void windowOpened(WindowEvent arg0) {
			setBounds(100, 100, 400, (int) (mainPanel.getSize().getHeight() + buttonPanel.getSize().getHeight() 
					+ getSize().getHeight() - getContentPane().getSize().getHeight()) + 15);
		}
	};
	
	public ItemStackChooseWindow(boolean isProduct, ObjectRunnable runnable){
		initialize(isProduct, runnable);

		addWindowListener(windowListener);
		
		setModalityType(ModalityType.APPLICATION_MODAL);
		setVisible(true);
	}
	
	public ItemStackChooseWindow(boolean isProduct, ObjectRunnable runnable, ItemStackResource item){
		initialize(isProduct, runnable);
		
		chooseMaterial(MaterialResources.getMaterialName(item));
		if (isProduct && item.amount != null)
			stackSizeSpinner.setValue(item.amount);
		if (!isProduct && item.container != null){
			containerLabel.setText(item.container);
			containerCheckBox.setSelected(true);
			useContainer();
		}
		if (isProduct && item.enchantments != null)
			for (String key : item.enchantments.keySet())
				addEnchantment(new EnchantmentPanel(closeHandler, new SimpleEntry(key, item.enchantments.get(key))));
		
		addWindowListener(windowListener);
		
		setModalityType(ModalityType.APPLICATION_MODAL);
		setVisible(true);
	}
	
	private void initialize(boolean isProduct, ObjectRunnable runnable){
		this.isProduct = isProduct;
		this.runnable = runnable;
		
		((JComponent) getContentPane()).setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		if (isProduct)
			setTitle("Choose Product");
		else
			setTitle("Choose Ingredient");
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout(5, 0));
		getContentPane().add(mainPanel, BorderLayout.NORTH);
		
		labelPanel = new JPanel();
		labelPanel.setLayout(new GridLayout(0, 1, 0, 5));
		mainPanel.add(labelPanel, BorderLayout.WEST);
		
		interactionPanel = new JPanel();
		interactionPanel.setLayout(new GridLayout(0, 1, 0, 5));
		mainPanel.add(interactionPanel, BorderLayout.CENTER);
		
		String materialTooltip;
		if (this.isProduct)
			materialTooltip = materialProductTooltip;
		else
			materialTooltip = materialIngredientTooltip;
		
		labelMaterial = new JLabel("Material");
		labelMaterial.setToolTipText(materialTooltip);
		labelPanel.add(labelMaterial);
		
		materialPanel = new JPanel();
		materialPanel.setLayout(new BorderLayout(0, 0));
		interactionPanel.add(materialPanel);
		
		materialChooseButton = new JButton("Choose");
		materialChooseButton.setToolTipText(materialTooltip);
		materialChooseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				chooseMaterial();
			}
		});
		materialPanel.add(materialChooseButton, BorderLayout.EAST);
		
		materialLabel = new MaterialLabel("", materialTooltip);
		materialPanel.add(materialLabel, BorderLayout.CENTER);
		
		if (this.isProduct){
			labelStackSize = new JLabel("Stack Size");
			labelStackSize.setToolTipText(stackSizeTooltip);
			labelPanel.add(labelStackSize);
			
			stackSizeSpinner = new JSpinner();
			stackSizeSpinner.setToolTipText(stackSizeTooltip);
			stackSizeSpinner.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), new Integer(64), new Integer(1)));
			interactionPanel.add(stackSizeSpinner);
			
			enchantmentListPanel = new JPanel();
			enchantmentListPanel.setLayout(new GridLayout(0, 1, 0, 5));
			enchantmentListPanel.add(new JLabel());
			mainPanel.add(enchantmentListPanel, BorderLayout.SOUTH);
			
			enchantmentsTopPanel = new JPanel();
			enchantmentsTopPanel.setLayout(new GridLayout(0, 3, 5, 0));
			enchantmentListPanel.add(enchantmentsTopPanel);

			labelEnchantment = new JLabel("Enchantment");
			labelEnchantment.setToolTipText(enchantmentTypeTooltip);
			enchantmentsTopPanel.add(labelEnchantment);
			labelAmplifier = new JLabel("Amplifier");
			labelAmplifier.setToolTipText(amplifierTooltip);
			enchantmentsTopPanel.add(labelAmplifier);
			addEnchantmentButton = new JButton("Add Enchantment");
			addEnchantmentButton.setToolTipText(addEnchantmentTooltip);
			enchantmentsTopPanel.add(addEnchantmentButton);
			addEnchantmentButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					addEnchantment();
				}
			});
			
			enchantmentPanels = new EnchantmentPanel[0];
		}
		else{
			labelContainer = new JLabel("Container");
			labelContainer.setToolTipText(containerTooltip);
			labelPanel.add(labelContainer);
			
			containerPanel = new JPanel();
			containerPanel.setLayout(new BorderLayout(0, 0));
			interactionPanel.add(containerPanel);
			
			containerCheckBox = new JCheckBox("Use");
			containerCheckBox.setToolTipText(containerTooltip);
			containerCheckBox.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					useContainer();
				}
			});
			containerPanel.add(containerCheckBox, BorderLayout.EAST);
			
			containerSubPanel = new JPanel();
			containerSubPanel.setLayout(new BorderLayout(0, 0));
			containerPanel.add(containerSubPanel, BorderLayout.CENTER);
			
			containerChooseButton = new JButton("Choose");
			containerChooseButton.setToolTipText(containerTooltip);
			containerChooseButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					chooseContainer();
				}
			});
			containerSubPanel.add(containerChooseButton, BorderLayout.EAST);
			
			containerLabel = new MaterialLabel("", containerTooltip);
			containerSubPanel.add(containerLabel, BorderLayout.CENTER);
			
			useContainer();
		}
		
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		
		saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});
		buttonPanel.add(saveButton);
		
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cancel();
			}
		});
		buttonPanel.add(cancelButton);
	}
	
	private void chooseMaterial(){
		new MaterialChooseWindow(MaterialChooseWindow.ITEMS_BLOCKS_NONE, new ObjectRunnable() {
			@Override
			public void run(Object obj) {
				chooseMaterial((String) obj);
			}
		});
	}
	
	private void chooseMaterial(String material){
		materialLabel.setText(material);
		setIcon(material);
	}
	
	private void useContainer(){
		boolean use = containerCheckBox.isSelected();
		labelContainer.setEnabled(use);
		containerChooseButton.setEnabled(use);
		containerLabel.setEnabled(use);
	}
	
	private void chooseContainer(){
		new MaterialChooseWindow(MaterialChooseWindow.ITEMS_NONE_METALESS, new ObjectRunnable() {
			@Override
			public void run(Object obj) {
				containerLabel.setText((String) obj);
			}
		});
	}
	
	private void save(){
		String material = materialLabel.getMaterial();
		if (material != null && material != ""){
			ItemStackResource item = new ItemStackResource();
			
			if (material.split("#").length == 2){
				try{
					item.meta = Integer.parseInt(material.split("#")[1]);
					material = material.split("#")[0];
				} catch (Exception e){
					e.printStackTrace();
				}
			}
			if (MaterialResources.isItem(material))
				item.item = material;
			else
				item.block = material;
			if (isProduct)
				item.amount = (Integer) stackSizeSpinner.getValue();
			else if (containerCheckBox.isSelected())
				item.container = containerLabel.getMaterial();
			
			if (isProduct){
				Map<String, Integer> enchantments = new HashMap<String, Integer>();
				for (EnchantmentPanel enchantment : enchantmentPanels){
					if (enchantment.isProperEnchantment()){
						SimpleEntry<String, Integer> entry = enchantment.getEnchantment();
						enchantments.put(entry.getKey(), entry.getValue());
					}
				}
				item.enchantments = enchantments;
			}
			
			runnable.run(item);
			dispose();
		}
		else{
			int selected = JOptionPane.showConfirmDialog(this, "You haven't chosen a material yet.", 
					"Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
			if (selected != JOptionPane.OK_OPTION)
				dispose();
		}
	}
	
	private void addEnchantment(){
		addEnchantment(new EnchantmentPanel(closeHandler));
	}
	
	private void addEnchantment(EnchantmentPanel enchantment){
		EnchantmentPanel[] newEnchantmentPanels = new EnchantmentPanel[enchantmentPanels.length + 1];
		for (int i = 0; i < enchantmentPanels.length; i++)
			newEnchantmentPanels[i] = enchantmentPanels[i];
		newEnchantmentPanels[enchantmentPanels.length] = enchantment;
		enchantment.setId(enchantmentPanels.length);
		enchantmentPanels = newEnchantmentPanels;
		
		enchantmentListPanel.add(enchantment);
		paintAll(getGraphics());
		updateSize();
	}
	
	private void removeEnchantment(int id){
		enchantmentListPanel.remove(enchantmentPanels[id]);

		EnchantmentPanel[] newEnchantmentPanels = new EnchantmentPanel[enchantmentPanels.length - 1];
		for (int i = 0; i < id; i++)
			newEnchantmentPanels[i] = enchantmentPanels[i];
		for (int i = id + 1; i < enchantmentPanels.length; i++){
			newEnchantmentPanels[i - 1] = enchantmentPanels[i];
			enchantmentPanels[i].setId(i - 1);
		}
		enchantmentPanels = newEnchantmentPanels;

		paintAll(getGraphics());
		updateSize();
	}
	
	private void updateSize(){
		setVisible(true);
		setBounds(this.getLocation().x, this.getLocation().y, (int) this.getSize().getWidth(), (int) (mainPanel.getSize().getHeight() + 
				buttonPanel.getSize().getHeight() + this.getSize().getHeight() - this.getContentPane().getSize().getHeight()) + 15);
	}
	
	private void cancel(){
		dispose();
	}
}
