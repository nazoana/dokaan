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

public class TableWidgetCellRenderer extends DefaultTableCellRenderer {

    /**
     * This has to do with serialization. It is not important, but is placed
     * here to prevent a compiler warning.
     */
    private static final long serialVersionUID = 1L;

    private Color shadedColor;
    private Color unshadedColor;
    private Color selectedColor;
    
    public TableWidgetCellRenderer(Color shadedColor, Color unshadedColor, Color selectedColor) {
        super();
        
        if (shadedColor == null){
            this.shadedColor = Color.LIGHT_GRAY;
        } else {
            this.shadedColor = shadedColor;
        }
        
        if (unshadedColor == null){
            unshadedColor = Color.WHITE;
        } else {
            this.unshadedColor = unshadedColor;
        }
        
        if (selectedColor == null){
            this.selectedColor = Color.decode("#6699FF");
        } else {
            this.selectedColor = selectedColor;
        }
        
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
			} else if (value instanceof Integer) {
				setHorizontalAlignment(SwingConstants.CENTER);
			} else if (value instanceof Long) {
				setHorizontalAlignment(SwingConstants.CENTER);
			} else if (value instanceof String) {
				setHorizontalAlignment(SwingConstants.LEFT);
			} else if (value instanceof Date
					& value.getClass().getSimpleName().equals("Date")) {
				setText(Util.formatDate((Date) value, "E, MMM dd, yyyy"));
			} else if (value instanceof SqlTimestamp
					& value.getClass().getSimpleName().equals("SqlTimestamp")) {
				setText(Util
						.formatDate((Date) value, "E, MMM dd, yyyy hh:mm a"));
			} else if (value instanceof Double) {
				setHorizontalAlignment(SwingConstants.CENTER);
			}
		} catch (NullPointerException e) {
			//TODO: log as debug message
		}
        if (value != null) {
        	setToolTipText(value.toString());
        }
        this.shadeAlternateRows(table, row, col, cell);
        cell.setFont(Globals.FONT_APPLICATION);
        cell.setForeground(Globals.BLACK);
        setBorder(BorderFactory.createEmptyBorder(8,8,8,8));
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
            cell.setBackground(unshadedColor);
        } else if (table.isCellSelected(row, col)) {
            cell.setBackground(selectedColor);
        } else {
            cell.setBackground(shadedColor);
        }
    }
}
