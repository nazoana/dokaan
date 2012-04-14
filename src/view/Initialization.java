package view;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.MenuSelectionManager;
import javax.swing.UIManager;

import ui.AppMenubar;
import ui.ContextMenuForTextComps;
import utilities.AppLogger;
import utilities.Globals;

/**
 * This class gets the initialization stuff going for this application
 *  
 * @author Mahmood Khan
 * @version 2012-04-13 1.0
 *
 */
public class Initialization {

	/** The logger object used to log messages */
	private static final Logger LOGGER = AppLogger.getAppLogger(Initialization.class.getName());
	
	/** Used to de-select all menus */
	private static MenuSelectionManager menuSelectionMgr;

	/**
	 * The main method that is called to initialize the application
	 */
	public static void Initialize(){
		
		SystemTrayIconSetup.setupTrayIcon();
		
		Globals.MAIN_FRMAE.setJMenuBar(new AppMenubar());
		
		/*
		 * To enable the context menu (with cut, copy, and paste) options
		 * in text components only such as JTextField, JTextArea, etc.
		 */
		Globals.TOOLKIT.getSystemEventQueue().push(new ContextMenuForTextComps());
		
		/*
		 * Set the default look & feel to be that of the system
		 */
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			LOGGER.log(Level.WARNING,"Failed to use system look & feel:" + e.getMessage());
		}
		
		// It is used to close all menus once the mouse moves away from it
		menuSelectionMgr = javax.swing.MenuSelectionManager.defaultManager();
		
		/*
		 * MouseMotionListener is added to the main frame so that when the mouse
		 * moves away from the menus, they should close
		 */
		Globals.MAIN_FRMAE.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseMoved(MouseEvent e) {
				if (e.getY() > 10) {
					menuSelectionMgr.clearSelectedPath();
				}
			}
			@Override
			public void mouseDragged(MouseEvent e) {
				// not implemented.
			}
		});
		
	}
}
