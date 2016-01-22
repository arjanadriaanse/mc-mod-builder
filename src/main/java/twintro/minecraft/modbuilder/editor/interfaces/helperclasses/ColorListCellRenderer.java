package twintro.minecraft.modbuilder.editor.interfaces.helperclasses;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class ColorListCellRenderer extends JLabel implements ListCellRenderer {
	public static final Color[] mapColors = new Color[]{
			new Color(255,0,0,0),
			new Color(125,176,55),
			new Color(244,230,161),
			new Color(197,197,197),
			new Color(252,0,0),
			new Color(158,158,252),
			new Color(165,165,165),
			new Color(0,123,0),
			new Color(252,252,252),
			new Color(162,166,182),
			new Color(149,108,76),
			new Color(111,111,111),
			new Color(63,63,252),
			new Color(141,118,71),
			new Color(252,249,242),
			new Color(213,125,50),
			new Color(176,75,213),
			new Color(101,151,213),
			new Color(226,226,50),
			new Color(125,202,25),
			new Color(239,125,163),
			new Color(75,75,75),
			new Color(151,151,151),
			new Color(75,125,151),
			new Color(125,62,176),
			new Color(50,75,176),
			new Color(101,75,50),
			new Color(101,125,50),
			new Color(151,50,50),
			new Color(25,25,25),
			new Color(247,235,76),
			new Color(91,216,210),
			new Color(73,129,252),
			new Color(0,214,57),
			new Color(127,85,48),
			new Color(111,2,0)
	};
	
	public ColorListCellRenderer(){
		setOpaque(true);
	}
	
	@Override
	public void setBackground(Color backgroundColor){}
	
	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus){
		setPreferredSize(new Dimension(50,20));
		setText("");
		if (((Color) value).getAlpha() == 0) setText("Transparent");
		super.setBackground((Color) value);
		return this;
	}
}
