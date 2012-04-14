package widgets;

import java.awt.BorderLayout;
import java.awt.GraphicsConfiguration;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import utilities.Globals;
import utilities.Util;

/**
 * Customizing the JFrame for this application
 * 
 * @author Mahmood Khan
 * @version 2012-04-13 1.0
 *
 */
public class AppFrame extends JFrame{

	/**
	 * This has to do with serialization; but it is here to avoid
	 * a compiler warning.
	 */
	private static final long serialVersionUID = 1L;

	public AppFrame(String id){
		super();
		configure(id);
	}
	
	public AppFrame(String id, GraphicsConfiguration gc){
		super(gc);
		configure(id);
	}
	
	public AppFrame(String id, String title){
		super(title);
		configure(id);
	}
	
	public AppFrame(String id, String title, GraphicsConfiguration gc){
		super();
		configure(id);
	}
	
	/**
	 * Customizes the frame
	 * 
	 * @param id - the name for this frame
	 */
	private void configure(String id){
		setName(id);
		getContentPane().setBackground(Globals.WHITE);
		getContentPane().setLayout(new BorderLayout());
		setIconImage(Util.getImage("../resources/logo.png"));
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);// WindowConstants.EXIT_ON_CLOSE
	}
	
	/**
	 * Makes the frame to be aligned in the center
	 * But this method has to be called after all content has been
	 * added to the frame's contentPane because the frame size varies
	 * depending on the size of content it holds.
	 */
	public void alignCenter(){
		setLocation(
				(Globals.SCREEN_SIZE.width / 2) - (Globals.MAIN_FRMAE.getWidth() / 2), 
				(Globals.SCREEN_SIZE.height / 2) - (Globals.MAIN_FRMAE.getHeight() / 2)
				);
	}
}
