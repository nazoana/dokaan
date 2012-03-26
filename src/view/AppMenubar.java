package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenuBar;
import javax.swing.JRadioButtonMenuItem;

import utilities.Globals;
import widgets.AppMenu;
import widgets.AppMenuItem;

/**
 * 
 * @author Mahmood Khan
 * @version 2012-03-25 1.0
 *
 */
public class AppMenubar extends JMenuBar {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AppMenubar() {
		super();
		setupMenu();
	}
	
	
	private void setupMenu(){
		//setOpaque(true);
		//setBackground(Globals.GREEN);
		setFont(Globals.FONT_APPLICATION);
		
		
		AppMenu fileMenu = new AppMenu("File");
		AppMenu editMenu = new AppMenu("Edit");
		AppMenu toolsMenu = new AppMenu("Tools");
		AppMenu helpMenu = new AppMenu("Help");
        add(fileMenu);
        add(editMenu);
        add(toolsMenu);
        add(helpMenu);
        
        // Create and add simple menu item to one of the drop down menu
        AppMenuItem newAction = new AppMenuItem("New");
        AppMenuItem openAction = new AppMenuItem("Open");
        AppMenuItem exitAction = new AppMenuItem("Exit");
        AppMenuItem cutAction = new AppMenuItem("Cut");
        AppMenuItem copyAction = new AppMenuItem("Copy");
        AppMenuItem pasteAction = new AppMenuItem("Paste");
        
        // Create and add CheckButton as a menu item to one of the drop down
        // menu
        JCheckBoxMenuItem checkAction = new JCheckBoxMenuItem("Check Action");
        // Create and add Radio Buttons as simple menu items to one of the drop
        // down menu
        JRadioButtonMenuItem radioAction1 = new JRadioButtonMenuItem(
                "Radio Button1");
        JRadioButtonMenuItem radioAction2 = new JRadioButtonMenuItem(
                "Radio Button2");
        // Create a ButtonGroup and add both radio Button to it. Only one radio
        // button in a ButtonGroup can be selected at a time.
        ButtonGroup bg = new ButtonGroup();
        bg.add(radioAction1);
        bg.add(radioAction2);
        fileMenu.add(newAction);
        fileMenu.add(openAction);
        fileMenu.add(checkAction);
        fileMenu.addSeparator();
        fileMenu.add(exitAction);
        editMenu.add(cutAction);
        editMenu.add(copyAction);
        editMenu.add(pasteAction);
        editMenu.addSeparator();
        editMenu.add(radioAction1);
        editMenu.add(radioAction2);
        // Add a listener to the New menu item. actionPerformed() method will
        // invoked, if user triggred this menu item
        newAction.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                System.out.println("You have clicked on the new action");
            }
        });
		
		

	}
	
}
