package widgets;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.geom.RoundRectangle2D;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.FocusManager;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

import utilities.Globals;

/**
 * 
 * @author Mahmood Khan
 * @version 2012-03-06 1.0
 *
 */
public class TextAreaWidget extends JTextArea implements FocusListener, DocumentListener{
	/**
	 * It has to do with serialization; it is not important.
	 * It is here to avoid compiler warning.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * This is the object to hold the focused action key
	 */
	private static final Object MyFocusActionKey = new Object();

	/**
	 * This indicates that the tab key is pressed
	 */
	private static final KeyStroke PressedTab = KeyStroke.getKeyStroke(
			KeyEvent.VK_TAB, 0, false);
	
	private JLabel lblCounter;

	private Shape shape;
	
	private String oldValue;

	public TextAreaWidget(String id) {
		super();
		oldValue = this.getText();
		setName(id);
		removeTab();
		setLineWrap(true);
		setFontAndColor();
		setDocument(new WidgetDocument(254));
		getDocument().addDocumentListener(this);
	}

	public TextAreaWidget(String id, String text) {
		super(text);
		oldValue = this.getText();
		setName(id);
		removeTab();
		setLineWrap(true);
		setFontAndColor();
		setDocument(new WidgetDocument(254));
		getDocument().addDocumentListener(this);
	}

	public TextAreaWidget(String id, int row, int column) {
		super(row, column);
		oldValue = this.getText();
		setName(id);
		removeTab();
		setLineWrap(true);
		setFontAndColor();
		setDocument(new WidgetDocument(254));
		getDocument().addDocumentListener(this);
	}
	
	@Override
	public void setName(String id) {
		super.setName(id);
	}

	@SuppressWarnings("serial")
	private void removeTab() {
		ActionMap grantsNotesActionMap = this.getActionMap();
		grantsNotesActionMap.put(MyFocusActionKey, new AbstractAction(
				"MyFocusActionKey") {

			public void actionPerformed(ActionEvent e) {
				Object source = e.getSource();
				if (source instanceof Component) {
					FocusManager.getCurrentKeyboardFocusManager()
							.focusNextComponent((Component) source);
				}
			}
		});
		this.getInputMap().put(PressedTab, MyFocusActionKey);
	}
	
	private void setFontAndColor(){
		setFont(Globals.FONT_APPLICATION);
		setForeground(Globals.GRAY_DARK);
	}
	
    @Override
    protected void paintComponent(Graphics g) {
    	Graphics2D g2 = (Graphics2D)g;
    	g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
    			RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Globals.YELLOW_LIGHT_FOR_BG_LABEL_WIDGET);
        g2.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 10, 10);
        super.paintComponent(g2);
    }
    
    @Override
    protected void paintBorder(Graphics g) {
    	Graphics2D g2 = (Graphics2D)g;
    	g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
    			RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Globals.GRAY); 
        g2.drawRoundRect(0,0, getWidth()-1, getHeight()-1, 10, 10);
    }
    
    @Override
    public boolean contains(int x, int y) {
         if (shape == null || !shape.getBounds().equals(getBounds())) {
             shape = new RoundRectangle2D.Float(0, 0, getWidth()-1, getHeight()-1, 10, 10);
         }
         return shape.contains(x, y);
    }

	@Override
	public void focusGained(FocusEvent evt) {
		setBackground(Globals.GREEN_VERY_LIGHT);
	}

	@Override
	public void focusLost(FocusEvent ev) {
		setBackground(Globals.WHITE_FOR_FG_HEADING_LABEL);
		if (!oldValue.equals(this.getText())) {
			//
		}
	}
	
	public void setCounterLabel(JLabel lblCounter){
		this.lblCounter = lblCounter;
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		updateLog(e);
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		updateLog(e);
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		updateLog(e);
	}
	
	private void updateLog(DocumentEvent e) {
        if (lblCounter != null) {
        	Document doc = (Document)e.getDocument();
        	int remainingChars = 254 - doc.getLength();
        	lblCounter.setText( remainingChars + " characters remaining");
        }
    }

}
