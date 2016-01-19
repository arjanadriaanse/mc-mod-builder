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

public class OreStructureEditor extends WindowClosingVerifierUser {
	private JPanel buttonPanel;
	private JPanel mainPanel;
	private JPanel labelPanel;
	private JPanel interactionPanel;
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
	private JButton saveStructureButton;
	private JButton cancelButton;
	private JButton materialChooseButton;
	private JButton replacingChooseButton;
	private JSpinner maxVeinSizeSpinner;
	private JSpinner amountSpinner;
	private JSpinner minYSpinner;
	private JSpinner maxYSpinner;
	private JComboBox dimensionComboBox;

	private String name;
	private ObjectRunnable runnable;
	private ObjectRunnable closeHandler;

	private static final String materialTooltip = "The block that will be generated as an ore.";
	private static final String replacingTooltip = "<html>The block that the ore must replace.<br>" + 
								"If left empty, this will default to stone, netherrack or end stone, depending on the dimension.";
	private static final String dimensionTooltip = "The dimension the ore will generate in";
	private static final String maxVeinSizeTooltip = "The maximum amount of ore blocks in one vein";
	private static final String amountTooltip = "The amount of ore veins that will be generated in one chunk";
	private static final String regionTooltip = "The height levels between which the ore will generate";
	
	public OreStructureEditor(String name, ObjectRunnable runnable, ObjectRunnable closeHandler) {
		this.name = name;
		this.runnable = runnable;
		this.closeHandler = closeHandler;
		
		setBounds(100, 100, 390, 400);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowClosingVerifierListener());
		setTitle("Edit structure: " + this.name);
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout(5, 0));
		getContentPane().add(mainPanel, BorderLayout.NORTH);
		
		labelPanel = new JPanel();
		labelPanel.setLayout(new GridLayout(0, 1, 0, 5));
		mainPanel.add(labelPanel, BorderLayout.WEST);
		
		interactionPanel = new JPanel();
		interactionPanel.setLayout(new GridLayout(0, 1, 0, 5));
		mainPanel.add(interactionPanel, BorderLayout.CENTER);
		
		labelMaterial = new JLabel("Material");
		labelMaterial.setToolTipText(materialTooltip);
		labelPanel.add(labelMaterial);
		
		materialPanel = new JPanel();
		materialPanel.setLayout(new BorderLayout(0, 0));
		interactionPanel.add(materialPanel);
		
		materialChooseButton = new JButton("Choose");
		materialChooseButton.setToolTipText(materialTooltip);
		materialChooseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chooseMaterial();
			}
		});
		materialPanel.add(materialChooseButton, BorderLayout.EAST);
		
		materialLabel = new JLabel("");
		materialLabel.setToolTipText(materialTooltip);
		materialPanel.add(materialLabel, BorderLayout.CENTER);
		
		labelReplacing = new JLabel("Replacing");
		labelReplacing.setToolTipText(replacingTooltip);
		labelPanel.add(labelReplacing);
		
		replacingPanel = new JPanel();
		replacingPanel.setLayout(new BorderLayout(0, 0));
		interactionPanel.add(replacingPanel);
		
		replacingChooseButton = new JButton("Choose");
		replacingChooseButton.setToolTipText(replacingTooltip);
		replacingChooseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chooseReplacing();
			}
		});
		replacingPanel.add(replacingChooseButton, BorderLayout.EAST);
		
		replacingLabel = new JLabel("");
		replacingLabel.setToolTipText(replacingTooltip);
		replacingPanel.add(replacingLabel, BorderLayout.CENTER);
		
		labelDimension = new JLabel("Dimension");
		labelDimension.setToolTipText(dimensionTooltip);
		labelPanel.add(labelDimension);
		
		dimensionComboBox = new JComboBox();
		dimensionComboBox.setToolTipText(dimensionTooltip);
		dimensionComboBox.addActionListener(actionListener);
		dimensionComboBox.setModel(new DefaultComboBoxModel(new String[] {"Overworld", "Nether", "End"}));
		interactionPanel.add(dimensionComboBox);
		
		labelMaxVeinSize = new JLabel("Max vein size");
		labelMaxVeinSize.setToolTipText(maxVeinSizeTooltip);
		labelPanel.add(labelMaxVeinSize);
		
		maxVeinSizeSpinner = new JSpinner();
		maxVeinSizeSpinner.setToolTipText(maxVeinSizeTooltip);
		maxVeinSizeSpinner.addChangeListener(changeListener);
		maxVeinSizeSpinner.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		interactionPanel.add(maxVeinSizeSpinner);
		
		labelAmount = new JLabel("Veins per chunk");
		labelAmount.setToolTipText(amountTooltip);
		labelPanel.add(labelAmount);
		
		amountSpinner = new JSpinner();
		amountSpinner.setToolTipText(amountTooltip);
		amountSpinner.addChangeListener(changeListener);
		amountSpinner.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		interactionPanel.add(amountSpinner);
		
		labelRegion = new JLabel("Spawn region");
		labelRegion.setToolTipText(regionTooltip);
		labelPanel.add(labelRegion);
		
		regionPanel = new JPanel();
		regionPanel.setLayout(new GridLayout(0, 2, 5, 0));
		interactionPanel.add(regionPanel);
		
		minYPanel = new JPanel();
		minYPanel.setLayout(new BorderLayout(5, 0));
		regionPanel.add(minYPanel);
		
		labelMinY = new JLabel("From");
		labelMinY.setToolTipText(regionTooltip);
		minYPanel.add(labelMinY, BorderLayout.WEST);
		
		minYSpinner = new JSpinner();
		minYSpinner.setToolTipText(regionTooltip);
		minYSpinner.addChangeListener(changeListener);
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
		
		labelMaxY = new JLabel("To");
		labelMaxY.setToolTipText(regionTooltip);
		maxYPanel.add(labelMaxY, BorderLayout.WEST);
		
		maxYSpinner = new JSpinner();
		maxYSpinner.setToolTipText(regionTooltip);
		maxYSpinner.addChangeListener(changeListener);
		maxYSpinner.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				minYSpinner.setModel(new SpinnerNumberModel((Integer) minYSpinner.getValue(), 
						new Integer(0), (Integer) maxYSpinner.getValue(), new Integer(1)));
			}
		});
		maxYSpinner.setModel(new SpinnerNumberModel(new Integer(64), new Integer(0), null, new Integer(1)));
		maxYPanel.add(maxYSpinner, BorderLayout.CENTER);
		
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		
		saveStructureButton = new JButton("Save Structure");
		saveStructureButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				save();
			}
		});
		buttonPanel.add(saveStructureButton);
		
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cancel();
			}
		});
		buttonPanel.add(cancelButton);
		
		setVisible(true);
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
	
	private void cancel(){
		for (WindowListener listener : getWindowListeners()){
			listener.windowClosing(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		}
	}
	
	@Override
	public void dispose() {
		closeHandler.run(name);
		super.dispose();
	}
}
