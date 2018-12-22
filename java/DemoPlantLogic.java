//package ;

/* *
 *
 * PLANT LOGIC
 *
 * * 
 */
import java.util.ArrayList;
import processing.core.*;

import kosui.ppplocalui.*;
import kosui.ppplogic.*;

public class DemoPlantLogic extends PApplet{

  static public int pbRoller=0;

  static private ZcVSupplyTask ttt;

  //=== overridden
  @Override public void setup(){

    //-- pre setting 
    size(320, 240);
    EcFactory.ccInit(this);

    //--
    ttt=new ZcVSupplyTask();

    //-- post setting
    println("--done setup");
  }//+++

  @Override public void draw(){

    //-- pre drawing
    background(0);
    pbRoller++;
    pbRoller&=0x0F;

    //-- updating
    //-- updating ** linking
    ttt.mnOneSecodPulse=pbRoller==8;
    ttt.mnHalfSecondFlicker=pbRoller<7;
    ttt.mnVSuuplyMotorRunSW=fnIsKeyPressed('f');
    

    //-- updating ** running
    ttt.ccScan();
    ttt.ccSimulate();

    //-- tagging
    VcTagger.ccTag("time", pbRoller);
    VcTagger.ccTag("----",0);
    VcTagger.ccTag("--1--",ttt.dcMC1);
    VcTagger.ccTag("--2--",ttt.dcMC2);
    VcTagger.ccTag("--3--",ttt.dcMC3);
    VcTagger.ccTag("--4--",ttt.dcMC4);
    VcTagger.ccTag("--5--",ttt.dcMC5);
    VcTagger.ccTag("--SWPL--",ttt.mnVSuuplyMotorRunPL);
    
    //-- tagging ** over
    VcTagger.ccTag("----");

    VcTagger.ccStabilize();

  }//+++

  @Override public void keyPressed(){
    switch(key){
      //-- triiger

      //-- system 
      case 'q': fsPover();
        break;
      default: break;
    }
  }//+++

  //=== operate
  boolean fnIsKeyPressed(char pxKey){
    return keyPressed&&(key==pxKey);
  }//+++

  void fsPover(){
    //-- flushing

    //-- defualt
    println("--exiting from PApplet");
    exit();
  }//+++

  //=== utiliry
  //=== inner
  //=== inner ** component
  
  class ZcCMDamper{

    private final ZcCheckedValueModel cmControlMotor;

    public ZcCMDamper(){
      cmControlMotor=new ZcCheckedValueModel(399, 3598);
      cmControlMotor.ccAddChecker(399, 200);
      cmControlMotor.ccAddChecker(3300, 199);
    }//++!

    public void ccSimulate(boolean pxOpen, boolean pxClose){
      if(pxOpen){cmControlMotor.ccShift(24);}
      if(pxClose){cmControlMotor.ccShift(-24);}
    }//+++

    public boolean ccIsClosed(){
      return cmControlMotor.ccCheckFor(0);
    }//+++

    public boolean ccIsFull(){
      return cmControlMotor.ccCheckFor(1);
    }//+++

    public int ccGetDegreeAD(){
      return cmControlMotor.ccGetValue();
    }//+++

  }//***
  
  //=== inner ** task

  class ZcVSupplyTask implements ZiTask{

    public boolean
      mnOneSecodPulse,mnHalfSecondFlicker,
      mnVBUO, mnVBUC, mnVDOSW, mnVDCSW,
      mnVDOL, mnVDCL,
      mnVSuuplyMotorRunSW,mnVSuuplyMotorRunPL,
      
      mnAN1,mnAN2,mnAN3,mnAN4,mnAN5
    ;//...

    public int
      mnVDO, mnVBO
    ;//...
    
    private int
      //--
      cmSuplyChainInterval=0
      //--
    ;//...
    
    private boolean
      cmOneSecondFlicker,
      dcMC1,dcMC2,dcMC3,dcMC4,dcMC5
      ;

    private final ZcCMDamper
      dcVEXFanDamper=new ZcCMDamper(),
      dcVBurnerFanDamper=new ZcCMDamper()
    ;//...
    
    

    @Override
    public void ccScan(){
      
      //-- system
      if(mnOneSecodPulse){cmOneSecondFlicker=!cmOneSecondFlicker;}
      
      //-- motor power
      if(mnVSuuplyMotorRunSW&&mnOneSecodPulse){cmSuplyChainInterval++;}
      if(!mnVSuuplyMotorRunSW){cmSuplyChainInterval=0;}
      dcMC1=cmSuplyChainInterval>2;
      dcMC2=cmSuplyChainInterval>3;
      dcMC3=cmSuplyChainInterval>4;
      dcMC4=cmSuplyChainInterval>5;
      dcMC5=cmSuplyChainInterval>6;
      mnAN1=dcMC1;
      mnAN2=dcMC2;
      mnAN3=dcMC3;
      mnAN4=dcMC4;
      mnAN5=dcMC5;
      mnVSuuplyMotorRunPL=
        mnVSuuplyMotorRunSW&&(
          (!mnAN5&&mnHalfSecondFlicker)
         ||(mnAN5)
        )
      ;
      
      //[HEAD]::now what??
      
      //-- contorl
      //-- control ** v exf damper
      mnVDOL=dcVEXFanDamper.ccIsFull();
      mnVDCL=dcVEXFanDamper.ccIsClosed();
      mnVDO=dcVEXFanDamper.ccGetDegreeAD();
      
      //-- control ** v burner damper
      mnVBO=dcVBurnerFanDamper.ccGetDegreeAD();
      
      

    }//+++

    @Override
    public void ccSimulate(){

      dcVBurnerFanDamper.ccSimulate(mnVBUO, mnVBUC);
      dcVEXFanDamper.ccSimulate(mnVDOSW, mnVDCSW);

    }//+++

  }//+++

  //=== entry
  static public void main(String[] passedArgs){
    String[] appletArgs=new String[]{"DemoPlantLogic"};
    if(passedArgs!=null){
      PApplet.main(concat(appletArgs, passedArgs));
    }else{
      PApplet.main(appletArgs);
    }
  }//+++

}//***eof
