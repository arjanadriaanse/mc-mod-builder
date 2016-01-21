package twintro.minecraft.modbuilder.editor.interfaces.choosewindows;

import java.awt.BorderLayout;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

import javax.swing.AbstractListModel;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import twintro.minecraft.modbuilder.editor.Editor;
import twintro.minecraft.modbuilder.editor.interfaces.activitypanels.ActivityPanel;
import twintro.minecraft.modbuilder.editor.interfaces.helperclasses.ImageListCellRenderer;
import twintro.minecraft.modbuilder.editor.interfaces.helperclasses.IconDialog;
import twintro.minecraft.modbuilder.editor.interfaces.helperclasses.IconFrame;
import twintro.minecraft.modbuilder.editor.interfaces.helperclasses.ListPanel;

public class TextureChooseWindow extends IconDialog {
	private ListPanel listPanel;
	private ObjectRunnable runnable;
	
	public TextureChooseWindow(ObjectRunnable runnable){
		this.runnable = runnable;

		setModal(true);
		setBounds(100, 100, 530, 400);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Choose Texture");
		
		listPanel = new ListPanel();
		listPanel.setLayout(new BorderLayout(0, 0));
		listPanel.elements = Editor.getTextureList();
		getContentPane().add(listPanel, BorderLayout.CENTER);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		listPanel.add(scrollPane, BorderLayout.CENTER);
		
		JList list = new JList();
		scrollPane.setViewportView(list);
		list.setVisibleRowCount(0);
		list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		list.setFixedCellWidth(128);
		list.setCellRenderer(new ImageListCellRenderer(listPanel));
		list.setModel(new AbstractListModel() {
			public int getSize() {
				return listPanel.elements.size();
			}
			public Object getElementAt(int index) {
				return ((String) listPanel.elements.keySet().toArray()[index]).replaceAll("_", " ");
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
	
	public void choose(String texture){
		runnable.run(texture);
		dispose();
	}
}
