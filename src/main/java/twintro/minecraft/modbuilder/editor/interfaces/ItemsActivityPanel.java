package twintro.minecraft.modbuilder.editor.interfaces;

import java.io.IOException;
import java.util.List;

import javax.swing.JOptionPane;

import twintro.minecraft.modbuilder.editor.ActivityPanel;
import twintro.minecraft.modbuilder.editor.generator.ResourcePackGenerator;
import twintro.minecraft.modbuilder.editor.resources.ItemElement;

public class ItemsActivityPanel extends ActivityPanel {
	public ItemsActivityPanel(String header, String button) {
		super(header, button);
	}

	@Override
	protected void add() {
		String name = JOptionPane.showInputDialog("Item name:");
		if (name != null)
			if (name.replaceAll(" ", "").length() > 0)
				new Object();//TODO
	}
	
	public void addItem(ItemElement element){
		createFile(element.itemModel, "assets/modbuilder/models/item/" + element.name + ".json");
		createFile(element.item, "assets/modbuilder/items/" + element.name + ".json");
		addElement(element.name, element.image);
	}
	
	public void createFile(Object model, String dir){
		try {
			ResourcePackGenerator.createFile(model, dir);
		} catch (IOException e) {
			e.printStackTrace();
		}
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
