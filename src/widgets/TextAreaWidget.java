package widgets;

import java.awt.Component;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.geom.RoundRectangle2D;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.FocusManager;
import javax.swing.InputMap;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
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
public class TextAreaWidget extends JTextArea implements FocusListener, DocumentListener, UndoableEditListener{
	/**
	 * It has to do with serialization; it is not important.
	 * It is here to avoid compiler warning.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * This is the object to hold the focused action key for the shift key
	 */
	private static final Object tabFocusActionKeyHolder = new Object();

	/**
	 * This is the object to hold the focus action key for the shift+tab key
	 */
	private static final Object shiftTabFocusActionKeyHolder = new Object();
	
	/**
	 * This indicates that the tab key is pressed
	 */
	private static final KeyStroke PressedTab = KeyStroke.getKeyStroke(
			KeyEvent.VK_TAB, 0, false);
	
	/**
	 * This indicates the Shift+Tab key combination is pressed
	 */
	private static final KeyStroke PressedShiftTab = KeyStroke.getKeyStroke(
			KeyEvent.VK_TAB, KeyEvent.SHIFT_DOWN_MASK, false);
		
	private JLabel lblCounter;

	private Shape shape;
	
	private String oldValue;
	
	/**
	 * Since JTextArea does not support ActionListener, a TextWidget is
	 * added as a field to fake ActionListener support
	 * 
	 * The txtWdiget fires the actionPerformed event whenever the JTextArea
	 * loses focus and when the old value is different from the new value.
	 * 
	 * The ActionPerformed event is captured in the actionPerformed method of 
	 * a class that implements ActionListener interface and has added this 
	 * as a field and registered an actionListener object on it by calling
	 * the addActionListener object in this class.
	 */
	private TextWidget txtWidgetToFakeActionListenerSupport;

	public TextAreaWidget(String id) {
		super();
		oldValue = this.getText();
		customize(id);
	}

	public TextAreaWidget(String id, String text) {
		super(text);
		oldValue = this.getText();
		customize(id);
	}

	public TextAreaWidget(String id, int row, int column) {
		super(row, column);
		oldValue = this.getText();
		customize(id);
	}
	
	
	public void customize(String id) {
		txtWidgetToFakeActionListenerSupport = new TextWidget(id + "ActionListener");
		setOpaque(false);
		setName(id);
		removeTab();
		setLineWrap(true);
		setWrapStyleWord(true);
		setFont(Globals.FONT_APPLICATION);
		setForeground(Globals.GRAY_DARK);
		setBorder(BorderFactory.createEmptyBorder(3, 5, 3, 5));
		setDocument(new WidgetDocument(254));
		getDocument().addDocumentListener(this);
		addFocusListener(this);
		getDocument().addUndoableEditListener(this);
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
    
	/**
	 * Changes the Shift key and Shift+Tab key combination behavior
	 * within this TextAreaWidget (JTextArea) object by moving the
	 * focus to the next and previous component respectively.
	 */
	@SuppressWarnings("serial")
	private void removeTab() {
		ActionMap actionMap = this.getActionMap();
		
		//To capture Shift key and move the focus to next component
		actionMap.put(tabFocusActionKeyHolder, new AbstractAction("object1") {

			public void actionPerformed(ActionEvent e) {
				Object source = e.getSource();
				if (source instanceof Component) {
					FocusManager.getCurrentKeyboardFocusManager()
							.focusNextComponent((Component) source);
				}
			}
		});
		getInputMap().put(PressedTab, tabFocusActionKeyHolder);
		
		//To capture the Shift+Tab and move the focus to previous component 
		actionMap.put(shiftTabFocusActionKeyHolder, new AbstractAction("object2") {

			public void actionPerformed(ActionEvent e) {
				Object source = e.getSource();
				if (source instanceof Component) {
					FocusManager.getCurrentKeyboardFocusManager()
							.focusPreviousComponent((Component)source);
				}
			}
		});
		getInputMap().put(PressedShiftTab, shiftTabFocusActionKeyHolder);
	}
		
	/**
	 * Registers an ActionListener on the txtWidgetToFakeActionListenerSupport
	 * object so that it can fire actionPerformed events
	 * 
	 * @param l an action listener
	 */
	public void addActionListener(ActionListener l){
		txtWidgetToFakeActionListenerSupport.addActionListener(l);
	}
	
	/**
	 * Sets the text of the counter label. The counter label
	 * is used to show users how many more characters they can
	 * type in this JTextArea object.
	 * 
	 * @param lblCounter a LabelWidget to be used as a label counter 
	 */
	public void setCounterLabel(JLabel lblCounter){
		this.lblCounter = lblCounter;
	}

	/**
	 * Overrides the JTextArea's paintComponent method to 
	 * draw round-corners JTextArea instead of right-angle
	 * corners
	 */
    @Override
    protected void paintComponent(Graphics g) {
    	Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(getBackground());
		g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
		super.paintComponent(g2);
    }
    
    /*
    @Override
    protected void paintBorder(Graphics g) {
    	Graphics2D g2 = (Graphics2D)g;
    	g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
    			RenderingHints.VALUE_ANTIALIAS_ON);
    	g2.setColor(Globals.GRAY);
        g2.drawRoundRect(0,0, getWidth()-1, getHeight()-1, 15, 15);
    }
    */
    
    @Override
    public boolean contains(int x, int y) {
         if (shape == null || !shape.getBounds().equals(getBounds())) {
             shape = new RoundRectangle2D.Float(0, 0, getWidth()-1, getHeight()-1, 15, 15);
         }
         return shape.contains(x, y);
    }


	@Override
	public void focusGained(FocusEvent evt) {
		setBackground(Globals.GREEN_VERY_LIGHT);
	}

	@Override
	public void focusLost(FocusEvent ev) {
		setBackground(Globals.WHITE);
		if (!oldValue.equals(this.getText())) {
			txtWidgetToFakeActionListenerSupport.setText("txtArea");
			txtWidgetToFakeActionListenerSupport.fireActionPerformed();
		}
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
        	lblCounter.setText(remainingChars + " characters remaining");
        }
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
