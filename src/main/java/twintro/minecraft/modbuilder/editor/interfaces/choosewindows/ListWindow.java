package twintro.minecraft.modbuilder.editor.interfaces.choosewindows;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

import javax.swing.AbstractListModel;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import twintro.minecraft.modbuilder.editor.interfaces.helperclasses.CustomListCellRenderer;
import twintro.minecraft.modbuilder.editor.interfaces.helperclasses.IconDialog;
import twintro.minecraft.modbuilder.editor.interfaces.helperclasses.ListPanel;
import twintro.minecraft.modbuilder.editor.resources.MaterialResources;

public class ListWindow extends IconDialog {
	private JPanel panel;
	private JList list;
	private String[] values;
	private ObjectRunnable runnable;
	
	private ListWindow(JPanel panel, ObjectRunnable runnable){
		this.panel = panel;
		this.runnable = runnable;

		setModal(true);
		setBounds(100, 100, 300, 200);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Choose Material");
		
		this.panel.setLayout(new BorderLayout(0, 0));
		getContentPane().add(this.panel, BorderLayout.CENTER);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.panel.add(scrollPane, BorderLayout.CENTER);
		
		list = new JList();
		scrollPane.setViewportView(list);
		list.setVisibleRowCount(0);
		list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
	}
	
	public ListWindow(Map<String, ImageIcon> elements, ObjectRunnable runnable){
		this(new ListPanel(), runnable);
		((ListPanel)panel).elements = elements;
		
		list.setFixedCellWidth(128);
		list.setCellRenderer(new CustomListCellRenderer((ListPanel)panel));
		list.setModel(new AbstractListModel() {
			public int getSize() {
				return ((ListPanel)panel).elements.size();
			}
			public Object getElementAt(int index) {
				return ((ListPanel)panel).elements.keySet().toArray()[index];
			}
		});
		list.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				if (e.getClickCount() == 2){
					String value = (String) ((JList) e.getSource()).getSelectedValue();
					choose("modbuilder:" + value);
				}
			}
		});
		
		setVisible(true);
	}
	
	public ListWindow(String[] elements, ObjectRunnable runnable){
		this(new JPanel(), runnable);
		values = elements;

		list.setFixedCellWidth(128);
		list.setModel(new AbstractListModel() {
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		list.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				if (e.getClickCount() == 2){
					String value = (String) ((JList) e.getSource()).getSelectedValue();
					choose(MaterialResources.getId(value));
				}
			}
		});
		
		setVisible(true);
	}
	
	private void choose(String value){
		runnable.run(value);
		dispose();
	}
}
