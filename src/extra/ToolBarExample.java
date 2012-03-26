package extra;

//ToolBarExample.java
//An example of JToolBar.  The actions used to build the toolbar are also
//placed in a JMenu to further demonstrate the flexibility of the Action
//class.  (See the examples in Chapter 3 for more details on Action.)
//
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

public class ToolBarExample extends JPanel {

	public JTextPane pane;
	public JMenuBar menuBar;
	public JToolBar toolBar;
	String fonts[] = { "Serif", "SansSerif", "Monospaced", "Dialog",
			"DialogInput" };

	public ToolBarExample() {
		menuBar = new JMenuBar();

		// Create a set of actions to use in both the menu and toolbar
		DemoAction leftJustifyAction = new DemoAction("Left", new ImageIcon(
				"left.gif"), "Left justify text", 'L');
		DemoAction rightJustifyAction = new DemoAction("Right", new ImageIcon(
				"right.gif"), "Right justify text", 'R');
		DemoAction centerJustifyAction = new DemoAction("Center",
				new ImageIcon("center.gif"), "Center justify text", 'M');
		DemoAction fullJustifyAction = new DemoAction("Full", new ImageIcon(
				"full.gif"), "Full justify text", 'F');

		JMenu formatMenu = new JMenu("Justify");
		formatMenu.add(leftJustifyAction);
		formatMenu.add(rightJustifyAction);
		formatMenu.add(centerJustifyAction);
		formatMenu.add(fullJustifyAction);

		menuBar.add(formatMenu);

		toolBar = new JToolBar("Formatting");
		toolBar.add(leftJustifyAction);
		toolBar.add(rightJustifyAction);
		toolBar.add(centerJustifyAction);
		toolBar.add(fullJustifyAction);

		toolBar.addSeparator();
		JLabel label = new JLabel("Font");
		toolBar.add(label);

		toolBar.addSeparator();
		JComboBox combo = new JComboBox(fonts);
		combo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					pane.getStyledDocument().insertString(
							0,
							"Font ["
									+ ((JComboBox) e.getSource())
											.getSelectedItem() + "] chosen!\n",
							null);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		toolBar.add(combo);

		// Disable one of the Actions
		fullJustifyAction.setEnabled(false);
	}

	public static void main(String s[]) {

		ToolBarExample example = new ToolBarExample();
		example.pane = new JTextPane();
		example.pane.setPreferredSize(new Dimension(250, 250));
		example.pane.setBorder(new BevelBorder(BevelBorder.LOWERED));
		example.toolBar.setMaximumSize(example.toolBar.getSize());

		JFrame frame = new JFrame("Menu Example");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setJMenuBar(example.menuBar);
		frame.getContentPane().add(example.toolBar, BorderLayout.NORTH);
		frame.getContentPane().add(example.pane, BorderLayout.CENTER);
		frame.pack();
		frame.setVisible(true);
	}

	class DemoAction extends AbstractAction {

		public DemoAction(String text, Icon icon, String description,
				char accelerator) {
			super(text, icon);
			putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(accelerator,
					Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
			putValue(SHORT_DESCRIPTION, description);
		}

		public void actionPerformed(ActionEvent e) {
			try {
				pane.getStyledDocument().insertString(0,
						"Action [" + getValue(NAME) + "] performed!\n", null);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
}
