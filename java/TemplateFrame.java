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
import java.util.HashMap;
import javax.swing.AbstractButton;
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
  
  private ${name}(){}//!!!
  
  //=== inner
  
  private static final BufferedImage O_ICON
   = new BufferedImage(32, 32, BufferedImage.TYPE_INT_RGB);
  
  private static final JFrame O_FRAME
   = new JFrame("${name} v0.1.0");
  
  private static final JTextArea O_AREA
   = new JTextArea(">>>"+C_V_NEWLINE);
  
  private static final JTextField O_FIELD
   = new JTextField("");
  
  //=== manager
  
  private static final HashMap<Object,Runnable> O_MAP_OF_ACTION
    = new HashMap<>();
  
  private static final ActionListener O_ACT_LISTENER = new ActionListener() {
    @Override public void actionPerformed(ActionEvent ae) {
      Object lpSource = ae.getSource();
      if(lpSource==null){return;}
      if(!O_MAP_OF_ACTION.containsKey(lpSource)){return;}
      O_MAP_OF_ACTION.get(lpSource).run();
    }//+++
  };//***
  
  private static final KeyListener O_KEY_LISTENER = new KeyListener() {
    @Override public void keyTyped(KeyEvent ke){}//+++
    @Override public void keyPressed(KeyEvent ke){}//+++
    @Override public void keyReleased(KeyEvent ke){
      int lpCharCode=(int)ke.getKeyChar();
      switch(lpCharCode){
        case 0x0A:
          ccWriteln(ccExecute(O_FIELD.getText()));
          O_FIELD.setText("");
        break;
        default:break;
      }//..?
    }//+++
  };//***
  
  //=== action
  
  public static final Runnable O_QUITTING = new Runnable(){
    @Override public void run() {
      int lpCode=0;
      System.out.println("${name}::end_with:"+Integer.toString(lpCode));
      System.exit(lpCode);
    }//+++
  };//***
  
  public static final Runnable O_DISCLAIMING = new Runnable(){
    @Override public void run() {
      ccMessageBox(
        "CAST IN THE NAME OF TEST"
        +C_V_NEWLINE+
        "YA NOT GUILTY"
      );
    }//+++
  };//***
  
  private static final Runnable O_SWING_SETUP = new Runnable(){ 
    @Override public void run() {
      
      //-- menu ** item
      JMenuItem lpInfoItem = new JMenuItem("Info");
      lpInfoItem.setActionCommand("--action-info");
      lpInfoItem.setMnemonic(KeyEvent.VK_I);
      
      JMenuItem lpQuitItem = new JMenuItem("Quit");
      lpQuitItem.setActionCommand("--action-quit");
      lpQuitItem.setMnemonic(KeyEvent.VK_Q);
      
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
      
      //-- shell ** ui
      O_AREA.setBackground(Color.LIGHT_GRAY);
      O_AREA.setDisabledTextColor(Color.DARK_GRAY);
      O_AREA.setEditable(false);
      O_AREA.setEnabled(false);
      JScrollPane lpAreaPane=new JScrollPane(O_AREA);
      O_FIELD.addKeyListener(O_KEY_LISTENER);
      
      //-- shell ** pane
      JPanel lpContentPane = new JPanel(new BorderLayout());
      lpContentPane.setBorder(BorderFactory.createEtchedBorder());
      lpContentPane.add(lpAreaPane,BorderLayout.CENTER);
      lpContentPane.add(O_FIELD,BorderLayout.PAGE_END);
      
      //-- frame ** setup
      O_FRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      O_FRAME.setJMenuBar(lpMenuBar);
      O_FRAME.setContentPane(lpContentPane);
      
      //-- frame ** icon
      ccSetupIcon(O_ICON);
      O_FRAME.setIconImage(O_ICON);
      
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
      
      //-- bind
      ccRegisterAction(lpQuitItem, O_QUITTING);
      ccRegisterAction(lpInfoItem, O_DISCLAIMING);
      ccRegisterAction("quit", O_QUITTING);
      
      //-- post
      ccWriteln("[sys]on", C_V_OS);
      ccWriteln("[sys]from", C_V_PWD);
      ccWriteln("[*** bon appetit ***]");

    }//+++
  };//***
  
  //=== util
  
  static public final boolean ccIsValidString(String pxLine){
    if(pxLine==null){return false;}
    return !pxLine.isEmpty();
  }//+++
  
  public static final void ccWriteln(String pxLine){
    ccWriteln(pxLine, null);
  }//+++
  
  public static final void ccWriteln(String pxTag, Object pxVal){
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
  
  public static final
  void ccRegisterAction(AbstractButton pxButton, Runnable pxAction){
    if(pxButton==null){return;}
    if(pxAction==null){return;}
    if(O_MAP_OF_ACTION.containsKey(pxButton)){return;}
    pxButton.addActionListener(O_ACT_LISTENER);
    O_MAP_OF_ACTION.put(pxButton, pxAction);
  }//+++
  
  public static final
  void ccRegisterAction(String pxCommand, Runnable pxAction){
    if(!ccIsValidString(pxCommand)){return;}
    if(pxAction==null){return;}
    if(O_MAP_OF_ACTION.containsKey(pxCommand)){return;}
    O_MAP_OF_ACTION.put(pxCommand, pxAction);
  }//+++
  
  public static final
  String ccExecute(String pxCommand){
    if(!ccIsValidString(pxCommand)){return ">>>";}
    if(O_MAP_OF_ACTION.containsKey(pxCommand)){
      O_MAP_OF_ACTION.get(pxCommand).run();
      return "[accepted]"+pxCommand;
    }else{
      return "[unhandled]"+pxCommand;
    }//..?
  }//+++
  
  //=== ui
  
  public static final void ccSetupIcon(BufferedImage pxImage){
    if(pxImage==null){return;}
    if(pxImage.getWidth()!=32){return;}
    if(pxImage.getHeight()!=32){return;}
    for(int x=0;x<32;x++){for(int y=0;y<32;y++){
      pxImage.setRGB(x, y, 0xFF339933);
      if(x>y){pxImage.setRGB(x, y, 0xFFEEEEEE);}
      if(
        (x<=2 || x>=29)||
        (y<=2 || y>=29)
      ){pxImage.setRGB(x, y, 0xFF111111);}
    }}//..~
  }//+++
  
  public static final void ccMessageBox(String pxMessage){
    if(SwingUtilities.isEventDispatchThread() && (O_FRAME!=null)){
      JOptionPane.showMessageDialog(O_FRAME,pxMessage);
    }else{System.err.println(".ccMessageBox()::"+pxMessage);}
  }//+++
  
  public static final boolean ccYesOrNoBox(String pxMessage){
    if(SwingUtilities.isEventDispatchThread() && (O_FRAME!=null)){
      int lpRes=JOptionPane.showConfirmDialog(
        O_FRAME,
        pxMessage,
        O_FRAME!=null?O_FRAME.getTitle():"<!>",
        JOptionPane.YES_NO_OPTION
      );
      return lpRes==0;
    }else{
      System.err.println(".ccMessageBox()::"+pxMessage);
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
  
  public static void ccApplyLookAndFeel(int pxIndex, boolean pxRead) {

    //-- pre
    String lpTarget = UIManager.getCrossPlatformLookAndFeelClassName();

    //-- getting
    if (pxIndex >= 0) {
      UIManager.LookAndFeelInfo[] lpInfos
        = UIManager.getInstalledLookAndFeels();
      if (pxRead) {
        System.out.println(".ssApplyLookAndFeel::installed lookNfeel: 0->");
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
    } catch (Exception e) {
      System.err.println(".ccApplyLookAndFeel()::" + e.getMessage());
    }//..?

  }//+++

  //=== entry
  
  public static final JFrame ccGetFrame(){return O_FRAME;}//+++

  public static void main(String[] args) {
    System.out.println("${name}.main()::enter");
    ccApplyLookAndFeel(4, false);
    SwingUtilities.invokeLater(O_SWING_SETUP);
    System.out.println("${name}.main()::exit");
  }//!!!

}//***eof
