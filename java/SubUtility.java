/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package pppstockyard;

import java.util.Calendar;

/**
 *
 * @author Key Parker from K.I.C
 */
public class SubUtility {
  
  private static SubUtility self = null;
  public static SubUtility ccGetInstance() {
    if(self==null){self=new SubUtility();}
    return self;
  }//+++
  private SubUtility(){}//..!
  
  //===
  
  public static final boolean ccIsValidString(String pxLine){
    if(pxLine==null){return false;}
    return !pxLine.isEmpty();
  }//+++
  
  public static final void ccPrintln(String pxLine){
    ccPrintln(pxLine, null);
  }//+++
  
  public static final void ccPrintln(String pxTag, Object pxValue){
    if(!ccIsValidString(pxTag)){return;}
    System.out.print(pxTag);
    if(pxValue==null){
      System.out.print("");
    }else{
      System.out.print(":");
      System.out.println(pxValue.toString());
    }//..?
  }//+++
  
  //===
  
  public static final String ccTimeStamp(String pxForm){
    if(!ccIsValidString(pxForm)){return pxForm;}
    String lpRes=pxForm.replaceFirst("yy",
      String.format("%02d", Calendar.getInstance().get(Calendar.YEAR)%2000)
    );
    lpRes=lpRes.replaceFirst("MM",
      String.format("%02d", Calendar.getInstance().get(Calendar.MONTH)+1)
    );
    lpRes=lpRes.replaceFirst("dd",
      String.format("%02d", Calendar.getInstance().get(Calendar.DAY_OF_MONTH))
    );
    lpRes=lpRes.replaceFirst("hh",
      String.format("%02d", Calendar.getInstance().get(Calendar.HOUR_OF_DAY))
    );
    lpRes=lpRes.replaceFirst("mm",
      String.format("%02d", Calendar.getInstance().get(Calendar.MINUTE))
    );
    lpRes=lpRes.replaceFirst("ss",
      String.format("%02d", Calendar.getInstance().get(Calendar.SECOND))
    );
    return lpRes;
  }//+++
  
 }//***eof
