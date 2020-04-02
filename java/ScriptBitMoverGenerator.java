/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package ppptest;

import java.io.File;
import kosui.ppputil.VcStampUtility;
import processing.core.PApplet;
import processing.data.StringDict;
import processing.data.StringList;

/**
 *
 * @author Key Parker from K.I.C
 */
public class ScriptBitMoverGenerator {
  
  /*
    LD OFF

    MPS
    AND M6550
    OUT M6560

    MRD
    AND M6551
    OUT M6561

    MRD
    AND M6552
    OUT M6562

    MPP
    AND M6553
    OUT M6563
  */
  
  static public final int C_WORD_LENGTH = 16;
  
  static public String cmFileLocation
   = "C:\\Users\\N216\\Desktop\\";
  
  static public String cmFileName
   = "bitmove";
  
  static public String cmFileExtention
   = ".txt";
  
  private String cmEnableFlag = "ON";
  
  private boolean cmReverseFrom = false;
  private boolean cmReverseTo   = false;
  private boolean cmDoesRead      = false;
  
  private final StringList cmData = new StringList();
  private final StringDict cmDesUnit = new StringDict();
  
  private final void ccInitUnitMap(){
    
    //-- apd 2
    
    //-- apd 2 di64*1
    cmDesUnit.set("X1.0", "M1008");
    cmDesUnit.set("X1.1", "M1009");
    cmDesUnit.set("X1.2", "M1010");
    cmDesUnit.set("X1.3", "M1011");
    
    //-- apd 2 do16*5
    cmDesUnit.set("M1012", "Y2.");
    cmDesUnit.set("M1013", "Y3.");
    cmDesUnit.set("M1014", "Y4.");
    cmDesUnit.set("M1015", "Y5.");
    cmDesUnit.set("M1016", "Y6.");
    
    //-- apd 2 di32*1
    cmDesUnit.set("X7.0", "M1017");
    cmDesUnit.set("X7.1", "M1018");
    
    //-- apd 1
    
    //-- apd 1 di64*1
    cmDesUnit.set("X170.10.0", "M1030");
    cmDesUnit.set("X170.10.1", "M1031");
    cmDesUnit.set("X170.10.2", "M1032");
    cmDesUnit.set("X170.10.3", "M1033");
    
    //-- apd 1 di32*1
    cmDesUnit.set("X170.11.0", "M1034");
    cmDesUnit.set("X170.11.1", "M1035");
    
    //-- apd 1 do16*5
    cmDesUnit.set("M1036", "Y170.12.");
    cmDesUnit.set("M1037", "Y170.13.");
    cmDesUnit.set("M1038", "Y170.14.");
    cmDesUnit.set("M1040", "Y170.15.");
    
  }//+++
  
  private final void ccStartLine(){
    cmData.append(String.format("LD %s\r\n", cmEnableFlag));
  }//+++
  
  private final void ccEndLine(){
    cmData.append("\r\n;------\r\n");
  }//+++
  
  private final void ccAddSingleLine(int pxOrder,String pxFromM, String pxToM){
    
    //-- indent
    switch (pxOrder) {
      case 0:cmData.append("MPS");break;
      case 15:cmData.append("MPP");break;
      default:cmData.append("MRD");break;
    }//...?
    
    //-- from
    cmData.append(String.format(
      "AND %s%s",
      pxFromM,PApplet.hex(
        cmReverseFrom?(C_WORD_LENGTH-pxOrder-1):pxOrder,
        1
      ))
    );
    
    //-- to
    cmData.append(String.format(
      "OUT %s%s\r\n",
      pxToM,PApplet.hex(
        cmReverseTo?(C_WORD_LENGTH-pxOrder-1):pxOrder,
        1
      ))
    );
    
  }//..?
  
  private final void ccProcessSingleLine(String pxFromM, String pxToM){
    
    ccStartLine();
    for(int i=0;i<C_WORD_LENGTH;i++){
      ccAddSingleLine(i,pxFromM,pxToM);
    }//..?
    ccEndLine();

  }//+++
  
  private final void ccProcessAll(){
    
    for(String it : cmDesUnit.keyArray()){
      if(cmDoesRead){
        System.out.println(it+":"+cmDesUnit.get(it));
      }//..?
      ccProcessSingleLine(it, cmDesUnit.get(it));
    }//..~
  
  }//+++
  
  private final void ccExportFile(){
    try {
      processing.core.PApplet.saveStrings(
        new File(
          cmFileLocation+cmFileName
            +VcStampUtility.ccFileNameTypeIII()+cmFileExtention
        ), cmData.array()
      );
    } catch (Exception e) {
      System.err.println("ppptest.ScriptBitMoverGenerator.ccProcess() $ "
        + e.getLocalizedMessage()
      );
    }
  }//+++
  
  public static void main(String[] args) {
    
    //-- enter
    System.out.println("ppptest.ScriptBitMoverGenerator.main::enter");
    
    //-- run
    ScriptBitMoverGenerator lpSelf = new ScriptBitMoverGenerator();
    lpSelf.ccInitUnitMap();
    lpSelf.ccProcessAll();
    lpSelf.ccExportFile();
    
    //-- exit
    System.out.println("ppptest.ScriptBitMoverGenerator.main::exit");
    
  }//!!!
  
}//**eof
