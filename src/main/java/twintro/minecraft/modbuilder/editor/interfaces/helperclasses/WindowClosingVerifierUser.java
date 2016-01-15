package twintro.minecraft.modbuilder.editor.interfaces.helperclasses;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public abstract class WindowClosingVerifierUser extends JFrame {
	protected boolean changed = true;
	protected final ActionListener actionListener = new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e) {
			change();
		}
	};
	protected final ChangeListener changeListener = new ChangeListener (){
		@Override
		public void stateChanged(ChangeEvent e) {
			change();
		}
	};
	protected void change(){
		changed = true;
	}
	public boolean needsClosingVerification(){
		return changed;
	}
}
