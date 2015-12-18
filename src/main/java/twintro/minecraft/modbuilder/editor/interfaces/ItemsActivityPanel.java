package twintro.minecraft.modbuilder.editor.interfaces;

import java.util.List;

import javax.swing.JOptionPane;

import twintro.minecraft.modbuilder.editor.ActivityPanel;
import twintro.minecraft.modbuilder.editor.resources.ItemElement;

public class ItemsActivityPanel extends ActivityPanel {
	public ItemsActivityPanel(String header, String button) {
		super(header, button);
	}
	
	private List<String> models;

	@Override
	protected void add() {
		String name = JOptionPane.showInputDialog("Item name:");
		if (name != null)
			if (name.replaceAll(" ", "").length() > 0)
				new ItemEditor(models);
	}
	
	public void addItem(ItemElement item){
		for (String model : elements.keySet()){
			
		}
	}
	
	@Override
	protected void edit() {
		String value = (String) list.getSelectedValue();
	//	ItemEditor editor = new ItemEditor(value);
	}
	
	@Override
	protected void delete() {
		String value = (String) list.getSelectedValue();
	}
	
	@Override
	public void updateList() {
		
	}
}
