package twintro.minecraft.modbuilder.editor.interfaces.helperclasses;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;
import java.util.Set;

import javax.swing.AbstractListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import twintro.minecraft.modbuilder.editor.CustomListCellRenderer;
import twintro.minecraft.modbuilder.editor.Editor;
import twintro.minecraft.modbuilder.editor.ListPanel;
import twintro.minecraft.modbuilder.editor.resources.VanillaElements;

public class MaterialChooseWindow extends JFrame {
	public static final int ITEMS_ONLY = 0;
	public static final int BLOCKS_ONLY = 1;
	public static final int ITEMS_AND_BLOCKS = 2;
	
	private Editor main;
	private boolean listWindowOpen = false;
	private MaterialRunnable runnable;
	
	public MaterialChooseWindow(int type, Editor main, MaterialRunnable runnable){
		this.main = main;
		this.runnable = runnable;
		
		setBounds(100, 100, 600, 100);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Choose Material");
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(1, 0, 5, 0));
		
		if (type != 1){
			JButton customItemButton = new JButton("Custom item");
			panel.add(customItemButton);
			customItemButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					customItem();
				}
			});
			
			JButton itemButton = new JButton("Vanilla item");
			panel.add(itemButton);
			itemButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					vanillaItem();
				}
			});
		}
		
		if (type != 0){
			JButton customBlockButton = new JButton("Custom block");
			panel.add(customBlockButton);
			customBlockButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					customBlock();
				}
			});
			
			JButton blockButton = new JButton("Vanilla block");
			panel.add(blockButton);
			blockButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					vanillaBlock();
				}
			});
		}
		
		setVisible(true);
	}
	
	private void customItem(){
		if (!listWindowOpen){
			new ListWindow(main.ItemPanel.elements, this);
			listWindowOpen = true;
		}
	}
	
	private void customBlock(){
		if (!listWindowOpen){
			new ListWindow(main.BlockPanel.elements, this);
			listWindowOpen = true;
		}
	}
	
	private void vanillaItem(){
		if (!listWindowOpen){
			new ListWindow(VanillaElements.vanillaItems, this);
			listWindowOpen = true;
		}
	}
	
	private void vanillaBlock(){
		if (!listWindowOpen){
			new ListWindow(VanillaElements.vanillaBlocks, this);
			listWindowOpen = true;
		}
	}
	
	public void listWindowDispose(){
		listWindowOpen = false;
	}
	
	public void choose(String value){
		runnable.chooseMaterial(value);
		dispose();
	}
	
	@Override
	public void dispose() {
		runnable.materialChooserDispose();
		super.dispose();
	}
	
	public static class ListWindow extends JFrame {
		JPanel panel;
		JList list;
		String[] values;
		MaterialChooseWindow main;
		
		private ListWindow(JPanel startPanel, MaterialChooseWindow main){
			panel = startPanel;
			this.main = main;
			
			setBounds(100, 100, 300, 200);
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			setTitle("Choose Material");
			
			panel = new ListPanel();
			panel.setLayout(new BorderLayout(0, 0));
			getContentPane().add(panel, BorderLayout.CENTER);
			
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			panel.add(scrollPane, BorderLayout.CENTER);
			
			list = new JList();
			scrollPane.setViewportView(list);
			list.setVisibleRowCount(0);
			list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
			
			setVisible(true);
		}
		
		public ListWindow(Map<String, ImageIcon> elements, MaterialChooseWindow main){
			this(new ListPanel(), main);
			
			((ListPanel)panel).elements = elements;
			
			list.setFixedCellWidth(128);
			list.setCellRenderer(new CustomListCellRenderer((ListPanel)panel));
			list.setModel(new AbstractListModel() {
				public int getSize() {
					return ((ListPanel)panel).elements.size();
				}
				public Object getElementAt(int index) {
					return ((ListPanel)panel).elements.keySet().toArray()[index];
				}
			});
			list.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e){
					if (e.getClickCount() == 2){
						String value = (String) ((JList) e.getSource()).getSelectedValue();
						choose("modbuilder:" + value);
					}
				}
			});
		}
		
		public ListWindow(String[] elements, MaterialChooseWindow main){
			this(new JPanel(), main);
			
			values = elements;

			list.setFixedCellWidth(128);
			list.setModel(new AbstractListModel() {
				public int getSize() {
					return values.length;
				}
				public Object getElementAt(int index) {
					return values[index];
				}
			});
			list.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e){
					if (e.getClickCount() == 2){
						String value = (String) ((JList) e.getSource()).getSelectedValue();
						choose(VanillaElements.getId(value));
					}
				}
			});
		}
		
		private void choose(String value){
			main.choose(value);
			dispose();
		}
		
		@Override
		public void dispose() {
			main.listWindowDispose();
			super.dispose();
		}
	}
}
