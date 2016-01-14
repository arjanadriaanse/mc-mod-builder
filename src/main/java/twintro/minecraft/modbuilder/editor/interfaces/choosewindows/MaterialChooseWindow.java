package twintro.minecraft.modbuilder.editor.interfaces.choosewindows;

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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import twintro.minecraft.modbuilder.editor.CustomListCellRenderer;
import twintro.minecraft.modbuilder.editor.Editor;
import twintro.minecraft.modbuilder.editor.ListPanel;
import twintro.minecraft.modbuilder.editor.interfaces.editors.RegularItemEditor;
import twintro.minecraft.modbuilder.editor.resources.MaterialResources;

public class MaterialChooseWindow extends JFrame {
	public static final int ITEMS_ONLY = 0;
	public static final int BLOCKS_ONLY = 1;
	public static final int ITEMS_AND_BLOCKS = 2;
	public static final int ITEMS_BLOCKS_NONE = 3;

	private static final String customItemsTooltip = "";//TODO
	private static final String customBlocksTooltip = "";//TODO
	private static final String vanillaItemsTooltip = "";//TODO
	private static final String vanillaBlocksTooltip = "";//TODO
	private static final String otherTooltip = "";//TODO
	private static final String noneTooltip = "";//TODO
	
	private MaterialRunnable runnable;
	private ListWindow listWindow = null;
	
	public MaterialChooseWindow(int type, MaterialRunnable runnable){
		this.runnable = runnable;
		
		if (type == ITEMS_AND_BLOCKS || type == ITEMS_BLOCKS_NONE)
			setBounds(100, 100, 300, 120);
		else
			setBounds(100, 100, 300, 90);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Choose Material");
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(0, 2, 5, 5));
		
		if (type != BLOCKS_ONLY){
			JButton customItemButton = new JButton("Custom item");
			customItemButton.setToolTipText(customItemsTooltip);
			panel.add(customItemButton);
			customItemButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					customItem();
				}
			});
			
			JButton itemButton = new JButton("Vanilla item");
			itemButton.setToolTipText(vanillaItemsTooltip);
			panel.add(itemButton);
			itemButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					vanillaItem();
				}
			});
		}
		
		if (type != ITEMS_ONLY){
			JButton customBlockButton = new JButton("Custom block");
			customBlockButton.setToolTipText(customBlocksTooltip);
			panel.add(customBlockButton);
			customBlockButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					customBlock();
				}
			});
			
			JButton blockButton = new JButton("Vanilla block");
			blockButton.setToolTipText(vanillaBlocksTooltip);
			panel.add(blockButton);
			blockButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					vanillaBlock();
				}
			});
		}
		
		JButton otherButton = new JButton("Other");
		otherButton.setToolTipText(otherTooltip);
		panel.add(otherButton);
		otherButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				other();
			}
		});
		
		if (type == ITEMS_BLOCKS_NONE){
			JButton noneButton = new JButton("None");
			noneButton.setToolTipText(noneTooltip);
			panel.add(noneButton);
			noneButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					none();
				}
			});
		}
		
		setVisible(true);
	}
	
	private void customItem(){
		if (listWindow == null){
			listWindow = new ListWindow(Editor.ItemPanel.elements, this);
		}
	}
	
	private void customBlock(){
		if (listWindow == null){
			listWindow = new ListWindow(Editor.BlockPanel.elements, this);
		}
	}
	
	private void vanillaItem(){
		if (listWindow == null){
			listWindow = new ListWindow(MaterialResources.vanillaItems, this);
		}
	}
	
	private void vanillaBlock(){
		if (listWindow == null){
			listWindow = new ListWindow(MaterialResources.vanillaBlocks, this);
		}
	}
	
	private void other(){
		if (listWindow == null){
			String material = JOptionPane.showInputDialog("Material name:");
			if (material != null){
				choose(material);
			}
		}
	}
	
	private void none(){
		choose("");
	}
	
	public void listWindowDispose(){
		listWindow = null;
	}
	
	public void choose(String value){
		runnable.chooseMaterial(value);
		dispose();
	}
	
	@Override
	public void dispose() {
		if (listWindow != null) listWindow.dispose();
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
						choose(MaterialResources.getId(value));
					}
				}
			});
		}
		
		private void choose(String value){
			main.choose(value);
		}
		
		@Override
		public void dispose() {
			main.listWindowDispose();
			super.dispose();
		}
	}
}
