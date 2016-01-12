package twintro.minecraft.modbuilder.editor.interfaces.helperclasses;

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
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.AbstractListModel;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import twintro.minecraft.modbuilder.data.resources.models.BlockModelResource;
import twintro.minecraft.modbuilder.editor.ActivityPanel;
import twintro.minecraft.modbuilder.editor.CustomListCellRenderer;
import twintro.minecraft.modbuilder.editor.ListPanel;
import twintro.minecraft.modbuilder.editor.generator.ResourcePackGenerator;

public class BlockModelChooseWindow extends JFrame {
	ListPanel listPanel;
	BlockModelRunnable main;
	int modelType = 1;
	ImageIcon selectedImage;
	String selectedImageName;
	Image[] textures1 = new BufferedImage[6];
	Image[] textures2 = new BufferedImage[2];
	String[] textureNames1 = new String[6];
	String[] textureNames2 = new String[2];
	double[] rotation1 = new double[]{Math.PI/6,Math.PI/6};
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
			new Point(208,192)
	};
	Point[] loc2 = new Point[]{
			new Point( 64,0),
			new Point(192,0),
	        new Point(160,160)
	};

	public BlockModelChooseWindow(Map<String, ImageIcon> elements, BlockModelRunnable main){
		this.main = main;
		load();
		
		setBounds(100, 100, 450, 400);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Choose Block Model:");
		
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
		
		JPanel topButtonPanel = new JPanel();
		mainPanel.add(topButtonPanel, BorderLayout.PAGE_START);
		topButtonPanel.setPreferredSize(new Dimension(300, 64));
		topButtonPanel.setLayout(new FlowLayout());
		
		ButtonGroup buttonGroup = new ButtonGroup();
		JRadioButton button1 = new JRadioButton("full block model");
		JRadioButton button2 = new JRadioButton("cross model");
		if(modelType==1) button1.setSelected(true);
		if(modelType==2) button2.setSelected(true);
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
		topButtonPanel.add(button1);
		topButtonPanel.add(button2);

		JPanel botButtonPanel = new JPanel();
		mainPanel.add(botButtonPanel, BorderLayout.PAGE_END);
		botButtonPanel.setPreferredSize(new Dimension(300, 32));
		botButtonPanel.setLayout(new FlowLayout());
		
		JButton buttonOK = new JButton("Use this model");
		buttonOK.setSelected(true);
		buttonOK.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent ae){
				save();
			}
		});
		botButtonPanel.add(buttonOK);
		
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
		paintPanel.setPreferredSize(new Dimension(300, 304));
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
			double sh = Math.sin(rotation1[0]);
			double ch = Math.cos(rotation1[0]);
			double sv = Math.sin(rotation1[1]);
			double cv = Math.cos(rotation1[1]);
	        BufferedImage img0 = transformImage(textures1[0], w2*(ch+sh), w2*sv*(ch-sh), w2*(sh-ch),w2*sv*(ch+sh), 2-w2*sh, 2-cv/2-w2*sv*ch);
	        BufferedImage img1 = transformImage(textures1[1], w2*(-ch+sh), w2*sv*(ch+sh), 0,cv, 2-w2*sh, 2-cv/2-w2*sv*ch);
	        BufferedImage img2 = transformImage(textures1[2], w2*(sh+ch), w2*sv*(-sh+ch), 0,cv, 2-w2*ch, 2-cv/2+w2*sv*sh);
	        BufferedImage img3 = transformImage(textures1[3], w2*(ch-sh), w2*sv*(-ch-sh), 0,cv, 2+w2*sh, 2-cv/2+w2*sv*ch);
	        BufferedImage img4 = transformImage(textures1[4], w2*(-sh-ch), w2*sv*(sh-ch), 0,cv, 2+w2*ch, 2-cv/2-w2*sv*sh);
	        BufferedImage img5 = transformImage(textures1[5], w2*(ch+sh), w2*sv*(ch-sh), w2*(ch-sh),w2*sv*(-ch-sh), 2-w2*ch, 2+cv/2+w2*sv*sh);
	        
	        BufferedImage[] front = new BufferedImage[3];
	        BufferedImage[] back = new BufferedImage[3];
	        if (sv>=0) {front[0] = img0; back[0] = img5;}
	        else {front[0] = img5; back[0] = img0;}
	        if (ch>=sh ^ cv>=0) {front[1] = img1; back[1] = img3;}
	        else {front[1] = img3; back[1] = img1;}
	        if (-sh>=ch ^ cv>=0) {front[2] = img2; back[2] = img4;}
	        else {front[2] = img4; back[2] = img2;}
	        
	        for(BufferedImage img : back) {
		        g.drawImage(img, loc1[6].x-128, loc1[6].y-128, null);
	        }
	        for(BufferedImage img : front) {
		        g.drawImage(img, loc1[6].x-128, loc1[6].y-128, null);
	        }
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
	        BufferedImage img0 = transformImage(textures2[0], 2*ch,-2*sh*sv, 0, 2*cv, 2*Math.max(0,-ch), 2*(Math.max(0, sh*sv)+Math.max(0,-cv)));
	        BufferedImage img1 = transformImage(textures2[1], 2*sh, 2*ch*sv, 0, 2*cv, 2*Math.max(0,-sh), 2*(Math.max(0,-ch*sv)+Math.max(0,-cv)));
	        
	        if (ch*sh>=0 ^ cv<=0)
	        	g.clipRect(loc2[2].x-128,loc2[2].y-128,128,256);
	        else
		        g.clipRect(loc2[2].x,loc2[2].y-128,128,256);
	        g.drawImage(img1, loc2[2].x-(int) Math.round(Math.abs(64*sh)), loc2[2].y-(int)(64*(Math.abs(cv)+Math.abs(ch*sv))), null);
	        g.setClip(null);
	        g.clipRect(loc2[2].x-128,loc2[2].y-128,256,256);
	        g.drawImage(img0, loc2[2].x-(int) Math.round(Math.abs(64*ch)), loc2[2].y-(int)(64*(Math.abs(cv)+Math.abs(sh*sv))), null);
	        g.setClip(null);
	        if (ch*sh>=0 ^ cv<=0)
		        g.clipRect(loc2[2].x,loc2[2].y-128,128,256);
	        else
	        	g.clipRect(loc2[2].x-128,loc2[2].y-128,128,256);
	        g.drawImage(img1, loc2[2].x-(int) Math.round(Math.abs(64*sh)), loc2[2].y-(int)(64*(Math.abs(cv)+Math.abs(ch*sv))), null);
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
			if (selectedImage!=null && new Rectangle(loc1[i].x,loc1[i].y, 64, 64).contains(me.getPoint())) {
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
			if (selectedImage!=null && new Rectangle(loc2[i].x,loc2[i].y, 64, 64).contains(me.getPoint())) {
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
			int s = 32;
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
	
	public void save(){
		BlockModelResource model = new BlockModelResource();
		if(modelType==1){
			if (textures1[0]==null || textures1[1]==null || textures1[2]==null || textures1[3]==null || textures1[4]==null || textures1[5]==null) return;
			if (textures1[0]==textures1[1] && textures1[0]==textures1[2] && textures1[0]==textures1[3] && textures1[0]==textures1[4] && textures1[0]==textures1[5]){
				model.parent="block/cube_all";
				model.textures=new HashMap<String,String>();
				model.textures.put("all","modbuilder:"+textureNames1[0]);
			}
			else {
				model.parent="block/cube";
				model.textures=new HashMap<String,String>();
				model.textures.put("up",    "modbuilder:"+textureNames1[0]);
				model.textures.put("west",  "modbuilder:"+textureNames1[1]);
				model.textures.put("south", "modbuilder:"+textureNames1[2]);
				model.textures.put("east",  "modbuilder:"+textureNames1[3]);
				model.textures.put("north", "modbuilder:"+textureNames1[4]);
				model.textures.put("down",  "modbuilder:"+textureNames1[5]);
			}
		}
		if(modelType==2){
			if (textures2[0]==null || textures2[1]==null) return;
			model.parent="block/cross";
			model.textures=new HashMap<String,String>();
			model.textures.put("cross", "modbuilder:"+textureNames2[0]);
		}
		main.setModel(model);
		this.dispose();
	}
	
	public void load(){
		BlockModelResource model = main.getModel();
		if(model==null) return;
		if(model.parent.equals("block/cube")) {
			String[] name = new String[]{"up","west","south","east","north","down"};
			for(int i=0;i<6;i++){
				if (model.textures.get(name[i]).split(":")[0].equals("modbuilder")) {
					String loc=ResourcePackGenerator.resourcePackFolderDir + "assets/modbuilder/textures/" + model.textures.get(name[i]).split(":")[1] + ".png";
					try{
						Image img = ImageIO.read(new File(loc)).getScaledInstance(64, 64, 0);
						BufferedImage bi = new BufferedImage(64,64,BufferedImage.TYPE_INT_ARGB);
						bi.createGraphics().drawImage(img,0,0,null);
						textures1[i]=bi;
					} 
					catch (IOException e){
					    e.printStackTrace();
					}
					textureNames1[i]=model.textures.get(name[i]).split(":")[1];
				}
			}
			modelType=1;
			return;
		}
		if(model.parent.equals("block/cross")) {
			if (model.textures.get("cross").split(":")[0].equals("modbuilder")) {
				String loc=ResourcePackGenerator.resourcePackFolderDir + "assets/modbuilder/textures/" + model.textures.get("cross").split(":")[1] + ".png";
				try{
					Image img = ImageIO.read(new File(loc)).getScaledInstance(64, 64, 0);
					BufferedImage bi = new BufferedImage(64,64,BufferedImage.TYPE_INT_ARGB);
					bi.createGraphics().drawImage(img,0,0,null);
					textures2[0]=bi;
				} 
				catch (IOException e){
				    e.printStackTrace();
				}
				textureNames2[0]=model.textures.get("cross").split(":")[1];
				textures2[1]=textures2[0];
				textureNames2[1]=textureNames2[0];
			}
			modelType=2;
			return;
		}
		if(model.parent.equals("block/cube_all")) {
			if (model.textures.get("all").split(":")[0].equals("modbuilder")) {
				String loc=ResourcePackGenerator.resourcePackFolderDir + "assets/modbuilder/textures/" + model.textures.get("all").split(":")[1] + ".png";
				try{
					Image img = ImageIO.read(new File(loc)).getScaledInstance(64, 64, 0);
					BufferedImage bi = new BufferedImage(64,64,BufferedImage.TYPE_INT_ARGB);
					bi.createGraphics().drawImage(img,0,0,null);
					textures1[0]=bi;
				} 
				catch (IOException e){
				    e.printStackTrace();
				}
				textureNames1[0]=model.textures.get("all").split(":")[1];
				textures1[1]=textures1[0];
				textureNames1[1]=textureNames1[0];
				textures1[2]=textures1[1];
				textureNames1[2]=textureNames1[1];
				textures1[3]=textures1[2];
				textureNames1[3]=textureNames1[2];
				textures1[4]=textures1[3];
				textureNames1[4]=textureNames1[3];
				textures1[5]=textures1[4];
				textureNames1[5]=textureNames1[4];
			}
			modelType=1;
			return;
		}
	}
	
	@Override
	public void dispose() {
		main.blockModelChooserDispose();
		super.dispose();
	}
}