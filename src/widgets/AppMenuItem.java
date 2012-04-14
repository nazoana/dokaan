package widgets;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JMenuItem;

import utilities.Globals;

/**
 * 
 * @author Mahmood Khan
 * @version 2012-03-25 1.0
 *
 */
public class AppMenuItem extends JMenuItem{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a JMenuItem with no set text or icon. 
	 */
	public AppMenuItem(){
		super();
		configure();
	}
	
	/**
	 * Creates a menu item whose properties are taken from the specified Action. 
	 * 
	 * @param a - the action of the JMenuItem
	 */
	public AppMenuItem(Action a){
		super(a);
		configure();
	}
	
	/**
	 * Creates a JMenuItem with the specified icon. 
	 * 
	 * @param icon - the icon of the JMenuItem
	 */
	public AppMenuItem(Icon icon){
		super(icon);
		configure();
	}
	

	/**
	 * Creates a JMenuItem with the specified text. 
	 * 
	 * @param s - the text of the JMenuItem
	 */
	public AppMenuItem(String s){
		super(s);
		configure();
	}
	
	/**
	 * Creates a JMenuItem with the specified text and icon. 
	 * 
	 * @param s - the text of the JMenuItem
	 * @param icon - the icon of the JMenuItem
	 */
	public AppMenuItem(String s, Icon icon){
		super(s, icon);
		configure();
	}
	
	/**
	 * Creates a JMenuItem with the specified text and keyboard mnemonic. 
	 * 
	 * @param s  - the text of the JMenuItem
	 * @param mnemonic  - the keyboard mnemonic for the JMenuItem
	 */
	public AppMenuItem(String s,  int mnemonic){
		super(s, mnemonic);
		configure();
	}
	
	private void configure(){
		//setBackground(Globals.WHITE);
		setFont(Globals.FONT_APPLICATION);
	}
}
