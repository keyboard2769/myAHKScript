import os
import re



def searchWorkingNumber(dummy, pxAdd):
  if dummy==0:return dummy

  lpOut="[BAD]A0000_"
  
  lpCurrentWorkingDir=os.getcwd()
  lpFileList = os.listdir(lpCurrentWorkingDir)
  lpRePattern='^(sSs)[A]\d{3,4}[pP]{0,1}\d{0,2}(\.txt)$'
  lpReField=re.compile('[aA]\d{3,4}[pP]{0,1}\d{0,2}')

  lpIndex=0
  for f in lpFileList:
    m=re.match(lpRePattern, str(f))
    lpIndex+=1
    if m : break;


  lpTheName=str(lpFileList[lpIndex-1])
  if re.match(lpRePattern, lpTheName) == None :return lpOut

  #print (lpIndex)
  #print (lpTheName)
  
  
  lpReResult=lpReField.search(lpTheName)
  lpOut=lpReResult.group()
  if pxAdd :
    lpOut=lpOut+'_'
    lpOut="[UC]S"+lpOut
  
  return lpOut



def searchSaPlan(pxName, pxAdd):
  lpOut=0
  lpCurrentWorkingDir=os.getcwd()
  
  zWorkingNumber=pxName
  #print (lpCurrentWorkingDir)

  lpFileList = os.listdir(lpCurrentWorkingDir)
  #print (lpFileList,len(lpFileList))

  
  lpFileNameList=[]
  for f in lpFileList:
    lpFileNameList.append(str(f))
  #print (lpFileNameList)

  testPtt='_'
  testFld='_'
  
  if pxAdd :
    testPtt='^((sa|SA)\d{4}[_]|[_]).+(\.xdw)$'
    testFld=re.compile('([sS][aA]\d{4}[_]|[_])')
  else :
    testPtt='^(EAS).+(NZSH\.ndr)$'
    testFld=re.compile('(NZSH)')
  

  zSum=0
  zIndex=0
  zIndexList=[]
  testStr=lpFileNameList
  for cur in testStr:
    #print ('at',zIndex,'let<',testPtt,'> res <',cur,'>and we got:',)
    zIndex+=1
    if re.match( testPtt, cur) :
      zSum+=1
      zIndexList.append(zIndex)
      #print ("    __ok")
    else :
      pass
      #print ("   nope")
  #print (zSum," matched")
  #print ("index:",zIndexList," has", len(zIndexList))
  if len(zIndexList)==0:return 7
  #print ("befor:",testStr)

  for i in zIndexList:
    testStr[i-1]=testFld.sub(zWorkingNumber,testStr[i-1])
    if pxAdd==False :
      pass
      testStr[i-1]=testStr[i-1][1:len(testStr[i-1])]
      testStr[i-1]="F"+testStr[i-1]
  #print ("after:",testStr)


  dtfm=0
  for j in zIndexList:
    #print(str(lpFileList[j-1]))
    try:
      os.rename(lpFileList[j-1],lpFileNameList[j-1])
    except IOError as err:
      print ("cant rename this ",err)
      lpOut=443
      
  #print (dtfm)
  return lpOut


with open('log.txt','w') as pbLogFile:
  pbTrace=input(":plan or draw??(p<盤図>/d<工事図>)::")
  pbIsPlan=False
  if pbTrace=="d" :
    pbIsPlan=False
  elif pbTrace=="p" :
    pbIsPlan=True


  
  pbTab=searchWorkingNumber(1,pbIsPlan)
  pbTag=searchSaPlan(pbTab,pbIsPlan)
  pbLogFile.write (":searchWorkingNumber:wokringNumberIs::"+pbTab)
  pbLogFile.write (":searchSaPlan:endWithCode::"+str(pbTag))
  print (":pover:endWithCode::"+str(pbTag))




