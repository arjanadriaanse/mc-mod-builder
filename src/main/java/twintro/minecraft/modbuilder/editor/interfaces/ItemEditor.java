package twintro.minecraft.modbuilder.editor.interfaces;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListDataListener;

import twintro.minecraft.modbuilder.data.resources.items.ItemResource;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

public class ItemEditor extends JFrame {

	private List<String> models;
	private ItemResource item;
	private JPanel contentPane;
	private JTextField stackSizeTextfield;
	private JTextField burnTimeTextfield;
	private JTextField containerTextfield;
	private JPanel panel_1;
	private final JButton saveButton = new JButton("Save Item");
	private final JButton cancelButton = new JButton("Cancel");
	private JLabel modelLabel;
	private JComboBox comboBox;

	public ItemEditor(List<String> models) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 503, 246);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(0, 2, 10, 10));
		
		modelLabel = new JLabel("Model");
		panel.add(modelLabel);
		
		comboBox = new JComboBox();
		comboBox.setEditable(true);
		comboBox.setModel(new DefaultComboBoxModel(models.toArray()));
		panel.add(comboBox);
		
		JLabel stackSizeLabel = new JLabel("Stacksize");
		panel.add(stackSizeLabel);
		
		stackSizeTextfield = new JTextField();
		panel.add(stackSizeTextfield);
		stackSizeTextfield.setColumns(10);
		
		JLabel containerLabel = new JLabel("Container");
		panel.add(containerLabel);
		
		containerTextfield = new JTextField();
		panel.add(containerTextfield);
		containerTextfield.setColumns(10);
		
		JLabel burnTimeLabel = new JLabel("Burntime");
		panel.add(burnTimeLabel);
		
		burnTimeTextfield = new JTextField();
		panel.add(burnTimeTextfield);
		burnTimeTextfield.setColumns(10);
		
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
	}
	
	public ItemResource getItem(){
		return item;
	}
	
	public ItemEditor(List<String> models,ItemResource item){
		this(models);
		this.item = item;
		comboBox.setSelectedItem(item.model);
		containerTextfield.setText(item.container);
		burnTimeTextfield.setText(item.burntime + "");
		stackSizeTextfield.setText(item.stacksize +"");
	}
	
	private void saveItem(){
		if (item==null){item = new ItemResource();}
		
		if(!comboBox.getSelectedItem().toString().isEmpty()){
		item.container = containerTextfield.getText().isEmpty() ? null : containerTextfield.getText();
		item.burntime = Integer.getInteger(burnTimeTextfield.getText());
		item.stacksize = Integer.getInteger(stackSizeTextfield.getText());
		item.model = (String)comboBox.getSelectedItem();
		
		this.dispose();
		} else {
			JOptionPane.showMessageDialog(this, "Please provide a model.", "Error", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	private void cancel(){
		
		item = null;
		this.dispose();
	}
		
}
