package ui;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import javax.swing.AbstractAction;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;
import javax.swing.MenuSelectionManager;
import javax.swing.SwingUtilities;
import javax.swing.text.JTextComponent;

import utilities.Util;

/**
 * This class defines a context menu that has cut, copy, paste, delete, 
 * and "select all" items for any text component such as JTextArea or
 * JTextField
 * 
 * @author Mahmood Khan
 * @version 2012-03-10 1.0
 *
 */
public class ContextMenuForTextComps extends EventQueue {
	protected void dispatchEvent(AWTEvent event) {
		super.dispatchEvent(event);
		
		// interested only in mouse events
		if (!(event instanceof MouseEvent))
			return;

		MouseEvent me = (MouseEvent) event;

		// interested only in pop-up triggers
		if (!me.isPopupTrigger())
			return;
		
		Component comp;
		try {
			// me.getComponent() returns the heavy weight component on which
			// event occurred
			comp = SwingUtilities.getDeepestComponentAt(
					me.getComponent(), me.getX(), me.getY());
		} catch (NullPointerException e) {
			return;
		}
		// interested only in text components
		if (!(comp instanceof JTextComponent))
			return;

		// no pop-up shown by user code
		if (MenuSelectionManager.defaultManager().getSelectedPath().length > 0)
			return;

		// create a pop-up menu and show it
		JTextComponent tc = (JTextComponent) comp;
		JPopupMenu menu = new JPopupMenu();
		
		//create the cut menu item
		JMenuItem cut = new JMenuItem(new CutAction(tc));
		cut.setIcon(Util.getImageIcon("../resources/cut.png"));
		cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
		
		//create the copy menu item
		JMenuItem copy = new JMenuItem(new CopyAction(tc));
		copy.setIcon(Util.getImageIcon("../resources/copy.png"));
		copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
		
		//create the paste menu item
		JMenuItem paste = new JMenuItem(new PasteAction(tc));
		paste.setIcon(Util.getImageIcon("../resources/paste.png"));
		paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
		
		//create the delete menu item
		JMenuItem delete = new JMenuItem(new DeleteAction(tc));
		delete.setIcon(Util.getImageIcon("../resources/delete.png"));
		delete.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
		
		JMenuItem selectAll = new JMenuItem(new SelectAllAction(tc));
		selectAll.setIcon(Util.getImageIcon("../resources/selectAll.png"));
		selectAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
		
		//menu.add(new CutAction(tc));
		menu.add(cut);
		menu.add(copy);
		menu.add(paste);
		menu.add(delete);
		menu.addSeparator();
		menu.add(selectAll);

		Point pt = SwingUtilities.convertPoint(me.getComponent(), me.getPoint(), tc);
		menu.show(tc, pt.x, pt.y);
	}

	/**
	 * A sub class that defines the Cut action
	 * 
	 * @author Mahmood Khan
	 * @version 2012-03-10 1.0
	 */
	class CutAction extends AbstractAction {
		/**
		 * It has to do with serialization; it is here to avoid compiler warning
		 */
		private static final long serialVersionUID = 1L;
		
		private JTextComponent comp;

		public CutAction(JTextComponent comp) {
			super("Cut");
			this.comp = comp;
			
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			comp.cut();
		}
		
		@Override
		public boolean isEnabled() {
			return comp.isEditable() && comp.isEnabled() && comp.getSelectedText() != null;
		}
	}

	/**
	 *  A sub class that defines Paste action
	 *  
	 * @author Mahmood Khan
	 *
	 */
	class PasteAction extends AbstractAction {
		/**
		 * It has to do with serialization; it is here to avoid compiler warning
		 */
		private static final long serialVersionUID = 1L;
		
		private JTextComponent comp;

		public PasteAction(JTextComponent comp) {
			super("Paste");
			this.comp = comp;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			comp.paste();
		}
		
		@Override
		public boolean isEnabled() {
			if (comp.isEditable() && comp.isEnabled()) {
				Transferable contents = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(this);
				return contents.isDataFlavorSupported(DataFlavor.stringFlavor);
			} else
				return false;
		}
	}

	/**
	 *  A sub class that defines the Delete action
	 *  
	 * @author Mahmood Khan
	 *
	 */
	class DeleteAction extends AbstractAction {
		/**
		 * It has to do with serialization; it is here to avoid compiler warning
		 */
		private static final long serialVersionUID = 1L;
		
		private JTextComponent comp;

		public DeleteAction(JTextComponent comp) {
			super("Delete");
			this.comp = comp;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			comp.replaceSelection(null);
		}
		
		@Override
		public boolean isEnabled() {
			return comp.isEditable() && comp.isEnabled() && comp.getSelectedText() != null;
		}
	}

	/**
	 *  A sub class that defines the Copy action
	 *  
	 * @author Mahmood Khan
	 *
	 */
	class CopyAction extends AbstractAction {
		/**
		 * It has to do with serialization; it is here to avoid compiler warning
		 */
		private static final long serialVersionUID = 1L;
		
		private JTextComponent comp;

		public CopyAction(JTextComponent comp) {
			super("Copy");
			this.comp = comp;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			comp.copy();
		}
		
		@Override
		public boolean isEnabled() {
			return comp.isEnabled() && comp.getSelectedText() != null;
		}
	}

	/**
	 *  A sub class that defines the "select all" action
	 *  
	 * @author Mahmood Khan
	 *
	 */
	class SelectAllAction extends AbstractAction {
		/**
		 * It has to do with serialization; it is here to avoid compiler warning
		 */
		private static final long serialVersionUID = 1L;
		
		private JTextComponent comp;

		public SelectAllAction(JTextComponent comp) {
			super("Select All");
			this.comp = comp;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			comp.selectAll();
		}

		@Override
		public boolean isEnabled() {
			return comp.isEnabled() && comp.getText().length() > 0;
		}
	}
}