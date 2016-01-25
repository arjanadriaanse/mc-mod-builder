package twintro.minecraft.modbuilder.editor.interfaces.editors;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import twintro.minecraft.modbuilder.editor.generator.ResourcePackIO;
import twintro.minecraft.modbuilder.editor.interfaces.choosewindows.ObjectRunnable;
import twintro.minecraft.modbuilder.editor.interfaces.helperclasses.IconFrame;
import twintro.minecraft.modbuilder.editor.resources.TextureObject;

public class OldTextureEditor extends IconFrame {
	String name;
	BufferedImage image;
	ObjectRunnable runnable;
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
	TexturePaint background = new TexturePaint(backgroundImage(),new Rectangle(0,0,16,16));
	Color[] colors = new Color[]{new Color(0,0,0),null,null,null};
	int[] sizes = new int[]{64,48,48,48,256};
	Point[] locs = new Point[]{
			new Point(288,48),
			new Point(288,128),
			new Point(288,192),
			new Point(288,256),
			new Point(16,48)};
	
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
	
	public OldTextureEditor(ObjectRunnable runnable){
		this.runnable = runnable;
		buildFrame();
	}
	
	public void open(String name, BufferedImage img){
		this.name = name;
		this.image = img;
		this.saved = true;
		setIconImage(image);
		setName("Edit texture: " + name);
		setVisible(true);
		panel.repaint();
	}
	
	public void buildFrame(){
		setSize(384, 368);
		setVisible(false);
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
		ActionListener action4 = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				fillImage();
			}
		};
		
		JButton button1 = new JButton("Save");
		JButton button2 = new JButton("Load");
		JButton button3 = new JButton("Clear");
		JButton button4 = new JButton("Fill");
		button1.setFocusable(false);
		button2.setFocusable(false);
		button3.setFocusable(false);
		button4.setFocusable(false);
		button1.addActionListener(action1);
		button2.addActionListener(action2);
		button3.addActionListener(action3);	
		button4.addActionListener(action4);	
		LayoutManager layout = new FlowLayout(FlowLayout.LEFT);
		panel.setLayout(layout);
		
		panel.add(button1);
		panel.add(button2);
		panel.add(button3);
		panel.add(button4);
		panel.addMouseListener(mouse);
		panel.addMouseMotionListener(mousemotion);
		addWindowListener(window);
		addKeyListener(key);
		add(panel);
	}
	
	public void paintSelf(Graphics2D g) {
		updateGUI();
		g.setColor(new Color(255,255,255));
		g.fillRect(0, 0, getWidth(), getHeight());
		for (int i=0;i<4;i++){
			if (colors[i]!=null) {
				g.setColor(colors[i]);
				g.fillRect((int) locs[i].getX(), (int) locs[i].getY(), sizes[i], sizes[i]);
				g.setColor(new Color(0,0,0));
				g.drawRect((int) locs[i].getX(), (int) locs[i].getY(), sizes[i], sizes[i]);
			}
		}
		g.setPaint(background);
		g.fillRect((int) locs[4].getX(), (int) locs[4].getY(), sizes[4], sizes[4]);
		g.drawImage(resizeImage(image, sizes[4], sizes[4]), (int) locs[4].getX(), (int) locs[4].getY(), null);
		g.setColor(new Color(0,0,0));
		g.drawRect((int) locs[4].getX(), (int) locs[4].getY(), sizes[4], sizes[4]);
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
		setIconImage(image);
		
		TextureObject texture = new TextureObject();
		texture.name = name;
		texture.image = new ImageIcon(image);
		runnable.run(texture);
	}
	
	public void loadImage() {
		JFileChooser menu = new JFileChooser();
		int result = menu.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION){
			File file = menu.getSelectedFile();
			if (file.exists()){
				if (file.getAbsolutePath().endsWith(".png")){
					ImageIcon icon = ResourcePackIO.resizeImage(new ImageIcon(file.getAbsolutePath()), 16, 16);
					BufferedImage img = ResourcePackIO.toBufferedImage(icon.getImage());
					String name = file.getName().substring(0, file.getName().length() - 4);
					open(name, img);
					panel.repaint();
					saveImage();
				}
			}
		}
	}

	public void fillImage(){
		for(int x=0;x<image.getWidth();x++)
			for(int y=0;y<image.getHeight();y++)
				image.setRGB(x,y, colors[0].getRGB());
		panel.repaint();
	}
	
	public void chooseColor() {
		Color new_color = JColorChooser.showDialog(panel, "Choose pencil color", colors[0]);
		if (new_color!=null) {
			if (new_color == colors[1])
				colors[1] = colors[0];
			else if (new_color == colors[2])
				colors[2] = colors[0];
			else if (new_color == colors[3])
				colors[3] = colors[0];
			else {
				colors[3] = colors[2];
				colors[2] = colors[1];
				colors[1] = colors[0];
			}
			colors[0] = new_color;
		}
		panel.repaint();
	}
	
	public void onClick(MouseEvent me) {
		int x = me.getX();
		int y = me.getY();
		if (me.getButton() == MouseEvent.BUTTON1) {
			Color temp_color = colors[0];
			if (inRec(x,y,(int) locs[0].getX(), (int) locs[0].getY(), sizes[0], sizes[0]))
				chooseColor();
			for (int i=1;i<4;i++)
				if (inRec(x,y,(int) locs[i].getX(), (int) locs[i].getY(), sizes[i], sizes[i]) && colors[i]!=null){
					colors[0] = colors[i];
					colors[i] = temp_color;
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
					image.setRGB(x, y, colors[0].getRGB());
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
			if (JOptionPane.showConfirmDialog(this, 
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
			setVisible(false);
		}
	}
}