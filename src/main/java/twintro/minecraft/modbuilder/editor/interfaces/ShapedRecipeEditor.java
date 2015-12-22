package twintro.minecraft.modbuilder.editor.interfaces;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JButton;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.SwingConstants;
import java.awt.Component;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

public class ShapedRecipeEditor extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ShapedRecipeEditor frame = new ShapedRecipeEditor();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ShapedRecipeEditor() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 506, 412);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(5, 5));
		setContentPane(contentPane);
		
		JPanel panel_3 = new JPanel();
		contentPane.add(panel_3, BorderLayout.CENTER);
		panel_3.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(30, 30, 250, 250);
		panel_3.add(panel);
		panel.setLayout(new GridLayout(3, 3, 4, 4));
		
		ItemStackButton[] buttons = new ItemStackButton[10];
		buttons[0] = new ItemStackButton("");
		panel.add(buttons[0]);
		
		buttons[1] = new ItemStackButton("");
		panel.add(buttons[1]);
		
		buttons[2] = new ItemStackButton("");
		panel.add(buttons[2]);
		
		buttons[3] = new ItemStackButton("");
		panel.add(buttons[3]);
		
		buttons[4] = new ItemStackButton("");
		panel.add(buttons[4]);
		
		buttons[5] = new ItemStackButton("");
		panel.add(buttons[5]);
		
		buttons[6] = new ItemStackButton("");
		panel.add(buttons[6]);
		
		buttons[7] = new ItemStackButton("");
		panel.add(buttons[7]);
		
		buttons[8] = new ItemStackButton("");
		panel.add(buttons[8]);
		
		buttons[9] = new ItemStackButton("");
		buttons[9].setBounds(365, 115, 80, 80);
		
		panel_3.add(buttons[9]);
		buttons[9].setHorizontalAlignment(SwingConstants.RIGHT);
		
		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2, BorderLayout.SOUTH);
		panel_2.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		
		JButton saveButton = new JButton("Save");
		saveButton.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_2.add(saveButton);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_2.add(cancelButton);
	}

}
