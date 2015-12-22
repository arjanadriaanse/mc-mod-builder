package twintro.minecraft.modbuilder.editor.resources;

import javax.swing.ImageIcon;

public abstract class Element {
	public String name;
	
	@Override
	public String toString(){
		return name;
	}
	
	public abstract ImageIcon getImage();
}
