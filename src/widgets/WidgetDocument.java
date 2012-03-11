package widgets;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 * 
 * @author Mahmood Khan
 * @version 2012-03-09 1.0
 *
 */
public class WidgetDocument extends PlainDocument{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private int limit;
		// optional uppercase conversion
		private boolean toUppercase = false;

		WidgetDocument(int limit) {
			super();
			this.limit = limit;
		}

		WidgetDocument(int limit, boolean upper) {
			super();
			this.limit = limit;
			toUppercase = upper;
		}

		public void insertString(int offset, String str, AttributeSet attr)
				throws BadLocationException {
			if (str == null)
				return;

			if ((getLength() + str.length()) <= limit) {
				if (toUppercase) str = str.toUpperCase();
				super.insertString(offset, str, attr);
			}
		}
}
