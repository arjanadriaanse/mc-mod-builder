package twintro.minecraft.modbuilder.editor.interfaces.editors;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;

import twintro.minecraft.modbuilder.editor.interfaces.choosewindows.ObjectRunnable;
import twintro.minecraft.modbuilder.editor.interfaces.helperclasses.TooltipLabel;
import twintro.minecraft.modbuilder.editor.interfaces.helperclasses.WindowClosingVerifierListener;
import twintro.minecraft.modbuilder.editor.interfaces.helperclasses.WindowClosingVerifierUser;

public abstract class PropertiesEditor extends WindowClosingVerifierUser {
	protected JPanel mainPanel;
	protected JPanel buttonPanel;
	protected JPanel labelPanel;
	protected JPanel interactionPanel;
	protected JButton saveButton;
	protected JButton cancelButton;
	
	protected String name;
	protected ObjectRunnable runnable;
	protected ObjectRunnable closeHandler;

	public PropertiesEditor(String name, ObjectRunnable runnable, ObjectRunnable closeHandler) {
		this.name = name;
		this.runnable = runnable;
		this.closeHandler = closeHandler;

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowClosingVerifierListener());
		((JComponent) getContentPane()).setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		mainPanel = new JPanel();
		getContentPane().add(mainPanel, BorderLayout.NORTH);
		mainPanel.setLayout(new BorderLayout(5, 0));
		
		labelPanel = new JPanel();
		mainPanel.add(labelPanel, BorderLayout.WEST);
		labelPanel.setLayout(new GridLayout(0, 1, 0, 5));
		
		interactionPanel = new JPanel();
		mainPanel.add(interactionPanel, BorderLayout.CENTER);
		interactionPanel.setLayout(new GridLayout(0, 1, 0, 5));
		
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		
		saveButton = new JButton();
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});
		buttonPanel.add(saveButton);
		
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancel();
			}
		});
		buttonPanel.add(cancelButton);
		
		setVisible(true);
	}
	
	protected void setSize(int width){
		setVisible(true);
		setBounds(100, 100, width, (int) (mainPanel.getSize().getHeight() + buttonPanel.getSize().getHeight() + this.getSize().getHeight()) + 15);
	}
	
	public JPanel panel(Component center){
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(0, 0));
		panel.add(center, BorderLayout.CENTER);
		return panel;
	}
	
	public JPanel panel(Component center, Component east){
		JPanel panel = panel(center);
		panel.add(east, BorderLayout.EAST);
		return panel;
	}
	
	public JPanel panel(Component center, Component east, JPanel superPanel){
		JPanel panel = panel(center, east);
		superPanel.add(panel);
		return panel;
	}
	
	public JLabel tooltipLabel(String name, String tooltip){
		JLabel label = new TooltipLabel(name, tooltip);
		return label;
	}
	
	public JLabel label(String name, String tooltip){
		JLabel label = new JLabel(name);
		label.setToolTipText(tooltip);
		return label;
	}
	
	public JLabel label(String name, String tooltip, JPanel superPanel){
		JLabel label = label(name, tooltip);
		superPanel.add(label);
		return label;
	}
	
	public JButton button(String name, String tooltip){
		JButton button = new JButton(name);
		button.setToolTipText(tooltip);
		return button;
	}
	
	public JButton button(String name, String tooltip, JPanel superPanel){
		JButton button = button(name, tooltip);
		superPanel.add(button);
		return button;
	}
	
	public JComboBox combobox(String tooltip){
		JComboBox combobox = new JComboBox();
		combobox.setToolTipText(tooltip);
		combobox.addActionListener(actionListener);
		return combobox;
	}
	
	public JComboBox combobox(String tooltip, JPanel superPanel){
		JComboBox combobox = combobox(tooltip);
		superPanel.add(combobox);
		return combobox;
	}
	
	public JSpinner spinner(String tooltip){
		JSpinner spinner = new JSpinner();
		spinner.setToolTipText(tooltip);
		spinner.addChangeListener(changeListener);
		return spinner;
	}
	
	public JSpinner spinner(String tooltip, JPanel superPanel){
		JSpinner spinner = spinner(tooltip);
		superPanel.add(spinner);
		return spinner;
	}
	
	public JCheckBox checkbox(String name, String tooltip){
		JCheckBox checkbox = new JCheckBox(name);
		checkbox.setToolTipText(tooltip);
		checkbox.addActionListener(actionListener);
		return checkbox;
	}
	
	public JCheckBox checkbox(String name, String tooltip, JPanel superPanel){
		JCheckBox checkbox = checkbox(name, tooltip);
		superPanel.add(checkbox);
		return checkbox;
	}
	
	private void cancel(){
		for (WindowListener listener : getWindowListeners()){
			listener.windowClosing(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		}
	}
	
	@Override
	public void dispose() {
		closeHandler.run(name);
		super.dispose();
	}
}
