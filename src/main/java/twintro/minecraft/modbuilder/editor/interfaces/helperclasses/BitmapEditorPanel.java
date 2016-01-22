package twintro.minecraft.modbuilder.editor.interfaces.helperclasses;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

public class BitmapEditorPanel extends Panel implements MouseInputListener, KeyListener {
	private BufferedImage bitmap;
	private Set<ActionListener> actionListeners = new HashSet<ActionListener>();
	
	public BufferedImage getBitmap() {
		return bitmap;
	}

	public void setBitmap(BufferedImage bitmap) {
		this.bitmap = bitmap;
		this.repaint();
	}

	private int cols, rows;
	
	private int fieldSize, xOffset, yOffset;
	
	private final int clearColor = 0;
	private int color;
	
	private Stack<Set<Action>> undoStack = new Stack<Set<Action>>();
	private Stack<Set<Action>> redoStack = new Stack<Set<Action>>();
	
	private Set<Action> actions;
	
	public BitmapEditorPanel(BufferedImage bitmap) {
		this.bitmap = bitmap;
		this.cols = bitmap.getWidth();
		this.rows = bitmap.getHeight();
		
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}
	
	@Override
	public void paint(java.awt.Graphics g) {
		fieldSize = Math.min(this.getWidth() / cols, this.getHeight() / rows);
		xOffset = (this.getWidth() - cols * fieldSize) / 2;
		yOffset = (this.getHeight() - rows * fieldSize) / 2;
		
		for (int col = 0; col < cols; col++) {
			for (int row = 0; row < rows; row++) {
				g.setColor(new Color(bitmap.getRGB(col, row), true));
				g.fillRect(xOffset + col * fieldSize, yOffset + row * fieldSize, fieldSize, fieldSize);
			}
		}
	}
	
	private Point getField(int x, int y) {
		int col = (x - xOffset) / fieldSize;
		int row = (y - yOffset) / fieldSize;
		if (col >= 0 && col < cols && row >= 0 && row < rows)
			return new Point(col, row);
		else
			return null;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		this.actions = new HashSet<Action>();
		undoStack.push(actions);
		redoStack.clear();
		this.color = e.getButton() == MouseEvent.BUTTON1 ? this.getForeground().getRGB() : this.clearColor;
		mouseDragged(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mouseMoved(e);
		this.actions = null;
		actionPerformed();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
			
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (actions != null) {
			Point field = getField(e.getX(), e.getY());
			if (field != null) {
				Action action = new PixelChange(field, bitmap.getRGB(field.x, field.y), this.color);
				if (!this.actions.contains(action)) {
					this.actions.add(action);
					action.redo(bitmap);
					this.repaint();
				}
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.isControlDown()) {
			if (e.getKeyCode() == KeyEvent.VK_Z) {
				if (canUndo()) {
					undo();
				}
			}
			else if (e.getKeyCode() == KeyEvent.VK_Y) {
				if (canRedo()) {
					redo();
				}
			}
		}
	}
	
	public void redo() {
		Set<Action> actions = undoStack.push(redoStack.pop());
		for (Action action : actions)
			action.redo(this.bitmap);
		
		this.repaint();
		actionPerformed();
	}
	
	public void undo() {
		Set<Action> actions = redoStack.push(undoStack.pop());
		for (Action action : actions)
			action.undo(this.bitmap);
		
		this.repaint();
		actionPerformed();
	}
	
	public boolean canRedo() {
		return !redoStack.isEmpty();
	}
	
	public boolean canUndo() {
		return !undoStack.isEmpty();
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}
	
	private interface Action {
		void redo(BufferedImage image);
		void undo(BufferedImage image);
	}
	
	private class PixelChange extends Point implements Action {
		private int oldColor, newColor;
		
		public PixelChange(Point point, int oldColor, int newColor) {
			super(point);
			this.oldColor = oldColor;
			this.newColor = newColor;
		}

		@Override
		public void redo(BufferedImage image) {
			image.setRGB(this.x, this.y, newColor);
			
		}

		@Override
		public void undo(BufferedImage image) {
			image.setRGB(this.x, this.y, oldColor);
		}
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setSize(800, 800);
		BitmapEditorPanel panel = new BitmapEditorPanel(new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB));
		frame.getContentPane().add(panel);
		frame.setVisible(true);
		panel.addKeyListener(panel);
		panel.setFocusable(true);
		panel.requestFocus();
	}

	public void clear() {
		Graphics graphics = bitmap.getGraphics();
		graphics.setColor(getForeground());
		graphics.fillRect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		graphics.dispose();
		
		this.repaint();
		actionPerformed();
	}
	
	public void addActionListener(ActionListener l) {
		actionListeners.add(l);
	}
	
	public void removeActionListener(ActionListener l) {
		actionListeners.remove(l);
	}
	
	private void actionPerformed() {
		for (ActionListener l : actionListeners) {
			l.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
		}
	}
}
