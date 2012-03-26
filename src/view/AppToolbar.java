package view;

import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;

import javax.swing.BorderFactory;
import javax.swing.JToolBar;

import utilities.Globals;
import utilities.Util;
import widgets.ButtonWidget;

public class AppToolbar extends JToolBar{

	/**
	 * It has to do with serialization; it is not important here.
	 * it is here to avoid compilation warning
	 */
	private static final long serialVersionUID = 1L;
	
	private Shape shape;

	public AppToolbar(String id) {
		super();
		configure(id);
	}
	
	/**
	 *  Creates a new tool bar with the specified orientation. 
	 *  The orientation must be either HORIZONTAL or VERTICAL. 
	 *  
	 * @param id - the id (name) for this tool bar
	 * @param orientation - the orientation desired
	 */
	public AppToolbar(String id, int orientation) {
		super(orientation);
		configure(id);
	}
	
	/**
	 * Creates a new tool bar with the specified name. 
	 * The name is used as the title of the un-docked tool bar. 
	 * The default orientation is HORIZONTAL. 
	 * @param id - the id of the tool bar
	 * @param name - the name of the tool bar
	 */
	public AppToolbar(String id, String name){
		super(name);
		configure(id);
	}
	
	/**
	 * Creates a new tool bar with a specified name and orientation. 
	 * All other constructors call this constructor. 
	 * If orientation is an invalid value, an exception will be thrown. 
	 * orientation 
	 * @param id - the id of the tool bar
	 * @param name - the name of the tool bar
	 * @param orientation - the initial orientation -- it must be either HORIZONTAL or VERTICAL 
	 * 
	 * Throws: 
	 * IllegalArgumentException - if orientation is neither HORIZONTAL nor VERTICAL
	 */
	public AppToolbar(String id, String name, int orientation){
		super(name, orientation);
	}
	
	private void configure(String id){
		setName(id);
		setBackground(Globals.WHITE);
		setFont(Globals.FONT_APPLICATION);
		setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
		setFloatable(false);
		setLayout(new FlowLayout());
		setOpaque(false);
		setRollover(true);
		setFocusable(false);
		setToolTipText("this is sample text");
		addButtons();
	}
	
	private void addButtons(){
		ButtonWidget btnNewCustomer = new ButtonWidget("btnNewCustomer", " New Customer");
		btnNewCustomer.setFocusable(false);
		btnNewCustomer.setIcon(Util.getImageIcon("../resources/new.png"));
		
		btnNewCustomer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				System.out.println("hello,");
			}
		});
		
		ButtonWidget btnCustomers = new ButtonWidget("btnCustomers", " Customers");
		btnCustomers.setFocusable(false);
		btnCustomers.setIcon(Util.getImageIcon("../resources/table.png"));
		
		
		ButtonWidget btnNewOrder = new ButtonWidget("btnNewOrder", " New Order");
		btnNewOrder.setIcon(Util.getImageIcon("../resources/new.png"));
		btnNewOrder.setFocusable(false);
		
		ButtonWidget btnOrders = new ButtonWidget("btnOrders", " Orders");
		btnOrders.setIcon(Util.getImageIcon("../resources/table.png"));
		btnOrders.setFocusable(false);
		
		ButtonWidget btnNewSupplier = new ButtonWidget("btnNewOrder", " New Supplier");
		btnNewSupplier.setIcon(Util.getImageIcon("../resources/new.png"));
		btnNewSupplier.setFocusable(false);
		
		ButtonWidget btnSuppliers = new ButtonWidget("btnSuppliers", " Suppliers");
		btnSuppliers.setIcon(Util.getImageIcon("../resources/table.png"));
		btnSuppliers.setFocusable(false);
		
		ButtonWidget btnHelp = new ButtonWidget("btnHelp", "Help ");
		btnHelp.setIcon(Util.getImageIcon("../resources/help.png"));
		btnHelp.setFocusable(false);
		
		ButtonWidget btnExit = new ButtonWidget("btnExit", "Exit ");
		btnExit.setIcon(Util.getImageIcon("../resources/exit.png"));
		btnExit.setFocusable(false);
		
		add(btnNewCustomer);
		add(btnCustomers);
		addSeparator();
		addSeparator();
		
		add(btnNewOrder);
		add(btnOrders);
		addSeparator();
		addSeparator();
		
		add(btnNewSupplier);
		add(btnSuppliers);
		
		add(btnHelp);
		
		add(btnExit);
		
	}
	
    @Override
    protected void paintComponent(Graphics g) {
    	Graphics2D g2 = (Graphics2D)g;
    	g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
    			RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Globals.YELLOW_LIGHT);
        g2.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);
        super.paintComponent(g2);
    }
    
    @Override
    protected void paintBorder(Graphics g) {
    	Graphics2D g2 = (Graphics2D)g;
    	g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
    			RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Globals.GRAY_LIGHT);
        g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);
    }
    
    @Override
    public boolean contains(int x, int y) {
        if (shape == null || !shape.getBounds().equals(getBounds())) {
            shape = new RoundRectangle2D.Float(0, 0, getWidth()-1, getHeight()-1, 15, 15);
        }
        return shape.contains(x, y);
    }
}
