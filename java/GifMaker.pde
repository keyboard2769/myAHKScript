/* *
[in setup()]::
  ccInitGIF("ex_",16,8);//..(filename)
  
[in draw()]::
  if(ccRecordGIF()){exit();}//..(seconds * frameRate)
*/

import gifAnimation.*;
GifMaker pbMaker;

int pbGifMakerDuration=0;

void ccInitGIF(String pxFileName, int pxFrameRate, int pxSecond){
  pbMaker = new GifMaker(this,pxFileName+".gif"); 
  pbMaker.setRepeat(0); 
  pbMaker.setDelay(40);
  pbGifMakerDuration=pxFrameRate*pxSecond;
}//+++

boolean ccRecordGIF(){
  pbMaker.addFrame();
  if(frameCount==1){println("GifMaker:recording..");}
  if(frameCount>pbGifMakerDuration){
    pbMaker.finish();
    println("GifMaker:finished!");
    return true;
  } return false;
}//+++
