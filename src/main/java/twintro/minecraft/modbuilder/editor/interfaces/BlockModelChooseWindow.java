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
	String modelType = "cube_all";
	String selectedImageName;
	ImageIcon selectedImage;
	Image[] textures = new Image[6];
	String[] textureNames = new String[6];

	public BlockModelChooseWindow(Map<String, ImageIcon> elements, NewBlockEditor main){
		this.main = main;
		
		setBounds(100, 100, 450, 300);
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
		mainPanel.setPreferredSize(new Dimension(300, 300));
		mainPanel.setLayout(new BorderLayout(0,0));
		mainPanel.setVisible(true);
		
		JPanel buttonsPanel = new JPanel();
		mainPanel.add(buttonsPanel, BorderLayout.PAGE_START);
		buttonsPanel.setPreferredSize(new Dimension(300, 50));
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
				if (modelType=="cube_all")
					paintFullBlock(g);
				else if (modelType=="cross")
					paintCross(g);
			}
		};
		mainPanel.add(paintPanel, BorderLayout.CENTER);
		paintPanel.setPreferredSize(new Dimension(300, 250));
		paintPanel.addMouseListener(new MouseAdapter(){
			@Override
			public void mousePressed(MouseEvent me) {
				click(me);
			}
		});
		
		setVisible(true);
	}
	
	public void paintFullBlock(Graphics g){
		g.drawRect(64, 0, 64, 64);
		g.drawRect(0, 64, 64, 64);
		g.drawRect(64, 64, 64, 64);
		g.drawRect(128, 64, 64, 64);
		g.drawRect(192, 64, 64, 64);
		g.drawRect(64, 128, 64, 64);
		g.drawImage(textures[0],50,0,null);
		g.drawImage(textures[1],150,0,null);
	}
	
	public void paintCross(Graphics g){
		g.drawRect(50, 0, 64, 64);
		g.drawRect(150, 0, 64, 64);
		g.drawImage(textures[0],50,0,null);
		g.drawImage(textures[1],150,0,null);
		
		if (textures[0]!=null && textures[1]!=null) {
	        BufferedImage skew1 = new BufferedImage(textures[0].getWidth(null), textures[0].getHeight(null), BufferedImage.TYPE_INT_ARGB);
	        BufferedImage skew2 = new BufferedImage(textures[1].getWidth(null), textures[1].getHeight(null), BufferedImage.TYPE_INT_ARGB);
	
	        double skewY1 = 0.3d;
	        double skewY2 = -skewY1;
	        double y1 = (skewY1 < 0) ? -skewY1 * textures[0].getWidth(null) : 0;
	        double y2 = (skewY2 < 0) ? -skewY2 * textures[1].getWidth(null) : 0;
	        AffineTransform at1 = AffineTransform.getTranslateInstance(0, y1);
	        AffineTransform at2 = AffineTransform.getTranslateInstance(0, y2);
	        at1.shear(0, skewY1);
	        at2.shear(0, skewY2);
	        AffineTransformOp op1 = new AffineTransformOp(at1, new RenderingHints(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC));
	        AffineTransformOp op2 = new AffineTransformOp(at2, new RenderingHints(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC));
	        skew1 = op1.filter((BufferedImage) textures[0], null);
	        skew2 = op2.filter((BufferedImage) textures[1], null);
	        
	        g.clipRect(100,100,32,200);
	        g.drawImage(skew1, 100, 100, null);
	        g.setClip(null);
	        g.clipRect(100,100,64,200);
	        g.drawImage(skew2, 100, 100, null);
	        g.setClip(null);
	        g.clipRect(132,100,32,200);
	        g.drawImage(skew1, 100, 100, null);
	        g.setClip(null);
		}
	}
	
	public void buttonPressed1(ActionEvent ae){
		modelType = "cube_all";
		listPanel.repaint();
	}
	
	public void buttonPressed2(ActionEvent ae){
		modelType = "cross";
		listPanel.repaint();
	}
	
	public void click(MouseEvent me){
		if(modelType == "cube_all")
			clickFullBlock(me);
		if(modelType == "cross")
			clickCross(me);
	}
	
	public void clickFullBlock(MouseEvent me){
		
	}
	
	public void clickCross(MouseEvent me){
		if (new Rectangle(50,0,64,64).contains(me.getPoint())) {
			textures[0] = selectedImage.getImage();
			textureNames[0] = selectedImageName;
		};
		if (new Rectangle(150,0,64,64).contains(me.getPoint())) {
			textures[1] = selectedImage.getImage();
			textureNames[1] = selectedImageName;
		};
		listPanel.repaint();
	}
	
	public void chooseTexture(String texture){
		selectedImageName = texture;
		selectedImage = listPanel.elements.get(texture);
		listPanel.repaint();
	}
}