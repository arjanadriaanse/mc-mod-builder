package twintro.minecraft.modbuilder.editor.interfaces;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.LayoutManager;
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
import javax.swing.JButton;
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

public class TextureEditor{
	//TODO undo
	//TODO load texture from file
	//TODO remove menu bar
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
	JColorChooser colorchooser = new JColorChooser();
	Color color = new Color(0,0,0);
	Color color2 = new Color(0,0,0);
	Color color3 = new Color(0,0,0);
	Color color4 = new Color(0,0,0);
	Color backgroundcolor = new Color(255,255,255);
	
	public TextureEditor(TexturesActivityPanel parent){
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
		
		JButton button1 = new JButton("Save");
		JButton button2 = new JButton("Load");
		JButton button3 = new JButton("Clear");
		
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
	
	public void paintSelf(Graphics g) {
		g.setColor(new Color(255,255,255));
		g.fillRect(0, 0, frame.getWidth(), frame.getHeight());
		g.setColor(backgroundcolor);
		g.fillRect(16, 16, 256, 256);
		g.setColor(color);
		g.fillRect(frame.getWidth()-96, 48, 64, 64);
		g.setColor(color2);
		g.fillRect(frame.getWidth()-96, 128, 48, 48);
		g.setColor(color3);
		g.fillRect(frame.getWidth()-96, 192, 48, 48);
		g.setColor(color4);
		g.fillRect(frame.getWidth()-96, 256, 48, 48);
		g.drawImage(resizeImage(image, 256, 256), 16, 48, null);
		g.setColor(new Color(0,0,0));
		g.drawRect(16, 48, 256, 256);
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
	
	public void loadImage() {
		
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
			if (inRec(x,y,frame.getWidth()-96, 48, 64, 64))
				chooseColor();
			if (inRec(x,y,frame.getWidth()-96, 128, 48, 48)) {
				color = color2;
				color2 = temp_color;
			}
			if (inRec(x,y,frame.getWidth()-96, 192, 48, 48)) {
				color = color3;
				color3 = temp_color;
			}
			if (inRec(x,y,frame.getWidth()-96, 256, 48, 48)) {
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