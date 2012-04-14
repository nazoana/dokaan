package view;

import java.awt.AWTException;
import java.awt.CheckboxMenuItem;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import utilities.AppLogger;
import utilities.Globals;
import utilities.Util;

/**
 * Sets up a system tray icon
 * 
 * @author Mahmood Khan
 * @version 2012-04-13 1.0
 *
 */
public final class SystemTrayIconSetup {

	/** The logger object used to log messages */
	private static final Logger logger = AppLogger.getAppLogger(SystemTrayIconSetup.class.getName());
	
	public static void setupTrayIcon() {
		
		// Check the SystemTray support
		if (!SystemTray.isSupported()) {
			logger.log(Level.WARNING, "System tray icon is not supported!");
			return;
		}
		final PopupMenu popup = new PopupMenu();
		final TrayIcon trayIcon = new TrayIcon(
				Util.getImage("../resources/logoSmall.png")); 
		
		final SystemTray tray = SystemTray.getSystemTray();

		// Create a popup menu components
		MenuItem aboutItem = new MenuItem("About");
		MenuItem application = new MenuItem("application");

		CheckboxMenuItem cb1 = new CheckboxMenuItem("Set auto size");
		CheckboxMenuItem cb2 = new CheckboxMenuItem("Set tooltip");
		Menu displayMenu = new Menu("Display");
		MenuItem errorItem = new MenuItem("Error");
		MenuItem warningItem = new MenuItem("Warning");
		MenuItem infoItem = new MenuItem("Info");
		MenuItem noneItem = new MenuItem("None");
		MenuItem exitItem = new MenuItem("Exit");

		// Add components to popup menu
		popup.add(aboutItem);
		popup.add(application);
		popup.addSeparator();
		popup.add(cb1);
		popup.add(cb2);
		popup.addSeparator();
		popup.add(displayMenu);
		displayMenu.add(errorItem);
		displayMenu.add(warningItem);
		displayMenu.add(infoItem);
		displayMenu.add(noneItem);
		popup.add(exitItem);

		trayIcon.setPopupMenu(popup);

		try {
			tray.add(trayIcon);
		} catch (AWTException e) {
			System.out.println("TrayIcon could not be added.");
			return;
		}

		trayIcon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Util.showInfo(null, "This dialog box is run from System Tray",
						"Tray");
			}
		});

		aboutItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Util.showInfo(null,
						"This dialog box is run from the About menu item",
						"Tray");
			}
		});

		application.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Globals.MAIN_FRMAE.setVisible(true);
			}
		});

		cb1.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				int cb1Id = e.getStateChange();
				if (cb1Id == ItemEvent.SELECTED) {
					trayIcon.setImageAutoSize(true);
				} else {
					trayIcon.setImageAutoSize(false);
				}
			}
		});

		cb2.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				int cb2Id = e.getStateChange();
				if (cb2Id == ItemEvent.SELECTED) {
					trayIcon.setToolTip("Sun TrayIcon");
				} else {
					trayIcon.setToolTip(null);
				}
			}
		});

		ActionListener listener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MenuItem item = (MenuItem) e.getSource();
				// TrayIcon.MessageType type = null;
				System.out.println(item.getLabel());
				if ("Error".equals(item.getLabel())) {
					// type = TrayIcon.MessageType.ERROR;
					trayIcon.displayMessage("Sun TrayIcon Demo",
							"This is an error message",
							TrayIcon.MessageType.ERROR);

				} else if ("Warning".equals(item.getLabel())) {
					// type = TrayIcon.MessageType.WARNING;
					trayIcon.displayMessage("Sun TrayIcon Demo",
							"This is a warning message",
							TrayIcon.MessageType.WARNING);

				} else if ("Info".equals(item.getLabel())) {
					// type = TrayIcon.MessageType.INFO;
					trayIcon.displayMessage("Sun TrayIcon Demo",
							"This is an info message",
							TrayIcon.MessageType.INFO);

				} else if ("None".equals(item.getLabel())) {
					// type = TrayIcon.MessageType.NONE;
					trayIcon.displayMessage("Sun TrayIcon Demo",
							"This is an ordinary message",
							TrayIcon.MessageType.NONE);
				}
			}
		};

		errorItem.addActionListener(listener);
		warningItem.addActionListener(listener);
		infoItem.addActionListener(listener);
		noneItem.addActionListener(listener);

		exitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tray.remove(trayIcon);
				System.exit(0);
			}
		});
	}
	
}
