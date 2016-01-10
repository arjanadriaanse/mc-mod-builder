package twintro.minecraft.modbuilder.editor.interfaces;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractListModel;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import twintro.minecraft.modbuilder.editor.ActivityPanel;
import twintro.minecraft.modbuilder.editor.CustomListCellRenderer;
import twintro.minecraft.modbuilder.editor.ListPanel;

public class BlockModelChooseWindow extends JFrame {
	ListPanel listPanel;
	NewBlockEditor main;
	int modelType = 1;
	ImageIcon selectedImage;
	String selectedImageName;
	Image[] textures1 = new Image[6];
	Image[] textures2 = new Image[6];
	String[] textureNames1 = new String[6];
	String[] textureNames2 = new String[6];
	double[] rotation1 = new double[]{Math.PI/4,Math.PI/6};
	double[] rotation2 = new double[]{Math.PI/4,Math.PI/6};
	boolean rotating = false;
	Point mousecoords;
	Point[] loc1 = new Point[]{
			new Point( 64,  0),
			new Point(  0, 64),
			new Point( 64, 64),
			new Point(128, 64),
			new Point(192, 64),
			new Point( 64,128),
			new Point(160,112)
	};
	Point[] loc2 = new Point[]{
			new Point( 64,0),
			new Point(192,0),
	        new Point(160,160)
	};

	public BlockModelChooseWindow(Map<String, ImageIcon> elements, NewBlockEditor main){
		this.main = main;
		
		setBounds(100, 100, 450, 400);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Choose Block Model");
		
		listPanel = new ListPanel();
		listPanel.setLayout(new BorderLayout(0, 0));
		listPanel.elements = elements;
		getContentPane().add(listPanel, BorderLayout.LINE_END);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		listPanel.add(scrollPane, BorderLayout.LINE_END);
		
		JList list = new JList();
		scrollPane.setViewportView(list);
		list.setVisibleRowCount(0);
		list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		list.setFixedCellWidth(128);
		list.setCellRenderer(new CustomListCellRenderer(listPanel));
		list.setModel(new AbstractListModel() {
			public int getSize() {
				return listPanel.elements.size();
			}
			public Object getElementAt(int index) {
				return listPanel.elements.keySet().toArray()[index];
			}
		});
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e){
				String value = (String) ((JList) e.getSource()).getSelectedValue();
				chooseTexture(value);
			}
		});
		
		JPanel mainPanel = new JPanel();
		listPanel.add(mainPanel, BorderLayout.LINE_START);
		mainPanel.setPreferredSize(new Dimension(300, 400));
		mainPanel.setLayout(new BorderLayout(0,0));
		mainPanel.setVisible(true);
		
		JPanel buttonsPanel = new JPanel();
		mainPanel.add(buttonsPanel, BorderLayout.PAGE_START);
		buttonsPanel.setPreferredSize(new Dimension(300, 64));
		buttonsPanel.setLayout(new FlowLayout());
		
		ButtonGroup buttonGroup = new ButtonGroup();
		JRadioButton button1 = new JRadioButton("full block model");
		JRadioButton button2 = new JRadioButton("cross model");
		button1.setSelected(true);
		button1.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae){
				buttonPressed1(ae);
			}
		});
		button2.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae){
				buttonPressed2(ae);
			}
		});
		buttonGroup.add(button1);
		buttonGroup.add(button2);
		buttonsPanel.add(button1);
		buttonsPanel.add(button2);
		
		JPanel paintPanel = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponents(g);
				if (modelType==1)
					paint1(g);
				else if (modelType==2)
					paint2(g);
			}
		};
		mainPanel.add(paintPanel, BorderLayout.LINE_START);
		paintPanel.setPreferredSize(new Dimension(300, 336));
		paintPanel.addMouseListener(new MouseAdapter(){
			@Override
			public void mousePressed(MouseEvent me) {
				mouseclick(me);
			}
						
			@Override
			public void mouseReleased(MouseEvent me) {
				mouserelease(me);
			}
		});
		paintPanel.addMouseMotionListener(new MouseMotionAdapter(){
			@Override
			public void mouseDragged(MouseEvent me) {
				mousemove(me);
			}
		});
		
		setVisible(true);
	}
	
	public void paint1(Graphics g){
		for(int i=0;i<6;i++){
			g.drawRect(loc1[i].x,loc1[i].y, 64, 64);
			g.drawImage(textures1[i],loc1[i].x,loc1[i].y,null);
		}
		if (textures1[0]!=null && textures1[1]!=null && textures1[2]!=null && textures1[3]!=null && textures1[4]!=null && textures1[5]!=null){
			double w2 = Math.sqrt(2)/2;
			BufferedImage skew1 = transformImage(textures1[3], w2, -0.3, 0,0.85,w2,1.3);
	        BufferedImage skew2 = transformImage(textures1[2], w2, 0.3, 0,0.85,0,1);
	        BufferedImage skew3 = transformImage(textures1[0], w2, 0.3, -w2,0.3,w2,0.7);
	        
	        g.drawImage(skew1, loc1[6].x, loc1[6].y, null);
	        g.drawImage(skew2, loc1[6].x, loc1[6].y, null);
	        g.drawImage(skew3, loc1[6].x, loc1[6].y, null);
		}
	}
	
	public void paint2(Graphics g){
		for(int i=0;i<2;i++){
			g.drawRect(loc2[i].x,loc2[i].y, 64, 64);
			g.drawImage(textures2[i],loc2[i].x,loc2[i].y,null);
		}
		if (textures2[0]!=null && textures2[1]!=null) {
			double sh = Math.sin(rotation2[0]);
			double ch = Math.cos(rotation2[0]);
			double sv = Math.sin(rotation2[1]);
			double cv = Math.cos(rotation2[1]);
	        BufferedImage img1 = transformImage(textures2[0], 2*ch,-2*sh*sv, 0, 2*cv, 2*Math.max(0,-ch), 2*(Math.max(0, sh*sv)+Math.max(0,-cv)));
	        BufferedImage img2 = transformImage(textures2[1], 2*sh, 2*ch*sv, 0, 2*cv, 2*Math.max(0,-sh), 2*(Math.max(0,-ch*sv)+Math.max(0,-cv)));
	        
	        if (ch*sh>=0 ^ cv<=0)
	        	g.clipRect(loc2[2].x-128,loc2[2].y-128,128,256);
	        else
		        g.clipRect(loc2[2].x,loc2[2].y-128,128,256);
	        g.drawImage(img2, loc2[2].x-(int) Math.round(Math.abs(64*sh)), loc2[2].y-(int)(64*(Math.abs(cv)+Math.abs(ch*sv))), null);
	        g.setClip(null);
	        g.clipRect(loc2[2].x-128,loc2[2].y-128,256,256);
	        g.drawImage(img1, loc2[2].x-(int) Math.round(Math.abs(64*ch)), loc2[2].y-(int)(64*(Math.abs(cv)+Math.abs(sh*sv))), null);
	        g.setClip(null);
	        if (ch*sh>=0 ^ cv<=0)
		        g.clipRect(loc2[2].x,loc2[2].y-128,128,256);
	        else
	        	g.clipRect(loc2[2].x-128,loc2[2].y-128,128,256);
	        g.drawImage(img2, loc2[2].x-(int) Math.round(Math.abs(64*sh)), loc2[2].y-(int)(64*(Math.abs(cv)+Math.abs(ch*sv))), null);
	        g.setClip(null);
		}
	}
	
	public BufferedImage transformImage(Image img, double m00, double m10, double m01, double m11, double m02, double m12){
        AffineTransform at = new AffineTransform(m00, m10, m01, m11, 64*m02, 64*m12);
        AffineTransformOp op = new AffineTransformOp(at, new RenderingHints(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC));
        return op.filter((BufferedImage) img, null);
	}
	
	public void buttonPressed1(ActionEvent ae){
		modelType = 1;
		listPanel.repaint();
	}
	
	public void buttonPressed2(ActionEvent ae){
		modelType = 2;
		listPanel.repaint();
	}
	
	public void mouseclick(MouseEvent me){
		if (modelType == 1)
			mouseclick1(me);
		if (modelType == 2)
			mouseclick2(me);
	}
	
	public void mouseclick1(MouseEvent me){
		for(int i=0;i<6;i++){
			if (new Rectangle(loc1[i].x,loc1[i].y, 64, 64).contains(me.getPoint())) {
				textures1[i] = selectedImage.getImage();
				textureNames1[i] = selectedImageName;
				break;
			};
		}
		if (new Rectangle(loc1[6].x-32,loc1[6].y-48,64,96).contains(me.getPoint())) {
			rotating=true;
			mousecoords=me.getPoint();
		}
		listPanel.repaint();
	}
	
	public void mouseclick2(MouseEvent me){
		for(int i=0;i<2;i++){
			if (new Rectangle(loc2[i].x,loc2[i].y, 64, 64).contains(me.getPoint())) {
				textures2[i] = selectedImage.getImage();
				textureNames2[i] = selectedImageName;
				break;
			};
		}
		if (new Rectangle(loc2[2].x-64,loc2[2].y-96,128,192).contains(me.getPoint())) {
			rotating=true;
			mousecoords=me.getPoint();
		}
		listPanel.repaint();
	}
	
	public void mousemove(MouseEvent me){
		if (rotating) {
			Point coords = me.getPoint();
			int s = 16;
			if (modelType==1) {
				rotation1[0]+=((double)(coords.x-mousecoords.x))/s;
				rotation1[1]+=((double)(coords.y-mousecoords.y))/s;
				if (rotation1[0]>2*Math.PI) rotation1[0]-=2*Math.PI;
				if (rotation1[1]>2*Math.PI) rotation1[1]-=2*Math.PI;
				if (rotation1[0]<0) rotation1[0]+=2*Math.PI;
				if (rotation1[0]<0) rotation1[1]+=2*Math.PI;
			}
			if (modelType==2) {
				rotation2[0]+=((double)(coords.x-mousecoords.x))/s;
				rotation2[1]+=((double)(coords.y-mousecoords.y))/s;
				if (rotation2[0]>2*Math.PI) rotation2[0]-=2*Math.PI;
				if (rotation2[1]>2*Math.PI) rotation2[1]-=2*Math.PI;
				if (rotation2[0]<0) rotation2[0]+=2*Math.PI;
				if (rotation2[0]<0) rotation2[1]+=2*Math.PI;
			}
			mousecoords=coords;
			listPanel.repaint();
		}
	}
	
	public void mouserelease(MouseEvent me){
		rotating=false;
	}
	
	public void chooseTexture(String texture){
		selectedImageName = texture;
		selectedImage = listPanel.elements.get(texture);
		listPanel.repaint();
	}
}