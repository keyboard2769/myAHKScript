package ppptest;

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

public final class TemplateFrame{
  
  public static final String C_V_PATHSEP
   = System.getProperty("file.separator");
  
  public static final String C_V_NEWLINE
   = System.getProperty("line.separator");
  
  public static final String C_V_OS
   = System.getProperty("os.name");
  
  public static final String C_V_PWD
   = System.getProperty("user.dir");
  
  private static JFrame pbFrame;
  
  private static JTextArea pbArea;
  
  private static JTextField pbField;
  
  private TemplateFrame(){};
  
  //=== construction
  
  public static final JFrame ccGetFrame(){return pbFrame;}//+++
  
  private static void ssSetupFrame() {

    //-- menu bar   
    JMenuItem lpInfoItem = new JMenuItem("info");
    lpInfoItem.setActionCommand("--action-info");
    lpInfoItem.setMnemonic(KeyEvent.VK_I);
    lpInfoItem.addActionListener(O_ACT_MNG);
    
    JMenuItem lpQuitItem = new JMenuItem("quit");
    lpQuitItem.setActionCommand("--action-quit");
    lpQuitItem.setMnemonic(KeyEvent.VK_Q);
    lpQuitItem.addActionListener(O_ACT_MNG);
    
    JMenu lpFileMenu=new JMenu("File");
    lpFileMenu.setMnemonic(KeyEvent.VK_F);
    lpFileMenu.add(lpQuitItem);
    
    JMenu lpHelpMenu = new JMenu("Help");
    lpHelpMenu.setMnemonic(KeyEvent.VK_H);
    lpHelpMenu.add(lpInfoItem);
    
    JMenuBar lpMenuBar = new JMenuBar();
    lpMenuBar.add(lpFileMenu);
    lpMenuBar.add(lpHelpMenu);
    
    //-- frame
    pbFrame = new JFrame("Unnamed Frame v0.01");
    pbFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    pbFrame.setJMenuBar(lpMenuBar);
    pbFrame.getContentPane().add(ssCreateCenterPane());
    
    //-- frame ** packup
    Point lpOrigin=ccGetScreenInitPoint();
    Dimension lpScreenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension lpWindowSize = new Dimension(320, 240);
    pbFrame.setLocation(
      lpOrigin.x+lpScreenSize.width/2-lpWindowSize.width/2,
      lpOrigin.y+lpScreenSize.height/2-lpWindowSize.height/2
    );
    pbFrame.setPreferredSize(lpWindowSize);
    pbFrame.setResizable(false);
    pbFrame.pack();
    pbFrame.setVisible(true);
    
  }//+++
  
  //=== inner
  
  private static final ActionListener O_ACT_MNG = new ActionListener() {
    @Override public void actionPerformed(ActionEvent ae) {
      String lpCommand = ae.getActionCommand();
    if(lpCommand.equals("--action-info")){
      ccMessageBox("still_a_test_version");
      return;
    }//..?
    
    if(lpCommand.equals("--action-quit")){
      System.out.println(
        "pppmain.MainActionManager.actionPerformed()::sys_exit <- 0"
      );System.exit(0);
      return;
    }//..?
    
    System.err.println("pppmain.MainActionManager.actionPerformed()::"
     + "unhandled_action_command:"+lpCommand);
    }//+++
  };
  
  private static JPanel ssCreateCenterPane(){
    
    //-- component ** area
    pbArea=new JTextArea("::standby"+C_V_NEWLINE);
    pbArea.setBackground(Color.LIGHT_GRAY);
    pbArea.setDisabledTextColor(Color.DARK_GRAY);
    pbArea.setEditable(false);
    pbArea.setEnabled(false);
    JScrollPane lpCenterPane=new JScrollPane(pbArea);
    
    //-- component ** field
    pbField = new JTextField("");
    pbField.addKeyListener(new KeyListener() {
      @Override public void keyTyped(KeyEvent ke) {}
      @Override public void keyPressed(KeyEvent ke) {}
      @Override public void keyReleased(KeyEvent ke) {
        int lpCharCode=(int)ke.getKeyChar();
        switch(lpCharCode){
          case 0x0A:
            ccStackln("[echo]"+pbField.getText());
            pbField.setText("");
            int lpMax=lpCenterPane.getVerticalScrollBar()
              .getModel().getMaximum();
            lpCenterPane.getVerticalScrollBar()
              .getModel().setValue(lpMax);
          break;
          default:break;
        }//..?
      }//+++
    });
    
    //-- packing
    JPanel lpRes=new JPanel(new BorderLayout());
    lpRes.add(lpCenterPane,BorderLayout.CENTER);
    lpRes.add(pbField,BorderLayout.PAGE_END);
    lpRes.setBorder(BorderFactory.createEtchedBorder());
    return lpRes;
    
  }//+++
  
  //=== utility
  
  public static final void ccStackln(String pxLine){
    if(pxLine==null){return;}
    pbArea.append(pxLine+C_V_NEWLINE);
  }//+++
  
  public static final void ccStackln(String pxTag, Object pxVal){
    if(pxTag==null || pxVal==null){return;}
    pbArea.append(pxTag+":"+pxVal.toString()+C_V_NEWLINE);
  }//+++
  
  private static void ssApplyLookAndFeel(int pxIndex, boolean pxRead) {

    String lpTarget = UIManager.getCrossPlatformLookAndFeelClassName();

    //-- getting
    if (pxIndex >= 0) {
      UIManager.LookAndFeelInfo[] lpInfos = UIManager.getInstalledLookAndFeels();
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
      System.err.println("..ScFactory.ccApplyLookAndFeel()::" + e.getMessage());
    } catch (InstantiationException e) {
      System.err.println("..ScFactory.ccApplyLookAndFeel()::" + e.getMessage());
    } catch (IllegalAccessException e) {
      System.err.println("..ScFactory.ccApplyLookAndFeel()::" + e.getMessage());
    } catch (UnsupportedLookAndFeelException e) {
      System.err.println("..ScFactory.ccApplyLookAndFeel()::" + e.getMessage());
    }//..%

  }//+++
  
  public static final void ccMessageBox(String pxMessage){
    if(SwingUtilities.isEventDispatchThread() && (pbFrame!=null)){
      JOptionPane.showMessageDialog(pbFrame,pxMessage);
    }else{System.err.println("[BLOCKED]ccMessageBox()::"+pxMessage);}
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
  
  //=== entry
    
  public static void main(String[] args) {
    System.out.println("TemlateConsoleFrame.main()::activate");
    ssApplyLookAndFeel(4, false);
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
      public void run() {ssSetupFrame();}//+++
    });
    System.out.println("TemlateConsoleFrame.main()::over");
  }//++!
  
}//**eof
