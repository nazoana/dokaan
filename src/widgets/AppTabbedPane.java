package widgets;

import java.awt.BasicStroke;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.plaf.basic.BasicButtonUI;

import utilities.Globals;


/**
 * 
 * @author Mahmood Khan
 * @version 2012-04-02 1.0
 *
 */
public class AppTabbedPane extends JTabbedPane{

    /**
     * It has to do with serialization; it is here to 
     * avoid a compiler warning.
     */
    private static final long serialVersionUID = 1L;
      
    
    public AppTabbedPane(String id){
        super();
        configure(id);
    }
    
    /**
     * Creates an empty TabbedPane with the specified tab placement of either: 
     * JTabbedPane.TOP, JTabbedPane.BOTTOM, JTabbedPane.LEFT, or JTabbedPane.RIGHT.
     * 
     * @param id
     * @param tabPlacement - the placement for the tabs relative to the content
     */
    public AppTabbedPane(String id, int tabPlacement){
        super(tabPlacement);
        configure(id);
    }
    
    /**
     * Creates an empty TabbedPane with the specified tab placement 
     * and tab layout policy. Tab placement may be either: 
     * JTabbedPane.TOP, JTabbedPane.BOTTOM, JTabbedPane.LEFT, or 
     * JTabbedPane.RIGHT. Tab layout policy may be either: 
     * JTabbedPane.WRAP_TAB_LAYOUT or JTabbedPane.SCROLL_TAB_LAYOUT. 
     *  
     * @param id
     * @param tabPlacement - the placement for the tabs relative to the content
     * @param tabLayoutPolicy - the policy for laying out tabs when all tabs will not fit on one run
     * 
     * Throws: 
     * IllegalArgumentException - if tab placement or tab layout 
     * policy are not one of the above supported values
     */
    public AppTabbedPane(String id, int tabPlacement, int tablLayoutPolicy){
        super(tabPlacement, tablLayoutPolicy);
        configure(id);
    }
    
    
    @Override
    public void updateUI() {
        setUI(new AquaBarTabbedPaneUI());
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }


    @Override
    public String getTitleAt(int index) {
        return super.getTitleAt(index).trim();
    }
    
    /**
     * Adds a tab to the tabbedPane
     * 
     * @param title
     * @param component
     * @param icon
     */
    public void addTab(String title, Component component, Icon icon) {
    	int tabCount = getTabCount();
        super.addTab(title + "  ", component);
        if (tabCount > 0) {
        	setTabComponentAt(tabCount, new ButtonTabComponent(this, icon));
        } else {
            setIconAt(tabCount, icon);
        }
    }
    
   
   /**
     * Customizes it to fit the applications better
     * @param id
     */
    private void configure(String id){
        setName(id);
        setBorder(BorderFactory.createEmptyBorder(1, 0, 0, 0));
        setOpaque(true);
        setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        setBackground(Globals.WHITE);
    }
    
    /**
     * Component to be used as tabComponent;
     * Contains a JLabel to show the text and
     * a JButton to close the tab it belongs to
     */
    @SuppressWarnings("serial")
    public class ButtonTabComponent extends JPanel {
    	
		private final JTabbedPane pane;
     
        public ButtonTabComponent(final JTabbedPane pane, Icon icon) {
            //unset default FlowLayout' gaps
            super(new FlowLayout(FlowLayout.RIGHT, 0, 0));
            if (pane == null) {
                throw new NullPointerException("TabbedPane is null");
            }
            this.pane = pane;
            setOpaque(false);
             
            //make JLabel read titles from JTabbedPane
			JLabel label = new JLabel() {
                public String getText() {
                    int i = pane.indexOfTabComponent(ButtonTabComponent.this);
                    if (i != -1) {
                        return pane.getTitleAt(i);
                    }
                    return null;
                }
            };
            label.setIcon(icon);
            add(label);
            //add more space between the label and the button
            label.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
            //tab button
            JButton button = new TabButton();
            
            add(button);
            //add more space to the top of the component
            setBorder(BorderFactory.createEmptyBorder(1, 0, 0, 0));
        }
     
        private class TabButton extends JButton implements ActionListener {
            public TabButton() {
                int size = 17;
                setPreferredSize(new Dimension(size, size));
                setToolTipText("close this tab");
                //Make the button looks the same for all Laf's
                setUI(new BasicButtonUI());
                //Make it transparent
                setContentAreaFilled(false);
                //No need to be focusable
                setFocusable(false);
                setBorder(BorderFactory.createEtchedBorder());
                setBorderPainted(false);
                //Making nice rollover effect
                //we use the same listener for all buttons
                addMouseListener(buttonMouseListener);
                setRolloverEnabled(true);
                //Close the proper tab by clicking the button
                addActionListener(this);
            }
     
            public void actionPerformed(ActionEvent e) {
                int i = pane.indexOfTabComponent(ButtonTabComponent.this);
                if (i != -1) {
                    pane.remove(i);
                }
            }
     
            //we don't want to update UI for this button
            public void updateUI() {
            }
     
            //paint the cross
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                //shift the image for pressed buttons
                //if (getModel().isPressed()) {
                //    g2.translate(1, 1);
                //}
                g2.setStroke(new BasicStroke(2));
                g2.setColor(Globals.BLACK);
                if (getModel().isRollover()) {
                	g2.setStroke(new BasicStroke(3));
                    g2.setColor(Globals.RED);
                }
                int delta = 5;
                g2.drawLine(delta, delta, getWidth() - delta - 1, getHeight() - delta - 1);
                g2.drawLine(getWidth() - delta - 1, delta, delta, getHeight() - delta - 1);
                g2.dispose();
            }
        }
    }
    
    private static final MouseListener buttonMouseListener = new MouseAdapter() {
        public void mouseEntered(MouseEvent e) {
            Component component = e.getComponent();
            if (component instanceof AbstractButton) {
                AbstractButton button = (AbstractButton) component;
                button.setBorderPainted(true);
            }
        }
 
        public void mouseExited(MouseEvent e) {
            Component component = e.getComponent();
            if (component instanceof AbstractButton) {
                AbstractButton button = (AbstractButton) component;
                button.setBorderPainted(false);
            }
        }
    };
  
}
