package twintro.minecraft.modbuilder.editor.interfaces.helperclasses;

import java.awt.Component;
import java.awt.Font;
import java.util.Map;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;

import scala.collection.immutable.List;

public class CustomListCellRenderer extends DefaultListCellRenderer {
	private static final Font font = new Font("Tahoma", Font.PLAIN, 15);
	private ListPanel panel;
	
	public CustomListCellRenderer(ListPanel panel){
		super();
		this.panel = panel;
	}
	
	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus){
		JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        label.setIcon(panel.elements.get((String) value));
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setHorizontalTextPosition(JLabel.CENTER);
        label.setVerticalTextPosition(JLabel.BOTTOM);
        label.setFont(font);
        return label;
	}
}
