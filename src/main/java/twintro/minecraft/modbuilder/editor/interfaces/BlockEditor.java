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

import twintro.minecraft.modbuilder.editor.interfaces.helperclasses.WindowClosingVerifierListener;
import twintro.minecraft.modbuilder.editor.resources.BlockElement;

public class BlockEditor extends JFrame {
	protected JPanel mainPanel;
	protected JPanel contentPane;
	protected JLabel labelCreativeTabs;
	protected JLabel modelLabel;
	protected JLabel creativeTabsLabel;
	
	protected boolean modelChooserIsOpen = false;
	protected String name;
	protected BlocksActivityPanel main;

	/**
	 * @wbp.parser.constructor
	 */
	public BlockEditor(String name, BlocksActivityPanel main) {
		this.name = name;
		this.main = main;

		setBounds(100, 100, 500, 500);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setTitle("Edit Block: " + this.name);
		addWindowListener(new WindowClosingVerifierListener());
		
		mainPanel = new JPanel();
		getContentPane().add(mainPanel, BorderLayout.SOUTH);
		mainPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		
		JButton btnRename = new JButton("Rename");
		btnRename.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rename();
			}
		});
		mainPanel.add(btnRename);
		
		JButton btnSaveBlock = new JButton("Save Block");
		mainPanel.add(btnSaveBlock);
		btnSaveBlock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				save();
			}
		});
		
		JButton btnCancel = new JButton("Cancel");
		mainPanel.add(btnCancel);
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
		
		JLabel lblModel = new JLabel("Model");
		panel_2.add(lblModel);
		
		labelCreativeTabs = new JLabel("Creative tabs");
		panel_2.add(labelCreativeTabs);
		
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
		
		JLabel lblHarvestlevel = new JLabel("Harvest level");
		lblHarvestlevel.setToolTipText("(0=wood, 1=stone, etc)");
		panel_2.add(lblHarvestlevel);
		
		JLabel lblBurntime = new JLabel("Burn time");
		panel_2.add(lblBurntime);
		
		JLabel lblHarvesttype = new JLabel("Harvest type");
		panel_2.add(lblHarvesttype);
		
		JLabel lblUnbreakable = new JLabel("Unbreakable");
		panel_2.add(lblUnbreakable);
		
		JPanel panel_3 = new JPanel();
		panel_1.add(panel_3, BorderLayout.CENTER);
		panel_3.setLayout(new GridLayout(0, 1, 0, 5));
		
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
		tabComboBox.setModel(new DefaultComboBoxModel(new String[] {"Add", "block", "decorations", "redstone", "transport", "misc", 
				"food", "tools", "combat", "brewing", "materials", "inventory"}));
		tabComboBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == e.SELECTED){
					if (e.getItem() != "Add"){
						creativeTabsChoose((String) e.getItem());
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
		
		creativeTabsLabel = new JLabel("");
		panel_15.add(creativeTabsLabel, BorderLayout.CENTER);
		
		JSpinner lightnessSpinner = new JSpinner();
		lightnessSpinner.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		panel_3.add(lightnessSpinner);
		
		JSpinner opacitySpinner = new JSpinner();
		panel_3.add(opacitySpinner);
		opacitySpinner.setModel(new SpinnerNumberModel(0, 0, 255, 1));
		
		JSpinner spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(new Float(0.6F), new Float(0), null, new Float(1)));
		panel_3.add(spinner);
		
		JSpinner spinner_1 = new JSpinner();
		spinner_1.setModel(new SpinnerNumberModel(new Float(0), new Float(0), null, new Float(1)));
		panel_3.add(spinner_1);
		
		JSpinner spinner_2 = new JSpinner();
		spinner_2.setModel(new SpinnerNumberModel(new Float(0), new Float(0), null, new Float(1)));
		panel_3.add(spinner_2);
		
		JSpinner harvestLevelSpinner = new JSpinner();
		harvestLevelSpinner.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		panel_3.add(harvestLevelSpinner);
		
		JSpinner burntimeSpinner = new JSpinner();
		burntimeSpinner.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		panel_3.add(burntimeSpinner);
		
		JComboBox harvestTypeComboBox = new JComboBox();
		harvestTypeComboBox.setModel(new DefaultComboBoxModel(new String[] {"HarvestType"}));
		panel_3.add(harvestTypeComboBox);
		
		JCheckBox unbreakableCheckBox = new JCheckBox("");
		panel_3.add(unbreakableCheckBox);
		unbreakableCheckBox.setHorizontalAlignment(SwingConstants.RIGHT);
		
		setVisible(true);
	}
	
	public BlockEditor(BlocksActivityPanel main, BlockElement block){
		this(block.name, main);
		
		//TODO read block and setup all values
	}
	
	private void cancel(){
		WindowClosingVerifierListener.close(this);
	}

	private void save(){
		if (!modelChooserIsOpen && modelLabel.getText().length() > 0){
			//TODO saving blocks
		}
	}
	
	protected void rename(){
		String name = JOptionPane.showInputDialog("Block name:");
		this.name = name;
		setTitle("Edit Block: " + this.name);
		//TODO refractor
	}
	
	protected void chooseModel() {
		if (!modelChooserIsOpen){
			new BlockModelChooseWindow(main.main.TexturePanel.elements, null);//TODO null is niet de bedoeling?? :) :O :D :P
			modelChooserIsOpen = true;
		}
	}

	private void creativeTabsChoose(String tab){
		if (creativeTabsLabel.getText().length() > 0) creativeTabsLabel.setText(creativeTabsLabel.getText() + ",");
		creativeTabsLabel.setText(creativeTabsLabel.getText() + tab);
	}
	
	private void resetTab(){
		creativeTabsLabel.setText("");
	}
	
	@Override
	public void dispose() {
		//TODO main.closeEditor(name);
		super.dispose();
	}
}

