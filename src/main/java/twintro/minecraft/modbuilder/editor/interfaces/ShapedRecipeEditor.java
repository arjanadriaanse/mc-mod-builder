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
import java.awt.image.ReplicateScaleFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;

public class ShapedRecipeEditor extends ShapelessRecipeEditor {

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
					ShapelessRecipeEditor frame = new ShapelessRecipeEditor("asd", new RecipesActivityPanel("s", "e", new Editor()));
					
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ShapedRecipeEditor(String nameNew, RecipesActivityPanel parent) {
		super(nameNew, parent);
	}


	protected void saveRecipe() {
		String recipe = "";
		char c = 'a'+1;
		
		Map<Character, ItemStackResource> recipeMap = new HashMap<Character, ItemStackResource>();
		for (int i = 0; i < 9; i++){
			String a = buttons[i].getText();
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
		//TODO send values to activity panel to save
	}
}
