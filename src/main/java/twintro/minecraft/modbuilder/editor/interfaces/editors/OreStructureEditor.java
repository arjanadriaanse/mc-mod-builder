package twintro.minecraft.modbuilder.editor.interfaces.editors;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import twintro.minecraft.modbuilder.data.resources.structures.GroundStructureResource;
import twintro.minecraft.modbuilder.data.resources.structures.OreStructureResource;
import twintro.minecraft.modbuilder.editor.interfaces.activitypanels.StructureActivityPanel;
import twintro.minecraft.modbuilder.editor.interfaces.choosewindows.MaterialChooseWindow;
import twintro.minecraft.modbuilder.editor.interfaces.choosewindows.ObjectRunnable;
import twintro.minecraft.modbuilder.editor.interfaces.helperclasses.WindowClosingVerifierListener;
import twintro.minecraft.modbuilder.editor.interfaces.helperclasses.WindowClosingVerifierUser;
import twintro.minecraft.modbuilder.editor.resources.StructureElement;
import javax.swing.SpinnerNumberModel;

public class OreStructureEditor extends PropertiesEditor {
	private JPanel materialPanel;
	private JPanel replacingPanel;
	private JPanel regionPanel;
	private JPanel minYPanel;
	private JPanel maxYPanel;
	private JLabel labelMaterial;
	private JLabel labelReplacing;
	private JLabel labelDimension;
	private JLabel labelMaxVeinSize;
	private JLabel labelAmount;
	private JLabel labelRegion;
	private JLabel labelMinY;
	private JLabel labelMaxY;
	private JLabel materialLabel;
	private JLabel replacingLabel;
	private JButton materialChooseButton;
	private JButton replacingChooseButton;
	private JSpinner maxVeinSizeSpinner;
	private JSpinner amountSpinner;
	private JSpinner minYSpinner;
	private JSpinner maxYSpinner;
	private JComboBox dimensionComboBox;

	private static final String materialTooltip = "The block that will be generated as an ore.";
	private static final String replacingTooltip = "<html>The block that the ore must replace.<br>" + 
								"If left empty, this will default to stone, netherrack or end stone, depending on the dimension.";
	private static final String dimensionTooltip = "The dimension the ore will generate in";
	private static final String maxVeinSizeTooltip = "The maximum amount of ore blocks in one vein";
	private static final String amountTooltip = "The amount of ore veins that will be generated in one chunk";
	private static final String regionTooltip = "The height levels between which the ore will generate";
	
	public OreStructureEditor(String name, ObjectRunnable runnable, ObjectRunnable closeHandler) {
		super(name, runnable, closeHandler);
		setBounds(100, 100, 400, 255);
		setTitle("Edit Structure: " + name);
		saveButton.setText("Save Structure");
		
		labelMaterial = label("Material", materialTooltip, labelPanel);
		materialChooseButton = button("Choose", materialTooltip);
		materialLabel = label("", materialTooltip);
		materialPanel = panel(materialLabel, materialChooseButton, interactionPanel);
		materialChooseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chooseMaterial();
			}
		});
		
		labelReplacing = label("Replacing", replacingTooltip, labelPanel);
		replacingChooseButton = button("Choose", replacingTooltip);
		replacingLabel = label("", replacingTooltip);
		replacingPanel = panel(replacingLabel, replacingChooseButton, interactionPanel);
		replacingChooseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chooseReplacing();
			}
		});
		
		labelDimension = label("Dimension", dimensionTooltip, labelPanel);
		dimensionComboBox = combobox(dimensionTooltip, interactionPanel);
		dimensionComboBox.setModel(new DefaultComboBoxModel(new String[] {"Overworld", "Nether", "End"}));
		
		labelMaxVeinSize = label("Max vein size", maxVeinSizeTooltip, labelPanel);
		maxVeinSizeSpinner = spinner(maxVeinSizeTooltip, interactionPanel);
		maxVeinSizeSpinner.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		
		labelAmount = label("Veins per chunk", amountTooltip, labelPanel);
		amountSpinner = spinner(amountTooltip, interactionPanel);
		amountSpinner.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		
		labelRegion = new JLabel("Spawn region");
		labelRegion.setToolTipText(regionTooltip);
		labelPanel.add(labelRegion);
		
		regionPanel = new JPanel();
		regionPanel.setLayout(new GridLayout(0, 2, 5, 0));
		interactionPanel.add(regionPanel);
		
		minYPanel = new JPanel();
		minYPanel.setLayout(new BorderLayout(5, 0));
		regionPanel.add(minYPanel);
		
		labelMinY = label("From", regionTooltip);
		minYPanel.add(labelMinY, BorderLayout.WEST);
		
		minYSpinner = spinner(regionTooltip);
		minYSpinner.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				maxYSpinner.setModel(new SpinnerNumberModel((Integer) maxYSpinner.getValue(), 
						(Integer) minYSpinner.getValue(), null, new Integer(1)));
			}
		});
		minYSpinner.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), new Integer(64), new Integer(1)));
		minYPanel.add(minYSpinner, BorderLayout.CENTER);
		
		maxYPanel = new JPanel();
		maxYPanel.setLayout(new BorderLayout(5, 0));
		regionPanel.add(maxYPanel);
		
		labelMaxY = label("To", regionTooltip);
		maxYPanel.add(labelMaxY, BorderLayout.WEST);
		
		maxYSpinner = spinner(regionTooltip);
		maxYSpinner.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				minYSpinner.setModel(new SpinnerNumberModel((Integer) minYSpinner.getValue(), 
						new Integer(0), (Integer) maxYSpinner.getValue(), new Integer(1)));
			}
		});
		maxYSpinner.setModel(new SpinnerNumberModel(new Integer(64), new Integer(0), null, new Integer(1)));
		maxYPanel.add(maxYSpinner, BorderLayout.CENTER);
	}
	
	public OreStructureEditor(StructureElement structure, ObjectRunnable runnable, ObjectRunnable closeHandler){
		this(structure.name, runnable, closeHandler);
		
		OreStructureResource ore = (OreStructureResource) structure.structure;
		if (ore.block != null)
			chooseMaterial(ore.block);
		if (ore.replaceblock != null)
			replacingLabel.setText(ore.replaceblock);
		if (ore.dimension != null){
			switch (ore.dimension){
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
		if (ore.maxveinsize != null)
			maxVeinSizeSpinner.setValue(ore.maxveinsize);
		if (ore.chancestospawn != null)
			amountSpinner.setValue(ore.chancestospawn);
		if (ore.minY != null && ore.maxY != null){
			minYSpinner.setModel(new SpinnerNumberModel(ore.minY, new Integer(0), ore.maxY, new Integer(1)));
			maxYSpinner.setModel(new SpinnerNumberModel(ore.maxY, ore.minY, null, new Integer(1)));
		}
		
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

	private void chooseReplacing() {
		new MaterialChooseWindow(MaterialChooseWindow.ITEMS_BLOCKS_NONE, new ObjectRunnable() {
			@Override
			public void run(Object obj) {
				change();
				replacingLabel.setText((String) obj);
			}
		});
	}

	@Override
	public boolean save() {
		if (materialLabel.getText().length() > 0){
			StructureElement structure = new StructureElement();
			structure.name = name;
			
			OreStructureResource base = new OreStructureResource();
			base.block = materialLabel.getText();
			base.replaceblock = replacingLabel.getText();
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
			base.maxveinsize = (Integer) maxVeinSizeSpinner.getValue();
			base.chancestospawn = (Integer) amountSpinner.getValue();
			base.minY = (Integer) minYSpinner.getValue();
			base.maxY = (Integer) maxYSpinner.getValue();
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
