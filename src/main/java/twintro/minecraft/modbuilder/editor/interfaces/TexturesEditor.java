package twintro.minecraft.modbuilder.editor.interfaces;

import java.awt.Image;
import java.awt.SystemColor;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class TexturesEditor extends JFrame{
	Image image;
	
	public TexturesEditor(String name, Image image) {
		this.image = image;
		setName(name);
		setSize(512, 512);
		setVisible(true);
		setIconImage(image);
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(SystemColor.inactiveCaption);
		this.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmNew = new JMenuItem("New");
		mnFile.add(mntmNew);
		
	}
	
	
}