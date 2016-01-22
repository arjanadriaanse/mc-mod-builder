package twintro.minecraft.modbuilder.editor.interfaces.helperclasses;

import java.awt.Component;
import java.awt.Font;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.SwingConstants;

public class ImageListCellRenderer extends DefaultListCellRenderer {
	private static final Font font = new Font("Tahoma", Font.PLAIN, 15);
	private ListPanel panel;
	
	public ImageListCellRenderer(ListPanel panel){
		super();
		this.panel = panel;
	}
	
	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus){
		JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        label.setIcon(panel.elements.get(value));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setHorizontalTextPosition(SwingConstants.CENTER);
        label.setVerticalTextPosition(SwingConstants.BOTTOM);
        label.setFont(font);
        return label;
	}
}
