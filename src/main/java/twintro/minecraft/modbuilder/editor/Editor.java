package twintro.minecraft.modbuilder.editor;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.FileDialog;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import twintro.minecraft.modbuilder.editor.generator.LanguageFile;
import twintro.minecraft.modbuilder.editor.generator.MetaFile;
import twintro.minecraft.modbuilder.editor.generator.ResourcePackIO;
import twintro.minecraft.modbuilder.editor.interfaces.activitypanels.ActivityPanel;
import twintro.minecraft.modbuilder.editor.interfaces.activitypanels.BlocksActivityPanel;
import twintro.minecraft.modbuilder.editor.interfaces.activitypanels.ItemsActivityPanel;
import twintro.minecraft.modbuilder.editor.interfaces.activitypanels.RecipesActivityPanel;
import twintro.minecraft.modbuilder.editor.interfaces.activitypanels.StructureActivityPanel;
import twintro.minecraft.modbuilder.editor.interfaces.activitypanels.TexturesActivityPanel;
import twintro.minecraft.modbuilder.editor.interfaces.helperclasses.ActivityButton;
import twintro.minecraft.modbuilder.editor.interfaces.helperclasses.IconFrame;
import twintro.minecraft.modbuilder.editor.resources.MaterialResources;

public class Editor {
	private static boolean interfaceOpened = false;
	private static JFrame frame;
	private static JPanel activityPanel;
	private static JMenuItem mntmExport;
	
	private static ActivityPanel TexturePanel;
	private static ActivityPanel RecipePanel;
	private static ActivityPanel BlockPanel;
	private static ActivityPanel ItemPanel;
	private static ActivityPanel StructurePanel;
	
	public static MetaFile metaFile;
	public static LanguageFile langFile;

	public static Map<String, ImageIcon> getTextureList(){
		return TexturePanel.elements;
	}
	public static Map<String, ImageIcon> getItemList(){
		return ItemPanel.elements;
	}
	public static Map<String, ImageIcon> getBlockList(){
		return BlockPanel.elements;
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
					initialize();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private static void initialize() {
		frame = new IconFrame();
		frame.setBounds(100, 100, 900, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Mod Builder");
		
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
		
		JMenuItem mntmHelp = new JMenuItem("Help");
		mnHelp.add(mntmHelp);
		mntmHelp.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				help();
			}
		});
		
		menuBar.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('N',KeyEvent.CTRL_DOWN_MASK), "new");
		menuBar.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('O',KeyEvent.CTRL_DOWN_MASK), "open");
		menuBar.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('E',KeyEvent.CTRL_DOWN_MASK), "export");
		menuBar.getActionMap().put("new", new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent ae){
				newMod();
			}
		});
		menuBar.getActionMap().put("open", new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent ae){
				openMod();
			}
		});
		menuBar.getActionMap().put("export", new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent ae){
				if (mntmExport.isEnabled())
					exportMod();
			}
		});
		
		frame.setVisible(true);
	}
	
	private static void newMod(){
		chooseFolder(true);
	}
	
	private static void openMod(){
		chooseFolder(false);
	}
	
	private static void chooseFolder(boolean newMod){
		JFileChooser menu = new JFileChooser();
		menu.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		menu.setCurrentDirectory(new File(System.getProperty("user.home") + 
				"/AppData/Roaming/.minecraft/resourcepacks"));
		int result;
		if (newMod) result = menu.showSaveDialog(frame);
		else result = menu.showOpenDialog(frame);
		if (result == JFileChooser.APPROVE_OPTION){
			File file = menu.getSelectedFile();
			if (file.exists() && file.listFiles().length == 0){
				newMod = true;
			}
			else if (file.exists() && newMod){
				int selected = JOptionPane.showConfirmDialog(frame, "The file already exists. Do you want to try to open it?", 
						"Error", JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
				if (selected == JOptionPane.OK_OPTION)
					newMod = false;
				else
					return;
			}
			String dir = file.getAbsolutePath().replace("\\", "/") + "/";
			ResourcePackIO.setResourcePackFolder(dir);
			if (!interfaceOpened) createInterface();
			mntmExport.setEnabled(true);
			frame.getContentPane().setVisible(true);
			if (!newMod){
				String errorMessage = updateInterface();
				if (errorMessage != null){
					closeInterface();
					mntmExport.setEnabled(false);
					JOptionPane.showConfirmDialog(frame, "The file could not be opened.\n" + errorMessage, 
							"Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
				}
			}
			else{
				metaFile = MetaFile.create(ResourcePackIO.getURL("pack.mcmeta"));
				langFile = LanguageFile.create(ResourcePackIO.getURL("assets/modbuilder/lang/en_US.lang"));
				updateInterface();
			}
		}
	}
	
	private static void exportMod(){
		JFileChooser menu = new JFileChooser();
		menu.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		if (menu.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION){
			String dir = menu.getSelectedFile().getAbsolutePath();
			if (!dir.endsWith(".zip")) dir += ".zip";
			ResourcePackIO.export(dir);
		}
	}
	
	private static void createInterface(){
		JSplitPane splitPane = new JSplitPane();
		splitPane.setBackground(SystemColor.control);
		frame.getContentPane().add(splitPane, BorderLayout.CENTER);
		
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
		
		JButton StructuresButton = new ActivityButton("Structures");
		StructuresButton.addActionListener(buttonListener);
		buttonPanel.add(StructuresButton);
		
		JButton ItemsButton = new ActivityButton("Items");
		ItemsButton.addActionListener(buttonListener);
		buttonPanel.add(ItemsButton);
		
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
		
		StructurePanel = new StructureActivityPanel("Structures", "New Ore");
		ActivityPanel.add(StructurePanel, "Structures");
		
		interfaceOpened = true;
		
		frame.setVisible(true);
	}
	
	private static void changePanel(String panel){
		CardLayout cl = (CardLayout)(activityPanel.getLayout());
		cl.show(activityPanel, panel);
	}
	
	private static String updateInterface(){
		if ((metaFile = MetaFile.open(ResourcePackIO.getURL("pack.mcmeta"))) == null) 
			return "Could not succesfully open mc.mcmeta.";
		if ((langFile = LanguageFile.open(ResourcePackIO.getURL("assets/modbuilder/lang/en_US.lang"))) == null) 
			return "Could not succesfully open en_US.lang";
		String errorMessage;
		if ((errorMessage = TexturePanel.updateList()) != null) 
			return errorMessage;
		if ((errorMessage = RecipePanel.updateList()) != null) 
			return errorMessage;
		if ((errorMessage = BlockPanel.updateList()) != null) 
			return errorMessage;
		if ((errorMessage = ItemPanel.updateList()) != null) 
			return errorMessage;
		if ((errorMessage = StructurePanel.updateList()) != null) 
			return errorMessage;
		return null;
	}
	
	private static void closeInterface(){
		frame.getContentPane().setVisible(false);
	}
	
	private static void about(){
		//TODO
	}
	
	private static void help(){
		//TODO
	}
}
