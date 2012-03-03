package utilities;

import java.util.logging.Level;
import java.util.logging.Logger;

public class TestClass {

    public static void main(String[] args) {
        System.out.println(getLineNumber());
        
        
        
        
        
        
        
        
        
        Logger.getLogger(TestClass.class.getName()).log(Level.WARNING, "hello: " + getLogInfo());
        Logger.getLogger(TestClass.class.getName()).log(Level.WARNING,  getLogInfo());
    }
    /** Get the current line number.
     * @return int - Current line number.
     */
    public static int getLineNumber() {
        return Thread.currentThread().getStackTrace()[2].getLineNumber();
    }
    
    public static String getLogInfo(){
        String fullClassName = Thread.currentThread().getStackTrace()[1].getClassName();            
        String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
        String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
        int lineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();

        return (className + "." + methodName + "():" + lineNumber);

    }
}
