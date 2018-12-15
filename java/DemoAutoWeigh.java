//package ;

/* <def A="revision"/>
 * # %flag% "file/version" :issue $description
 * - %% "" : $
 * - %% "" : $
 * - %% "" : $
 * <end/>
 *
 * code : _
 * name : 
 * core : Processing 2.x
 * original : NKH653
 *
 */

import processing.core.*;
import processing.data.*;

//import processing.event.*; 
//import processing.opengl.*; 
import java.util.HashMap;
import java.util.ArrayList;
import javax.swing.SwingUtilities;
import kosui.ppplocalui.EcFactory;
//import java.io.File; 
//import java.io.BufferedReader; 
//import java.io.PrintWriter; 
//import java.io.InputStream; 
//import java.io.OutputStream; 
//import java.io.IOException; 

import kosui.ppplocalui.*;
import kosui.ppplogic.ZiTask;
import kosui.ppplogic.ZcPLC;
import kosui.pppswingui.ScFactory;
import kosui.pppswingui.VcKeeper;

public class DemoAutoWeigh extends PApplet{
  
  static final int E_READ_LINE=8;
  
  static final int I_MODIFY_SW=190;
  
  static public int pbRoller=0;

  volatile String pbSwingInputString="<nn>";

  EcBaseCoordinator pbCoordinator;

  UcModelGroup pbModelGroup;

  UcButtonGroup pbButtonGroup;

  UcBookGroup pbBookGroup;

  //=== overridden
  
  @Override public void setup(){

    //-- pre setting 
    size(320, 240);
    noSmooth();
    frameRate(16);
    noStroke();
    textAlign(LEFT, TOP);
    ellipseMode(CENTER);

    //-- static init
    EcFactory.ccInit(this);
    VcTagger.ccInit(this, 7);
    VcAxis.ccInit(this);
    VcAxis.ccFlip();

    //-- instance init
    pbModelGroup=new UcModelGroup(5, 5);

    pbButtonGroup=new UcButtonGroup(
      pbModelGroup.cmPane.ccEndX()+5,
      pbModelGroup.cmPane.ccGetY()
    );

    pbBookGroup=new UcBookGroup(
      pbButtonGroup.cmPane.ccGetX(),
      pbButtonGroup.cmPane.ccEndY()+5
    );

    pbCoordinator=new EcBaseCoordinator();
    pbCoordinator.ccAddGroup(pbModelGroup);
    pbCoordinator.ccAddGroup(pbButtonGroup);
    pbCoordinator.ccAddGroup(pbBookGroup);

    //-- post setting
    println("--done setup");
  }//+++

  @Override public void draw(){

    //-- pre drawing
    background(0);
    pbRoller++;
    pbRoller&=0x0F;
    
    //-- line keeping
    switch(VcKeeper.ccGetCommand()){
      case E_READ_LINE:
        println(VcKeeper.ccGetTokens(","));
        VcKeeper.ccClear();
        break;
      default:break;
    }//..?

    //-- updating
    pbCoordinator.ccUpdate();

    //-- tagging
    VcTagger.ccTag("roller", pbRoller);
    VcTagger.ccStabilize();

    //-- rulling
    VcAxis.ccUpdate();

  }//+++

  @Override public void keyPressed(){
    switch(key){

      //-- triiger
      case 'm': VcAxis.ccFlip();
        break;

      case '.': VcAxis.ccSetAnchor(mouseX, mouseY);
        break;

      //-- system 
      case 'q': fsPover();
        break;
      default: break;
    }
  }//+++

  @Override public void mousePressed(){
    println(nf(
      pbCoordinator.ccGetMouseOverID(), 2
    ));
    
    switch(pbCoordinator.ccGetMouseOverID()){
      
      case I_MODIFY_SW:
        fsReadInput();
        break;
      
      default:break;
    }//..?
    
  }//+++

  //=== operate
  
  void fsPover(){
    //-- flushing

    //-- defualt
    println("--exiting from PApplet");
    exit();
  }//+++

  //=== utility
  
  void fsReadInput(){
    SwingUtilities.invokeLater(new Runnable(){
      @Override public void run(){
        String lpInputString=ScFactory.
          ccGetStringFromInputBox(
            "input batck plan(%number%,%ton%,%batch%):",
            "0,0,0"
          );
        VcKeeper.ccSet(E_READ_LINE, lpInputString);
      }
    });
  }//+++

  //=== extra
  
  class EcLable extends EcRect implements EiUpdatable{

    String cmText;

    EcLable(String pxText){
      cmText=pxText;
    }//++!

    @Override public void ccUpdate(){
      fill(0xEE);
      text(cmText, cmX, cmY);
    }//+++

  }//***

  class EcHopperShape extends EcShape{

    final int cmCut=12;

    @Override public void ccUpdate(){

      fill(cmBaseColor);
      rect(cmX, cmY, cmW, cmH);
      quad(
        cmX, cmY+cmH,
        cmX+cmW, cmY+cmH,
        cmX+cmW-cmCut, cmY+cmH+cmCut,
        cmX+cmCut, cmY+cmH+cmCut
      );

      fill(0xEE);
      text("mixer", cmX+3, cmY+3);

    }//+++

  }//***

  //=== inner ** ui
  
  class UcModelGroup implements EiGroup{

    EcPane cmPane;

    EcGauge cmAgGauge[];

    EcButton cmAgGateSW[];

    EcHopperShape cmMixer;

    EcButton cmMixerGateSW;

    EcTextBox cmAgCellBox;

    EcTextBox cmAgTargetBox;

    public UcModelGroup(int pxX, int pxY){

      cmPane=new EcPane();
      cmPane.ccSetTitle("model");
      cmPane.ccSetLocation(pxX, pxY);

      cmAgGauge=new EcGauge[5];
      for(int i=0; i<5; i++){
        cmAgGauge[i]=EcFactory.ccCreateGauge("AG"+nf(i, 1), true, true, 16, 24);
        cmAgGauge[i].ccSetNameAlign('x');
      }

      cmAgGauge[1].ccSetLocation(cmPane, 45, 22);
      cmAgGauge[2].ccSetLocation(cmAgGauge[1], 2, 0);
      cmAgGauge[3].ccSetLocation(cmAgGauge[2], 2, 0);
      cmAgGauge[4].ccSetLocation(cmAgGauge[3], 2, 0);
      cmAgGauge[0].ccSetLocation(cmAgGauge[2], 0, 20);

      cmAgGauge[0].ccSetSize(-1, 55);
      cmAgGauge[0].ccSetEndPoint(cmAgGauge[3].ccEndX(), -1);

      cmAgGateSW=new EcButton[5];
      for(int i=0; i<5; i++){
        cmAgGateSW[i]=EcFactory.ccCreateButton("AG"+nf(i, 1), 150+i);
        cmAgGateSW[i].ccSetText("+");
        cmAgGateSW[i].ccSetSize(16, 16);
        cmAgGateSW[i].ccSetLocation(cmAgGauge[i], 0, 2);
      }
      cmAgGateSW[0].ccSetText("DD");
      cmAgGateSW[0].ccSetEndPoint(cmAgGauge[0].ccEndX(), -1);

      cmMixer=new EcHopperShape();
      cmMixer.ccSetLocation(cmAgGauge[1], 0, 100);
      cmMixer.ccSetSize(48, 24);
      cmMixer.ccSetEndPoint(cmAgGauge[4].ccEndX(), -1);

      cmMixerGateSW=EcFactory.ccCreateButton("mg", 160);
      cmMixerGateSW.ccSetText("DD");
      cmMixerGateSW.ccSetLocation(
        cmMixer.ccGetX()+cmMixer.cmCut,
        cmMixer.ccEndY()+cmMixer.cmCut+2
      );
      cmMixerGateSW.ccSetEndPoint(cmMixer.ccEndX()-cmMixer.cmCut, -1);

      cmAgCellBox=EcFactory.ccCreateBox("0000 kg", "ag-c", 'x');
      cmAgCellBox.ccSetSize();
      cmAgTargetBox=EcFactory.ccCreateBox("0000 kg", "ag-t", 'x');
      cmAgTargetBox.ccSetSize();

      cmAgTargetBox.ccSetLocation(cmAgGauge[0], 15, 5);
      cmAgCellBox.ccSetLocation(cmAgTargetBox, 0, 2);

      cmPane.ccSetSize(170, 220);

    }//++!

    @Override public ArrayList<EcElement> ccGiveElementList(){
      ArrayList<EcElement> lpRes=new ArrayList<>();
      for(EcGauge it : cmAgGauge){
        lpRes.add(it);
      }
      for(EcButton it : cmAgGateSW){
        lpRes.add(it);
      }
      lpRes.add(cmMixerGateSW);
      lpRes.add(cmAgTargetBox);
      lpRes.add(cmAgCellBox);
      return lpRes;
    }//+++

    @Override public ArrayList<EiUpdatable> ccGiveShapeList(){
      ArrayList<EiUpdatable> lpRes=new ArrayList<>();
      lpRes.add(cmPane);
      lpRes.add(cmMixer);
      return lpRes;
    }//+++

  }//***

  class UcButtonGroup implements EiGroup{

    EcPane cmPane;

    EcButton cmStartButton,
      cmStopButton,
      cmResumeButton,
      cmCancelButton;//--

    public UcButtonGroup(int pxX, int pxY){

      cmPane=new EcPane();
      cmPane.ccSetTitle("operate");
      cmPane.ccSetLocation(pxX, pxY);

      cmStartButton=EcFactory.ccCreateButton("start", 111);
      cmStartButton.ccSetLocation(cmPane, 5, 22);
      cmStartButton.ccSetSize();

      cmStopButton=EcFactory.ccCreateButton("stop", 112);
      cmStopButton.ccSetLocation(cmStartButton, 5, 0);
      cmStopButton.ccSetSize();

      cmResumeButton=EcFactory.ccCreateButton("resume", 113);
      cmResumeButton.ccSetLocation(cmStartButton, 0, 5);
      cmResumeButton.ccSetSize();

      cmCancelButton=EcFactory.ccCreateButton("cancel", 114);
      cmCancelButton.ccSetLocation(cmResumeButton, 5, 0);
      cmCancelButton.ccSetSize();

      cmPane.ccSetEndPoint(
        cmCancelButton.ccEndX()+10,
        cmCancelButton.ccEndY()+10
      );

    }//++!

    @Override public ArrayList<EcElement> ccGiveElementList(){
      ArrayList<EcElement> lpRes=new ArrayList<>();
      lpRes.add(cmStartButton);
      lpRes.add(cmStopButton);
      lpRes.add(cmResumeButton);
      lpRes.add(cmCancelButton);
      return lpRes;
    }//+++

    @Override public ArrayList<EiUpdatable> ccGiveShapeList(){
      ArrayList<EiUpdatable> lpRes=new ArrayList<>();
      lpRes.add(cmPane);
      return lpRes;
    }//+++

  }//***

  class UcBookGroup implements EiGroup{

    EcPane cmPane;

    EcLable cmAnchor;
    
    EcButton cmModifySW;

    EcTextBox[] cmBatchBox;

    EcTextBox[] cmTonBox;

    public UcBookGroup(int pxX, int pxY){
      cmPane=EcFactory.ccCreatePane("box", pxX, pxY, 77, 77);

      cmAnchor=new EcLable(">");
      cmAnchor.ccSetLocation(cmPane, 5, 22);
      cmAnchor.ccSetSize(16, 8);

      cmTonBox=new EcTextBox[3];
      for(int i=0; i<3; i++){
        cmTonBox[i]=EcFactory.ccCreateBox("0000 t");
      }
      cmTonBox[0].ccSetLocation(cmAnchor, 5, 0);
      cmTonBox[1].ccSetLocation(cmTonBox[0], 0, 3);
      cmTonBox[2].ccSetLocation(cmTonBox[1], 0, 3);

      cmBatchBox=new EcTextBox[3];
      for(int i=0; i<3; i++){
        cmBatchBox[i]=EcFactory.ccCreateBox("000 b");
      }
      cmBatchBox[0].ccSetLocation(cmTonBox[0], 5, 0);
      cmBatchBox[1].ccSetLocation(cmBatchBox[0], 0, 3);
      cmBatchBox[2].ccSetLocation(cmBatchBox[1], 0, 3);
      
      cmModifySW=EcFactory.ccCreateButton("modify", I_MODIFY_SW);
      cmModifySW.ccSetSize();
      cmModifySW.ccSetLocation(cmBatchBox[2], 0, 6);

      cmPane.ccSetEndPoint(
        cmModifySW.ccEndX()+8,
        cmModifySW.ccEndY()+8
      );

    }//++!

    @Override public ArrayList<EcElement> ccGiveElementList(){
      ArrayList<EcElement> lpRes=new ArrayList<>();
      for(EcTextBox it : cmTonBox){
        lpRes.add(it);
      }
      for(EcTextBox it : cmBatchBox){
        lpRes.add(it);
      }
      lpRes.add(cmModifySW);
      return lpRes;
    }//+++

    @Override public ArrayList<EiUpdatable> ccGiveShapeList(){
      ArrayList<EiUpdatable> lpRes=new ArrayList<>();
      lpRes.add(cmPane);
      lpRes.add(cmAnchor);
      return lpRes;
    }//+++

  }//***
  
  //=== inner ** behind
  
  class McBook{
    int cmTon=0;
    int cmBatch=0;
  }//***
  
  class McRecipeManager{
    
    int ccGetValue(int pxRecipeNumber, int pxKindNumber){
      //[]::we shall clean this later..you know what i mean
      return pxKindNumber*11;
    }//+++
    
  }//***
  
  class ZcCell implements ZiTask{
    
    private final int CHARGE_SPEED=2;
    private final int DISCHARGE_SPEED=2;
    
    int cmAD=398;
    boolean cmGate0MV=false; 
    boolean cmGate1MV=false; 
    boolean cmGate2MV=false; 
    boolean cmGate3MV=false; 
    boolean cmGate4MV=false;

    @Override public void ccScan(){;}//+++
    
    @Override public void ccSimulate(){
      if(cmGate1MV||cmGate2MV||
         cmGate3MV||cmGate4MV
        ){
        cmAD+=CHARGE_SPEED;
      }
      if(cmGate0MV){
        cmAD-=DISCHARGE_SPEED;
      }
    }//+++
  
  }//***
  
  class ZcCPUZero {
    
    boolean mnAutoWeighStartSW=false;
    boolean mnAutoWeighStopSW=false;
    boolean mnAutoWeighResumeSW=false;
    boolean mnAutoWeighCancelSW=false;
    
    ZcCell dcCell=new ZcCell();
    
    ZcCPUZero(){
      //[HEAD]::well im still not quitting
    }//++!
    
  }//***

  //=== entry
  
  static public void main(String[] passedArgs){
    String[] appletArgs=new String[]{"DemoAutoWeigh"};
    if(passedArgs!=null){
      PApplet.main(concat(appletArgs, passedArgs));
    }else{
      PApplet.main(appletArgs);
    }
  }//+++

}//***eof
