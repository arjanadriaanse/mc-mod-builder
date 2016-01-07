package twintro.minecraft.modbuilder.editor.interfaces.helperclasses;

import java.awt.Component;
import java.awt.Window;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JOptionPane;

public class WindowClosingVerifierListener implements WindowListener{

	public static void close(Window arg0){
		Object[] options = {"Yes",
        "Cancel"};
		int selected = JOptionPane.showOptionDialog(arg0, "Are you sure you want to close this window?", 
		"Confirm", 
		JOptionPane.YES_NO_OPTION, 
		JOptionPane.WARNING_MESSAGE, 
		null, options, options[1]);
		if (selected == 0) arg0.dispose();
	
	}
	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent arg0) {

		Object[] options = {"Yes",
        "Cancel"};
		int selected = JOptionPane.showOptionDialog(arg0.getWindow(), "Are you sure you want to close this window?", 
		"Confirm", 
		JOptionPane.YES_NO_OPTION, 
		JOptionPane.WARNING_MESSAGE, 
		null, options, options[1]);
		if (selected == 0) arg0.getWindow().dispose();
		
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
