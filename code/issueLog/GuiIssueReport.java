/**
 * This java file consists a class which creates  a form 
 * to display report for sending report manually.
 * @author Pulkit Saxena
 * @version 1.0
 * Date 5-Aug-2007
 */
package issueLog;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * This class creats issue log report and 
 * displays i in a text area and opens the New outlook window
 * @author Pulkit Saxena
 * @version 1.0
 * Date 5-aug-2007
 */
public class GuiIssueReport extends JFrame {
	
	JTextArea textArea;
	JPanel panelTextArea;
	JPanel panelMenuBar;
	Container cntReport;
    JMenuBar menuBar;
    JMenu editMenu;
    JMenuItem editMenuCut;
    JMenuItem editMenuCopy;
    JMenuItem  editMenuPaste;
    static	String excelPath;
    static String reciepentsList;
    static String manager;
    static String excelFullName;
    static String anchor;
    static String status;
    Connection conn;
    Statement stmnt;
    ResultSet result;
    ResultSet cntOpen;
    ResultSetMetaData metaData;
    boolean bool;
    int numOpen;
    StringBuffer strReport;
    public GuiIssueReport(String formName){
    	super(formName);
    	
    //creating the menu bar
    	menuBar= new JMenuBar();
    	
    //creating menu 
    	JMenu editMenu = new JMenu("Edit");
    	
    //creating menu items to  menu Edit
   	 JMenuItem editMenuCut = new JMenuItem("Cut");
   	 editMenuCut.setAccelerator(KeyStroke.getKeyStroke('X',
   			 Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(),false));
   	 JMenuItem editMenuCopy = new JMenuItem("Copy");
   	 editMenuCopy.setAccelerator(KeyStroke.getKeyStroke('C',
   			 Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(),false));
   	 JMenuItem editMenuPaste = new JMenuItem("Paste");
   	 editMenuPaste.setAccelerator(KeyStroke.getKeyStroke('V',
   			 Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(),false ));

 	
   	 //adding menu items for menu Edit
   	   editMenu.add(editMenuCut);
   	   editMenu.add(editMenuCopy);
   	   editMenu.add(editMenuPaste);
     	   	
   	  //adding editMenu to menuBar
   	  menuBar.add(editMenu);
   	   
   	   //creating panel and text area
	   panelTextArea = new JPanel(new  GridBagLayout());
	 
	   textArea = new JTextArea(699,599);
	   textArea.setEditable(false);
	  //adding scroll pane
	  JScrollPane scrollPane = 
		    new JScrollPane(textArea,
		                    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
		                    JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	    scrollPane.setPreferredSize(new Dimension(450, 110));
	   
	    textArea.setEditable(true);
	    
	    GridBagConstraints c = new GridBagConstraints();
       
	    //adding componets to panel
	    
	  c.fill = GridBagConstraints.BOTH;
      c.weightx = 1.0;
      c.weighty = 1.0;
      panelTextArea.add(scrollPane,c);
	 
    //creating panel to add menubar
	  panelMenuBar = new JPanel(new GridLayout());
	  panelMenuBar.add(menuBar);
    	
	 //adding panels to container

	  cntReport = this.getContentPane();
	  cntReport.setLayout(new BorderLayout());
	  cntReport.add(panelMenuBar,BorderLayout.PAGE_START);
	  cntReport.add(panelTextArea,BorderLayout.CENTER);
	  
	  //setting look and feel of frame
	  setSize(700,600);
	  JFrame.setDefaultLookAndFeelDecorated(true);
	   
      
	   	
    //creating object to handle events
      GuiIssueReportEvntHandler evntHandler =
    	      new GuiIssueReportEvntHandler(this);
      
      editMenuCut.addActionListener(evntHandler);
      editMenuCopy.addActionListener(evntHandler);
      editMenuPaste.addActionListener(evntHandler);
      
      
      GuiIssueReportAdapter adapter = new GuiIssueReportAdapter(this);
      this.addWindowListener(adapter);
      
      
    }

   /**
    * This method generates the issue log report
    * @return the issue log report
    */
    public String createReport() {
    	   
    	   //declaraing local variable to store report
             strReport  =new StringBuffer();
    		 String report = new String("");
    	  
    		 try
    		   {

    	    //connection to file database.properties
    		Properties props =new Properties();
    		FileInputStream in = new FileInputStream("database.properties");
    		props.load(in);
    		in.close();


    	 //getting the path from file database.properties
    		 excelPath = props.getProperty("create.path");
    		 reciepentsList=props.getProperty("create.list");
    		 manager=props.getProperty("create.manager");
    	     anchor=props.getProperty("create.anchor");
    	     status= props.getProperty("create.status");
    		
    	     if(excelPath==null||excelPath.equals("")||reciepentsList==null ||
    				reciepentsList.equals("")||manager==null
    				||manager.equals("")||anchor==null ||anchor.equals("")
    				||status==null ||status.equals(""))
    		     {
    			JOptionPane.showMessageDialog(this,
    					"fields not prsent in database.properties file"); 
    		     }

//    	formattig of date by formatters form 
    		SimpleDateFormat form=new SimpleDateFormat("MMddyy");

//    	Date instance created
    		java.util.Date dt=new java.util.Date();

//    	formating date instance dt,storing in variables 

    		String sSub=form.format(dt);

//    	getting full path and name of projct tracker 
    		excelFullName =excelPath+sSub;
    	  
    		//check for project tracker existence
    		String filePath =excelFullName+".xls";
    	  File fileCheck = new File(filePath);
    		if(!fileCheck.exists())
    	  	{
    			JOptionPane.showMessageDialog(this,
    			" Project Tracker for today's date not found ,system will exit");   	
    		  System.exit(0); 
    	  	}
    		
    		   
    		   
    		   }catch(IOException e)
    		   {
    			   JOptionPane.showMessageDialog(this,
    				" database.properties file not present");   
    		   }	
    	 
    		  //craeting database connection objects 
    		    conn = null;
    	       PreparedStatement stmnt = null; 
    		   PreparedStatement stmOpen = null;
    		 //writing the database query
    	       String query = "select * from [Issue_Tracker$] WHERE Status=? ;";
    	       String countOpen ="select count(*) from [Issue_Tracker$] WHERE Status=?;"; 
    	    try{   
    	     //connecting to back end :-Project Tracker
    	       Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
    	       String myDB =
    	          "jdbc:odbc:Driver={Microsoft Excel Driver (*.xls)};DBQ="+excelFullName+";"
    	           + "DriverID=22;READONLY=false";
    	      
    	       conn = DriverManager.getConnection(myDB,"","");
    	     
    	       //creating prepared statement
    	       stmnt = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
    	       stmOpen = conn.prepareStatement(countOpen);
    	       
    	    //setting parameter for prepared statement
    	      stmnt.setString(1,status);
    	      stmOpen.setString(1,status);
    	      
    	   //creating resultset object for retrieving fields
    	      result  =null;
    	      result=stmnt.executeQuery();
    	    
    	     //creating resultset for counting of issues
    	      cntOpen=null;
    	      bool=stmOpen.execute();
    	      cntOpen=stmOpen.getResultSet();
    	    //getting the num of open issues
    	     
 	    	 while(cntOpen.next()){
 	    	 numOpen=cntOpen.getInt(1);
 	    	 }
 	    	
 	    		
 	    	
 	    		//checking for any open issue
 	    	 if(numOpen==0)
 	    		   {
 	    		strReport.append("There are no open issues");
    	           }
    	     else 
    	     {
    	       	    
    	   String strOpen ="Total num of open issues :"+numOpen;
    	   strReport.append(strOpen);
    	   strReport.append("\n");
    	   strReport.append("The list of open issues is as follows :-");
    	   strReport.append("\n");
    	 
     // generating metadata of database :-Project Tracker 
    	 
    	   metaData = result.getMetaData();
    	    
    	  
    	
    	  //printing metadata
    	   strReport.append(metaData.getColumnLabel(1)+"\t"+
    		 metaData.getColumnLabel(2)+"\t"+metaData.getColumnLabel(4)+
    		 "\t"+metaData.getColumnLabel(5)+"\t\t"+metaData.getColumnLabel(6));
    			 
    			 
    	  strReport.append("\n");
    	     
    	  //retieving values from project tracker
    	  while(result.next()) {
    	    	
    	    	//retrieving report :-columns for  issues	 
    	    	 strReport.append("\n"+result.getString(1)+"\t"+result.getString(2)
    	    			+"\t"+result.getString(4)+"\t"+result.getString(5)+"\t"+result.getString(6) );
    	    		 
    	    	    }
    	     	      
    	   
    	   
    	     } 
 	    	  conn.close();
    	     }catch(Exception e)
    	    {
    	    	JOptionPane.showMessageDialog(this,
    			" database error\t\t"+e);    	
    	    }
    	    
    	    report=strReport.toString();
    	    return report;   
    	    
    }

    /**
     * This method shows the form and displays
     * the issue log report in its textarea.
     * @author Pulkit Saxena
     * @version 1.0
     * Date 7-Aug-2007
     *
     */
    public void showForm(){
        // generating report
         String strReport= this.createReport() ;
         textArea.setText(strReport);
         this.setVisible(true);
}
}
