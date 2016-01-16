package twintro.minecraft.modbuilder.editor.interfaces.editors;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import twintro.minecraft.modbuilder.data.resources.structures.GroundStructureResource;
import twintro.minecraft.modbuilder.data.resources.structures.OreStructureResource;
import twintro.minecraft.modbuilder.data.resources.structures.StructureType;
import twintro.minecraft.modbuilder.editor.Editor;
import twintro.minecraft.modbuilder.editor.interfaces.activitypanels.StructureActivityPanel;
import twintro.minecraft.modbuilder.editor.interfaces.choosewindows.ObjectRunnable;
import twintro.minecraft.modbuilder.editor.interfaces.helperclasses.WindowClosingVerifierListener;
import twintro.minecraft.modbuilder.editor.interfaces.helperclasses.WindowClosingVerifierUser;
import twintro.minecraft.modbuilder.editor.resources.BlockElement;

import javax.swing.JToggleButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JSpinner;
import javax.swing.SwingConstants;

public class GroundStructureEditor extends WindowClosingVerifierUser {
	private JPanel contentPane;
	private JComboBox dimensionComboBox;
	private JSpinner veinSizeSpinner;
	private JSpinner minYspinner;
	private JSpinner maxYspinner;
	private JLabel coverBlockLabel;
	private JButton coverBlockButton;
	private JLabel onblockslabel;
	private JButton onBlocksButton;
	private JSpinner amountperchunkSpinner;

	private String name;
	private ObjectRunnable runnable;
	private ObjectRunnable closeHandler;

	public GroundStructureEditor(String name, ObjectRunnable runnable, ObjectRunnable closeHandler) {
		this.name = name;
		this.runnable = runnable;
		this.closeHandler = closeHandler;
		
		setBounds(100, 100, 390, 400);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowClosingVerifierListener());
		setTitle("Edit structure: " + name);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.SOUTH);
		panel.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		
		JButton btnSaveItem = new JButton("Save Item");
		panel.add(btnSaveItem);
		btnSaveItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				saveStructure();
			}
		});
		
		JButton btnCancel = new JButton("Cancel");
		panel.add(btnCancel);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cancel();
			}
		});

			
		JPanel panel_6 = new JPanel();
		getContentPane().add(panel_6, BorderLayout.NORTH);
		panel_6.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		panel_6.add(panel_1);
		panel_1.setLayout(new BorderLayout(5, 0));
		
		JPanel panel_2 = new JPanel();
		panel_1.add(panel_2, BorderLayout.WEST);
		panel_2.setLayout(new GridLayout(0, 1, 0, 5));
		
		JLabel lblNewLabel_16 = new JLabel("");
		panel_2.add(lblNewLabel_16);
		
		JLabel lblAsdasdasd = new JLabel("Cover block");
		panel_2.add(lblAsdasdasd);
		
		JLabel lblBlocksToCover = new JLabel("Blocks to cover");
		panel_2.add(lblBlocksToCover);
		
		JLabel lblDimension = new JLabel("Dimension");
		panel_2.add(lblDimension);
		
		JLabel lblAmount = new JLabel("Amount ");
		lblAmount.setToolTipText("sets the amount to generate on a chunk");
		panel_2.add(lblAmount);
				
		JPanel panel_3 = new JPanel();
		panel_1.add(panel_3, BorderLayout.CENTER);
		panel_3.setLayout(new GridLayout(0, 1, 0, 5));
		
		JLabel lblNewLabel_15 = new JLabel("Generic item options:");
		panel_3.add(lblNewLabel_15);
		
		JPanel panel_7 = new JPanel();
		panel_3.add(panel_7);
		panel_7.setLayout(new BorderLayout(0, 0));
		
		coverBlockLabel = new JLabel("");
		panel_7.add(coverBlockLabel, BorderLayout.CENTER);
		
		coverBlockButton = new JButton("Choose");
		coverBlockButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chooseBlock();
			}
		});
		panel_7.add(coverBlockButton, BorderLayout.EAST);
		
		JPanel panel1 = new JPanel();
		panel_3.add(panel1);
		panel1.setLayout(new BorderLayout(0, 0));
		
		onblockslabel = new JLabel("");
		panel1.add(onblockslabel, BorderLayout.CENTER);
		
		onBlocksButton = new JButton("Choose");
		onBlocksButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chooseBlocksToCover();
			}
		});
		panel1.add(onBlocksButton, BorderLayout.EAST);
		
		JPanel panel_5 = new JPanel();
		panel_3.add(panel_5);
		panel_5.setLayout(new BorderLayout(0, 0));
		
		dimensionComboBox = new JComboBox();
		panel_5.add(dimensionComboBox, BorderLayout.NORTH);
		dimensionComboBox.setModel(new DefaultComboBoxModel(new String[] {"Overworld", "Nether", "End"}));
		
		JPanel panel_4 = new JPanel();
		panel_3.add(panel_4);
		panel_4.setLayout(new BorderLayout(0, 0));
		
		amountperchunkSpinner = new JSpinner();
		panel_4.add(amountperchunkSpinner, BorderLayout.CENTER);
			
		setVisible(true);
	}
	
	protected void chooseBlocksToCover() {
		
	}
	
	protected void chooseBlock() {
		
	}
	
	protected void saveStructure() {
			GroundStructureResource savable = new GroundStructureResource();
			
			switch(dimensionComboBox.getSelectedIndex()){
				case 1: savable.dimension = -1;
				case 2: savable.dimension = 1;
				default: savable.dimension = 0;
			}
			savable.amountperchunk = (Integer) amountperchunkSpinner.getValue();
			savable.type = StructureType.ground;
	}

	@Override
	public boolean save() {
		return false;
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
