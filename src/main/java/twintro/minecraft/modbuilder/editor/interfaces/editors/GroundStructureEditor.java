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
import twintro.minecraft.modbuilder.editor.interfaces.helperclasses.WindowClosingVerifierListener;
import twintro.minecraft.modbuilder.editor.interfaces.helperclasses.WindowClosingVerifierUser;
import twintro.minecraft.modbuilder.editor.resources.BlockElement;
import twintro.minecraft.modbuilder.editor.resources.StructureElement;

import javax.swing.JToggleButton;
import javax.swing.SpinnerNumberModel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JSpinner;
import javax.swing.SwingConstants;

public class GroundStructureEditor extends WindowClosingVerifierUser {
	private JPanel buttonPanel;
	private JPanel mainPanel;
	private JPanel labelPanel;
	private JPanel interactionPanel;
	private JPanel materialPanel;
	private JPanel coverPanel;
	private JPanel coverSubPanel;
	private JLabel labelMaterial;
	private JLabel labelCover;
	private JLabel labelDimension;
	private JLabel labelAmount;
	private JLabel materialLabel;
	private JLabel coverLabel;
	private JButton saveStructureButton;
	private JButton cancelButton;
	private JButton materialChooseButton;
	private JButton coverChooseButton;
	private JButton coverResetButton;
	private JSpinner amountSpinner;
	private JComboBox dimensionComboBox;

	private String name;
	private ObjectRunnable runnable;
	private ObjectRunnable closeHandler;

	private static final String materialTooltip = "";//TODO
	private static final String coverTooltip = "";//TODO
	private static final String dimensionTooltip = "";//TODO
	private static final String amountTooltip = "";//TODO
	
	public GroundStructureEditor(String name, ObjectRunnable runnable, ObjectRunnable closeHandler) {
		this.name = name;
		this.runnable = runnable;
		this.closeHandler = closeHandler;
		
		setBounds(100, 100, 390, 400);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowClosingVerifierListener());
		setTitle("Edit structure: " + name);
		
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
		
		labelCover = new JLabel("Blocks to cover");
		labelCover.setToolTipText(coverTooltip);
		labelPanel.add(labelCover);
		
		coverPanel = new JPanel();
		coverPanel.setLayout(new BorderLayout(0, 0));
		interactionPanel.add(coverPanel);
		
		coverChooseButton = new JButton("Choose");
		coverChooseButton.setToolTipText(coverTooltip);
		coverChooseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addBlockToCover();
			}
		});
		coverPanel.add(coverChooseButton, BorderLayout.EAST);
		
		coverSubPanel = new JPanel();
		coverSubPanel.setLayout(new BorderLayout(0, 0));
		coverPanel.add(coverSubPanel, BorderLayout.CENTER);
		
		coverResetButton = new JButton("Reset");
		coverResetButton.setToolTipText(coverTooltip);
		coverResetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetBlocksToCover();
			}
		});
		coverSubPanel.add(coverResetButton, BorderLayout.EAST);
		
		coverLabel = new JLabel("");
		coverLabel.setToolTipText(coverTooltip);
		coverSubPanel.add(coverLabel, BorderLayout.CENTER);
		
		labelDimension = new JLabel("Dimension");
		labelDimension.setToolTipText(dimensionTooltip);
		labelPanel.add(labelDimension);
		
		dimensionComboBox = new JComboBox();
		dimensionComboBox.setToolTipText(dimensionTooltip);
		dimensionComboBox.addActionListener(actionListener);
		dimensionComboBox.setModel(new DefaultComboBoxModel(new String[] {"Overworld", "Nether", "End"}));
		interactionPanel.add(dimensionComboBox);
		
		labelAmount = new JLabel("Amount per chunk");
		labelAmount.setToolTipText(amountTooltip);
		labelPanel.add(labelAmount);
		
		amountSpinner = new JSpinner();
		amountSpinner.setToolTipText(amountTooltip);
		amountSpinner.addChangeListener(changeListener);
		amountSpinner.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		interactionPanel.add(amountSpinner);
		
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
	
	public GroundStructureEditor(StructureElement structure, ObjectRunnable runnable, ObjectRunnable closeHandler){
		this(structure.name, runnable, closeHandler);
		
		GroundStructureResource ground = (GroundStructureResource) structure.structure;
		if (ground.block != null)
			materialLabel.setText(ground.block);
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
				materialLabel.setText((String) obj);
			}
		});
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
		if (coverLabel.getText().length() > 0) coverLabel.setText(coverLabel.getText() + ",");
		coverLabel.setText(coverLabel.getText() + block);
	}
	
	private void resetBlocksToCover(){
		change();
		coverLabel.setText("");
	}

	@Override
	public boolean save() {
		if (materialLabel.getText().length() > 0){
			StructureElement structure = new StructureElement();
			structure.name = name;
			
			GroundStructureResource base = new GroundStructureResource();
			base.block = materialLabel.getText();
			Set<String> blocks = new HashSet<String>();
			for (String block : coverLabel.getText().split(",")) blocks.add(block);
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
