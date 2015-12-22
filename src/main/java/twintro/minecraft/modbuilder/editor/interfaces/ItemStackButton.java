package twintro.minecraft.modbuilder.editor.interfaces;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import sun.awt.WindowClosingListener;
import twintro.minecraft.modbuilder.data.resources.recipes.ItemStackResource;
import twintro.minecraft.modbuilder.editor.resources.BlockElement;
import twintro.minecraft.modbuilder.editor.resources.ItemElement;

public class ItemStackButton extends JButton implements WindowListener, ActionListener{
	
	private boolean isProduct;
	List<ItemElement> items;
	List<BlockElement> blocks;
	public ItemStackResource item;
	ItemStackEditor editor = null;
	ItemStackButton thisButton = this;
	
	ItemStackButton(String s){
		super(s);
		//TODO remove dummy itemlist
		items = new ArrayList<ItemElement>();
		blocks = new ArrayList<BlockElement>();
		BlockElement dirt = new BlockElement();
		dirt.name = "dirt";
		blocks.add(dirt);
		ItemElement sMDR = new ItemElement();
		sMDR.name = "SuperMegaDeahRocket";
		items.add(sMDR);
		//end to do
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
			
			if (isProduct)
			editor = new ItemStackEditor(item, items, blocks, isProduct, this.getParent().getParent().getParent().getParent().getParent()); //rofl
			else 
			editor = new ItemStackEditor(item, items, blocks, isProduct, this.getParent().getParent().getParent().getParent().getParent().getParent().getParent()); //rofl
			
			editor.addWindowListener(this);
		}
	}

}
