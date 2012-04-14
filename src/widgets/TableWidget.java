
package widgets;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.awt.print.PrinterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.RowSorter.SortKey;
import javax.swing.SwingConstants;
import javax.swing.TransferHandler;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import ui.ClipboardKeyAdapter;
import utilities.Globals;
import utilities.Util;
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
public class TableWidget extends JTable implements MouseListener,
		ActionListener {

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

	private String[] columnToolTips;

	private TableRowSorter<TableWidgetModel> sorter;

	private TextWidget txtFilter;

	private JPopupMenu menu;
	
	private int rightClickedRow;
	
	private int rightClickedCol;

	/**
	 * Constructor
	 * 
	 * @param id
	 *            the id for this table
	 */
	public TableWidget(String id) {
		super();
		configure(id);
	}

	public TableWidget(String id, TableModel tableModel) {
		super(tableModel);
		configure(id);
	}

	public TableWidget(String id, int rows, int cols) {
		super(rows, cols);
		configure(id);
	}

	public TableWidget(String id, Object[][] rowData, Object[] columnNames) {
		super(rowData, columnNames);
		configure(id);
	}

	public TableWidget(String id, TableModel model, TableColumnModel columnModel) {
		super(model, columnModel);
		configure(id);
	}

	@SuppressWarnings("rawtypes")
	public TableWidget(String id, Vector rowData, Vector columnNames) {
		super(rowData, columnNames);
		configure(id);
	}

	public TableWidget(String id, TableModel model,
			TableColumnModel columnModel, ListSelectionModel listSelectionModel) {
		super(model, columnModel, listSelectionModel);
		configure(id);
	}

	/**
	 * Sets the model for this table
	 * 
	 * @param model
	 */
	public void setTableModel(TableModel model) {
		setModel(model);
	}

	public void enableFilter(TextWidget txtWidget) {
		txtFilter = txtWidget;
		// Whenever filterText changes, invoke newFilter.
		txtFilter.getDocument().addDocumentListener(new DocumentListener() {
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
	 * Update the row filter regular expression from the expression in the text
	 * box.
	 */
	private void newFilter() {
		RowFilter<TableWidgetModel, Object> rf = null;
		List<RowFilter<Object, Object>> rfs = new ArrayList<RowFilter<Object, Object>>(
				4);
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

	private void setupMenu() {
		menu = new JPopupMenu("Popup");
		AppMenuItem copyCell = new AppMenuItem("Copy Cell", Util.getImageIcon("../resources/copy.png"));
		copyCell.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
		copyCell.setToolTipText("Copies the content of the cell that is right-clicked");
		
		AppMenuItem copyRow = new AppMenuItem("Copy Row", Util.getImageIcon("../resources/copy.png"));
		copyRow.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.SHIFT_MASK + ActionEvent.CTRL_MASK));
		copyRow.setToolTipText("Copies the selected row");

		AppMenuItem copyTable = new AppMenuItem("Copy Table", Util.getImageIcon("../resources/copy.png"));
		copyTable.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.ALT_MASK));
		copyTable.setToolTipText("Copies the entire table");
		
		AppMenuItem print = new AppMenuItem("Print", Util.getImageIcon("../resources/print.png"));
		print.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
		print.setToolTipText("Prints the entire table");
		
		copyCell.setActionCommand("copyCell");
		copyCell.addActionListener(this);
		
		copyRow.setActionCommand("copySingleRow");
		copyRow.addActionListener(this);

		copyTable.setActionCommand("copyAll");
		copyTable.addActionListener(this);

		print.setActionCommand("print");
		print.addActionListener(this);
		
		menu.add(copyCell);
		menu.add(copyRow);
		menu.add(copyTable);
		menu.addSeparator();
		menu.add(print);
		menu.addSeparator();
	}

	/**
	 * Customizes this table
	 */
	public void configure(String id) {
		if (menu == null) {
			setupMenu();
		}

		addMouseListener(this);
		/*
		 * <pre>
		 * One issue with KeyListeners is that the component being 
         * listened to must have the focus. One way to get around 
         * this is to use Key Bindings.
         * 
		 * int condition = JComponent.WHEN_IN_FOCUSED_WINDOW;
		 * InputMap inputMap = getInputMap(condition);
		 * ActionMap actionMap = getActionMap();
		 * 
		 * inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK),"Copy");
		 * actionMap.put("Copy", new AbstractAction() {
		 * 	public void actionPerformed(ActionEvent e) {
		 * 		TransferHandler th = getTransferHandler();
		 * 		if (th != null) {
		 * 			Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
		 * 			th.exportToClipboard(null, cb, TransferHandler.COPY);
		 * 		}
		 *	}
		 * });
		 * </pre>
		*/
		addKeyListener(new ClipboardKeyAdapter(this));
		setName(id);

		getModel().addTableModelListener(this);
		setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

		sorter = new TableRowSorter<TableWidgetModel>((TableWidgetModel) getModel());
		setRowSorter(sorter);

		/*
		 * The reason the new TableRowSorter object is used above instead of the
		 * (setAutoCreateRowSorter(true)) below is because the above sorter
		 * object is also needed to filter the table, which is implemented in
		 * the enableFilter and newFilter methods.
		 * 
		 * setAutoCreateRowSorter(true);
		 */

		// what column should the table be sorted by default.
		getRowSorter().toggleSortOrder(0);

		setFocusable(false);
		setOpaque(false);

		setFillsViewportHeight(true);
		// setPreferredScrollableViewportSize(new Dimension(40, 30));
		setRowHeight(20);
		setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

		setGridColor(Globals.GRAY_LIGHT);
		setShowGrid(true);
		setIntercellSpacing(new Dimension(1, 1));
		setShowVerticalLines(true);
		setShowHorizontalLines(true);

		setRowSelectionAllowed(true);
		//setCellSelectionEnabled(true);

		getTableHeader().setPreferredSize(
				new Dimension(getTableHeader().getWidth(), 20));
		getTableHeader().setDefaultRenderer(new TransparentHeader());
		getTableHeader().setOpaque(false);
		setColumnIdentifiers();
		// addHeaderListener();
	}

	/*
	 * Adds a mouse listener to the column headers public void
	 * addHeaderListener() { getTableHeader().addMouseListener(new
	 * MouseAdapter() { public void mousePressed(MouseEvent event) {
	 * JTableHeader header = (JTableHeader) (event.getSource()); int index =
	 * header.columnAtPoint(event.getPoint()); Class<?> dataType =
	 * getModel().getColumnClass(index); System.out.println(dataType + " : " +
	 * getColumnModel().getColumn(index).getIdentifier()); } }); }
	 */

	/**
	 * Overrides the default tableHeader and adds tool-tips
	 */
	@Override
	protected JTableHeader createDefaultTableHeader() {
		return new JTableHeader(columnModel) {
			// Has to do with serialization; it's here to avoid compilation
			// warning
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
				// getColumnModel().getColumn(i).setMinWidth(colWidths[i]);
				// getColumnModel().getColumn(i).setMaxWidth(colWidths[i]);
				getColumnModel().getColumn(i).setPreferredWidth(colWidths[i]);
			}
		}
	}

	/**
	 * Assigns an identifier to each column heading, which is the same as the
	 * column header's value except that it strips out the space. 1. set column
	 * identifiers 2. set renderer 3. Sets tool-tips
	 */
	private void setColumnIdentifiers() {
		TableColumnModel tcm = getColumnModel();

		TableWidgetCellRenderer renderer = new TableWidgetCellRenderer();

		int numColumns = tcm.getColumnCount();

		columnToolTips = new String[numColumns];

		for (int i = 0; i < numColumns; i++) {
			TableColumn column = tcm.getColumn(i);
			// set column renderer
			column.setCellRenderer(renderer);

			// retrieve current column header value and clean it
			String columnHeaderValue = Validator.cleanAlphaString(column
					.getHeaderValue().toString());

			// set column identifier
			column.setIdentifier(columnHeaderValue);

			// set the default column tool tip to be the same as column header
			// value
			columnToolTips[i] = columnHeaderValue;
		}
	}
	
	/**
	 * Returns the popup menu associated with the table
	 * @return
	 */
	public JPopupMenu getPopupMenu(){
		return menu;
	}

	/**
	 * Returns the row index that is right clicked on
	 * 
	 * @return
	 */
	public int getRightClickedRow(){
		return rightClickedRow;
	}
	
	/**
	 * Returns the column index that is right clicked on.
	 * @return
	 */
	public int getRightClickedCol(){
		return rightClickedCol;
	}
	/**
	 * Overrides the paintComponent method of the super class (JTable) in order
	 * to be able to draw round corners.
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
	 * Overrides the super class (JTable) paintBorder method to draw round
	 * border
	 */
	@Override
	protected void paintBorder(Graphics g) {
		//Graphics2D g2 = (Graphics2D) g;
		//g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		//		RenderingHints.VALUE_ANTIALIAS_ON);
		// g2.setColor(Globals.GRAY);
		// g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
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
	 * A custom JTableHeaderRenderer. It makes the header round-cornered and
	 * formats it nicely.
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
		public TransparentHeader() {
			createAscendingSortIcon();
			createDescendingSortIcon();
		}

		/**
		 * Returns the default table header cell renderer.
		 * <P>
		 * If the column is sorted, the appropriate icon is retrieved from the
		 * current Look and Feel, and a border appropriate to a table header
		 * cell is applied.
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

			LabelWidget label = new LabelWidget(value.toString().replaceAll(
					" ", ""), value.toString());
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
		 * Overloaded to return an icon suitable to the primary sorted column,
		 * or null if the column is not the primary sort key.
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
					// return UIManager.getIcon("Table.ascendingSortIcon");
					return ascendingIcon;
				case DESCENDING:
					// return UIManager.getIcon("Table.descendingSortIcon");
					return descendingIcon;
				}
			}
			return null;
		}

		/**
		 * Creates an ascending icon to be used for a sorted column in ascending
		 * order
		 */
		private void createAscendingSortIcon() {
			int w = 20;
			int h = 20;
			BufferedImage ascendingImage;
			ascendingImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
			Graphics2D g2 = ascendingImage.createGraphics();
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setPaint(Globals.YELLOW_LIGHT);
			g2.fillRect(0, 0, w, h);
			int[] x = { 5, w / 2, 15 };
			int[] y = { 15, 8, 15 };
			Polygon polygon_ascending = new Polygon(x, y, 3);
			g2.setPaint(Globals.GREEN);
			g2.fill(polygon_ascending);
			g2.dispose();
			ascendingIcon = new ImageIcon(ascendingImage);
		}

		/**
		 * Creates an ascending icon to be used for a sorted column in
		 * descending order
		 */
		private void createDescendingSortIcon() {
			int w = 20;
			int h = 20;
			BufferedImage descendingImage;
			descendingImage = new BufferedImage(w, h,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D g2 = descendingImage.createGraphics();
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setPaint(Globals.YELLOW_LIGHT);
			g2.fillRect(0, 0, w, h);
			int[] x = { 5, w / 2, 15 };
			int[] y = { 8, 15, 8 };

			Polygon polygon_descending = new Polygon(x, y, 3);
			g2.setPaint(Globals.GREEN);
			g2.fill(polygon_descending);
			g2.dispose();
			descendingIcon = new ImageIcon(descendingImage);
		}

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (!e.isPopupTrigger()) {
			return;
		}
		Point p = e.getPoint();
		rightClickedRow = rowAtPoint(p);
		rightClickedCol = columnAtPoint(p);
		ListSelectionModel model = getSelectionModel();
		model.setSelectionInterval(rightClickedRow, rightClickedRow);
		menu.show(e.getComponent(), e.getX(), e.getY());
	}

	/**
	 * This is overriding the actionPerformed method in the ActionListener interface.
	 * It will get invoked any time an item that has this class registered as its
	 * actionListener fires an action.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		TransferHandler th = getTransferHandler();
		Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
		
		if (actionCommand.equals("copyCell")) {
		    StringSelection cellContent = new StringSelection(getValueAt(rightClickedRow,rightClickedCol).toString());
		    cb.setContents(cellContent, null);
		}
		else if  (actionCommand.equals("copySingleRow")) {
			if (th != null) {
				th.exportToClipboard(this, cb, TransferHandler.COPY);
			}
		} 
		else if (actionCommand.equals("copyAll")) {
			ActionEvent nev = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "copy");
			selectAll();
			getActionMap().get(nev.getActionCommand()).actionPerformed(nev);
		}
		else if (actionCommand.equals("print")){
			try {
				print();
			} catch (PrinterException e1) {
				// TODO Auto-generated catch block
				
				e1.printStackTrace();
			}
		}
		else if (actionCommand.equals("edit")){
			System.out.println("TODO:  edit");
			//TODO: 
		}
		else if (actionCommand.equals("delete")) {
		    System.out.println(e.getSource().getClass());
		    //TODO
		}
		
	}
}
