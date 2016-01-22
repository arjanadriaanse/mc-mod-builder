package twintro.minecraft.modbuilder.editor.interfaces.helperclasses;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public abstract class WindowClosingVerifierUser extends IconFrame {
	protected boolean changed = true;
	public WindowClosingVerifierUser(){
		JComponent keyListener = new JComponent(){};
		keyListener.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke('S',InputEvent.CTRL_DOWN_MASK), "save");
		keyListener.getActionMap().put("save", new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent ae){
				save();
			}
		});
		this.add(keyListener);
	}
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
