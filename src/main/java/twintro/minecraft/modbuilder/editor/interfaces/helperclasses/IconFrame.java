package twintro.minecraft.modbuilder.editor.interfaces.helperclasses;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class IconFrame extends JFrame {
	public IconFrame(){
		super();
		setIconImage(new ImageIcon("src/main/resources/icon.png").getImage());
	}
}
