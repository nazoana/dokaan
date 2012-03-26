package widgets;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.geom.RoundRectangle2D;

import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.JToolTip;
import javax.swing.text.Document;

import utilities.Globals;

/**
 * 
 * @author Mahmood Khan
 * @version 2012-03-06 1.0
 * 
 */
public class TextWidget extends JTextField implements FocusListener {

	/**
	 * It has to do with serialization; it is not important. it is here to avoid
	 * a compiler warning.
	 */
	private static final long serialVersionUID = 1L;

	private String oldValue;

	private Shape shape;
	
	private ToolTipWidget tooltip;

	public TextWidget(String id) {
		super();
		setName(id);
		oldValue = this.getText();
		addFocusListener();
		setOpaque(false);
		setFontAndColor();
	}

	public TextWidget(String id, int columns) {
		super(columns);
		setName(id);
		oldValue = this.getText();
		addFocusListener();
		setOpaque(false);
		setFontAndColor();
	}

	public TextWidget(String id, String text) {
		super(text);
		setName(id);
		oldValue = this.getText();
		addFocusListener();
		setOpaque(false);
		setFontAndColor();
	}

	public TextWidget(String id, String text, int columns) {
		super(text, columns);
		setName(id);
		oldValue = this.getText();
		addFocusListener();
		setOpaque(false);
		setFontAndColor();
	}

	public TextWidget(String id, Document doc, String text, int columns) {
		super(doc, text, columns);
		setName(id);
		oldValue = this.getText();
		addFocusListener();
		setOpaque(false);
		setFontAndColor();
	}

	private void addFocusListener() {
		addFocusListener(this);
	}

	public String getOldValue() {
		return oldValue;
	}

	public void setFontAndColor() {
		setFont(Globals.FONT_APPLICATION);
		setForeground(Globals.GRAY_DARK);
		setBorder(BorderFactory.createEmptyBorder(3, 5, 3, 5));
	}

	@Override
	public JToolTip createToolTip() {
		if (tooltip == null) {
			tooltip = new ToolTipWidget();
			//tooltip.setComponent(this);
		}
		return tooltip;
	}
	
	@Override
	public void setName(String id) {
		super.setName(id);
	}

	@Override
	public void setText(String text) {
		oldValue = this.getText();
		super.setText(text);
	}
	
	@Override
	protected void fireActionPerformed() {
		super.fireActionPerformed();
	}

	@Override
	public void focusGained(FocusEvent evt) {
		setBackground(Globals.GREEN_VERY_LIGHT);
	}

	/**
	 * Overrides and implements the focusLost method from FocusListener interface
	 * 
	 * The fireActinPerformed method invokes the actionPerformed method of the class
	 * that has added this TextBox and has implemented the ActionListener interface.
	 * For example, the CustomerView class has implemented the ActionListener interface
	 * and has added this widget as a GUI element so whenever the fireActionPerformed
	 * method in here is called, it triggers the actionPerformed method in the 
	 * Customer.class.
	 */
	@Override
	public void focusLost(FocusEvent evt) {
		setBackground(Globals.WHITE);
		if (!oldValue.equals(this.getText())) {
			fireActionPerformed();
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(getBackground());
		g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
		super.paintComponent(g2);
	}

	@Override
	protected void paintBorder(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(Globals.GRAY);
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
