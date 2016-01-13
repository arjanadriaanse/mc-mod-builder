package twintro.minecraft.modbuilder.editor.interfaces.editors;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import twintro.minecraft.modbuilder.data.resources.recipes.ItemStackResource;
import twintro.minecraft.modbuilder.data.resources.recipes.ShapelessRecipe;
import twintro.minecraft.modbuilder.data.resources.recipes.SmeltingRecipe;
import twintro.minecraft.modbuilder.editor.Editor;
import twintro.minecraft.modbuilder.editor.interfaces.activitypanels.RecipesActivityPanel;
import twintro.minecraft.modbuilder.editor.interfaces.helperclasses.ItemStackButton;
import twintro.minecraft.modbuilder.editor.interfaces.helperclasses.WindowClosingVerifierListener;
import twintro.minecraft.modbuilder.editor.resources.RecipeElement;

import java.awt.GridLayout;
import java.awt.Font;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class SmeltingRecipeEditor extends RecipeEditor {

	private JPanel contentPane;
	private String name;
	private RecipesActivityPanel main;
	private ItemStackButton inputSmeltingButton;
	private ItemStackButton outputSmeltingButton;
	private JSpinner xpSpinner;

	/**
	 * Launch the application.
	 */

	/**
	 * Create the frame.
	 * @wbp.parser.constructor
	 */
	public SmeltingRecipeEditor(String newName, RecipesActivityPanel parent, Set<String> items, Set<String> blocks) {
		this.name = newName;
		this.main = parent;
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowClosingVerifierListener());
		
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2, BorderLayout.SOUTH);
		panel_2.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		
		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveRecipe();
			}
		});
		saveButton.setHorizontalAlignment(SwingConstants.RIGHT);

		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cancel();
			}
		});
		
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
		

		panel_2.add(saveButton);
		cancelButton.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_2.add(cancelButton);

		JLabel lblCreateTheShaped = new JLabel("Create the smelting recipe ");
		contentPane.add(lblCreateTheShaped, BorderLayout.NORTH);
		

		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		outputSmeltingButton = new ItemStackButton("New button", items, blocks);
		outputSmeltingButton.setText("");
		outputSmeltingButton.setIsProduct(true);
		outputSmeltingButton.setBounds(243, 15, 90, 90);
		panel_1.add(outputSmeltingButton);
		
		JLabel lblNewLabel = new JLabel("\u2192");
		lblNewLabel.setBounds(142, 10, 90, 90);
		panel_1.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 69));
		
		inputSmeltingButton = new ItemStackButton("New button", items, blocks);
		inputSmeltingButton.setText("");
		inputSmeltingButton.setIsProduct(false);
		inputSmeltingButton.setBounds(15, 15, 90, 90);
		panel_1.add(inputSmeltingButton);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBounds(15, 150, 318, 24);
		panel_1.add(panel_3);
		panel_3.setLayout(new BorderLayout(5, 5));
		
		JLabel lblNewLabel_1 = new JLabel("XP gained");
		panel_3.add(lblNewLabel_1, BorderLayout.WEST);
		
		xpSpinner = new JSpinner();
		xpSpinner.setModel(new SpinnerNumberModel(new Float(0), null, null, new Float(0.1)));
		panel_3.add(xpSpinner, BorderLayout.CENTER);
		
		setVisible(true);
	}
	
	public SmeltingRecipeEditor(String value, RecipesActivityPanel recipesActivityPanel, RecipeElement recipe,
			Set<String> editorItems, Set<String> editorBlocks) {
		this(value, recipesActivityPanel, editorItems, editorBlocks);
		SmeltingRecipe smltngRcpy = (SmeltingRecipe)recipe.recipe;
		outputSmeltingButton.item = smltngRcpy.output;
		if (outputSmeltingButton.item.block == null || outputSmeltingButton.item.block == ""){
			if (outputSmeltingButton.item.amount != null && outputSmeltingButton.item.amount != 0){
				outputSmeltingButton.setText(outputSmeltingButton.item.amount + " " + outputSmeltingButton.item.item);
			}else{
				outputSmeltingButton.setText(outputSmeltingButton.item.item);
			}
		} else {
			if (outputSmeltingButton.item.amount != null && outputSmeltingButton.item.amount != 0){
				outputSmeltingButton.setText(outputSmeltingButton.item.amount + " " + outputSmeltingButton.item.block);
			}else{
				outputSmeltingButton.setText(outputSmeltingButton.item.block);
			}
		}
		inputSmeltingButton.item = smltngRcpy.input;
		if (inputSmeltingButton.item.block == null || inputSmeltingButton.item.block == ""){
			inputSmeltingButton.setText(inputSmeltingButton.item.item);

		} else {
			inputSmeltingButton.setText(inputSmeltingButton.item.block);
		}
		if (smltngRcpy.xp != 0.0 && smltngRcpy.xp != 0){
			xpSpinner.setValue(smltngRcpy.xp);
		}
	}

	
	
	public void itemChanged(String old, String newName){
		if (inputSmeltingButton.item.item == old) inputSmeltingButton.item.item = newName;
		if (outputSmeltingButton.item.item == old) outputSmeltingButton.item.item = newName;
	}
	
	public void blockChanged(String old, String newName){
		if (inputSmeltingButton.item.block == old) inputSmeltingButton.item.block = newName;
		if (outputSmeltingButton.item.block == old) outputSmeltingButton.item.block = newName;
	}
	
	private void saveRecipe(){
		SmeltingRecipe recipe = new SmeltingRecipe();
		recipe.output = outputSmeltingButton.item;
		recipe.input = inputSmeltingButton.item;
		recipe.xp = (Float)xpSpinner.getValue();
		RecipeElement itemToSave = new RecipeElement();
		itemToSave.name = this.name;
		itemToSave.recipe = recipe;
		main.addRecipe(itemToSave);
	}
	
	private void cancel(){
		this.getWindowListeners()[0].windowClosing(new WindowEvent(this, 0));
	}
}