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
  
  static final int C_SHAPE_METAL=0xFF999999;//supposedly deep gray
  
  static final int C_LAMP_MC=0xFFCC0000;//supposedly orange
  static final int C_LAMP_AN=0xFF00CC00;//supposedly green
  static final int C_LAMP_AL=0xFFCC0066;//supposedly red
  static final int C_LAMP_LS=0xFF0066CC;//supposedly blue
  static final int C_LAMP_LV=0xFF00EE00;//supposedly green
  
  static final int C_BOX_DEGREE=0xFF666666;//supposedly dim gray
  
  
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
    ttFeeder=new EcFeeder("AG1", 100, 100, 1590);
    
    
    //--post setting
  }//+++

  @Override
  public void draw() {

    //-- pre draw
    background(0);
    pbRoller++;pbRoller &= 0x0F;
    boolean lpHalsec=pbRoller < 7;
    int lpTestValue=ceil(map(mouseX,0,320,0,100));

    //-- local loop
    
    fill(lpHalsec ? 0xEE : 0x33);
    text("Running...", 2, 2);
    
    ttFeeder.ccUpdate();
    
    
    //-- system loop
    
    //-- tagging
    VcTagger.ccTag("testVal",lpTestValue);
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
    
    protected String cmUnit=" ";

    @Override
    public void ccUpdate() {

      stroke(0xCC);
      ccActFill();
      pbOwner.rect(cmX, cmY, cmW, cmH);
      noStroke();

      pbOwner.fill(cmTextColor);
      pbOwner.textAlign(RIGHT, CENTER);
      pbOwner.text(
        cmText+cmUnit, cmX + cmW - EcFactory.C_BOX_MARG,
        EcFactory.C_TEXT_ADJ_Y + ccCenterY()
      );
      pbOwner.textAlign(LEFT, TOP);

      ccDrawName(EcFactory.C_LABEL_TEXT);

    }//+++
    
    void ccSetValue(int pxVal, int pxDigital){
      cmText=nf(pxVal,pxDigital);
    }//+++
    
    void ccSetValue(float pxVal, int pxDigital){
      cmText=nfc(pxVal, pxDigital);
    }//+++

  }//***
  
  //=== ** for plant shape
  
  public class EcHopperShape extends EcShape{
    
    protected int cmCut=2;
    private int cmHoldLength=6;

    @Override
    public void ccUpdate() {
      
      pbOwner.fill(cmBaseColor);
      pbOwner.rect(cmX,cmY,cmW,cmH-cmCut);
      pbOwner.quad( 
        cmX,cmY+cmHoldLength,
        cmX+cmW,cmY+cmHoldLength,
        cmX+cmW-cmCut,cmY+cmH,
        cmX+cmCut,cmY+cmH
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
    public void ccUpdate() {
      pbOwner.fill(cmBaseColor);
      pbOwner.rect(cmX,cmY,cmW,cmH,cmH/2);
    }//++
    
    public final void ccSetLength(int pxL){
      cmW=pxL;
    }//+++
    
  }//***
  
  //=== ** for plant unit

  class EcFeeder extends EcShape {

    boolean mnIsAX;
    boolean mnIsAL;
    boolean mnIsSG;
    int mnSpeedAD;

    EcValueBox cmBox;
    EcGauge cmGauge;
    EcHopperShape cmHopper;
    EcBelconShape cmBelt;

    public EcFeeder(String pxName,int pxX, int pxY,int pxHeadID) {
      
      super();
      ccSetLocation(pxX, pxY);
      
      mnIsAX=false;
      mnIsAL=false;
      mnIsSG=false;
      mnSpeedAD=0;
      
      cmBox=new EcValueBox();//[TODO]::make a factoryline later
      cmBox.ccSetLocation(cmX, cmY);
      cmBox.ccSetText("0000 r");
      cmBox.ccSetSize();
      
      ccSetSize(cmBox.ccGetW()+1, cmBox.ccGetW()+1);
      
      //[TODO]::make static later
      cmGauge=EcFactory.ccCreateGauge(pxName, true, false, cmW-1, 4);
      cmGauge.ccSetLocation(cmBox, 0, -cmBox.ccGetH()-4-1);
      cmGauge.ccSetNameAlign('a');
      
      cmHopper=new EcHopperShape();
      cmHopper.ccSetBound(cmX, cmY, cmW, cmH);
      cmHopper.ccSetCut(cmW/4);//[TODO]::make static later
      cmHopper.ccSetBaseColor(C_SHAPE_METAL);
      
      cmBelt=new EcBelconShape();
      cmBelt.ccSetSize(cmW, 8);//[TODO]::make static later
      cmBelt.ccSetLocation(cmHopper, 0, 4);
      cmBelt.ccSetBaseColor(C_SHAPE_METAL);
      
    }//++!
    
    @Override
    public void ccUpdate() {
      
      cmHopper.ccUpdate();
      cmBelt.ccUpdate();
      
      cmGauge.ccUpdate();
      cmBox.ccUpdate();
    
    }//+++

  }//***

  //=== entry
  static public void main(String[] passedArgs) {
    PApplet.main(DemoPlantIcon.class.getCanonicalName());
  }//+++

}//***eof
