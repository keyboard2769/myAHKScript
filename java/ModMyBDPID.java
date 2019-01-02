import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class ModMyBDPID extends PApplet {

//--
int pbLeRoller=0;
//--
int pbBoxVTemp=0;
int pbBoxVPress=0;
//--
ScBond pbTheBond = new ScBond();
//--

public void setup(){
  size(320,240);frameRate(16);textAlign(LEFT,TOP);noSmooth();noStroke();
  frame.setTitle("Burner Dryer");
  
  //pbTheBond.ccDiffertialAmplifier();
}


public void draw(){background(0);pbLeRoller++;pbLeRoller&=0x0f;fill(0xEE);text(nf(pbLeRoller,2),0,0);
  //--
  if(pbLeRoller<7){
    fill(0x77,0xEE,0x77);
    text("Keyset down below those LABLES!!...\n[Q]to quit"
      ,2,height/2-16);
  }
  //--
  //--
  zeldaRefresh();
  pbTheBond.ccRefresh(pbLeRoller);
  drScreen(pbLeRoller==1);
  //--
}//+++

public void keyPressed(){switch(key){
  //--
  case 'w':pbTheBond.ccShiftTargetTemp( 100);break;
  case 's':pbTheBond.ccShiftTargetTemp(-100);break;
  //--
  case 'a':pbTheBond.ccShiftAggTonPerHour(-20);break;
  case 'd':pbTheBond.ccShiftAggTonPerHour( 20);break;
  //--
  case 'u':case 'W':pbTheBond.ccShiftTargetPressure( 100);break;
  case 'j':case 'S':pbTheBond.ccShiftTargetPressure(-100);break;
  //--
  case 'i':case 'D':pbTheBond.ccShiftTargetPressureRange( 10);break;
  case 'k':case 'A':pbTheBond.ccShiftTargetPressureRange(-10);break;
  //--
  case 'z':pbTheBond.ccBurnerFanSwitch();break;
  case 'x':pbTheBond.ccExfanSwitch();break;
  case 'c':pbTheBond.ccIgnite();break;
  //--
  case 'q':exit();break;
  default:break;
}}

public void zeldaRefresh(){
  //--
  pbTheBond.mnBurnerOpenSW=keyPressed&&(key=='r');
  pbTheBond.mnBurnerCloseSW=keyPressed&&(key=='f');
  //--
  pbTheBond.mnExfanOpenSW=keyPressed&&(key=='t');
  pbTheBond.mnExfanCloseSW=keyPressed&&(key=='g');
  //--
  //--
}//+++
public void drScreen(boolean pxAct){
  //--
  if(pxAct){
    pbBoxVTemp=pbTheBond.mnAggregateTempTB;
    pbBoxVPress=pbTheBond.mnDryerPressureTB;
  }
  //--
  fnDrawTemperature(  1, 1,"TH1" ,pbBoxVTemp);
  fnDrawBool( 1, 3,"BUO",pbTheBond.dcBUO);
  fnDrawBool( 1, 4,"BUC",pbTheBond.dcBUC);
  fnDrawBool( 4, 3,"MH",pbTheBond.dcMH);
  fnDrawBool( 4, 4,"ML",pbTheBond.dcML);
  //--
  fnDrawPercentage(8, 1,"BurnerDeg",pbTheBond.dcBurnerDegAD,3700);
  fnDrawPercentage(8, 2,"ExFanDeg" ,pbTheBond.dcExfanDegAD,3700);
  fnDrawPacal(       8, 4,"VSE" ,pbBoxVPress);
  //--
  fnDrawInt(  1, 7,"SetTemp..[W/S]" ,pbTheBond.mnTargetTempTB);
  fnDrawInt(  1, 8,"SetVDPress..[U/J]" ,pbTheBond.mnTargetPressureTB);
  fnDrawInt(  1, 9,"SetVDPressRNG..[I/K]" ,pbTheBond.mnTargetPressureRangeTB);
  fnDrawInt(  9, 7,"T/H..[A/D]" ,pbTheBond.cmAggTonPerHour);
  fnDrawBool( 11, 8,"#28..[Z]",pbTheBond.cmBurnerFanAN);
  fnDrawBool( 11, 9,"#10..[X]",pbTheBond.cmExfanAN);
  fnDrawBool( 11,10,"MMV..[C]",pbTheBond.cmIsOnFire);
  
  //--
}//+++
public void fnDrawBool(int pxX,int pxY,String pxLable,boolean pxVal){
  int lpGrid=20;
  noFill();stroke(0xEE);rect(pxX*lpGrid,pxY*lpGrid,16,16);noStroke();
  fill(0xEE);if(pxVal){rect(pxX*lpGrid+3,pxY*lpGrid+3,11,11);}
  text(pxLable,pxX*lpGrid+18,pxY*lpGrid);
}//+++


public void fnDrawInt(int pxX,int pxY,String pxLable,int pxVal){
  int lpGrid=20;
  noFill();stroke(0xEE);rect(pxX*lpGrid,pxY*lpGrid,48,16);noStroke();
  fill(0xEE);text(nf(pxVal,4),pxX*lpGrid+2,pxY*lpGrid+2);
  text(pxLable,pxX*lpGrid+50,pxY*lpGrid+2);
}//+++


public void fnDrawGauge(int pxX,int pxY,String pxLable,int pxVal,int pxMax){
  int lpGrid=20;
  int lpSlide=PApplet.parseInt(map(constrain(pxVal,0,pxMax),0,pxMax,0,48))+pxX*lpGrid;
  noFill();stroke(0xEE);rect(pxX*lpGrid,pxY*lpGrid+6,48,3);noStroke();
  fill(0xEE);rect(lpSlide,pxY*lpGrid,3,16);
  text(pxLable,pxX*lpGrid+50,pxY*lpGrid+2);
}//+++

public void fnDrawPercentage(int pxX,int pxY,String pxLable,int pxVal,int pxMax){
  int lpGrid=20;
  noFill();stroke(0xEE);rect(pxX*lpGrid,pxY*lpGrid,48,16);noStroke();
  fill(0xEE);text(nfc(map(constrain(pxVal,0,pxMax),0,pxMax,0,99.9f),1)+"%",pxX*lpGrid+2,pxY*lpGrid+2);
  text(pxLable,pxX*lpGrid+50,pxY*lpGrid+2);
}

public void fnDrawPacal(int pxX,int pxY,String pxLable,int pxVal){
  int lpGrid=20;
  noFill();stroke(0xEE);rect(pxX*lpGrid,pxY*lpGrid,68,16);noStroke();
  fill(0xEE);text(nfc(map(constrain(pxVal,0,5000),1500,3000,0,199.9f),1)+"kpa",pxX*lpGrid+2,pxY*lpGrid+2);
  text(pxLable,pxX*lpGrid+70,pxY*lpGrid+2);
}

public void fnDrawTemperature(int pxX,int pxY,String pxLable,int pxVal){
  int lpGrid=20;
  noFill();stroke(0xEE);rect(pxX*lpGrid,pxY*lpGrid,68,16);noStroke();
  fill(0xEE);text(nfc(map(constrain(pxVal,0,5000),10,3000,1,300),1)+"\'C",pxX*lpGrid+2,pxY*lpGrid+2);
  text(pxLable,pxX*lpGrid+70,pxY*lpGrid+2);
}



class ScBond{
  //--
  boolean mnBurnerOpenSW=false;boolean mnBurnerCloseSW=false;
  boolean mnExfanOpenSW=false;boolean mnExfanCloseSW=false;
  int mnDryerPressureTB=1500;
  int mnAggregateTempTB=370;
  //--
  int mnTargetTempTB=1500;
  int mnTargetPressureTB=1300;
  int mnTargetPressureRangeTB=100;
  //--
  int cmAggTonPerHour=320;
  boolean cmBurnerFanAN=false;
  boolean cmExfanAN=false;
  boolean cmIsOnFire=false;
  //--
  float cmPID_devi=0;
  float cmPID_offs=0;
  //--
  boolean dcBUO=false;boolean dcBUC=false;
  boolean dcMH=false;boolean dcML=false;
  int dcBurnerDegAD=888;
  int dcExfanDegAD=888;
  //--
  //--
  PVector simBurnerTemp=new PVector(320,0);
  PVector simDryerTemp=new PVector(320,0);
  PVector simAggregateTemp=new PVector(320,0);
  PVector simAirTemp=new PVector(320,0);
  //--
  PVector simBurnerPressure=new PVector(1888,0);
  PVector simDryerPressure=new PVector(1888,0);
  PVector simExfanPressure=new PVector(1888,0);
  PVector simAirPressure=new PVector(1888,0);
  //--
  ScBond(){;}
  public void ccRefresh(int pxRoller){ccTask(pxRoller);ccSimulate(pxRoller);}
  //--
  public void ccTask(int pxRoller){
    //--
    mnAggregateTempTB=PApplet.parseInt(simAggregateTemp.x);
    //--
    float lpBurnerDegTarget=0;
    if(cmIsOnFire){
      lpBurnerDegTarget=400+3200*ccPropOperate(simAggregateTemp.x,PApplet.parseFloat(mnTargetTempTB)+cmPID_offs,0.1f,0.9f);
      ccDiffOperate(simAggregateTemp.x,PApplet.parseFloat(mnTargetTempTB),0.5f,pxRoller%2==1);
      ccIntegOperate(PApplet.parseFloat(mnTargetTempTB)*0.05f,cmPID_devi,0.1f,pxRoller%4==1);
    }else{cmPID_devi=0;cmPID_offs=0;lpBurnerDegTarget=0;}
    cmPID_offs=constrain(cmPID_offs,-9999,9999);
    //--
    float lpBurnerOperate=ccPropOperate(PApplet.parseFloat(dcBurnerDegAD),lpBurnerDegTarget,0.05f,0.1f);
    //--
    fill(200);text("PID::"+nfc(cmPID_devi,2)+":"+nfc(cmPID_offs,2),5,height-16);
    //--
    boolean lpBurnerAutoOpenFLG=lpBurnerOperate> ( 0.5f);
    boolean lpBurnerAutoCloseFLG=lpBurnerOperate<(-0.5f);
    //--
    dcBUO=mnBurnerOpenSW  || (cmIsOnFire?lpBurnerAutoOpenFLG:false);
    dcBUC=mnBurnerCloseSW || (cmIsOnFire?lpBurnerAutoCloseFLG:true);
    //--
    mnDryerPressureTB=PApplet.parseInt(simDryerPressure.x);
    //--
    boolean lpExfanAutoOpenFLG =mnDryerPressureTB > (mnTargetPressureTB+mnTargetPressureRangeTB);
    boolean lpExfanAutoCloseFLG=mnDryerPressureTB < (mnTargetPressureTB-mnTargetPressureRangeTB);
    //--
    dcMH=mnExfanOpenSW  || (cmExfanAN?lpExfanAutoOpenFLG:false);
    dcML=mnExfanCloseSW || (cmExfanAN?lpExfanAutoCloseFLG:true);
    //--
    //--
    //--
    //--
  }
  //--
  public void ccSimulate(int pxRoller){
    //--
    if(dcBUO){dcBurnerDegAD+=20;}
    if(dcBUC){dcBurnerDegAD-=20;}
    dcBurnerDegAD=constrain(dcBurnerDegAD,400,3600);
    //--
    //--
    if(dcMH){dcExfanDegAD+=20;}
    if(dcML){dcExfanDegAD-=20;}
    dcExfanDegAD=constrain(dcExfanDegAD,400,3600);
    //--
    //--
    //-- > Temperature < --
    float lpRealTPH=PApplet.parseFloat(cmAggTonPerHour)+random(-5,5);
    simBurnerTemp.x=(cmIsOnFire?6800:320)*map(dcBurnerDegAD,400,3600,0.38f,0.99f);
    simAirTemp.x=320;
    ccDiffertialBalancer(simBurnerTemp,simDryerTemp,map(dcBurnerDegAD,400,3600,0.01f,0.3f)+random(0.03f,0.06f));
    ccDiffertialBalancer(simDryerTemp,simAggregateTemp,map(420-lpRealTPH,0,450,0.1f,0.2f)+random(0.03f,0.06f));
    ccDiffertialBalancer(simDryerTemp,simAirTemp,random(0.08f,0.12f));
    ccDiffertialBalancer(simAggregateTemp,simAirTemp,map(lpRealTPH,0,450,0.1f,0.2f)+random(0.03f,0.06f));
    //--
    //--
    //-- > Pressure < --
    simBurnerPressure.x=1500+(cmBurnerFanAN?1000:10)*map(dcBurnerDegAD,400,3600,0.22f,0.88f);
    simExfanPressure.x=1500-(cmExfanAN?1300:10)*map(dcExfanDegAD,400,3600,0.22f,0.88f);
    simAirPressure.x=1488;
    ccDiffertialBalancer(simBurnerPressure,simDryerPressure,random(0.08f,0.12f));
    ccDiffertialBalancer(simDryerPressure,simExfanPressure,random(0.08f,0.12f));
    ccDiffertialBalancer(simDryerPressure,simAirPressure,random(0.01f,0.05f));
    
    //--
    //--
  }
  //--
  //--
  //-- > shift PVector value < --
  private void ccDiffertialBalancer(PVector pxPlus, PVector pxMinus, float pxAmp){
    PVector lpDif=PVector.sub(pxPlus,pxMinus);
    lpDif.mult(pxAmp);
    pxPlus.sub(lpDif);
    pxMinus.add(lpDif);
  }
  //--
  //--
  //--
  //-- > PID < --
  public float ccPropOperate(float pxCurrent, float pxTarget, float pxDead, float pxProp){
    float lpDeadHi=pxTarget*(1+pxDead);
    float lpDeadLo=pxTarget*(1-pxDead);
    float lpPropHi=pxTarget*(1+pxProp);
    float lpPropLo=pxTarget*(1-pxProp);
    if(pxCurrent>lpPropLo && pxCurrent<lpDeadLo){return map(pxCurrent,lpPropLo,lpDeadLo,1.0f, 0.0f);}
    if(pxCurrent>lpDeadHi && pxCurrent<lpPropHi){return map(pxCurrent,lpDeadHi,lpPropHi,0.0f,-1.0f);}
    if(pxCurrent<=lpPropLo){return  1.0f;}
    if(pxCurrent>=lpPropHi){return -1.0f;}
    return 0.0f;
  }
  //--
  public void ccDiffOperate(float pxCurrent, float pxTarget, float pxCoeff, boolean pxClock){
    if(!pxClock){return;}
    cmPID_devi=pxCoeff*(cmPID_devi+pxTarget-pxCurrent);
  }
  //--
  public void ccIntegOperate(float pxRange,float pxOffset, float pxCoeff, boolean pxClock){
    if(!pxClock){return;}
    if(abs(pxOffset)>pxRange){
    cmPID_offs+=pxOffset*pxCoeff;}
  }
  //--
  //--
  //-- > shift primitive value < --
  //--
  public void ccBurnerFanSwitch(){cmBurnerFanAN=!cmBurnerFanAN;}
  public void ccExfanSwitch(){cmExfanAN=!cmExfanAN;}
  //--
  public void ccIgnite(){
    if(!cmBurnerFanAN){cmIsOnFire=false;return;}
    cmIsOnFire=!cmIsOnFire;
  }
  //--
  public void ccShiftAggTonPerHour(int pxOffset){cmAggTonPerHour+=pxOffset;cmAggTonPerHour=constrain(cmAggTonPerHour,10,400);}
  public void ccShiftTargetTemp(int pxOffset){mnTargetTempTB+=pxOffset;mnTargetTempTB=constrain(mnTargetTempTB,10,9990);}
  public void ccShiftTargetPressure(int pxOffset){mnTargetPressureTB+=pxOffset;mnTargetPressureTB=constrain(mnTargetPressureTB,300,1500);}
  public void ccShiftTargetPressureRange(int pxOffset){mnTargetPressureRangeTB+=pxOffset;mnTargetPressureRangeTB=constrain(mnTargetPressureRangeTB,10,200);}
  //--
  //--
}//+++
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "ModMyBDPID" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
