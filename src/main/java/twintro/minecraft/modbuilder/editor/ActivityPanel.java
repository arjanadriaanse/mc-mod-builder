package twintro.minecraft.modbuilder.editor;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class ActivityPanel extends JPanel {
	private Object[] ol;
	
	public ActivityPanel(String header, String button) {
		this.setLayout(new BorderLayout(0, 0));
		addElements(header, button);
		createObjectList();
	}
	
	private void addElements(String header, String button){
		JPanel mainPanel = new JPanel();
		this.add(mainPanel, BorderLayout.NORTH);
		mainPanel.setLayout(new BorderLayout(0, 0));
		
		JButton addButton = new ActivityButton(button);
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addObject();
			}
		});
		mainPanel.add(addButton, BorderLayout.EAST);
		
		JLabel headerLabel = new JLabel(header);
		headerLabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
		mainPanel.add(headerLabel, BorderLayout.WEST);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.add(scrollPane, BorderLayout.CENTER);
		
		JList list = new JList();
		scrollPane.setViewportView(list);
		list.setFont(new Font("Tahoma", Font.PLAIN, 47));
		list.setVisibleRowCount(0);
		list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		list.setModel(new AbstractListModel() {
			public int getSize() {
				return ol.length;
			}
			public Object getElementAt(int index) {
				return ol[index];
			}
		});
	}
	
	private void createObjectList(){
		ol = new Object[]{"yolo"};
	}
	
	private void addObject(){
		
	}
}
