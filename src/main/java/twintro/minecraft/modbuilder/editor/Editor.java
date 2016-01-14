package twintro.minecraft.modbuilder.editor;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.FileDialog;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import twintro.minecraft.modbuilder.editor.generator.LanguageFile;
import twintro.minecraft.modbuilder.editor.generator.MetaFile;
import twintro.minecraft.modbuilder.editor.generator.ResourcePackGenerator;
import twintro.minecraft.modbuilder.editor.interfaces.TexturesActivityPanel;
import twintro.minecraft.modbuilder.editor.interfaces.activitypanels.BlocksActivityPanel;
import twintro.minecraft.modbuilder.editor.interfaces.activitypanels.ItemsActivityPanel;
import twintro.minecraft.modbuilder.editor.interfaces.activitypanels.RecipesActivityPanel;
import twintro.minecraft.modbuilder.editor.interfaces.activitypanels.StructureActivityPanel;
import twintro.minecraft.modbuilder.editor.resources.MaterialResources;

public class Editor {
	private static boolean interfaceOpened = false;
	private static JFrame frame;
	private static JPanel activityPanel;
	private static JMenuItem mntmExport;
	
	public static ActivityPanel TexturePanel;
	public static ActivityPanel RecipePanel;
	public static ActivityPanel BlockPanel;
	public static ActivityPanel ItemPanel;
	public static ActivityPanel StructurePanel;
	
	public static MetaFile metaFile;
	public static LanguageFile langFile;
	
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
					initialize();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private static void initialize() {
		//Window
		frame = new JFrame();
		frame.setBounds(100, 100, 900, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//TODO frame.setTitle("");
		
		//Menubar
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBackground(SystemColor.inactiveCaption);
		frame.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmNew = new JMenuItem("New");
		mnFile.add(mntmNew);
		mntmNew.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				newMod();
			}
		});
		
		JMenuItem mntmOpen = new JMenuItem("Open");
		mnFile.add(mntmOpen);
		mntmOpen.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				openMod();
			}
		});
		
		mntmExport = new JMenuItem("Export");
		mnFile.add(mntmExport);
		mntmExport.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				exportMod();
			}
		});
		mntmExport.setEnabled(false);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mnHelp.add(mntmAbout);
		mntmAbout.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				about();
			}
		});
		
		frame.setVisible(true);
	}
	
	private static void newMod(){
		if (chooseFolder(true)){
			metaFile = MetaFile.create(ResourcePackGenerator.resourcePackFolderDir + "pack.mcmeta");
			langFile = LanguageFile.create(ResourcePackGenerator.resourcePackFolderDir + 
					"assets/modbuilder/lang/en_US.lang");
		}
	}
	
	private static void openMod(){
		if (chooseFolder(false)){
			metaFile = MetaFile.open(ResourcePackGenerator.resourcePackFolderDir + "pack.mcmeta");
			langFile = LanguageFile.open(ResourcePackGenerator.resourcePackFolderDir + 
					"assets/modbuilder/lang/en_US.lang");
		}
	}
	
	private static boolean chooseFolder(boolean newMod){
		JFileChooser menu = new JFileChooser();
		menu.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		menu.setCurrentDirectory(new File(System.getProperty("user.home") + 
				"/AppData/Roaming/.minecraft/resourcepacks"));
		int result;
		if (newMod) result = menu.showSaveDialog(frame);
		else result = menu.showOpenDialog(frame);
		if (result == JFileChooser.APPROVE_OPTION){
			File file = menu.getSelectedFile();
			if (!file.exists())
				file.mkdirs();
			String dir = file.getAbsolutePath().replace("\\", "/") + "/";
			ResourcePackGenerator.resourcePackFolderDir = dir;
			if (!interfaceOpened) createInterface();
			updateInterface();
			return true;
		}
		return false;
	}
	
	private static void exportMod(){
		JFileChooser menu = new JFileChooser();
		menu.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		if (menu.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION){
			String dir = menu.getSelectedFile().getAbsolutePath();
			if (!dir.endsWith(".zip")) dir += ".zip";
			try {
				ResourcePackGenerator.export(dir);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static void createInterface(){
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
		/*
		JButton structuresButton = new ActivityButton("Structures");
		structuresButton.addActionListener(buttonListener);
		buttonPanel.add(structuresButton);
		*/
		JButton ItemsButton = new ActivityButton("Items");
		ItemsButton.addActionListener(buttonListener);
		buttonPanel.add(ItemsButton);
		
		
		//Activity panel
		JPanel ActivityPanel = new JPanel();
		splitPane.setRightComponent(ActivityPanel);
		
		ActivityPanel.setLayout(new CardLayout(0, 0));
		activityPanel = ActivityPanel;
		
		TexturePanel = new TexturesActivityPanel("Textures", "New Texture");
		ActivityPanel.add(TexturePanel, "Textures");
		
		RecipePanel = new RecipesActivityPanel("Recipes", "New Shapeless Recipe");
		ActivityPanel.add(RecipePanel, "Recipes");
		
		BlockPanel = new BlocksActivityPanel("Blocks", "New Block");
		ActivityPanel.add(BlockPanel, "Blocks");
		
		ItemPanel = new ItemsActivityPanel("Items", "New Item");
		ActivityPanel.add(ItemPanel, "Items");
		
		StructurePanel = new StructureActivityPanel("Structures", "New Structure");
		activityPanel.add(StructurePanel, "Structures");
		SwingUtilities.updateComponentTreeUI(frame);

		interfaceOpened = true;
		mntmExport.setEnabled(true);
	}
	
	private static void changePanel(String panel){
		CardLayout cl = (CardLayout)(activityPanel.getLayout());
		cl.show(activityPanel, panel);
	}
	
	private static void updateInterface(){
		TexturePanel.updateList();
		RecipePanel.updateList();
		BlockPanel.updateList();
		ItemPanel.updateList();
		StructurePanel.updateList();
	}
	
	private static void about(){
		//TODO
	}
}
