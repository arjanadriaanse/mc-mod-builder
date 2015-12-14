package twintro.minecraft.modbuilder.editor.interfaces;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import twintro.minecraft.modbuilder.editor.generator.ResourcePackGenerator;

public class TexturesEditor{
	String name;
	BufferedImage image;
	TexturesActivityPanel parent;
	JFrame frame;
	JPanel panel;
	JColorChooser colorchooser = new JColorChooser();
	Color color = new Color(0,0,0);
	Color color2;
	Color color3;
	Color backgroundcolor = new Color(255,255,255);
	int size = 16;
	
	public TexturesEditor(String name, BufferedImage image, TexturesActivityPanel parent) {
		this.name = name;
		this.image = image;
		this.parent = parent;
		frame = buildFrame();
		panel = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponents(g);
				paintSelf(g);
			}
		};
		frame.add(panel);
		
	}
	
	public JFrame buildFrame(){
		JFrame f = new JFrame("Edit texture: " + name);
		f.setSize(384, 368);
		f.setVisible(true);
		f.setIconImage(image);
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(SystemColor.inactiveCaption);
		f.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmNew = new JMenuItem("New");
		mntmNew.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				newImage();
			}
		});
		JMenuItem mntmSave = new JMenuItem("Save");
		mntmSave.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				saveImage();
			}
		});
		
		mnFile.add(mntmNew);
		mnFile.add(mntmSave);
		
		JMenu mnEdit = new JMenu("Edit");
		menuBar.add(mnEdit);
		
		JMenuItem mntmSetP = new JMenuItem("Change pencil color");
		mntmSetP.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setColor();
			}
		});
		JMenuItem mntmSetB = new JMenuItem("Change background color");
		mntmSetB.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setBackgroundColor();
			}
		});
		
		mnEdit.add(mntmSetP);
		mnEdit.add(mntmSetB);
		
		
		return f;
	}
	
	public void paintSelf(Graphics g) {
		g.setColor(new Color(255,255,255));
		g.fillRect(0, 0, frame.getWidth(), frame.getHeight());
		g.setColor(backgroundcolor);
		g.fillRect(16, 16, size*16, size*16);
		g.setColor(color);
		g.fillRect(frame.getWidth()-96, 16, 64, 64);
		g.drawImage(resizeImage(image, size*16, size*16), 16, 16, null);
		g.setColor(new Color(0,0,0));
		g.drawRect(16, 16, size*16, size*16);
		
	}
	
	private static Image resizeImage(BufferedImage img, int width, int height){
		return img.getScaledInstance(width, height, 0);
	}
	
	public void newImage() {
		image = new BufferedImage(16,16,BufferedImage.TYPE_INT_ARGB);
		panel.repaint();
	}
	
	public void saveImage() {
		parent.elements.put(name, new ImageIcon(image));
		ResourcePackGenerator.addTexture(image, "assets/modbuilder/textures/" + name + ".png");
		parent.list.updateUI();
	}
	
	public void setColor() {
		Color new_color = colorchooser.showDialog(panel, "Choose pencil color", color);
		if (new_color!=null)
			color = new_color;
		panel.repaint();
	}
	
	public void setBackgroundColor() {
		Color new_color = colorchooser.showDialog(panel, "Choose background color", backgroundcolor);
		if (new_color!=null)
			backgroundcolor = new_color;
		panel.repaint();
	}
}