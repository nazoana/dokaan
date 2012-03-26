package utilities;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import widgets.ButtonWidget;
import widgets.PanelWidget;

import java.util.*;

/**
 *
 * Typical usage is like:
 *
 *  // initial date
 *  GregorianCalendar date = new GregorianCalendar()
 *
 *      // The  owner is the JFrame of the application ("AppClass.this")
 *
 *  // show the date chooser
 *  DateChooser dc = new DateChooser(owner, date);
 *
 *  // user can either choose a date or cancel by closing
 *  if (dc.showDateChooser() == DateChooser.OK_OPTION) {
 *    date = dc.getDate();
 *  }
 */

public class DateChooser extends JDialog implements ActionListener {
    private static final long serialVersionUID = 1L;
    
    public static final int        OK_OPTION     = 1;
    public static final int        CANCEL_OPTION = 2;

    private static final ArrayList<String> monthNames;
    static {
        monthNames = new ArrayList<String>(12);
        monthNames.add("January");
        monthNames.add("February");
        monthNames.add("March");
        monthNames.add("April");
        monthNames.add("May");
        monthNames.add("June");
        monthNames.add("July");
        monthNames.add("August");
        monthNames.add("September");
        monthNames.add("October ");
        monthNames.add("November");
        monthNames.add("December");
    };

    private GregorianCalendar      date;
    private JLabel                 monthLabel;
    private JLabel                 yearLabel;
    private JPanel                 dayGrid;
    private boolean                ready;

    
    public static void main(String[] args) {

    	 GregorianCalendar date = new GregorianCalendar();
    	 // The  owner is the JFrame of the application ("AppClass.this")
    	JFrame frame = new JFrame();
    	 // show the date chooser
    	 DateChooser dc = new DateChooser(frame, date);

    	 // user can either choose a date or cancel by closing
    	 if (dc.showDateChooser() == DateChooser.OK_OPTION) {
    	 	date = dc.getDate();
    	 }
	}
    /**
     * Constructor for DateChooser
     * 
     * @param owner
     *            JFrame instance, owner of DateChooser dialog
     * @param d
     *            GregorianCalendar instance that will be the initial date for
     *            this dialog
     */
    public DateChooser(JFrame owner, GregorianCalendar d) {
        super(owner, "Date Chooser", true);

        date = d;

        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        PanelWidget yearPane = new PanelWidget("yearPane");
        PanelWidget monthPane = new PanelWidget("monthPane");
        yearPane.setLayout(new BoxLayout(yearPane, BoxLayout.X_AXIS));
        monthPane.setLayout(new BoxLayout(monthPane, BoxLayout.X_AXIS));

        ButtonWidget[] navButton = new ButtonWidget[4];

        // build the panel with month name and navigation buttons
        monthPane.add(navButton[0] = new ButtonWidget("prevMonth", "<"));
        monthPane.add(monthLabel = new JLabel(String.valueOf(monthNames
                .get(date.get(GregorianCalendar.MONTH))), JLabel.CENTER));
        monthLabel.setMinimumSize(new Dimension(54, 12));
        monthLabel.setMaximumSize(new Dimension(54, 12));
        monthLabel.setPreferredSize(new Dimension(54, 12));
        monthPane.add(navButton[1] = new ButtonWidget("nextMonth", ">"));

        // build the panel with year and navigation buttons
        yearPane.add(navButton[2] = new ButtonWidget("prevYear", "<<"));
        yearPane.add(yearLabel = new JLabel(String.valueOf(date
                .get(GregorianCalendar.YEAR)), JLabel.CENTER),
                BorderLayout.CENTER);
        yearLabel.setMinimumSize(new Dimension(30, 12));
        yearLabel.setMaximumSize(new Dimension(30, 12));
        yearLabel.setPreferredSize(new Dimension(30, 12));
        yearPane.add(navButton[3] = new ButtonWidget("nextYear", ">>"));

        // register a listener on the navigation buttons
        for (int i = 0; i < 4; i++) {
            navButton[i].addActionListener(this);
        }

        // set the tool tip text on the navigation buttons
        navButton[0].setToolTipText("Go to the previous month");
        navButton[1].setToolTipText("Go to the next month");
        navButton[2].setToolTipText("Go to the previous year");
        navButton[3].setToolTipText("Go to the next year");

        // put the panel for months and years together and add some formatting
        PanelWidget topPane = new PanelWidget("topPane");
        topPane.setLayout(new BoxLayout(topPane, BoxLayout.X_AXIS));
        topPane.setBorder(BorderFactory.createEmptyBorder(5, 10, 0, 10));
        topPane.add(monthPane);
        topPane.add(Box.createRigidArea(new Dimension(30, 0)));
        topPane.add(yearPane);

        // create the panel that will hold the days of the months
        dayGrid = new JPanel(new GridLayout(7, 7));
        updateDayGrid();

        contentPane.add(topPane, BorderLayout.NORTH);
        contentPane.add(dayGrid, BorderLayout.CENTER);

        setResizable(false);
        ready = false;
        pack();

        // center this dialog over the owner
        int xPos = (int) (owner.getLocation().getX() + (owner.getWidth() / 2) - (getWidth() / 2));
        int yPos = (int) (owner.getLocation().getY() + (owner.getHeight() / 2) - (getHeight() / 2));
        setLocation(xPos, yPos);
    }

    /**
     * Return the last selected date for this instance of DateChooser
     */
    public GregorianCalendar getDate() {
        return date;
    }

    /**
     * Displays a DateChooser dialog on the screen. If a new date is selected
     * return OK_OPTION. If the action is canceled returns CANCEL_OPTION.
     * Both of the returned values are defined as static constants.
     */
    public int showDateChooser() {
        ready = false;
        setVisible(true);
        if (ready) {
            return (OK_OPTION);
        } else {
            return (CANCEL_OPTION);
        }
    }

    /**
     * Action handler for this dialog, which handles all the button presses.
     * 
     * @param evt
     *            ActionEvent
     */
    public void actionPerformed(ActionEvent evt) {
        String label = ((JButton) evt.getSource()).getText();

        if (label.equals("<")) {
            int m = monthNames.indexOf(monthLabel.getText());
            m = prevMonth(m);
            monthLabel.setText((String) monthNames.get(m));
            updateDayGrid();
        } else if (label.equals(">")) {
            int m = monthNames.indexOf(monthLabel.getText());
            m = nextMonth(m);
            monthLabel.setText((String) monthNames.get(m));
            updateDayGrid();
        } else if (label.equals("<<")) {
            int y = 0;
            try {
                y = Integer.parseInt(yearLabel.getText());
            } catch (NumberFormatException e) {
                System.err.println(e.toString());
            }
            yearLabel.setText(String.valueOf(--y));
            updateDayGrid();
        } else if (label.equals(">>")) {
            int y = 0;
            try {
                y = Integer.parseInt(yearLabel.getText());
            } catch (NumberFormatException e) {
                System.err.println(e.toString());
            }
            yearLabel.setText(String.valueOf(++y));
            updateDayGrid();
        } else {
            int m = monthNames.indexOf(monthLabel.getText());
            int y = 0;
            int d = 0;
            int hr = 0; 
            int min = 0;
            int aaa = 0;
            try {
                y = Integer.parseInt(yearLabel.getText());
                d = Integer.parseInt(label);
                hr = date.get(GregorianCalendar.HOUR);
                min = date.get(GregorianCalendar.MINUTE);
                aaa = date.get(GregorianCalendar.AM_PM);
            } catch (NumberFormatException e) {
                System.err.println(e.toString());
            }
            date = new GregorianCalendar(y, m, d, hr, min, aaa);
            date.setLenient(false);
            ready = true;
            dispose();
        }
    }

    /**
     * This method is used by DateChooser to calculate and display days of the
     * month in correct format for the month currently displayed. Days of the
     * months are displayed as JButtons that the user can select. DateChooser's
     * current day is highlighted in red color.
     */
    private void updateDayGrid() {
        dayGrid.removeAll();

        // get the currently selected month and year
        int m = monthNames.indexOf(monthLabel.getText());
        int y = 0;
        try {
            y = Integer.parseInt(yearLabel.getText());
        } catch (NumberFormatException e) {
            System.err.println(e.toString());
        }

        // look at the first day of the month for this month
        GregorianCalendar temp = new GregorianCalendar(y, m, 1);
        temp.setLenient(false);
        int offset = 0;

        // decide what day of the week is the first day of this month
        switch (temp.get(GregorianCalendar.DAY_OF_WEEK)) {
        case GregorianCalendar.MONDAY:
            offset = 0;
            break;
        case GregorianCalendar.TUESDAY:
            offset = 1;
            break;
        case GregorianCalendar.WEDNESDAY:
            offset = 2;
            break;
        case GregorianCalendar.THURSDAY:
            offset = 3;
            break;
        case GregorianCalendar.FRIDAY:
            offset = 4;
            break;
        case GregorianCalendar.SATURDAY:
            offset = 5;
            break;
        case GregorianCalendar.SUNDAY:
            offset = 6;
            break;
        }
        
        JLabel monday = new JLabel("Mon", JLabel.CENTER);
        monday.setOpaque(true);
        monday.setBackground(Globals.YELLOW);
        
        JLabel tuesday = new JLabel("Tue", JLabel.CENTER);
        tuesday.setOpaque(true);
        tuesday.setBackground(Globals.YELLOW);
        
        JLabel wednesday = new JLabel("Wed", JLabel.CENTER);
        wednesday.setOpaque(true);
        wednesday.setBackground(Globals.YELLOW);

        JLabel thursday = new JLabel("Thu", JLabel.CENTER);
        thursday.setOpaque(true);
        thursday.setBackground(Globals.YELLOW);

        JLabel friday = new JLabel("Fri", JLabel.CENTER);
        friday.setOpaque(true);
        friday.setBackground(Globals.YELLOW);

        JLabel saturday = new JLabel("Sat", JLabel.CENTER);
        saturday.setOpaque(true);
        //saturday.setBackground(Utility.getColorRed());
        //saturday.setForeground(Utility.getColorWhite());
        saturday.setBackground(Globals.YELLOW);
        saturday.setForeground(Globals.RED);
        
        JLabel sunday = new JLabel("Sun", JLabel.CENTER);
        sunday.setOpaque(true);
        //sunday.setBackground(Utility.getColorRed());
        //sunday.setForeground(Utility.getColorWhite());
        sunday.setBackground(Globals.YELLOW);
        sunday.setForeground(Globals.RED);
        
        // display 7 days of the week across the top
        dayGrid.add(monday);
        dayGrid.add(tuesday);
        dayGrid.add(wednesday);
        dayGrid.add(thursday);
        dayGrid.add(friday);
        dayGrid.add(saturday);
        dayGrid.add(sunday);

        // skip to the correct first day of the week for this month
        for (int i = 1; i <= offset; i++) {
            dayGrid.add(new JLabel(""));
        }

        // display days of the month for this month
        ButtonWidget day;
        for (int i = 1; i <= getLastDay(); i++) {
            dayGrid.add(day = new ButtonWidget(String.valueOf(i), String.valueOf(i)));
            day.setToolTipText("Click on a day to choose it");
            day.addActionListener(this);

            // show the current day in bright red.
            if (i == date.get(Calendar.DATE) && m == date.get(Calendar.MONTH)
                    && y == date.get(Calendar.YEAR)) {
                day.setForeground(Color.red);
            }
        }

        // display the remaining empty slots to preserve the structure
        for (int i = (offset + getLastDay() + 1); i <= 42; i++) {
            dayGrid.add(new JLabel(""));
        }

        repaint();
        validate();
    }

    /**
     * Return the month following the one passed in as an argument. If the
     * argument is the las month of the year, return the first month.
     * 
     * @param month
     *            Current month expressed as an integer (0 to 11).
     */
    private int nextMonth(int month) {
        if (month == 11) {
            return (0);
        }
        return (++month);
    }

    /**
     * Return the month preceding the one passed in as an argument. If the
     * argument is the first month of the year, return the last month.
     * 
     * @param month
     *            Current month expressed as an integer (0 to 11).
     */
    private int prevMonth(int month) {
        if (month == 0) {
            return (11);
        }
        return (--month);
    }

    /**
     * Return the value of the last day in the currently selected month
     */
    private int getLastDay() {
        int m = (monthNames.indexOf(monthLabel.getText()) + 1);
        int y = 0;
        try {
            y = Integer.parseInt(yearLabel.getText());
        } catch (NumberFormatException e) {
            System.err.println(e.toString());
        }

        if ((m == 4) || (m == 6) || (m == 9) || (m == 11)) {
            return (30);
        } else if (m == 2) {
            if (date.isLeapYear(y)) {
                return (29);
            }
            return (28);
        }
        return (31);
    }
}
