package widgets;

import javax.swing.JTextField;
import javax.swing.text.Document;

/**
 * 
 * @author Mahmood Khan
 * @version 2012-03-06 1.0
 *
 */
public class TextWidget extends JTextField{

	/**
	 * It has to do with serialization; it is not important.
	 * it is here to avoid a compiler warning.
	 */
	private static final long serialVersionUID = 1L;
	
	private String oldValue;

	public TextWidget(){
		super();
		oldValue = this.getText();
	}
	
	public TextWidget(int columns){
		super(columns);
		oldValue = this.getText();
	}
	
	public TextWidget(String text){
		super(text);
		oldValue = this.getText();
	}
	
	public TextWidget(String text, int columns){
		super(text, columns);
		oldValue = this.getText();
	}
	
	public TextWidget(Document doc, String text, int columns){
		super(doc, text, columns);
		oldValue = this.getText();
	}
	
	public String getOldValue(){
		return oldValue;
	}
}
