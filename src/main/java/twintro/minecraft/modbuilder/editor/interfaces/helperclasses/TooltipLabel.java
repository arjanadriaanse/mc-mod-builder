package twintro.minecraft.modbuilder.editor.interfaces.helperclasses;

import javax.swing.JLabel;

public class TooltipLabel extends JLabel {
	private String tooltip;
	
	public TooltipLabel(String string, String tooltip) {
		super(string);
		this.tooltip = tooltip;
		if (getToolTipText() == null) super.setToolTipText(this.tooltip);
	}

	@Override
	public void setText(String text) {
		super.setText(text);
		setToolTipText(text);
	}
	
	@Override
	public void setToolTipText(String text) {
		if (text.length() == 0) super.setToolTipText(tooltip);
		else super.setToolTipText(text);
	}
}
