package twintro.minecraft.modbuilder.editor.interfaces.helperclasses;

import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import twintro.minecraft.modbuilder.data.resources.recipes.ItemStackResource;
import twintro.minecraft.modbuilder.editor.generator.ResourcePackIO;
import twintro.minecraft.modbuilder.editor.interfaces.choosewindows.ItemStackChooseWindow;
import twintro.minecraft.modbuilder.editor.interfaces.choosewindows.ObjectRunnable;
import twintro.minecraft.modbuilder.editor.resources.MaterialResources;

public class ItemStackButton extends JButton {
	private boolean isProduct = false;
	private WindowClosingVerifierUser main;

	public ItemStackResource item;
	
	private ObjectRunnable runnable = new ObjectRunnable() {
		@Override
		public void run(Object obj) {
			chooseItem((ItemStackResource) obj);
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
		setText();
		setImage(MaterialResources.getImage(item));
		main.change();
	}
	
	private void click(){
		if (item == null)
			new ItemStackChooseWindow(isProduct, runnable);
		else
			new ItemStackChooseWindow(isProduct, runnable, item);
	}
	
	public void setText() {
		String material = MaterialResources.simplifyItemStackName(MaterialResources.getDisplayName(item));
		if (material != null && material != ""){
			if (item.container != null && item.container != ""){
				String container = MaterialResources.simplifyItemStackName(item.container);
				setToolTipText("<html>" + material + "<br>Container: " + container + "</html>");
			}
			else{
				setToolTipText(material);
			}
		}
		else setToolTipText(null);
		setText(material);
	}
	
	private void setImage(ImageIcon icon){
		if (icon == null){
			setIcon(null);
			if (isProduct) main.setIconImage(new ImageIcon("src/main/resources/icon.png").getImage());
		}
		else{
			setIcon(ResourcePackIO.resizeImage(icon, getWidth() / 16 * 2 / 3 * 16, getHeight() / 16 * 2 / 3 * 16));
			if (isProduct) main.setIconImage(icon.getImage());
		}
	}
}
