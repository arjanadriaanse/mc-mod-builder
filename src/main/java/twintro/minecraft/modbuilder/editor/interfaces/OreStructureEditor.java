package twintro.minecraft.modbuilder.editor.interfaces;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

import twintro.minecraft.modbuilder.editor.interfaces.helperclasses.WindowClosingVerifierListener;

public class OreStructureEditor extends StructureEditor {

	private JPanel contentPane;
	private String name;
	private StructureActivityPanel main;
	private JSpinner maxYspinner;
	private JSpinner minYspinner;
	private JSpinner veinSizeSpinner;
	private JComboBox dimensionComboBox;
	private JButton coverBlockButton;
	private JLabel coverBlockLabel;


	public OreStructureEditor(String nameNew, StructureActivityPanel parent, Set<String> editorBlocks) {
		
		setBounds(100, 100, 390, 400);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowClosingVerifierListener());
		setTitle("Edit structure: " + name);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.SOUTH);
		panel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		
		JButton btnRename = new JButton("Rename");
		btnRename.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String nameNew2 = JOptionPane.showInputDialog("Item name:");
				GroundStructureEditor temp = main.openEditors.get(name);
				main.openEditors.remove(name);
				name = nameNew2;
				main.openEditors.put(name, temp);
				setTitle("Edit structure: " + name);
			}
		});
		panel.add(btnRename);
		
		JButton btnSaveItem = new JButton("Save Item");
		panel.add(btnSaveItem);
		btnSaveItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				saveStructure();
			}
		});
		
		JButton btnCancel = new JButton("Cancel");
		panel.add(btnCancel);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cancel();
			}
		});
		
		JPanel panel_6 = new JPanel();
		getContentPane().add(panel_6, BorderLayout.NORTH);
		panel_6.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		panel_6.add(panel_1);
		panel_1.setLayout(new BorderLayout(5, 0));
		
		JPanel panel_2 = new JPanel();
		panel_1.add(panel_2, BorderLayout.WEST);
		panel_2.setLayout(new GridLayout(0, 1, 0, 5));
		
		JLabel lblNewLabel_16 = new JLabel("");
		panel_2.add(lblNewLabel_16);
		
		JLabel lblAsdasdasd = new JLabel("Blocks to generate");
		panel_2.add(lblAsdasdasd);
		
		JLabel lblNewLabel = new JLabel("Dimension");
		panel_2.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Max vein size");
		panel_2.add(lblNewLabel_1);
		
		JLabel lblChanceToSpawn = new JLabel("Chance to spawn");
		panel_2.add(lblChanceToSpawn);
		
		JLabel lblMinimumHeight = new JLabel("Minimum height ");
		lblMinimumHeight.setToolTipText("Height from the bottom of the world the vein can be generated at");
		panel_2.add(lblMinimumHeight);
		
		JLabel lblMaximumHeight = new JLabel("Maximum height");
		lblMaximumHeight.setToolTipText("Highest the vein can spawn from the bottom of the world");
		panel_2.add(lblMaximumHeight);
		
		JPanel panel_3 = new JPanel();
		panel_1.add(panel_3, BorderLayout.CENTER);
		panel_3.setLayout(new GridLayout(0, 1, 0, 5));
		
		JLabel lblNewLabel_15 = new JLabel("Ore generation options:");
		panel_3.add(lblNewLabel_15);
		
		JPanel panel_7 = new JPanel();
		panel_3.add(panel_7);
		panel_7.setLayout(new BorderLayout(0, 0));
		
		coverBlockLabel = new JLabel("");
		panel_7.add(coverBlockLabel, BorderLayout.CENTER);
		
		coverBlockButton = new JButton("Choose");
		coverBlockButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chooseBlocks();
			}
		});
		panel_7.add(coverBlockButton, BorderLayout.EAST);
		
		JPanel panel_5 = new JPanel();
		panel_3.add(panel_5);
		panel_5.setLayout(new BorderLayout(0, 0));
		
		dimensionComboBox = new JComboBox();
		panel_5.add(dimensionComboBox, BorderLayout.NORTH);
		dimensionComboBox.setModel(new DefaultComboBoxModel(new String[] {"Overworld", "Nether", "End"}));
		
		JPanel panel1 = new JPanel();
		panel_3.add(panel1);
		panel1.setLayout(new BorderLayout(0, 0));
		
		veinSizeSpinner = new JSpinner();
		panel1.add(veinSizeSpinner, BorderLayout.CENTER);
		
		JPanel panel_4 = new JPanel();
		panel_3.add(panel_4);
		panel_4.setLayout(new BorderLayout(0, 0));
		
		JSpinner spinner = new JSpinner();
		panel_4.add(spinner, BorderLayout.CENTER);
		
		JPanel panelbleh_8 = new JPanel();
		panel_3.add(panelbleh_8);
		panelbleh_8.setLayout(new BorderLayout(0, 0));
		
		minYspinner = new JSpinner();
		panelbleh_8.add(minYspinner, BorderLayout.CENTER);
		
		JPanel panel_9 = new JPanel();
		panel_3.add(panel_9);
		panel_9.setLayout(new BorderLayout(0, 0));
		
		maxYspinner = new JSpinner();
		panel_9.add(maxYspinner, BorderLayout.NORTH);
		
	}

	protected void chooseBlocks() {
		// TODO Auto-generated method stub
		
	}

	protected void saveStructure() {
		// TODO Auto-generated method stub
		
	}

	protected void cancel() {
		// TODO Auto-generated method stub
		
	}

}
