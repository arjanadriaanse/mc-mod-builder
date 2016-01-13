package twintro.minecraft.modbuilder.editor.interfaces.helperclasses;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import sun.awt.WindowClosingListener;
import twintro.minecraft.modbuilder.data.resources.recipes.ItemStackResource;
import twintro.minecraft.modbuilder.editor.interfaces.editors.ItemStackEditor;
import twintro.minecraft.modbuilder.editor.resources.BlockElement;
import twintro.minecraft.modbuilder.editor.resources.ItemElement;

public class ItemStackButton extends JButton implements WindowListener, ActionListener{
	
	private boolean isProduct;
	List<ItemElement> items;
	List<BlockElement> blocks;
	public ItemStackResource item;
	ItemStackEditor editor = null;
	ItemStackButton thisButton = this;
	
	public ItemStackButton(String s, Set<String> itemss, Set<String> blockss){
		super(s);
		
		items = new ArrayList<ItemElement>();
		blocks = new ArrayList<BlockElement>();
		for (String str : itemss){
			try
			{
				items.add(ItemElement.getFromName(str));
			}catch(Exception e){}
		}
		for (String str : blockss){
			try
			{
				blocks.add(BlockElement.getFromName(str));
			}catch(Exception e){}
		}
		
		item = null;
		isProduct = false;
		this.addActionListener(this);
	}
	
	public boolean getIsProduct(){
		return isProduct;
	}
	public void setIsProduct(boolean value){
		isProduct = value;
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		if (editor.changed){
			item = editor.getItem();
			if (item.block!=null && !item.block.isEmpty()){
					if (item.amount != null && item.amount != 0){
						thisButton.setText(item.amount + " " + item.block);
					} else thisButton.setText(item.block);
				}
			else if (item.item !=null && !item.item.isEmpty()){
				if (item.amount != null && item.amount != 0){
					thisButton.setText(item.amount + " " + item.item);
				} else thisButton.setText(item.item);
			}
		}
		editor = null;
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (editor == null){
			
			editor = new ItemStackEditor(item, items, blocks, isProduct, javax.swing.SwingUtilities.getWindowAncestor(this)); //rofl
			editor.addWindowListener(this);
		}
	}

}
