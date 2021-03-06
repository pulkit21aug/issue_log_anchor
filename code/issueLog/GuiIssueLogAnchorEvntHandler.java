/**
 * This java file consists a class which handles the 
 * event geerated by the object of class GuiIssueLogAnchor
 * @author Pulkit Saxena
 * @version 1.0
 * Date 5-Aug-2007
 */
package issueLog;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.Properties;
import java.text.SimpleDateFormat;

//impoeting api
import com.moyosoft.connector.com.ComponentObjectModelException;
import com.moyosoft.connector.exception.LibraryNotFoundException;
import com.moyosoft.connector.ms.outlook.Outlook;
import com.moyosoft.connector.ms.outlook.folder.FolderType;
import com.moyosoft.connector.ms.outlook.folder.OutlookFolder;
import com.moyosoft.connector.ms.outlook.item.ItemType;
import com.moyosoft.connector.ms.outlook.mail.OutlookMail;

/**
 * This class handles the event generated by choosing the 
 * respective menu items of the class GuiIssueLogAnchor
 * @author Pulkit Saxena
 * @version 1.0
 *
 */
public class GuiIssueLogAnchorEvntHandler implements ActionListener  {
	
GuiIssueLogAnchor objGuiIssueLogAnchor;

//declaring variables to hold report i.e. mail body 
String sendReport; 
StringBuffer sendReportBuff ;
FileReader reader;
BufferedReader fileReaderBuff;
String temp;
String reminder;
StringBuffer reminderBuff;
String temp1;
FileReader readerReminder;
BufferedReader filereaderReminderBuff;

//declaring variables for sending report by mail
String reportManager;
String issueAnchor;	
String reciepentsList;

	/**
	 * This is counstructor for the class
	 * @param objGuiIssueLogAnchor
	 */
	public 	GuiIssueLogAnchorEvntHandler(GuiIssueLogAnchor objGuiIssueLogAnchor)
	{
	this.objGuiIssueLogAnchor =objGuiIssueLogAnchor;
	
	
	
	}

	
/**
 * This method handles the event generated by the
 * object  
 * @return void
 * @param ae -ActionEvent ae
 */

public void actionPerformed(ActionEvent ae)
{
	 //to recognise who created event
	 String arg=(String)ae.getActionCommand();
   
     //hanlding event for menuItem New
	 if(arg.equals("New")){
		 GuiIssueLogAnchor objGuiIssueLogAnchor = new GuiIssueLogAnchor ("Java-Issue Log Anchor"); 
		 
	 }

	 //handling event for menu item Exit
	 else if(arg.equals("Exit")){
		 System.exit(0);
	 }

	 //handling event for menu item Send Manual Report
	 else if(arg.equals("Send Manual Report")){
		 
    
	 //invoking to show report in a form	
	GuiIssueReport objGuiIssueReport = new GuiIssueReport("Issue Log Report");
	objGuiIssueReport.showForm();
	 
	 //opening outlook 
	 
	 try {
	   		String cmd ="cmd.exe /c start ";
	   				 
	   	    String mailList ="mailto:"+GuiIssueReport.manager;
	   	  //invoking native method
	   	    Runtime.getRuntime().exec(cmd+mailList);
	   	  
	   		 }catch(Exception e) 
	   		 {
	   			 JOptionPane.showMessageDialog (objGuiIssueLogAnchor,new 
	   		    	  String("Outlook Error "+e)); 	  
	   		 }	  
   		 
	   }
	 
	 //hanlding event for menu item Send Auto Report:-Sending Report to manager
	 //by connecting to outlook 
	 
	 else if(arg.equals("Send Auto Report")){
 		
		 sendReport = new String("");
			sendReportBuff = new StringBuffer("");
	   //retriveing report and storing in String
		 GuiIssueReport objGuiIssueReport = new GuiIssueReport("Issue Log Report");
		 sendReport= objGuiIssueReport.createReport();  
	 
	   try{
	      //reading file Report.txt :-the template for Report
		
		//creating reader object for redaing file
		
		reader = new FileReader("Report.txt");
		fileReaderBuff = new BufferedReader(reader);
		//redaing template file 
		while((temp=fileReaderBuff.readLine())!=null)
		{
			sendReportBuff =sendReportBuff.append(temp);
			sendReportBuff.append("\r\n"); 
		}
	   
	   }catch(IOException e)
	       {
		   JOptionPane.showMessageDialog ( objGuiIssueLogAnchor,new 
                   String("Can not find file "+e));	   
	        }
	 
	   sendReportBuff.append(sendReport);
	  
	   //adding footer to report
	   sendReportBuff.append("\n\n");
	   sendReportBuff.append("Thanks and Regards");
	   
	   
	   try
	   {

    //connection to file database.properties
	Properties props =new Properties();
	FileInputStream in = new FileInputStream("database.properties");
	props.load(in);
	in.close();


 //getting the path from file database.properties
	
	
	 reportManager=props.getProperty("create.manager");
     issueAnchor=props.getProperty("create.anchor");
    	
     if(reportManager==null||reportManager.equals("")||
    		 issueAnchor==null ||issueAnchor.equals(""))
			
	     {
		JOptionPane.showMessageDialog(objGuiIssueLogAnchor,
				"fields not prsent in database.properties file"); 
	     }
	   }catch(IOException e)
	   {
		   JOptionPane.showMessageDialog(objGuiIssueLogAnchor,
			" database.properties file not present");   
	   }	
	   //sending mail by connecting o outlook
	   
	   try
	      {
	         // Outlook application
	         Outlook outlookApplication = new Outlook();

	         // Get the Outbox folder
	         OutlookFolder outbox = outlookApplication.getDefaultFolder(FolderType.OUTBOX);

	         // Create a new mail in the outbox folder
	         OutlookMail mail = (OutlookMail) outbox.createItem(ItemType.MAIL);

	         
	         // Set the subject, destination and contents of the mail
       
	         //formattig of date by formatters form 
	    		SimpleDateFormat form=new SimpleDateFormat("MMddyy");

          //Date instance created
	    		java.util.Date dt=new java.util.Date();

          //formating date instance dt,storing in variables 

	    	String subDate=form.format(dt);
	        String subject ="Issue_Log_Report_"+subDate;
	    	 mail.setSubject(subject);
	         mail.setTo(reportManager);
	         
	         //creating mail body
           //final report i.e. mail body 
	         sendReportBuff.append("\n");
	         sendReportBuff.append(issueAnchor);
	         sendReport =sendReportBuff.toString();
	  	   
	         mail.setBody(sendReport);

	         // Send the mail
	         mail.send();

	         // Dispose the library
	         outlookApplication.dispose();
	      }
	      catch(ComponentObjectModelException ex)
	      {
	    	  JOptionPane.showMessageDialog(objGuiIssueLogAnchor,"COM error has occured: "+ex);
	         ex.printStackTrace();
	      }
	      catch(LibraryNotFoundException ex)
	      {
	         // If this error occurs, verify the file 'moyocore.dll' is present
	         // in java.library.path
	    	  JOptionPane.showMessageDialog(objGuiIssueLogAnchor,"The Java Outlook Library has not been found.");
	         ex.printStackTrace();
	      }
	 
	 
	  }
	 
	 else if(arg.equals("Generate Report"))
    	  {
		 GuiIssueReport objGuiIssueReport = new GuiIssueReport("Issue Log Report");
		 objGuiIssueReport.showForm();
	    }

	 else if(arg.equals("Send Reminder"))
	  {
		    reminder = new String("");
			reminderBuff = new StringBuffer("");
		 //reading reminder file
		 try{
		      //reading file Report.txt :-the template for Report
			
			//creating reader object for redaing file
			
			 readerReminder = new FileReader("Reminder.txt");
			 filereaderReminderBuff = new BufferedReader(readerReminder);
			//redaing template file 
			while((temp1=filereaderReminderBuff.readLine())!=null)
			{
				reminderBuff =reminderBuff.append(temp1);
				reminderBuff.append("\r\n"); 
			}
		   
		   }catch(IOException e)
		       {
			   JOptionPane.showMessageDialog ( objGuiIssueLogAnchor,new 
	                   String("Can not find reminder file "+e));	   
		        }
		 
	      
	  
	  
	    //reading database.properties file for finding recipients list
	  
	      try
		   {

	    //connection to file database.properties
		Properties props =new Properties();
		FileInputStream in = new FileInputStream("database.properties");
		props.load(in);
		in.close();


	 //getting the path from file database.properties
		
		
		 reportManager=props.getProperty("create.manager");
	     issueAnchor=props.getProperty("create.anchor");
	     reciepentsList=props.getProperty("create.list");
	     
	     if(reportManager==null||reportManager.equals("")||
	    		 issueAnchor==null ||issueAnchor.equals("")||
	    		 reciepentsList==null||reciepentsList.equals(""))
				
		     {
			JOptionPane.showMessageDialog(objGuiIssueLogAnchor,
					"fields not prsent in database.properties file"); 
		     }
		   }catch(IOException e)
		   {
			   JOptionPane.showMessageDialog(objGuiIssueLogAnchor,
				" database.properties file not present");   
		   }	
	 
		   try
		      {
		         // Outlook application
		         Outlook outlookApplication = new Outlook();

		         // Get the Outbox folder
		         OutlookFolder outbox = outlookApplication.getDefaultFolder(FolderType.OUTBOX);

		         // Create a new mail in the outbox folder
		         OutlookMail mail = (OutlookMail) outbox.createItem(ItemType.MAIL);

		         
		         // Set the subject, destination and contents of the mail
	       
		      
		    	
		        String subject ="Issue_Log_Reminder";
		    	 mail.setSubject(subject);
		         mail.setTo(reciepentsList);
		    	 mail.setCC(reportManager);
		         
		         //creating mail body
	           //final report i.e. mail body 
		    	 reminderBuff.append("Thanks and Regards");
		    	 reminderBuff.append("\n");
		    	 reminderBuff.append(issueAnchor);
		         reminder =reminderBuff.toString();
		  	   
		         mail.setBody(reminder);

		         // Send the mail
		         mail.send();

		         // Dispose the library
		         outlookApplication.dispose();
		      }
		      catch(ComponentObjectModelException ex)
		      {
		    	  JOptionPane.showMessageDialog(objGuiIssueLogAnchor,"COM error has occured: "+ex);
		         ex.printStackTrace();
		      }
		      catch(LibraryNotFoundException ex)
		      {
		         // If this error occurs, verify the file 'moyocore.dll' is present
		         // in java.library.path
		    	  JOptionPane.showMessageDialog(objGuiIssueLogAnchor,"The Java Outlook Library has not been found.");
		         ex.printStackTrace();
		      }
		 
		 	  
	  
	  }

	 else if(arg.equals("User Manual"))
	    {
		 
		 try {
		   		String cmd ="cmd.exe /c start ";
		   				 
		   	    String sAboutIla ="help.pdf";
		   	  //invoking native method
		   	    Runtime.getRuntime().exec(cmd+sAboutIla);
		   	  
		   		 }catch(IOException e) 
		   		 {
		   			 JOptionPane.showMessageDialog (objGuiIssueLogAnchor,new 
		   		    	  String("File not found /PDF Reader nor found "+e)); 	  
		   		 }	  
		   	 
	     }

}
}