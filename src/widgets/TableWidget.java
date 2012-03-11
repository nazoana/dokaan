package widgets;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;



import utilities.Globals;

/**
 * 
 * @author Mahmood Khan
 * @version 2012-03-10 1.0
 *
 */
public class TableWidget extends JTable{

	/**
	 * It has to do with serialization; it is not important
	 * here. But it is here to avoid compiler warning.
	 */
	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unused")
	
    private final static String SINGLE_SELECTION = "Single";
	
    @SuppressWarnings("unused")
	private final static String MULTIPLE_SELECTION = "multiple";
    
    @SuppressWarnings("unused")
    private final static String INTERVAL_SELECTION = "intervalSelection";
    
    private Shape shape;
    
	public TableWidget(String id){
		super();
		configure();
	}
	
	public TableWidget(String id, TableModel tableModel){
		super(tableModel);
		configure();
	}
	
	public TableWidget(String id, int rows, int cols){
		super(rows, cols);
		configure();
	}
	
	public TableWidget(String id, Object[][] rowData, Object[] columnNames){
		super(rowData, columnNames);
		configure();
	}
	
	public TableWidget(String id, TableModel model, TableColumnModel columnModel){
		super(model, columnModel);
		configure();
	}
	
	@SuppressWarnings("rawtypes")
	public TableWidget(String id, Vector rowData,  Vector columnNames){
		super(rowData, columnNames);
		configure();
	}
	
	public TableWidget(String id, TableModel model, TableColumnModel columnModel, ListSelectionModel listSelectionModel){
		super(model, columnModel, listSelectionModel);
		configure();
	}
	
	public void setTableModel(TableModel model){
		setModel(model);
	}
	
	private void configure(){

		setHeaderOptions(25, Globals.GRAY_LIGHT, true, Globals.FONT_APPLICATION);
		setTableOptions(new int[] {40, 150, 250});
		setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		/*
		TableRowSorter<TableWidgetModel> sorter = new TableRowSorter<TableWidgetModel>(
                (TableWidgetModel) getModel());
		setRowSorter(sorter);
  		*/
		setAutoCreateRowSorter(true);
		//what column should the table be sorted by default.
        getRowSorter().toggleSortOrder(1);
        
		setFocusable(false);
		setOpaque(false);
		
		setFillsViewportHeight(true);
		setPreferredScrollableViewportSize(new Dimension(40, 30));
        setRowHeight(20);
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		setGridColor(Globals.GRAY_LIGHT);
        setShowGrid(true);
        setIntercellSpacing(new Dimension(1, 1));
        setShowVerticalLines(true);
        setShowHorizontalLines(true);
        
        setRowSelectionAllowed(true);
        //setCellSelectionEnabled(true);
        
        setRenderer();
        //setDefaultRenderer(Object.class, l);
        //setDefaultRenderer(Object.class, l);
        //setDefaultRenderer(Boolean.class, new TranslucentBooleanRenderer());

        getTableHeader().setDefaultRenderer(new TransparentHeader("tblHeader"));
        getTableHeader().setOpaque(false);
        
	}
	
	@Override 
	public Component prepareEditor(TableCellEditor editor, int row, int column) {
        Component c = super.prepareEditor(editor, row, column);
        if(c instanceof JComponent) {
            ((JComponent)c).setOpaque(false);
        }
        return c;
    }
	
	/**
     * Set renderer for the following column numbers
     * @param colNums
     *          Comma separated list of column numbers in the table
     */
    private void setRenderer() {
        TableWidgetCellRenderer renderer = new TableWidgetCellRenderer(Globals.GRAY_LIGHT, null, null);
        for (int i = 0; i < getModel().getColumnCount(); i++){
            getColumnModel().getColumn(i).setCellRenderer(renderer);
        }
    }
	
    /**
     * Sets table's header options
     * 
     * @param height
     *            Table header's height
     * @param bgColor
     *            Table header's background color
     * @param colReorder
     *            If true, columns can be rearranged by dragging them
     * @param font
     *            Font to be used for the header
     */
    public void setHeaderOptions(int height, Color bgColor, boolean colReorder,
            Font font) {
        JTableHeader header = getTableHeader();
        header.setBackground(bgColor);
        getTableHeader().setPreferredSize(
                new Dimension(getTableHeader().getWidth(), height));
        getTableHeader().setReorderingAllowed(colReorder);
        header.setFont(font);
    }
    
    
    /**
     * Specifies certain table options
     * 
     * @param colWidths
     *          The width of columns
     */
    public void setTableOptions(int[] colWidths) {
        if (colWidths != null){
	        for (int i = 0 ; i < colWidths.length; i++){
	            if (colWidths[i] == 0) continue;
	            getColumnModel().getColumn(i).setMinWidth(colWidths[i]);
	            getColumnModel().getColumn(i).setMaxWidth(colWidths[i]);
	        }
        }
        
    }
    

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(getBackground());
		g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
		super.paintComponent(g2);
	}

	@Override
	protected void paintBorder(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(Globals.GRAY);
		g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
	}

	@Override
	public boolean contains(int x, int y) {
		if (shape == null || !shape.getBounds().equals(getBounds())) {
			shape = new RoundRectangle2D.Float(0, 0, getWidth() - 1,
					getHeight() - 1, 20, 20);
		}
		return shape.contains(x, y);
	}
	
	/**
	 * 
	 * @author Mahmood Khan
	 * @version 2012-03-11 1.0
	 *
	 */
	class TransparentHeader extends LabelWidget implements TableCellRenderer {
	    public TransparentHeader(String id) {
			super(id);
		}
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		
	    @Override
	    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
	        setText( value != null ? value.toString() : "" );
	        setHorizontalAlignment(JLabel.CENTER);
	        setOpaque(false);
	        return this;
	    }
	}
}
