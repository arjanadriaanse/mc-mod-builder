package twintro.minecraft.modbuilder.editor.interfaces;

import twintro.minecraft.modbuilder.editor.ActivityPanel;

public class TexturesActivityPanel extends ActivityPanel {
	public TexturesActivityPanel(String header, String button) {
		super(header, button);
	}

	@Override
	protected void add() {
		elements.put("colorblock_b", getImage("colorblock_b"));
		list.update(list.getGraphics());
	}
}
