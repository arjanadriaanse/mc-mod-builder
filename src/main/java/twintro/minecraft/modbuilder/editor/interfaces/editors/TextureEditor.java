package twintro.minecraft.modbuilder.editor.interfaces.editors;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

import twintro.minecraft.modbuilder.editor.generator.ResourcePackIO;
import twintro.minecraft.modbuilder.editor.interfaces.helperclasses.BitmapEditorPanel;
import twintro.minecraft.modbuilder.editor.resources.TextureObject;

public class TextureEditor extends JFrame {
	private String name;
	private BufferedImage image;
	private boolean saved;
	
	private BitmapEditorPanel editorPanel;
	private JButton colorButton;
	private JButton undoButton;
	private JButton redoButton;
	private JButton clearButton;
	private JButton loadButton;
	private JButton saveButton;
	private JButton cancelButton;
	
	private JColorChooser colorChooser;
	
	public TextureEditor() {		
		colorChooser = new JColorChooser();
		
		ActionListener toolActionListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == editorPanel) {
					undoButton.setEnabled(editorPanel.canUndo());
					redoButton.setEnabled(editorPanel.canRedo());
					saved = false;
				}
				else if (e.getSource() == colorButton) {
					Color color = JColorChooser.showDialog(colorButton, "Choose pencil color", editorPanel.getForeground());
					if (color != null) {
						editorPanel.setForeground(color);
						colorButton.setBackground(color);
					}
				}
				else if (e.getSource() == undoButton) {
					editorPanel.undo();
				}
				else if (e.getSource() == redoButton) {
					editorPanel.redo();
				}
				else if (e.getSource() == clearButton) {
					editorPanel.clear();
				}
				else if (e.getSource() == loadButton) {
					editorPanel.clear();
				}
			}
		};
		
		editorPanel = new BitmapEditorPanel(new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB));
		editorPanel.addActionListener(toolActionListener);
		getContentPane().add(editorPanel, BorderLayout.CENTER);
		
		JPanel toolPanel = new JPanel();
		toolPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		getContentPane().add(toolPanel, BorderLayout.NORTH);
				
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);

		colorButton = new JButton("Color");
		colorButton.addActionListener(toolActionListener);
		toolPanel.add(colorButton);
		
		undoButton = new JButton("Undo");
		undoButton.setEnabled(false);
		undoButton.addActionListener(toolActionListener);
		toolPanel.add(undoButton);
		
		redoButton = new JButton("Redo");
		redoButton.setEnabled(false);
		redoButton.addActionListener(toolActionListener);
		toolPanel.add(redoButton);
		
		clearButton = new JButton("Clear");
		clearButton.addActionListener(toolActionListener);
		toolPanel.add(clearButton);
		
		loadButton = new JButton("Load");
		loadButton.addActionListener(toolActionListener);
		toolPanel.add(loadButton);

		saveButton = new JButton("Save");
		buttonPanel.add(saveButton);
		
		cancelButton = new JButton("Cancel");
		buttonPanel.add(cancelButton);
		
		this.setSize(500, 500);
	}

	public void open(String name, BufferedImage img){
		this.name = name;
		this.image = img;
		this.saved = true;
		this.setIconImage(image);
		this.setName("Edit texture: " + name);
		this.setVisible(true);
	}
	
	public void loadImage() {
		JFileChooser menu = new JFileChooser();
		int result = menu.showOpenDialog(this);
		if (result == JFileChooser.APPROVE_OPTION){
			File file = menu.getSelectedFile();
			if (file.exists()){
				if (file.getAbsolutePath().endsWith(".png")) {
					ImageIcon icon = ResourcePackIO.resizeImage(new ImageIcon(file.getAbsolutePath()), 16, 16);
					BufferedImage img = ResourcePackIO.toBufferedImage(icon.getImage());
					String name = file.getName().substring(0, file.getName().length() - 4);
					open(name, img);
					//panel.repaint();
					saveImage();
				}
			}
		}
	}
	
	public void saveImage() {
		saved=true;
		this.setIconImage(image);
		
		TextureObject texture = new TextureObject();
		texture.name = name;
		texture.image = new ImageIcon(image);
		//runnable.run(texture);
	}
}
