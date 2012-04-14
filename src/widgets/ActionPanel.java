package widgets;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseListener;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import utilities.Globals;

/**
 * An instance of this class functions as a slider handler for 
 * showing/hiding another JPanel
 * 
 * @author Mahmood Khan
 * @version 2012-04-2 1.0
 *
 */
public class ActionPanel extends JPanel {
    /**
     * This has to do with serialization. It is not important, but is placed
     * here to prevent a compiler warning.
     */
    private static final long serialVersionUID = 1L;
    
    private String heading;
    private Font font;
    private boolean selected;
    private BufferedImage open;
    private BufferedImage closed;
    private Rectangle target;
    private static final int OFFSET = 30;
    private static final int PADDING = 5;

    /**
     * Constructor 
     * 
     * @param heading
     * @param mouseListener
     */
    public ActionPanel(String heading, MouseListener mouseListener) {
        this.heading = heading;
        setName(heading);
        addMouseListener(mouseListener);
        font = Globals.FONT_BOLD;
        selected = false;
        setBackground(Globals.YELLOW); //new Color(200, 200, 220));
        setPreferredSize(new Dimension(850, 20));
        //setBorder(BorderFactory.createRaisedBevelBorder());
        setBorder(null);
        createImages();
        setRequestFocusEnabled(true);
    }
    
    /**
     * Returns the rectangle that is click-able 
     * in order to expand and collapse
     * @return
     */
    public Rectangle getTarget(){
        return target;
    }

    /**
     * Toggles the selected action panel
     */
    public void toggleSelection() {
        selected = !selected;
        repaint();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        //int w = getWidth();
        int h = getHeight();
        if (selected)
            g2.drawImage(open, PADDING, 0, this);
        else
            g2.drawImage(closed, PADDING, 0, this);
        g2.setFont(font);
        FontRenderContext frc = g2.getFontRenderContext();
        LineMetrics lm = font.getLineMetrics(heading, frc);
        float height = lm.getAscent() + lm.getDescent();
        float x = OFFSET;
        float y = (h + height) / 2 - lm.getDescent();
        g2.drawString(heading, x, y);
    }

    /**
     * Creates the red and green arrows
     */
    private void createImages() {
        int w = 20;
        int h = getPreferredSize().height;
        target = new Rectangle(2, 0, 200, 18);
        open = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = open.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setPaint(getBackground());
        g2.fillRect(0, 0, w, h);
        int[] x = { 2, w / 2, 18 };
        int[] y = { 4, 15, 4 };
        Polygon p = new Polygon(x, y, 3);
        g2.setPaint(Color.GREEN.darker());
        g2.fill(p);
        //g2.setPaint(Color.blue.brighter());
        //g2.draw(p);
        g2.dispose();
        
        closed = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        g2 = closed.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setPaint(getBackground());
        g2.fillRect(0, 0, w, h);
        x = new int[] { 3, 13, 3 };
        y = new int[] { 4, h / 2, 16 };
        p = new Polygon(x, y, 3);
        g2.setPaint(Color.red);
        g2.fill(p);
        //g2.setPaint(Color.blue.brighter());
        //g2.draw(p);
        g2.dispose();
    }
}
