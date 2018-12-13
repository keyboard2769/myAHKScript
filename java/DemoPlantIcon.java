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

public class DemoPlantIcon extends PApplet {

  //=== 
  static final int C_SHAPE_METAL = 0xFF999999;//supposedly deep gray

  static final int C_LAMP_MC = 0xFFCC0000;//supposedly orange

  static final int C_LAMP_AN = 0xFF00CC00;//supposedly green

  static final int C_LAMP_AL = 0xFFCC0066;//supposedly red

  static final int C_LAMP_LS = 0xFF0066CC;//supposedly blue

  static final int C_LAMP_LV = 0xFF00EE00;//supposedly green

  static final int C_BOX_TEXT = 0xFFEEEEEE;//supposedly dim gray

  static final int C_BOX_DEGREE = 0xFF666666;//supposedly dim gray

  static final int C_FEEDER_STUCK = 0xFF666666;//supposedly dim gray

  static final int C_FEEDER_FLOWING = 0xFF33CC33;//supposedly green

  //=== public
  static volatile int pbRoller;

  EcFeeder ttFeeder;

  //=== overridden
  @Override
  public void setup() {

    size(800, 600);
    //noSmooth();
    //-- initiating
    EcFactory.ccInit(this);

    //-- binding
    //-- construction
    ttFeeder = new EcFeeder("AG1", 100, 100, 1590);

    //--post setting
  }//+++

  @Override
  public void draw() {

    //-- pre draw
    background(0);
    pbRoller++;
    pbRoller &= 0x0F;
    boolean lpHalsec = pbRoller < 7;
    int lpTestValue = ceil(map(mouseX, 0, width, 0, 1100));

    //-- local loop
    fill(lpHalsec ? 0xEE : 0x33);
    text("Running...", 2, 2);

    
    ttFeeder.ccSetMotorStatus('x');
    if(lpTestValue<200){ttFeeder.ccSetMotorStatus('l');}
    if(lpTestValue>600){ttFeeder.ccSetMotorStatus('n');}
    
    ttFeeder.ccSetIsSG(lpTestValue < 500);
    ttFeeder.ccSetRPM(lpTestValue);
    ttFeeder.ccUpdate();

    //-- system loop
    //-- tagging
    VcTagger.ccTag("testVal", lpTestValue);
    VcTagger.ccStabilize();

  }//+++

  @Override
  public void keyPressed() {
    switch (key) {
      case 'q':
        fsPover();
      default:
        break;
    }//..?
  }//+++

  //=== operate
  void fsPover() {

    //-- flushsing or closign
    //-- post exit
    println("::exit from main sketch.");
    exit();
  }//+++

  //=== utiliry
  //=== inner
  //=== ** for localui
  public class EcValueBox extends EcTextBox {

    protected int cmDigit = 4;

    protected String cmUnit = " ";

    @Override
    public void ccUpdate() {

      stroke(0xCC);
      ccActFill();
      pbOwner.rect(cmX, cmY, cmW, cmH);
      noStroke();

      pbOwner.fill(cmTextColor);
      pbOwner.textAlign(RIGHT, CENTER);
      pbOwner.text(
        cmText + cmUnit, cmX + cmW,
        EcFactory.C_TEXT_ADJ_Y + ccCenterY()
      );
      pbOwner.textAlign(LEFT, TOP);

      ccDrawName(EcFactory.C_LABEL_TEXT);

    }//+++

    public final void ccSetValue(int pxVal) {
      cmText = nf(pxVal, cmDigit);
    }//+++

    public final void ccSetValue(int pxVal, int pxDigit) {
      cmDigit = pxDigit;
      cmText = nf(pxVal, cmDigit);
    }//+++

    public final void ccSetValue(float pxVal, int pxDigit) {
      cmDigit = pxDigit;
      cmText = nfc(pxVal, cmDigit);
    }//+++

    public final void ccSetUnit(String pxUnit) {
      cmUnit = pxUnit;
    }//+++

  }//***

  public class EcStatusLamp extends EcLamp {

    private int cmStatus;

    private final ArrayList<Integer> cmStatusColorList;

    public EcStatusLamp() {
      super();
      cmStatus = 0;
      cmStatusColorList = new ArrayList<>(16);
      cmStatusColorList.add(EcFactory.C_DIM_GRAY);
      cmStatusColorList.add(EcFactory.C_DIM_RED);
    }//++!

    /**
     * {@inheritDoc }
     */
    @Override
    public void ccUpdate() {

      ccDrawRoundLampAtCenter(cmStatusColorList.get(cmStatus)
      );
      ccDrawTextAtCenter(EcFactory.C_LAMP_TEXT);
      ccDrawName(EcFactory.C_LABEL_TEXT);

    }//+++
    
    public final void ccSetStatus(int pxStatus){
      cmStatus=pxStatus;
    }//+++

    public final void ccReplaceStatusColor(int pxIndex, int pxColor) {
      cmStatusColorList.set(pxIndex, pxColor);
    }//+++
    
    public final void ccAddStatusColor(int pxColor){
      cmStatusColorList.add(pxColor);
    }//+++

  }//***

  //=== ** for plant shape
  public class EcHopperShape extends EcShape {

    protected int cmCut = 2;

    private int cmHoldLength = 6;

    @Override
    public void ccUpdate() {

      pbOwner.fill(cmBaseColor);
      pbOwner.rect(cmX, cmY, cmW, cmH - cmCut);
      pbOwner.quad(
        cmX, cmY + cmHoldLength,
        cmX + cmW, cmY + cmHoldLength,
        cmX + cmW - cmCut, cmY + cmH,
        cmX + cmCut, cmY + cmH
      );

    }//+++

    public final void ccSetCut(int pxCut) {
      if (pxCut >= (cmW / 2)) {
        cmCut = (cmW / 2) - 1;
      } else {
        cmCut = pxCut;
      }
      cmHoldLength = cmH - cmCut;
    }//+++

  }//***

  public class EcBelconShape extends EcShape {

    @Override
    public void ccUpdate() {
      pbOwner.fill(cmBaseColor);
      pbOwner.rect(cmX, cmY, cmW, cmH, cmH / 2);
    }//++

    public final void ccSetLength(int pxL) {
      cmW = pxL;
    }//+++

  }//***

  //=== ** for plant unit
  class EcFeeder extends EcShape {

    private final EcValueBox cmBox;

    private final EcGauge cmGauge;

    private final EcHopperShape cmHopper;

    private final EcBelconShape cmBelt;

    private final EcStatusLamp cmLamp;

    public EcFeeder(String pxName, int pxX, int pxY, int pxHeadID) {

      super();
      ccSetLocation(pxX, pxY);

      cmBox = new EcValueBox();//[TODO]::make a factoryline later
      cmBox.ccSetLocation(cmX, cmY);
      cmBox.ccSetValue(555, 4);//[TODO]::
      cmBox.ccSetUnit("r");
      cmBox.ccSetTextColor(C_BOX_TEXT);
      cmBox.ccSetColor(C_BOX_DEGREE, C_BOX_DEGREE);
      cmBox.ccSetSize();

      ccSetSize(cmBox.ccGetW() + 1, cmBox.ccGetW() + 1);

      cmGauge = EcFactory.ccCreateGauge(pxName, true, false, cmW - 1, 4);//[TODO]::
      cmGauge.ccSetLocation(cmBox, 0, -cmBox.ccGetH() - 4 - 1);
      cmGauge.ccSetNameAlign('a');
      cmGauge.ccSetColor(C_FEEDER_STUCK, C_FEEDER_FLOWING);

      cmHopper = new EcHopperShape();
      cmHopper.ccSetBound(cmX, cmY, cmW, cmH);
      cmHopper.ccSetCut(cmW / 4);//[TODO]::make static later
      cmHopper.ccSetBaseColor(C_SHAPE_METAL);

      cmBelt = new EcBelconShape();
      cmBelt.ccSetSize(cmW, 10);//[TODO]::make static later
      cmBelt.ccSetLocation(cmHopper, 0, 4);
      cmBelt.ccSetBaseColor(C_SHAPE_METAL);

      //[TODO]::i want something like this:
      //  cmLamp=EcFactory.ccCreateStatusLamp(key,w,h,color,id);
      cmLamp = new EcStatusLamp();
      cmLamp.ccSetSize(10,10);
      cmLamp.ccSetLocation(cmBelt.ccGetX(),cmBelt.ccGetY());
      cmLamp.ccSetNameAlign('x');
      cmLamp.ccSetText(" ");
      cmLamp.ccReplaceStatusColor(1, EcFactory.C_LIT_RED);
      cmLamp.ccAddStatusColor(EcFactory.C_LIT_YELLOW);

    }//++!

    @Override
    public void ccUpdate() {

      cmHopper.ccUpdate();
      cmBelt.ccUpdate();

      cmGauge.ccUpdate();
      cmBox.ccUpdate();
      
      cmLamp.ccUpdate();

    }//+++

    public final void ccSetRPM(int pxVal) {
      cmGauge.ccSetPercentage(pxVal, 1800);
      cmBox.ccSetValue(pxVal);
    }//+++

    /**
     * if SG is ON that means the feeder is STUCKED!!
     */
    public final void ccSetIsSG(boolean pxStatus) {
      cmGauge.ccSetActivated(pxStatus);
    }//+++
    
    /**
     * #
     * @param pxStatus_nlx [n]AN..[l]AL..[x|]OFF..
     */
    public final void ccSetMotorStatus(char pxStatus_nlx){
      switch(pxStatus_nlx){
        case 'n':cmLamp.ccSetStatus(2);break;
        case 'l':cmLamp.ccSetStatus(1);break;
        default:cmLamp.ccSetStatus(0);
      }
    }//+++
    
  }//***

  //=== entry
  static public void main(String[] passedArgs) {
    PApplet.main(DemoPlantIcon.class.getCanonicalName());
  }//+++

}//***eof
