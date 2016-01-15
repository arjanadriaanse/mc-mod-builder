package twintro.minecraft.modbuilder.editor.interfaces.editors;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.TexturePaint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.beans.Visibility;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import twintro.minecraft.modbuilder.editor.ActivityPanel;
import twintro.minecraft.modbuilder.editor.generator.ResourcePackGenerator;
import twintro.minecraft.modbuilder.editor.interfaces.activitypanels.TexturesActivityPanel;
import twintro.minecraft.modbuilder.editor.interfaces.helperclasses.IconFrame;

public class TextureEditor extends IconFrame {
	//TODO undo
	
	String name;
	BufferedImage image;
	TexturesActivityPanel parent;
	JFrame frame;
	JPanel panel;
	MouseAdapter mouse;
	Integer mousebutton = MouseEvent.NOBUTTON;
	MouseEvent mouseevent;
	MouseMotionAdapter mousemotion;
	WindowAdapter window;
	KeyAdapter key;
	boolean mousepressed;
	boolean saved = true;
	JColorChooser colorchooser = new JColorChooser();
	Color color = new Color(0,0,0);
	Color color2 = new Color(0,0,0);
	Color color3 = new Color(0,0,0);
	Color color4 = new Color(0,0,0);
	TexturePaint background = new TexturePaint(backgroundImage(),new Rectangle(0,0,16,16));
	int imgsize = 256;
	int c1size = 64;
	int csize = 48;
	int[] imgloc = {16,48};
	int[] c1loc = {288,48};
	int[] c2loc = {288,128};
	int[] c3loc = {288,192};
	int[] c4loc = {288,256};
	
	public void updateGUI() {
		
	}
		
	public BufferedImage backgroundImage() {
		BufferedImage bi = new BufferedImage(2,2,BufferedImage.TYPE_INT_ARGB);
		bi.setRGB(0, 0, new Color(255,255,255).getRGB());
		bi.setRGB(0, 1, new Color(192,192,192).getRGB());
		bi.setRGB(1, 0, new Color(192,192,192).getRGB());
		bi.setRGB(1, 1, new Color(255,255,255).getRGB());
		return bi;
	}
	
	public TextureEditor(TexturesActivityPanel parent){
		this.parent = parent;
		frame = buildFrame();
	}
	
	public void open(String name, BufferedImage img){
		this.name = name;
		this.image = img;
		this.saved = true;
		frame.setIconImage(image);
		frame.setName("Edit texture: " + name);
		frame.setVisible(true);
	}
	
	public JFrame buildFrame(){
		JFrame f = new IconFrame();
		f.setSize(384, 368);
		f.setVisible(false);
		panel = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponents(g);
				paintSelf((Graphics2D) g);
			}
		};
		mouse = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent me) {
				onClick(me);
			}
			@Override
			public void mousePressed(MouseEvent me) {
				mouseevent = me;
				mousebutton = me.getButton();
				mousepressed = true;
				Thread thread = new Thread() {
					@Override
					public void run() {
						while (mousepressed) {
							editPixels();
						}
					}
				};
				thread.start();
			}
			@Override
			public void mouseReleased(MouseEvent me) {
				mousebutton = 0;
				mousepressed = false;
			}
		};
		mousemotion = new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent me) {
				mouseevent = me;
			}
		};
		window = new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				onClose(we);
			}
		};
		key = new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent ke) {
				onKeyPress(ke);
			}
		};
		ActionListener action1 = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				saveImage();
			}
		};
		ActionListener action2 = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				loadImage();
			}
		};
		ActionListener action3 = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				newImage();
			}
		};
		
		JButton button1 = new JButton("Save");
		JButton button2 = new JButton("Load");
		JButton button3 = new JButton("Clear");
		button1.setFocusable(false);
		button2.setFocusable(false);
		button3.setFocusable(false);
		button1.addActionListener(action1);
		button2.addActionListener(action2);
		button3.addActionListener(action3);	
		LayoutManager layout = new FlowLayout(FlowLayout.LEFT);
		panel.setLayout(layout);
		
		panel.add(button1);
		panel.add(button2);
		panel.add(button3);
		panel.addMouseListener(mouse);
		panel.addMouseMotionListener(mousemotion);
		f.addWindowListener(window);
		f.addKeyListener(key);
		f.add(panel);
		return f;
	}
	
	public void paintSelf(Graphics2D g) {
		updateGUI();
		g.setColor(new Color(255,255,255));
		g.fillRect(0, 0, frame.getWidth(), frame.getHeight());
		g.setPaint(background);
		g.fillRect(imgloc[0], imgloc[1], imgsize, imgsize);
		g.setColor(color);
		g.fillRect(c1loc[0], c1loc[1], c1size, c1size);
		g.setColor(color2);
		g.fillRect(c2loc[0], c2loc[1], csize, csize);
		g.setColor(color3);
		g.fillRect(c3loc[0], c3loc[1], csize, csize);
		g.setColor(color4);
		g.fillRect(c4loc[0], c4loc[1], csize, csize);
		g.drawImage(resizeImage(image, imgsize, imgsize), imgloc[0], imgloc[1], null);
		g.setColor(new Color(0,0,0));
		g.drawRect(imgloc[0], imgloc[1], imgsize, imgsize);
		g.drawRect(c1loc[0], c1loc[1], c1size, c1size);
		g.drawRect(c2loc[0], c2loc[1], csize, csize);
		g.drawRect(c3loc[0], c3loc[1], csize, csize);
		g.drawRect(c4loc[0], c4loc[1], csize, csize);
	}
	
	private static Image resizeImage(BufferedImage img, int width, int height){
		return img.getScaledInstance(width, height, 0);
	}
	
	public void newImage() {
		image = new BufferedImage(16,16,BufferedImage.TYPE_INT_ARGB);
		panel.repaint();
	}
	
	public void saveImage() {
		saved=true;
		frame.setIconImage(image);
		parent.addImage(new ImageIcon(image), name);
	}
	
	public void loadImage() {
		JFileChooser menu = new JFileChooser();
		int result = menu.showOpenDialog(frame);
		if (result == JFileChooser.APPROVE_OPTION){
			File file = menu.getSelectedFile();
			if (file.exists()){
				if (file.getAbsolutePath().endsWith(".png")){
					ImageIcon icon = ActivityPanel.resizeImage(new ImageIcon(file.getAbsolutePath()), 16, 16);
					BufferedImage img = ActivityPanel.toBufferedImage(icon.getImage());
					String name = file.getName().substring(0, file.getName().length() - 4);
					open(name, img);
					panel.repaint();
					saveImage();
				}
			}
		}
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
	
	public void clearColors() {
		color2 = new Color(0,0,0);
		color3 = new Color(0,0,0);
		color4 = new Color(0,0,0);
		panel.repaint();
	}
	
	public void onClick(MouseEvent me) {
		int x = me.getX();
		int y = me.getY();
		if (me.getButton() == MouseEvent.BUTTON1) {
			Color temp_color = color;
			if (inRec(x,y,c1loc[0], c1loc[1], c1size, c1size))
				chooseColor();
			if (inRec(x,y,c2loc[0], c2loc[1], csize, csize)) {
				color = color2;
				color2 = temp_color;
			}
			if (inRec(x,y,c3loc[0], c3loc[1], csize, csize)) {
				color = color3;
				color3 = temp_color;
			}
			if (inRec(x,y,c4loc[0], c4loc[1], csize, csize)) {
				color = color4;
				color4 = temp_color;
			}
			panel.repaint();
		}
	}

	public void editPixels() {
		if (mouseevent!=null) {
			if (inRec(mouseevent.getX(),mouseevent.getY(),16,48,256,256)) {
				int x = (mouseevent.getX()-16)/16;
				int y = (mouseevent.getY()-48)/16;
				if (mousebutton == MouseEvent.BUTTON1)
					image.setRGB(x, y, color.getRGB());
				if (mousebutton == MouseEvent.BUTTON3)
					image.setRGB(x, y, new Color(0,0,0,0).getRGB());
				saved=false;
				panel.repaint();
			}
		}
	}
	
	public boolean inRec(int pointx, int pointy, int recx, int recy, int recwidth, int recheight) {
		return pointx>=recx && pointy>=recy && pointx-recx<recwidth && pointy-recy<recheight;
	}
	
	public void onClose(WindowEvent we) {
		if (!saved) {
			if (JOptionPane.showConfirmDialog(frame, 
                "Do you want to save the texture?", "Save texture?", 
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                saveImage();
            }
		}
	}
	
	public void onKeyPress(KeyEvent ke) {
		int key = ke.getKeyCode();
		if (ke.isControlDown()) {
			if (key==KeyEvent.VK_N)
				newImage();
			if (key==KeyEvent.VK_S)
				saveImage();
			if (key==KeyEvent.VK_Y)
				; //TODO redo
			if (key==KeyEvent.VK_Z)
				; //TODO undo
			
		}
		if (key==KeyEvent.VK_ENTER) {
			saveImage();
			frame.setVisible(false);
		}
	}
}