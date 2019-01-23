/**
 * This java file consists a class which handles the window closin
 * event for the object of the class GuiIssueReport
 * @author Pulkit Saxena
 * @version 1.0
 * Date 5-Aug-2007
 */
package issueLog;

import java.awt.event.*;

/**
 * This class handles the window cloing event.It extens
 * WindowAdapter class 
 * @author Pulkit Saxena
 *  @version 1.0
 *  Date 5-Aug-2007
 */
public class GuiIssueReportAdapter extends WindowAdapter {

	GuiIssueReport objGuiIssueReport ;
	
	public GuiIssueReportAdapter(GuiIssueReport objGuiIssueReport )
	{
		this.objGuiIssueReport = objGuiIssueReport;
	}
   
	/**
	 * This function handles window closing event 
	 * @param we
	 */
	public void windowClosing(WindowEvent we)
	 {
		objGuiIssueReport.setVisible(false);
	  }
	
	
}
