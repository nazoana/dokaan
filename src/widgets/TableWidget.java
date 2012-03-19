package widgets;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.SwingConstants;
import javax.swing.RowSorter.SortKey;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import utilities.Globals;
import utilities.Validator;

/**
 * 
 * This class customizes the standard Swing's JTable object, it provides
 * additional functionality and makes the default JTable look much nicer
 * 
 * @author Mahmood Khan
 * @version 2012-03-10 1.0
 * 
 */
public class TableWidget extends JTable{

	/**
	 * It has to do with serialization; it is not important here. But it is here
	 * to avoid compiler warning.
	 */
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unused")
	private final static String SINGLE_SELECTION = "Single";

	@SuppressWarnings("unused")
	private final static String MULTIPLE_SELECTION = "multiple";

	@SuppressWarnings("unused")
	private final static String INTERVAL_SELECTION = "intervalSelection";

	private Shape shape;
	
	private String[] columnToolTips ;
	
	private TableRowSorter<TableWidgetModel> sorter;
	
	private TextWidget txtFilter;
	
	/** 
	 * Constructor
	 * @param id the id for this table
	 */
	public TableWidget(String id) {
		super();
		setId(id);
		configure();
	}

	public TableWidget(String id, TableModel tableModel) {
		super(tableModel);
		setId(id);
		configure();
	}

	public TableWidget(String id, int rows, int cols) {
		super(rows, cols);
		setId(id);
		configure();
	}

	public TableWidget(String id, Object[][] rowData, Object[] columnNames) {
		super(rowData, columnNames);
		setId(id);
		configure();
	}

	public TableWidget(String id, TableModel model, TableColumnModel columnModel) {
		super(model, columnModel);
		setId(id);
		configure();
	}

	@SuppressWarnings("rawtypes")
	public TableWidget(String id, Vector rowData, Vector columnNames) {
		super(rowData, columnNames);
		setId(id);
		configure();
	}

	public TableWidget(String id, TableModel model,
			TableColumnModel columnModel, ListSelectionModel listSelectionModel) {
		super(model, columnModel, listSelectionModel);
		setId(id);
		configure();
	}

	/**
	 * Sets the model for this table
	 * 
	 * @param model
	 */
	public void setTableModel(TableModel model) {
		setModel(model);
	}
	
	/**
	 * Sets the name for this table
	 * @param id
	 */
	private void setId(String id){
		setName(id);
	}
	
	public void enableFilter(TextWidget txtWidget){
		txtFilter = txtWidget;
        //Whenever filterText changes, invoke newFilter.
        txtFilter.getDocument().addDocumentListener(
                new DocumentListener() {
                    public void changedUpdate(DocumentEvent e) {
                        newFilter();
                    }
                    public void insertUpdate(DocumentEvent e) {
                        newFilter();
                    }
                    public void removeUpdate(DocumentEvent e) {
                        newFilter();
                    }
                });
	}

	 /**
     * Update the row filter regular expression from the expression in
     * the text box.
     */
	private void newFilter() {
		RowFilter<TableWidgetModel, Object> rf = null;
		List<RowFilter<Object, Object>> rfs = new ArrayList<RowFilter<Object, Object>>(4);
		// If current expression doesn't parse, don't update.
		try {
			rfs.add(RowFilter.regexFilter(txtFilter.getText(), 0));
			rfs.add(RowFilter.regexFilter("(?i)" + txtFilter.getText(), 1));
			rfs.add(RowFilter.regexFilter("(?i)" + txtFilter.getText(), 2));
			rfs.add(RowFilter.regexFilter("(?i)" + txtFilter.getText(), 3));

			rf = RowFilter.orFilter(rfs);
			
			// rf = RowFilter.regexFilter(txtFilter.getText(), 0);
		} catch (java.util.regex.PatternSyntaxException e) {
			return;
		}
		sorter.setRowFilter(rf);
	}
	
	/**
	 * Customizes this table
	 */
	public void configure() {
		
		getModel().addTableModelListener(this);
		setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		sorter = new TableRowSorter<TableWidgetModel>( (TableWidgetModel) getModel());
		setRowSorter(sorter);
		
		//setAutoCreateRowSorter(true);
		
		// what column should the table be sorted by default.
		getRowSorter().toggleSortOrder(0);

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

	
		getTableHeader().setPreferredSize(new Dimension(getTableHeader().getWidth(), 18));
		getTableHeader().setDefaultRenderer(new TransparentHeader());
		getTableHeader().setOpaque(false);
		//addHeaderListener();
		setColumnIdentifiers();
	}


	
	/*
	 * Adds a mouse listener to the column headers
	 public void addHeaderListener() { 
		 getTableHeader().addMouseListener(new MouseAdapter() {
			 public void mousePressed(MouseEvent event) {
				  JTableHeader header = (JTableHeader) (event.getSource()); 
				  int index = header.columnAtPoint(event.getPoint()); 
				  Class<?> dataType = getModel().getColumnClass(index);
				  System.out.println(dataType + " : " + getColumnModel().getColumn(index).getIdentifier());
			 }
		});
	 }
	 */
	
	/**
	 * Overrides the default tableHeader and adds tool-tips
	 */
	@Override
	protected JTableHeader createDefaultTableHeader() {
		return new JTableHeader(columnModel) {
			//Has to do with serialization; it's here to avoid compilation warning
			private static final long serialVersionUID = 1L;
			
			public String getToolTipText(MouseEvent e) {
				java.awt.Point p = e.getPoint();
				int index = columnModel.getColumnIndexAtX(p.x);
				int realIndex = columnModel.getColumn(index).getModelIndex();
				return columnToolTips[realIndex];
			}
		};
	}


	/**
	 * Specifies certain table options
	 * 
	 * @param colWidths
	 *            The width of columns
	 */
	public void setColumnsWidths(int[] colWidths) {
		if (colWidths != null) {
			for (int i = 0; i < colWidths.length; i++) {
				if (colWidths[i] == 0) {
					continue;
				}
				//getColumnModel().getColumn(i).setMinWidth(colWidths[i]);
				//getColumnModel().getColumn(i).setMaxWidth(colWidths[i]);
				
				getColumnModel().getColumn(i).setPreferredWidth(colWidths[i]);
			}
		}
	}

	/**
	 * Assigns an identifier to each column heading, which is the same as
	 * the column header's value except that it strips out the space.
	 * 1. set column identifiers
	 * 2. set renderer
	 * 3. Sets tool-tips
	 */
	private void setColumnIdentifiers(){
		TableColumnModel tcm = getColumnModel();
		
		TableWidgetCellRenderer renderer = new TableWidgetCellRenderer(
				Globals.GRAY_LIGHT, null, null);
		
		int numColumns = tcm.getColumnCount();
		
		columnToolTips = new String[numColumns];
		
		for (int i = 0; i < numColumns; i++){
			TableColumn column = tcm.getColumn(i);
			//set column renderer
			column.setCellRenderer(renderer);
			
			//retrieve current column header value and clean it
			String columnHeaderValue = Validator.cleanAlphaString(column.getHeaderValue().toString());

			//set column identifier
			column.setIdentifier(columnHeaderValue);
			
			//set the default column tool tip to be the same as column header value
			columnToolTips[i] = columnHeaderValue;
		}
		
	}
	
	/**
	 * Overrides the paintComponent method of the super class (JTable)
	 * in order to be able to draw round corners.
	 */
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(getBackground());
		g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
		super.paintComponent(g2);
	}

	/**
	 * Overrides the super class (JTable) paintBorder method to 
	 * draw round border
	 */
	@Override
	protected void paintBorder(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(Globals.GRAY);
		g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
	}

	/**
	 * Gives the UI delegate an opportunity to define the precise shape of this 
	 * component for the sake of mouse processing. 
	 */
	@Override
	public boolean contains(int x, int y) {
		if (shape == null || !shape.getBounds().equals(getBounds())) {
			shape = new RoundRectangle2D.Float(0, 0, getWidth() - 1,
					getHeight() - 1, 20, 20);
		}
		return shape.contains(x, y);
	}

	/**
	 * A custom JTableHeaderRenderer. It makes the header round-cornered
	 * and formats it nicely.
	 * 
	 * @author Mahmood Khan
	 * @version 2012-03-11 1.0
	 * 
	 */
	class TransparentHeader implements TableCellRenderer {

		/** icon used for a sorted column in ascending order */
	    private ImageIcon ascendingIcon;
	    
	    /** icon used for a sorted column in descending order */
	    private ImageIcon descendingIcon;
	    
	    /**
	     * Constructor
	     */
		public TransparentHeader(){
			createAscendingSortIcon();
			createDescendingSortIcon();
		}
		
		/**
		 * Returns the default table header cell renderer.
		 * <P>
		 * If the column is sorted, the appropriate icon is retrieved from the
		 * current Look and Feel, and a border appropriate to a table header cell is
		 * applied.
		 * <P>
		 * Subclasses may override this method to provide custom content or
		 * formatting.
		 * 
		 * @param table
		 *            the <code>JTable</code>.
		 * @param value
		 *            the value to assign to the header cell
		 * @param isSelected
		 *            This parameter is ignored.
		 * @param hasFocus
		 *            This parameter is ignored.
		 * @param row
		 *            This parameter is ignored.
		 * @param column
		 *            the column of the header cell to render
		 * @return the default table header cell renderer
		 */
		@Override
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
	
			LabelWidget label = new LabelWidget(value.toString().replaceAll(" ", ""), value.toString());
				label.setHorizontalAlignment(SwingConstants.CENTER);
				label.setIcon(getIcon(table, column));
				return label;
		}

		/**
		 * Returns the current sort key, or null if the column is unsorted.
		 * 
		 * @param table
		 *            the table
		 * @param column
		 *            the column index
		 * @return the SortKey, or null if the column is unsorted
		 */
		protected SortKey getSortKey(JTable table, int column) {
			RowSorter<?> rowSorter = table.getRowSorter();
			if (rowSorter == null) {
				return null;
			}

			List<?> sortedColumns = rowSorter.getSortKeys();
			if (sortedColumns.size() > 0) {
				return (SortKey) sortedColumns.get(0);
			}
			return null;
		}

		/**
		 * Overloaded to return an icon suitable to the primary sorted column, or
		 * null if the column is not the primary sort key.
		 * 
		 * @param table
		 *            the <code>JTable</code>.
		 * @param column
		 *            the column index.
		 * @return the sort icon, or null if the column is unsorted.
		 */
		protected Icon getIcon(JTable table, int column) {
			SortKey sortKey = getSortKey(table, column);
			if (sortKey != null
					&& table.convertColumnIndexToView(sortKey.getColumn()) == column) {
				switch (sortKey.getSortOrder()) {
				case ASCENDING:
					//return UIManager.getIcon("Table.ascendingSortIcon");
					return ascendingIcon;
				case DESCENDING:
					//return UIManager.getIcon("Table.descendingSortIcon");
					return descendingIcon;
				}
			}
			return null;
		}
		

	    
		/**
		 * Creates an ascending icon to be used for a sorted column in ascending order
		 */
	    private void createAscendingSortIcon() {
	        int w = 20;
	        int h = 20;
	        BufferedImage ascendingImage;
	        ascendingImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
	        Graphics2D g2 = ascendingImage.createGraphics();
	        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	                RenderingHints.VALUE_ANTIALIAS_ON);
	        g2.setPaint(getBackground());
	        g2.fillRect(0, 0, w, h);
	        int[] x =  { 5, w/2, 15 };
	        int[] y = { 15, 8, 15 };
	        Polygon polygon_ascending = new Polygon(x, y, 3);
	        g2.setPaint(Globals.GREEN);
	        g2.fill(polygon_ascending);
	        g2.dispose();
	        ascendingIcon = new ImageIcon(ascendingImage);
	    }
	    
	    /**
		 * Creates an ascending icon to be used for a sorted column in descending order
		 */
	    private void createDescendingSortIcon(){
	        int w = 20;
	        int h = 20;
	        BufferedImage descendingImage;
	        descendingImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
	        Graphics2D g2 = descendingImage.createGraphics();
	        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	                RenderingHints.VALUE_ANTIALIAS_ON);
	        g2.setPaint(getBackground());
	        g2.fillRect(0, 0, w, h);
	        int[] x = { 5, w / 2, 15 };
	        int[] y =  { 8, 15, 8 };
	  
	        Polygon polygon_descending = new Polygon(x, y, 3);
	        g2.setPaint(Globals.GREEN);
	        g2.fill(polygon_descending);
	        g2.dispose();
	        descendingIcon = new ImageIcon(descendingImage);
	    }
	    
	}
}
