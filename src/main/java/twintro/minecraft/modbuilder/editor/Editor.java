package twintro.minecraft.modbuilder.editor;

import java.awt.EventQueue;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFrame;

public class Editor {
	public static final String modName = "exampleName";
	public static final String resourcePackFolderDir = 
			"C:/Users/M.P/AppData/Roaming/.minecraft/resourcepacks/" + modName + "/";
	public static final String outputDir = "C:/Users/M.P/Desktop/" + modName + ".zip";
	
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
		
		try {
			export();
		} catch (IOException e) {
			e.printStackTrace();
		}
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
	
	private static void export() throws IOException{
		ResourcePackGenerator rpg = new ResourcePackGenerator();
		
		File file = null;
		BufferedWriter output = null;
        try{
            file = new File(resourcePackFolderDir + "exampleFile.txt");
            output = new BufferedWriter(new FileWriter(file));
            output.write("exampleText");
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally{
            if (output != null) output.close();
        }
		
		rpg.generate();
	}
}
