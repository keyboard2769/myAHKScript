import math

def fsShowTitle():
  print("--> とりあえずよく使う計算のまとめやで <--")
  print("--> === システム用 === <--")
  print("::[help]命令リストの再表示")
  print("::[ bev]基本環境数値の設定")
  print("::[ bep]基本環境数値の表示")
  print("::[quit]終了")
  print("--> === 作業用 === <--")
  print("::[ map]配置図長さ計算")
  print("--> === 計算用 === <--")
  print("::[hp2r]ヒータ抵抗値計算")
  print("::[hr2p]ヒータ容量値計算")
  print("::[hp22]ヒータ電流値計算(単相)")
  print("::[hp23]ヒータ電流値計算(三相)")
  print("::[mp2a]モータ電流値計算")



def clBasicEnvironmentValue():
  try:
    beValues['volt'] = float(input(":想定電源電圧は(V)::"))
    beValues['cos'] = float(input(":想定力率は(0-1)::"))
  except ValueError:
    print (":入力拒否::")
    return 255
  print (beValues)
  return 0



def clReadUpBasicEnvironmentValue():
  print (beValues)
  return 0

  
#from here basic command line oprates

def clMapLengthOperate():
  try:
    lpLenthM = float(input(":配置図表示長[mm]::"))
    lpAnnoMM = float(input(":四角アノテ幅[mm]::"))
  except ValueError:
    print (":入力拒否::")
    return 255    
  result=5000*lpAnnoMM/lpLenthM
  print ("5m長さのアノテ幅は:   "+str(result)+"[mm]")
  return 0


def clPowerToResis(pxV):
  try:
    lpP = float(input(":ヒータ容量(W)::"))
  except ValueError:
    print (":入力拒否::")
    return 255
  result=pxV*pxV/lpP
  print ("想定抵抗は:   "+str(result)+"[ohm]")
  return 0


def clHeaterMotorPowerToAmpair3Ph(pxV):
  try:
    lpP=float(input(":ヒータ容量(W)::"))
  except ValueError:
    print(":入力拒否::")
    return 255
  result = math.sqrt(3)*pxV
  result = lpP/result
  print("想定電流は:   "+str(result)+"[A]")
  return 0


def clHeaterMotorPowerToAmpair2Ph(pxV):
  try:
    lpP = float(input(":ヒータ容量(W)::"))
  except ValueError:
    print(":入力拒否::")
    return 255
  result = lpP/pxV
  print("想定電流は:   "+str(result)+"[A]")
  return 0



def clResisToPower(pxV):
  try:
    lpP = float(input(":ヒータ抵抗(ohm)::"))
  except ValueError:
    print (":入力拒否::")
    return 255
  result=pxV*pxV/lpP
  
  print("想定容量は:   "+str(result)+"[w]<<--実は抵抗計算と一緒")
  return 0






def clMotorPowerToAmpair(pxV,pxCos):
  try:
    lpP = float(input(":モータ容量(kW)::"))
  except ValueError:
    print(":入力拒否::")
    return 255
  result = math.sqrt(3)*pxV*pxCos
  result = lpP/result
  result = result*1000

  lpKva = math.sqrt(3) * (pxV/1000) * result
  
  print("想定電流は:   "+str(result)+"[A]")
  print("(力率仮定:   "+str(pxCos)+" )")
  print("(想定皮相:   "+str(lpKva)+"[kVA] )")
  return 0



def clAdToKg():
  pass
  print("not yet")
  return 0


#fome here basic function collections
def fnScalling(pxIn,pxInputZero,pxOutputZero,pxInputSpan,pxOutputSpan):
  pass
  return 0

# basic env value define
beValues={
      'volt':380.0,
      'cos':0.85,
      'hige':20.0,
      'time':0.01,
      'sq3':33
  }


# mainFunc
def main():
  fsShowTitle()
  key=0
  while key!=5:
    print ("-->>                        <<--")
    print ("-->>入力コード:",key,"つづく<<--")
    cArg=input("::")
    # for system
    if cArg=="help":key=fsShowTitle()
    if cArg=="bev":key=clBasicEnvironmentValue()
    if cArg=="bep":key=clReadUpBasicEnvironmentValue()
    if cArg=="quit":key=5
    # for oprates
    if cArg=="map":key=clMapLengthOperate()
    if cArg=="hp2r":key=clPowerToResis(beValues['volt'])
    if cArg=="hr2p":key=clResisToPower(beValues['volt'])
    if cArg=="hp22":key=clHeaterMotorPowerToAmpair2Ph(beValues['volt'])
    if cArg=="hp23":key=clHeaterMotorPowerToAmpair3Ph(beValues['volt'])
    if cArg=="mp2a":key=clMotorPowerToAmpair(beValues['volt'],beValues['cos'])
  print ('--> 終了 <--')
  return 0

# from here the main loop
if __name__ == "__main__":
  main()
