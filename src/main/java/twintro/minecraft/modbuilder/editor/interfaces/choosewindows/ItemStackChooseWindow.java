package twintro.minecraft.modbuilder.editor.interfaces.choosewindows;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import twintro.minecraft.modbuilder.data.resources.recipes.ItemStackResource;
import twintro.minecraft.modbuilder.editor.Editor;
import twintro.minecraft.modbuilder.editor.interfaces.helperclasses.IconDialog;
import twintro.minecraft.modbuilder.editor.interfaces.helperclasses.IconFrame;
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
	
	private boolean isProduct;
	private ObjectRunnable runnable;

	private static final String materialProductTooltip = "The material of the product";
	private static final String materialIngredientTooltip = "The material of the ingredient";
	private static final String containerTooltip = "<html>The material of the container<br>"
			+ "The container is the item or block that will be left behind after crafting</html>";
	private static final String stackSizeTooltip = "The amount of the item or block that will be crafted";
	
	public ItemStackChooseWindow(boolean isProduct, ObjectRunnable runnable){
		initialize(isProduct, runnable);
		setVisible(true);
	}
	
	public ItemStackChooseWindow(boolean isProduct, ObjectRunnable runnable, ItemStackResource item){
		initialize(isProduct, runnable);
		
		if (item.item != null)
			chooseMaterial(item.item);
		else if (item.block != null)
			chooseMaterial(item.block);
		if (isProduct && item.amount != null)
			stackSizeSpinner.setValue(item.amount);
		if (!isProduct && item.container != null){
			containerLabel.setText(item.container);
			containerCheckBox.setSelected(true);
			useContainer();
		}
		
		setVisible(true);
	}
	
	private void initialize(boolean isProduct, ObjectRunnable runnable){
		this.isProduct = isProduct;
		this.runnable = runnable;

		setModal(true);
		setBounds(100, 100, 300, 135);
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
		
		materialLabel = new TooltipLabel("", materialTooltip);
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
			
			containerLabel = new TooltipLabel("", containerTooltip);
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
		new MaterialChooseWindow(MaterialChooseWindow.ITEMS_BLOCKS_NONE, new ObjectRunnable() {
			@Override
			public void run(Object obj) {
				containerLabel.setText((String) obj);
			}
		});
	}
	
	private void save(){
		String material = materialLabel.getText();
		if (material != null && material != ""){
			ItemStackResource item = new ItemStackResource();
			
			if (MaterialResources.isItem(material))
				item.item = material;
			else
				item.block = material;
			if (isProduct)
				item.amount = (Integer) stackSizeSpinner.getValue();
			else if (containerCheckBox.isSelected())
				item.container = containerLabel.getText();
			
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
	
	private void cancel(){
		dispose();
	}
}
