package twintro.minecraft.modbuilder.editor.interfaces.editors;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
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
import twintro.minecraft.modbuilder.editor.interfaces.helperclasses.WindowClosingVerifierUser;
import twintro.minecraft.modbuilder.editor.resources.RecipeElement;

import java.awt.GridLayout;
import java.awt.Font;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class SmeltingRecipeEditor extends WindowClosingVerifierUser {
	private JPanel contentPane;
	private String name;
	private RecipesActivityPanel main;
	private ItemStackButton inputSmeltingButton;
	private ItemStackButton outputSmeltingButton;
	private JSpinner xpSpinner;
	
	private static final String xpTooltip = "";//TODO

	/**
	 * Create the frame.
	 * @wbp.parser.constructor
	 */
	public SmeltingRecipeEditor(String newName, RecipesActivityPanel parent) {
		this.name = newName;
		this.main = parent;
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowClosingVerifierListener());
		this.setTitle("Edit Recipe: " + name);
		
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2, BorderLayout.SOUTH);
		panel_2.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		
		JButton saveButton = new JButton("Save Recipe");
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});
		saveButton.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cancel();
			}
		});
		
		/*
		JButton btnRename = new JButton("Rename");
		btnRename.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String nameNew2 = JOptionPane.showInputDialog("Item name:");
				RecipeEditor temp = main.openEditors.get(name);
				main.openEditors.remove(name);
				name = nameNew2;
				main.openEditors.put(name, temp);
				setTitle("Edit Recipe: " + name);
			}
		});
		panel_2.add(btnRename);
		*/

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
		
		outputSmeltingButton = new ItemStackButton("", this);
		outputSmeltingButton.setProduct();
		outputSmeltingButton.setBounds(243, 15, 90, 90);
		panel_1.add(outputSmeltingButton);
		
		JLabel lblNewLabel = new JLabel("\u2192");
		lblNewLabel.setBounds(142, 10, 90, 90);
		panel_1.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 69));
		
		inputSmeltingButton = new ItemStackButton("", this);
		inputSmeltingButton.setBounds(15, 15, 90, 90);
		panel_1.add(inputSmeltingButton);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBounds(15, 150, 318, 24);
		panel_1.add(panel_3);
		panel_3.setLayout(new BorderLayout(5, 5));
		
		JLabel lblNewLabel_1 = new JLabel("XP gained");
		lblNewLabel_1.setToolTipText(xpTooltip);
		panel_3.add(lblNewLabel_1, BorderLayout.WEST);
		
		xpSpinner = new JSpinner();
		xpSpinner.setToolTipText(xpTooltip);
		xpSpinner.addChangeListener(changeListener);
		xpSpinner.setModel(new SpinnerNumberModel(new Float(0), null, null, new Float(0.1)));
		panel_3.add(xpSpinner, BorderLayout.CENTER);
		
		setVisible(true);
	}
	
	public SmeltingRecipeEditor(String value, RecipesActivityPanel recipesActivityPanel, RecipeElement recipe) {
		this(value, recipesActivityPanel);
		SmeltingRecipe smltngRcpy = (SmeltingRecipe)recipe.recipe;
		outputSmeltingButton.chooseItem(smltngRcpy.output);
		inputSmeltingButton.chooseItem(smltngRcpy.input);
		xpSpinner.setValue(smltngRcpy.xp);
		
		changed = false;
	}
	
	public void itemChanged(String old, String newName){
		if (inputSmeltingButton.item.item == old) inputSmeltingButton.item.item = newName;
		if (outputSmeltingButton.item.item == old) outputSmeltingButton.item.item = newName;
	}
	
	public void blockChanged(String old, String newName){
		if (inputSmeltingButton.item.block == old) inputSmeltingButton.item.block = newName;
		if (outputSmeltingButton.item.block == old) outputSmeltingButton.item.block = newName;
	}
	
	public void save(){
		SmeltingRecipe recipe = new SmeltingRecipe();
		recipe.output = outputSmeltingButton.item;
		recipe.input = inputSmeltingButton.item;
		recipe.xp = (Float)xpSpinner.getValue();
		RecipeElement itemToSave = new RecipeElement();
		itemToSave.name = this.name;
		itemToSave.recipe = recipe;
		main.addRecipe(itemToSave);
		
		dispose();
	}
	
	private void cancel(){
		for (WindowListener listener : getWindowListeners()){
			listener.windowClosing(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		}
	}
	
	@Override
	public void dispose() {
		main.closeEditor(name);
		super.dispose();
	}
}
