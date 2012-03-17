package widgets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.event.UndoableEditListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.undo.UndoableEditSupport;

/**
 * 
 * @author Mahmood Khan
 * @version 2012-03-11 1.0
 *
 */
public class TableWidgetModel extends AbstractTableModel{
    /**
     * This has to do with serialization. It is not important, but is placed
     * here to prevent a compiler warning.
     */
    private static final long serialVersionUID = 1L;
    
    /** The column headers of the table */
    private String[] columnNames;
    
    /** Column data types */
    @SuppressWarnings({ "rawtypes", "unused" })
    private Class[] columnTypes;
    
    /** 1 = edit-able; 0 = non-edit-able */
    private int[] colEditable;
    
    /** The actual data to be displayed in the table */
    private ArrayList<Object[]> rowData;
    
    /** Map column names to column types */
    private Map<String, Object[]> colSpecMap;
    
    
    /** Manages undo related listeners */
    private UndoableEditSupport undoSupport ;;
    
    /**
     * Constructor
     * 
     * @param columnNames
     *            Headers for columns
     * @param colTypes
     *            The types of data column store
     * @param rowData
     *            The actual row data in the table
     */
    @SuppressWarnings("rawtypes")
    public TableWidgetModel(String[] columnNames, Class[] colTypes, int[] colEditable,
            ArrayList<Object[]> rowData) {
        super();
        undoSupport = new UndoableEditSupport();
        colSpecMap = new HashMap<String, Object[]>();
        this.columnNames = columnNames;
        this.rowData = rowData;
        this.columnTypes = colTypes;
        this.colEditable = colEditable;
        
        for (int i = 0; i < columnNames.length; i++) {
            colSpecMap.put(
            		columnNames[i], 
            		new Object[] { colTypes[i], colEditable[i] }
            		);
        }
    }
       
      
    /**
     * Returns the number of columns in the table
     */
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    /**
     * Returns the number of rows in the table
     */
    @Override
    public int getRowCount() {
        try {
            return rowData.size();
        } catch (NullPointerException e){
            return 0;
        }
    }

    /**
     * Returns the value of a specific cell in the table
     */
    @Override
    public Object getValueAt(int r, int c) {
    	try {
    		return rowData.get(r)[c];
    	} catch (NullPointerException e){
    		return null;
    	} catch (ArrayIndexOutOfBoundsException e) {
    		return null;
    	}
    }

    /**
     * Returns the name of a column
     */
    @Override
    public String getColumnName(int colIndex) {
    	if (colIndex < 0){ 
    		return null;
    	}
        return columnNames[colIndex];
    }

    /**
     * JTable uses this method to determine the default renderer/ editor for
     * each cell. If this method is not implemented, then a boolean column would
     * contain text ("true"/"false"), rather than a check box.
     * 
     */
    //@SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public Class<?> getColumnClass(int colIndex) {
        return (Class<?>) colSpecMap.get(getColumnName(colIndex))[0];
    }

    /**
     * Specifies which column are edit-able
     */
    @Override
    public boolean isCellEditable(int rowIndex, int colIndex) {
        return (((Integer) colSpecMap.get(getColumnName(colIndex))[1]) == 1 ? true
                : false);
    }
    
    /**
     * Allows to change value of an edit-able cell in table
     * @param value
     *          The new value replacing an existing one
     * @param rowIndex
     *          The row that is about to be edited
     * @param colIndex
     *          The column that is about to be edited
     */
    @Override
    public void setValueAt(Object value, int rowIndex, int colIndex){
        setValueAt(value, rowIndex, colIndex, true);
        //fireTableCellUpdated(rowIndex, colIndex);
    }
    /**
     * Allows to change value of an edit-able cell in table
     * @param value
     *          The new value replacing an existing one
     * @param rowIndex
     *          The row that is about to be edited
     * @param colIndex
     *          The column that is about to be edited
     * @param undoable
     *          if true, then undo data will be registered
     */
    public void setValueAt(Object value, int rowIndex, int colIndex, boolean undoable) {
        UndoableEditListener listeners[] = getListeners(UndoableEditListener.class);
        if (listeners == null) {
            setValueHelper(value, rowIndex, colIndex);
            return;
        }
        // save old cell state
        Object oldValue = getValueAt(rowIndex, colIndex);
        setValueHelper(value, rowIndex, colIndex);
        
        // build an UndoableEditEvent and a CellEdit "Command" object
        // only if there was a change in the cell value
        Object newValue = getValueAt(rowIndex, colIndex);
        if(oldValue==null && newValue == null){
            return;
        }
        
        // if anything changed -> announce edit
        if(oldValue!=null && !oldValue.equals(newValue)
                || newValue != null && !newValue.equals(oldValue)) {
        	TableWidgetCellEdit cellEdit = new TableWidgetCellEdit(this, oldValue, newValue, rowIndex, colIndex);
            undoSupport.postEdit(cellEdit);
        }
    }
    

    /**
     * Sets the value at the given row/column position
     * @param value
     *          value to assign to cell
     * @param rowIndex
     *          row number of cell
     * @param colIndex
     *          column number of cell
     */
    public void setValueHelper(Object value, int rowIndex, int colIndex){
        rowData.get(rowIndex)[colIndex] =  value;
        setValueAt(value, rowIndex, colIndex);
        fireTableCellUpdated(rowIndex, colIndex);
    }
    
    /**
     * Removes the row referenced by the rowIndex
     * @param rowIndex
     *          The rowIndex specifies a row to be deleted
     */
    public void removeTableRow(int rowIndex){
        rowData.remove(rowIndex);
        this.fireTableDataChanged();
    }
    
    /**
     * Adds a row to the table
     * @param row
     *          A row with the same number of column and appropriate 
     *          data types to be added to the table
     */
    public void addRow(Object[] row){
        rowData.add(row);
        this.fireTableDataChanged();  
    }
    
    /**
     * Setter for rowData field
     * @param rowData
     */
    public void setRowData(ArrayList<Object[]> rowData){
        this.rowData = rowData;
        fireTableDataChanged();
    }
    
    /**
     * Setter for colEditable
     * @param colEditable
     */
    public void setColEditable(int[] colEditable){
        this.colEditable = colEditable;
    }
    
    /**
     * Getter for colEditable
     * @return
     */
    public int[] getColEditable(){
        return this.colEditable;
    }

    /**
     * Access to the UndoableEditSupport to add/remove undo related listeners
     * @return
     */
    public UndoableEditSupport getUndoSupport() {
        return undoSupport;
    }
}
