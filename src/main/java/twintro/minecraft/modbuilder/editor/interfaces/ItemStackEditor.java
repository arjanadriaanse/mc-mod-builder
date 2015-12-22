package twintro.minecraft.modbuilder.editor.interfaces;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import twintro.minecraft.modbuilder.data.resources.items.ItemResource;
import twintro.minecraft.modbuilder.data.resources.models.ItemModelResource;
import twintro.minecraft.modbuilder.data.resources.models.ItemModelResource.Display;
import twintro.minecraft.modbuilder.data.resources.recipes.ItemStackResource;
import twintro.minecraft.modbuilder.editor.resources.BlockElement;
import twintro.minecraft.modbuilder.editor.resources.Element;
import twintro.minecraft.modbuilder.editor.resources.ItemElement;
import javax.swing.JRadioButton;

public class ItemStackEditor extends JDialog {


	public boolean changed;
	private ItemStackResource item;
	public ItemStackResource getItem(){
		return item;
	}
	private boolean isProduct;
	private List<String> items;
	private JPanel contentPane;
	private JTextField stackSizeTextfield;
	private JTextField containerTextfield;
	private JPanel panel_1;
	private final JButton saveButton = new JButton("Save Item");
	private final JButton cancelButton = new JButton("Cancel");
	private JLabel modelLabel;
	private JComboBox comboBox;


	public ItemStackEditor(ItemStackResource item ,List<ItemElement> items, List<BlockElement> blocks, boolean isProduct, Container parent) {
		super((Frame) parent);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 503, 246);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(0, 2, 10, 10));
		
		modelLabel = new JLabel("Select the item");
		panel.add(modelLabel);
		
		List<Element> elements = new ArrayList<Element>();
		elements.addAll(blocks); elements.addAll(items);
		comboBox = new JComboBox();
		comboBox.setEditable(true);
		comboBox.setModel(new DefaultComboBoxModel(elements.toArray()));
		
		panel.add(comboBox);
		
		this.isProduct = isProduct;
		
		if (isProduct){
			JLabel stackSizeLabel = new JLabel("Amount to craft");
			panel.add(stackSizeLabel);
		
			stackSizeTextfield = new JTextField();
			panel.add(stackSizeTextfield);
			stackSizeTextfield.setColumns(10);

		}
		
		JLabel containerLabel = new JLabel("The container item");
		panel.add(containerLabel);
		
		containerTextfield = new JTextField();
		panel.add(containerTextfield);
		containerTextfield.setColumns(10);
		
		
		panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				saveItem();
			}
		});
		panel_1.add(saveButton);
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancel();
			}
		});
		panel_1.add(cancelButton);
		
		setVisible(true);
		
		

		if (item != null){
			comboBox.setSelectedItem(item.item);
			containerTextfield.setText(item.container);
			if (isProduct){
				stackSizeTextfield.setText(item.amount +"");
			}
		}
	}
		
	
	
	
	private void saveItem(){
		if (item==null){item = new ItemStackResource();}
		
		
		if(!comboBox.getSelectedItem().toString().isEmpty()){
		item.container = containerTextfield.getText().isEmpty() ? null : containerTextfield.getText();
		
		
		if (comboBox.getSelectedItem() instanceof BlockElement) item.block = comboBox.getSelectedItem().toString();
		else item.item = comboBox.getSelectedItem().toString();
		
		if (isProduct && !stackSizeTextfield.getText().isEmpty())
			try{		
				item.amount = Integer.valueOf(stackSizeTextfield.getText());
			} catch (NumberFormatException e){
			}

		changed = true;
		this.dispose();
		} else {
			JOptionPane.showMessageDialog(this, "Please provide an item.", "Error", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	private void cancel(){
		item = null;
		changed = false;
		this.dispose();
	}
		
}

