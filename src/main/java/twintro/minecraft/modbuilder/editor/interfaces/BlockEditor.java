package twintro.minecraft.modbuilder.editor.interfaces;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import twintro.minecraft.modbuilder.data.resources.blocks.BlockResource;

public class BlockEditor{
	String name;
	BufferedImage texture;
	String textureloc;
	BlockResource block;
	BlocksActivityPanel parent;
	JFrame frame;
	JPanel panel;
	
	public BlockEditor(String name) {
		
	}
	
	public BlockEditor(BlockResource block, BlocksActivityPanel parent) {
		this.block = block;
		this.parent = parent;
		frame = buildFrame();
		panel = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponents(g);
				paintSelf(g);
			}
		};
		frame.add(panel);
		
	}
	
	public JFrame buildFrame(){
		JFrame f = new JFrame("Edit block: " + name);
		f.setSize(384, 368);
		f.setVisible(true);
		f.setIconImage(texture);
		return f;
	}
	
	public void paintSelf(Graphics g) {
		
	}
	
	public void newBlock() {
		
	}
	
	public void saveBlock() {
		
	}
}