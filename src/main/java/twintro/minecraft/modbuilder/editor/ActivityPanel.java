package twintro.minecraft.modbuilder.editor;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import twintro.minecraft.modbuilder.editor.generator.ResourcePackGenerator;

public abstract class ActivityPanel extends JPanel {
	public Map<String, ImageIcon> elements;
	protected JList list;
	
	public ActivityPanel(String header, String button) {
		this.setLayout(new BorderLayout(0, 0));
		elements = new HashMap<String, ImageIcon>();
		addElements(header, button);
	}
	
	protected void addElement(String name){
		elements.put(name, getImage(name));
		list.updateUI();
	}
	
	protected static ImageIcon getImage(String name){
		return resizeImage(new ImageIcon(ResourcePackGenerator.getTextureURL(
				"assets/modbuilder/textures/items/" + name + ".png")), 64, 64);
	}
	
	private static ImageIcon resizeImage(ImageIcon icon, int width, int height){
		Image img = icon.getImage();
		BufferedImage bi = toBufferedImage(img, width, height);
		return new ImageIcon(bi);
	}
	
	protected static BufferedImage toBufferedImage(Image img){
		return toBufferedImage(img, img.getWidth(null), img.getHeight(null));
	}
	
	private static BufferedImage toBufferedImage(Image img, int width, int height){
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics g = bi.createGraphics();
		g.drawImage(img, 0, 0, width, height, null);
		return bi;
	}
	
	private void addElements(String header, String button){
		JPanel mainPanel = new JPanel();
		this.add(mainPanel, BorderLayout.NORTH);
		mainPanel.setLayout(new BorderLayout(0, 0));
		
		JButton addButton = new ActivityButton(button);
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				add();
			}
		});
		mainPanel.add(addButton, BorderLayout.EAST);
		
		JLabel headerLabel = new JLabel(header);
		headerLabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
		mainPanel.add(headerLabel, BorderLayout.WEST);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.add(scrollPane, BorderLayout.CENTER);
		
		list = new JList();
		scrollPane.setViewportView(list);
		list.setVisibleRowCount(0);
		list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		list.setCellRenderer(new CustomListCellRenderer(this));
		list.setModel(new AbstractListModel() {
			public int getSize() {
				return elements.size();
			}
			public Object getElementAt(int index) {
				return elements.keySet().toArray()[index];
			}
		});
		list.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				if (e.getClickCount() == 2){
					edit();
				}
				if (e.getButton() == e.BUTTON3 && list.getSelectedIndex() != -1){
					JPopupMenu menu = new JPopupMenu();
					
					JMenuItem deleteItem = new JMenuItem("Delete");
					deleteItem.addActionListener(new ActionListener(){
						@Override
						public void actionPerformed(ActionEvent arg0) {
							delete();
						}
					});
					menu.add(deleteItem);
					
					JMenuItem editItem = new JMenuItem("Edit");
					editItem.addActionListener(new ActionListener(){
						@Override
						public void actionPerformed(ActionEvent arg0) {
							edit();
						}
					});
					menu.add(editItem);
					
					menu.show(ActivityPanel.this, e.getX(), e.getY());
				}
			}
		});
	}
	
	protected abstract void add();
	protected abstract void edit();
	protected abstract void delete();
	public abstract void updateList();
}
