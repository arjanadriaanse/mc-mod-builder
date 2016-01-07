package twintro.minecraft.modbuilder.editor.interfaces;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import twintro.minecraft.modbuilder.data.resources.recipes.ItemStackResource;
import twintro.minecraft.modbuilder.editor.Editor;

import java.awt.GridLayout;
import java.awt.Window;

import javax.swing.JButton;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.SwingConstants;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;

import javax.swing.JFrame;

public class RecipeEditor extends JFrame {

	private JPanel contentPane;
	private String name;
	private RecipesActivityPanel main;
	private ItemStackButton[] buttons;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ShapedRecipeEditor frame = new ShapedRecipeEditor("asd", new RecipesActivityPanel("s", "e", new Editor()));
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
		public RecipeEditor(String nameNew, RecipesActivityPanel parent) {
		this.name = nameNew;
		this.main = parent;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 506, 412);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(5, 5));
		setContentPane(contentPane);
		this.setTitle("Edit recipe:" + name);
		
		JPanel panel_3 = new JPanel();
		contentPane.add(panel_3, BorderLayout.CENTER);
		panel_3.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(30, 30, 250, 250);
		panel_3.add(panel);
		panel.setLayout(new GridLayout(3, 3, 4, 4));
		
		buttons = new ItemStackButton[10];
		buttons[0] = new ItemStackButton("");
		panel.add(buttons[0]);
		
		buttons[1] = new ItemStackButton("");
		panel.add(buttons[1]);
		
		buttons[2] = new ItemStackButton("");
		panel.add(buttons[2]);
		
		buttons[3] = new ItemStackButton("");
		panel.add(buttons[3]);
		
		buttons[4] = new ItemStackButton("");
		panel.add(buttons[4]);
		
		buttons[5] = new ItemStackButton("");
		panel.add(buttons[5]);
		
		buttons[6] = new ItemStackButton("");
		panel.add(buttons[6]);
		
		buttons[7] = new ItemStackButton("");
		panel.add(buttons[7]);
		
		buttons[8] = new ItemStackButton("");
		panel.add(buttons[8]);
		
		buttons[9] = new ItemStackButton("");
		buttons[9].setIsProduct(true);
		buttons[9].setBounds(365, 115, 80, 80);
		
		panel_3.add(buttons[9]);
		
		JLabel label = new JLabel("\u2192");
		label.setFont(new Font("Times New Roman", Font.BOLD, 69));
		label.setBounds(288, 105, 80, 93);
		panel_3.add(label);
		buttons[9].setHorizontalAlignment(SwingConstants.RIGHT);
		
		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2, BorderLayout.SOUTH);
		panel_2.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		
		JLabel lblCreateTheShaped = new JLabel("Create the recipe, the shape does not matter");
		contentPane.add(lblCreateTheShaped, BorderLayout.NORTH);
		

		JButton btnRename = new JButton("Rename");
		btnRename.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String nameNew2 = JOptionPane.showInputDialog("Item name:");
				RecipeEditor temp = main.openEditors.get(name);
				main.openEditors.remove(name);
				name = nameNew2;
				main.openEditors.put(name, temp);
				setTitle("Edit structure: " + name);
			}
		});
		panel_2.add(btnRename);
		
		JButton btnSaveItem = new JButton("Save recipe");
		panel_2.add(btnSaveItem);
		btnSaveItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				saveRecipe();
			}
		});
		
		JButton btnCancel = new JButton("Cancel");
		panel_2.add(btnCancel);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cancel();
			}
		});

	}

	protected void cancel() {
		this.dispose();
		//TODO ask for confirm
	}

	protected void saveRecipe() {
	
		ItemStackResource[] savableInput = new ItemStackResource[9];
		ItemStackResource savableOutput = new ItemStackResource();
		for (int i = 0; i < 9; i++){
			if (buttons[i].getText() != ""){
				savableInput[i] = buttons[i].item;
			}
		}
		savableOutput = buttons[9].item;
		
/*			String a = buttons[i].getText();
			if (a == "") recipe += " ";
			else {
				for(char b = (char) ('a'+i); b<='j'; b++){
					if (a == recipeMap.get(b).item || a==recipeMap.get(b).block && recipeMap.get(b).container == buttons[i].item.container){
						recipe += b;
						b+='j';
					}
					else if(!recipeMap.containsKey(b)) {
						recipeMap.put(b, buttons[i].item);
						recipe += b;
					}
				}
			}
		}
		String outputItem = buttons[9].getText();
		int outputAmount = buttons[9].item.amount;
		*/	
		//TODO send recipe to activity panel to save
	}
}
