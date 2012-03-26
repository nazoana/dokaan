package widgets;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import javax.swing.AbstractAction;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
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

		// me.getComponent() returns the heavy weight component on which event occurred
		Component comp = SwingUtilities.getDeepestComponentAt(
				me.getComponent(), me.getX(), me.getY());

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
		
		//create the copy menu item
		JMenuItem copy = new JMenuItem(new CopyAction(tc));
		copy.setIcon(Util.getImageIcon("../resources/copy.png"));
		
		//create the paste menu item
		JMenuItem paste = new JMenuItem(new PasteAction(tc));
		paste.setIcon(Util.getImageIcon("../resources/paste.png"));
		
		//create the delete menu item
		JMenuItem delete = new JMenuItem(new DeleteAction(tc));
		delete.setIcon(Util.getImageIcon("../resources/delete.png"));
		
		JMenuItem selectAll = new JMenuItem(new SelectAllAction(tc));
		selectAll.setIcon(Util.getImageIcon("../resources/selectAll.png"));
		
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
	 *
	 */
	class CutAction extends AbstractAction {
		/**
		 * It has to do with serialization; it is here to avoid compiler warning
		 */
		private static final long serialVersionUID = 1L;
		JTextComponent comp;

		public CutAction(JTextComponent comp) {
			super("Cut         Ctrl+X");
			this.comp = comp;
		}

		public void actionPerformed(ActionEvent e) {
			comp.cut();
		}

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
		JTextComponent comp;

		public PasteAction(JTextComponent comp) {
			super("Paste      Ctrl+V");
			this.comp = comp;
		}

		public void actionPerformed(ActionEvent e) {
			comp.paste();
		}

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
		JTextComponent comp;

		public DeleteAction(JTextComponent comp) {
			super("Delete");
			this.comp = comp;
		}

		public void actionPerformed(ActionEvent e) {
			comp.replaceSelection(null);
		}

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
		JTextComponent comp;

		public CopyAction(JTextComponent comp) {
			super("Copy      Ctrl+C");
			this.comp = comp;
		}

		public void actionPerformed(ActionEvent e) {
			comp.copy();
		}

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
		JTextComponent comp;

		public SelectAllAction(JTextComponent comp) {
			super("Select All  Ctrl+A");
			this.comp = comp;
		}

		public void actionPerformed(ActionEvent e) {
			comp.selectAll();
		}

		public boolean isEnabled() {
			return comp.isEnabled() && comp.getText().length() > 0;
		}
	}
}