/*  *
 * modification of swing grid bag layout exsample
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

public class swingGridBagLayoutMOD {

  final static boolean
    C_SHOULD_FILL=false,
    C_SHOULD_WEIGHT_X=false,
    C_RIGHT_TO_LEFT=false
  ;//...

  public static void ssInitContent(Container pxPane){
    
    pxPane.setLayout(new GridBagLayout());
    GridBagConstraints lpConstraints=ccCreateBag();
    
    //-- 
    JButton 
      lpButtonA=new JButton("A"),
      lpButtonB=new JButton("B"),
      lpButtonC=new JButton("C"),
      lpButtonLong=new JButton("LONG"),
      lpButtonQuit=new JButton("quit")
    ;//+++
    lpButtonQuit.setMnemonic('q');
    lpButtonQuit.addActionListener(new ActionListener() {
      @Override public void actionPerformed(ActionEvent pxAe){
        System.out.println("--ExcerciseGridBagLayout::quitButton:exit");
        System.exit(0);
      }
    });
    
    //--
    ccConfigBagGrid(lpConstraints, 0, 0, 1, 1);
    pxPane.add(lpButtonA, lpConstraints);
    
    //--
    ccConfigBagGrid(lpConstraints, 1, 0, 3, 1);
    pxPane.add(lpButtonB, lpConstraints);

    //--
    ccConfigBagGrid(lpConstraints, 4, 0, 1, 1);
    pxPane.add(lpButtonC, lpConstraints);

    //--
    ccConfigBagGrid(lpConstraints, 0, 1, 4, 1);
    ccConfigBagPadding(lpConstraints, 20,40);
    pxPane.add(lpButtonLong, lpConstraints);

    
    //--
    ccConfigBagGrid(lpConstraints, 4, 1, 1, 1);
    ccConfigBagPadding(lpConstraints,20,40);
    pxPane.add(lpButtonQuit, lpConstraints);
  }//+++
  
  //===
    
  public static GridBagConstraints ccCreateBag(){
    GridBagConstraints lpRes=new GridBagConstraints();
    //lpRes.weightx=0.5;
    //lpRes.weighty=0.5;
    lpRes.fill=GridBagConstraints.HORIZONTAL;
    //lpRes.anchor=GridBagConstraints.HORIZONTAL;
    lpRes.insets.set(2, 2, 2, 2);
    return lpRes;
  }//+++
  
  public static void ccConfigBagGrid(
    GridBagConstraints pxTarget,
    int pxX, int pxY, int pxW, int pxH
  ){
    pxTarget.gridx=pxX;
    pxTarget.gridy=pxY;
    pxTarget.gridwidth=pxW;
    pxTarget.gridheight=pxH;
  }//+++
  
    
  public static void ccConfigBagPadding(
    GridBagConstraints pxTarget, int pxW, int pxH
  ){
    pxTarget.ipadx=pxW;
    pxTarget.ipady=pxH;
  }//+++
  
  
  //===

  private static void ssCreateAndShowGUI(){
    
    //-- main frame
    JFrame lpFrame=new JFrame("Grid Bag");
    lpFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    //-- init
    ssInitContent(lpFrame.getContentPane());

    //-- packing
    lpFrame.pack();
    lpFrame.setLocation(600, 600);
    //lpFrame.setSize(320, 240);
    lpFrame.setVisible(true);
    lpFrame.setResizable(false);
    
  }//+++
  
  private static void ssApplyLookAndFeel(){
    LookAndFeelInfo[] lpDes=UIManager.getInstalledLookAndFeels();
    for(LookAndFeelInfo it : lpDes){
      System.out.println("--installed:"+it.getClassName());
    }//..~
    try{
      UIManager.setLookAndFeel(lpDes[lpDes.length-1].getClassName());
    }catch(Exception e){
      System.err.println("ExcerciseGridBagLayout.main()::"+e.getMessage());
    }
  }//+++
  
  //===
  
  //=== entry
  public static void main(String[] args){
    System.out.println("--ExcerciseGridBagLayout::launch");
    ssApplyLookAndFeel();
    javax.swing.SwingUtilities.invokeLater(new Runnable(){
      @Override public void run(){
        ssCreateAndShowGUI();
      }//+++
    });
    System.out.println("--ExcerciseGridBagLayout::exit");
  }//+++

}//***eof
