/* *
 * DemoPlantIcon
 *
 * add describs here
 *
 * (for Processing 2.x core )
 */

//package ;
import java.util.ArrayList;
import processing.core.*;

import kosui.ppplocalui.*;
import kosui.ppputil.*;
import static processing.core.PConstants.CHORD;
import static processing.core.PConstants.PI;

public class DemoPlantIcon extends PApplet{

  //=== 
  static final int C_SHAPE_METAL=0xFF999999;//supposedly deep gray
  
  static final int C_SHAPE_DUCT=0xFFAAAAAA;

  static final int C_LAMP_MC=0xFFCC0000;//supposedly orange

  static final int C_LAMP_AN=0xFF00CC00;//supposedly green

  static final int C_LAMP_AL=0xFFCC0066;//supposedly red

  static final int C_LAMP_LS=0xFF0066CC;//supposedly blue

  static final int C_LAMP_LV=0xFF00EE00;//supposedly green

  static final int C_BOX_TEXT=0xFFEEEEEE;//supposedly dim gray

  static final int C_BOX_DEGREE=0xFF666666;//supposedly dim gray

  static final int C_FEEDER_STUCK=0xFF666666;//supposedly dim gray

  static final int C_FEEDER_FLOWING=0xFF33CC33;//supposedly green

  //=== public
  static volatile int pbRoller;

  EcHotTower ttt;
  
  //=== overridden
  @Override
  public void setup(){

    size(800, 600);
    noSmooth();
    //-- initiating
    EcFactory.ccInit(this);

    //-- binding
    //-- construction
    ttt=new EcHotTower("nnn", 100, 100, 1590);
      
    
    //--post setting
  }//+++

  @Override
  public void draw(){

    //-- pre draw
    background(0);
    pbRoller++;
    pbRoller&=0x0F;
    boolean lpHalsec=pbRoller<7;
    int lpTestValue=ceil(map(mouseX, 0, width, 1, 99));

    //-- local loop
    
    //    ttt.ccSetIsFull(false);
    //    ttt.ccSetIsClosed(false);
    //    if(lpTestValue<5){ttt.ccSetIsClosed(true);}
    //    if(lpTestValue>95){ttt.ccSetIsFull(true);}
    //    ttt.ccSetDegree(lpTestValue);
    
    
    ttt.ccSetMotorStatus(0, 'a');
    ttt.ccSetMotorStatus(1, 'l');
    ttt.ccSetMotorStatus(2, 'a');
    if(lpHalsec){
      ttt.ccSetMotorStatus(0, 'l');
      ttt.ccSetMotorStatus(1, 'a');
      ttt.ccSetMotorStatus(2, 'l');
    }
    
    ttt.ccSetIsOverFlowGateOpening(lpHalsec);
    ttt.ccSetIsOverSizeGateOpening(!lpHalsec);
    ttt.ccSetIsOverFlowFull(!lpHalsec);
    ttt.ccSetIsOverSizeFull(lpHalsec);
    
    ttt.ccUpdate();
    
    //-- system loop
    //-- tagging
    VcTagger.ccTag("roller", pbRoller);
    VcTagger.ccTag("mx", mouseX);
    VcTagger.ccTag("my", mouseY);
    VcTagger.ccTag("testVal", lpTestValue);
    VcTagger.ccStabilize();

  }//+++

  @Override
  public void keyPressed(){
    switch(key){

      case 'w':
      break;
        
      case 's':
      break;

      case 'a':
        //ttt.ccSetMotorStatus('a');
        
      break;
      
      case 'd':
        //ttt.ccSetMotorStatus('l');
        
      break;

      case 'r':
      break;
        
      case 'f':
        
      break;
      
      //--
      
      case 'j':
      break;
        
      case 'k':
      break;
      
      case 'n':
      break;
        
      case 'm':
      break;
      
      //-- 

      case ' ':
      break;

      case 'q':
        fsPover();
      break;
        
      default:
      break;
      
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
  
  //=== utility ** future static
  
  //[TODO]::transfer to its own class[EcMotorIcon]
  static public void ccSetMotorStatus(
    EcMotorIcon pxTarget, char pxStatus_acnlx
  ){
    switch(pxStatus_acnlx){

      case 'a':
        pxTarget.ccSetIsContacted(true);
        pxTarget.ccSetHasAnswer(true);
        pxTarget.ccSetHasAlarm(false);
      break;

      case 'c':
        pxTarget.ccSetIsContacted(true);
        pxTarget.ccSetHasAnswer(false);
        pxTarget.ccSetHasAlarm(false);
      break;

      case 'n':
        pxTarget.ccSetIsContacted(false);
        pxTarget.ccSetHasAnswer(true);
        pxTarget.ccSetHasAlarm(false);
      break;

      case 'l':
        pxTarget.ccSetIsContacted(false);
        pxTarget.ccSetHasAnswer(false);
        pxTarget.ccSetHasAlarm(true);
      break;

      default:
        pxTarget.ccSetIsContacted(false);
        pxTarget.ccSetHasAnswer(false);
        pxTarget.ccSetHasAlarm(false);
      break;
    }
  }//+++
  
  //=== inner
  //=== inner ** additional localui
  public class EcValueBox extends EcTextBox{

    protected int cmDigit=4;

    protected String cmUnit=" ";

    @Override
    public void ccUpdate(){

      stroke(0xCC);
      ccActFill();
      pbOwner.rect(cmX, cmY, cmW, cmH);
      noStroke();

      pbOwner.fill(cmTextColor);
      pbOwner.textAlign(RIGHT, CENTER);
      pbOwner.text(
        cmText+cmUnit, cmX+cmW,
        EcFactory.C_TEXT_ADJ_Y+ccCenterY()
      );
      pbOwner.textAlign(LEFT, TOP);

      ccDrawName(EcFactory.C_LABEL_TEXT);

    }//+++

    public final void ccSetValue(int pxVal){
      cmText=nf(pxVal, cmDigit);
    }//+++

    public final void ccSetValue(int pxVal, int pxDigit){
      cmDigit=pxDigit;
      cmText=nf(pxVal, cmDigit);
    }//+++

    public final void ccSetValue(float pxVal, int pxDigit){
      cmDigit=pxDigit;
      cmText=nfc(pxVal, cmDigit);
    }//+++

    public final void ccSetUnit(String pxUnit){
      cmUnit=pxUnit;
    }//+++

  }//***
  
  public class EcTriangleLamp extends EcLamp{
    
    private char cmDirection='l';

    @Override
    protected void ccDrawLamp(int pxColor){
      
      pbOwner.fill(pxColor);
      pbOwner.stroke(EcFactory.C_LIT_GRAY);
      switch(cmDirection){
        
        case 'u':
          pbOwner.triangle(
            ccCenterX(), cmY,
            ccEndX(), ccEndY(),
            cmX, ccEndY()
          );
          break;
          
        case 'd':
          pbOwner.triangle(
            cmX, cmY,
            ccEndX(), cmY,
            ccCenterX(), ccEndY()
          );
          break;
        
        case 'l':
          pbOwner.triangle(
            cmX, ccCenterY(),
            ccEndX(), cmY,
            ccEndX(), ccEndY()
          );
          break;
          
        case 'r':
          pbOwner.triangle(
            cmX, cmY,
            ccEndX(), ccCenterY(),
            cmX, ccEndY()
          );
          break;
        
        default:break;
      }
      pbOwner.noStroke();
      
    }//+++
    
    public final void ccSetDirection(char pxMode_udlr){
      cmDirection=pxMode_udlr;  
    }//+++
    
  }//***

  public class EcStatusLamp extends EcLamp{

    private int cmStatus;

    private final ArrayList<Integer> cmStatusColorList;

    public EcStatusLamp(){
      super();
      cmStatus=0;
      cmStatusColorList=new ArrayList<>(16);
      cmStatusColorList.add(EcFactory.C_DIM_GRAY);
      cmStatusColorList.add(EcFactory.C_DIM_RED);
    }//++!

    /**
     * {@inheritDoc }
     */
    @Override
    public void ccUpdate(){

      ccDrawLamp(
        cmStatusColorList.get(cmStatus)
      );
      ccDrawTextAtCenter(EcFactory.C_LAMP_TEXT);
      ccDrawName(EcFactory.C_LABEL_TEXT);

    }//+++

    public final void ccSetStatus(int pxStatus){
      cmStatus=pxStatus;
    }//+++

    public final void ccReplaceStatusColor(int pxIndex, int pxColor){
      cmStatusColorList.set(pxIndex, pxColor);
    }//+++

    public final void ccAddStatusColor(int pxColor){
      cmStatusColorList.add(pxColor);
    }//+++

  }//***
  
  //=== inner ** for plant shape
  public class EcHopperShape extends EcShape{

    protected int cmCut=2;

    private int cmHoldLength=6;

    @Override
    public void ccUpdate(){

      pbOwner.fill(cmBaseColor);
      pbOwner.rect(cmX, cmY, cmW, cmH-cmCut);
      pbOwner.quad(
        cmX, cmY+cmHoldLength,
        cmX+cmW, cmY+cmHoldLength,
        cmX+cmW-cmCut, cmY+cmH,
        cmX+cmCut, cmY+cmH
      );

    }//+++

    public final void ccSetCut(int pxCut){
      if(pxCut>=(cmW/2)){
        cmCut=(cmW/2)-1;
      }else{
        cmCut=pxCut;
      }
      cmHoldLength=cmH-cmCut;
    }//+++

  }//***

  public class EcBelconShape extends EcShape{

    @Override
    public void ccUpdate(){
      pbOwner.fill(cmBaseColor);
      pbOwner.rect(cmX, cmY, cmW, cmH, cmH/2);
    }//++

    public final void ccSetLength(int pxL){
      cmW=pxL;
    }//+++

  }//***
  
  public class EcBlowerShape extends EcShape{
    
    private char cmDirection='r';

    @Override
    public void ccUpdate(){
      pbOwner.fill(cmBaseColor);
      pbOwner.rect(cmX, cmY, cmW, cmH);
      switch(cmDirection){
        case 'u':pbOwner.ellipse(ccEndX(), ccEndY(), cmW*2, cmW*2);break;
        case 'd':pbOwner.ellipse(ccEndX(), cmY, cmW*2, cmW*2);break;
        case 'r':pbOwner.ellipse(cmX, ccEndY(), cmH*2, cmH*2);break;
        case 'l':pbOwner.ellipse(ccEndX(), ccEndY(), cmH*2, cmH*2);break;
        default:break;
      }//+++
    }//+++
    
    public final void ccSetDirection(char pxMode_udlr){
      cmDirection=pxMode_udlr;
    }//+++
    
  }//***
  
  public class EcScrewShape extends EcShape{
    
    private final int //[TODO]::make static
      C_SHEATH=8,
      C_SHAFT=4
    ;
    
    private int cmInCut=0,cmOutCut=0;

    @Override
    public void ccUpdate(){
      
      pbOwner.fill(cmBaseColor);
      pbOwner.rect(cmX,ccCenterY()-C_SHEATH/2,cmW,C_SHEATH);
      pbOwner.rect(cmX-C_SHAFT,ccCenterY()-C_SHAFT/2,cmW+C_SHAFT*2,C_SHAFT);
      if(cmInCut>0){pbOwner.rect(cmX+cmInCut,cmY,C_SHAFT,cmH/2);}
      if(cmOutCut>0){pbOwner.rect(cmX+cmOutCut,ccCenterY(),C_SHAFT,cmH/2);}
    
    }//+++
    
    public final void ccSetCut(int pxIn, int pxOut){
      cmInCut=pxIn>=cmW?0:pxIn;
      cmOutCut=pxOut>=cmW?0:pxOut;
    }//+++
    
  }//***
  
  public class EcElevatorShape extends EcShape{
    
    private int cmCut=4;
    private char cmDirection='l';

    @Override
    public void ccUpdate(){
      
      pbOwner.fill(cmBaseColor);
      pbOwner.rect(cmX,cmY,cmW,cmH);
      switch(cmDirection){
        case 'l':
          pbOwner.triangle(
            cmX, cmY,
            cmX, cmY+cmCut,
            cmX-cmW, cmY+cmCut
          );
          pbOwner.triangle(
            ccEndX(), ccEndY()-cmCut,
            ccEndX()+cmW, ccEndY()-cmCut,
            ccEndX(),ccEndY()
          );
          break;
        case 'r':
          pbOwner.triangle(
            ccEndX(),cmY,
            ccEndX()+cmW,cmY+cmCut,
            ccEndX(),cmY+cmCut
          );
          pbOwner.triangle(
            cmX-cmW,ccEndY()-cmCut,
            cmX,ccEndY()-cmCut,
            cmX,ccEndY()
          );
          break;
        default:break;
      }//..?
    
    }//+++
    
    public final void ccSetCut(int pxVal){cmCut=pxVal;}//+++
    
    public final void ccSetDirection(char pxMode_lr){
      cmDirection=pxMode_lr;
    }//+++
    
  }//***

  //=== inner ** for plant icon
  public class EcMotorIcon extends EcElement{

    private final int // [TODO]::make static
      C_LED_W=4,
      C_LED_H=8
    ;

    private boolean cmMC, cmAN, cmAL;

    public EcMotorIcon(){
      super();

      cmMC=false;
      cmAN=false;
      cmAL=false;

      ccSetSize(C_LED_W*5, C_LED_H+3);

    }//++!

    @Override
    public void ccUpdate(){

      pbOwner.stroke(EcFactory.C_WHITE);
      pbOwner.fill(C_SHAPE_METAL);
      pbOwner.rect(cmX, cmY, cmW, cmH, cmH/2);
      pbOwner.noStroke();

      pbOwner.fill(cmAN?EcFactory.C_GREEN:EcFactory.C_DIM_GRAY);
      pbOwner.rect(cmX+C_LED_W*1, cmY+2, C_LED_W, C_LED_H);
      pbOwner.fill(cmMC?EcFactory.C_ORANGE:EcFactory.C_DIM_GRAY);
      pbOwner.rect(cmX+C_LED_W*2, cmY+2, C_LED_W, C_LED_H);
      pbOwner.fill(cmAL?EcFactory.C_RED:EcFactory.C_DIM_GRAY);
      pbOwner.rect(cmX+C_LED_W*3, cmY+2, C_LED_W, C_LED_H);

    }//+++

    public void ccSetIsContacted(boolean pxStatus){
      cmMC=pxStatus;
    }//+++

    public void ccSetHasAnswer(boolean pxStatus){
      cmAct=pxStatus;
      cmAN=pxStatus;
    }//+++

    public void ccSetHasAlarm(boolean pxStatus){
      cmAL=pxStatus;
    }//+++

  }//***
  
  public class EcPumpIcon extends EcMotorIcon{
    
    private String cmDirection;

    public EcPumpIcon(){
      super();
      cmDirection="-";
      ccSetColor(EcFactory.C_DIM_YELLOW, EcFactory.C_DIM_GRAY);
    }//++!
    
    @Override
    public void ccUpdate(){
      super.ccUpdate();
      
      pbOwner.stroke(EcFactory.C_WHITE);
      ccActFill();
      pbOwner.rect(cmX, cmY+cmH+1, cmW, cmH, cmH/2);
      pbOwner.noStroke();
      pbOwner.fill(EcFactory.C_DARK_GRAY);
      pbOwner.text(cmDirection, cmX+5, cmY+cmH-1);
      
    }//***
    
    public final void ccSetDirection(char pxMode_lrx){
      switch(pxMode_lrx){
        case 'l':cmDirection="<";break;
        case 'r':cmDirection=">";break;
        default:cmDirection="-";break;
      }
    }//+++
    
    public final int ccPipeY(){
      return cmY+cmH+cmH/2;
    }//+++
    
  }//***
  
  public class EcSingleSolenoidIcon extends EcElement{
    
    private final float C_CUT=0.2f;//[TODO]::make static
    private final int C_GAP=2;//[TODO]::make static
    
    protected boolean 
      cmUP,
      cmFAS,cmCAS
    ;
    
    public EcSingleSolenoidIcon(){
      super();
      ccSetSize(16, 16);
    }//++!

    @Override
    public void ccUpdate(){
    
      pbOwner.strokeWeight(C_GAP);{
        pbOwner.stroke(EcFactory.C_LIT_GRAY);
        pbOwner.fill(EcFactory.C_DIM_GRAY);
        pbOwner.ellipse(cmX, cmY, cmW-1, cmH-1);
      }pbOwner.strokeWeight(1);
      pbOwner.noStroke();
      
      pbOwner.fill(cmFAS?EcFactory.C_WHITE:EcFactory.C_DIM_GRAY);
      pbOwner.arc(cmX,cmY,cmW-C_GAP*2,cmW-C_GAP*2,PI+C_CUT,2*PI-C_CUT,CHORD);
      pbOwner.fill(cmCAS?EcFactory.C_RED:EcFactory.C_DIM_GRAY);
      pbOwner.arc(cmX,cmY,cmW-C_GAP*2,cmW-C_GAP*2,C_CUT,PI-C_CUT,CHORD);
      
      pbOwner.fill(cmUP?EcFactory.C_ORANGE:EcFactory.C_LIT_GRAY);
      pbOwner.ellipse(cmX, cmY, cmW-C_GAP*5, cmH-C_GAP*5);
    
    }//+++
    
    public final void ccSetIsOpening(boolean pxStatus){cmUP=pxStatus;}//+++
    public final void ccSetIsFull(boolean pxStatus){cmFAS=pxStatus;}//+++
    public final void ccSetIsClosed(boolean pxStatus){cmCAS=pxStatus;}//+++
    
  }//***
  
  public class EcDoubleSolenoidIcon extends EcElement{
    
    private final int//[TODO]::make static
      C_LED_W=8,
      C_LED_H=2
    ;
    
    protected boolean 
      cmUP,cmDN,
      cmFAS,cmMAS,cmCAS
    ;
    
    public EcDoubleSolenoidIcon(){
      super();
      cmUP=false;
      cmDN=false;
      cmFAS=false;
      cmMAS=false;
      cmCAS=false;
      ccSetSize(C_LED_W+3,C_LED_H*3+C_LED_W*2+6);
    }//++!
    
    @Override
    public void ccUpdate(){
      
      pbOwner.stroke(EcFactory.C_WHITE);
      pbOwner.fill(EcFactory.C_DARK_GRAY);
      pbOwner.rect(cmX, cmY, cmW, cmH, cmH/2);
      pbOwner.noStroke();

      pbOwner.fill(cmFAS?EcFactory.C_GREEN:EcFactory.C_DIM_GRAY);
      pbOwner.rect(cmX+2, cmY+1+C_LED_W+C_LED_H*1, C_LED_W, C_LED_H);
      pbOwner.fill(cmMAS?EcFactory.C_WATER:EcFactory.C_DIM_GRAY);
      pbOwner.rect(cmX+2, cmY+1+C_LED_W+C_LED_H*2, C_LED_W, C_LED_H);
      pbOwner.fill(cmCAS?EcFactory.C_RED:EcFactory.C_DIM_GRAY);
      pbOwner.rect(cmX+2, cmY+1+C_LED_W+C_LED_H*3, C_LED_W, C_LED_H);
      
      pbOwner.fill(cmUP?EcFactory.C_ORANGE:EcFactory.C_DIM_GRAY);
      pbOwner.ellipse(ccCenterX()+1, cmY+1+C_LED_W/2, C_LED_W, C_LED_W);
      pbOwner.fill(cmDN?EcFactory.C_ORANGE:EcFactory.C_DIM_GRAY);
      pbOwner.ellipse(ccCenterX()+1, cmY-1+cmH-C_LED_W/2, C_LED_W, C_LED_W);
      
    }//+++
    
    public final void ccSetIsOpening(boolean pxStatus){cmUP=pxStatus;}//+++
    public final void ccSetIsClosing(boolean pxStatus){cmDN=pxStatus;}//+++
    public final void ccSetIsFull(boolean pxStatus){cmFAS=pxStatus;}//+++
    public final void ccSetIsMiddle(boolean pxStatus){cmMAS=pxStatus;}//+++
    public final void ccSetIsClosed(boolean pxStatus){cmCAS=pxStatus;}//+++

  }//***
  
  public class EcControlMotorIcon extends EcDoubleSolenoidIcon{
    
    private final float C_CUT=0.2f;//[TODO]::make static
    private final int C_GAP=2;//[TODO]::make static
    
    private float cmDegree;
      
    public EcControlMotorIcon(){
      super();
      cmDegree=0.1f;
      ccSetSize(16, 16);
    }//++!

    @Override
    public void ccUpdate(){
      
      pbOwner.strokeWeight(C_GAP);{
        pbOwner.stroke(EcFactory.C_LIT_GRAY);
        pbOwner.fill(EcFactory.C_DIM_GRAY);
        pbOwner.ellipse(cmX, cmY, cmW-1, cmH-1);
      }pbOwner.strokeWeight(1);
      pbOwner.noStroke();
      
      pbOwner.fill(EcFactory.C_LIT_GRAY);
      pbOwner.rectMode(CENTER);
      pbOwner.pushMatrix();{
        pbOwner.translate(cmX, cmY);
        pbOwner.rotate(cmDegree);
        pbOwner.rect(0,0, cmW+C_GAP*4, C_GAP);
      }pbOwner.popMatrix();
      pbOwner.rectMode(CORNER);
      
      pbOwner.fill(cmFAS?EcFactory.C_WHITE:EcFactory.C_DIM_GRAY);
      pbOwner.arc(cmX,cmY,cmW-C_GAP*2,cmW-C_GAP*2,PI+C_CUT,2*PI-C_CUT,CHORD);
      pbOwner.fill(cmCAS?EcFactory.C_RED:EcFactory.C_DIM_GRAY);
      pbOwner.arc(cmX,cmY,cmW-C_GAP*2,cmW-C_GAP*2,C_CUT,PI-C_CUT,CHORD);
      
      pbOwner.fill(EcFactory.C_GRAY);
      pbOwner.ellipse(cmX, cmY, cmW-C_GAP*5, cmH-C_GAP*5);
    
    }//+++
    
    public final void ccSetDegree(int pxPercentage){
      cmDegree=PI*((float)pxPercentage)/200.0f;
    }//+++
    
  }//***
  
  //=== inner ** for plant unit
  public interface EiAbstractUnit{
  ;

  }

  public interface EiMoterized extends EiAbstractUnit{

    public void ccSetMotorStatus(char pxStatus_acnlx);

  }//***

  public interface EiReversible extends EiMoterized{

    abstract public void ccSetDirectionMode(char pxMode_alr);

  }//***
  
  public interface EiMultipleMoterized extends EiAbstractUnit{
    
    public void ccSetMotorStatus(int pxIndex, char pxStatus_acnlx);
    
  }//***
  
  class EcMoterizedUnit extends EcElement implements EiMoterized{
    
    EcMotorIcon cmMotor;
    
    public EcMoterizedUnit(){
      super();
      cmMotor=new EcMotorIcon();
    }
    
    /**
     * [a]mc+an..
     * [c]mc..
     * [n]an..
     * [l]al..
     * [x]OFF
     * @param pxStatus_acnlx #
     */
    @Override
    public void ccSetMotorStatus(char pxStatus_acnlx){
      //[TODO]::give it back to its own class later on
      DemoPlantIcon.ccSetMotorStatus(cmMotor, pxStatus_acnlx);
    }//+++
    
  }//***

  class EcBelconUnit extends EcElement implements EiReversible{

    protected final EcStatusLamp cmMotorLampL;

    protected final EcStatusLamp cmMotorLampR;

    protected final EcLamp cmEmsLamp;

    private char cmDirectionMode;

    public EcBelconUnit(){
      super();

      cmDirectionMode='a';

      //[TODO]::i want something like this:
      //  cmLamp=EcFactory.ccCreateStatusLamp(key,w,h,color,id);
      cmMotorLampL=new EcStatusLamp();
      cmMotorLampL.ccSetSize(16, 16);
      cmMotorLampL.ccSetNameAlign('x');
      cmMotorLampL.ccSetText(" ");
      cmMotorLampL.ccReplaceStatusColor(1, EcFactory.C_RED);
      cmMotorLampL.ccAddStatusColor(EcFactory.C_LIT_YELLOW);

      cmMotorLampR=new EcStatusLamp();
      cmMotorLampR.ccSetSize(16, 16);
      cmMotorLampR.ccSetNameAlign('x');
      cmMotorLampR.ccSetText(" ");
      cmMotorLampR.ccReplaceStatusColor(1, EcFactory.C_RED);
      cmMotorLampR.ccAddStatusColor(EcFactory.C_LIT_YELLOW);

      cmEmsLamp=new EcLamp();
      cmEmsLamp.ccSetSize(16, 16);
      cmEmsLamp.ccSetNameAlign('x');
      cmEmsLamp.ccSetText("E");
      cmEmsLamp.ccSetColor(EcFactory.C_RED);

    }//++!

    /**
     * {@inheritDoc}
     */
    @Override
    public void ccSetMotorStatus(char pxStatus_nlx){
      switch(pxStatus_nlx){
        case 'n':
          switch(cmDirectionMode){
            case 'l':
              cmMotorLampL.ccSetStatus(2);
              break;
            case 'r':
              cmMotorLampR.ccSetStatus(2);
              break;
            default:
              cmMotorLampL.ccSetStatus(2);
              cmMotorLampR.ccSetStatus(2);
              break;
          }
          break;
        case 'l':
          cmMotorLampL.ccSetStatus(1);
          cmMotorLampR.ccSetStatus(1);
          break;
        default:
          cmMotorLampL.ccSetStatus(0);
          cmMotorLampR.ccSetStatus(0);
          break;
      }
    }//+++

    @Override
    public void ccSetDirectionMode(char pxMode_alr){
      cmDirectionMode=pxMode_alr;
    }//+++

    public void ccSetIsEMS(boolean pxStatus){
      cmEmsLamp.ccSetActivated(pxStatus);
    }//+++

  }//***

  //=== inner ** for plant unit ** instance depandent
  class EcFeeder extends EcElement implements EiMoterized{

    private final EcValueBox cmBox;

    private final EcGauge cmGauge;

    private final EcHopperShape cmHopper;

    private final EcBelconShape cmBelt;

    private final EcStatusLamp cmMotorLamp;

    public EcFeeder(String pxName, int pxX, int pxY, int pxHeadID){

      super();
      ccSetLocation(pxX, pxY);
      ccSetID(pxHeadID);

      cmBox=new EcValueBox();//[TODO]::make a factoryline later
      cmBox.ccSetLocation(cmX, cmY);
      cmBox.ccSetValue(555, 4);//[TODO]::
      cmBox.ccSetUnit("r");
      cmBox.ccSetTextColor(C_BOX_TEXT);
      cmBox.ccSetColor(C_BOX_DEGREE, C_BOX_DEGREE);
      cmBox.ccSetSize();

      ccSetSize(cmBox.ccGetW()+1, cmBox.ccGetW()+1);

      cmGauge=EcFactory.ccCreateGauge(pxName, true, false, cmW-1, 4);//[TODO]::
      cmGauge.ccSetLocation(cmBox, 0, -cmBox.ccGetH()-4-1);
      cmGauge.ccSetNameAlign('a');
      cmGauge.ccSetColor(C_FEEDER_STUCK, C_FEEDER_FLOWING);

      cmHopper=new EcHopperShape();
      cmHopper.ccSetBound(cmX, cmY, cmW, cmH);
      cmHopper.ccSetCut(cmW/4);//[TODO]::make static later
      cmHopper.ccSetBaseColor(C_SHAPE_METAL);

      cmBelt=new EcBelconShape();
      cmBelt.ccSetSize(cmW, 10);//[TODO]::make static later
      cmBelt.ccSetLocation(cmHopper, 0, 4);
      cmBelt.ccSetBaseColor(C_SHAPE_METAL);

      //[TODO]::i want something like this:
      //  cmLamp=EcFactory.ccCreateStatusLamp(key,w,h,color,id);
      cmMotorLamp=new EcStatusLamp();
      cmMotorLamp.ccSetSize(10, 10);
      cmMotorLamp.ccSetLocation(cmBelt.ccGetX(), cmBelt.ccGetY());
      cmMotorLamp.ccSetNameAlign('x');
      cmMotorLamp.ccSetText(" ");
      cmMotorLamp.ccReplaceStatusColor(1, EcFactory.C_LIT_RED);
      cmMotorLamp.ccAddStatusColor(EcFactory.C_LIT_YELLOW);

    }//++!

    @Override
    public void ccUpdate(){

      cmHopper.ccUpdate();
      cmBelt.ccUpdate();

      cmGauge.ccUpdate();
      cmBox.ccUpdate();

      cmMotorLamp.ccUpdate();

    }//+++

    public final void ccSetRPM(int pxVal){
      cmGauge.ccSetPercentage(pxVal, 1800);
      cmBox.ccSetValue(pxVal);
    }//+++

    /**
     * if SG is ON that means the feeder is STUCKED!!
     */
    public final void ccSetIsSG(boolean pxStatus){
      cmGauge.ccSetActivated(pxStatus);
    }//+++

    /**
     * #
     * @param pxStatus_nlx [n]AN..[l]AL..[x|]OFF..
     */
    @Override
    public void ccSetMotorStatus(char pxStatus_nlx){
      switch(pxStatus_nlx){
        case 'n':
          cmMotorLamp.ccSetStatus(2);
          break;
        case 'l':
          cmMotorLamp.ccSetStatus(1);
          break;
        default:
          cmMotorLamp.ccSetStatus(0);
      }
    }//+++

  }//***

  class EcHorizontalBelcon extends EcBelconUnit{

    private final EcBelconShape cmShape;

    public EcHorizontalBelcon(
      String pxName, int pxX, int pxY, int pxLength, int pxHeadID
    ){

      super();
      cmMotorLampL.ccSetLocation(pxX+1, pxY+1);
      cmEmsLamp.ccSetLocation(cmMotorLampL, pxLength/4, 0);
      cmMotorLampR.ccSetLocation(pxX+pxLength-cmMotorLampR.ccGetW()-1, pxY+1);

      cmShape=new EcBelconShape();
      cmShape.ccSetBound(pxX, pxY, pxLength, 18);
      cmShape.ccSetBaseColor(C_SHAPE_METAL);

    }//++!

    @Override
    public void ccUpdate(){
      cmShape.ccUpdate();
      cmMotorLampL.ccUpdate();
      cmEmsLamp.ccUpdate();
      cmMotorLampR.ccUpdate();
    }//+++

  }//***

  class EcInclineBelcon extends EcBelconUnit{

    private final EcLamp cmCAS;

    public EcInclineBelcon(
      String pxName, int pxX, int pxY, int pxLength, int pxCut, int pxHeadID
    ){

      super();
      ccSetBound(pxX, pxY, pxLength, pxCut);

      cmMotorLampL.ccSetLocation(
        pxX+1-cmEmsLamp.ccGetW()/2,
        pxY+1-cmEmsLamp.ccGetW()/2
      );

      cmMotorLampR.ccSetLocation(
        pxX+pxLength-cmEmsLamp.ccGetW()/2,
        pxY+1+pxCut-cmEmsLamp.ccGetW()/2
      );

      cmEmsLamp.ccSetLocation(
        pxX+pxLength-cmEmsLamp.ccGetW()*2,
        pxY+1+pxCut-cmEmsLamp.ccGetW()/2
      );

      cmCAS=new EcLamp();
      cmCAS.ccSetLocation(cmMotorLampL, pxCut, 0);
      cmCAS.ccSetSize(cmEmsLamp);
      cmCAS.ccSetText("C");
      cmCAS.ccSetColor(EcFactory.C_LIT_GREEN);

    }//++!

    @Override
    public void ccUpdate(){

      strokeWeight(20);
      stroke(C_SHAPE_METAL);
      {
        pbOwner.line(cmX, cmY, cmX+cmH, cmX+cmH);
        pbOwner.line(cmX+cmH, cmX+cmH, ccEndX(), ccEndY());
      }
      strokeWeight(1);
      noStroke();

      cmCAS.ccUpdate();
      cmMotorLampL.ccUpdate();
      cmEmsLamp.ccUpdate();
      cmMotorLampR.ccUpdate();
    }//+++

    void ccSetIsCAS(boolean pxStatus){
      cmCAS.ccSetActivated(pxStatus);
    }//+++

  }//***

  class EcDryer extends EcMoterizedUnit{
    
    private int cmTphMax;
    
    private final EcValueBox cmKPABox;
    private final EcGauge cmTPHGauge;
    private final EcValueBox cmTPHBox;
    
    public EcDryer(
      String pxName, int pxX, int pxY, int pxHeadID
    ){
      
      super();
      
      final int C_OFFSET = 3;
      
      cmTphMax=320;
      
      ccTakeKey(pxName);
      ccSetLocation(pxX, pxY);
      ccSetID(pxHeadID);
      
      //-- construction
      cmKPABox=new EcValueBox();
      cmKPABox.ccSetLocation(pxX+C_OFFSET*2,pxY+C_OFFSET*2);
      cmKPABox.ccSetText("-9999 kPa");
      cmKPABox.ccSetSize();
      cmKPABox.ccSetValue(-20, 4);
      cmKPABox.ccSetUnit(" kPa");
      cmKPABox.ccSetTextColor(EcFactory.C_LIT_GRAY);
      cmKPABox.ccSetColor(EcFactory.C_PURPLE,EcFactory.C_DIM_BLUE);
      
      cmTPHGauge=new EcGauge();
      cmTPHGauge.ccSetLocation(pxX+C_OFFSET, pxY+C_OFFSET);
      cmTPHGauge.ccSetColor(EcFactory.C_ORANGE, EcFactory.C_DIM_GRAY);
      
      cmTPHBox=new EcValueBox();
      cmTPHBox.ccSetLocation(cmKPABox,
        C_OFFSET*4+cmKPABox.ccGetW(), cmKPABox.ccGetH()+C_OFFSET*2);
      cmTPHBox.ccSetText("999 TpH");
      cmTPHBox.ccSetSize();
      cmTPHBox.ccSetValue(1,3);
      cmTPHBox.ccSetUnit(" TpH");
      cmTPHBox.ccSetColor(EcFactory.C_PURPLE,EcFactory.C_DIM_YELLOW);
      
      //-- layout
      cmTPHGauge.ccSetEndPoint
        (cmTPHBox.ccEndX()+C_OFFSET, cmTPHBox.ccEndY()+C_OFFSET);
      ccSetEndPoint
        (cmTPHGauge.ccEndX()+C_OFFSET, cmTPHGauge.ccEndY()+C_OFFSET);
      cmMotor.ccSetLocation(cmX+C_OFFSET*2, cmY+cmH-cmMotor.ccGetH()/2);
      
    }//++!

    @Override
    public void ccUpdate(){
      
      pbOwner.fill(EcFactory.C_GRAY);
      pbOwner.rect(cmX,cmY,cmW,cmH);
      
      cmTPHGauge.ccUpdate();
      cmKPABox.ccUpdate();
      cmTPHBox.ccUpdate();
      cmMotor.ccUpdate();
    
    }//+++
    
    public final void ccSetIsOnFire(boolean pxStatus){
      cmTPHGauge.ccSetActivated(pxStatus);
    }//+++
    
    public final void ccSetKPA(int pxVal){
      cmKPABox.ccSetValue(pxVal);
    }//+++
    
    public final void ccSetTPH(int pxVal){
      cmTPHBox.ccSetValue(pxVal);
      cmTPHGauge.ccSetPercentage(pxVal, cmTphMax);
    }//+++
    
    public final void ccSetMAX(int pxVal){cmTphMax=pxVal;}//+++
    
  }//***
  
  class EcFuelUnit extends EcElement implements EiMoterized{
    
    final int C_DUCT_THICK=4;//[TODO]make const
    final int C_MV_LAMP_SIZE=16;
    
    EcPumpIcon cmPump;
    EcTriangleLamp cmFuelPL;
    EcTriangleLamp cmHeavyPL;
    
    public EcFuelUnit(String pxName, int pxX, int pxY, int pxHeadID){
      
      ccTakeKey(pxName);
      ccSetLocation(pxX, pxY);
      ccSetSize(60,30);
      ccSetID(pxHeadID);
      
      cmPump=new EcPumpIcon();
      cmPump.ccSetLocation(pxX, pxY-cmPump.ccGetH()*3/2);
      cmPump.ccSetDirection('l');
      
      cmFuelPL=new EcTriangleLamp();
      cmFuelPL.ccSetLocation
        (ccEndX()-C_MV_LAMP_SIZE/2, cmY+2-C_MV_LAMP_SIZE/2);
      cmFuelPL.ccSetSize(C_MV_LAMP_SIZE,C_MV_LAMP_SIZE);
      cmFuelPL.ccSetColor(EcFactory.C_ORANGE, EcFactory.C_DIM_GRAY);
      cmFuelPL.ccSetText(" ");
      cmFuelPL.ccSetName("FO");
      cmFuelPL.ccSetNameAlign('r');
      
      cmHeavyPL=new EcTriangleLamp();
      cmHeavyPL.ccSetLocation
        (ccEndX()-C_MV_LAMP_SIZE/2, ccEndY()-2-C_MV_LAMP_SIZE/2);
      cmHeavyPL.ccSetSize(C_MV_LAMP_SIZE,C_MV_LAMP_SIZE);
      cmHeavyPL.ccSetColor(EcFactory.C_ORANGE, EcFactory.C_DIM_GRAY);
      cmHeavyPL.ccSetText(" ");
      cmHeavyPL.ccSetName("HO");
      cmHeavyPL.ccSetNameAlign('r');
      
    }//++!

    @Override
    public void ccUpdate(){
      
      pbOwner.fill(C_SHAPE_DUCT);
      pbOwner.rect(cmX,cmY,cmW,C_DUCT_THICK);
      pbOwner.rect(ccCenterX(),cmY,C_DUCT_THICK,cmH);
      pbOwner.rect(ccCenterX(),cmY+cmH-C_DUCT_THICK,cmW/2,C_DUCT_THICK);
      
      cmPump.ccUpdate();
      cmHeavyPL.ccUpdate();
      cmFuelPL.ccUpdate();
      
    }//+++

    @Override
    public void ccSetMotorStatus(char pxStatus_acnlx){
      //[TODO]::give static owner back
      DemoPlantIcon.ccSetMotorStatus(cmPump, pxStatus_acnlx);
    }//+++
    
    public final void ccSetFuelON(boolean pxStatus){
      cmFuelPL.ccSetActivated(pxStatus);
    }//+++
    
    public final void ccSetHeavyON(boolean pxStatus){
      cmHeavyPL.ccSetActivated(pxStatus);
    }//+++
    
  }//***
  
  class EcGasUnit extends EcElement {
    
    final int C_DUCT_THICK=4;//[TODO]make const
    
    private final int //[TODO]make const
      C_LED_W=8,
      C_LED_H=4
    ;
    
    private boolean 
      cmIsLeakingHigh,cmIsLeakingLow
    ;//...
    
    private final EcBelconShape cmGasUnitShape;
    private final EcLamp cmGasPressureHI;
    private final EcLamp cmGasPressureLO;
    private final EcTriangleLamp cmGasValveA;
    private final EcTriangleLamp cmGasValveB;
    
    public EcGasUnit(String pxName, int pxX, int pxY, int pxHeadID){
      
      super();
      ccTakeKey(pxName);
      ccSetLocation(pxX,pxY);
      ccSetSize(65,20);
      ccSetID(pxHeadID);
      
      cmIsLeakingHigh=false;
      cmIsLeakingLow=false;
      
      cmGasUnitShape = new EcBelconShape();
      cmGasUnitShape.ccSetLocation(cmX+16, ccEndY()-6);
      cmGasUnitShape.ccSetSize(32, 16);
      cmGasUnitShape.ccSetBaseColor(C_SHAPE_METAL);
      
      cmGasPressureHI=new EcLamp();
      cmGasPressureHI.ccSetLocation(cmX-5, cmY-6);
      cmGasPressureHI.ccSetSize(12, 12);
      cmGasPressureHI.ccSetText(" ");
      cmGasPressureHI.ccSetName("Hi");
      cmGasPressureHI.ccSetNameAlign('a');
      cmGasPressureHI.ccSetColor(EcFactory.C_PURPLE);
      
      cmGasPressureLO=new EcLamp();
      cmGasPressureLO.ccSetLocation(ccEndX()-6, ccEndY()-5);
      cmGasPressureLO.ccSetSize(12, 12);
      cmGasPressureLO.ccSetText(" ");
      cmGasPressureLO.ccSetName("Lo");
      cmGasPressureLO.ccSetNameAlign('r');
      cmGasPressureLO.ccSetColor(EcFactory.C_PURPLE);
      
      cmGasValveA=new EcTriangleLamp();
      cmGasValveA.ccSetLocation(cmGasUnitShape,17,-14);
      cmGasValveA.ccSetSize(8,8);
      cmGasValveA.ccSetText(" ");
      cmGasValveA.ccSetName("A");
      cmGasValveA.ccSetNameAlign('a');
      cmGasValveA.ccSetDirection('d');
      cmGasValveA.ccSetColor(EcFactory.C_ORANGE);
      
      cmGasValveB=new EcTriangleLamp();
      cmGasValveB.ccSetLocation(cmGasUnitShape,1,-14);
      cmGasValveB.ccSetSize(8,8);
      cmGasValveB.ccSetText(" ");
      cmGasValveB.ccSetName("B");
      cmGasValveB.ccSetNameAlign('a');
      cmGasValveB.ccSetDirection('d');
      cmGasValveB.ccSetColor(EcFactory.C_ORANGE);
      
    }//++!

    @Override
    public void ccUpdate(){
      
      //-- gas pipe
      pbOwner.fill(C_SHAPE_DUCT);
      pbOwner.rect(cmX,cmY,C_DUCT_THICK,cmH);
      pbOwner.rect(cmX,ccEndY(),cmW,C_DUCT_THICK);
      
      //-- gas unit shape
      pbOwner.stroke(EcFactory.C_LIT_GRAY);
      cmGasUnitShape.ccUpdate();
      pbOwner.noStroke();
      
      //-- pressure switch
      pbOwner.fill(cmIsLeakingHigh?
        EcFactory.C_LIT_GREEN:EcFactory.C_BLACK
      );
      pbOwner.rect(
        cmGasUnitShape.ccGetX()+8, cmGasUnitShape.ccGetY()+4,
        C_LED_W, C_LED_H
      );      
      pbOwner.fill(cmIsLeakingLow?
        EcFactory.C_LIT_YELLOW:EcFactory.C_BLACK
      );
      pbOwner.rect(
        cmGasUnitShape.ccGetX()+8, cmGasUnitShape.ccGetY()+C_LED_H+6,
        C_LED_W, C_LED_H
      );
      
      //-- elements
      cmGasPressureHI.ccUpdate();
      cmGasPressureLO.ccUpdate();
      cmGasValveA.ccUpdate();
      cmGasValveB.ccUpdate();
      
    }//+++
    
    public final void ccSetIsPressureHI(boolean pxStatus){
      cmGasPressureHI.ccSetActivated(pxStatus);
    }//+++
    
    public final void ccSetIsPressureLO(boolean pxStatus){
      cmGasPressureLO.ccSetActivated(pxStatus);
    }//+++
    
    public final void ccSetIsLeakHI(boolean pxStatus){
      cmIsLeakingHigh=pxStatus;
    }//+++
    
    public final void ccSetIsLeakLO(boolean pxStatus){
      cmIsLeakingLow=pxStatus;
    }//+++
    
    public final void ccSetIsValveAOpen(boolean pxStatus){
      cmGasValveA.ccSetActivated(pxStatus);
    }//+++
    
    public final void ccSetIsValveBOpen(boolean pxStatus){
      cmGasValveB.ccSetActivated(pxStatus);
    }//+++
    
  }//***
  
  class EcBurner extends EcMoterizedUnit{
    
    private final int 
      C_LED_W=4,
      C_LED_H=8
    ;
    
    private boolean
      cmIG,cmPV,cmCDS
    ;
    
    private final EcBlowerShape cmBurnerShape;
    private final EcValueBox cmDegreeBox;
    private final EcControlMotorIcon cmDamperIcon;
    
    public EcBurner(String pxName, int pxX, int pxY, int pxHeadID){
      
      super();
      ccTakeKey(pxName);
      ccSetLocation(pxX, pxY);
      ccSetID(pxHeadID);
      
      cmIG=false;
      cmPV=false;
      cmCDS=false;
      
      cmBurnerShape = new EcBlowerShape();
      cmBurnerShape.ccSetLocation(cmX, cmY);
      
      cmDegreeBox=new EcValueBox();
      cmDegreeBox.ccSetLocation(cmX+2, cmY-8);
      cmDegreeBox.ccSetText("-010%");
      cmDegreeBox.ccSetSize();
      cmDegreeBox.ccSetValue(1,3);
      cmDegreeBox.ccSetUnit("%");
      cmDegreeBox.ccSetTextColor(EcFactory.C_LIT_GRAY);
      cmDegreeBox.ccSetColor(EcFactory.C_PURPLE, EcFactory.C_DARK_GREEN);
      
      ccSetSize(cmDegreeBox.ccGetW()+8, cmDegreeBox.ccGetH()+4);
      cmBurnerShape.ccSetSize(cmW, cmH);
      cmBurnerShape.ccSetDirection('r');
      
      cmDamperIcon=new EcControlMotorIcon();
      cmDamperIcon.ccSetLocation(ccCenterX()+5,ccEndY()+cmDamperIcon.ccGetH());
      
      cmMotor.ccSetLocation(cmX-cmMotor.ccGetW()*3/2, cmY+cmMotor.ccGetH());
      
    }//++!

    @Override
    public void ccUpdate(){
      
      cmBurnerShape.ccUpdate();
      cmDegreeBox.ccUpdate();
      cmDamperIcon.ccUpdate();
      cmMotor.ccUpdate();
      
      pbOwner.fill(cmIG?EcFactory.C_YELLOW:EcFactory.C_DARK_GRAY);
      pbOwner.rect(ccEndX()-C_LED_W*4, ccEndY()-2-C_LED_H, C_LED_W, C_LED_H);
      pbOwner.fill(cmPV?EcFactory.C_ORANGE:EcFactory.C_DARK_GRAY);
      pbOwner.rect(ccEndX()-C_LED_W*3, ccEndY()-2-C_LED_H, C_LED_W, C_LED_H);
      pbOwner.fill(cmCDS?EcFactory.C_RED:EcFactory.C_DARK_GRAY);
      pbOwner.rect(ccEndX()-C_LED_W*2, ccEndY()-2-C_LED_H, C_LED_W, C_LED_H);
      
    }//+++
    
    public final void ccSetDegree(int pxPercentage){
      cmDegreeBox.ccSetValue(pxPercentage);
      cmDamperIcon.ccSetDegree(pxPercentage);
    }//+++
    
    public final void ccSetIsFull(boolean pxStatus){
      cmDamperIcon.ccSetIsFull(pxStatus);
    }//+++
    
    public final void ccSetIsClosed(boolean pxStatus){
      cmDamperIcon.ccSetIsClosed(pxStatus);
    }//+++
    
    public final void ccSetIsIgniting(boolean pxStatus){cmIG=pxStatus;}//+++
    public final void ccSetIsPiloting(boolean pxStatus){cmPV=pxStatus;}//+++
    public final void ccSetHasFire(boolean pxStatus){cmCDS=pxStatus;}//+++
    
  }//***
  
  class EcBagFilter extends EcElement implements EiMultipleMoterized{
    
    private final int //[TODO]::make static
      C_BAG_CUT=30,
      C_COARSE_CUT=15,
      //--
      C_FILTER_W=3,
      C_FILTER_H=18,
      C_FILTER_GAP=2
    ;//...
    
    public final int //[TODO]::make static
      C_M_BAG_SCREW=0,
      C_M_COARSE_SCREW=1
    ;//...
    
    private int cmFilterCount;
    private int cmCurrentCount;
    
    private final EcHopperShape cmBag;
    private final EcHopperShape cmCoarse;
    private final EcScrewShape cmBagScrew;
    private final EcScrewShape cmCoarseScrew;
    private final EcLamp cmBagUpperLV;
    private final EcLamp cmBagLowerLV;
    private final EcSingleSolenoidIcon cmCoolingDamper;
    private final EcMotorIcon cmBagScrewMotor;
    private final EcMotorIcon cmCoarseScrewMotor;
    private final EcTriangleLamp cmToDustFeederPL;
    private final EcTriangleLamp cmToDustExtractionPL;
    private final EcValueBox cmEntranceTemrature;

    public EcBagFilter(
      String pxName, int pxX, int pxY,int pxFilterCount, int pxHeadID
    ){
      
      super();
      ccTakeKey(pxName);
      ccSetLocation(pxX, pxY);
      ccSetSize(150,100);//[TODO]::width should be as long as V dryer
      ccSetID(pxHeadID);
      
      cmFilterCount=pxFilterCount;
      cmCurrentCount=0;
      
      cmBag=new EcHopperShape();
      cmBag.ccSetLocation(cmX, cmY);
      cmBag.ccSetSize(cmW-C_BAG_CUT, cmH-C_BAG_CUT);
      cmBag.ccSetBaseColor(C_SHAPE_METAL);
      cmBag.ccSetCut(C_BAG_CUT);
      
      cmCoarse=new EcHopperShape();
      cmCoarse.ccSetLocation(ccEndX()-C_BAG_CUT, cmY+C_COARSE_CUT);
      cmCoarse.ccSetSize(C_BAG_CUT,cmH-C_COARSE_CUT*2);
      cmCoarse.ccSetBaseColor(C_SHAPE_METAL);
      cmCoarse.ccSetCut(C_COARSE_CUT);
      
      cmBagScrew=new EcScrewShape();
      cmBagScrew.ccSetLocation(cmBag, 0,2);
      cmBagScrew.ccShiftLocation(C_BAG_CUT/2, 0);
      cmBagScrew.ccSetSize(cmBag.ccGetW()-C_BAG_CUT, 12);
      cmBagScrew.ccSetCut(C_BAG_CUT, -1);
      
      cmCoarseScrew=new EcScrewShape();
      cmCoarseScrew.ccSetLocation(ccCenterX()-2, cmCoarse.ccEndY()+2);
      cmCoarseScrew.ccSetSize(cmW/2, 24);
      cmCoarseScrew.ccSetCut(cmW/2-C_COARSE_CUT, 0);
      
      cmBagUpperLV=new EcLamp();
      cmBagUpperLV.ccSetLocation(cmX+C_BAG_CUT/2, cmBag.ccEndY()-C_BAG_CUT);
      cmBagUpperLV.ccSetSize(12, 12);
      cmBagUpperLV.ccSetName("F2H");
      cmBagUpperLV.ccSetNameAlign('l');
      cmBagUpperLV.ccSetText(" ");
      cmBagUpperLV.ccSetColor(EcFactory.C_LIT_GREEN);
      
      cmBagLowerLV=new EcLamp();
      cmBagLowerLV.ccSetLocation(cmBagUpperLV,15,15);
      cmBagLowerLV.ccSetSize(12, 12);
      cmBagLowerLV.ccSetName("F2H");
      cmBagLowerLV.ccSetNameAlign('l');
      cmBagLowerLV.ccSetText(" ");
      cmBagLowerLV.ccSetColor(EcFactory.C_LIT_GREEN);
      
      cmCoolingDamper=new EcSingleSolenoidIcon();
      cmCoolingDamper.ccSetLocation(ccEndX(), cmY+C_COARSE_CUT);
      
      cmBagScrewMotor=new EcMotorIcon();
      cmBagScrewMotor.ccSetLocation
        (cmBagScrew.ccEndX()-30, cmBagScrew.ccCenterY()-4);
      
      cmCoarseScrewMotor=new EcMotorIcon();
      cmCoarseScrewMotor.ccSetLocation
        (cmCoarseScrew.ccEndX()-20, cmCoarseScrew.ccCenterY()-2);
      
      cmToDustFeederPL = new EcTriangleLamp();
      cmToDustFeederPL.ccSetDirection('l');
      cmToDustFeederPL.ccSetName("DF");
      cmToDustFeederPL.ccSetNameAlign('b');
      cmToDustFeederPL.ccSetText(" ");
      cmToDustFeederPL.ccSetColor(EcFactory.C_ORANGE);
      cmToDustFeederPL.ccSetSize(8,8);
      cmToDustFeederPL.ccSetLocation
        (cmBagScrew.ccGetX()-18  , cmBagScrew.ccCenterY()-4);
      
      cmToDustExtractionPL = new EcTriangleLamp();
      cmToDustExtractionPL.ccSetDirection('r');
      cmToDustExtractionPL.ccSetName("DE");
      cmToDustExtractionPL.ccSetNameAlign('b');
      cmToDustExtractionPL.ccSetText(" ");
      cmToDustExtractionPL.ccSetColor(EcFactory.C_ORANGE);
      cmToDustExtractionPL.ccSetSize(8,8);
      cmToDustExtractionPL.ccSetLocation
        (cmBagScrew.ccEndX()+8  , cmBagScrew.ccCenterY()-4);
      
      cmEntranceTemrature = new EcValueBox();
      cmEntranceTemrature.ccSetText("-123'c");
      cmEntranceTemrature.ccSetSize();
      cmEntranceTemrature.ccSetValue(37,3);
      cmEntranceTemrature.ccSetUnit("'c");
      cmEntranceTemrature.ccSetColor(EcFactory.C_PURPLE, EcFactory.C_DIM_RED);
      cmEntranceTemrature.ccSetTextColor(EcFactory.C_LIT_GRAY);
      cmEntranceTemrature.ccSetLocation(cmCoarse,5,C_COARSE_CUT);
      
    }//+++

    @Override
    public void ccUpdate(){
      
      cmBag.ccUpdate();
      cmCoarse.ccUpdate();
      cmBagScrew.ccUpdate();
      cmCoarseScrew.ccUpdate();
      cmBagUpperLV.ccUpdate();
      cmBagLowerLV.ccUpdate();
      cmCoolingDamper.ccUpdate();
      cmBagScrewMotor.ccUpdate();
      cmCoarseScrewMotor.ccUpdate();
      cmToDustFeederPL.ccUpdate();
      cmToDustExtractionPL.ccUpdate();
      cmEntranceTemrature.ccUpdate();
      
      for(int i=0;i<cmFilterCount;i++){
        pbOwner.fill(cmCurrentCount==i?
          EcFactory.C_LIT_ORANGE:EcFactory.C_DARK_GRAY
        );
        pbOwner.rect(
          cmX+C_FILTER_GAP*2+(C_FILTER_GAP+C_FILTER_W)*i,
          cmY+C_FILTER_GAP*2,
          C_FILTER_W,C_FILTER_H
        );
      }
      
    }//+++
    
    @Override
    public void ccSetMotorStatus(int pxIndex, char pxStatus_acnlx){
      
      switch(pxIndex){
        
        case C_M_BAG_SCREW:
          //[TODO]::chager the static owner lator
          DemoPlantIcon.ccSetMotorStatus(cmBagScrewMotor, pxStatus_acnlx);
        break;
        
        case C_M_COARSE_SCREW:
          //[TODO]::chager the static owner lator
          DemoPlantIcon.ccSetMotorStatus(cmCoarseScrewMotor, pxStatus_acnlx);
        break;
        
      }//..?
      
    }//+++
    
    public void ccSetCoolingDamoerStatus(char pxTarget_soc,boolean pxStatus){
      switch(pxTarget_soc){
        case 's':cmCoolingDamper.ccSetIsOpening(pxStatus);break;
        case 'o':cmCoolingDamper.ccSetIsFull(pxStatus);break;
        case 'c':cmCoolingDamper.ccSetIsClosed(pxStatus);break;
      }
    }//+++
    
    public void ccSetEntranceTemprature(int pxVal){
      cmEntranceTemrature.ccSetValue(pxVal);
    }//+++
    
    public void ccSetBagLevelerStatus(char pxTarget_hl,boolean pxStatus){
      switch(pxTarget_hl){
        case 'h':cmBagUpperLV.ccSetActivated(pxStatus);break;
        case 'l':cmBagLowerLV.ccSetActivated(pxStatus);break;
      }
    }//+++
    
    public void ccSetDustFlow(char pxTarget_ef,boolean pxStatus){
      switch(pxTarget_ef){
        case 'e':cmToDustExtractionPL.ccSetActivated(pxStatus);break;
        case 'f':cmToDustFeederPL.ccSetActivated(pxStatus);break;
      }
    }//+++
    
    public void ccSetCurrentFilterCount(int pxCount){
      cmCurrentCount=pxCount;
    }//+++
    
    public void ccSetFilterCount(int pxCount){
      cmFilterCount=pxCount;
    }//+++

  }//***
  
  class EcExhaustFan extends EcMoterizedUnit{
    
    private final int //[TODO]::make static
      C_DUCT_THICK=8,
      C_DUCT_GAP=4
    ;//...
    
    private final EcBlowerShape cmFanShape;
    private final EcLamp cmPressurePL;
    private final EcControlMotorIcon cmDamper;
    private final EcValueBox cmDegreeBox;
    
    
    public EcExhaustFan(String pxName, int pxX, int pxY, int pxHeadID){

      super();
      ccTakeKey(pxName);
      ccSetLocation(pxX,pxY);
      ccSetID(pxHeadID);
      ccSetSize(C_DUCT_THICK*7, C_DUCT_THICK*12);
      
      cmFanShape = new EcBlowerShape();
      cmFanShape.ccSetLocation(cmX, cmY+C_DUCT_THICK*8);
      cmFanShape.ccSetSize(C_DUCT_THICK*2, C_DUCT_THICK*4);
      cmFanShape.ccSetBaseColor(C_SHAPE_DUCT);
      cmFanShape.ccSetDirection('u');
        
      cmPressurePL = new EcLamp();
      cmPressurePL.ccSetLocation(cmFanShape, -8, 10);
      cmPressurePL.ccSetSize(16,16);
      cmPressurePL.ccSetText("L");
      cmPressurePL.ccSetColor(EcFactory.C_LIT_GREEN);
      
      cmDamper = new EcControlMotorIcon();
      cmDamper.ccSetLocation(ccEndX()-3*C_DUCT_THICK+3, ccEndY()+3);
      
      cmDegreeBox = new EcValueBox();
      cmDegreeBox.ccSetText("-099%");
      cmDegreeBox.ccSetSize();
      cmDegreeBox.ccSetValue(1,3);
      cmDegreeBox.ccSetUnit("%");
      cmDegreeBox.ccSetTextColor(EcFactory.C_LIT_GRAY);
      cmDegreeBox.ccSetLocation(cmFanShape, 0, C_DUCT_THICK*5/2);
      cmDegreeBox.ccSetTextColor(EcFactory.C_LIT_GRAY);
      cmDegreeBox.ccSetColor(EcFactory.C_PURPLE, EcFactory.C_DARK_GREEN);
      
      cmMotor.ccSetLocation(cmFanShape,4, C_DUCT_THICK*7/2);
      
    }//++!

    @Override
    public void ccUpdate(){
      
      pbOwner.fill(C_SHAPE_METAL);
      pbOwner.rect(cmX, cmY, C_DUCT_THICK*2, C_DUCT_THICK*8-C_DUCT_GAP);
      pbOwner.rect(ccEndX()-C_DUCT_THICK, ccCenterY(), C_DUCT_THICK, cmH/2);
      
      int lpFanEnd=cmFanShape.ccGetW()*2+C_DUCT_GAP;
      pbOwner.rect(cmX+lpFanEnd, ccEndY(), cmW-lpFanEnd, C_DUCT_THICK);
      
      cmFanShape.ccUpdate();
      cmPressurePL.ccUpdate();
      cmDamper.ccUpdate();
      cmDegreeBox.ccUpdate();
      cmMotor.ccUpdate();
      
    }//***
    
    /**
     * NOT constrainning, dont get over. 
     * @param pxVal 0-100
     */
    public final void ccSetDegree(int pxVal){
      cmDegreeBox.ccSetValue(pxVal);
      cmDamper.ccSetDegree(pxVal);
    }//+++
    
    public final void ccSetIsFull(boolean pxStatus){
      cmDamper.ccSetIsFull(pxStatus);
    }//+++
    
    public final void ccSetIsClosed(boolean pxStatus){
      cmDamper.ccSetIsClosed(pxStatus);
    }//+++
    
    public final void ccSetHasPressure(boolean pxStatus){
      cmPressurePL.ccSetActivated(pxStatus);
    }//+++
    
  }//***
  
  class EcHotTower extends EcElement implements EiMultipleMoterized{
    
    public final int//[TODO]::make static
      C_I_SCREEN=0,
      C_I_HOTELEVATOR=1,
      C_I_BLOWER=2
    ;//...
    
    private final int //[TODO]::make static
      C_TOWER_W=40,
      C_FLOOR_H=20,
      C_FLOOR_GAP=4,
      C_DUCT_THICK=8,
      C_SCREEN_CUT=16
    ;//...
    
    private final EcElevatorShape cmHotElevatorShape;
    private final EcBlowerShape cmBlowerShape;
    private final EcLamp cmOverFlowLV;
    private final EcLamp cmOverSizeLV;
    private final EcTriangleLamp cmOverFlowGate;
    private final EcTriangleLamp cmOverSizeGate;
    private final EcMotorIcon cmScreenMotor;
    private final EcMotorIcon cmElevatorMotor;
    private final EcMotorIcon cmBlowerMotor;
    
    public EcHotTower(String pxName, int pxX, int pxY, int pxHeadID){
      
      super();
      ccTakeKey(pxName);
      ccSetLocation(pxX, pxY);
      ccSetSize(C_TOWER_W,C_FLOOR_H*4+C_FLOOR_GAP*2);
      
      int lpCut=16;
      cmHotElevatorShape = new EcElevatorShape();
      cmHotElevatorShape.ccSetCut(lpCut);
      cmHotElevatorShape.ccSetLocation(
        ccEndX()+C_DUCT_THICK/2+C_FLOOR_GAP*2,
        cmY-C_FLOOR_H-lpCut-C_FLOOR_GAP*2
      );
      cmHotElevatorShape.ccSetSize(12,cmH+lpCut+C_FLOOR_H+C_FLOOR_GAP*2);
      cmHotElevatorShape.ccSetBaseColor(EcFactory.C_GRAY);
      
      cmBlowerShape = new EcBlowerShape();
      cmBlowerShape.ccSetLocation(ccCenterX()+C_FLOOR_H-4, cmY+C_FLOOR_H+C_FLOOR_GAP*2);
      cmBlowerShape.ccSetSize(cmH/6, C_FLOOR_H/3);
      cmBlowerShape.ccSetBaseColor(EcFactory.C_WHITE);
      cmBlowerShape.ccSetDirection('r');
      
      cmOverSizeLV = new EcLamp();
      cmOverSizeLV.ccSetLocation(cmX-C_FLOOR_GAP-C_DUCT_THICK-1, cmY-6);
      cmOverSizeLV.ccSetSize(12,12);
      cmOverSizeLV.ccSetText(" ");
      cmOverSizeLV.ccSetColor(EcFactory.C_GREEN);
      
      cmOverFlowLV = new EcLamp();
      cmOverFlowLV.ccSetLocation(ccEndX()+C_FLOOR_GAP-5, cmY-6);
      cmOverFlowLV.ccSetSize(12,12);
      cmOverFlowLV.ccSetText(" ");
      cmOverFlowLV.ccSetColor(EcFactory.C_GREEN);
      
      cmOverSizeGate = new EcTriangleLamp();
      cmOverSizeGate.ccSetLocation(cmOverSizeLV, 1, cmH);
      cmOverSizeGate.ccSetSize(12,12);
      cmOverSizeGate.ccSetText(" ");
      cmOverSizeGate.ccSetDirection('d');
      cmOverSizeGate.ccSetColor(EcFactory.C_ORANGE);
      
      cmOverFlowGate = new EcTriangleLamp();
      cmOverFlowGate.ccSetLocation(cmOverFlowLV, 1, cmH);
      cmOverFlowGate.ccSetSize(12,12);
      cmOverFlowGate.ccSetText(" ");
      cmOverFlowGate.ccSetDirection('d');
      cmOverFlowGate.ccSetColor(EcFactory.C_ORANGE);
      
      cmElevatorMotor = new EcMotorIcon();
      cmElevatorMotor.ccSetLocation(cmHotElevatorShape, 2, -4);
      
      cmScreenMotor = new EcMotorIcon();
      cmScreenMotor.ccSetLocation
        (cmX+C_SCREEN_CUT,cmY-C_FLOOR_GAP-C_FLOOR_H-8);
      
      cmBlowerMotor = new EcMotorIcon();
      cmBlowerMotor.ccSetLocation
        (cmX+C_TOWER_W/2+4,cmY+C_FLOOR_GAP+C_FLOOR_H-4);
      
    }//++!

    @Override
    public void ccUpdate(){
      
      //-- draw tower
      pbOwner.fill(EcFactory.C_LIT_GRAY);
      pbOwner.rect(cmX, cmY, cmW, C_FLOOR_H);
      pbOwner.rect(cmX, cmY+C_FLOOR_H+C_FLOOR_GAP, cmW, C_FLOOR_H);
      pbOwner.rect(cmX, cmY+2*(C_FLOOR_H+C_FLOOR_GAP), cmW, C_FLOOR_H);
      pbOwner.rect(
        cmX, cmY+3*C_FLOOR_H+2*C_FLOOR_GAP,
        C_DUCT_THICK, C_FLOOR_H
      );
      pbOwner.rect(
        ccEndX()-C_DUCT_THICK, cmY+3*C_FLOOR_H+2*C_FLOOR_GAP,
        C_DUCT_THICK, C_FLOOR_H
      );
      
      //-- draw screen
      pbOwner.fill(EcFactory.C_GRAY);
      pbOwner.quad(
        cmX+C_SCREEN_CUT, cmY-C_FLOOR_GAP-C_FLOOR_H,
        ccEndX(), cmY-C_FLOOR_GAP-C_FLOOR_H,
        ccEndX(), cmY-C_FLOOR_GAP,
        cmX, cmY-C_FLOOR_GAP
      );
      pbOwner.fill(EcFactory.C_LIT_GRAY);
      pbOwner.rect(ccEndX()-2, cmY-C_FLOOR_GAP-2, -3*C_TOWER_W/4, -2);
      pbOwner.rect(ccEndX()-2, cmY-C_FLOOR_GAP-6, -2*C_TOWER_W/4, -2);
      pbOwner.rect(ccEndX()-2, cmY-C_FLOOR_GAP-10, -2*C_TOWER_W/4, -2);
      pbOwner.rect(ccEndX()-2, cmY-C_FLOOR_GAP-14, -2*C_TOWER_W/4, -2);
      
      //-- draw extract duct
      pbOwner.fill(EcFactory.C_GRAY);
      pbOwner.rect(
        cmX-C_FLOOR_GAP, cmY,
        -1*C_DUCT_THICK/2, cmH-C_FLOOR_GAP*2
      );
      pbOwner.rect(
        ccEndX()+C_FLOOR_GAP, cmY, 
        C_DUCT_THICK/2, cmH-C_FLOOR_GAP*2
      );
      
      //-- draw elevator
      cmHotElevatorShape.ccUpdate();
      
      //-- draw blower
      cmBlowerShape.ccUpdate();
      
      //-- update element
      cmOverSizeLV.ccUpdate();
      cmOverFlowLV.ccUpdate();
      cmOverSizeGate.ccUpdate();
      cmOverFlowGate.ccUpdate();
      cmElevatorMotor.ccUpdate();
      cmScreenMotor.ccUpdate();
      cmBlowerMotor.ccUpdate();
      
    }//+++

    @Override
    public void ccSetMotorStatus(int pxIndex, char pxStatus_acnlx){
      switch(pxIndex){
        case C_I_SCREEN:
          DemoPlantIcon.ccSetMotorStatus(cmScreenMotor, pxStatus_acnlx);
          break;
        case C_I_HOTELEVATOR:
          DemoPlantIcon.ccSetMotorStatus(cmElevatorMotor, pxStatus_acnlx);
          break;
        case C_I_BLOWER:
          DemoPlantIcon.ccSetMotorStatus(cmBlowerMotor, pxStatus_acnlx);
          break;
        default:break;
      }//..?
    }//+++
    
    public final void ccSetIsOverFlowFull(boolean pxStatus){
      cmOverFlowLV.ccSetActivated(pxStatus);
    }//+++
    
    public final void ccSetIsOverSizeFull(boolean pxStatus){
      cmOverSizeLV.ccSetActivated(pxStatus);
    }//+++
    
    public final void ccSetIsOverFlowGateOpening(boolean pxStatus){
      cmOverFlowGate.ccSetActivated(pxStatus);
    }//+++
    
    public final void ccSetIsOverSizeGateOpening(boolean pxStatus){
      cmOverSizeGate.ccSetActivated(pxStatus);
    }//+++
    
  }//***
  
  class EcFillerSilo extends EcMoterizedUnit{
    
    //[HEAD]:: now what?
    
    
  }//***
  
  //class EcDustSilo {}//***
  //class EcDustMixer {}//***
  //class EcMixer {}//***
  //class EcCobHopper {}//***
  
  //class EcColdElevator {}//***
  //class EcRSurgeBin　{}//***
  //class EcRExhaustFan　{}//***
  
  //class EcOnePathSkip　{}//***
  //class EcMixtureSilo　{}//***

  //=== entry
  static public void main(String[] passedArgs){
    PApplet.main(DemoPlantIcon.class.getCanonicalName());
  }//+++

}//***eof