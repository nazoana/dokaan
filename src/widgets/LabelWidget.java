package widgets;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JLabel;

import utilities.Globals;

/**
 * 
 * @author Mahmood Khan
 * @version 2012-03-08 1.0
 *
 */
public class LabelWidget extends JLabel{

	/**
	 * This has to do with serialization, it is not important here.
	 * Is is only here to avoid compilation warning
	 */
	private static final long serialVersionUID = 1L;
	
	private Shape shape;

	public LabelWidget(String id){
		super();
		setFontAndColor();
	}
	
	public LabelWidget(String id, String text){
		super(text);
		setFontAndColor();
	}
	
	public LabelWidget(String id, Icon icon){
		super(icon);
		setFontAndColor();
	}
	
	public LabelWidget(String id, Icon icon, int horizontalAlignment){
		super(icon, horizontalAlignment);
		setFontAndColor();
	}
	
	public LabelWidget(String id, String text, int horizontalAlignment){
		super(text, horizontalAlignment);
		setFontAndColor();
	}
	
	public LabelWidget(String id, String text, Icon icon, int horizontalAlignment){
		super(text, icon, horizontalAlignment);
		setFontAndColor();
	}
	
	private void setFontAndColor(){
		super.setFont(Globals.FONT_BOLD);
		super.setForeground(Globals.GRAY_DARK);
		setBorder(BorderFactory.createEmptyBorder(3,5,3,5));
	}
	
    @Override
    protected void paintComponent(Graphics g) {
    	Graphics2D g2 = (Graphics2D)g;
    	g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
    			RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Globals.YELLOW_LIGHT);
        g2.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 10, 10);
        super.paintComponent(g2);
    }
    
    @Override
    protected void paintBorder(Graphics g) {
    	Graphics2D g2 = (Graphics2D)g;
    	g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
    			RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Globals.GRAY_LIGHT); 
        g2.drawRoundRect(0,0, getWidth()-1, getHeight()-1, 10, 10);
    }
    
    @Override
    public boolean contains(int x, int y) {
         if (shape == null || !shape.getBounds().equals(getBounds())) {
             shape = new RoundRectangle2D.Float(0, 0, getWidth()-1, getHeight()-1, 10, 10);
         }
         return shape.contains(x, y);
    }
}
