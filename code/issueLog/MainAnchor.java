/**
 * This java file consists a clss MainAnchor which 
 * consists a main method .
 * @author Pulkit Saxena
 * @version 1.0
 * Date 4-Aug-007
 */
package issueLog;

/**
 * This class consists a main method whcih is entry to the 
 * program.
 * @author  Pulkit Saxena
 * @version 1.0
 *  Date 4-AUG-2007
 *
 */
public class MainAnchor {

	/**
	 * This method is the entry to the program
	 * and calls an onject of WelcomeForm 
	 * @param args
	 */
	public static void main(String[] args) {
			
	  WelcomeForm welcomeForm = new WelcomeForm("Issue Log Anchor");
      try
      {
       Thread.sleep(1500);
    }catch(Exception e){};
      
     welcomeForm.showGui();
      }
        
    }



