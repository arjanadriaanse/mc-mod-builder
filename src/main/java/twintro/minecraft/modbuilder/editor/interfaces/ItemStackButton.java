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

public class ItemStackButton extends JButton implements WindowListener, ActionListener{
	
	List<String> items;
	public ItemStackResource item;
	ItemStackEditor editor = null;
	ItemStackButton thisButton = this;
	
	ItemStackButton(String s){
		super(s);
		//TODO remove dummy itemlist
		items = new ArrayList<String>();
		items.add("dirt??");
		items.add("SuperMegaDeathRocket");
		//end to do
		item = null;
		this.addActionListener(this);
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {

		if (editor.changed){
			if (item.block!=null &&
					!item.block.isEmpty()){
					if (item.amount >= 0){
						thisButton.setText(item.amount + " " + item.block);
					} else thisButton.setText(1 + " " + item.block);
				}
			else if (item.item !=null &&
					!item.item.isEmpty()){
				if (item.amount != null && item.amount > 0){
					thisButton.setText(item.amount + " " + item.item);
				} else thisButton.setText(1 + " " + item.item);
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
			editor = new ItemStackEditor(item, items);
			editor.addWindowListener(this);
		}
	}

}
