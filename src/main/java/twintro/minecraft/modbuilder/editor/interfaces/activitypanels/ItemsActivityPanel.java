package twintro.minecraft.modbuilder.editor.interfaces.activitypanels;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import twintro.minecraft.modbuilder.data.resources.items.ItemType;
import twintro.minecraft.modbuilder.editor.Editor;
import twintro.minecraft.modbuilder.editor.generator.LanguageFile;
import twintro.minecraft.modbuilder.editor.generator.ResourcePackIO;
import twintro.minecraft.modbuilder.editor.interfaces.choosewindows.ObjectRunnable;
import twintro.minecraft.modbuilder.editor.interfaces.editors.FoodItemEditor;
import twintro.minecraft.modbuilder.editor.interfaces.editors.RegularItemEditor;
import twintro.minecraft.modbuilder.editor.interfaces.editors.ToolItemEditor;
import twintro.minecraft.modbuilder.editor.interfaces.helperclasses.ActivityButton;
import twintro.minecraft.modbuilder.editor.resources.ItemElement;

public class ItemsActivityPanel extends ObjectActivityPanel {
	private final ObjectRunnable runnable = new ObjectRunnable() {
		@Override
		public void run(Object obj) {
			saveItem((ItemElement) obj);
		}
	};
	
	public ItemsActivityPanel(String header, String button) {
		super(header, button);
		openEditors = new HashMap<String, JFrame>();
	}
	
	@Override
	protected void add() {
		String name = nameDialog("Item");
		if (isValidName(name)){
			name = name.replaceAll(" ", "_");
			if (!Editor.getBlockList().containsKey(name)){
				RegularItemEditor editor = new RegularItemEditor(name, runnable, closeHandler);
				openEditors.put(name, editor);
			}
			else{
				JOptionPane.showConfirmDialog(this, "This name is already in use.", 
						"Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	private void addFood(){
		String name = nameDialog("Item");
		if (isValidName(name)){
			name = name.replaceAll(" ", "_");
			if (!Editor.getBlockList().containsKey(name)){
				FoodItemEditor editor = new FoodItemEditor(name, runnable, closeHandler);
				openEditors.put(name, editor);
			}
		}
	}
	
	private void addTool(){
		String name = nameDialog("Item");
		if (isValidName(name)){
			name = name.replaceAll(" ", "_");
			if (!Editor.getBlockList().containsKey(name)){
				RegularItemEditor editor = new ToolItemEditor(name, runnable, closeHandler);
				openEditors.put(name, editor);
			}
		}
	}
	
	@Override
	protected void edit() {
		String value = ((String) list.getSelectedValue()).replaceAll(" ", "_");
		try {
			if (!openEditors.containsKey(value)){
				ItemElement item = ItemElement.getFromName(value);
				ItemType type = item.item.type;
				RegularItemEditor editor;
				if (type == ItemType.food) editor = new FoodItemEditor(item, runnable, closeHandler);
				else if (type == ItemType.tool) editor = new ToolItemEditor(item, runnable, closeHandler);
				else editor = new RegularItemEditor(item, runnable, closeHandler);
				openEditors.put(value,editor);
			}
			else {
				openEditors.get(value).setVisible(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void delete() {
		String value = (String) list.getSelectedValue();
		int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete " + value + "?\r\n"
				+ "References to this object will not be updated, which might cause problems.", 
				"Warning", JOptionPane.YES_NO_OPTION);
		if (result == JOptionPane.YES_OPTION){
			ResourcePackIO.deleteFile("assets/modbuilder/models/item/" + value + ".json");
			ResourcePackIO.deleteFile("assets/modbuilder/items/" + value + ".json");
			removeElement(value);
			
			Editor.metaFile.resource.modbuilder.items.remove(value);
			Editor.metaFile.save();
			
			Editor.langFile.list.remove(LanguageFile.toLine(value, true));
			Editor.langFile.save();
		}
	}
	
	@Override
	public String updateList(){
		File folder = new File(ResourcePackIO.getURL("assets/modbuilder/items/"));
		if (folder.exists()){
			for (File file : folder.listFiles()){
				if (file.getAbsolutePath().endsWith(".json")){
					try {
						String name = file.getName().substring(0, file.getName().length() - 5);
						addElement(name, ItemElement.getFromName(name).getImage());
					} catch (Exception e) {
						e.printStackTrace();
						return "Could not find all item element objects for the item " + file.getName();
					}
				}
			}
		}
		return null;
	}
	
	@Override
	protected void createButtonPanel(JPanel buttonPanel, String button) {
		JButton toolButton = new ActivityButton("New Tool");
		toolButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addTool();
			}
		});
		buttonPanel.add(toolButton);
		
		JButton foodButton = new ActivityButton("New Food");
		foodButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addFood();
			}
		});
		buttonPanel.add(foodButton);
		
		super.createButtonPanel(buttonPanel, button);
	}
	
	private void saveItem(ItemElement item){
		ResourcePackIO.createFile(item.itemModel, "assets/modbuilder/models/item/" + item.name + ".json");
		ResourcePackIO.createFile(item.item, "assets/modbuilder/items/" + item.name + ".json");
		addElement(item.name, item.getImage());
		
		Editor.metaFile.resource.modbuilder.items.add(item.name);
		Editor.metaFile.save();
		
		Editor.langFile.list.add(LanguageFile.toLine(item.name, true));
		Editor.langFile.save();
	}
}
