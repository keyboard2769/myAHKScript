<#assign licenseFirst = "/*">
<#assign licensePrefix = " * ">
<#assign licenseLast = " */">
<#include "${project.licensePath}">

<#if package?? && package != "">
package ${package};

</#if>
import java.awt.Frame;
import processing.core.PApplet;

public class ${name} extends PApplet {
  
  static private ${name} self;

  static private int cmRoller=0;

  //=== overridden

  @Override public void setup() {
    println(".setup()::start");

    //-- pre
    size(320, 240);
    frameRate(16);
    textAlign(LEFT,TOP);
    ellipseMode(CENTER);
    noSmooth();
    noStroke();
    frame.setTitle("${name}");
    self=this;

    //-- init
    
    //-- init ** 
    
    //-- init ** 
    
    //-- post setting
    println(".setup()::over");
    
  }//+++
  
  @Override public void draw() { 

    //-- pre
    ssRoll();

    //-- update
    background(0);
    
    //-- update **
    
    //-- update **
    fill(0xEE);
    text(nf(cmRoller,2),5,5);
    
    //-- tag

  }//+++
  
  @Override public void keyPressed() {
    switch(key){
      
      //-- trigger

      //-- system 
      case 'q':ssPover();break;
      default:break;
    }//..?
  }//+++
  
  //=== utility

  private void ssPover(){
    
    //-- flushing
    
    //-- flushing **
    
    //-- flushing **
    
    //-- defualt
    println(".ssPover()::call PApplet.exit()");
    exit();
  }//+++
  
  private void ssRoll(){
    cmRoller++;cmRoller&=0x0F;
  }//+++
  
  //=== inner

  //=== entry
  
  static public boolean ccGetRollingAbove(int pxZeroToFifteen){
    return cmRoller>pxZeroToFifteen;
  }//+++
  
  static public boolean ccGetRollingAt(int pxZeroToFifteen){
    return cmRoller==pxZeroToFifteen;
  }//+++
  
  static public ${name} ccGetSketch(){return self;}//+++
  
  static public PApplet ccGetApplet(){return self;}//+++
  
  static public Frame ccGetFrame(){return self.frame;}//+++

  static public void main(String[] passedArgs) {
    PApplet.main(${name}.class.getCanonicalName());
  }//+++

}//***eof
