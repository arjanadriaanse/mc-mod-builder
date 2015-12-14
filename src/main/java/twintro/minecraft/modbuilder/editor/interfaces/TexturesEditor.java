package twintro.minecraft.modbuilder.editor.interfaces;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class TexturesEditor{
	Image image;
	JFrame frame;	
	public TexturesEditor(String name, final Image image) {
		this.image = image;
		
		frame = BuildFrame();
		JPanel panel = new JPanel() {
			@Override
			public void paintComponents(Graphics g) {
				super.paintComponents(g);
				g.drawImage((BufferedImage)image, 128, 128, null);
			}
		};
		
		frame.add(panel);
		frame.setName("Edit texture: " + name);
		
	}
	
	public JFrame BuildFrame(){
		JFrame f = new JFrame();
		f.setSize(512, 512);
		f.setVisible(true);
		f.setIconImage(image);
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(SystemColor.inactiveCaption);
		f.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmNew = new JMenuItem("New");
		mnFile.add(mntmNew);
		
		return f;
	}
}