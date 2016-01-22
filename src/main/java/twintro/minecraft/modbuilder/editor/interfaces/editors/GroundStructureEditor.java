package twintro.minecraft.modbuilder.editor.interfaces.editors;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import twintro.minecraft.modbuilder.data.resources.MaterialResource;
import twintro.minecraft.modbuilder.data.resources.TabResource;
import twintro.minecraft.modbuilder.data.resources.blocks.BaseBlockResource;
import twintro.minecraft.modbuilder.data.resources.blocks.BlockResource;
import twintro.minecraft.modbuilder.data.resources.models.BlockstateResource;
import twintro.minecraft.modbuilder.data.resources.models.ItemModelResource;
import twintro.minecraft.modbuilder.data.resources.models.BlockstateResource.Variant;
import twintro.minecraft.modbuilder.data.resources.models.ItemModelResource.Display;
import twintro.minecraft.modbuilder.data.resources.structures.GroundStructureResource;
import twintro.minecraft.modbuilder.data.resources.structures.OreStructureResource;
import twintro.minecraft.modbuilder.data.resources.structures.StructureType;
import twintro.minecraft.modbuilder.editor.Editor;
import twintro.minecraft.modbuilder.editor.interfaces.activitypanels.StructureActivityPanel;
import twintro.minecraft.modbuilder.editor.interfaces.choosewindows.MaterialChooseWindow;
import twintro.minecraft.modbuilder.editor.interfaces.choosewindows.ObjectRunnable;
import twintro.minecraft.modbuilder.editor.interfaces.choosewindows.TextureChooseWindow;
import twintro.minecraft.modbuilder.editor.interfaces.helperclasses.MaterialLabel;
import twintro.minecraft.modbuilder.editor.interfaces.helperclasses.WindowClosingVerifierListener;
import twintro.minecraft.modbuilder.editor.interfaces.helperclasses.WindowClosingVerifierUser;
import twintro.minecraft.modbuilder.editor.resources.BlockElement;
import twintro.minecraft.modbuilder.editor.resources.MaterialResources;
import twintro.minecraft.modbuilder.editor.resources.StructureElement;

import javax.swing.JToggleButton;
import javax.swing.SpinnerNumberModel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JSpinner;
import javax.swing.SwingConstants;

public class GroundStructureEditor extends PropertiesEditor {
	private JPanel materialPanel;
	private JPanel coverPanel;
	private JPanel coverSubPanel;
	private JLabel labelMaterial;
	private JLabel labelCover;
	private JLabel labelDimension;
	private JLabel labelAmount;
	private JLabel coverLabel;
	private MaterialLabel materialLabel;
	private JButton materialChooseButton;
	private JButton addCoverButton;
	private JButton coverResetButton;
	private JSpinner amountSpinner;
	private JComboBox dimensionComboBox;
	
	private String coverString;

	private static final String materialTooltip = "The block that will be generated in the world";
	private static final String coverTooltip = "<html>The block where the ground cover needs to stand on.<br>" + 
								"If empty, the ground cover will generate on every block";
	private static final String dimensionTooltip = "<html>The dimension the ground cover will generate in.<br>" + 
								"Note that the ground in the nether means above the bedrock layer!";
	private static final String amountTooltip = "The amount of blocks the generator will try to place in one chunk";
	
	public GroundStructureEditor(String name, ObjectRunnable runnable, ObjectRunnable closeHandler) {
		super(name, runnable, closeHandler);
		
		setTitle("Edit Structure: " + name);
		saveButton.setText("Save Structure");
		
		labelMaterial = label("Material", materialTooltip, labelPanel);
		materialChooseButton = button("Choose", materialTooltip);
		materialLabel = materialLabel("", materialTooltip);
		materialPanel = panel(materialLabel, materialChooseButton, interactionPanel);
		materialChooseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chooseMaterial();
			}
		});
		
		labelCover = label("Blocks To Cover", coverTooltip, labelPanel);
		addCoverButton = button("Add", coverTooltip);
		coverResetButton = button("Reset", coverTooltip);
		coverLabel = tooltipLabel("", coverTooltip);
		coverString = "";
		coverSubPanel = panel(coverLabel, coverResetButton);
		coverPanel = panel(coverSubPanel, addCoverButton, interactionPanel);
		addCoverButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addBlockToCover();
			}
		});
		coverResetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetBlocksToCover();
			}
		});
		
		labelDimension = label("Dimension", dimensionTooltip, labelPanel);
		dimensionComboBox = combobox(dimensionTooltip, interactionPanel);
		dimensionComboBox.setModel(new DefaultComboBoxModel(new String[] {"Overworld", "Nether", "End"}));
		
		labelAmount = label("Amount Per Chunk", amountTooltip, labelPanel);
		amountSpinner = spinner(amountTooltip, interactionPanel);
		amountSpinner.setModel(new SpinnerNumberModel(new Integer(1), new Integer(0), null, new Integer(1)));
		
		setSize(400);
	}
	
	public GroundStructureEditor(StructureElement structure, ObjectRunnable runnable, ObjectRunnable closeHandler){
		this(structure.name, runnable, closeHandler);
		
		GroundStructureResource ground = (GroundStructureResource) structure.structure;
		if (ground.block != null)
			chooseMaterial(ground.block);
		if (ground.onlyonblocks != null)
			for (String block : ground.onlyonblocks)
				addBlockToCover(block);
		if (ground.dimension != null){
			switch (ground.dimension){
			case -1:
				dimensionComboBox.setSelectedItem("Nether");
				break;
			case 0:
				dimensionComboBox.setSelectedItem("Overworld");
				break;
			case 1:
				dimensionComboBox.setSelectedItem("End");
			}
		}
		if (ground.amountperchunk != null)
			amountSpinner.setValue(ground.amountperchunk);
		
		changed = false;
	}
	
	private void chooseMaterial() {
		new MaterialChooseWindow(MaterialChooseWindow.BLOCKS_ONLY, new ObjectRunnable() {
			@Override
			public void run(Object obj) {
				change();
				chooseMaterial((String) obj);
			}
		});
	}
	
	private void chooseMaterial(String material){
		materialLabel.setText(material);
		setIcon(material);
	}
	
	private void addBlockToCover() {
		new MaterialChooseWindow(MaterialChooseWindow.BLOCKS_ONLY, new ObjectRunnable() {
			@Override
			public void run(Object obj) {
				change();
				addBlockToCover((String) obj);
			}
		});
	}
	
	private void addBlockToCover(String block){
		if (coverString.length() > 0) coverString += ", ";
		coverString += block;
		if (coverLabel.getText().length() > 0) coverLabel.setText(coverLabel.getText() + ", ");
		coverLabel.setText(coverLabel.getText() + MaterialResources.simplifyItemStackName(block));
	}
	
	private void resetBlocksToCover(){
		change();
		coverLabel.setText("");
		coverString = "";
	}

	@Override
	public boolean save() {
		if (materialLabel.getMaterial().length() > 0){
			StructureElement structure = new StructureElement();
			structure.name = name;
			
			GroundStructureResource base = new GroundStructureResource();
			base.block = materialLabel.getMaterial();
			Set<String> blocks = new HashSet<String>();
			for (String block : coverString.split(", ")) blocks.add(block);
			base.onlyonblocks = blocks;
			switch (dimensionComboBox.getSelectedIndex()){
			case 0:
				base.dimension = 0;
				break;
			case 1:
				base.dimension = -1;
				break;
			case 2:
				base.dimension = 1;
			}
			base.amountperchunk = (Integer) amountSpinner.getValue();
			structure.structure = base;
			
			runnable.run(structure);
			dispose();
		}
		else{
			int selected = JOptionPane.showConfirmDialog(this, "You haven't chosen a material yet.", 
					"Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
			if (selected == JOptionPane.OK_OPTION)
				return false;
		}
		return true;
	}
}
