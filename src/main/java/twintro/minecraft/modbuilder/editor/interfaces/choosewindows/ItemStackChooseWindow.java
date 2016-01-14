package twintro.minecraft.modbuilder.editor.interfaces.choosewindows;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import twintro.minecraft.modbuilder.data.resources.recipes.ItemStackResource;
import twintro.minecraft.modbuilder.editor.Editor;
import twintro.minecraft.modbuilder.editor.resources.VanillaElements;

public class ItemStackChooseWindow extends JFrame {
	private JPanel mainPanel;
	private JPanel buttonPanel;
	private JPanel labelPanel;
	private JPanel interactionPanel;
	private JPanel materialPanel;
	private JPanel containerPanel;
	private JPanel containerSubPanel;
	private JLabel labelMaterial;
	private JLabel labelContainer;
	private JLabel labelStackSize;
	private JLabel materialLabel;
	private JLabel containerLabel;
	private JButton materialChooseButton;
	private JButton containerChooseButton;
	private JButton saveButton;
	private JButton cancelButton;
	private JSpinner stackSizeSpinner;
	private JCheckBox containerCheckBox;
	
	private MaterialChooseWindow materialChooseWindow;
	private boolean isProduct;
	private Editor main;
	private ItemStackRunnable runnable;

	private static final String materialProductTooltip = "The material of the product";
	private static final String materialIngredientTooltip = "The material of the ingredient";
	private static final String containerTooltip = "<html>The material of the container<br>"
			+ "The container is the item or block that will be left behind after crafting</html>";
	private static final String stackSizeTooltip = "The amount of the item or block that will be crafted";
	
	public ItemStackChooseWindow(boolean isProduct, Editor main, ItemStackRunnable runnable){
		this.isProduct = isProduct;
		this.main = main;
		this.runnable = runnable;
		
		setBounds(100, 100, 300, 130);
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
		
		materialLabel = new JLabel("");
		materialLabel.setToolTipText(materialTooltip);
		materialPanel.add(materialLabel, BorderLayout.CENTER);
		
		if (this.isProduct){
			labelStackSize = new JLabel("Stack size");
			labelStackSize.setToolTipText(stackSizeTooltip);
			labelPanel.add(labelStackSize);
			
			stackSizeSpinner = new JSpinner();
			stackSizeSpinner.setToolTipText(stackSizeTooltip);
			stackSizeSpinner.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), new Integer(64), new Integer(1)));
			interactionPanel.add(stackSizeSpinner);
		}
		else{
			labelContainer = new JLabel("Container");
			labelContainer.setToolTipText(containerTooltip);
			labelContainer.setEnabled(false);
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
			containerChooseButton.setEnabled(false);
			containerChooseButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					chooseContainer();
				}
			});
			containerSubPanel.add(containerChooseButton, BorderLayout.EAST);
			
			containerLabel = new JLabel("");
			containerLabel.setToolTipText(containerTooltip);
			containerLabel.setEnabled(false);
			containerSubPanel.add(containerLabel, BorderLayout.CENTER);
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
		
		setVisible(true);
	}
	
	public ItemStackChooseWindow(boolean isProduct, Editor main, ItemStackRunnable runnable, ItemStackResource item){
		this(isProduct, main, runnable);
		
		if (item.item != null)
			materialLabel.setText(item.item);
		else if (item.block != null)
			materialLabel.setText(item.block);
		if (isProduct && item.amount != null)
			stackSizeSpinner.setValue(item.amount);
		if (!isProduct && item.container != null){
			containerLabel.setText(item.container);
			containerCheckBox.setSelected(true);
			useContainer();
		}
	}
	
	private void chooseMaterial(){
		if (materialChooseWindow == null){
			materialChooseWindow = new MaterialChooseWindow(MaterialChooseWindow.ITEMS_AND_BLOCKS, main, new MaterialRunnable() {
				@Override
				public void chooseMaterial(String material) {
					materialLabel.setText(material);
				}

				@Override
				public void materialChooserDispose() {
					materialChooseWindow = null;
				}
			});
		}
	}
	
	private void useContainer(){
		boolean use = containerCheckBox.isSelected();
		labelContainer.setEnabled(use);
		containerChooseButton.setEnabled(use);
		containerLabel.setEnabled(use);
	}
	
	private void chooseContainer(){
		if (materialChooseWindow == null){
			materialChooseWindow = new MaterialChooseWindow(MaterialChooseWindow.ITEMS_AND_BLOCKS, main, new MaterialRunnable() {
				@Override
				public void chooseMaterial(String material) {
					containerLabel.setText(material);
				}

				@Override
				public void materialChooserDispose() {
					materialChooseWindow = null;
				}
			});
		}
	}
	
	private void save(){
		String material = materialLabel.getText();
		if (material != null){
			ItemStackResource item = new ItemStackResource();
			
			//TODO material.split":"[1] also in ToolItemEditor 233
			if (VanillaElements.isItem(material))
				item.item = material;
			else
				item.block = material;
			if (isProduct)
				item.amount = (Integer) stackSizeSpinner.getValue();
			else if (containerCheckBox.isSelected())
				item.container = containerLabel.getText();
			
			runnable.chooseItemStack(item);
		}
	}
	
	private void cancel(){
		if (materialChooseWindow != null) materialChooseWindow.dispose();
		dispose();
	}
	
	@Override
	public void dispose() {
		runnable.itemStackChooserDispose();
		super.dispose();
	}
}
