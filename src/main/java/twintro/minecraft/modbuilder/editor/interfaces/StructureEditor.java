package twintro.minecraft.modbuilder.editor.interfaces;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import twintro.minecraft.modbuilder.editor.Editor;

import javax.swing.JToggleButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JSpinner;
import javax.swing.SwingConstants;

public class StructureEditor extends JFrame {

	private JPanel contentPane;
	private String name;
	private StructureActivityPanel main;
	private JComboBox dimensionComboBox;
	private JPanel panel_8;
	int structureTypeIndex;
	private JSpinner veinSizeSpinner;
	private JSpinner minYspinner;
	private JSpinner maxYspinner;
	private JLabel coverBlockLabel;
	private JButton coverBlockButton;
	private JLabel onblockslabel;
	private JButton onBlocksButton;
	private JSpinner amountperchunkSpinner;

	
	public StructureEditor(String nameNew, StructureActivityPanel main, Integer typeHelper) {
		this.name = nameNew;
		this.main = main;
		structureTypeIndex = typeHelper;
		
		panel_8 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_8.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		
		JButton groundCoverEditingButton = new JButton("Ground Cover");
		groundCoverEditingButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JPanel passpanel = constructorCommons();
				constructorGround(passpanel);
				structureTypeIndex = 0;
				repaint();
			}
		});
		panel_8.add(groundCoverEditingButton);
		
		JButton oreEditingButton = new JButton("Ores");
		oreEditingButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPanel passpanel = constructorCommons();
				constructorOre(passpanel);
				structureTypeIndex = 1;
				repaint();
			}
		});
		panel_8.add(oreEditingButton);

		
		JPanel passpanel = this.constructorCommons();
		if (structureTypeIndex == 0){
			this.constructorGround(passpanel);
		}else if (structureTypeIndex == 1){
			this.constructorOre(passpanel);
		}
		setVisible(true);
	}
	
	private void constructorOre(JPanel panel_6) {
		
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
		panel_7.add(coverBlockButton, BorderLayout.EAST);
		
		JPanel panel_5 = new JPanel();
		panel_3.add(panel_5);
		panel_5.setLayout(new BorderLayout(0, 0));
		
		dimensionComboBox = new JComboBox();
		panel_5.add(dimensionComboBox, BorderLayout.NORTH);
		dimensionComboBox.setModel(new DefaultComboBoxModel(new String[] {"Overworld", "Nether", "End"}));
		
		JPanel panel = new JPanel();
		panel_3.add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		veinSizeSpinner = new JSpinner();
		panel.add(veinSizeSpinner, BorderLayout.CENTER);
		
		JPanel panel_4 = new JPanel();
		panel_3.add(panel_4);
		panel_4.setLayout(new BorderLayout(0, 0));
		
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

	private void constructorGround(JPanel panel_6) {
		
		JPanel panel_1 = new JPanel();
		panel_6.add(panel_1);
		panel_1.setLayout(new BorderLayout(5, 0));
		
		JPanel panel_2 = new JPanel();
		panel_1.add(panel_2, BorderLayout.WEST);
		panel_2.setLayout(new GridLayout(0, 1, 0, 5));
		
		JLabel lblNewLabel_16 = new JLabel("");
		panel_2.add(lblNewLabel_16);
		
		JLabel lblAsdasdasd = new JLabel("Cover block");
		panel_2.add(lblAsdasdasd);
		
		JLabel lblBlocksToCover = new JLabel("Blocks to cover");
		panel_2.add(lblBlocksToCover);
		
		JLabel lblDimension = new JLabel("Dimension");
		panel_2.add(lblDimension);
		
		JLabel lblAmount = new JLabel("Amount ");
		lblAmount.setToolTipText("sets the amount to generate on a chunk");
		panel_2.add(lblAmount);
				
		JPanel panel_3 = new JPanel();
		panel_1.add(panel_3, BorderLayout.CENTER);
		panel_3.setLayout(new GridLayout(0, 1, 0, 5));
		
		JLabel lblNewLabel_15 = new JLabel("Generic item options:");
		panel_3.add(lblNewLabel_15);
		
		JPanel panel_7 = new JPanel();
		panel_3.add(panel_7);
		panel_7.setLayout(new BorderLayout(0, 0));
		
		coverBlockLabel = new JLabel("");
		panel_7.add(coverBlockLabel, BorderLayout.CENTER);
		
		coverBlockButton = new JButton("Choose");
		panel_7.add(coverBlockButton, BorderLayout.EAST);
		
		JPanel panel = new JPanel();
		panel_3.add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		onblockslabel = new JLabel("");
		panel.add(onblockslabel, BorderLayout.CENTER);
		
		onBlocksButton = new JButton("Choose");
		panel.add(onBlocksButton, BorderLayout.EAST);
		
		JPanel panel_5 = new JPanel();
		panel_3.add(panel_5);
		panel_5.setLayout(new BorderLayout(0, 0));
		
		dimensionComboBox = new JComboBox();
		panel_5.add(dimensionComboBox, BorderLayout.NORTH);
		dimensionComboBox.setModel(new DefaultComboBoxModel(new String[] {"Overworld", "Nether", "End"}));
		
		JPanel panel_4 = new JPanel();
		panel_3.add(panel_4);
		panel_4.setLayout(new BorderLayout(0, 0));
		
		amountperchunkSpinner = new JSpinner();
		panel_4.add(amountperchunkSpinner, BorderLayout.CENTER);
		
	}

	private JPanel constructorCommons(){
		getContentPane().removeAll();
		
		setBounds(100, 100, 390, 400);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Edit structure: " + name);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.SOUTH);
		panel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		
		JButton btnRename = new JButton("Rename");
		btnRename.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String nameNew2 = JOptionPane.showInputDialog("Item name:");
				StructureEditor temp = main.openEditors.get(name);
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

		panel_6.add(panel_8, BorderLayout.NORTH);
	
		return panel_6;		
	}
	
	private void cancel(){
		this.dispose();
	}


	protected void saveStructure() {
		// TODO Opslaan naar json
		
	}
}
