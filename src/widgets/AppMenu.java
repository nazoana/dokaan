package widgets;

import javax.swing.Action;
import javax.swing.JMenu;

import utilities.Globals;

/**
 * 
 * @author Mahmood Khan
 * @version 2012-03-25 1.0
 *
 */
public class AppMenu extends JMenu{

	/**
	 * It has to do with serialization; it is here to avoid
	 * compilation warning
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new JMenu with no text. 
	 */
	public AppMenu(){
		super();
		configure();
	}
	
	/**
	 * Constructs a menu whose properties are taken from the Action supplied. 
	 * 
	 * @param a  - an Action
	 */
	public AppMenu(Action a){
		super(a);
		configure();
	}
	
	/**
	 * Constructs a new JMenu with the supplied string as its text.  
	 * 
	 * @param s - the text for the menu label
	 */
	public AppMenu(String s){
		super(s);
		configure();
	}
	
	/**
	 * Constructs a new JMenu with the supplied string as its text 
	 * and specified as a tear-off menu or not. 
	 * 
	 * @param s - the text for the menu label
	 * @param b - can the menu be torn off (not yet implemented)
	 */
	public AppMenu(String s, boolean b){
		super(s, b);
		configure();
	}
	
	private void configure(){
		setBackground(Globals.WHITE);
		setFont(Globals.FONT_APPLICATION);
	}
	
}
