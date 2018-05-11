/*
 * last modified _1805111101
 * this will not be a built-in part of any project.
 */

package pppplayground;

import jxl.*; 
import jxl.format.CellFormat;
import jxl.format.Colour;
import jxl.write.*; 

import java.io.File; 
import jxl.write.biff.RowsExceededException;

public class ProtoXLS {
  
  private WritableWorkbook cmWorkbook=null;
  private WritableSheet cmCurrentSheet=null;
  private WritableCell cmBufferCell=null;
  
  private int cmCurrentRow=0;
  private int cmCurrentColumn=0;
  
  //===
  
  private ProtoXLS (){}//+++
  
  static public final ProtoXLS ccNewWorkBook
    (File pxOutputFile,String pxDefaultSheetName)
  { if(pxOutputFile==null){return null;}
    ProtoXLS lpIns= new ProtoXLS();
    try{
      lpIns.cmWorkbook=Workbook.createWorkbook(pxOutputFile);
    }catch(Exception e){
      System.err.println(".$.$.ccNewWorkBook()::"+e.toString());
      return null;
    }
    lpIns.cmCurrentSheet=lpIns.cmWorkbook.createSheet(pxDefaultSheetName, 0);
    return lpIns;
  }//+++
  
  //===
  
  public final void ccNewSheet(String pxName){
    if(cmWorkbook==null){return;}
    int lpLength=cmWorkbook.getNumberOfSheets();
    cmCurrentSheet=cmWorkbook.createSheet(pxName, lpLength+1);
  }//+++
    
  //===
    
  public final void ccSetCurrentSheet(String pxName){
    String[] lpNames=cmWorkbook.getSheetNames();
    int lpIndex=-1;
    for(int i=0,s=lpNames.length;i<s;i++){
      if(lpNames[i].equals(pxName)){lpIndex=i;break;}
    }if(lpIndex == -1){return;}
    ccSetCurrentSheet(lpIndex);
  }//+++
  
  public final void ccSetCurrentSheet(int pxIndex){
    int lpMask=pxIndex&0xFFFF;
    int lpLength=cmWorkbook.getNumberOfSheets()-1;
    int lpFix=lpMask>=lpLength?lpLength:lpMask;
    cmCurrentSheet=cmWorkbook.getSheet(lpFix);
  }//+++
  
  public final void ccSetCurrentCell(int pxRow, int pxColumn)
    {cmCurrentRow=pxRow&0xFFFF;cmCurrentColumn=pxColumn&0xFFFF;}//+++
  
  public final void ccSetBackColor(Colour pxColor){
    WritableCellFormat lpApply=ccGetCurrentFormat();
    if(lpApply==null){return;}
    try{
      lpApply.setBackground(pxColor);
    } catch(WriteException e){e.printStackTrace();}
    WritableCell lpTarget=cmCurrentSheet
      .getWritableCell(cmCurrentColumn, cmCurrentRow);
    lpTarget.setCellFormat(lpApply);
  }//+++
  
  public final void ccSetForeColor(Colour pxColor){
    WritableCellFormat lpApply=ccGetCurrentFormat();
    if(lpApply==null){return;}
    WritableFont lpFont=new WritableFont(lpApply.getFont());
    Colour lpHold=lpApply.getBackgroundColour();
    try{
      lpFont.setColour(pxColor);
      lpApply.setFont(lpFont);
      lpApply.setBackground(lpHold);
    } catch(WriteException e){e.printStackTrace();}
    WritableCell lpTarget=cmCurrentSheet
      .getWritableCell(cmCurrentColumn, cmCurrentRow);
    lpTarget.setCellFormat(lpApply);
  }//+++
  
  public final void ccSetBorderLine(char pxMode, int pxStyle){
    WritableCellFormat lpApply=ccGetCurrentFormat();
    if(lpApply==null){return;}
    jxl.format.Border lpBorder;
    jxl.format.BorderLineStyle lpStyle;
    switch(pxMode){
      case 'a':lpBorder=jxl.format.Border.TOP;break;
      case 'b':lpBorder=jxl.format.Border.BOTTOM;break;
      case 'l':lpBorder=jxl.format.Border.LEFT;break;
      case 'r':lpBorder=jxl.format.Border.RIGHT;break;
      case 'x':lpBorder=jxl.format.Border.NONE;break;
      default:lpBorder=jxl.format.Border.ALL;break;
    }switch(pxStyle){
      case 1:lpStyle=jxl.format.BorderLineStyle.THIN;break;
      case 2:lpStyle=jxl.format.BorderLineStyle.MEDIUM;break;
      case 3:lpStyle=jxl.format.BorderLineStyle.THICK;break;
      default:lpStyle=jxl.format.BorderLineStyle.NONE;break;
    }
    try{
      lpApply.setBorder(lpBorder, lpStyle);
    } catch(WriteException e){e.printStackTrace();}
    WritableCell lpTarget=cmCurrentSheet
      .getWritableCell(cmCurrentColumn, cmCurrentRow);
    lpTarget.setCellFormat(lpApply);
  }//+++
  
  @Deprecated void ccSetTextStyle(char pxMode){}//[TOIMP]::if necessary
  
  @Deprecated void ccSetTextSize(int pxSize){}//[TOIMP]::if necessary
  
  public final void ccSetValue(Object pxValue)
    {ccSetValue(cmCurrentRow, cmCurrentColumn, pxValue);}//+++
  
  public final void ccSetValue(int pxR, int pxC, Object pxValue){
    ccSetCurrentCell(pxR, pxC);
    WritableCell lpRes;
    if(pxValue instanceof java.lang.Number){
      lpRes=new jxl.write.Number
        (pxC, pxR, ((java.lang.Number) pxValue).doubleValue());
    }else{lpRes=new Label(cmCurrentColumn, cmCurrentRow, pxValue.toString());}
    CellFormat lpApply=cmCurrentSheet
      .getCell(cmCurrentColumn, cmCurrentRow).getCellFormat();
    if(lpApply!=null){lpRes.setCellFormat(lpApply);}
    try{cmCurrentSheet.addCell(lpRes);}
    catch(WriteException e){e.printStackTrace();}
  }//+++
  
  //===
  
  public final void ccSetColumnWidth(int pxCharCount){
    if(cmCurrentSheet==null){return;}
    cmCurrentSheet.setColumnView(cmCurrentColumn, pxCharCount);
  }//+++
  
  public final void ccSetRowHeight(int pxHeight){
    if(cmCurrentSheet==null){return;}
    try{
      cmCurrentSheet.setRowView(cmCurrentRow, pxHeight);
    } catch(RowsExceededException e){e.printStackTrace();}
  }//+++
  
  @Deprecated int ccFindRowIndex(String pxContent, String pxColumn)
    {return 0;}//[TOIMP]::if necessary
  @Deprecated int ccFindRowIndex(String pxContent, int pxColumnIndex)
    {return 0;}//[TOIMP]::if necessary
  
  public final void ccAddRow(int pxAnchorIndex){
    if(cmCurrentSheet==null){return;}
    cmCurrentSheet.insertRow(pxAnchorIndex);
  }//+++
  
  public final void ccAddColumn(int pxAnchorIndex){
    if(cmCurrentSheet==null){return;}
    cmCurrentSheet.insertColumn(pxAnchorIndex);
  }//+++
  
  @Deprecated void ccDeleteRow(int pxIndex){}//[TOIMP]::if necessary
  
  public final void ccDeleteColumn(int pxIndex){
    if(cmCurrentSheet==null){return;}
    cmCurrentSheet.removeColumn(pxIndex);
  }//+++
  
  //===
  
  public final void ccCopyCell(int pxSourceR, int pxSourceC
    ,int pxTargetR, int pxTargetC)
  { if(pxSourceR==pxTargetR && pxSourceC==pxTargetC){return;}
    if(cmCurrentSheet==null){return;}  
    WritableCell lpSource=cmCurrentSheet.getWritableCell(pxSourceC, pxSourceR);
    if(lpSource==null){return;}
    WritableCell lpTarget=lpSource.copyTo(pxTargetC, pxTargetR);
    try{
      cmCurrentSheet.addCell(lpTarget);
    }catch(WriteException e){e.printStackTrace();}
  }//+++
  
  public final void ccCopyCell(int pxSourceR, int pxSourceC,
    int pxCount, boolean pxInVerticle) 
  { for(int i=1;i<=pxCount;i++){
      if(pxInVerticle){
        ccCopyCell(pxSourceR, pxSourceC, pxSourceR+i, pxSourceC);
      }else{
        ccCopyCell(pxSourceR, pxSourceC, pxSourceR, pxSourceC+i);
      }
    }
  }//+++
  
  public final void ccPushCell(){
    if(cmCurrentSheet==null){return;}
    cmBufferCell=cmCurrentSheet.getWritableCell(cmCurrentColumn, cmCurrentRow);
  }//+++
  
  public final void ccPopCell(){
    if(cmCurrentSheet==null){return;}
    try{
      cmCurrentSheet.addCell
        (cmBufferCell.copyTo(cmCurrentColumn, cmCurrentRow));
    } catch(WriteException e){e.printStackTrace();}
  }//+++
  
  @Deprecated void ccCopyCellValue(int pxSourceR, int pxSourceC
    ,int pxTargetR, int pxTargetC)
  {}//[TOIMP]::if necessary
  
  @Deprecated void ccCopyCellValue(int pxSourceR, int pxSourceC
    ,int pxTargetR, int pxTargetC, int pxTargetW, int pxTargetH) 
  {}//[TOIMP]::if necessary
  
  @Deprecated void ccCopyCellStyle(int pxSourceR, int pxSourceC
    ,int pxTargetR, int pxTargetC)
  {}//[TOIMP]::if necessary
  
  @Deprecated void ccCopyCellStyle(int pxSourceR, int pxSourceC
    ,int pxTargetR, int pxTargetC, int pxTargetW, int pxTargetH) 
  {}//[TOIMP]::if necessary
  
  //==
  
  public final void ccSave(){
    try{
      cmWorkbook.write();
      cmWorkbook.close();
    } catch(Exception e){
      System.err.println("$.$.ccSave()::something went wrong:"+e.toString());
    }
  }//+++
  
  //===
  
  public final int ccGetColumnCount(){
    if(cmCurrentSheet==null){return 0;}
    return cmCurrentSheet.getColumns();
  }//+++
  
  WritableCellFormat ccGetCurrentFormat(){
    if(cmCurrentSheet==null){return null;}
    Cell lpCurrentCell=cmCurrentSheet.getCell(cmCurrentColumn, cmCurrentRow);
    if(lpCurrentCell==null){return null;}
    CellFormat lpLoad=lpCurrentCell.getCellFormat();
    WritableCellFormat lpRes;
    if(lpLoad==null){lpRes=new WritableCellFormat();}
    else{lpRes=new WritableCellFormat(lpLoad);}
    return lpRes;
  }//+++
  
  public final String ccGetCurrentString(){
    if(cmCurrentSheet==null){return null;}
    Cell lpCurrentCell=cmCurrentSheet.getCell(cmCurrentColumn, cmCurrentRow);
    if(lpCurrentCell==null){return "</>";}
    String lpRes=lpCurrentCell.getContents();
    return lpRes==null?"</>":lpRes;
  }//+++
  
  Object ccGetCurrentValue(){
    if(cmCurrentSheet==null){return null;}
    Cell lpCurrentCell=cmCurrentSheet.getCell(cmCurrentColumn, cmCurrentRow);
    if(lpCurrentCell==null){return 0;}
    if(lpCurrentCell.getType() == CellType.NUMBER){
      return ((jxl.write.Number)lpCurrentCell).getValue();
    }else{
      return lpCurrentCell.getContents();
    }
  }//+++
  
  @Deprecated String[] ccGetValues(int pxR, int pxC, int pxW, int pxH)
    {return null;}//[TOIMP]::if necessary
  
}//***eof
