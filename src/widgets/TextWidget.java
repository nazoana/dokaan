package widgets;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JTextField;
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
		super.setFont(Globals.FONT_APPLICATION);
		super.setForeground(Globals.GRAY_DARK);
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
	public void focusGained(FocusEvent evt) {
		//System.out.println("ID: " + evt.getID() + "\nname: "
				//+ evt.getClass().getName()
				// + "\nparamString: " + evt.paramString()
				// +"\nOriginator :" + evt.getComponent().getName()
				//+ "\nSource :" + ((TextWidget) evt.getSource()).getName());
		setBackground(Globals.GREEN_VERY_LIGHT);
	}

	@Override
	public void focusLost(FocusEvent evt) {
		//System.out.println("SOURCE: " + evt.getSource().getClass().getName());
		setBackground(Globals.WHITE_FOR_FG_HEADING_LABEL);
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
		g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
		super.paintComponent(g2);
	}

	@Override
	protected void paintBorder(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(Globals.GRAY);
		g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);
	}

	@Override
	public boolean contains(int x, int y) {
		if (shape == null || !shape.getBounds().equals(getBounds())) {
			shape = new RoundRectangle2D.Float(0, 0, getWidth() - 1,
					getHeight() - 1, 10, 10);
		}
		return shape.contains(x, y);
	}
}
