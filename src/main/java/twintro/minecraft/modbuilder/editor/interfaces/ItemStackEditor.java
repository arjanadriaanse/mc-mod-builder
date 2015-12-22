package twintro.minecraft.modbuilder.editor.interfaces;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
import twintro.minecraft.modbuilder.editor.resources.ItemElement;

public class ItemStackEditor extends JFrame {

	private ItemStackButton parent;
	private List<String> items;
	private JPanel contentPane;
	private JTextField stackSizeTextfield;
	private JTextField containerTextfield;
	private JPanel panel_1;
	private final JButton saveButton = new JButton("Save Item");
	private final JButton cancelButton = new JButton("Cancel");
	private JLabel modelLabel;
	private JComboBox comboBox;

	public ItemStackEditor(ItemStackButton parent ,List<String> items) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 503, 246);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(0, 2, 10, 10));
		
		modelLabel = new JLabel("Model"); //TODO fix after i get proper intel about what i should put here
		panel.add(modelLabel);
		
		comboBox = new JComboBox();
		comboBox.setEditable(true);
		comboBox.setModel(new DefaultComboBoxModel(items.toArray()));
		panel.add(comboBox);
		
		JLabel stackSizeLabel = new JLabel("Amount to have in crafting");
		panel.add(stackSizeLabel);
		
		stackSizeTextfield = new JTextField();
		panel.add(stackSizeTextfield);
		stackSizeTextfield.setColumns(10);
		
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
		panel_1.add(cancelButton);
		
		setVisible(true);
		
		this.parent = parent;

		if (parent.item != null){
			comboBox.setSelectedItem(parent.item);
			containerTextfield.setText(parent.item.container);
			stackSizeTextfield.setText(parent.item.amount +"");
		}
	}
	
	
	
	
	private void saveItem(){
		if (parent.item==null){parent.item = new ItemStackResource();}
		
		if(!comboBox.getSelectedItem().toString().isEmpty()){
		parent.item.container = containerTextfield.getText().isEmpty() ? null : containerTextfield.getText();
		parent.item.amount = Integer.getInteger(stackSizeTextfield.getText());
		parent.item.item = (String) comboBox.getSelectedItem();
		
		this.dispose();
		} else {
			JOptionPane.showMessageDialog(this, "Please provide an item.", "Error", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	private void cancel(){
		parent.item = null;
		this.dispose();
	}
		
}

