/* *
 * Play Echoer
 * 
 * this is a dummy example.
 * replace this title comment with 
 *   something better. 
 *
 * + keybord input: 
 *  - [Q]:exit program
 *  - [N]:hide/show tagged infos
 *  - [M]:hide/show mouse axis 
 *  - [,]:reset mouse anchor
 *  - [enter]:input command
 *
 * + command input:
 *  - [quit]:exit program
 *  - [help]:look for help info(but you ll get nothing)
 *
 * (this is supposed to be an example sketch of project kosui )
 * (for Processing 2.x core )
 */
 

import kosui.ppplocalui.*;

void setup(){
  
  size(320,240);
  EcFactory.ccInit(this);
  VcConsole.ccSetIsMessageBarVisible(true);
  VcConsole.ccSetWatchBarVisible(true);
  
  VcConsole.ccAddOperation("quit", new ViOperable() {
    @Override public void ccOperate(String[] pxLine){
      println("--exit..");
      exit();
    }//+++
  });
  VcConsole.ccAddOperation("echo", new ViOperable() {
    @Override public void ccOperate(String[] pxLine){
      for(String it:pxLine){
        VcStacker.ccStack(it);
      }//..~
    }//+++
  });
  println("--done setup..");
  
}//+++

void draw(){
  background(0);
  VcStacker.ccUpdate();
  VcConsole.ccWatch("character_count", VcStacker.ccGetSize());
  VcConsole.ccUpdate();
  VcConsole.ccStabilize();
}//+++

void keyPressed(){
  if(VcConsole.ccKeyTyped(key, keyCode)){return;}
}//++

