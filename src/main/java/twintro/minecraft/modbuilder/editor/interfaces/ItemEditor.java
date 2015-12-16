package twintro.minecraft.modbuilder.editor.interfaces;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import twintro.minecraft.modbuilder.data.resources.items.ItemResource;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ItemEditor extends JFrame {

	ItemResource item;
	private JPanel contentPane;
	private JTextField stackSizeTextfield;
	private JTextField burnTimeTextfield;
	private JTextField containerTextfield;
	private JPanel panel_1;
	private final JButton saveButton = new JButton("Save Item");
	private final JButton cancelButton = new JButton("Cancel");
	private JLabel modelLabel;
	private JTextField modelTextfield;

	public ItemEditor() {
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
		
		modelTextfield = new JTextField();
		panel.add(modelTextfield);
		modelTextfield.setColumns(10);
		
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
	}
	
	public ItemEditor(ItemResource item){
		this();
		this.item = item;
		modelTextfield.setText(item.model);
		containerTextfield.setText(item.container);
		burnTimeTextfield.setText(item.burntime + "");
		stackSizeTextfield.setText(item.stacksize +"");
	}
	
	private void saveItem(){
		if (item==null){item = new ItemResource();}
		
		if(!modelTextfield.getText().isEmpty()){
		item.container = containerTextfield.getText().isEmpty() ? null : containerTextfield.getText();
		item.burntime = Integer.getInteger(burnTimeTextfield.getText());
		item.stacksize = Integer.getInteger(stackSizeTextfield.getText());
		item.model = modelTextfield.getText();
		
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
