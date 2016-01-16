package twintro.minecraft.modbuilder.editor.interfaces.activitypanels;

import java.util.Map;

import javax.swing.JFrame;

import twintro.minecraft.modbuilder.editor.interfaces.choosewindows.ObjectRunnable;

public abstract class ObjectActivityPanel extends ActivityPanel {
	protected Map<String, JFrame> openEditors;

	protected final ObjectRunnable closeHandler = new ObjectRunnable() {
		@Override
		public void run(Object obj) {
			closeEditor((String) obj);
		}
	};
	
	public ObjectActivityPanel (String header, String button){
		super(header, button);
	}

	private void closeEditor(String name){
		if (openEditors.containsKey(name))
			openEditors.remove(name);
	}
}
