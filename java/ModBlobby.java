//package ;

/* *
 * ModBlobby
 *
 * replace this line to some description.
 * 
 * (for Processing 2.x core )
 */

import processing.core.*;

import shiffman.box2d.*;
import org.jbox2d.collision.shapes.*;
import org.jbox2d.common.*;
import org.jbox2d.dynamics.*;
import org.jbox2d.dynamics.joints.*;

import java.util.ArrayList;

import kosui.ppplocalui.VcAxis;
import kosui.ppplocalui.VcTagger;

public class ModBlobby extends PApplet{
  
  //=== public

  static private Box2DProcessing pbWorld;

  // A list we'll use to track fixed objects
  static private ArrayList<Box> pbBoundaryList;
  static private ArrayList<Ball> pbBallList;
  
  // Our "blob" object
  static private Belt pbBelt;
  static private Motor pbMotorL,pbMotorR;
  
  
  //=== overridden

  @Override public void setup(){
    
    //--
    size(400, 300);
    rectMode(CENTER);
    noSmooth();
    
    //--
    VcAxis.ccInit(this);
    VcTagger.ccInit(this, 8);

    //--
    pbWorld = new Box2DProcessing(this);
    pbWorld.createWorld();

    //--
    pbBoundaryList = new ArrayList<>();
    pbBallList = new ArrayList<>();
    
    //--
    pbBelt = new Belt();
    pbMotorL = new Motor(185, 150,'l');
    pbMotorR = new Motor(215, 150,'r');
    
    //--
    pbBoundaryList.add(fnCreateBoundary(100, 10, 10, 80));
    pbBoundaryList.add(fnCreateBoundary(100, 90, 50, 10));
    pbBoundaryList.add(fnCreateBoundary(150, 90, 10, 20));
    
    pbBoundaryList.add(fnCreateBoundary(250, 10, 10, 80));
    pbBoundaryList.add(fnCreateBoundary(210, 90, 50, 10));
    pbBoundaryList.add(fnCreateBoundary(200, 90, 10, 20));

  }//+++

  @Override public void draw(){
    
    //--
    background(0);
    
    //-- control
    boolean lpIsBlowSW=fsIsPressed('w');
    if(fsIsPressed('r')&&(random(1)<0.3f)){
      pbBallList.add(new Ball(140+random(-16,16),30,random(6,8)));
    }//..?
    
    //-- updating
    pbWorld.step();

    pbBelt.display();
    pbMotorL.display();
    pbMotorR.display();

    for(Box it : pbBoundaryList){it.display();}
    
    for(Ball it : pbBallList){it.display();}
    for(int i = pbBallList.size()-1;i>=0;i--){
      Ball it=pbBallList.get(i);
      if(lpIsBlowSW){it.applyForce(98f);}
      if(it.done()){pbBallList.remove(i);}
    }//..~
    
    //--
    pushStyle();
    {
      rectMode(CORNER);
      textAlign(LEFT,TOP);
      VcAxis.ccUpdate();
      VcTagger.ccTag("fps", nfc(frameRate,2));
      VcTagger.ccTag("amount", pbBallList.size());
      VcTagger.ccTag("speed", nfc(pbMotorL.getMotorSpeed(),2));
      VcTagger.ccTag("torque", nfc(pbMotorL.getMotorTorque(),2));
      VcTagger.ccStabilize();
    }
    popStyle();

  }//+++

  @Override public void keyPressed(){
    switch(key){
      
      case 's':
        pbMotorL.toggleMotor();
        pbMotorR.toggleMotor();
        break;
        
      case 'd':
        pbMotorL.shiftPower(1f);
        pbMotorR.shiftPower(1f);
        break;
        
      case 'a':
        pbMotorL.shiftPower(-1f);
        pbMotorR.shiftPower(-1f);
        break;
      
      
      case 'q':
        println("-- exit from ModBlobby..");
        exit();
        break;
      default:
        break;
    }//..?
  }//+++

  @Override public void mousePressed(){
    switch(mouseButton){
      case LEFT:
        break;
      case RIGHT:
        VcAxis.ccSetAnchor(mouseX, mouseY);
        break;
      default:
        break;
    }//..?
  }//+++
  
  //=== helper
  
  final boolean fsIsPressed(char pxKey)
    {return keyPressed &&(pxKey==key);}
  
  final Body fnCreateBeltCut(float pxX, float pxY){
    
    CircleShape lpShape = new CircleShape();
    lpShape.m_radius = pbWorld.scalarPixelsToWorld(4f);
    FixtureDef lpFixture = new FixtureDef();
    lpFixture.shape = lpShape;
    lpFixture.density = 1f;
    lpFixture.friction= 3f;
    BodyDef lpBodyDefinition = new BodyDef();
    lpBodyDefinition.type = BodyType.DYNAMIC;
    lpBodyDefinition.fixedRotation = true; // no rotation!
    lpBodyDefinition.position.set(pbWorld.coordPixelsToWorld(pxX, pxY));
    Body lpBody = pbWorld.createBody(lpBodyDefinition);
    lpBody.createFixture(lpFixture);
    return lpBody;
    
  }//+++
  
  final Box fnCreateBoundary(float pxX, float pxY, float pxW, float pxH){
    Box lpBox=new Box(
      pxX+pxW/2,pxY+pxH/2,
      pxW,pxH,true
    );
    return lpBox;
  }//+++
  
  //=== inner
  
  class Belt{
    
    float cmCutRadius;  
    float cmBeltLengthRadius;
    float cmCutAmount; 

    ArrayList<Body> cmCutList;
    ConstantVolumeJointDef cmJointDefinition;

    //===
    
    Belt(){
      
      cmBeltLengthRadius = 40;
      cmCutAmount = 40;
      cmCutRadius = 4;

      cmCutList = new ArrayList<>();
      cmJointDefinition = new ConstantVolumeJointDef();
      
      //-- 
      int lpStartX=150;
      int lpStartY=125;
      int lpBeltW=90;
      int lpBeltH=40;
      int lpCutDiff=10;
      for(int i=0;i<lpBeltW;i+=lpCutDiff)
        {ccAddCut(lpStartX+i, lpStartY);}
      for(int i=0;i<lpBeltH;i+=lpCutDiff){
        ccAddCut(lpStartX+lpBeltW, lpStartY+i);}
      for(int i=0;i<lpBeltW;i+=lpCutDiff)
        {ccAddCut(lpStartX+lpBeltW-i, lpStartY+lpBeltH);}
      for(int i=0;i<lpBeltH;i+=lpCutDiff)
        {ccAddCut(lpStartX, lpStartY+lpBeltH-i);}

      //-- 
      cmJointDefinition.frequencyHz = 24f;
      cmJointDefinition.dampingRatio = 1f;
      cmJointDefinition.collideConnected=true;
      pbWorld.world.createJoint(cmJointDefinition);

    }//++!
    
    private void ccAddCut(float pxX, float pxY){
      Body lpBody=fnCreateBeltCut(pxX, pxY);
      cmJointDefinition.addBody(lpBody);
      cmCutList.add(lpBody);
    }//+++

    public void display(){

      //-- draw joint
      beginShape();
      noFill();
      stroke(0xFF999999);
      strokeWeight(4);
      for(Body it : cmCutList){
        Vec2 lpPosition = pbWorld.getBodyPixelCoord(it);
        vertex(lpPosition.x, lpPosition.y);
      }//..~
      endShape(CLOSE);
      strokeWeight(1);

      //-- draw cut
      for(Body it : cmCutList){
        
        Vec2 lpPosition = pbWorld.getBodyPixelCoord(it);
        float lpAngle = it.getAngle();
        
        pushMatrix();{
          translate(lpPosition.x, lpPosition.y);
          rotate(lpAngle);
          fill(0xFFAAAAAA);
          ellipse(0, 0, cmCutRadius * 2, cmCutRadius * 2);
          stroke(0xFFEEEEEE);
          line(0,0,cmCutRadius,0);
        }popMatrix();
        
      }//..~
      
    }//+++

  }//***

  class Box{

    float cmX;
    float cmY;
    float cmW;
    float cmH;
    Body cmBody;

    //===
    
    Box(float pxX, float pxY, float pxW, float pxH, boolean pxIsStatic){
      cmX = pxX;
      cmY = pxY;
      cmW = pxW;
      cmH = pxH;

      PolygonShape lpShape = new PolygonShape();
      float lpWorldW = pbWorld.scalarPixelsToWorld(cmW / 2);
      float lpWorldH = pbWorld.scalarPixelsToWorld(cmH / 2);
      lpShape.setAsBox(lpWorldW, lpWorldH);

      BodyDef lpDefinition = new BodyDef();
      lpDefinition.type = pxIsStatic?BodyType.STATIC:BodyType.DYNAMIC;
      lpDefinition.position.set(pbWorld.coordPixelsToWorld(cmX, cmY));
      
      cmBody = pbWorld.createBody(lpDefinition);
      cmBody.createFixture(lpShape, 1);

    }//++!

    public void display(){
      fill(0xFF999999);
      stroke(0xFF333333);
      rect(cmX, cmY, cmW, cmH);
    }//+++

  }//***
  
  class Ball{

    float cmRadius;
    int cmColor;
    Body cmBody;

    Ball(float pxX, float pxY, float pxRadius){
      
      cmRadius = pxRadius;
      cmColor = 0xFF999999;
      
      makeBody(pxX, pxY, cmRadius);
      
      //..i dont know what is is for
      //[MAYNOTUSE]::cmBody.setUserData(this);

    }//++!

    private void makeBody(float pxX, float pxY, float pxRadius){
      
      //--
      CircleShape lpShape = new CircleShape();
      lpShape.m_radius = pbWorld.scalarPixelsToWorld(pxRadius);

      //--
      FixtureDef lpFixture = new FixtureDef();
      lpFixture.shape = lpShape;
      lpFixture.density = 2.0f;
      lpFixture.friction = 9.9f;
      lpFixture.restitution = 0.3f;
      
      //--
      BodyDef lpDefinition = new BodyDef();
      lpDefinition.position = pbWorld.coordPixelsToWorld(pxX, pxY);
      lpDefinition.type = BodyType.DYNAMIC;
      cmBody = pbWorld.world.createBody(lpDefinition);
      cmBody.createFixture(lpFixture);
      cmBody.setAngularVelocity(random(-10, 10));
      
    }//+++
    
    //===
    
    public void display(){
      
      Vec2 lpPosition = pbWorld.getBodyPixelCoord(cmBody);
      float lpAngle = cmBody.getAngle();
      
      pushMatrix();
      {
        translate(lpPosition.x, lpPosition.y);
        rotate(-lpAngle);
        fill(cmColor);
        stroke(0);
        strokeWeight(1);
        ellipse(0, 0, cmRadius * 2, cmRadius * 2);
        line(0, 0, cmRadius, 0);
      }
      popMatrix();
      
    }//+++

    //===

    private void killBody(){pbWorld.destroyBody(cmBody);}//+++
    
    public void applyForce(float pxForce){
      cmBody.applyForceToCenter(new Vec2(0,pxForce));
    }//+++

    public boolean done(){
      Vec2 pos = pbWorld.getBodyPixelCoord(cmBody);
      if(pos.y > height + cmRadius * 2){
        killBody();
        return true;
      }return false;
    }//+++

  }//***
  
  class Motor{

    Ball cmRotor;
    Box cmAxel;
    RevoluteJoint cmJoint;
    
    Motor(float pxX, float pxY, char pxMode_lr){

      //--
      float lpDirectOffsetX;
      if(pxMode_lr=='r'){
        lpDirectOffsetX=20;
      }else{
        lpDirectOffsetX=-20;
      }//..?
      cmRotor = new Ball(pxX+lpDirectOffsetX, pxY, 15);
      cmRotor.cmColor=0x99CCCC33;
      cmAxel = new Box(pxX, pxY, 30, 10,true);

      //--
      RevoluteJointDef lpDefinition = new RevoluteJointDef();
      lpDefinition.initialize(
        cmRotor.cmBody,
        cmAxel.cmBody,
        cmRotor.cmBody.getWorldCenter()
      );
      lpDefinition.motorSpeed = PI * 2;
      lpDefinition.maxMotorTorque = 1000.0f;
      lpDefinition.enableMotor = true;
      
      cmJoint = (RevoluteJoint) pbWorld.world.createJoint(lpDefinition);
    }//++!
    
    //===
    
    public void shiftPower(float pxRank){
      float lpSpeed=cmJoint.getMotorSpeed();
      float lpTorque=cmJoint.getMaxMotorTorque();
      
      lpSpeed+=pxRank*0.3f;
      lpTorque+=pxRank*10;
      
      cmJoint.setMotorSpeed(lpSpeed);
      cmJoint.setMaxMotorTorque(lpTorque);
      
    }//+++
    
    public float getMotorSpeed(){
      return cmJoint.getMotorSpeed();
    }//+++
    
    public float getMotorTorque(){
      return cmJoint.getMaxMotorTorque();
    }//+++
    
    public void toggleMotor(){
      cmJoint.enableMotor(!cmJoint.isMotorEnabled());
    }//+++

    public boolean motorOn(){
      return cmJoint.isMotorEnabled();
    }//+++

    //===
    
    public void display(){
      
      //-- updating
      cmAxel.display();
      cmRotor.display();

      //-- draw joint
      Vec2 anchor = pbWorld.coordWorldToPixels
        (cmRotor.cmBody.getWorldCenter());
      fill(
        motorOn()?
        0xFFCC3333:0xFF555555  
      );
      stroke(0xFF111111);
      ellipse(anchor.x, anchor.y, 4, 4);
      
    }//+++

  }//***
  
  //=== entry

  static public void main(String[] passedArgs){
    String[] appletArgs = new String[]{"ModBlobby"};
    if(passedArgs != null){
      PApplet.main(concat(appletArgs, passedArgs));
    } else{
      PApplet.main(appletArgs);
    }
  }//++!

}//***eif
