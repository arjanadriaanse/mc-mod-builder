package twintro.minecraft.modbuilder.editor.interfaces.editors;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
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

import twintro.minecraft.modbuilder.editor.interfaces.activitypanels.StructureActivityPanel;
import twintro.minecraft.modbuilder.editor.interfaces.choosewindows.ObjectRunnable;
import twintro.minecraft.modbuilder.editor.interfaces.helperclasses.WindowClosingVerifierListener;
import twintro.minecraft.modbuilder.editor.interfaces.helperclasses.WindowClosingVerifierUser;

public class OreStructureEditor extends WindowClosingVerifierUser {
	private JPanel contentPane;
	private JSpinner maxYspinner;
	private JSpinner minYspinner;
	private JSpinner veinSizeSpinner;
	private JComboBox dimensionComboBox;
	private JButton coverBlockButton;
	private JLabel coverBlockLabel;

	private String name;
	private ObjectRunnable runnable;
	private ObjectRunnable closeHandler;

	public OreStructureEditor(String name, ObjectRunnable runnable, ObjectRunnable closeHandler) {
		this.name = name;
		this.runnable = runnable;
		this.closeHandler = closeHandler;
		
		setBounds(100, 100, 390, 400);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowClosingVerifierListener());
		setTitle("Edit structure: " + this.name);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.SOUTH);
		panel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		
		JButton btnSaveItem = new JButton("Save");
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

	private void chooseBlocks() {
		
	}

	private void saveStructure() {
		
	}

	@Override
	public boolean save() {
		return false;
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
