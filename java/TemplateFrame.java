<#assign licenseFirst = "/*">
<#assign licensePrefix = " * ">
<#assign licenseLast = " */">
<#include "${project.licensePath}">

<#if package?? && package != "">
package ${package};

</#if>
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author ${user}
 */
public class ${name} {

  public static final String C_V_PATHSEP
   = System.getProperty("file.separator");
  
  public static final String C_V_NEWLINE
   = System.getProperty("line.separator");
  
  public static final String C_V_OS
   = System.getProperty("os.name");
  
  public static final String C_V_PWD
   = System.getProperty("user.dir");
  
  private ${name}(){}//..!
  
  //=== inner
  
  private static final BufferedImage O_ICON
   = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
  
  private static final JFrame O_FRAME
   = new JFrame("${name} v0.01");
  
  private static final JPanel O_CONTENT_PANE
   = new JPanel(new BorderLayout());
  
  private static final JTextArea O_AREA
   = new JTextArea("standby::"+C_V_NEWLINE);
  
  private static final JTextField O_FIELD
   = new JTextField("");
  
  private static final ActionListener O_ACT_LISTENER = new ActionListener() {
    @Override public void actionPerformed(ActionEvent ae) {
      String lpCommand = ae.getActionCommand();
      
      //--
      if(lpCommand.equals("--action-info")){
        ccMessageBox("still_a_test_version");
        return;
      }//..?
      
      //--
      if(lpCommand.equals("--action-quit")){
        System.out.println(
          "pppmain.MainActionManager.actionPerformed()::sys_exit <- 0"
        );System.exit(0);
        return;
      }//..?
      
      //-- unhandled 
      System.err.println("pppmain.MainActionManager.actionPerformed()::"
       + "unhandled_action_command:"+lpCommand);
      
    }//+++
  };
  
  //=== setup
  
  private static void ssSetupIcon(){
    for(int x=0;x<32;x++){for(int y=0;y<32;y++){
      O_ICON.setRGB(x, y, 0xFF339933);
      if(x>y){O_ICON.setRGB(x, y, 0xFFEEEEEE);}
      if(
        (x<=2 || x>=29)||
        (y<=2 || y>=29)
      ){O_ICON.setRGB(x, y, 0xFF111111);}
    }}//..?
  }//+++
  
  private static void ssSetupContentPane(){
    
    //-- component ** area
    O_AREA.setBackground(Color.LIGHT_GRAY);
    O_AREA.setDisabledTextColor(Color.DARK_GRAY);
    O_AREA.setEditable(false);
    O_AREA.setEnabled(false);
    JScrollPane lpCenterPane=new JScrollPane(O_AREA);
    
    //-- component ** field
    O_FIELD.addKeyListener(new KeyListener() {
      @Override public void keyTyped(KeyEvent ke){}//+++
      @Override public void keyPressed(KeyEvent ke){}//+++
      @Override public void keyReleased(KeyEvent ke){
        int lpCharCode=(int)ke.getKeyChar();
        switch(lpCharCode){
          case 0x0A:
            ccStackln("[echo]"+O_FIELD.getText());
            O_FIELD.setText("");
          break;
          default:break;
        }//..?
      }//+++
    });
    
    //-- packing
    O_CONTENT_PANE.add(lpCenterPane,BorderLayout.CENTER);
    O_CONTENT_PANE.add(O_FIELD,BorderLayout.PAGE_END);
    O_CONTENT_PANE.setBorder(BorderFactory.createEtchedBorder());
    
  }//+++
  
  private static void ssSetupFrame() {

    //-- menu ** item
    JMenuItem lpInfoItem = new JMenuItem("Info");
    lpInfoItem.setActionCommand("--action-info");
    lpInfoItem.setMnemonic(KeyEvent.VK_I);
    lpInfoItem.addActionListener(O_ACT_LISTENER);
    
    JMenuItem lpQuitItem = new JMenuItem("Quit");
    lpQuitItem.setActionCommand("--action-quit");
    lpQuitItem.setMnemonic(KeyEvent.VK_Q);
    lpQuitItem.addActionListener(O_ACT_LISTENER);
    
    //-- menu ** bar
    JMenu lpFileMenu=new JMenu("File");
    lpFileMenu.setMnemonic(KeyEvent.VK_F);
    lpFileMenu.add(lpQuitItem);
    
    JMenu lpHelpMenu = new JMenu("Help");
    lpHelpMenu.setMnemonic(KeyEvent.VK_H);
    lpHelpMenu.add(lpInfoItem);
    
    JMenuBar lpMenuBar = new JMenuBar();
    lpMenuBar.add(lpFileMenu);
    lpMenuBar.add(lpHelpMenu);
    
    //-- setup
    ssSetupIcon();
    ssSetupContentPane();
    
    //-- frame ** setup
    O_FRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    O_FRAME.setIconImage(O_ICON);
    O_FRAME.setJMenuBar(lpMenuBar);
    O_FRAME.setContentPane(O_CONTENT_PANE);
    
    //-- frame ** packup
    Point lpOrigin=ccGetScreenInitPoint();
    Dimension lpScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension lpWindowSize = new Dimension(320, 240);
    O_FRAME.setLocation(
      lpOrigin.x+lpScreenSize.width/2-lpWindowSize.width/2,
      lpOrigin.y+lpScreenSize.height/2-lpWindowSize.height/2
    );
    O_FRAME.setPreferredSize(lpWindowSize);
    O_FRAME.setResizable(false);
    O_FRAME.pack();
    O_FRAME.setVisible(true);
    
    //-- post
    ccStackln("on", C_V_OS);
    ccStackln("from", C_V_PWD);
    ccStackln("*** have fun ***");
    
  }//+++
  
  //=== utility
  
  public static final JFrame ccGetFrame(){return O_FRAME;}//+++
  
  public static final void ccStackln(String pxLine){
    ccStackln(pxLine, null);
  }//+++
  
  public static final void ccStackln(String pxTag, Object pxVal){
    if(pxTag==null){return;}
    if(pxVal==null){
      O_AREA.append(pxTag+C_V_NEWLINE);
    }else{
      O_AREA.append(pxTag+":"+pxVal.toString()+C_V_NEWLINE);
    }//..?
    int lpLength = O_AREA.getText().length();
    O_AREA.setSelectionStart(lpLength-1);
    O_AREA.setSelectionEnd(lpLength);
  }//+++
  
  public static final void ccMessageBox(String pxMessage){
    if(SwingUtilities.isEventDispatchThread() && (O_FRAME!=null)){
      JOptionPane.showMessageDialog(O_FRAME,pxMessage);
    }else{System.err.println("[BLOCKED]ccMessageBox()::"+pxMessage);}
  }//+++
  
  public static final boolean ccYesOrNoBox(String pxMessage){
    if(SwingUtilities.isEventDispatchThread() && (O_FRAME!=null)){
      int lpRes=JOptionPane.showConfirmDialog(
        O_FRAME,
        pxMessage,
        O_FRAME!=null?O_FRAME.getTitle():"!!",
        JOptionPane.YES_NO_OPTION
      );
      return lpRes==0;
    }else{
      System.err.println("[BLOCKED]ccMessageBox()::"+pxMessage);
      return false;
    }//..?
  }//+++

  public static final Point ccGetScreenInitPoint(){
    Point lpDummyPoint = null;
    Point lpInitPoint = null;
    for (
      GraphicsDevice lpDevice:
      GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()
    ){
      if (lpDummyPoint == null) {
        lpDummyPoint = 
          lpDevice.getDefaultConfiguration().getBounds().getLocation();
      } else if (lpInitPoint == null) {
        lpInitPoint = 
          lpDevice.getDefaultConfiguration().getBounds().getLocation();
      }//..?
    }//..~
    if (lpInitPoint == null) {lpInitPoint = lpDummyPoint;}
    if (lpInitPoint == null) {lpInitPoint = new Point(0,0);}
    return lpInitPoint;
  }//+++
  
  private static void ssApplyLookAndFeel(int pxIndex, boolean pxRead) {

    //-- pre
    String lpTarget = UIManager.getCrossPlatformLookAndFeelClassName();

    //-- getting
    if (pxIndex >= 0) {
      UIManager.LookAndFeelInfo[] lpInfos
        = UIManager.getInstalledLookAndFeels();
      if (pxRead) {
        System.out.println("--installed lookNfeel: 0->");
        int cnt=0;
        for (UIManager.LookAndFeelInfo it : lpInfos) {
          System.out.print("["+Integer.toString(cnt)+"] ");
          System.out.println(it.getClassName());
          cnt++;
        }//..~
      }//..?
      int lpIndex=pxIndex>(lpInfos.length-1)?lpInfos.length-1:pxIndex;
      lpTarget = lpInfos[lpIndex].getClassName();
    }//..?

    //-- applying
    try {
      UIManager.setLookAndFeel(lpTarget);
    } catch (ClassNotFoundException e) {
      System.err.println(".ccApplyLookAndFeel()::" + e.getMessage());
    } catch (InstantiationException e) {
      System.err.println(".ccApplyLookAndFeel()::" + e.getMessage());
    } catch (IllegalAccessException e) {
      System.err.println(".ccApplyLookAndFeel()::" + e.getMessage());
    } catch (UnsupportedLookAndFeelException e) {
      System.err.println(".ccApplyLookAndFeel()::" + e.getMessage());
    }//..%

  }//+++

  //=== entry

  public static void main(String[] args) {
    System.out.println("${name}.main()::activate");
    ssApplyLookAndFeel(4, false);
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      public void run() {ssSetupFrame();}//+++
    });
    System.out.println("${name}.main()::over");
  }//++!

}//***eof
