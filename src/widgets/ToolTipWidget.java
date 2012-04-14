package widgets;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JToolTip;

import utilities.Globals;


/**
 * 
 * @author Mahmood Khan
 * @version 2012-04-01 1.0
 *
 */
public class ToolTipWidget extends JToolTip{
	
	/**
	 * It has to do with serialization; it is not needed but
	 * it is only here to avoid a compilation warning.
	 */
	private static final long serialVersionUID = 1L;
	
	private LabelWidget label;
	
	private PanelWidget panel;
	
	private Shape shape;
	
	public ToolTipWidget() {
		super();
		setBackground(Globals.WHITE);
		setOpaque(false);
		
		label = new LabelWidget("tooltip");
		label.setFont(Globals.FONT_APPLICATION);
		
		panel = new PanelWidget("tooltip", new BorderLayout());
		panel.add(BorderLayout.CENTER, label);
		
		setLayout(new BorderLayout());
		add(panel);
	}
	
	
	@Override
	public Dimension getPreferredSize() {
		return panel.getPreferredSize();
	}

	@Override
	public void setTipText(String tipText) {
		if (tipText != null && !tipText.isEmpty()) {
			StringBuffer tipTextHtml = new StringBuffer();
			tipTextHtml.append("<html>");
			int start = 0;
			int end = 0;
			int total = tipText.length();
			int charsPerLine = 40;
			while (total / charsPerLine  > 1) {
				start = end ;
				end = end + charsPerLine;
				total = total - charsPerLine;
				tipTextHtml.append(tipText.substring(start, end));
				tipTextHtml.append("<br/>");
			}
			if (total <= charsPerLine) {
				tipTextHtml.append(tipText);
			}
			tipTextHtml.append("</html>");
			label.setText(tipTextHtml.toString());
		} else {
			super.setTipText(tipText);
		}
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

	
	@Override
	protected void paintBorder(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(Globals.GRAY_VERY_LIGHT);
		g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
	}
	
	
	@Override
	public boolean contains(int x, int y) {
		if (shape == null || !shape.getBounds().equals(getBounds())) {
			shape = new RoundRectangle2D.Float(0, 0, getWidth() - 1,
					getHeight() - 1, 15, 15);
		}
		return shape.contains(x, y);
	}
}
