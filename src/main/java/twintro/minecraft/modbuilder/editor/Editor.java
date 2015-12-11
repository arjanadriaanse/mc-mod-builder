package twintro.minecraft.modbuilder.editor;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.UIManager;

import twintro.minecraft.modbuilder.data.resources.MaterialResource;
import twintro.minecraft.modbuilder.data.resources.TabResource;
import twintro.minecraft.modbuilder.data.resources.blocks.BlockResource;
import twintro.minecraft.modbuilder.editor.generator.ResourcePackGenerator;
import twintro.minecraft.modbuilder.editor.interfaces.BlocksActivityPanel;
import twintro.minecraft.modbuilder.editor.interfaces.ItemsActivityPanel;
import twintro.minecraft.modbuilder.editor.interfaces.RecipesActivityPanel;
import twintro.minecraft.modbuilder.editor.interfaces.TexturesActivityPanel;

public class Editor {
	public static final String modName = "exampleName";
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
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
		//TODO remove this
		//Testing stuff
	/*	BlockResource block = new BlockResource();
		block.model = "stone";
		block.material = MaterialResource.rock;
		block.tab = TabResource.block;
		
		try {
			ResourcePackGenerator.createFile(block, "assets/modbuilder/blocks/wiel.json");
			ResourcePackGenerator.generate();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
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
		//Window
		frame = new JFrame();
		frame.setBounds(100, 100, 900, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Menubar
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(SystemColor.inactiveCaption);
		frame.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmNew = new JMenuItem("New");
		mnFile.add(mntmNew);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mnHelp.add(mntmAbout);
		
		//Main panel
		JSplitPane splitPane = new JSplitPane();
		splitPane.setBackground(SystemColor.control);
		frame.getContentPane().add(splitPane, BorderLayout.CENTER);
		
		//Button panel
		JPanel buttonPanel = new JPanel();
		splitPane.setLeftComponent(buttonPanel);
		buttonPanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		ActionListener buttonListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changePanel(((JButton)e.getSource()).getText());
			}
		};
		
		JButton TexturesButton = new ActivityButton("Textures");
		TexturesButton.addActionListener(buttonListener);
		buttonPanel.add(TexturesButton);
		
		JButton RecipesButton = new ActivityButton("Recipes");
		RecipesButton.addActionListener(buttonListener);
		buttonPanel.add(RecipesButton);
		
		JButton BlocksButton = new ActivityButton("Blocks");
		BlocksButton.addActionListener(buttonListener);
		buttonPanel.add(BlocksButton);
		
		JButton ItemsButton = new ActivityButton("Items");
		ItemsButton.addActionListener(buttonListener);
		buttonPanel.add(ItemsButton);
		
		//Activity panel
		JPanel ActivityPanel = new JPanel();
		splitPane.setRightComponent(ActivityPanel);
		ActivityPanel.setLayout(new CardLayout(0, 0));
		activityPanel = ActivityPanel;
		
		JPanel TexturePanel = new TexturesActivityPanel("Textures", "New Texture");
		ActivityPanel.add(TexturePanel, "Textures");
		
		JPanel RecipePanel = new RecipesActivityPanel("Recipes", "New Recipe");
		ActivityPanel.add(RecipePanel, "Recipes");
		
		JPanel BlockPanel = new BlocksActivityPanel("Blocks", "New Block");
		ActivityPanel.add(BlockPanel, "Blocks");
		
		JPanel ItemPanel = new ItemsActivityPanel("Items", "New Item");
		ActivityPanel.add(ItemPanel, "Items");
	}
	
	JPanel activityPanel;
	
	private void changePanel(String panel){
		CardLayout cl = (CardLayout)(activityPanel.getLayout());
		cl.show(activityPanel, panel);
	}
}
