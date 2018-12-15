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

  EcBlowerShape ttt;
  
  //=== overridden
  @Override
  public void setup(){

    size(800, 600);
    noSmooth();
    //-- initiating
    EcFactory.ccInit(this);

    //-- binding
    //-- construction
    ttt=new EcBlowerShape();
    ttt.ccSetLocation(100, 100);
    ttt.ccSetSize(20, 60);
      
    
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

      case 'a':
        ttt.ccSetDirection('l');
      break;
        
      case 's':
        ttt.ccSetDirection('d');
      break;

      case 'd':
        ttt.ccSetDirection('r');
      break;

      case 'w':
        ttt.ccSetDirection('u');
      break;

      case 'r':
      break;
        
      case 'f':
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

  //=== utiliry
  //=== inner
  //=== inner ** for localui
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
      pbOwner.strokeWeight(2);
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
  
  public class EcRangedIcon extends EcElement{
    
    private final int//[TODO]::make static
      C_LED_W=8,
      C_LED_H=2
    ;
    
    protected boolean 
      cmUP,cmDN,
      cmFAS,cmMAS,cmCAS
    ;
    
    public EcRangedIcon(){
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
      pbOwner.fill(cmMAS?EcFactory.C_SKY:EcFactory.C_DIM_GRAY);
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
  
  public class EcDamperIcon extends EcRangedIcon{
    
    private final float C_CUT=0.2f;//[TODO]::make static
    private final int C_GAP=2;//[TODO]::make static
    
    private float cmDegree;
      
    public EcDamperIcon(){
      super();
      cmDegree=0.1f;
      ccSetSize(16, 16);
    }//++!

    @Override
    public void ccUpdate(){
      
      pbOwner.strokeWeight(C_GAP);
      pbOwner.stroke(EcFactory.C_LIT_GRAY);
      pbOwner.fill(EcFactory.C_DIM_GRAY);
      pbOwner.ellipse(cmX, cmY, cmW-1, cmH-1);
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
    
    public final void ccSetDirection(char pxMode_ablr){
      cmDirection=pxMode_ablr;
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
      switch(pxStatus_acnlx){
        
        case 'a':
          cmMotor.ccSetIsContacted(true);
          cmMotor.ccSetHasAnswer(true);
          cmMotor.ccSetHasAlarm(false);
          break;
          
        case 'c':
          cmMotor.ccSetIsContacted(true);
          cmMotor.ccSetHasAnswer(false);
          cmMotor.ccSetHasAlarm(false);
          break;
          
        case 'n':
          cmMotor.ccSetIsContacted(false);
          cmMotor.ccSetHasAnswer(true);
          cmMotor.ccSetHasAlarm(false);
          break;
          
        case 'l':
          cmMotor.ccSetIsContacted(false);
          cmMotor.ccSetHasAnswer(false);
          cmMotor.ccSetHasAlarm(true);
          break;
        
        default:
          cmMotor.ccSetIsContacted(false);
          cmMotor.ccSetHasAnswer(false);
          cmMotor.ccSetHasAlarm(false);
          break;
      }
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
      cmKPABox.ccSetColor(EcFactory.C_PUEPLE,EcFactory.C_DIM_BLUE);
      
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
      cmTPHBox.ccSetColor(EcFactory.C_PUEPLE,EcFactory.C_DIM_YELLOW);
      
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
      switch(pxStatus_acnlx){
        
        case 'a':
          cmPump.ccSetIsContacted(true);
          cmPump.ccSetHasAnswer(true);
          cmPump.ccSetHasAlarm(false);
        break;
          
        case 'c':
          cmPump.ccSetIsContacted(true);
          cmPump.ccSetHasAnswer(false);
          cmPump.ccSetHasAlarm(false);
        break;
          
        case 'n':
          cmPump.ccSetIsContacted(false);
          cmPump.ccSetHasAnswer(true);
          cmPump.ccSetHasAlarm(false);
        break;
          
        case 'l':
          cmPump.ccSetIsContacted(false);
          cmPump.ccSetHasAnswer(false);
          cmPump.ccSetHasAlarm(true);
        break;
        
        default:
          cmPump.ccSetIsContacted(false);
          cmPump.ccSetHasAnswer(false);
          cmPump.ccSetHasAlarm(false);
        break;
      }
    }//+++
    
    public final void ccSetFuelON(boolean pxStatus){
      cmFuelPL.ccSetActivated(pxStatus);
    }//+++
    
    public final void ccSetHeavyON(boolean pxStatus){
      cmHeavyPL.ccSetActivated(pxStatus);
    }//+++
    
  }//***
  
  class EcBurner extends EcMoterizedUnit{
    
    //[HEAD]::now what?? we re working on burner!!
    
  }//***

  //=== entry
  static public void main(String[] passedArgs){
    PApplet.main(DemoPlantIcon.class.getCanonicalName());
  }//+++

}//***eof