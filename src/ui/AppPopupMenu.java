package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;

import utilities.Util;
import widgets.AppMenuItem;

/**
 * 
 * @author Mahmood Khan
 * @version 2012-03-28 1.0
 *
 */
public class AppPopupMenu extends JPopupMenu implements ActionListener{

	/**
	 * It has to do with serialization; it is not needed here;
	 * it is here to avoid a compilation warning.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 */
	public AppPopupMenu(){
		super();
	}
	
	/**
	 * Constructs a JPopupMenu with the specified title. 
	 * 
	 * @param label - the string that a UI may use to display as a title for the popup menu.
	 */
	public AppPopupMenu(String label) {
		super(label);
	}
	
	/**
	 * Configures the pop-up menu for the table right click action.
	 */
	public void configureForTable(){
		AppMenuItem copyCell = new AppMenuItem("Copy Cell", Util.getImageIcon("../resources/copy.png"));
		copyCell.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
		copyCell.setToolTipText("Copies the content of the cell that is right-clicked");
		
		AppMenuItem copyRow = new AppMenuItem("Copy Row", Util.getImageIcon("../resources/copy.png"));
		copyRow.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.SHIFT_MASK + ActionEvent.CTRL_MASK));
		copyRow.setToolTipText("Copies the selected row");

		AppMenuItem copyTable = new AppMenuItem("Copy Table", Util.getImageIcon("../resources/copy.png"));
		copyTable.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.ALT_MASK));
		copyTable.setToolTipText("Copies the entire table");

		AppMenuItem delete = new AppMenuItem("Delete", Util.getImageIcon("../resources/delete.png"));
		delete.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
		delete.setToolTipText("Deletes the selected row");
		
		AppMenuItem print = new AppMenuItem("Print", Util.getImageIcon("../resources/print.png"));
		print.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
		print.setToolTipText("Prints the entire table");
		
		AppMenuItem edit = new AppMenuItem("Edit", Util.getImageIcon("../resources/edit.png"));
		edit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
		edit.setToolTipText("Edits the selected row");

		copyCell.setActionCommand("copyCell");
		copyCell.addActionListener(this);
		
		copyRow.setActionCommand("copySingleRow");
		copyRow.addActionListener(this);

		copyTable.setActionCommand("copyAll");
		copyTable.addActionListener(this);

		print.setActionCommand("print");
		print.addActionListener(this);
		
		edit.setActionCommand("edit");
		edit.addActionListener(this);
		
		delete.setActionCommand("delete");
		delete.addActionListener(this);

		add(copyCell);
		add(copyRow);
		add(copyTable);
		addSeparator();
		add(print);
		add(edit);
		addSeparator();
		add(delete);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
