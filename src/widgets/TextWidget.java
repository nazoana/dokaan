package widgets;

import java.awt.Event;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.geom.RoundRectangle2D;

import javax.swing.BorderFactory;
import javax.swing.InputMap;
import javax.swing.JTextField;
import javax.swing.JToolTip;
import javax.swing.KeyStroke;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.Document;

import ui.AppMenubar;
import utilities.Globals;

/**
 * 
 * @author Mahmood Khan
 * @version 2012-03-06 1.0
 * 
 */
public class TextWidget extends JTextField implements FocusListener, UndoableEditListener {

	/**
	 * It has to do with serialization; it is not important. it is here to avoid
	 * a compiler warning.
	 */
	private static final long serialVersionUID = 1L;

	private String oldValue;

	private Shape shape;
	
	private ToolTipWidget tooltip;
	


	/**
	 * Constructor
	 * 
	 * @param id - the id for this component
	 */
	public TextWidget(String id) {
		super();
		configure(id);
	}

	/**
	 * Constructor
	 * 
	 * @param id - the id for this component
	 * @param columns - the number of columns in the text component
	 */
	public TextWidget(String id, int columns) {
		super(columns);
		configure(id);
	}

	/**
	 * Constructor
	 * 
	 * @param id - the id for this component
	 * @param name - the text that is going to show up in the component
	 */
	public TextWidget(String id, String text) {
		super(text);
		configure(id);
	}

	/**
	 * Constructor
	 * 
	 * @param id - the id for this component
	 * @param name - the text that is going to show up in the component
	 * @param columns - the number of columns in the text component
	 */
	public TextWidget(String id, String text, int columns) {
		super(text, columns);
		configure(id);
	}

	/**
	 * Constructor
	 * 
	 * @param id - the id for this component
	 * @param doc - the document that the text component should use
	 * @param name - the text that is going to show up in the component
	 * @param columns - the number of columns in the text component
	 */
	public TextWidget(String id, Document doc, String text, int columns) {
		super(doc, text, columns);
		configure(id);
	}


	public String getOldValue() {
		return oldValue;
	}

	@Override
	public JToolTip createToolTip() {
		if (tooltip == null) {
			tooltip = new ToolTipWidget();
			//tooltip.setComponent(this);
		}
		return tooltip;
	}
	
	/**
	 * customized the text component
	 * 
	 * @param id - the id of this text component
	 */
	public void configure(String id) {
		setName(id);
		oldValue = this.getText();
		addFocusListener(this);
		getDocument().addUndoableEditListener(this);
		setOpaque(false);
		setFont(Globals.FONT_APPLICATION);
		setForeground(Globals.GRAY_DARK);
		setBorder(BorderFactory.createEmptyBorder(3, 5, 3, 5));
		addBindings();
	}
	
    /**
     * Add a couple of emacs key bindings for navigation.
     */
    protected void addBindings() {
        InputMap inputMap = getInputMap();
 
        //Ctrl-b to go backward one character
        KeyStroke key = KeyStroke.getKeyStroke(KeyEvent.VK_B, Event.CTRL_MASK);
        inputMap.put(key, DefaultEditorKit.backwardAction);
 
        //Ctrl-f to go forward one character
        key = KeyStroke.getKeyStroke(KeyEvent.VK_F, Event.CTRL_MASK);
        inputMap.put(key, DefaultEditorKit.forwardAction);
 
        //Ctrl-p to go up one line
        key = KeyStroke.getKeyStroke(KeyEvent.VK_P, Event.CTRL_MASK);
        inputMap.put(key, DefaultEditorKit.upAction);
 
        //Ctrl-n to go down one line
        key = KeyStroke.getKeyStroke(KeyEvent.VK_N, Event.CTRL_MASK);
        inputMap.put(key, DefaultEditorKit.downAction);
        
        //Ctrl-e to go to the end of the line
        key = KeyStroke.getKeyStroke(KeyEvent.VK_E, Event.CTRL_MASK);
        inputMap.put(key, DefaultEditorKit.endLineAction);
        
        //Ctrl-d deletes the next character
        key = KeyStroke.getKeyStroke(KeyEvent.VK_D, Event.CTRL_MASK);
        inputMap.put(key, DefaultEditorKit.deleteNextCharAction);
        
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

	/**
	 * This is overriding the method defined in the UndoableEditListener interface.
	 * 
	 * This text widget has registered this class its undoableEditListener
	 * so any time and edit happens the listener is notified.
	 */
	@Override
	public void undoableEditHappened(UndoableEditEvent e) {
		// Remember the edit and update the menus.
		AppMenubar.UNDO_MANAGER.addEdit(e.getEdit());
		AppMenubar.updateUndoState();
		AppMenubar.updateRedoState();
	}
}
