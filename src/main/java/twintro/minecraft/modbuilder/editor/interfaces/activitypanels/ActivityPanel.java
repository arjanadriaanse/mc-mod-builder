package twintro.minecraft.modbuilder.editor.interfaces.activitypanels;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.swing.AbstractListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import twintro.minecraft.modbuilder.editor.generator.ResourcePackIO;
import twintro.minecraft.modbuilder.editor.interfaces.helperclasses.ActivityButton;
import twintro.minecraft.modbuilder.editor.interfaces.helperclasses.ImageListCellRenderer;
import twintro.minecraft.modbuilder.editor.interfaces.helperclasses.ListPanel;

public abstract class ActivityPanel extends ListPanel {
	protected JList list;
	
	public ActivityPanel(String header, String button) {
		setLayout(new BorderLayout(0, 0));
		elements = new HashMap<String, ImageIcon>();
		
		JPanel mainPanel = new JPanel();
		this.add(mainPanel, BorderLayout.NORTH);
		mainPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		createButtonPanel(buttonPanel, button);
		
		mainPanel.add(buttonPanel, BorderLayout.EAST);
		
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
		list.setFixedCellWidth(128);
		list.setCellRenderer(new ImageListCellRenderer(this));
		list.setModel(new AbstractListModel() {
			@Override
			public int getSize() {
				return elements.size();
			}
			@Override
			public Object getElementAt(int index) {
				return get(elements.keySet(), index).replaceAll("_", " ");
			}
		});
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e){
				clickElement(e);
			}
		});
	}
	
	private static String get(Set<String> set, int index){
		int bonus = 0;
		while (true){
			String test = ((String) set.toArray()[0]);
			Set<String> later = new HashSet<String>();
			Set<String> earlier = new HashSet<String>();
			for (String s : set){
				int compare = test.compareTo(s);
				if (compare < 0)
					later.add(s);
				else if (compare > 0)
					earlier.add(s);
			}
			int pos = earlier.size() + bonus;
			if (pos == index) return test;
			else if (pos > index) set = earlier;
			else{
				set = later;
				bonus += earlier.size() + 1;
			}
		}
	}
	
	protected void addElement(String name, ImageIcon img){
		elements.put(name.replaceAll("_", " "), ResourcePackIO.resizeImage(img, 64, 64));
		list.updateUI();
	}
	
	protected void removeElement(String name){
		elements.remove(name);
		list.updateUI();
	}
	
	protected void createButtonPanel(JPanel buttonPanel, String button){
		JButton addButton = new ActivityButton(button);
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				add();
			}
		});
		buttonPanel.add(addButton);
	}
	
	private void clickElement(MouseEvent e){
		if (e.getClickCount() == 2){
			edit();
		}
		if (e.getButton() == MouseEvent.BUTTON3 && list.getSelectedIndex() != -1){
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
	
	protected String nameDialog(String kind){
		String name = JOptionPane.showInputDialog(kind + " name:");
		if (name != null){
			if (name.replaceAll(" ", "").length() > 0){
				if (!elements.containsKey(name)){
					return name;
				}
				else{
					int selected = JOptionPane.showConfirmDialog(this, "The name is already in use by another " + kind.toLowerCase() + ".", 
							"Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
					if (selected == JOptionPane.OK_OPTION)
						return nameDialog(kind);
				}
			}
			else{
				int selected = JOptionPane.showConfirmDialog(this, "The name is invalid.", 
						"Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
				if (selected == JOptionPane.OK_OPTION)
					return nameDialog(kind);
			}
		}
		return null;
	}
	
	protected boolean isValidName(String name){
		if (name == null) return false;
		for (char c : new char[]{'"','#',':','_'}){
			if (name.contains(c + "")){
				JOptionPane.showConfirmDialog(this, "Your name cannot include the character '" + c + "'.", 
						"Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
				return false;
			}
		}
		name = name.replaceAll(" ", "_");
		if (!elements.containsKey(name)){
			return true;
		}
		else{
			JOptionPane.showConfirmDialog(this, "The name is already in use.", 
					"Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
			return false;
		}
	}
	
	protected abstract void add();
	protected abstract void edit();
	protected abstract void delete();
	public abstract String updateList();
}
