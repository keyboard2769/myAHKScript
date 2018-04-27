
import os
import shutil
import re
import time
import datetime
import zipfile

#* *** *** ***
#**
#* var
#**
#* *** *** ***

#-- Const
csTargetDirect = "C:\\Users\\2053.DOMAINH\\Desktop"

csBaseName="\\向先作成ベース"
csPlcFolderName="\\PLCプログラム\\NBD-SX"

csFileNameDA="\\SA000S_図番構成表(制御装置)_.xls"
csFileNameDB="\\SA000S_動力明細_.xls"
csFileNameDC="\\SA000S_入出力レイアウト(ZEUS-SX)_.xls"
csFileNameDD="\\SA000S_現地配線材料_.xls"
csFileNameDE="\\SA000S_一次電源(提出用)_.xls"

csFileNameMA="\\SA000S_操作盤（ｎｅｘｔ－Ｄｕｏ）購入仕様書1507211_.xls"
csFileNameMB="\\DuoSX_SA000S_タイマ・MSG一覧(標準ベース)_.xls"
csFileNameMC="\\SA000S_電子見積依頼_.xls"
csFileNameMD="\\0000000_SA000S(向先名)_内容（Ｄｕｏ）150420_.xls"



csTargetPattern = re.compile("000S")
csNumberPattern = re.compile("^[0-9]{4}([pP]{1}[0-9]{0,2}){0,1}$")
csPlcFileNamePattern = re.compile("(.{0,32})(zpj3)$")


#-- public

pbAskedNumber="0000"
pbDirect = os.getcwd()
pbFileList = os.listdir(pbDirect)

#* *** *** ***
#**
#* overriden
#**
#* *** *** ***

def main():

  #--
  print("--仕様書自動複製フローに入ります：")
  print("=== "*4)
  print("--現在位置は："+str(pbDirect))
  if fnCheckFile(str(pbDirect))!=0:
    print("=== "*4)
    print("--以下ファイル排列は確認されています：")
    print(pbFileList)
    print("=== "*4)
  
    #--
    print("-- 製番の入力は数字のみとします：")
    print("-- **  (例えば「SA3351」の場合は「3351」だけを入れましょう)：")
    print("-- **  (P番の場合二桁までは受入れ可能とされます)：")
    global pbAskedNumber
    while 1:
      pbAskedNumber=input("--製番の入力：")
      if re.match(csNumberPattern,pbAskedNumber) :break
      print("--違法な数字入力："+pbAskedNumber)
    print("--確認された製番："+pbAskedNumber)
    print("=== "*4)


    #--
    print("-- 以下のモードがサポートされます：")
    print("-- ** [d]動力盤..[m]操作盤..[].")
    print("-- ** [back]ﾍﾞｰｽ圧縮..")
    lpAskedMode= input("-- モードの入力::")
    lpCode=fsMainGate(lpAskedMode)
    print("--確認された動作："+str(lpCode))
    print("=== "*4)

  #--
  lpEnd=input("--何かをいれて終了させましょう：")
  print("--確認された終了："+lpEnd)
  return
#+++




#* *** *** ***
#**
#* gate
#**
#* *** *** ***


def fsMainGate(pxMode):
  global pbAskedNumber
  if pxMode == "d":return fsCopyModeD(pbAskedNumber)
  if pxMode == "m":return fsCopyModeM(pbAskedNumber)
  #[DEL]::if pxMode == "p":return fsCopyModeP(pbAskedNumber)...we shuold think about it later
  if pxMode == "back":return fsBackup()
  return "unhandled"
#+++


#<<< <<< gate


#* *** *** ***
#**
#* Support
#**
#* *** *** ***


def fsBackup():
  lpSourcePath=pbDirect
  lpTargetPath=csTargetDirect+csBaseName
  lpTargetPath=lpTargetPath+fnTellCurrentTimeAsString(4)+".zip"
  try:
    uvMakeZip(lpSourcePath,lpTargetPath)
    return "done"
  except BaseException:
    print("--[err]失敗と見なされた圧縮動作："+lpSourcePath+" to "+lpTargetPath)
    return "n/n"
#+++


#[DEL]::
#..def fsCopyModeP(pxNumber):
#..  lpList= os.listdir(pbDirect+csPlcFolderName)
#..lpName = "n/a"
#..  for it in lpList:
#..    if re.match(csPlcFileNamePattern,it) :
#..      lpName=it
#..      break
#..  lpSourcePath=pbDirect+csPlcFolderName+"\\"+lpName
#..  lpTargetPath=str(csTargetDirect+"\\"+fsRenameWithNumber(lpName,pxNumber))
#..  lpTargetPath=str(re.sub(re.compile(".zpj3$"),fnTellCurrentTimeAsString(4)+".zpj3",lpTargetPath))#[TODO]...clean this later
#..  if os.path.exists(lpSourcePath):
#..    shutil.copyfile(lpSourcePath,lpTargetPath)
#..    print("--確認された標的："+lpName)
#..    return "done"
#..  print("[err]--標的は見つからない："+lpSourcePath)
#..  return "n/e"  
#..+++


def fsCopyModeM(pxNumber):
  lpRes="n/a"
  lpRes=fsFileCopy(csFileNameMA,pxNumber)
  lpRes=fsFileCopy(csFileNameMB,pxNumber)
  lpRes=fsFileCopy(csFileNameMC,pxNumber)
  lpRes=fsFileCopy(csFileNameMD,pxNumber)
  return lpRes
#+++
  


def fsCopyModeD(pxNumber):
  lpRes="n/a"
  lpRes=fsFileCopy(csFileNameDA,pxNumber)
  lpRes=fsFileCopy(csFileNameDB,pxNumber)
  lpRes=fsFileCopy(csFileNameDC,pxNumber)
  lpRes=fsFileCopy(csFileNameDD,pxNumber)
  lpRes=fsFileCopy(csFileNameDE,pxNumber)
  return lpRes
#+++


def fsFileCopy(pxFileName, pxNewNumber):
  lpSourcePath=str(pbDirect+pxFileName)
  lpTargetPath=str(csTargetDirect+fsRenameWithNumber(pxFileName,pxNewNumber))
  lpTargetPath=str(re.sub(re.compile(".xls$"),"kkkk.xls",lpTargetPath))#[TODO]...clean this later
  if os.path.exists(lpSourcePath):
    shutil.copyfile(lpSourcePath,lpTargetPath)
    print("--確認された標的："+pxFileName)
    return "done"
  print("[err]--標的は見つからない："+pxFileName)
  return "n/e"
#+++



def fsRenameWithNumber(pxFileName,pxNumber):
  lpRes = re.sub(csTargetPattern,pxNumber,pxFileName)
  return lpRes
#+++


#<<< <<< Support



#* *** *** ***
#**
#* Utility
#**
#* *** *** ***

def fnCheckFile(pxFilePath):
  print ("--パスの有効性を確認します:"+pxFilePath)
  lpCheckSum=0
  if os.access(pxFilePath, os.F_OK):
    print ("--パスは存在しています。")
    lpCheckSum=+1
  if os.access(pxFilePath, os.R_OK):
    print ("--パスは読み取れます。")
    lpCheckSum=+1
  if os.access(pxFilePath, os.W_OK):
    print ("--パスは書き込めます。")
    lpCheckSum=+1
  if os.access(pxFilePath, os.X_OK):
    print ("--パスは実行出来ます。")
    lpCheckSum=+1
  if lpCheckSum==0 :
    print ("--パスは確認出来ません。")
  return lpCheckSum
#+++


def fnTellCurrentTimeAsString(pxMode):
  #[REF]::print (datetime.datetime.now().strftime('%Y-%m-%d %H:%M:%S'))
  #[TODO]::[2].."_yymmdd"[3].."_yymmddhhmm"[4].."yy-mm-dd"
  if pxMode==4:return str(datetime.datetime.now().strftime('_%y%m%d%H%M'))
  return "n/m"
#+++


#<<< <<< Utility




#* *** *** ***
#**
#* unverified
#**
#* *** *** ***

def uvMakeZip(source_dir, output_filename):
  zipf = zipfile.ZipFile(output_filename, 'w')
  pre_len = len(os.path.dirname(source_dir))
  for parent, dirnames, filenames in os.walk(source_dir):
    for filename in filenames:
      pathfile = os.path.join(parent, filename)
      arcname = pathfile[pre_len:].strip(os.path.sep)
      zipf.write(pathfile, arcname)
  zipf.close()
  return
#+++

#<<< <<< unverified


#* *** *** ***
#**
#* class
#**
#* *** *** ***
#<<< <<< class


#* *** *** ***
#**
#* DONT_TOUCH_THIS
#**
#* *** *** ***
if __name__ == "__main__":
  main()
#***EOF
