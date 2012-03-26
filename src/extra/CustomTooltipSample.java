package extra;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolTip;

public class CustomTooltipSample extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CustomLabel m_label;

	public CustomTooltipSample() {
		setTitle("Custom tooltip sample");
		setSize(300, 200);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		m_label = new CustomLabel("My Label");
		m_label.setToolTipText("Yo, I am a tooltip with components!"); // activate
																		// tooltips
																		// for
																		// this
																		// component
		add(m_label);
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				JFrame frame = new CustomTooltipSample();
				frame.setVisible(true);
			}
		});
	}

	private static class CustomLabel extends JLabel {
		private CustomTooltip m_tooltip;

		public CustomLabel(String text) {
			super(text);
		}

		@Override
		public JToolTip createToolTip() {
			if (m_tooltip == null) {
				m_tooltip = new CustomTooltip();
				m_tooltip.setComponent(this);
			}
			return m_tooltip;
		}
	}

	private static class CustomTooltip extends JToolTip {
		private JLabel m_label;
		private JButton m_button;
		private JPanel m_panel;

		public CustomTooltip() {
			super();
			m_label = new JLabel();
			m_button = new JButton("See, I am a button!");
			m_panel = new JPanel(new BorderLayout());
			m_panel.add(BorderLayout.CENTER, m_label);
			m_panel.add(BorderLayout.SOUTH, m_button);
			setLayout(new BorderLayout());
			add(m_panel);
		}

		@Override
		public Dimension getPreferredSize() {
			return m_panel.getPreferredSize();
		}

		@Override
		public void setTipText(String tipText) {
			if (tipText != null && !tipText.isEmpty()) {
				m_label.setText(tipText);
			} else {
				super.setTipText(tipText);
			}
		}
	}
}