package twintro.minecraft.modbuilder.editor.interfaces.editors;

import javax.swing.JFrame;

public class StructureEditor extends JFrame {
	String temporaryStorageString;
	
	public void chooseBlock(String result){
		temporaryStorageString = result;
	}
}
