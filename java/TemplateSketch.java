package ppptest;

import processing.core.*;
import processing.data.*;
import processing.event.*;
import processing.opengl.*;

public class TemplateSketch extends PApplet{
  
  public static int pbRoller = 0;

  //=== overridden
  
  @Override public void setup() {
    
    //-- pre setting
    size(320,240);
    noSmooth();
    frame.setTitle("TemplateSketch");
    
    //-- replace setting
    frameRate(16);noStroke();textAlign(LEFT, TOP);ellipseMode(CENTER);
    
    //-- post setting
    println("-- setup over");
    
  }//+++

  @Override public void draw() {
  
    //-- pre drawing
    background(0);
    pbRoller++;pbRoller&=0x0f;
    
    //-- updating
    fill(0xFF);
    text(nf(pbRoller,2),5,5);
    
    //-- tagging
    
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
      case 'f':break;
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
    println("--exit <- ");
    exit();
  }//+++
  
  //=== inner
  
  //=== entry
  
  public static void main(String[] args) {
    PApplet.main(TemplateSketch.class.getCanonicalName());
  }//++!
  
}//***eof
