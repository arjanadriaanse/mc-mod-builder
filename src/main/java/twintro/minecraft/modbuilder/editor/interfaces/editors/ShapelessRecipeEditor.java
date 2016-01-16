package twintro.minecraft.modbuilder.editor.interfaces.editors;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import scala.swing.event.WindowClosed;
import twintro.minecraft.modbuilder.data.resources.recipes.ItemStackResource;
import twintro.minecraft.modbuilder.data.resources.recipes.RecipeType;
import twintro.minecraft.modbuilder.data.resources.recipes.ShapelessRecipe;
import twintro.minecraft.modbuilder.editor.Editor;
import twintro.minecraft.modbuilder.editor.interfaces.activitypanels.RecipesActivityPanel;
import twintro.minecraft.modbuilder.editor.interfaces.choosewindows.ObjectRunnable;
import twintro.minecraft.modbuilder.editor.interfaces.helperclasses.ItemStackButton;
import twintro.minecraft.modbuilder.editor.interfaces.helperclasses.WindowClosingVerifierListener;
import twintro.minecraft.modbuilder.editor.interfaces.helperclasses.WindowClosingVerifierUser;
import twintro.minecraft.modbuilder.editor.resources.RecipeElement;

import java.awt.GridLayout;
import java.awt.Window;

import javax.swing.JButton;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.SwingConstants;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;

import javax.swing.JFrame;

public class ShapelessRecipeEditor extends WindowClosingVerifierUser {
	private JPanel contentPane;
	protected JLabel lblCreateTheShaped;
	protected ItemStackButton[] buttons;
	
	protected String name;
	protected ObjectRunnable runnable;
	private ObjectRunnable closeHandler;
	
	public ShapelessRecipeEditor(String nameNew, ObjectRunnable runnable, ObjectRunnable closeHandler) {
		this.name = nameNew;
		this.runnable = runnable;
		this.closeHandler = closeHandler;
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowClosingVerifierListener());
		setBounds(100, 100, 506, 412);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(5, 5));
		setContentPane(contentPane);
		this.setTitle("Edit Recipe: " + name);
		
		JPanel panel_3 = new JPanel();
		contentPane.add(panel_3, BorderLayout.CENTER);
		panel_3.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(30, 30, 250, 250);
		panel_3.add(panel);
		panel.setLayout(new GridLayout(3, 3, 4, 4));
		
		buttons = new ItemStackButton[10];
		for (int i = 0; i<9; i++){
			buttons[i] = new ItemStackButton("", this);
			panel.add(buttons[i]);
			buttons[i].item = new ItemStackResource();
		}
		
		buttons[9] = new ItemStackButton("", this);
		buttons[9].setProduct();
		buttons[9].setBounds(365, 115, 80, 80);
		
		panel_3.add(buttons[9]);
		
		JLabel label = new JLabel("\u2192");
		label.setFont(new Font("Times New Roman", Font.BOLD, 69));
		label.setBounds(288, 105, 80, 93);
		panel_3.add(label);
		
		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2, BorderLayout.SOUTH);
		panel_2.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		
		lblCreateTheShaped = new JLabel("Create the recipe, the shape does not matter");
		contentPane.add(lblCreateTheShaped, BorderLayout.NORTH);
		
		JButton btnSaveItem = new JButton("Save Recipe");
		panel_2.add(btnSaveItem);
		btnSaveItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				save();
			}
		});
		
		JButton btnCancel = new JButton("Cancel");
		panel_2.add(btnCancel);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cancel();
			}
		});
		setVisible(true);
	}
		
	public ShapelessRecipeEditor(RecipeElement recipe, ObjectRunnable runnable, ObjectRunnable closeHandler) {
		this(recipe.name, runnable, closeHandler);
		ShapelessRecipe shplsRcpy = (ShapelessRecipe)recipe.recipe;
		
		for(int i = 0; i < shplsRcpy.input.size();i++)
			buttons[i].chooseItem(shplsRcpy.input.get(i));
		buttons[9].chooseItem(shplsRcpy.output);
		
		changed = false;
	}

	@Override
	public boolean save() {
		List<ItemStackResource> savableInput = new ArrayList<ItemStackResource>();
		ItemStackResource savableOutput = new ItemStackResource();
		for (int i = 0; i < 9; i++){
			ItemStackResource item = buttons[i].item;
			if (!(item == null || ((item.item == null || item.item == "") && (item.block == null || item.block == ""))))
				savableInput.add(item);
		}
		if (savableInput.size() == 0){
			int selected = JOptionPane.showConfirmDialog(this, "Please give atleast one item to have as input", 
					"Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
			if (selected == JOptionPane.OK_OPTION)
				return false;
			return true;
		}
		ItemStackResource item = buttons[9].item;
		if (item == null || ((item.item == null || item.item == "") && (item.block == null || item.block == ""))){
			int selected = JOptionPane.showConfirmDialog(this, "Please give an output item", 
					"Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
			if (selected == JOptionPane.OK_OPTION)
				return false;
			return true;
		}
		savableOutput = item;
		ShapelessRecipe recipe = new ShapelessRecipe();
		recipe.type = RecipeType.shapeless;
		recipe.input = savableInput;
		recipe.output = savableOutput;
		RecipeElement recipeElement = new RecipeElement();
		recipeElement.recipe = recipe;
		recipeElement.name = this.name;
		
		runnable.run(recipeElement);
		dispose();
		
		return true;
	}

	private void cancel() {
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
