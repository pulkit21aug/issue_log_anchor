/**
 * Thsi java file consists a class which handles the window closing 
 * event for the object GuiIssueLogAnchor
 * @author Pulkit Saxena
 * @version 1.0
 * Date 4-Aug-2007
 */
package issueLog;
import java.awt.event.*;

/**
 * This clas handles the widow closing event
 * @author Pulkit Saxena
 * @version 1.0
 */
class GuiIssueLogAnchorAdapter extends WindowAdapter{
	GuiIssueLogAnchor objGuiIssueLogAnchor;
	/**
	 * This constructor initialises the componenets
	 * @param objGuiIssueLogAnchor
	 */
	GuiIssueLogAnchorAdapter(GuiIssueLogAnchor objGuiIssueLogAnchor){
		this.objGuiIssueLogAnchor = objGuiIssueLogAnchor;
		
		
	}
 
	public void windowClosing(WindowEvent we)
	 {
		//objGuiIssueLogAnchor.setVisible(false);

        		System.exit(0);
			 
	 }
	
	
}
