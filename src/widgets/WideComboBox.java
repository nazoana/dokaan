package widgets;

import javax.swing.*; 
import java.awt.*; 
import java.util.Vector; 
 
// got this workaround from the following bug: 
//      http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4618607 
/**
 * We create a subclass to JComboBox here.
 * @author mkhan
 * @version 2012-03-12 1.0
 *
 */
public class WideComboBox extends JComboBox{ 
 
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public WideComboBox() { 
    } 
 
    public WideComboBox(final Object items[]){ 
        super(items); 
    } 
 
    public WideComboBox(Vector items) { 
        super(items); 
    } 
 
    public WideComboBox(ComboBoxModel aModel) { 
        super(aModel); 
    } 
 
    private boolean layingOut = false; 
 
    public void doLayout(){ 
        try{ 
            layingOut = true; 
            super.doLayout(); 
        }finally{ 
            layingOut = false; 
        } 
    } 
 
    public Dimension getSize(){ 
        Dimension dim = super.getSize(); 
        if(!layingOut) 
            dim.width = Math.max(dim.width, getPreferredSize().width); 
        return dim; 
    } 
}