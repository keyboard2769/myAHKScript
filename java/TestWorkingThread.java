/* *
 * TestWorkingThread
 *
 * add describs here
 *
 * (for Processing 2.x core )
 */

//package ;

import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import kosui.ppplocalui.EcFactory;
import kosui.ppplocalui.VcTagger;
import kosui.pppswingui.ScFactory;
import kosui.ppputil.VcConst;
import processing.core.*; 
import static processing.core.PApplet.println;
import processing.data.*; 
import kosui.pppswingui.VcKeeper;
import kosui.ppputil.ViOperatable;

public class TestWorkingThread extends PApplet {
  
  //=== overridden
  
  static volatile int pbRoller;
  
  Keeper herKeeper;
  Worker worker;
  McTranslator translator;
  
  final String typo="ABCDEabcde";
  
  @Override public void setup() {
    
    //pre setting 
    size(320, 240);
    noSmooth();

    EcFactory.ccInit(this);
    
    //-- initiating
    herKeeper=new Keeper();
    worker=new Worker();
    translator=new McTranslator();
    
    //-- binding
    
    //--post setting
    
  }//+++
  
  @Override public void draw(){
    
    //-- pre draw
    background(0);
    pbRoller++;pbRoller&=0x0F;
    
    //-- local loop
    herKeeper.keep();
    
    fill(0xEE);
    text(typo,100,100);
    
    //-- system loop
    VcTagger.ccTag("roll", pbRoller);
    VcTagger.ccStabilize();
    
  }//+++
  
  @Override public void keyPressed(){
    switch(key){
      
      case 't':
        println(translator.ccTranslate("quit"));
        
      break;
        
      case 'j':
        fnBrowseFont();
      break;
      
      case 'f':
        fnBrowseDict();
      break;
      
      case 'q':fsPover();break;
      
      default:break;
    }//..?
  }//+++
  
  //=== operate
  
  void fsPover(){
    
    //-- flushsing or closign
    
    //-- post exit
    println("::exit from main sketch.");
    exit();
  }//+++
  
  //=== utility

  boolean fnIsPressed(char pxKey)
    {return keyPressed && (key==pxKey);}//+++
  
  void fnApplyFont(String pxPath){
    try{
      textFont(loadFont(pxPath));
      textSize(22);
    }catch(Exception e){
      System.err.println("TestWorkingThread.setup()"
        + "::font apply fail:"+e.getMessage());
    }
  }//+++
  
  void fnBrowseDict(){
    SwingUtilities.invokeLater(new Runnable() {
      @Override public void run(){
        String lpPath=ScFactory.ccGetPathByFileChooser('f');
        worker.setPath(lpPath);
        worker.execute();
      }
    });
  }//+++
  
  void fnBrowseFont(){
    SwingUtilities.invokeLater(new Runnable() {
      @Override public void run(){
        String lpPath=ScFactory.ccGetPathByFileChooser('f');
        herKeeper.setCommand("font,"+lpPath);
      }
    });
  }//+++
  
  //=== inner
  
  class Worker extends SwingWorker<Void, Void>{
    
    String cmPath=null;
    
    void setPath(String pxPath){cmPath=pxPath;}

    @Override protected Void doInBackground() throws Exception {
      
      if(cmPath==null){
        return null;
      }
      
      translator.ccLoadFromFile(cmPath);
      
      return null;
    }

    @Override protected void done() {
      herKeeper.setCommand("echo,worker,done");
    }
    
  }//***
  
  class Keeper{
    volatile String cmComand="";
    
    void keep(){
      if(VcConst.ccIsValidString(cmComand)){
        String[] lpDesCommand=cmComand.split(",");
        switch(lpDesCommand[0]){
          
          case "font":
            println("--change font::"+lpDesCommand[1]);
            fnApplyFont(lpDesCommand[1]);
          break;
          
          case "echo":
            println(lpDesCommand);
          break;
          
          default:System.err.println("..keep()::unhandled:"+lpDesCommand[0]);
        }
      }//..?
      setCommand("");
    }//+++
    
    synchronized void setCommand(String pxCommand){cmComand=pxCommand;}//+++
  
  }//***
  
  class McTranslator{
    JSONObject cmData;
    McTranslator(){
      cmData=null;
    }
    //--
    String ccTranslate(String pxInput){
      String lpRes=pxInput;
      if(cmData==null){return lpRes;}
      if(cmData.hasKey(pxInput)){
        lpRes=cmData.getString(pxInput);
      }
      return lpRes;
    }//+++
    void ccLoadFromFile(String pxPath){
      println("--loading on:"+Thread.currentThread().getName());
      if(loadBytes(pxPath)!=null){
        try{
          cmData=loadJSONObject(pxPath);
        }catch(Exception e){
          System.err.println("..failed to load dictionary:"
            + e.getMessage());
          cmData=null;
        }
      }//..?
    }//+++ 
  }//***
  
  //=== entry
  
  static public void main(String[] passedArgs){
    PApplet.main(TestWorkingThread.class.getCanonicalName());
  }//+++
  
}//***eof

/*
  static private volatile String cmCommand = "";
  static private String[] cmParam=null;
  static private final HashMap<String, ViOperatable> OPERATION_MAP
    =new HashMap<String, ViOperatable>();
  
  private VcKeeper() {}//+++ 
  
  //===
  
  synchronized static public void ccSetCommand(String pxCommand) {
    cmCommand = pxCommand;
  }//+++
  
  public static final void ccAddOperation(String pxCommand, ViOperatable pxOperation){
    if(OPERATION_MAP.containsKey(pxCommand)){return;}
    OPERATION_MAP.put(pxCommand, pxOperation);
  }//+++
  
  public static final void ccKeep(){
    if(VcConst.ccIsValidString(cmCommand)){
      cmParam=cmCommand.split(",");
      if(OPERATION_MAP.containsKey(cmParam[0])){
        OPERATION_MAP.get(cmParam[0]).ccOperate(cmParam);
      }else{
        System.err.println("-- kosui.pppswingui.VcKeeper.ccKeep()::"
          + "unhandled_command:"+cmParam[0]); 
      }cmCommand=""; 
    }//..?
  }//+++
  
  public interface ViOperatable{
    void ccOperate(String[] pxParam);
  }//+++

*/


