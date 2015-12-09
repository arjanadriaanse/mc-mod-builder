package twintro.minecraft.modbuilder.editor;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class ActivityButton extends JButton {
	public ActivityButton(String text){
		super(text);
		this.setBackground(Color.LIGHT_GRAY);
		this.setFont(new Font("Tahoma", Font.PLAIN, 17));
	}
}
