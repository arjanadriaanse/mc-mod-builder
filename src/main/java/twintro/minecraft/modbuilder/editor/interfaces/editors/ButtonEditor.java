package twintro.minecraft.modbuilder.editor.interfaces.editors;

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
import twintro.minecraft.modbuilder.editor.interfaces.helperclasses.ButtonRunnable;
import twintro.minecraft.modbuilder.editor.interfaces.helperclasses.MaterialChooseWindow;
import twintro.minecraft.modbuilder.editor.interfaces.helperclasses.MaterialRunnable;
import twintro.minecraft.modbuilder.editor.resources.VanillaElements;

public class ButtonEditor extends JFrame {
	private JPanel buttonPanel;
	private JPanel mainPanel;
	private JPanel labelPanel;
	private JPanel interactionPanel;
	private JPanel materialPanel;
	private JPanel containerPanel;
	private JPanel containerSubPanel;
	private JLabel labelMaterial;
	private JLabel labelContainer;
	private JLabel labelStacksize;
	private JLabel materialLabel;
	private JLabel containerLabel;
	private JButton materialChooseButton;
	private JButton containerChooseButton;
	private JButton saveButton;
	private JButton cancelButton;
	private JSpinner stacksizeSpinner;
	private JCheckBox containerUseCheckBox;
	
	private boolean isProduct;
	private boolean materialChooserIsOpen;
	private Editor main;
	private ButtonRunnable runnable;
	
	private static final String materialInputTooltip = "The ingredient's material";
	private static final String materialOutputTooltip = "The product's material";
	private static final String containerTooltip = "The container's material";
	private static final String stacksizeTooltip = "The stacksize of the product";
	
	public ButtonEditor(boolean isProduct, Editor main, ButtonRunnable runnable){
		this.isProduct = isProduct;
		this.main = main;
		this.runnable = runnable;
		
		setBounds(100, 100, 300, 200);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		if (isProduct)
			setTitle("Choose Output");
		else
			setTitle("Choose Ingredient");
		
		mainPanel = new JPanel();
		getContentPane().add(mainPanel, BorderLayout.NORTH);
		mainPanel.setLayout(new BorderLayout(0, 0));
		
		labelPanel = new JPanel();
		mainPanel.add(labelPanel, BorderLayout.WEST);
		labelPanel.setLayout(new GridLayout(0, 1, 0, 5));
		
		interactionPanel = new JPanel();
		mainPanel.add(interactionPanel, BorderLayout.CENTER);
		interactionPanel.setLayout(new GridLayout(0, 1, 0, 5));
		
		labelMaterial = new JLabel("Material");
		if (this.isProduct) labelMaterial.setToolTipText(materialOutputTooltip);
		else labelMaterial.setToolTipText(materialInputTooltip);
		labelPanel.add(labelMaterial);
		
		materialPanel = new JPanel();
		interactionPanel.add(materialPanel);
		materialPanel.setLayout(new BorderLayout(0, 0));
		
		materialChooseButton = new JButton("Choose");
		if (this.isProduct) materialChooseButton.setToolTipText(materialOutputTooltip);
		else materialChooseButton.setToolTipText(materialInputTooltip);
		materialPanel.add(materialChooseButton, BorderLayout.EAST);
		materialChooseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				chooseMaterial();
			}
		});
		
		materialLabel = new JLabel("");
		materialPanel.add(materialLabel, BorderLayout.CENTER);
		
		if (isProduct){
			labelStacksize = new JLabel("Stack size");
			labelStacksize.setToolTipText(stacksizeTooltip);
			labelPanel.add(labelStacksize);
			
			stacksizeSpinner = new JSpinner();
			stacksizeSpinner.setToolTipText(stacksizeTooltip);
			stacksizeSpinner.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), new Integer(64), new Integer(1)));
			interactionPanel.add(stacksizeSpinner);
		}
		else{
			labelContainer = new JLabel("Container");
			labelContainer.setToolTipText(containerTooltip);
			labelContainer.setEnabled(false);
			labelPanel.add(labelContainer);
			
			containerPanel = new JPanel();
			containerPanel.setLayout(new BorderLayout(0, 0));
			interactionPanel.add(containerPanel);
			
			containerUseCheckBox = new JCheckBox("Use");
			containerUseCheckBox.setToolTipText(containerTooltip);
			containerPanel.add(containerUseCheckBox, BorderLayout.EAST);
			containerUseCheckBox.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					containerUse();
				}
			});
			
			containerSubPanel = new JPanel();
			containerSubPanel.setLayout(new BorderLayout(0, 0));
			containerPanel.add(containerSubPanel, BorderLayout.CENTER);
			
			containerChooseButton = new JButton("Choose");
			containerChooseButton.setToolTipText(containerTooltip);
			containerChooseButton.setEnabled(false);
			containerSubPanel.add(containerChooseButton, BorderLayout.EAST);
			containerChooseButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					chooseContainer();
				}
			});
			
			containerLabel = new JLabel("");
			containerLabel.setToolTipText(containerTooltip);
			containerLabel.setEnabled(false);
			containerSubPanel.add(containerLabel, BorderLayout.CENTER);
		}
		
		buttonPanel = new JPanel();
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		buttonPanel.setLayout(new FlowLayout());
		
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
	
	public ButtonEditor(boolean isProduct, Editor main, ButtonRunnable runnable, ItemStackResource item){
		this(isProduct, main, runnable);

		if (item.item != null) materialLabel.setText(item.item);
		else if (item.block != null) materialLabel.setText(item.block);
		if (isProduct && item.amount != null) stacksizeSpinner.setValue(item.amount);
		if (!isProduct && item.container != null){
			containerLabel.setText(item.container);
			containerUseCheckBox.setSelected(true);
			containerUse();
		}
	}
	
	private void chooseMaterial(){
		if (!materialChooserIsOpen){
			new MaterialChooseWindow(MaterialChooseWindow.ITEMS_AND_BLOCKS, main, new MaterialRunnable() {
				@Override
				public void chooseMaterial(String material) {
					materialLabel.setText(material);
				}

				@Override
				public void materialChooserDispose() {
					materialChooserIsOpen = false;
				}
			});
			materialChooserIsOpen = true;
		}
	}
	
	private void containerUse(){
		boolean use = containerUseCheckBox.isSelected();
		labelContainer.setEnabled(use);
		containerChooseButton.setEnabled(use);
		containerLabel.setEnabled(use);
	}
	
	private void chooseContainer(){
		if (!materialChooserIsOpen){
			new MaterialChooseWindow(MaterialChooseWindow.ITEMS_AND_BLOCKS, main, new MaterialRunnable() {
				@Override
				public void chooseMaterial(String material) {
					containerLabel.setText(material);
				}

				@Override
				public void materialChooserDispose() {
					materialChooserIsOpen = false;
				}
			});
			materialChooserIsOpen = true;
		}
	}
	
	private void save(){
		if (materialLabel.getText() != null){
			ItemStackResource item = new ItemStackResource();
			
			if (VanillaElements.isItem(materialLabel.getText()) || 
					main.ItemPanel.elements.keySet().contains(materialLabel.getText()))
				item.item = materialLabel.getText();
			else
				item.block = materialLabel.getText();
			if (isProduct)
				item.amount = (Integer) stacksizeSpinner.getValue();
			else
				if (containerUseCheckBox.isSelected())
					item.container = containerLabel.getText();
			
			runnable.save(item);
			dispose();
		}
	}
	
	private void cancel(){
		dispose();
	}
}
