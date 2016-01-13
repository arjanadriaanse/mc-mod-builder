package twintro.minecraft.modbuilder.editor.interfaces.editors;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridLayout;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import twintro.minecraft.modbuilder.data.resources.TabResource;
import twintro.minecraft.modbuilder.data.resources.items.BaseItemResource;
import twintro.minecraft.modbuilder.data.resources.items.ItemResource;
import twintro.minecraft.modbuilder.data.resources.models.ItemModelResource;
import twintro.minecraft.modbuilder.data.resources.models.ItemModelResource.Display;
import twintro.minecraft.modbuilder.editor.interfaces.activitypanels.ItemsActivityPanel;
import twintro.minecraft.modbuilder.editor.interfaces.choosewindows.MaterialChooseWindow;
import twintro.minecraft.modbuilder.editor.interfaces.choosewindows.MaterialRunnable;
import twintro.minecraft.modbuilder.editor.interfaces.choosewindows.TextureChooseWindow;
import twintro.minecraft.modbuilder.editor.interfaces.choosewindows.TextureRunnable;
import twintro.minecraft.modbuilder.editor.interfaces.helperclasses.WindowClosingVerifierListener;
import twintro.minecraft.modbuilder.editor.resources.ItemElement;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.HashSet;
import java.awt.event.ActionEvent;

public class RegularItemEditor extends JFrame implements TextureRunnable, MaterialRunnable {
	protected JPanel mainPanel;
	protected JPanel buttonPanel;
	protected JPanel texturePanel;
	protected JPanel labelPanel;
	protected JPanel interactionPanel;
	protected JPanel containerPanel;
	protected JPanel containerSubPanel;
	protected JPanel burntimePanel;
	protected JPanel creativeTabsPanel;
	protected JPanel creativeTabsSubPanel;
	protected JLabel labelTexture;
	protected JLabel labelMaxStackSize;
	protected JLabel labelCreativeTabs;
	protected JLabel labelContainer;
	protected JLabel labelBurnTime;
	protected JLabel textureLabel;
	protected JLabel creativeTabsLabel;
	protected JLabel containerLabel;
	protected JButton textureChooseButton;
	protected JButton creativeTabsResetButton;
	protected JButton containerChooseButton;
	protected JButton saveButton;
	protected JButton cancelButton;
	protected JSpinner maxStackSizeSpinner;
	protected JSpinner burntimeSpinner;
	protected JComboBox creativeTabsComboBox;
	protected JCheckBox containerCheckbox;
	protected JCheckBox burntimeCheckbox;

	protected boolean textureChooserIsOpen = false;
	protected boolean materialChooserIsOpen = false;
	protected String name;
	protected ItemsActivityPanel main;

	private static final String textureTooltip = "The texture of the item";
	private static final String maxStackSizeTooltip = "The maximum amount of the item there can be in a stack";
	private static final String creativeTabsTooltip = "The tabs in the creative menu where the item can be found";
	private static final String containerTooltip = "<html>The item that will be left behind when you use this item in a crafing recipe<br>"
				+ "For example, when you craft a cake, you use milk buckets in the crafting recipe "
				+ "and when you grab the cake, you will get your buckets back</html>";
	private static final String burntimeTooltip = "The amount of items that will get cooked when you use this item as a fuel source in a furnace";
	
	/**
	 * @wbp.parser.constructor
	 */
	public RegularItemEditor(String name, ItemsActivityPanel main) {
		this.name = name;
		this.main = main;

		setBounds(100, 100, 500, 500);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setTitle("Edit Item: " + this.name);
		addWindowListener(new WindowClosingVerifierListener());
		
		mainPanel = new JPanel();
		getContentPane().add(mainPanel, BorderLayout.NORTH);
		mainPanel.setLayout(new BorderLayout(5, 0));
		
		labelPanel = new JPanel();
		mainPanel.add(labelPanel, BorderLayout.WEST);
		labelPanel.setLayout(new GridLayout(0, 1, 0, 5));
		
		interactionPanel = new JPanel();
		mainPanel.add(interactionPanel, BorderLayout.CENTER);
		interactionPanel.setLayout(new GridLayout(0, 1, 0, 5));
		
		labelTexture = new JLabel("Texture");
		labelTexture.setToolTipText(textureTooltip);
		labelPanel.add(labelTexture);
		
		texturePanel = new JPanel();
		interactionPanel.add(texturePanel);
		texturePanel.setLayout(new BorderLayout(0, 0));
		
		textureChooseButton = new JButton("Choose");
		textureChooseButton.setToolTipText(textureTooltip);
		textureChooseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textureChoose();
			}
		});
		texturePanel.add(textureChooseButton, BorderLayout.EAST);
		
		textureLabel = new JLabel("");
		textureLabel.setToolTipText(textureTooltip);
		texturePanel.add(textureLabel, BorderLayout.CENTER);
		
		if (!(this instanceof ToolItemEditor)){
			labelMaxStackSize = new JLabel("Maximum stack size");
			labelMaxStackSize.setToolTipText(maxStackSizeTooltip);
			labelPanel.add(labelMaxStackSize);
			
			maxStackSizeSpinner = new JSpinner();
			maxStackSizeSpinner.setToolTipText(maxStackSizeTooltip);
			maxStackSizeSpinner.setModel(new SpinnerNumberModel(64, 0, 64, 1));
			interactionPanel.add(maxStackSizeSpinner);
		}
		
		if (!(this instanceof ToolItemEditor || this instanceof FoodItemEditor)){
			labelCreativeTabs = new JLabel("Creative tabs");
			labelCreativeTabs.setToolTipText(creativeTabsTooltip);
			labelPanel.add(labelCreativeTabs);
			
			creativeTabsPanel = new JPanel();
			interactionPanel.add(creativeTabsPanel);
			creativeTabsPanel.setLayout(new BorderLayout(0, 0));
			
			creativeTabsComboBox = new JComboBox();
			creativeTabsComboBox.setToolTipText(creativeTabsTooltip);
			creativeTabsComboBox.setModel(new DefaultComboBoxModel(new String[] {"Add", "block", "decorations", "redstone", "transport", "misc", 
					"food", "tools", "combat", "brewing", "materials", "inventory"}));
			creativeTabsComboBox.addItemListener(new ItemListener() {
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
			creativeTabsPanel.add(creativeTabsComboBox, BorderLayout.EAST);
			
			creativeTabsSubPanel = new JPanel();
			creativeTabsPanel.add(creativeTabsSubPanel, BorderLayout.CENTER);
			creativeTabsSubPanel.setLayout(new BorderLayout(0, 0));
			
			creativeTabsResetButton = new JButton("Reset");
			creativeTabsResetButton.setToolTipText(creativeTabsTooltip);
			creativeTabsResetButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					creativeTabsReset();
				}
			});
			creativeTabsSubPanel.add(creativeTabsResetButton, BorderLayout.EAST);
			
			creativeTabsLabel = new JLabel("");
			creativeTabsLabel.setToolTipText(creativeTabsTooltip);
			creativeTabsSubPanel.add(creativeTabsLabel, BorderLayout.CENTER);
		}
		
		labelContainer = new JLabel("Container");
		labelContainer.setToolTipText(containerTooltip);
		labelContainer.setEnabled(false);
		labelPanel.add(labelContainer);
		
		containerPanel = new JPanel();
		interactionPanel.add(containerPanel);
		containerPanel.setLayout(new BorderLayout(0, 0));
		
		containerCheckbox = new JCheckBox("Use");
		containerCheckbox.setToolTipText(containerTooltip);
		containerCheckbox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				containerUse();
			}
		});
		containerPanel.add(containerCheckbox, BorderLayout.EAST);
		
		containerSubPanel = new JPanel();
		containerPanel.add(containerSubPanel, BorderLayout.CENTER);
		containerSubPanel.setLayout(new BorderLayout(0, 0));
		
		containerChooseButton = new JButton("Choose");
		containerChooseButton.setToolTipText(containerTooltip);
		containerChooseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				containerChoose();
			}
		});
		containerChooseButton.setEnabled(false);
		containerSubPanel.add(containerChooseButton, BorderLayout.EAST);
		
		containerLabel = new JLabel("");
		containerLabel.setToolTipText(containerTooltip);
		containerLabel.setEnabled(false);
		containerSubPanel.add(containerLabel, BorderLayout.CENTER);
		
		labelBurnTime = new JLabel("Burn time");
		labelBurnTime.setToolTipText(burntimeTooltip);
		labelBurnTime.setEnabled(false);
		labelPanel.add(labelBurnTime);
		
		burntimePanel = new JPanel();
		burntimePanel.setToolTipText(burntimeTooltip);
		interactionPanel.add(burntimePanel);
		burntimePanel.setLayout(new BorderLayout(0, 0));
		
		burntimeCheckbox = new JCheckBox("Use");
		burntimeCheckbox.setToolTipText(burntimeTooltip);
		burntimeCheckbox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				burntimeUse();
			}
		});
		burntimePanel.add(burntimeCheckbox, BorderLayout.EAST);
		
		burntimeSpinner = new JSpinner();
		burntimeSpinner.setToolTipText(burntimeTooltip);
		burntimeSpinner.setEnabled(false);
		burntimeSpinner.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		burntimePanel.add(burntimeSpinner, BorderLayout.CENTER);
		
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		
		saveButton = new JButton("Save Item");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});
		buttonPanel.add(saveButton);
		
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancel();
			}
		});
		buttonPanel.add(cancelButton);
		
		setVisible(true);
	}

	public RegularItemEditor(ItemsActivityPanel main, ItemElement item) {
		this(item.name, main);
		regularSetup(main, item);
		if (item.item.stacksize != null)
			maxStackSizeSpinner.setValue(item.item.stacksize);
		for (TabResource s : ((ItemResource)item.item).tabs)
			creativeTabsChoose(s.name());
	}
	
	protected void regularSetup(ItemsActivityPanel main, ItemElement item){
		if (item.itemModel.textures.containsKey("layer0")) {
			textureLabel.setText(item.itemModel.textures.get("layer0"));
			setIconImage(main.main.TexturePanel.elements.get(item.itemModel.textures.get("layer0").split(":")[1]).getImage());
		}
		if (item.item.container != null){
			containerLabel.setText(item.item.container);
			containerCheckbox.setSelected(true);
			containerUse();
		}
		if (item.item.burntime != null){
			burntimeSpinner.setValue(item.item.burntime);
			burntimeCheckbox.setSelected(true);
			burntimeUse();
		}
	}

	protected void cancel() {
		WindowClosingVerifierListener.close(this);
	}

	protected void save() {
		if (!textureChooserIsOpen && !materialChooserIsOpen && textureLabel.getText().length() > 0){
			ItemResource base = new ItemResource();
			base.tabs = new HashSet<TabResource>();
			if (creativeTabsLabel.getText().length() > 0)
				for (String s : creativeTabsLabel.getText().split(","))
					base.tabs.add(TabResource.valueOf(s));
			
			ItemElement item = writeItem(base);
			main.addItem(item);
		}
		else{
			JOptionPane.showMessageDialog(this, "Not all required properties are given a value yet.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	protected ItemElement writeItem(BaseItemResource base){
		ItemElement item = new ItemElement();
		item.name = name;
		
		ItemModelResource model = new ItemModelResource();
		model.parent = "builtin/generated";
		model.textures = new HashMap<String, String>();
		model.textures.put("layer0", textureLabel.getText());
		model.display = Display.regular();
		item.itemModel = model;
		
		base.model = "modbuilder:" + name;
		if (!(this instanceof ToolItemEditor))
			base.stacksize = (Integer) maxStackSizeSpinner.getValue();
		if (containerLabel.getText().length() > 0 && containerCheckbox.isSelected()) 
			base.container = containerLabel.getText();
		if (burntimeCheckbox.isSelected())
			base.burntime = (Integer) burntimeSpinner.getValue();
		item.item = base;
		
		return item;
	}

	protected void burntimeUse() {
		boolean use = burntimeCheckbox.isSelected();
		burntimeSpinner.setEnabled(use);
		labelBurnTime.setEnabled(use);
	}

	protected void containerChoose() {
		if (!materialChooserIsOpen){
			new MaterialChooseWindow(MaterialChooseWindow.ITEMS_AND_BLOCKS, main.main, this);
			materialChooserIsOpen = true;
		}
	}

	protected void containerUse() {
		boolean use = containerCheckbox.isSelected();
		containerChooseButton.setEnabled(use);
		containerLabel.setEnabled(use);
		labelContainer.setEnabled(use);
	}

	protected void creativeTabsReset() {
		creativeTabsLabel.setText("");
	}

	protected void creativeTabsChoose(String tab) {
		if (creativeTabsLabel.getText().length() > 0) creativeTabsLabel.setText(creativeTabsLabel.getText() + ",");
		creativeTabsLabel.setText(creativeTabsLabel.getText() + tab);
	}

	protected void textureChoose() {
		if (!textureChooserIsOpen){
			new TextureChooseWindow(main.main.TexturePanel.elements, this);
			textureChooserIsOpen = true;
		}
	}
	
	@Override
	public void dispose() {
		main.closeEditor(name);
		super.dispose();
	}

	@Override
	public void chooseTexture(String texture) {
		textureLabel.setText(texture);
		setIconImage(main.main.TexturePanel.elements.get(texture.split(":")[1]).getImage());
	}
	
	@Override
	public void textureChooserDispose(){
		textureChooserIsOpen = false;
	}

	@Override
	public void chooseMaterial(String material) {
		containerLabel.setText(material);
	}

	@Override
	public void materialChooserDispose() {
		materialChooserIsOpen = false;
	}
}
