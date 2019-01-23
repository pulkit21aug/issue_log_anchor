/**
 * Thsi java file consists  a class which creates the 
 * GUI for ILA 1.0 and  shows main screen with all the menu items.
 * @author Pulkit Saxena
 * @version 1.0
 * Date 4-AUG-2007
 */  
package issueLog;



import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Properties;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import javax.swing.*;


/**
 * This class creates GUI for ILA 1.0 main form
 * and displays all the menu Items
 * @author Pulkit Saxena
 * @version 1.0
 * Date 4-Aug-2007
 */
public class GuiIssueLogAnchor extends JFrame {
 //declaring variables
	
 Container cnt;	
 JPanel panelMenuBar;
 JPanel panelLabel;
 JLabel label;

 /**
  * This constructor initialises the variables and
  * and creates the form components
  * @param formName
  */
public GuiIssueLogAnchor(String formName) {
   super(formName);

  //creating menu bar
  JMenuBar menuBar = new JMenuBar();
  
  //creating menu 
  JMenu issueLogMenu = new JMenu("IssueLog");
  JMenu reportMenu = new JMenu("Report");
  JMenu reminderMenu = new JMenu("Reminder");
  JMenu helpMenu = new JMenu("Help");
  //creating menu items for IssueLogMenu
  JMenuItem issueLogMenuNew = new JMenuItem("New");
  issueLogMenuNew.setAccelerator(KeyStroke.getKeyStroke('N',
			 Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(),false));
  
  JMenuItem issueLogMenuExit = new JMenuItem("Exit");
  
  //adding menu items to IssueLogMenu
  issueLogMenu.add(issueLogMenuNew);
  issueLogMenu.add(issueLogMenuExit);
  
  
	  //creating menu items for  reportMenu
	   JMenuItem  reportMenuSendManual = new JMenuItem("Send Manual Report");
       JMenuItem  reportMenuSendAuto  = new JMenuItem("Send Auto Report");
       JMenuItem  reportMenuGenerateReport = new JMenuItem("Generate Report");
       
       //adding menu items to reportMenu
       reportMenu.add(reportMenuSendManual);
       reportMenu.add(reportMenuSendAuto);
       reportMenu.add(reportMenuGenerateReport);
       
       //creating menu items for reminderMenu
        JMenuItem reminderMenuSendAuto = new JMenuItem("Send Reminder");
       
       //adding menu items to reminderMenu
      
       reminderMenu.add(reminderMenuSendAuto);
       
       //creating menu items for helpMenu
       
       JMenuItem helpMenuUserManual = new JMenuItem("User Manual");
       helpMenu.add(helpMenuUserManual);
       
       //adding menu to menu bar
       menuBar.add(issueLogMenu);
       menuBar.add(reportMenu);
       menuBar.add(reminderMenu);
       menuBar.add(helpMenu);
       
       //creating panel 
       panelMenuBar = new JPanel(new GridLayout());
       panelMenuBar.add(menuBar);
       panelLabel = new JPanel();
       
       //creatng Label
       label= new JLabel("Issue Log Anchor:-Infosys");
       label.setBackground(Color.pink);
       label.setForeground(Color.MAGENTA);
       panelLabel.add(label);
     
       
       
       
       //getting conatiner
       cnt = this.getContentPane();
       cnt.setLayout(new BorderLayout());
       cnt.add(panelMenuBar,BorderLayout.PAGE_START);
 	   cnt.add(panelLabel,BorderLayout.CENTER);
       
 	   
      //setting look and feel of frame
 	  setSize(300,250);
 	  JFrame.setDefaultLookAndFeelDecorated(true);
 	  setLocationRelativeTo(null);
      // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       this.setVisible(true);    
     
       //creating objects to handel events
       
       GuiIssueLogAnchorEvntHandler  handler = new GuiIssueLogAnchorEvntHandler(this);
       issueLogMenuNew.addActionListener(handler);
       issueLogMenuExit.addActionListener(handler);
       
       
       reportMenuSendManual.addActionListener(handler);
       reportMenuSendAuto.addActionListener(handler);
       reportMenuGenerateReport.addActionListener(handler);
       
      
       reminderMenuSendAuto.addActionListener(handler);
       
       helpMenuUserManual.addActionListener(handler);
       
       
       //creating object to handle window closing event
       GuiIssueLogAnchorAdapter objGuiIssueLogAnchorAdapter  =
    	       new GuiIssueLogAnchorAdapter(this);
       this.addWindowListener(objGuiIssueLogAnchorAdapter);
   
   }
  



}
	
	

