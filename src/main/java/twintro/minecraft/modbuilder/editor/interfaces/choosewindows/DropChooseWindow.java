package twintro.minecraft.modbuilder.editor.interfaces.choosewindows;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import twintro.minecraft.modbuilder.data.resources.recipes.ItemStackResource;
import twintro.minecraft.modbuilder.editor.interfaces.helperclasses.IconDialog;
import twintro.minecraft.modbuilder.editor.interfaces.helperclasses.TooltipLabel;
import twintro.minecraft.modbuilder.editor.resources.MaterialResources;

public class DropChooseWindow extends IconDialog {
	private JPanel mainPanel;
	private JPanel buttonPanel;
	private JPanel labelPanel;
	private JPanel amountPanel;
	private JPanel minAmountPanel;
	private JPanel maxAmountPanel;
	private JPanel interactionPanel;
	private JPanel materialPanel;
	private JLabel labelMaterial;
	private JLabel labelAmount;
	private JLabel labelMinAmount;
	private JLabel labelMaxAmount;
	private JLabel materialLabel;
	private JButton materialChooseButton;
	private JButton addButton;
	private JButton cancelButton;
	private JSpinner minAmountSpinner;
	private JSpinner maxAmountSpinner;
	
	private ObjectRunnable runnable;

	private static final String materialTooltip = "The material of the drop";
	private static final String amountTooltip = "The amount of the item or block that will be dropped";
	
	public DropChooseWindow(ObjectRunnable runnable){
		this.runnable = runnable;
		
		((JComponent) getContentPane()).setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Choose Drop");
		
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout(5, 0));
		getContentPane().add(mainPanel, BorderLayout.NORTH);
		
		labelPanel = new JPanel();
		labelPanel.setLayout(new GridLayout(0, 1, 0, 5));
		mainPanel.add(labelPanel, BorderLayout.WEST);
		
		interactionPanel = new JPanel();
		interactionPanel.setLayout(new GridLayout(0, 1, 0, 5));
		mainPanel.add(interactionPanel, BorderLayout.CENTER);
		
		labelMaterial = new JLabel("Material");
		labelMaterial.setToolTipText(materialTooltip);
		labelPanel.add(labelMaterial);
		
		materialPanel = new JPanel();
		materialPanel.setLayout(new BorderLayout(0, 0));
		interactionPanel.add(materialPanel);
		
		materialChooseButton = new JButton("Choose");
		materialChooseButton.setToolTipText(materialTooltip);
		materialChooseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				chooseMaterial();
			}
		});
		materialPanel.add(materialChooseButton, BorderLayout.EAST);
		
		materialLabel = new TooltipLabel("", materialTooltip);
		materialPanel.add(materialLabel, BorderLayout.CENTER);
		
		labelAmount = new JLabel("Amount");
		labelAmount.setToolTipText(amountTooltip);
		labelPanel.add(labelAmount);
		
		amountPanel = new JPanel();
		amountPanel.setLayout(new GridLayout(0, 2, 5, 0));
		interactionPanel.add(amountPanel);
		
		minAmountPanel = new JPanel();
		minAmountPanel.setLayout(new BorderLayout(5, 0));
		amountPanel.add(minAmountPanel);
		
		labelMinAmount = new JLabel("From");
		labelMinAmount.setToolTipText(amountTooltip);
		minAmountPanel.add(labelMinAmount, BorderLayout.WEST);
		
		minAmountSpinner = new JSpinner();
		minAmountSpinner.setToolTipText(amountTooltip);
		minAmountSpinner.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				maxAmountSpinner.setModel(new SpinnerNumberModel((Integer) maxAmountSpinner.getValue(), 
						(Integer) minAmountSpinner.getValue(), new Integer(64), new Integer(1)));
			}
		});
		minAmountSpinner.setModel(new SpinnerNumberModel(new Integer(1), new Integer(0), new Integer(1), new Integer(1)));
		minAmountPanel.add(minAmountSpinner, BorderLayout.CENTER);
		
		maxAmountPanel = new JPanel();
		maxAmountPanel.setLayout(new BorderLayout(5, 0));
		amountPanel.add(maxAmountPanel);
		
		labelMaxAmount = new JLabel("To");
		labelMaxAmount.setToolTipText(amountTooltip);
		maxAmountPanel.add(labelMaxAmount, BorderLayout.WEST);
		
		maxAmountSpinner = new JSpinner();
		maxAmountSpinner.setToolTipText(amountTooltip);
		maxAmountSpinner.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				minAmountSpinner.setModel(new SpinnerNumberModel((Integer) minAmountSpinner.getValue(), 
						new Integer(0), (Integer) maxAmountSpinner.getValue(), new Integer(1)));
			}
		});
		maxAmountSpinner.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), new Integer(64), new Integer(1)));
		maxAmountPanel.add(maxAmountSpinner, BorderLayout.CENTER);
		
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		
		addButton = new JButton("Add");
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				add();
			}
		});
		buttonPanel.add(addButton);
		
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cancel();
			}
		});
		buttonPanel.add(cancelButton);
		
		addWindowListener(new WindowListener() {
			@Override
			public void windowActivated(WindowEvent arg0) {}
			@Override
			public void windowClosed(WindowEvent arg0) {}
			@Override
			public void windowClosing(WindowEvent arg0) {}
			@Override
			public void windowDeactivated(WindowEvent arg0) {}
			@Override
			public void windowDeiconified(WindowEvent arg0) {}
			@Override
			public void windowIconified(WindowEvent arg0) {}
			@Override
			public void windowOpened(WindowEvent arg0) {
				setBounds(100, 100, 300, (int) (mainPanel.getSize().getHeight() + buttonPanel.getSize().getHeight() 
						+ getSize().getHeight() - getContentPane().getSize().getHeight()) + 15);
			}
		});
		
		setModalityType(ModalityType.APPLICATION_MODAL);
		setVisible(true);
	}
	
	private void chooseMaterial(){
		new MaterialChooseWindow(MaterialChooseWindow.ITEMS_BLOCKS_NONE, new ObjectRunnable() {
			@Override
			public void run(Object obj) {
				materialLabel.setText((String) obj);
			}
		});
	}
	
	private void add(){
		String material = materialLabel.getText();
		if (material != null && material != ""){
			ItemStackResource item = new ItemStackResource();

			if (material.split("#").length == 2){
				try{
					item.meta = Integer.parseInt(material.split("#")[1]);
					material = material.split("#")[0];
				} catch (Exception e){
					e.printStackTrace();
				}
			}
			if (MaterialResources.isItem(material))
				item.item = material;
			else
				item.block = material;
			item.amount = (Integer) minAmountSpinner.getValue();
			item.amountincrease = (Integer) maxAmountSpinner.getValue() - (Integer) minAmountSpinner.getValue();
			
			runnable.run(item);
			dispose();
		}
		else{
			int selected = JOptionPane.showConfirmDialog(this, "You haven't chosen a material yet.", 
					"Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
			if (selected != JOptionPane.OK_OPTION)
				dispose();
		}
	}
	
	private void cancel(){
		dispose();
	}
}
