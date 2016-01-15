package twintro.minecraft.modbuilder.editor.interfaces.helperclasses;

import java.awt.Component;
import java.awt.Window;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JOptionPane;

public class WindowClosingVerifierListener implements WindowListener{
	public static void close(Window window){
		Object[] options = {"Yes",
        "Cancel"};
		int selected = JOptionPane.showOptionDialog(window, "Are you sure you want to close this window?", 
		"Confirm", 
		JOptionPane.YES_NO_OPTION, 
		JOptionPane.WARNING_MESSAGE, 
		null, options, options[1]);
		if (selected == 0) window.dispose();
	}
	
	@Override
	public void windowClosing(WindowEvent e) {
		Window window = e.getWindow();
		if (window instanceof WindowClosingVerifierUser){
			if (((WindowClosingVerifierUser) window).needsClosingVerification())
				close(window);
			else
				window.dispose();
		}
		else{
			close(window);
		}	
	}
	
	@Override
	public void windowActivated(WindowEvent e) {}
	@Override
	public void windowClosed(WindowEvent e) {}
	@Override
	public void windowDeactivated(WindowEvent e) {}
	@Override
	public void windowDeiconified(WindowEvent e) {}
	@Override
	public void windowIconified(WindowEvent e) {}
	@Override
	public void windowOpened(WindowEvent e) {}
}
