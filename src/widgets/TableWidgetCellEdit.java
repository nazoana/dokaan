package widgets;

import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoableEdit;

/**
 * 
 * @author Mahmood Khan
 * @version 2012-04-01 1.0
 *
 */
public class TableWidgetCellEdit extends AbstractUndoableEdit implements UndoableEdit{
    /**
* This has to do with serialization. It is not important, but is placed
* here to prevent a compiler warning.
*/
    private static final long serialVersionUID = 1L;

    private TableWidgetModel tableModel;

    private Object oldValue;

    private Object newValue;

    private int row;

    private int column;

    /**
* This constructor takes all information required for the undo. We want to
* be able to restore the previous value of a table cell, so we need the
* TableModel, the old value, the new value (for re-do), and the row/column
* position in the model where value goes.
*
* @param tableModel
* @param oldValue
* @param newValue
* @param row
* @param column
*/
    public TableWidgetCellEdit(TableWidgetModel tableModel, Object oldValue,
            Object newValue, int row, int column) {

        this.tableModel = tableModel;
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.row = row;
        this.column = column;
    }
    
    public Object getOldValue(){
        return oldValue;
    }

    public Object getNewValue(){
        return newValue;
    }
    
    public int getRow(){
        return row;
    }
    
    public int getColumn(){
        return column;
    }
    
    public TableWidgetModel getModel(){
        return tableModel;
    }
    /**
* This returns a String to be presented to the user in the edit menu, for
* example “Cell Edit”, which will be presented as “Undo Cell Edit” or
* “Re-do Cell Edit” respectively.
*/
    @Override
    public String getPresentationName() {
        //TODO:
        return null;
    }

    /**
* This operation actually performs the undo operation. For undoing a value
* change in a TableModel, will set the cell at position row/column the
* oldValue (given in the constructor). The required housekeeping of the
* undo/re-do stack will be done automatically by the UndoManager and
* UndoableEdit classes.
*/
    @Override
    public void undo() throws CannotUndoException {
        // Call the UndoableEdit class for housekeeping
        super.undo();
        // set the old value, excluding all undo activity
        tableModel.setValueAt(oldValue, row, column, false);
    }

    /**
* Same as undo(), except that we set the newValue to the cell.
*/
    @Override
    public void redo() throws CannotUndoException {
        // Call the UndoableEdit class for housekeeping
        super.redo();
        // set the old value, excluding all undo activity
        tableModel.setValueAt(newValue, row, column, false);
    }
}
