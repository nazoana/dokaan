package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenuBar;
import javax.swing.KeyStroke;
import javax.swing.text.DefaultEditorKit;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

import utilities.Globals;
import utilities.Util;
import view.DokaanMain;
import widgets.AppMenu;
import widgets.AppMenuItem;

/**
 * This is the main menu bar of the application. It provides
 * access to various functions and parts of the system.
 * 
 * @author Mahmood Khan
 * @version 2012-03-25 1.0
 *
 */
public class AppMenubar extends JMenuBar implements ActionListener {

	/**
	 * This has to do with serialization; it is not important, but it
	 * is placed here to avoid a compilation warning
	 */
	private static final long serialVersionUID = 1L;
	
	public static final UndoManager UNDO_MANAGER = new UndoManager();

	private static AppMenuItem undoAction;
	
	private static AppMenuItem redoAction;
	
	/**
	 * Constructor
	 */
	public AppMenubar() {
		super();
		setupMenu();
	}
	
	/**
	 * Sets up the menu bar with all the menu items
	 */
	private void setupMenu(){
		//setOpaque(true);
		//setBackground(Globals.GREEN_VERY_LIGHT);
		setFont(Globals.FONT_APPLICATION);
		
		AppMenu fileMenu = new AppMenu("File");
		AppMenu editMenu = new AppMenu("Edit");
		AppMenu toolsMenu = new AppMenu("Tools");
		AppMenu reportsMenu = new AppMenu("Reports");
		AppMenu helpMenu = new AppMenu("Help");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		editMenu.setMnemonic(KeyEvent.VK_E);
		toolsMenu.setMnemonic(KeyEvent.VK_T);
		reportsMenu.setMnemonic(KeyEvent.VK_R);
		helpMenu.setMnemonic(KeyEvent.VK_H);
        add(fileMenu);
        add(editMenu);
        add(toolsMenu);
        add(reportsMenu);
        add(helpMenu);
        
        // define items for File Menu -> New
        AppMenuItem newCustomer = new AppMenuItem("Customer", Util.getImageIcon("../resources/customer.png"));
        AppMenuItem newOrder = new AppMenuItem("Order", Util.getImageIcon("../resources/order.png"));
        AppMenuItem newSupplier = new AppMenuItem("Supplier", Util.getImageIcon("../resources/supplier.png"));

        // define items for File Menu -> Open
        AppMenuItem customers = new AppMenuItem("Customers", Util.getImageIcon("../resources/customer.png"));
        AppMenuItem orders = new AppMenuItem("Orders", Util.getImageIcon("../resources/order.png"));
        AppMenuItem suppliers = new AppMenuItem("Suppliers", Util.getImageIcon("../resources/supplier.png"));
        
        // define items for File Menu
        AppMenuItem overview = new AppMenuItem("Overview", Util.getImageIcon("../resources/summary.png"));
        AppMenuItem exitAction = new AppMenuItem("Exit", Util.getImageIcon("../resources/exit.png"));
        exitAction.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
        
      
        // define items for edit menu
        undoAction = new AppMenuItem("Undo", Util.getImageIcon("../resources/undo.png"));
        undoAction.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
        undoAction.setEnabled(false);
        
        redoAction = new AppMenuItem("Redo", Util.getImageIcon("../resources/redo.png"));
        redoAction.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.CTRL_MASK));
        redoAction.setEnabled(false);
        
        //AppMenuItem cutAction = new AppMenuItem("Cut", Util.getImageIcon("../resources/cut.png"));
        AppMenuItem cutAction = new AppMenuItem(new DefaultEditorKit.CutAction());
        cutAction.setText("Cut");
        cutAction.setIcon(Util.getImageIcon("../resources/cut.png"));
        cutAction.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
        
        //AppMenuItem copyAction = new AppMenuItem("Copy", Util.getImageIcon("../resources/copy.png"));
        AppMenuItem copyAction = new AppMenuItem(new DefaultEditorKit.CopyAction());
        copyAction.setText("Copy");
        copyAction.setIcon(Util.getImageIcon("../resources/copy.png"));
        copyAction.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        
        //AppMenuItem pasteAction = new AppMenuItem("Paste", Util.getImageIcon("../resources/paste.png"));
        AppMenuItem pasteAction = new AppMenuItem(new DefaultEditorKit.PasteAction());
        pasteAction.setText("Paste");
        pasteAction.setIcon(Util.getImageIcon("../resources/paste.png"));
        pasteAction.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
        
        //AppMenuItem deleteAction = new AppMenuItem("Delete", Util.getImageIcon("../resources/delete.png"));
        
        /*
        AppMenuItem selectAllAction = new AppMenuItem(DefaultEditorKit.selectAllAction);
        selectAllAction.setText("Select All");
        selectAllAction.setIcon(Util.getImageIcon("../resources/selectAll.png"));
        selectAllAction.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
        */

        // define items for tools menu
        AppMenuItem backupItem = new AppMenuItem("Backup", Util.getImageIcon("../resources/backup.png"));
        AppMenuItem restoreItem = new AppMenuItem("Restore", Util.getImageIcon("../resources/download.png"));
        AppMenuItem optionsItem = new AppMenuItem("Options", Util.getImageIcon("../resources/options.png"));
        
        AppMenuItem customersWithCredit = new AppMenuItem("Customers Credit");
        
        // define items for help menu
        AppMenuItem helpContentItem = new AppMenuItem("Help Content", Util.getImageIcon("../resources/help.png"));
        AppMenuItem registerItem = new AppMenuItem("Register", Util.getImageIcon("../resources/signup.png"));
        AppMenuItem aboutItem = new AppMenuItem("About Dokaan", Util.getImageIcon("../resources/about.png"));
        AppMenuItem bugItem = new AppMenuItem("Report a Bug", Util.getImageIcon("../resources/bug.png"));
        AppMenuItem feedbackItem = new AppMenuItem("Provide Feedback", Util.getImageIcon("../resources/feedback.png"));
        AppMenuItem sendLogItem = new AppMenuItem("Send Logs", Util.getImageIcon("../resources/send.png"));
        helpContentItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
        // define action listeners
        newCustomer.setActionCommand("newCustomer");
        newCustomer.addActionListener(this);
        overview.setActionCommand("overview");
        overview.addActionListener(this);
        customers.setActionCommand("openCustomers");
        customers.addActionListener(this);
        exitAction.setActionCommand("exit");
        exitAction.addActionListener(this);
        
        undoAction.setActionCommand("undo");
        undoAction.addActionListener(this);
        
        redoAction.setActionCommand("redo");
        redoAction.addActionListener(this);
        
        // Add items to File -> New sub-menu
        AppMenu newSubmenu = new AppMenu("New");
        newSubmenu.setIcon(Util.getImageIcon("../resources/new.png"));
        newSubmenu.add(newCustomer);
        newSubmenu.add(newOrder);
        newSubmenu.add(newSupplier);
        
        // Add items to File -> Open sub-menu
        AppMenu openSubmenu = new AppMenu("Open");
        openSubmenu.setIcon(Util.getImageIcon("../resources/table.png"));
        openSubmenu.add(customers);
        openSubmenu.add(orders);
        openSubmenu.add(suppliers);
        
        // add items to File Menu
        fileMenu.add(newSubmenu);
        fileMenu.add(openSubmenu);
        fileMenu.add(overview);
        fileMenu.addSeparator();
        fileMenu.add(exitAction);
        
        // add items to Edit Menu
        editMenu.add(undoAction);
        editMenu.add(redoAction);
        editMenu.addSeparator();
        editMenu.add(cutAction);
        editMenu.add(copyAction);
        editMenu.add(pasteAction);
        //editMenu.addSeparator();
        //editMenu.add(deleteAction);
        //editMenu.add(selectAllAction);
        
        // add items to Tools Menu
        toolsMenu.add(backupItem);
        toolsMenu.add(restoreItem);
        toolsMenu.addSeparator();
        toolsMenu.add(optionsItem);
        
        // add items to Reports menu
        reportsMenu.add(customersWithCredit);
        
        
        // add items to Help Menu
		helpMenu.add(helpContentItem);
		helpMenu.add(registerItem);
		helpMenu.add(bugItem);
		helpMenu.add(feedbackItem);
		helpMenu.add(sendLogItem);
		helpMenu.addSeparator();
		helpMenu.add(aboutItem);
		
	}

	
	public static AppMenuItem getUndoAction(){
		return undoAction;
	}
	
	public static AppMenuItem getRedoAction(){
		return redoAction;
	}
	
	/**
	 * Updates the undo menu item. It needs to be public
	 * so that other classes can also update the menu item
	 */
	public static void updateUndoState() {
		if (UNDO_MANAGER.canUndo()) {
			undoAction.setEnabled(true);
		} else {
			undoAction.setEnabled(false);
		}
	}
	
	/**
	 * Updates the redo menu item. It needs to be public
	 * so that other classes can also update the menu item
	 */
	public static void updateRedoState() {
		if (UNDO_MANAGER.canRedo()) {
			redoAction.setEnabled(true);
		} else {
			redoAction.setEnabled(false);
		}
	}
	
	/**
	 * This method overrides the ActionListener interface actionPerformed
	 * method. It is invoked any time a component that has this class as 
	 * an actionListener registered fires an an action.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		if (actionCommand.equals("newCustomer")){
			DokaanMain.showNewCustomerTab();
		}
		else if (actionCommand.equals("openCustomers")){
			DokaanMain.showCustomersTab();
		}
		else if (actionCommand.equals("overview")){
			DokaanMain.showOverviewTab();
		}
		else if (actionCommand.equals("exit")){
			System.exit(0);
		}
		//Edit Menu Actions:
		else if (actionCommand.equals("undo")){
			try {
				UNDO_MANAGER.undo();
			} catch (CannotUndoException ex) {
				System.out.println(ex.getMessage());
			}
			updateUndoState();
			updateRedoState();
		}
		else if (actionCommand.equals("redo")){
			try {
				UNDO_MANAGER.redo();
			} catch (CannotUndoException ex) {
				System.out.println(ex.getMessage());
			}
			updateRedoState();
			updateUndoState();
		}
	}
	
}
