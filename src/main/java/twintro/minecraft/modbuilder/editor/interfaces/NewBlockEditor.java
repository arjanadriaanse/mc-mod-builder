package twintro.minecraft.modbuilder.editor.interfaces;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.AbstractButton;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class NewBlockEditor extends JFrame {

	private String name;
	private JPanel contentPane;
	private JLabel modelLabel;
	private JTextField slipperinessTextField;
	private JTextField hardnessTextField;
	private JTextField resistanceTextField;
	private JLabel lblCreativeTab;
	private BlocksActivityPanel parent;
	private JLabel creativeTabLabel;
	private boolean modelChooserIsOpen = false;

	public NewBlockEditor(String newName, BlocksActivityPanel parent, Integer type) {
		BorderLayout borderLayout = (BorderLayout) getContentPane().getLayout();
		borderLayout.setVgap(5);
		borderLayout.setHgap(5);
		this.parent = parent;
		this.name = newName;
		
		setBounds(100, 100, 372, 447);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Edit block: " + name);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.SOUTH);
		panel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		
		JButton btnRename = new JButton("Rename");
		btnRename.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String nameNew2 = JOptionPane.showInputDialog("Block name:");
				name = nameNew2;
				setTitle("Edit block: " + name);
			}
		});
		panel.add(btnRename);
		
		JButton btnSaveBlock = new JButton("Save Block");
		panel.add(btnSaveBlock);
		btnSaveBlock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				saveBlock();
			}
		});
		
		JButton btnCancel = new JButton("Cancel");
		panel.add(btnCancel);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cancel();
			}
		});
		
		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.NORTH);
		panel_1.setLayout(new BorderLayout(5, 5));
		
		JPanel panel_2 = new JPanel();
		panel_1.add(panel_2, BorderLayout.WEST);
		panel_2.setLayout(new GridLayout(0, 1, 0, 5));
		
		JLabel lblNewLabel_16 = new JLabel("");
		panel_2.add(lblNewLabel_16);
		
		JLabel lblModel = new JLabel("Model");
		panel_2.add(lblModel);
		
		lblCreativeTab = new JLabel("Creative tab");
		panel_2.add(lblCreativeTab);
		
		JLabel lblLightness = new JLabel("Lightness");
		lblLightness.setToolTipText("Amount of light the block gives off (0-15)");
		panel_2.add(lblLightness);
		
		JLabel lblNewLabel = new JLabel("Opacity");
		panel_2.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Slipperiness");
		lblNewLabel_1.setToolTipText("Reccomended values below 1");
		panel_2.add(lblNewLabel_1);
		
		JLabel lblHardness = new JLabel("Hardness");
		panel_2.add(lblHardness);
		
		JLabel lblResistance = new JLabel("Resistance");
		lblResistance.setToolTipText("resistance to explosions");
		panel_2.add(lblResistance);
		
		JLabel lblHarvesttype = new JLabel("Harvesttype");
		panel_2.add(lblHarvesttype);
		
		JLabel lblUnbreakable = new JLabel("Unbreakable");
		panel_2.add(lblUnbreakable);
		
		JLabel lblHarvestlevel = new JLabel("Harvestlevel");
		lblHarvestlevel.setToolTipText("(0=wood, 1=stone, etc)");
		panel_2.add(lblHarvestlevel);
		
		JLabel lblBurntime = new JLabel("Burntime");
		panel_2.add(lblBurntime);
		
			
		JPanel panel_3 = new JPanel();
		panel_1.add(panel_3, BorderLayout.CENTER);
		panel_3.setLayout(new GridLayout(0, 1, 0, 5));
		
		JLabel lblNewLabel_15 = new JLabel("Block options:");
		panel_3.add(lblNewLabel_15);
		
		JPanel panel_7 = new JPanel();
		panel_3.add(panel_7);
		panel_7.setLayout(new BorderLayout(0, 0));
		
		JButton btnChoose = new JButton("Choose");
		panel_7.add(btnChoose, BorderLayout.EAST);
		btnChoose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				chooseModel();
			}
		});
		
		modelLabel = new JLabel("");
		modelLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_7.add(modelLabel, BorderLayout.CENTER);
		
		JPanel panel_14 = new JPanel();
		panel_3.add(panel_14);
		panel_14.setLayout(new BorderLayout(0, 0));
		

		JComboBox tabComboBox = new JComboBox();
		tabComboBox.setModel(new DefaultComboBoxModel(new String[] {
				"Add", "block", "decorations", "redstone", "transport", "misc", 
				"food", "tools", "combat", "brewing", "materials", "inventory"}));
		tabComboBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == e.SELECTED){
					if (e.getItem() != "Add"){
						selectTab((String) e.getItem());
						((JComboBox) e.getSource()).setSelectedIndex(0);
					}
				}
			}
		});
		
		panel_14.add(tabComboBox, BorderLayout.EAST);
		
		JPanel panel_15 = new JPanel();
		panel_14.add(panel_15, BorderLayout.CENTER);
		panel_15.setLayout(new BorderLayout(0, 0));
		
		JButton button = new JButton("Reset");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resetTab();
			}
		});
		panel_15.add(button, BorderLayout.EAST);
		
		creativeTabLabel = new JLabel("");
		panel_15.add(creativeTabLabel, BorderLayout.CENTER);
		
		JPanel panel_4 = new JPanel();
		panel_3.add(panel_4);
		panel_4.setLayout(new BorderLayout(0, 0));
		
		JSpinner lightnessSpinner = new JSpinner();
		panel_4.add(lightnessSpinner, BorderLayout.NORTH);
		
		JPanel panel_5 = new JPanel();
		panel_3.add(panel_5);
		panel_5.setLayout(new BorderLayout(0, 0));
		
		JSpinner opacitySpinner = new JSpinner();
		panel_5.add(opacitySpinner, BorderLayout.CENTER);
		
		JPanel panel_6 = new JPanel();
		panel_3.add(panel_6);
		panel_6.setLayout(new BorderLayout(0, 0));
		
		slipperinessTextField = new JTextField();
		slipperinessTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_6.add(slipperinessTextField, BorderLayout.CENTER);
		slipperinessTextField.setColumns(10);
		
		JPanel panel_8 = new JPanel();
		panel_3.add(panel_8);
		panel_8.setLayout(new BorderLayout(0, 0));
		
		hardnessTextField = new JTextField();
		hardnessTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_8.add(hardnessTextField, BorderLayout.CENTER);
		hardnessTextField.setColumns(10);
		
		JPanel panel_9 = new JPanel();
		panel_3.add(panel_9);
		panel_9.setLayout(new BorderLayout(0, 0));
		
		resistanceTextField = new JTextField();
		resistanceTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_9.add(resistanceTextField, BorderLayout.CENTER);
		resistanceTextField.setColumns(10);
		
		JPanel panel_11 = new JPanel();
		panel_3.add(panel_11);
		panel_11.setLayout(new BorderLayout(0, 0));
		
		JLabel harvestTypeLabel = new JLabel("");
		harvestTypeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_11.add(harvestTypeLabel, BorderLayout.CENTER);
		
		JComboBox harvestTypeComboBox = new JComboBox();
		panel_11.add(harvestTypeComboBox, BorderLayout.EAST);
		
		JPanel panel_10 = new JPanel();
		panel_3.add(panel_10);
		panel_10.setLayout(new BorderLayout(0, 0));
		
		JCheckBox unbreakableCheckBox = new JCheckBox("");
		unbreakableCheckBox.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_10.add(unbreakableCheckBox, BorderLayout.CENTER);
		
		JPanel panel_13 = new JPanel();
		panel_3.add(panel_13);
		panel_13.setLayout(new BorderLayout(0, 0));
		
		JSpinner harvestLevelSpinner = new JSpinner();
		panel_13.add(harvestLevelSpinner, BorderLayout.CENTER);
		
		JPanel panel_12 = new JPanel();
		panel_3.add(panel_12);
		panel_12.setLayout(new BorderLayout(0, 0));
		
		JSpinner burntimeSpinner = new JSpinner();
		panel_12.add(burntimeSpinner, BorderLayout.CENTER);
		
	
		setVisible(true);
	}

	protected void chooseModel() {
		if (!modelChooserIsOpen){
			new BlockModelChooseWindow(parent.main.TexturePanel.elements, this);
			modelChooserIsOpen = true;
		}
	}

	private void saveBlock(){
		//TODO saving blocks
	}

	private void selectTab(String tab){
		if (creativeTabLabel.getText().length() > 0) creativeTabLabel.setText(creativeTabLabel.getText() + ",");
		creativeTabLabel.setText(creativeTabLabel.getText() + tab);
	}
	
	private void resetTab(){
		creativeTabLabel.setText("");
	}
	

	private void cancel(){
		this.dispose();
	}
	
}

