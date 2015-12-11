package twintro.minecraft.modbuilder.editor.interfaces;

import twintro.minecraft.modbuilder.editor.ActivityPanel;

public class RecipesActivityPanel extends ActivityPanel {
	public RecipesActivityPanel(String header, String button) {
		super(header, button);
	}

	@Override
	protected void add() {
		
	}
	
	@Override
	protected void edit() {
		String value = (String) list.getSelectedValue();
	}
	
	@Override
	protected void delete() {
		String value = (String) list.getSelectedValue();
	}
	
	@Override
	public void updateList() {
		
	}
}
