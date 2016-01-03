package twintro.minecraft.modbuilder.editor.interfaces;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JSpinner;
import javax.swing.JCheckBox;
import javax.swing.SpinnerNumberModel;

import twintro.minecraft.modbuilder.data.resources.TabResource;
import twintro.minecraft.modbuilder.data.resources.items.BaseItemResource;
import twintro.minecraft.modbuilder.data.resources.items.ItemResource;
import twintro.minecraft.modbuilder.data.resources.items.ItemType;
import twintro.minecraft.modbuilder.data.resources.models.ItemModelResource;
import twintro.minecraft.modbuilder.data.resources.models.ItemModelResource.Display;
import twintro.minecraft.modbuilder.editor.resources.ItemElement;
import javax.swing.JRadioButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import javafx.scene.paint.Color;

public class NewItemEditor extends JFrame implements TextureRunnable {
	private JLabel textureLabel;
	private JSpinner stackSizeSpinner;
	private JCheckBox containerCheckbox;
	private JLabel containerLabel;
	private JCheckBox burntimeCheckbox;
	private JSpinner burntimeSpinner;
	private JLabel tabsLabel;
	private JButton chooseContainerButton;
	private String name;
	private ItemsActivityPanel main;
	private boolean textureChooserIsOpen = false;
	JRadioButton radioItemButton, radioFoodButton, radioToolButton;
	private JTextField damageTextField;
	private JButton repairThingButton;
	private JCheckBox useRepairThingsBox;
	private JSpinner enchantabillitySpinner;
	private JButton blockAddButton;
	private JSpinner harvestLevelSpinner;
	private JSpinner efficiencySpinner;
	private JSpinner durabillitySpinner;
	private JTextField saturationTextField;
	private JCheckBox wolfCheckBox;
	private JCheckBox edibleCheckBox;
	private JSpinner effectIndexSpinner;
	private JSpinner potionIDSpinner;
	private JSpinner durationSpinner;
	private JSpinner amplifierSpinner;
	private JLabel lblNewLabel_17;
	private JLabel lblPotionid;
	private JLabel lblDuration;
	private JLabel lblNewLabel_18;
	private JSpinner amountSpinner;
	private Map<Integer, Integer[]> potionEffectMap = new HashMap<Integer, Integer[]>();
	private JPanel panel_28;
	private JButton potionSaveButton;;
	/**
	 * @wbp.parser.constructor
	 */
	public NewItemEditor(String nameNew, ItemsActivityPanel main, int iTypeIndex) {
		this.name = nameNew;
		this.main = main;
		
		setBounds(100, 100, 483, 758);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Edit Item: " + name);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.SOUTH);
		panel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		
		JButton btnRename = new JButton("Rename");
		btnRename.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String nameNew2 = JOptionPane.showInputDialog("Item name:");
				name = nameNew2;
				setTitle("Edit Item: " + name);
			}
		});
		panel.add(btnRename);
		
		JButton btnSaveItem = new JButton("Save Item");
		panel.add(btnSaveItem);
		btnSaveItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				saveItem();
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
		panel_1.setLayout(new BorderLayout(5, 0));
		
		JPanel panel_2 = new JPanel();
		panel_1.add(panel_2, BorderLayout.WEST);
		panel_2.setLayout(new GridLayout(0, 1, 0, 5));
		
		JLabel lblNewLabel_16 = new JLabel("");
		panel_2.add(lblNewLabel_16);
		
		JLabel lblModel = new JLabel("Texture");
		panel_2.add(lblModel);
		
		JLabel lblLabel = new JLabel("Maximum stack size");
		panel_2.add(lblLabel);
		
		JLabel lblLabel_1 = new JLabel("Container");
		lblLabel_1.setToolTipText("When you use the item to craft, this will be left behind.\r\nFor example, milk buckets will leave a bucket behind.");
		panel_2.add(lblLabel_1);
		
		JLabel lblLabel_2 = new JLabel("Burn time");
		lblLabel_2.setToolTipText("How many items will be cooked when you use the item as fuel.");
		panel_2.add(lblLabel_2);
		
		JLabel lblLabel_3 = new JLabel("Creative tabs");
		panel_2.add(lblLabel_3);
		
		JLabel lblNewLabel_11 = new JLabel("");
		panel_2.add(lblNewLabel_11);
		
		JLabel lblNewLabel_4 = new JLabel("Durabillity");
		panel_2.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("Efficiency");
		lblNewLabel_5.setToolTipText("How fast it will destroy blocks");
		panel_2.add(lblNewLabel_5);
		
		JLabel lblNewLabel_6 = new JLabel("Damage");
		panel_2.add(lblNewLabel_6);
		
		JLabel lblNewLabel_7 = new JLabel("Harvestlevel");
		lblNewLabel_7.setToolTipText("The level of hardest, 0=wood, 2=iron, etc.");
		panel_2.add(lblNewLabel_7);
		
		JLabel lblNewLabel_8 = new JLabel("Affected blocks");
		panel_2.add(lblNewLabel_8);
		
		JLabel lblNewLabel_9 = new JLabel("Enchantabillity");
		panel_2.add(lblNewLabel_9);
		
		JLabel lblNewLabel_10 = new JLabel("Repair item/block");
		panel_2.add(lblNewLabel_10);
		
		JLabel lblNewLabel_12 = new JLabel("");
		panel_2.add(lblNewLabel_12);
		
		JLabel lblNewLabel_19 = new JLabel("Amount");
		lblNewLabel_19.setToolTipText("Amount of hunger it fills");
		panel_2.add(lblNewLabel_19);
		
		JLabel label_1 = new JLabel("Saturation");
		label_1.setToolTipText("How long the food will keep your hunger stilled");
		panel_2.add(label_1);
		
		JLabel label_2 = new JLabel("");
		panel_2.add(label_2);
		
		JLabel label_3 = new JLabel("Potion effects");
		panel_2.add(label_3);
		
		JLabel lblNewLabel_13 = new JLabel("");
		panel_2.add(lblNewLabel_13);
		
		JLabel label_4 = new JLabel("");
		panel_2.add(label_4);
		
		JPanel panel_3 = new JPanel();
		panel_1.add(panel_3, BorderLayout.CENTER);
		panel_3.setLayout(new GridLayout(0, 1, 0, 5));
		
		JLabel lblNewLabel_15 = new JLabel("Generic item options:");
		panel_3.add(lblNewLabel_15);
		
		JPanel panel_7 = new JPanel();
		panel_3.add(panel_7);
		panel_7.setLayout(new BorderLayout(0, 0));
		
		JButton btnChoose = new JButton("Choose");
		panel_7.add(btnChoose, BorderLayout.EAST);
		btnChoose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				chooseTexture();
			}
		});
		
		textureLabel = new JLabel("");
		panel_7.add(textureLabel, BorderLayout.CENTER);
		
		stackSizeSpinner = new JSpinner();
		stackSizeSpinner.setModel(new SpinnerNumberModel(64, 1, 64, 1));
		panel_3.add(stackSizeSpinner);
		
		JPanel panel_6 = new JPanel();
		panel_6.setToolTipText("When you use the item to craft, this will be left behind.\r\nFor example, milk buckets will leave a bucket behind.");
		panel_3.add(panel_6);
		panel_6.setLayout(new BorderLayout(0, 0));
		
		containerCheckbox = new JCheckBox("Use");
		containerCheckbox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chooseContainerButton.setEnabled(containerCheckbox.isSelected());
			}
		});
		panel_6.add(containerCheckbox, BorderLayout.EAST);
		
		JPanel panel_8 = new JPanel();
		panel_6.add(panel_8, BorderLayout.CENTER);
		panel_8.setLayout(new BorderLayout(0, 0));
		
		chooseContainerButton = new JButton("Choose");
		panel_8.add(chooseContainerButton, BorderLayout.EAST);
		chooseContainerButton.setEnabled(false);
		chooseContainerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				chooseContainer();
			}
		});
		
		containerLabel = new JLabel("");
		panel_8.add(containerLabel, BorderLayout.CENTER);
		
		JPanel panel_5 = new JPanel();
		panel_5.setToolTipText("How many items will be cooked when you use the item as fuel.");
		panel_3.add(panel_5);
		panel_5.setLayout(new BorderLayout(0, 0));
		
		burntimeCheckbox = new JCheckBox("Use");
		burntimeCheckbox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				burntimeSpinner.setEnabled(burntimeCheckbox.isSelected());
			}
		});
		panel_5.add(burntimeCheckbox, BorderLayout.EAST);
		
		burntimeSpinner = new JSpinner();
		burntimeSpinner.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		burntimeSpinner.setEnabled(false);
		panel_5.add(burntimeSpinner, BorderLayout.CENTER);
		
		JPanel panel_4 = new JPanel();
		panel_3.add(panel_4);
		panel_4.setLayout(new BorderLayout(0, 0));
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {
				"Add", "block", "decorations", "redstone", "transport", "misc", 
				"food", "tools", "combat", "brewing", "materials", "inventory"}));
		panel_4.add(comboBox, BorderLayout.EAST);
		comboBox.addItemListener(new ItemListener() {
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
		
		JPanel panel_9 = new JPanel();
		panel_4.add(panel_9, BorderLayout.CENTER);
		panel_9.setLayout(new BorderLayout(0, 0));
		
		JButton btnReset = new JButton("Reset");
		panel_9.add(btnReset, BorderLayout.EAST);
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				resetTab();
			}
		});
		
		tabsLabel = new JLabel("");
		panel_9.add(tabsLabel, BorderLayout.CENTER);
		
		JLabel label = new JLabel("Tool options:");
		panel_3.add(label);
		
		JPanel panel_14 = new JPanel();
		panel_3.add(panel_14);
		panel_14.setLayout(new BorderLayout(0, 0));
		
		durabillitySpinner = new JSpinner();
		
		panel_14.add(durabillitySpinner);
		
		
		JPanel panel_15 = new JPanel();
		panel_3.add(panel_15);
		panel_15.setLayout(new BorderLayout(0, 0));
		
		efficiencySpinner = new JSpinner();
		panel_15.add(efficiencySpinner);
		
		JPanel panel_16 = new JPanel();
		panel_3.add(panel_16);
		panel_16.setLayout(new BorderLayout(0, 0));
		
		damageTextField = new JTextField();
		damageTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_16.add(damageTextField);
		damageTextField.setColumns(10);
		
		JPanel panel_17 = new JPanel();
		panel_3.add(panel_17);
		panel_17.setLayout(new BorderLayout(0, 0));
		
		harvestLevelSpinner = new JSpinner();
		panel_17.add(harvestLevelSpinner);
		
		JPanel panel_18 = new JPanel();
		panel_3.add(panel_18);
		panel_18.setLayout(new BorderLayout(0, 0));
		
		JLabel blocksLabel = new JLabel("");
		panel_18.add(blocksLabel);
		
		blockAddButton = new JButton("Add blocks");
		blockAddButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		panel_18.add(blockAddButton, BorderLayout.EAST);
		
		JPanel panel_19 = new JPanel();
		panel_3.add(panel_19);
		panel_19.setLayout(new BorderLayout(0, 0));
		
		enchantabillitySpinner = new JSpinner();
		panel_19.add(enchantabillitySpinner);
		
		JPanel panel_20 = new JPanel();
		panel_3.add(panel_20);
		panel_20.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_21 = new JPanel();
		panel_20.add(panel_21, BorderLayout.CENTER);
		panel_21.setLayout(new BorderLayout(0, 0));
		
		JLabel repairThingLabel = new JLabel("");
		panel_21.add(repairThingLabel);
		
		repairThingButton = new JButton("Choose");
		panel_21.add(repairThingButton, BorderLayout.EAST);
		
		useRepairThingsBox = new JCheckBox("Use");
		panel_20.add(useRepairThingsBox, BorderLayout.EAST);
		
		JLabel lblNewLabel_14 = new JLabel("Food options:");
		panel_3.add(lblNewLabel_14);
		
		JPanel panel_10 = new JPanel();
		panel_1.add(panel_10, BorderLayout.NORTH);
		panel_10.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblNewLabel_3 = new JLabel("Itemtype:");
		panel_10.add(lblNewLabel_3);
		
		JPanel panel_11 = new JPanel();
		panel_10.add(panel_11);
		
		JLabel lblNewLabel = new JLabel("regular item");
		panel_11.add(lblNewLabel);
	
		potionSaveButton = new JButton("Save potion effect");
		
		radioItemButton = new JRadioButton("");
		radioItemButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				radioFoodButton.setSelected(false);
				radioItemButton.setSelected(true);
				radioToolButton.setSelected(false);
				durabillitySpinner.setEnabled(radioToolButton.isSelected());
				efficiencySpinner.setEnabled(radioToolButton.isSelected());
				harvestLevelSpinner.setEnabled(radioToolButton.isSelected());
				damageTextField.setEnabled(radioToolButton.isSelected());
				enchantabillitySpinner.setEnabled(radioToolButton.isSelected());
				blockAddButton.setEnabled(radioToolButton.isSelected());
				useRepairThingsBox.setEnabled(radioToolButton.isSelected());
				repairThingButton.setEnabled(radioToolButton.isSelected());
		
				saturationTextField.setEnabled(radioFoodButton.isSelected());
				wolfCheckBox.setEnabled(radioFoodButton.isSelected());
				edibleCheckBox.setEnabled(radioFoodButton.isSelected());
				potionIDSpinner.setEnabled(radioFoodButton.isSelected());
				effectIndexSpinner.setEnabled(radioFoodButton.isSelected());
				durationSpinner.setEnabled(radioFoodButton.isSelected());
				amplifierSpinner.setEnabled(radioFoodButton.isSelected());
				lblNewLabel_17.setEnabled(radioFoodButton.isSelected());
				lblPotionid.setEnabled(radioFoodButton.isSelected());
				lblDuration.setEnabled(radioFoodButton.isSelected());
				lblNewLabel_18.setEnabled(radioFoodButton.isSelected());
				amountSpinner.setEnabled(radioFoodButton.isSelected());
				potionSaveButton.setEnabled(radioFoodButton.isSelected());
			}
		});
		panel_11.add(radioItemButton);
		
		JPanel panel_13 = new JPanel();
		panel_10.add(panel_13);
		
		JLabel lblNewLabel_1 = new JLabel("tool item");
		panel_13.add(lblNewLabel_1);
		
		radioToolButton = new JRadioButton("");
		radioToolButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				radioFoodButton.setSelected(false);
				radioToolButton.setSelected(true);
				radioItemButton.setSelected(false);
				durabillitySpinner.setEnabled(radioToolButton.isSelected());
				efficiencySpinner.setEnabled(radioToolButton.isSelected());
				harvestLevelSpinner.setEnabled(radioToolButton.isSelected());
				damageTextField.setEnabled(radioToolButton.isSelected());
				enchantabillitySpinner.setEnabled(radioToolButton.isSelected());
				blockAddButton.setEnabled(radioToolButton.isSelected());
				useRepairThingsBox.setEnabled(radioToolButton.isSelected());
				repairThingButton.setEnabled(radioToolButton.isSelected());
								
				saturationTextField.setEnabled(radioFoodButton.isSelected());
				wolfCheckBox.setEnabled(radioFoodButton.isSelected());
				edibleCheckBox.setEnabled(radioFoodButton.isSelected());
				potionIDSpinner.setEnabled(radioFoodButton.isSelected());
				effectIndexSpinner.setEnabled(radioFoodButton.isSelected());
				durationSpinner.setEnabled(radioFoodButton.isSelected());
				amplifierSpinner.setEnabled(radioFoodButton.isSelected());
				lblNewLabel_17.setEnabled(radioFoodButton.isSelected());
				lblPotionid.setEnabled(radioFoodButton.isSelected());
				lblDuration.setEnabled(radioFoodButton.isSelected());
				lblNewLabel_18.setEnabled(radioFoodButton.isSelected());
				amountSpinner.setEnabled(radioFoodButton.isSelected());
				potionSaveButton.setEnabled(radioFoodButton.isSelected());
			}
		});
		panel_13.add(radioToolButton);
		
		JPanel panel_12 = new JPanel();
		panel_10.add(panel_12);
		
		JLabel lblNewLabel_2 = new JLabel("food item");
		panel_12.add(lblNewLabel_2);
		
		JPanel panel_32 = new JPanel();
		panel_32.setToolTipText("Amount of hunger it fills");
		panel_3.add(panel_32);
		panel_32.setLayout(new BorderLayout(0, 0));
		
		amountSpinner = new JSpinner();
		panel_32.add(amountSpinner);
		
		JPanel panel_25 = new JPanel();
		panel_3.add(panel_25);
		panel_25.setLayout(new BorderLayout(0, 0));
		
		saturationTextField = new JTextField();
		saturationTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_25.add(saturationTextField);
		saturationTextField.setColumns(10);
		
		JPanel panel_24 = new JPanel();
		panel_3.add(panel_24);
		panel_24.setLayout(new GridLayout(0, 2, 0, 0));
		
		JPanel panel_26 = new JPanel();
		panel_24.add(panel_26);
		panel_26.setLayout(new BorderLayout(0, 0));
		
		wolfCheckBox = new JCheckBox("Feed to wolves");
		panel_26.add(wolfCheckBox);
		
		JPanel panel_27 = new JPanel();
		panel_24.add(panel_27);
		panel_27.setLayout(new BorderLayout(0, 0));
		
		edibleCheckBox = new JCheckBox("Always edible");
		edibleCheckBox.setToolTipText("Can consume this item even when hunger is full");
		panel_27.add(edibleCheckBox, BorderLayout.NORTH);
		
		JPanel panel_23 = new JPanel();
		panel_3.add(panel_23);
		panel_23.setLayout(new GridLayout(0, 2, 5, 0));
		
		panel_28 = new JPanel();
		panel_23.add(panel_28);
		panel_28.setLayout(new GridLayout(0, 2, 0, 0));
		
		lblNewLabel_17 = new JLabel("Editor index");
		panel_28.add(lblNewLabel_17);
		
		effectIndexSpinner = new JSpinner();
		effectIndexSpinner.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent arg0) {
				if (potionEffectMap.containsKey((Integer) effectIndexSpinner.getValue())){
					potionIDSpinner.setValue(potionEffectMap.get((Integer)effectIndexSpinner.getValue())[0]);
					durationSpinner.setValue(potionEffectMap.get((Integer)effectIndexSpinner.getValue())[1]);
					amplifierSpinner.setValue(potionEffectMap.get((Integer)effectIndexSpinner.getValue())[2]);
					java.awt.Color background = java.awt.Color.green;
					panel_28.setBackground(background);
				}
				else {
					panel_28.setBackground(java.awt.Color.lightGray);
				}
			}
		});
		panel_28.add(effectIndexSpinner);
		panel_28.setBackground(java.awt.Color.lightGray);
		
		JPanel panel_29 = new JPanel();
		panel_23.add(panel_29);
		panel_29.setLayout(new GridLayout(0, 2, 0, 0));
		
		lblPotionid = new JLabel("PotionID");
		panel_29.add(lblPotionid);
		
		potionIDSpinner = new JSpinner();
		panel_29.add(potionIDSpinner);
		
		JPanel panel_22 = new JPanel();
		panel_3.add(panel_22);
		panel_22.setLayout(new GridLayout(0, 2, 5, 0));
		
		JPanel panel_30 = new JPanel();
		panel_22.add(panel_30);
		panel_30.setLayout(new GridLayout(0, 2, 0, 0));
		
		lblDuration = new JLabel("Duration");
		lblDuration.setToolTipText("In ticks");
		panel_30.add(lblDuration);
		
		durationSpinner = new JSpinner();
		panel_30.add(durationSpinner);
		
		JPanel panel_31 = new JPanel();
		panel_22.add(panel_31);
		panel_31.setLayout(new GridLayout(1, 0, 0, 0));
		
		lblNewLabel_18 = new JLabel("Amplifier");
		lblNewLabel_18.setToolTipText("Amplifies the effect, 0 means 1x, 1 means 2x.");
		panel_31.add(lblNewLabel_18);
		
		amplifierSpinner = new JSpinner();
		amplifierSpinner.setToolTipText("Amplifies the effect, 0 means 1x, 1 means 2x.");
		panel_31.add(amplifierSpinner);
		
		
		radioFoodButton = new JRadioButton("");
		radioFoodButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				radioFoodButton.setSelected(true);
				radioItemButton.setSelected(false);
				radioToolButton.setSelected(false);
				durabillitySpinner.setEnabled(radioToolButton.isSelected());
				efficiencySpinner.setEnabled(radioToolButton.isSelected());
				harvestLevelSpinner.setEnabled(radioToolButton.isSelected());
				damageTextField.setEnabled(radioToolButton.isSelected());
				enchantabillitySpinner.setEnabled(radioToolButton.isSelected());
				blockAddButton.setEnabled(radioToolButton.isSelected());
				useRepairThingsBox.setEnabled(radioToolButton.isSelected());
				repairThingButton.setEnabled(radioToolButton.isSelected());

				saturationTextField.setEnabled(radioFoodButton.isSelected());
				wolfCheckBox.setEnabled(radioFoodButton.isSelected());
				edibleCheckBox.setEnabled(radioFoodButton.isSelected());
				potionIDSpinner.setEnabled(radioFoodButton.isSelected());
				effectIndexSpinner.setEnabled(radioFoodButton.isSelected());
				durationSpinner.setEnabled(radioFoodButton.isSelected());
				amplifierSpinner.setEnabled(radioFoodButton.isSelected());				
				lblNewLabel_17.setEnabled(radioFoodButton.isSelected());
				lblPotionid.setEnabled(radioFoodButton.isSelected());
				lblDuration.setEnabled(radioFoodButton.isSelected());
				lblNewLabel_18.setEnabled(radioFoodButton.isSelected());
				amountSpinner.setEnabled(radioFoodButton.isSelected());
				potionSaveButton.setEnabled(radioFoodButton.isSelected());
			}
		});
		panel_12.add(radioFoodButton);
		if (iTypeIndex ==1) radioToolButton.setSelected(true);
		if (iTypeIndex ==2) radioFoodButton.setSelected(true);
		if (iTypeIndex ==0) radioItemButton.setSelected(true);
		
		durabillitySpinner.setEnabled(radioToolButton.isSelected());
		efficiencySpinner.setEnabled(radioToolButton.isSelected());
		harvestLevelSpinner.setEnabled(radioToolButton.isSelected());
		damageTextField.setEnabled(radioToolButton.isSelected());
		enchantabillitySpinner.setEnabled(radioToolButton.isSelected());
		blockAddButton.setEnabled(radioToolButton.isSelected());
			
		saturationTextField.setEnabled(radioFoodButton.isSelected());
		wolfCheckBox.setEnabled(radioFoodButton.isSelected());
		edibleCheckBox.setEnabled(radioFoodButton.isSelected());
		potionIDSpinner.setEnabled(radioFoodButton.isSelected());
		effectIndexSpinner.setEnabled(radioFoodButton.isSelected());
		durationSpinner.setEnabled(radioFoodButton.isSelected());
		amplifierSpinner.setEnabled(radioFoodButton.isSelected());
		lblNewLabel_17.setEnabled(radioFoodButton.isSelected());
		lblPotionid.setEnabled(radioFoodButton.isSelected());
		lblDuration.setEnabled(radioFoodButton.isSelected());
		lblNewLabel_18.setEnabled(radioFoodButton.isSelected());
		amountSpinner.setEnabled(radioFoodButton.isSelected());
		potionSaveButton.setEnabled(radioFoodButton.isSelected());
		
		JPanel panel_33 = new JPanel();
		panel_3.add(panel_33);
		panel_33.setLayout(new BorderLayout(0, 0));
		
		potionSaveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				savePotion((Integer) effectIndexSpinner.getValue(), (Integer)potionIDSpinner.getValue(),(Integer)durationSpinner.getValue(),(Integer) amplifierSpinner.getValue());
			}
		});
		panel_33.add(potionSaveButton, BorderLayout.EAST);
		setVisible(true);
	}
	
	private void savePotion(int a, int b, int c, int d){
		if (b < 24 && b > 0 && c >= 0 && d >= 0){
			Integer[] output = {b,c,d};
			potionEffectMap.put(a, output);
			panel_28.setBackground(java.awt.Color.green);
		}
		else {
			JOptionPane.showMessageDialog(this, "Invalid potion input.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public NewItemEditor(ItemsActivityPanel main, ItemElement item, int iTypeIndex, boolean constructorhelper){
		this(item.name, main, iTypeIndex);
		 
		if (item.itemModel.textures.containsKey("layer0")) {
			textureLabel.setText(item.itemModel.textures.get("layer0"));
			setIconImage(main.main.TexturePanel.elements.get(item.itemModel.textures.get("layer0").split(":")[1]).getImage());
		}
		if (item.item.stacksize != null)
			stackSizeSpinner.setValue(item.item.stacksize);
		if (item.item.container != null){
			containerLabel.setText(item.item.container);
			containerCheckbox.setSelected(true);
			chooseContainerButton.setEnabled(true);
		}
		if (item.item.burntime != null){
			burntimeSpinner.setValue(item.item.burntime);
			burntimeCheckbox.setSelected(true);
			burntimeSpinner.setEnabled(true);
		}
		for (TabResource s : ((ItemResource)item.item).tabs)
			selectTab(s.name());
	}

	private void resetTab(){
		tabsLabel.setText("");
	}
	
	private void selectTab(String tab){
		if (tabsLabel.getText().length() > 0) tabsLabel.setText(tabsLabel.getText() + ",");
		tabsLabel.setText(tabsLabel.getText() + tab);
	}
	
	private void chooseTexture(){
		if (!textureChooserIsOpen){
			new TextureChooseWindow(main.main.TexturePanel.elements, this);
			textureChooserIsOpen = true;
		}
	}
	
	private void chooseContainer(){
		
	}
	
	private void saveItem(){
		if (!textureChooserIsOpen && textureLabel.getText().length() > 0){
			ItemElement item = new ItemElement();
			item.name = name;
			
			ItemModelResource model = new ItemModelResource();
			model.parent = "builtin/generated";
			model.textures = new HashMap<String, String>();
			model.textures.put("layer0", textureLabel.getText());
			model.display = Display.regular();
			item.itemModel = model;
			
			ItemResource base = new ItemResource();
			base.model = "modbuilder:" + name;
			base.stacksize = (Integer) stackSizeSpinner.getValue();
			if (containerLabel.getText().length() > 0 && containerCheckbox.isSelected()) 
				base.container = containerLabel.getText();
			if (burntimeCheckbox.isSelected())
				base.burntime = (Integer) burntimeSpinner.getValue();
			base.tabs = new HashSet<TabResource>();
			for (String s : tabsLabel.getText().split(","))
				base.tabs.add(TabResource.valueOf(s));
			item.item = base;
			
			main.addItem(item);
		}
	}
	
	private void cancel(){
		this.dispose();
	}
	
	@Override
	public void dispose() {
		main.closeEditor(name);
		super.dispose();
	}

	@Override
	public void choose(String texture) {
		textureLabel.setText(texture);
		setIconImage(main.main.TexturePanel.elements.get(texture.split(":")[1]).getImage());
		textureChooserIsOpen = false;
	}
}
