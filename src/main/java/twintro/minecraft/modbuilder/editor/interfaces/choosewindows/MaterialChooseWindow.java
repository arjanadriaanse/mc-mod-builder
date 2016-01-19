package twintro.minecraft.modbuilder.editor.interfaces.choosewindows;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;
import java.util.Set;

import javax.swing.AbstractListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import twintro.minecraft.modbuilder.editor.Editor;
import twintro.minecraft.modbuilder.editor.interfaces.editors.RegularItemEditor;
import twintro.minecraft.modbuilder.editor.interfaces.helperclasses.ImageListCellRenderer;
import twintro.minecraft.modbuilder.editor.interfaces.helperclasses.IconDialog;
import twintro.minecraft.modbuilder.editor.interfaces.helperclasses.IconFrame;
import twintro.minecraft.modbuilder.editor.interfaces.helperclasses.ListPanel;
import twintro.minecraft.modbuilder.editor.resources.MaterialResources;

public class MaterialChooseWindow extends IconDialog {
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
	
	private ObjectRunnable runnable;
	
	public MaterialChooseWindow(int type, ObjectRunnable runnable){
		this.runnable = runnable;

		setModal(true);
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
		new ListWindow(Editor.getItemList(), new ObjectRunnable(){
			@Override
			public void run(Object obj){
				choose((String) obj);
			}
		});
	}
	
	private void customBlock(){
		new ListWindow(Editor.getBlockList(), new ObjectRunnable(){
			@Override
			public void run(Object obj){
				choose((String) obj);
			}
		});
	}
	
	private void vanillaItem(){
		new ListWindow(MaterialResources.vanillaItems, new ObjectRunnable(){
			@Override
			public void run(Object obj){
				choose((String) obj);
			}
		});
	}
	
	private void vanillaBlock(){
		new ListWindow(MaterialResources.vanillaBlocks, new ObjectRunnable(){
			@Override
			public void run(Object obj){
				choose((String) obj);
			}
		});
	}
	
	private void other(){
		String material = JOptionPane.showInputDialog("Material name:");
		if (material != null){
			choose(material);
		}
	}
	
	private void none(){
		choose("");
	}
	
	private void choose(String value){
		runnable.run(value);
		dispose();
	}
}
