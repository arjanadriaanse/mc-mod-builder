package twintro.minecraft.modbuilder.editor.interfaces.helperclasses;

import java.awt.Container;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import sun.awt.WindowClosingListener;
import twintro.minecraft.modbuilder.data.resources.MaterialResource;
import twintro.minecraft.modbuilder.data.resources.recipes.ItemStackResource;
import twintro.minecraft.modbuilder.editor.ActivityPanel;
import twintro.minecraft.modbuilder.editor.Editor;
import twintro.minecraft.modbuilder.editor.generator.ResourcePackGenerator;
import twintro.minecraft.modbuilder.editor.interfaces.choosewindows.ItemStackChooseWindow;
import twintro.minecraft.modbuilder.editor.interfaces.choosewindows.ItemStackRunnable;
import twintro.minecraft.modbuilder.editor.interfaces.editors.ItemStackEditor;
import twintro.minecraft.modbuilder.editor.resources.BlockElement;
import twintro.minecraft.modbuilder.editor.resources.ItemElement;
import twintro.minecraft.modbuilder.editor.resources.MaterialResources;

public class ItemStackButton extends JButton{
	private boolean isProduct = false;
	private ItemStackChooseWindow itemStackChooser;
	private WindowClosingVerifierUser main;

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
	
	public ItemStackButton(String s, WindowClosingVerifierUser main){
		super(s);
		this.main = main;
		
		addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				click();
			}
		});
		
		setVerticalTextPosition(SwingConstants.BOTTOM);
	    setHorizontalTextPosition(SwingConstants.CENTER);
	    setMargin(new Insets(1,1,1,1));
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
			if (item.amount != null && item.amount > 1)
				setText(item.amount + " " + item.block);
			else 
				setText(item.block);
			setImage(MaterialResources.getImage(item.block));
		}
		else if (item.item != null && !item.item.isEmpty()){
			if (item.amount != null && item.amount > 1)
				setText(item.amount + " " + item.item);
			else 
				setText(item.item);
			setImage(MaterialResources.getImage(item.item));
		}
		else{
			setText("");
			setIcon(null);
		}
		main.change();
	}
	
	public void click(){
		if (itemStackChooser == null){
			if (item == null)
				itemStackChooser = new ItemStackChooseWindow(isProduct, runnable);
			else
				itemStackChooser = new ItemStackChooseWindow(isProduct, runnable, item);
		}
	}
	
	@Override
	public void setText(String text) {
		if (text != null && text != ""){
			if (item.container != null && item.container != "") 
				setToolTipText("<html>" + text + "<br>Container: " + item.container + "</html>");
			else 
				setToolTipText(text);
		}
		else setToolTipText(null);
		text = text.replace("modbuilder:", "");
		super.setText(MaterialResources.getName(text));
	}
	
	private void setImage(ImageIcon icon){
		if (icon == null) 
			setIcon(null);
		else
			setIcon(ActivityPanel.resizeImage(icon, 
				getWidth() / 16 * 2 / 3 * 16, 
				getHeight() / 16 * 2 / 3 * 16));
	}
}
