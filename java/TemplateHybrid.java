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
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import processing.core.PApplet;

/**
 *
 * @author ${user}
 */
public class ${name} extends PApplet {
  
  static private ${name} self;
  
  //=== model
  
  static volatile private int vmRoller=0;
  
  //=== swing ui
  
  //-- swing ** info message
  static final String[] C_DES_MESSAGE = new String[]{
    "## basic usage: \n",
    " 1) bla bla bla.\n",
    " 2) bla bla bla.\n",
    " 3) bla bla bla.\n",
    "## formulas : \n",
    " - blah blah blah.\n",
    " - blah blah blah.\n",
    " - blah blah blah.\n",
    " - blah blah blah.\n",
    " \n",
    "have fun!\n"
  };
  
  static volatile String vmInputDialogBrief = "(?)";
  static volatile String vmInputDialogDefault = "...";
  static volatile String vmLastInputted = "";
  
  //-- swing ** components
  
  static final JFrame O_FRAME = new JFrame();
  
  //-- swing ** action
  
  static final Runnable O_INPUT_GETTING = new Runnable() {
    @Override public void run() {
      if(!SwingUtilities.isEventDispatchThread())
        {System.err.println(".$ OEDT!!");return;}
      vmLastInputted = JOptionPane.showInputDialog(
        O_FRAME,
        vmInputDialogBrief, vmInputDialogDefault
      );
    }//+++
  };//***eof
  
  static final ActionListener O_NOTCH_LISTENER = new ActionListener() {
    @Override public void actionPerformed(ActionEvent ae) {
      Object lpSouce = ae.getSource();

      //if(lpSource.equals(...))
      
      //-- unhandled
      {System.err.println(
        "O_NOTCH_LISTENER::unhandled:"+lpSouce.toString()
      );}//..?

    }//+++
  };//***
  
  static final MouseAdapter O_MOMENTARY_LISTENER = new MouseAdapter() {
    @Override public void mousePressed(MouseEvent me) {
      Object lpSource = me.getSource();

      //if(lpSource.equals(...))
      
      //-- unhandled
      {System.err.println(
        "O_MOMENTARY_LISTENER::unhandled:"+lpSource.toString()
      );}//..?

    }//+++
    @Override public void mouseReleased(MouseEvent me) {

      //-- reset bit

    }//+++
  };//***
  
  static final MouseAdapter O_INPUT_BOX_LISTENER = new MouseAdapter() {
    @Override public void mouseReleased(MouseEvent me) {
      Object lpSource = me.getSource();

      //if(lpSource.equals(...)){vm...=...;O_INPUT_GETTING.Run();

      //-- unhandled
      {System.err.println(
        "O_INPUT_BOX_LISTENER::unhandled:"+lpSource.toString()
      );}//..?

    }//+++
  };//***
  
  //-- swing ** steup
  
  static final Runnable O_SWING_INIT = new Runnable() {
    @Override public void run() {
      
      //-- restyle
      try{
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      }catch(Exception e){
        System.err.println("O_SWING_INIT::"+e.getMessage());
        System.exit(-1);
      }//..?
      
      //-- operate pane
      JPanel lpOperatingPane = new JPanel(new BorderLayout(1,1));
      lpOperatingPane.add(new JButton("=D="),BorderLayout.CENTER);
      
      //-- setting pane
      JPanel lpSettingPane = new JPanel(new BorderLayout(1,1));
      lpSettingPane.add(new JButton("=D="),BorderLayout.CENTER);
      
      //-- info pane
      JPanel lpInfoPane = new JPanel(new BorderLayout(1, 1));
      JTextArea lpInfoArea = new JTextArea("How to use:\n");
      lpInfoArea.setEditable(false);
      lpInfoArea.setEnabled(false);
      for(String it : C_DES_MESSAGE){lpInfoArea.append(it);}
      lpInfoPane.add(new JScrollPane(lpInfoArea));
      
      //-- content pane
      JTabbedPane lpContentPane = new JTabbedPane();
      lpContentPane.add("Operating",lpOperatingPane);
      lpContentPane.add("Setting",lpSettingPane);
      lpContentPane.add("Info",lpInfoPane);
      
      //-- pack
      O_FRAME.setTitle("${name}");
      O_FRAME.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
      O_FRAME.getContentPane().add(lpContentPane);
      O_FRAME.setPreferredSize(new Dimension(320, 240));
      O_FRAME.setResizable(false);
      O_FRAME.setLocation(240,240);
      O_FRAME.pack();
      O_FRAME.setVisible(true);
      
      //-- post
      System.out.println(".O_SWING_INIT $ end");
      
    }//+++
  };//***
  
  static final Runnable O_SWING_FLIP = new Runnable() {
    @Override public void run() {
      boolean lpNow = O_FRAME.isVisible();
      O_FRAME.setVisible(!lpNow);
    }//+++
  };//***
  
  //=== factory
  
  static final void ccSetupInputBox(JTextField pxTarget, int pxW, int pxH){
    pxTarget.setEditable(false);
    pxTarget.setEnabled(false);
    pxTarget.setBackground(Color.LIGHT_GRAY);
    pxTarget.setForeground(Color.DARK_GRAY);
    pxTarget.setDisabledTextColor(Color.DARK_GRAY);
    pxTarget.setHorizontalAlignment(JTextField.RIGHT);
    pxTarget.setPreferredSize(new Dimension(pxW, pxH));
    pxTarget.addMouseListener(O_INPUT_BOX_LISTENER);
  }//+++

  static final void ccSetupMomentarySwitch(JButton pxTarget){
    pxTarget.setFocusPainted(false);
    pxTarget.setBackground(Color.decode("#EEEECC"));
    pxTarget.addMouseListener(O_MOMENTARY_LISTENER);
  }//+++
  
  //=== utility
  
  static final boolean ccIsValidString(String pxLine){
    if(pxLine==null){return false;}
    return !pxLine.isEmpty();
  }//+++

  static final float ccToSecond(int pxFrameCount){
    float lpAmplified = ((float)pxFrameCount)*100f/16f;
    int lpCeiled = ceil(lpAmplified);return ((float)(lpCeiled))/100f;
  }//+++

  static final int ccToFrameCount(float pxSecond){
    return (int)(pxSecond*16f);
  }//+++
  
  int ccGetCenterX(Rectangle pxTarget){
    return pxTarget.x+pxTarget.width/2;
  }//+++

  int ccGetCenterY(Rectangle pxTarget){
    return pxTarget.y+pxTarget.height/2;
  }//+++

  int ccGetEndX(Rectangle pxTarget){
    return pxTarget.x + pxTarget.width;
  }//+++

  int ccGetEndY(Rectangle pxTarget){
    return pxTarget.y + pxTarget.height;
  }//+++
  
  void ccFollowH(Rectangle pxSelf, Rectangle pxTarget, int pxOffset){
    pxSelf.x=ccGetEndX(pxTarget)+pxOffset;
    pxSelf.y=pxTarget.y;
  }//+++
  
  void ccFollowV(Rectangle pxSelf, Rectangle pxTarget, int pxOffset){
    pxSelf.x=pxTarget.x;
    pxSelf.y=ccGetEndY(pxTarget)+pxOffset;
  }//+++
  
  void ccFollowE(Rectangle pxSelf, Rectangle pxTarget, int pxOffset){
    pxSelf.x=ccGetEndX(pxTarget)+pxOffset;
    pxSelf.y=ccGetCenterY(pxTarget) - pxSelf.height/2;
  }//+++
  
  void ccFollowS(Rectangle pxSelf, Rectangle pxTarget, int pxOffset){
    pxSelf.x=ccGetCenterX(pxTarget) - pxSelf.width/2;
    pxSelf.y=ccGetEndY(pxTarget)+pxOffset;
  }//+++
  
  void ccDrawLineH(int pxY){
    line(0,pxY,width,pxY);
  }//+++
  
  void ccDrawLineV(int pxX){
    line(pxX,0,pxX,height);
  }//+++
  
  void ccSurroundText(String pxText, int pxX, int pxY){
    if(pxText==null){return;}
    final int lpMargin = 2;
    int lpWidth = ceil(textWidth(pxText)) + lpMargin*2;
    int lpReturnCount = 1;
    for(char it : pxText.toCharArray()){
      if(it == '\r' || it == '\n'){lpReturnCount++;}
    }//..~
    int lpHeight = ceil(textAscent()+textDescent())*lpReturnCount + lpMargin*2;
    pushStyle();
    {
      fill(0x55555555);
      stroke(0xFFAAAAAA);
      rect(pxX, pxY, lpWidth, lpHeight);
      fill(0xFFEEEEEE);
      text(pxText, pxX+lpMargin, pxY+lpMargin);
    }
    popStyle();
  }//+++
  
  //=== overridden
  
  @Override public void setup() {

    //-- pre
    size(320, 240);
    noSmooth();
    self=this;
    
    //-- replace
    frameRate(16);
    textAlign(LEFT,TOP);
    ellipseMode(CENTER);
    noStroke();
    frame.setTitle("${name}");

    //-- swing ui **
    SwingUtilities.invokeLater(O_SWING_INIT);
    
    //-- local ui **
    
    //-- init ** 
    
    //-- post
    println(".setup() $ end");
    
  }//+++
  
  @Override public void draw() { 

    //-- pre
    vmRoller++;vmRoller&=0x0F;

    //-- update
    background(0);
    
    //-- update **
    
    //-- update **
    fill(0xEE);
    text(nf(vmRoller,2),5,5);
    
    //-- tag

  }//+++
  
  @Override public void keyPressed() {
    switch(key){
      case 'q':exit();break;
      default:break;
    }//..?
  }//+++
  
  @Override public void mousePressed() {
    switch (mouseButton) {
      case LEFT:return;
      case RIGHT:SwingUtilities.invokeLater(O_SWING_FLIP);break;
      default:break; //+++
    }//..?
  }//+++
  
  //=== entry
  
  static public void main(String[] passedArgs) {
    PApplet.main(${name}.class.getCanonicalName());
  }//+++

}//***eof
