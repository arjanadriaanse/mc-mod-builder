package twintro.minecraft.modbuilder.editor;

import java.awt.EventQueue;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFrame;

import twintro.minecraft.modbuilder.data.resources.MaterialResource;
import twintro.minecraft.modbuilder.data.resources.TabResource;
import twintro.minecraft.modbuilder.data.resources.blocks.BlockResource;
import twintro.minecraft.modbuilder.editor.generator.ResourcePackGenerator;

public class Editor {
	public static final String modName = "exampleName";
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					Editor window = new Editor();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		//Testing stuff
		BlockResource block = new BlockResource();
		block.model = "stone";
		block.material = MaterialResource.rock;
		block.tab = TabResource.block;
		
		try {
			ResourcePackGenerator.createFile(block, "assets/modbuilder/blocks/wiel.json");
			ResourcePackGenerator.generate();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//End testing stuff
	}

	private JFrame frame;

	/**
	 * Create the application.
	 */
	public Editor() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
