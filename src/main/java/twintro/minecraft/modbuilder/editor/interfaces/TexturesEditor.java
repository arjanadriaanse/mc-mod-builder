package twintro.minecraft.modbuilder.editor.interfaces;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.beans.Visibility;

import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import twintro.minecraft.modbuilder.editor.ActivityPanel;
import twintro.minecraft.modbuilder.editor.generator.ResourcePackGenerator;

public class TexturesEditor{
	String name;
	BufferedImage image;
	TexturesActivityPanel parent;
	JFrame frame;
	JPanel panel;
	MouseAdapter mouse;
	JColorChooser colorchooser = new JColorChooser();
	Color color = new Color(0,0,0);
	Color color2 = new Color(0,0,0);
	Color color3 = new Color(0,0,0);
	Color color4 = new Color(0,0,0);
	Color backgroundcolor = new Color(255,255,255);
	int size = 16;
	
	public TexturesEditor(TexturesActivityPanel parent){
		this.parent = parent;
		frame = buildFrame();
		panel = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponents(g);
				paintSelf(g);
			}
		};
		mouse = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me) {
				click(me);
			}
			
			@Override
			public void mousePressed(MouseEvent me) {
				pressed(me);
			}
		};
		panel.addMouseListener(mouse);
		frame.add(panel);
	}
	
	public void open(String name, BufferedImage img){
		this.name = name;
		this.image = img;
		frame.setIconImage(image);
		frame.setName("Edit texture: " + name);
		frame.setVisible(true);
	}
	
	public JFrame buildFrame(){
		JFrame f = new JFrame();
		f.setSize(384, 368);
		f.setVisible(false);
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(SystemColor.inactiveCaption);
		f.setJMenuBar(menuBar);
		f.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
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
				chooseColor();
			}
		});
		JMenuItem mntmSetB = new JMenuItem("Change background color");
		mntmSetB.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				chooseBackgroundColor();
			}
		});
		JMenuItem mntmClear = new JMenuItem("Clear color memory");
		mntmSetB.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				clearColors();
			}
		});
		
		mnEdit.add(mntmSetP);
		mnEdit.add(mntmSetB);
		mnEdit.add(mntmClear);
		
		return f;
	}
	
	public void paintSelf(Graphics g) {
		g.setColor(new Color(255,255,255));
		g.fillRect(0, 0, frame.getWidth(), frame.getHeight());
		g.setColor(backgroundcolor);
		g.fillRect(16, 16, size*16, size*16);
		g.setColor(color);
		g.fillRect(frame.getWidth()-96, 16, 64, 64);
		g.setColor(color2);
		g.fillRect(frame.getWidth()-96, 96, 48, 48);
		g.setColor(color3);
		g.fillRect(frame.getWidth()-96, 160, 48, 48);
		g.setColor(color4);
		g.fillRect(frame.getWidth()-96, 224, 48, 48);
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
		parent.addImage(new ImageIcon(image), name, "assets/modbuilder/textures/");
	}
	
	public void chooseColor() {
		Color new_color = colorchooser.showDialog(panel, "Choose pencil color", color);
		if (new_color!=null) {
			if (new_color == color2)
				color2 = color;
			else if (color == color3)
				color3 = color;
			else if (color == color4)
				color4 = color;
			else {
				color4 = color3;
				color3 = color2;
				color2 = color;
			}
			color = new_color;
		}
		panel.repaint();
	}
	
	public void chooseBackgroundColor() {
		Color new_color = colorchooser.showDialog(panel, "Choose background color", backgroundcolor);
		if (new_color!=null)
			backgroundcolor = new_color;
		panel.repaint();
	}
	
	public void clearColors() {
		color2 = new Color(0,0,0);
		color3 = new Color(0,0,0);
		color4 = new Color(0,0,0);
		panel.repaint();
	}
	
	public void click(MouseEvent me) {
		int x = me.getX();
		int y = me.getY();
		if (me.getButton() == MouseEvent.BUTTON1) {
			Color temp_color = color;
			if (inRec(x,y,frame.getWidth()-96, 16, 64, 64))
				chooseColor();
			if (inRec(x,y,frame.getWidth()-96, 96, 48, 48)) {
				color = color2;
				color2 = temp_color;
			}
			if (inRec(x,y,frame.getWidth()-96, 160, 48, 48)) {
				color = color3;
				color3 = temp_color;
			}
			if (inRec(x,y,frame.getWidth()-96, 224, 48, 48)) {
				color = color4;
				color4 = temp_color;
			}
			panel.repaint();
		}
	}
	
	public void pressed(MouseEvent me) {
		if (inRec(me.getX(),me.getY(),16,16,size*16,size*16))
			clickOnImage(me);
	}
	
	public void clickOnImage(MouseEvent me) {
		int x = (me.getX()-16)/size;
		int y = (me.getY()-16)/size;
		if (me.getButton() == MouseEvent.BUTTON1)
			image.setRGB(x, y, color.getRGB());
		if (me.getButton() == MouseEvent.BUTTON3)
			image.setRGB(x, y, new Color(0,0,0,0).getRGB());
		panel.repaint();
	}
	
	public boolean inRec(int pointx, int pointy, int recx, int recy, int recwidth, int recheight) {
		return pointx>=recx && pointy>=recy && pointx-recx<=recwidth && pointy-recy<=recheight;
	}
}