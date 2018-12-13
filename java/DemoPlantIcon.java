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

public class DemoPlantIcon extends PApplet{

  //=== 
  static final int C_SHAPE_METAL=0xFF999999;//supposedly deep gray

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

  EcInclineBelcon ttBelt;

  //=== overridden
  @Override
  public void setup(){

    size(800, 600);
    //noSmooth();
    //-- initiating
    EcFactory.ccInit(this);

    //-- binding
    //-- construction
    ttBelt=new EcInclineBelcon("vhb", 200, 200, 80, 40, 1590);

    //--post setting
  }//+++

  @Override
  public void draw(){

    //-- pre draw
    background(0);
    pbRoller++;
    pbRoller&=0x0F;
    boolean lpHalsec=pbRoller<7;
    int lpTestValue=ceil(map(mouseX, 0, width, 0, 1100));

    //-- local loop
    ttBelt.ccUpdate();

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
        ttBelt.ccSetDirectionMode('l');
        break;

      case 'd':
        ttBelt.ccSetDirectionMode('r');
        break;

      case 'w':
        ttBelt.ccSetMotorStatus('n');
        break;

      case 's':
        ttBelt.ccSetMotorStatus('l');
        break;

      case 'f':
        ttBelt.ccSetIsEMS(true);
        break;

      case ' ':
        ttBelt.ccSetDirectionMode('a');
        ttBelt.ccSetMotorStatus('x');
        ttBelt.ccSetIsEMS(false);
        break;

      case 'q':
        fsPover();
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
  //=== ** for localui
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

      ccDrawRoundLampAtCenter(cmStatusColorList.get(cmStatus)
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

  //=== ** for plant shape
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

  //=== ** for plant unit
  public interface EcAbstractUnit{
  ;

  }

  public interface EcMoterizedUnit extends EcAbstractUnit{

    public void ccSetMotorStatus(char pxStatus_nlx);

  }//***

  public interface EcReversibleMoterizedUnit extends EcMoterizedUnit{

    abstract public void ccSetDirectionMode(char pxMode_alr);

  }//***

  class EcBelconUnit extends EcElement implements EcReversibleMoterizedUnit{

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

  class EcFeeder extends EcElement implements EcMoterizedUnit{

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

    }//++!

    @Override
    public void ccUpdate(){
      
      strokeWeight(20);
      stroke(C_SHAPE_METAL);{
        pbOwner.line(cmX,cmY,cmX+cmH,cmX+cmH);
        pbOwner.line(cmX+cmH,cmX+cmH,ccEndX(),ccEndY());
      }strokeWeight(1);
      noStroke();
      
      cmMotorLampL.ccUpdate();
      cmEmsLamp.ccUpdate();
      cmMotorLampR.ccUpdate();
    }//+++

  }//***

  //=== entry
  static public void main(String[] passedArgs){
    PApplet.main(DemoPlantIcon.class.getCanonicalName());
  }//+++

}//***eof
