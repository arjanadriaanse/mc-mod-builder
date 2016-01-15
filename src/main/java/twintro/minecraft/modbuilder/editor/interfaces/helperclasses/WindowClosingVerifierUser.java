package twintro.minecraft.modbuilder.editor.interfaces.helperclasses;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public abstract class WindowClosingVerifierUser extends IconFrame {
	public boolean changed = true;
	public final ActionListener actionListener = new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e) {
			change();
		}
	};
	public final ChangeListener changeListener = new ChangeListener (){
		@Override
		public void stateChanged(ChangeEvent e) {
			change();
		}
	};
	public void change(){
		changed = true;
	}
	public boolean needsClosingVerification(){
		return changed;
	}
	public abstract boolean save();
}
