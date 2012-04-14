package widgets;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import utilities.Globals;

/**
 * 
 * @author Mahmood Khan
 * @version 2012-04-01 1.0
 *
 */
public class PanelWidget extends JPanel{

	/**
	 * It has to do with serialization; it is here
	 * to avoid compilation warning
	 */
	private static final long serialVersionUID = 1L;
	
	private Shape shape;

	public PanelWidget(String id){
		super();
		configure(id);
	}
	
	public PanelWidget(String id, boolean isDoubleBuffered){
		super(isDoubleBuffered);
		configure(id);
	}
	
	public PanelWidget(String id, LayoutManager layout){
		super(layout);
		configure(id);
	}
	
	public PanelWidget(String id, LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		configure(id);
	}
	
	private void configure(String id){
		setName(id);
		setOpaque(true);
		setFont(Globals.FONT_APPLICATION);
		setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(Globals.WHITE);
		g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
		super.paintComponent(g2);
	}

	/*
	@Override
	protected void paintBorder(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(Globals.GREEN);
		g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
	}
	*/

	@Override
	public boolean contains(int x, int y) {
		if (shape == null || !shape.getBounds().equals(getBounds())) {
			shape = new RoundRectangle2D.Float(0, 0, getWidth() - 1,
					getHeight() - 1, 15, 15);
		}
		return shape.contains(x, y);
	}
	
}
