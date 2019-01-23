/**
 * This class shows the welcome screen to user displaying 
 * copy right information to user.
 */
package issueLog;
 

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.Timer.*;
import java.util.*;
import java.awt.image.*;

/**
 * This class contains a method to display Welcome Form 
 * to user and contains a method to create progress bar
 * and create an object of class GuiIssueLogAnchor.
 * @author Pulkit Saxena
 * @version 1.0
 * @Date created 4-Aug-07 . 
 *  
 */
public class WelcomeForm extends JWindow  {
    JLabel  jlabel;
	ImageIcon imageIcon;
	JProgressBar  progressbar;
	javax.swing.Timer activityMonitor;
	SimulatedActivity activity;
	//Image    image;
	Container cnt;
   /**
   * this is constructor for and initialiss the value
   * for creating WelcomeForm
   * @param formName is the title of the form
   */	  
  public WelcomeForm(String formName){
     // super(formName);
 	  JFrame.setDefaultLookAndFeelDecorated(true);
      setSize(500,500);
      setLocationRelativeTo(null);
     // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     // setBorder(empty);
      imageIcon = createImageIcon("images/girl.jpg");
      jlabel = new JLabel(imageIcon,JLabel.CENTER);
      jlabel.setBackground(new Color(0xffffdd));
      progressbar = new JProgressBar(0,800);
      progressbar.setStringPainted(true);
      progressbar.setMaximum(800);
      activity = new SimulatedActivity(800);
      new Thread(activity).start();
      activityMonitor = new javax.swing.Timer(100,new ActionListener()
              {
                                                public void  actionPerformed(ActionEvent ae)
                                               {
                                                 int current = activity.getCurrent();
                                                 
                                                 progressbar.setStringPainted(!progressbar.isIndeterminate());
                                                  progressbar.setValue(current);
                                                 if(current==activity.getTarget())
                                                 {activityMonitor.stop();
                                                                                                       

                                                                                                                                                                                  
                                                      }
                                                  }
                                                         }  );
           
  
          activityMonitor.start();
      cnt = this.getContentPane();
      cnt.setLayout(new BorderLayout());
      cnt.add(jlabel,BorderLayout.CENTER);
      cnt.add(progressbar,BorderLayout.PAGE_END);
      this.setVisible(true);
 	         
	}
		
  /*
   * This method  loads the image for dipalying in the
   * frame 
   * @path  path of the image
   * @return an object of image icon on success
   * @return null if the path is invalid
   */
  protected static ImageIcon createImageIcon(String path)
           {
java.net.URL imgURL = WelcomeForm.class.getResource(path);
   if (imgURL != null) 
     {
             return new ImageIcon(imgURL);
     } 
   else
       {
       System.err.println("Couldn't find file: " + path);
       return null;
       }
              
     }
   
  /*
   * This function creates the object of class GuiIssueLogAnchor
   * and creates the GUI of ILA
   * @returns void 
   */
   public void  showGui()
   {
	this.setVisible(false);
	GuiIssueLogAnchor objGuiIssueLogAnchor = 
		new GuiIssueLogAnchor("Java-ILA 1.0");
   }






}

/**
 *This class handles the timer acticivity
 */
class SimulatedActivity implements Runnable
{
   int current;
    int target;  
         public SimulatedActivity(int t)
{ 
  current =0;
target = t;
}

public int getTarget()
{
 return target;
}

public int getCurrent()
{
 return current;
}
public void run()
{
 try
  {
 while (current < target)
{
  Thread.sleep(1);
  current=current+1;
}
}catch(InterruptedException e){System.out.println(e);}

}

}

