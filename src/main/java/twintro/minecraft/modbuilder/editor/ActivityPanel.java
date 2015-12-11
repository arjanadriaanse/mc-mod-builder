package twintro.minecraft.modbuilder.editor;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class ActivityPanel extends JPanel {
	private Map<String, ImageIcon> elements;
	
	public ActivityPanel(String header, String button) {
		this.setLayout(new BorderLayout(0, 0));
		elements = createElements();
		addElements(header, button);
	}
	
	private Map<String, ImageIcon> createElements(){
		List<String> nameList = new ArrayList<String>();
        nameList.add("climber");
        nameList.add("colorblock_b");
        return createElements(nameList);
	}
	
	private Map<String, ImageIcon> createElements(List<String> list){
		Map<String, ImageIcon> map = new HashMap<String, ImageIcon>();
        for (String s : list) {
            map.put(s, new ImageIcon(
                    getClass().getResource("/editor/" + s + ".png")));
        }
        return map;
	}
	
	private void addElements(String header, String button){
		JPanel mainPanel = new JPanel();
		this.add(mainPanel, BorderLayout.NORTH);
		mainPanel.setLayout(new BorderLayout(0, 0));
		
		JButton addButton = new ActivityButton(button);
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				add();
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
		list.setCellRenderer(new CustomListCellRenderer(elements));
		list.setModel(new AbstractListModel() {
			public int getSize() {
				return elements.size();
			}
			public Object getElementAt(int index) {
				return elements.keySet().toArray()[index];
			}
		});
	}
	
	private void add(){
		
	}
}
