/*
 * Copyright (C) 2018 Key Parker from K.I.C.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package kosui.ppplocalui;

//import processing.core.PApplet;
//import gifAnimation.GifMaker;

/* *
 * 
 * this used to be a wrapper class embedded into the library.
 * but now i think we should never across libraies in this way. 
 * use the original version that can be downloaded from
 * add library menu of processing separately.
 * 
 * the main idea is just a alias to the original one:
 *
 * - in setup():
 *   VcGifMaker.ccInit(this, 16, 8);
 *
 * - in draw():
 *   if(VcGifMaker.ccRecord()){exit();}
 */

final class VcGifMaker {
  
//  static private PApplet cmOwner;
//  
//  static final String C_OUTPUT_PATH="C:\\Users\\2053.DOMAINH\\Desktop\\";//...CLEAN UP THIS!!
//  
//  static private GifMaker cmMaker=null;
//  
//  static private boolean cmIsValid=false;
//  
//  static private int cmDuration = 0;
//  
    private VcGifMaker(){}//+++
//  
//  static final void ccInit
//    (PApplet pxOwner, String pxFileName, int pxFrameRate, int pxSeconds)
//  { if(pxOwner!=null){
//      cmOwner=pxOwner;
//      cmMaker=new GifMaker(pxOwner, C_OUTPUT_PATH+pxFileName+".gif");
//      cmMaker.setRepeat(0);
//      cmMaker.setDelay(40);
//      cmDuration=pxFrameRate*pxSeconds;
//      cmIsValid=true;
//    }else
//      {cmIsValid=false;}
//  }//+++
//  
//  static final boolean ccRecord(){
//    if(cmIsValid){
//      cmMaker.addFrame();
//      if(cmOwner.frameCount==1){
//        System.out.println(".VcGifMaker:"
//          +"recording GIF!! dont close application on purpose!!");
//      }
//      if(cmOwner.frameCount>cmDuration){
//        cmMaker.finish();
//        System.out.println(".VcGifMaker.ccUpdate()::finished");
//        cmIsValid=false;
//        return true;
//      } return false;
//    }
//    return false;
//  }//+++
  
}//***eof
