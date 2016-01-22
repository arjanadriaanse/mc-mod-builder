package twintro.minecraft.modbuilder.editor.interfaces.helperclasses;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import twintro.minecraft.modbuilder.editor.Editor;

public class IconFrame extends JFrame {
	public IconFrame(){
		super();
		setIconImage(new ImageIcon("src/main/resources/icon.png").getImage());
	}
	
	@Override
	public void setTitle(String title) {
		super.setTitle(title.replaceAll("_", " "));
	}
	
	protected void setIcon(String material){
		if (material.startsWith("modbuilder:")){
			material = material.substring(11);
			if (Editor.getBlockList().containsKey(material))
				setIconImage(Editor.getBlockList().get(material).getImage());
			else if (Editor.getItemList().containsKey(material))
				setIconImage(Editor.getItemList().get(material).getImage());
			else
				setIconImage(new ImageIcon("src/main/resources/icon.png").getImage());
		}
		else
			setIconImage(new ImageIcon("src/main/resources/icon.png").getImage());
	}
}
