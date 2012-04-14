package widgets;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;

import utilities.Globals;

/**
 * 
 * @author Mahmood Khan
 * @version 2012-03-08 1.0
 */
public class ButtonWidget extends JButton{

	/**
	 * This has to do with serialization; it is not important, but is
	 * placed here to prevent a compiler warning.
	 */
	private static final long serialVersionUID = 1L;
	
	private Shape shape;

	/**
	 * Constructor
	 * 
	 * @param id
	 */
	public ButtonWidget(String id){
		super();
		configure();
	}

	/**
	 * Constructor
	 * 
	 * @param id
	 * @param action
	 */
	public ButtonWidget(String id, Action action){
		super(action);
		configure();
	}
	
	/**
	 * Constructor
	 * 
	 * @param id
	 * @param icon
	 */
	public ButtonWidget(String id, Icon icon){
		super(icon);
		configure();
	}
	
	/**
	 * Constructor
	 * 
	 * @param id
	 * @param text
	 */
	public ButtonWidget(String id, String text){
		super(text);
		configure();
	}
	
	/**
	 * Constructor
	 * 
	 * @param id
	 * @param text
	 * @param icon
	 */
	public ButtonWidget(String id, String text, Icon icon){
		super(text, icon);
		configure();
	}
	
	private void configure(){
		setOpaque(false);
		setFont(Globals.FONT_APPLICATION);
		setForeground(Globals.GRAY_DARK);
		setBorder(BorderFactory.createEmptyBorder(4, 5, 4, 5));
		//setBorderPainted(false);
		//setContentAreaFilled(false);
		//setRolloverEnabled(true);
		//setFocusable(false);
	}
	
    @Override
    protected void paintComponent(Graphics g) {
    	Graphics2D g2 = (Graphics2D)g;
    	g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
    			RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Globals.GRAY_LIGHT);
        g2.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);
        super.paintComponent(g2);
    }
    
    @Override
    protected void paintBorder(Graphics g) {
    	//Graphics2D g2 = (Graphics2D)g;
    	//g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
    	//		RenderingHints.VALUE_ANTIALIAS_ON);
        //g2.setColor(Globals.GRAY_VERY_LIGHT);
        //g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);
    }
    
    @Override
    public boolean contains(int x, int y) {
        if (shape == null || !shape.getBounds().equals(getBounds())) {
            shape = new RoundRectangle2D.Float(0, 0, getWidth()-1, getHeight()-1, 15, 15);
        }
        return shape.contains(x, y);
    }
}
