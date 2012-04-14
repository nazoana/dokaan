package widgets;

import java.awt.Color;
import java.awt.Component;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import org.datanucleus.store.types.sco.simple.SqlTimestamp;

import utilities.Globals;
import utilities.Util;

/**
 * 
 * @author Mahmood Khan
 * @version 2012-04-01 1.0
 *
 */
public class TableWidgetCellRenderer extends DefaultTableCellRenderer {

    /**
     * This has to do with serialization. It is not important, but is placed
     * here to prevent a compiler warning.
     */
    private static final long serialVersionUID = 1L;

    private static final Color SHADED_ROW_COLOR = Globals.GRAY_LIGHT;
    private static final Color UNSHADED_ROW_COLOR = Globals.WHITE;
    private static final Color SELECTED_ROW_COLOR = Globals.BLUE; //Color.decode("#6699FF");
    
	/** The logger object used to log messages */
	//private static final Logger LOGGER = AppLogger.getAppLogger(TableWidgetCellRenderer.class.getName());
    
    public TableWidgetCellRenderer() {
        super();
    }

    /**
     * This method is called each time a cell in a column 
     * using this renderer needs to be rendered
     */
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int col) {

        Component cell = super.getTableCellRendererComponent(table, value,
                isSelected, hasFocus, row, col);

        if (isSelected) {
            // cell (and perhaps other cells) are selected
        }

        if (hasFocus) {
            // this cell is the anchor and the table has the focus
        }
        
		try {
			if (value instanceof Boolean) {
				JCheckBox cBox = new JCheckBox();
				cBox.setSelected(Boolean.TRUE.equals(value));
				cBox.setHorizontalAlignment(SwingConstants.CENTER);
				this.shadeAlternateRows(table, row, col, cBox);
				return cBox;
			} 
			else if (value instanceof Integer) {
				setHorizontalAlignment(SwingConstants.CENTER);
			} 
			else if (value instanceof Long) {
				setHorizontalAlignment(SwingConstants.CENTER);
			} 
			else if (value instanceof String) {
				setHorizontalAlignment(SwingConstants.LEFT);
			} 
			else if (value instanceof Date & value.getClass().getSimpleName().equals("Date")) {
				setHorizontalAlignment(SwingConstants.LEFT);
				setText(Util.formatDate((Date) value, "E, MMM dd, yyyy"));
			} 
			else if (value instanceof SqlTimestamp & value.getClass().getSimpleName().equals("SqlTimestamp")) {
				setHorizontalAlignment(SwingConstants.LEFT);
				setText(Util.formatDate((Date) value, "E, MMM dd, yyyy hh:mm a"));
			} 
			else if (value instanceof Double) {
				setHorizontalAlignment(SwingConstants.CENTER);
			}
		} catch (NullPointerException e) {
			//ignore
			//LOGGER.log(Level.INFO, "table cell value is null: " + e.getMessage());
		}
        if (value != null) {
        	setToolTipText(value.toString());
        }
        this.shadeAlternateRows(table, row, col, cell);
        cell.setFont(Globals.FONT_APPLICATION);
        cell.setForeground(Globals.BLACK);
        setBorder(BorderFactory.createEmptyBorder(3,3,3,3));
        return cell;
    }

    /**
     * Used to shade each row 
     * @param table
     * @param row
     * @param col
     * @param cell
     */
    public void shadeAlternateRows(JTable table, int row, int col,
            Component cell) {
        if (row % 2 == 0 && !table.isCellSelected(row, col)) {
            cell.setBackground(UNSHADED_ROW_COLOR);
        } else if (table.isCellSelected(row, col)) {
            cell.setBackground(SELECTED_ROW_COLOR);
        } else {
            cell.setBackground(SHADED_ROW_COLOR);
        }
    }
}
