package widgets;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.FocusManager;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

/**
 * 
 * @author Mahmood Khan
 * @version 2012-03-06 1.0
 *
 */
public class TextAreaWidget extends JTextArea {
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

	JTextArea jt = new JTextArea();

	public TextAreaWidget() {
		super();
		removeTab();
		setLineWrap(true);
	}

	public TextAreaWidget(String text) {
		super(text);
		removeTab();
		setLineWrap(true);
	}

	public TextAreaWidget(int row, int column) {
		super(row, column);
		removeTab();
		setLineWrap(true);
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
}
