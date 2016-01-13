package twintro.minecraft.modbuilder.editor.interfaces.editors;

import java.util.Set;

import javax.swing.JFrame;

import twintro.minecraft.modbuilder.editor.interfaces.activitypanels.StructureActivityPanel;

public class StructureEditor extends JFrame {
	String temporaryStorageString;
	
	public StructureEditor(String name, StructureActivityPanel parent, Set<String> blocks) {
	}

	public void chooseBlock(String result){
		temporaryStorageString = result;
	}
}
