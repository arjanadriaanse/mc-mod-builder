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
import twintro.minecraft.modbuilder.editor.Editor;
import twintro.minecraft.modbuilder.editor.interfaces.choosewindows.ItemStackChooseWindow;
import twintro.minecraft.modbuilder.editor.interfaces.choosewindows.ItemStackRunnable;
import twintro.minecraft.modbuilder.editor.interfaces.editors.ItemStackEditor;
import twintro.minecraft.modbuilder.editor.resources.BlockElement;
import twintro.minecraft.modbuilder.editor.resources.ItemElement;

public class ItemStackButton extends JButton{
	private boolean isProduct = false;
	private ItemStackChooseWindow itemStackChooser;

	public ItemStackResource item;
	
	private ItemStackRunnable runnable = new ItemStackRunnable() {
		@Override
		public void chooseItemStack(ItemStackResource item) {
			chooseItem(item);
		}

		@Override
		public void itemStackChooserDispose() {
			itemStackChooser = null;
		}
	};
	
	public ItemStackButton(String s){
		super(s);
		this.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				click();
			}
		});
	}
	
	public boolean isProduct(){
		return isProduct;
	}
	
	public void setProduct(){
		isProduct = true;
	}
	
	public void chooseItem(ItemStackResource item){
		this.item = item;
		if (item.block != null && !item.block.isEmpty()){
			if (item.amount != null && item.amount > 1){
				setText(item.amount + " " + item.block);
			}
			else setText(item.block);
		}
		else if (item.item != null && !item.item.isEmpty()){
			if (item.amount != null && item.amount > 1){
				setText(item.amount + " " + item.item);
			}
			else setText(item.item);
		}
		else{
			setText("");
		}
	}
	
	public void click(){
		if (itemStackChooser == null){
			if (item == null)
				itemStackChooser = new ItemStackChooseWindow(isProduct, runnable);
			else
				itemStackChooser = new ItemStackChooseWindow(isProduct, runnable, item);
		}
	}

}
