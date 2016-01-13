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
import twintro.minecraft.modbuilder.editor.interfaces.BlocksActivityPanel;
import twintro.minecraft.modbuilder.editor.interfaces.ItemsActivityPanel;
import twintro.minecraft.modbuilder.editor.interfaces.RecipesActivityPanel;
import twintro.minecraft.modbuilder.editor.interfaces.StructureActivityPanel;
import twintro.minecraft.modbuilder.editor.interfaces.TexturesActivityPanel;

public class Editor {
	private boolean interfaceOpened = false;
	
	private JPanel activityPanel;
	public ActivityPanel TexturePanel;
	private ActivityPanel RecipePanel;
	private ActivityPanel BlockPanel;
	private ActivityPanel ItemPanel;
	private ActivityPanel StructurePanel;
	
	public MetaFile metaFile;
	public LanguageFile langFile;
	
	private JMenuItem mntmExport;
	
	/**
	 * Launch the application.
	 */
	public BlocksActivityPanel getBlockPanel(){
		return (BlocksActivityPanel) BlockPanel;
	}
	
	
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
	}
	
	private void newMod(){
		if (chooseFolder(true)){
			metaFile = MetaFile.create(ResourcePackGenerator.resourcePackFolderDir + "pack.mcmeta");
			langFile = LanguageFile.create(ResourcePackGenerator.resourcePackFolderDir + 
					"assets/modbuilder/lang/en_US.lang");
		}
	}
	
	private void openMod(){
		if (chooseFolder(false)){
			metaFile = MetaFile.open(ResourcePackGenerator.resourcePackFolderDir + "pack.mcmeta");
			langFile = LanguageFile.open(ResourcePackGenerator.resourcePackFolderDir + 
					"assets/modbuilder/lang/en_US.lang");
		}
	}
	
	private boolean chooseFolder(boolean newMod){
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
	
	private void exportMod(){
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
	
	private void createInterface(){
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
		

		JButton structuresButton = new ActivityButton("Structures");
		structuresButton.addActionListener(buttonListener);
		buttonPanel.add(structuresButton);
		
		JButton ItemsButton = new ActivityButton("Items");
		ItemsButton.addActionListener(buttonListener);
		buttonPanel.add(ItemsButton);
		
		
		//Activity panel
		JPanel ActivityPanel = new JPanel();
		splitPane.setRightComponent(ActivityPanel);
		
		ActivityPanel.setLayout(new CardLayout(0, 0));
		activityPanel = ActivityPanel;
		
		TexturePanel = new TexturesActivityPanel("Textures", "New Texture", this);
		ActivityPanel.add(TexturePanel, "Textures");
		
		RecipePanel = new RecipesActivityPanel("Recipes", "New Shapeless Recipe", this);
		ActivityPanel.add(RecipePanel, "Recipes");
		
		BlockPanel = new BlocksActivityPanel("Blocks", "New Block", this);
		ActivityPanel.add(BlockPanel, "Blocks");
		
		ItemPanel = new ItemsActivityPanel("Items", "New Item", this);
		ActivityPanel.add(ItemPanel, "Items");
		
		StructurePanel = new StructureActivityPanel("Structures", "New Structure", this);
		activityPanel.add(StructurePanel, "Structures");
		SwingUtilities.updateComponentTreeUI(frame);

		interfaceOpened = true;
		mntmExport.setEnabled(true);
	}
	
	private void changePanel(String panel){
		CardLayout cl = (CardLayout)(activityPanel.getLayout());
		cl.show(activityPanel, panel);
	}
	
	private void updateInterface(){
		TexturePanel.updateList();
		RecipePanel.updateList();
		BlockPanel.updateList();
		ItemPanel.updateList();
		StructurePanel.updateList();
	}
	
	private void about(){
		//TODO
	}
	
	public Set<String> getBlocksInBlockPanel(){
		return BlockPanel.getAllElements();
	}
	
	public Set<String> getItemsInItemPanel(){
		return ItemPanel.getAllElements();
	}
}
