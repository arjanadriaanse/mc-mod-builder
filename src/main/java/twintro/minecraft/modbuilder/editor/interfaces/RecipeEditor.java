package twintro.minecraft.modbuilder.editor.interfaces;

import javax.swing.JFrame;

public class RecipeEditor extends JFrame {

	RecipeEditor(){
		setVisible(true);
		ShapedRecipeEditor newEditor;
	
		newEditor = new ShapedRecipeEditor("asd");
		newEditor.setVisible(true);
	}
	
	
}
