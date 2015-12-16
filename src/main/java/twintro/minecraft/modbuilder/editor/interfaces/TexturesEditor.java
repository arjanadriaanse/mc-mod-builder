package twintro.minecraft.modbuilder.editor.interfaces;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.SystemColor;
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

import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import twintro.minecraft.modbuilder.editor.ActivityPanel;
import twintro.minecraft.modbuilder.editor.generator.ResourcePackGenerator;

public class TexturesEditor{
	//TODO undo
	//TODO draw met muis ingedrukt
	//TODO load texture from file
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
	Thread thread;
	boolean mousepressed;
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
		panel = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponents(g);
				paintSelf(g);
			}
		};
		thread = new Thread() {
			@Override
			public void run() {
				while (mousepressed) {
					editPixels();
				}
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
		panel.addMouseListener(mouse);
		panel.addMouseMotionListener(mousemotion);
		f.addWindowListener(window);
		f.addKeyListener(key);
		f.add(panel);
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
		parent.addImage(new ImageIcon(image), name);
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
	
	public void onClick(MouseEvent me) {
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

	public void editPixels() {
		if (mouseevent!=null) {
			if (inRec(mouseevent.getX(),mouseevent.getY(),16,16,size*16,size*16)) {
				int x = (mouseevent.getX()-16)/size;
				int y = (mouseevent.getY()-16)/size;
				if (mousebutton == MouseEvent.BUTTON1)
					image.setRGB(x, y, color.getRGB());
				if (mousebutton == MouseEvent.BUTTON3)
					image.setRGB(x, y, new Color(0,0,0,0).getRGB());
				panel.repaint();
			}
		}
	}
	
	public boolean inRec(int pointx, int pointy, int recx, int recy, int recwidth, int recheight) {
		return pointx>=recx && pointy>=recy && pointx-recx<=recwidth && pointy-recy<=recheight;
	}
	
	public void onClose(WindowEvent we) {
        if (JOptionPane.showConfirmDialog(frame, 
                "Do you want to save the texture?", "Save texture?", 
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                saveImage();
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