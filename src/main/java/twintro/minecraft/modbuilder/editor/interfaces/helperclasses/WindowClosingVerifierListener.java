package twintro.minecraft.modbuilder.editor.interfaces.helperclasses;

import java.awt.Component;
import java.awt.Window;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JOptionPane;

public class WindowClosingVerifierListener implements WindowListener{
	public static void close(Window window){
		Object[] options = {"Yes", "No", "Cancel"};
		int selected = JOptionPane.showOptionDialog(window, "There are unsaved changes.\r\n" + 
				"Would you like to save first?", 
		"Confirm", 
		JOptionPane.YES_NO_CANCEL_OPTION,
		JOptionPane.WARNING_MESSAGE, 
		null, options, options[0]);
		if (selected == 0 && window instanceof WindowClosingVerifierUser) 
			if (!((WindowClosingVerifierUser) window).save())
				return;
		if (selected != 2) window.dispose();
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
