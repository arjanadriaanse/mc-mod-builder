package twintro.minecraft.modbuilder.editor;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public abstract class ActivityPanel extends JPanel {
	public Map<String, ImageIcon> elements;
	protected JList list;
	
	public ActivityPanel(String header, String button) {
		this.setLayout(new BorderLayout(0, 0));
		elements = new HashMap<String, ImageIcon>();
		elements.put("climber", getImage("climber"));
		addElements(header, button);
	}
	
	protected ImageIcon getImage(String name){
		return resizeImage(new ImageIcon(getClass().getResource("/editor/" + name + ".png")), 64, 64);
	}
	
	private ImageIcon resizeImage(ImageIcon icon, int width, int height){
		Image img = icon.getImage();
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics g = bi.createGraphics();
		g.drawImage(img, 0, 0, width, height, null);
		return new ImageIcon(bi);
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
		list.setFont(new Font("Tahoma", Font.PLAIN, 47));
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
	}
	
	protected abstract void add();
}
