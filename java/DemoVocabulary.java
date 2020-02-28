/*
 * Vocabulary
 */

package ppptest;

import java.io.File;
import processing.core.*;
import processing.data.JSONArray;
import processing.data.StringList;

public class DemoVocabulary extends PApplet{
  
  public static final String C_LIST_LOCATION
    = "<?>";//..fit this
  
  public static final String C_FONT_LOCATION
    = "<?>";//..fit this

  public static int pbRoller = 0;

  //=== overridden
  
  McVocabulary pbVocabulary = new McVocabulary();
  int pbDrawX=160,pbDrawY=120;
  
  @Override public void setup() {
    
    //-- pre setting
    size(320,240);
    noSmooth();
    frame.setTitle("DemoVocabulary");
    
    //-- replace setting
    frameRate(4);noStroke();textAlign(LEFT, TOP);ellipseMode(CENTER);
    
    //-- init
    pbVocabulary.ccImportFromFile(C_LIST_LOCATION);
    
    //-- post setting
    println(".$ setup end");
    
  }//+++

  @Override public void draw() {
  
    //-- pre drawing
    background(0);
    pbRoller++;pbRoller&=0x0f;
    
    //-- updating
    if(pbRoller==14){
      ssRefresh();
    }//..?
    pbVocabulary.ccDrawVocab(pbDrawX,pbDrawY,255);
    
    //-- tagging
    textSize(11);
    fill(0xff);
    text(nf(pbRoller,2),5,5);
    
  }//+++

  @Override public void keyPressed() {
    switch(key){
      
      //-- direction
      case 'w':break;
      case 's':break;
      case 'a':break;
      case 'd':break;
      
      //-- confirm
      case 'r':break;
      case 'f':
        ssRefresh();
      break;
      case 'j':break;
      case 'k':break;
      
      //-- defult
      case 'q':ssPover();break;
      default:break;
      
    }//..?
  }//+++
  
  //=== utility
  
  private void ssPover(){
    
    //-- default
    println(".$ call PApplet.exit");
    exit();
    
  }//+++
  
  public static final boolean ccIsValidString(String pxLine){
    if(pxLine==null){return false;}
    return !pxLine.isEmpty();
  }//+++
  
  int ccGetSquaredRandomPosition(int pxLength){
    float lpMargin = ((float)(pxLength & 0xFFFF)) / 8f;
    float lpRandomized = random(lpMargin, lpMargin*7f);
    return ceil(lpRandomized);
  }//+++
  
  private void ssRefresh(){
    pbVocabulary.ccPickVocab();
    pbDrawX=ccGetSquaredRandomPosition(width);
    pbDrawY=ccGetSquaredRandomPosition(height);
  }//+++
  
  //=== inner
  
  class McVocabulary{
    
    JSONArray cmTheList=new JSONArray();
    int cmCurrentIndex = 0;
    String cmCurrentEN = "";
    String cmCurrentJP = "";
    
    void ccRegisterVocab(String pxEN, String pxJP){
      if(!ccIsValidString(pxEN)){return;}
      if(!ccIsValidString(pxJP)){return;}
      cmTheList.append(new JSONArray(new StringList(new String[]{pxEN,pxJP})));
    }//+++
    
    boolean ccImportFromFile(String pxLocation){
      JSONArray lpImported;
      try {
         lpImported = loadJSONArray(pxLocation);
      } catch (Exception e) {
        println(
          ".ccImportFromFile $ error during importing file : "
           + e.getMessage()
        );
        lpImported = null;
      }//..?
      if(lpImported!=null){
        for(int i=0,s=lpImported.size();i<s;i++){
          
          //-- get pare 
          JSONArray lpPare = lpImported.getJSONArray(i);
          if(lpPare == null){continue;}
          if(lpPare.size()<2){continue;}
          
          //-- read pare
          String lpKey = lpPare.getString(0);
          if(lpKey==null){continue;}
          if(lpKey.startsWith("@")){
            //[extend]::future instruction 
            continue;
          }//..?
          
          //-- append pare
          cmTheList.append(lpImported.getJSONArray(i));
          
        }//..~
        ccPickVocab();
      }else{
        println(".ccImportFromFile $ failed to import file");
        ccInitVocabulary();
      }//..?
      return lpImported != null;
    }//+++
    
    void ccInitVocabulary(){
      ccRegisterVocab("one", "hi");
      ccRegisterVocab("two", "fu");
      ccRegisterVocab("three", "mi");
      ccRegisterVocab("four", "yo");
    }//+++
    
    void ccExportToFile(){
      cmTheList.save(new File(C_LIST_LOCATION), "");
    }//+++
    
    void ccPickVocab(){
      cmCurrentIndex=(int)(random(1f)*(cmTheList.size()-3));
      cmCurrentEN=cmTheList.getJSONArray(cmCurrentIndex).getString(0);
      cmCurrentJP=cmTheList.getJSONArray(cmCurrentIndex).getString(1);
    }//+++
    
    void ccDrawVocab(int pxX, int pxY, int pxC){
      pushStyle();{
      
        //-- pre
        final int lpBasicSize = 16;
        fill(pxC);
        
        //-- en
        textSize(lpBasicSize);
        textAlign(CENTER, BOTTOM);
        text(cmCurrentEN,pxX,pxY);
        
        //-- jp
        textSize(lpBasicSize*4);
        textAlign(CENTER, TOP);
        text(cmCurrentJP,pxX,pxY);
      
      }popStyle();
    }//+++
  
  }//***

  //=== entry
  
  public static void main(String[] args) {
    PApplet.main(DemoVocabulary.class.getCanonicalName());
  }//!!!

}//***eof
