/*
 * Camera Capture
 *
 */
 
import processing.video.*;

Capture pbVideo;

void setup() {
  size(800, 600, JAVA2D);
  frameRate(30);
  pbVideo = new Capture(this,"name=Integrated Camera,size=640x480,fps=30");
  pbVideo.start();
  //printArray(Capture.list());
  println(".$ end of setup");
}//+++

void draw() {
  text("X", mouseX, mouseY);
}//+++

void keyPressed() {
  switch(key) {
    
    case 'r':
      ccCaptureWithCamera();
    break;
    
    case 'f':
      ccExportToFile();
    break;
  
    case 'q':
      pbVideo.stop();
      pbVideo.dispose();
      exit();
    break;
    
    default:break;
    
  }//..?
}//+++

//===

void ccCaptureWithCamera(){
  if (pbVideo.available()) {
    pbVideo.read();
    image(pbVideo,0,0);
  }//..?
}//+++

void ccExportToFile(){
  println(".$ call PApplrt::saveFrame");
  saveFrame(String.format(
    "cc_%2d%2d%2d%2d%2d%2d.png",
    year(),month(),day(),
    hour(),minute(),second()
  ));
}//+++

//eof