package widgets;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JScrollPane;
import javax.swing.JViewport;

import utilities.Globals;

/**
 * 
 * @author Mahmood Khan
 * @version 2012-03-08 1.0
 *
 */
public class ScrollPaneWidget extends JScrollPane{

	/**
	 * It has to do with serialization; it is not important.
	 * It is only here to avoid compiler warning
	 */
	private static final long serialVersionUID = 1L;
	
	private Shape shape;
	
	private final Color alphaZero = new Color(0, true);

	public ScrollPaneWidget(String id){
		super();
		setName(id);
	}
	
	public ScrollPaneWidget(String id, Component view){
		super(view);
		setName(id);
	}
	
	public ScrollPaneWidget(String id, int vsbPolicy, int hsbPolicy){
		super(vsbPolicy, hsbPolicy);
		setName(id);
	}
	
	public ScrollPaneWidget(String id, Component view, int vsbPolicy, int hsbPolicy){
		super(view, vsbPolicy, hsbPolicy);
		setName(id);
	}
	
	public void setName(String id){
		super.setName(id);
		setOpaque(false);
        setBackground(alphaZero);
        getViewport().setOpaque(false);
        //getViewport().setBackground(alphaZero);
        setColumnHeader(new JViewport());
        getColumnHeader().setOpaque(false);
        getColumnHeader().setBackground(alphaZero);

        //setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
	}
	
    
    @Override
    protected void paintComponent(Graphics g) {
    	Graphics2D g2 = (Graphics2D)g;
    	g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
    			RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Globals.WHITE_FOR_FG_HEADING_LABEL);
        g2.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 35, 35);
        super.paintComponent(g2);
    }
    
    @Override
    protected void paintBorder(Graphics g) {
    	Graphics2D g2 = (Graphics2D)g;
    	g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
    			RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Globals.GRAY);
        //g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 35, 35);
    }
    
    @Override
    public boolean contains(int x, int y) {
        if (shape == null || !shape.getBounds().equals(getBounds())) {
            shape = new RoundRectangle2D.Float(0, 0, getWidth()-1, getHeight()-1, 15, 15);
        }
        return shape.contains(x, y);
    }
}
