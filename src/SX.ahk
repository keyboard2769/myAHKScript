﻿#NoEnv  ; Recommended for performance and compatibility with future AutoHotkey releases.
; #Warn  ; Enable warnings to assist with detecting common errors.
SendMode Input  ; Recommended for new scripts due to its superior speed and reliability.
SetWorkingDir %A_ScriptDir%  ; Ensures a consistent starting directory.

; *** for  fujitsu windows7 main processor *** 
SetKeyDelay, 64

;=== ** button pane window
Gui, Add, Text,, "myAHK Launcher Pane"
Gui, Add, Button, w128 h20, P_DUMMY_A
Gui, Add, Button, w128 h20, P_DUMMY_B
Gui, Add, Edit, vInputCommand
Gui, Add, Button, w128 h20, P_COMMAND_RUN
Gui, Add, Button, w128 h20, P_COMMAND_CLEAR
Gui +AlwaysOnTop 
Gui, Show, x100 y100 w150 h400 
Gui, hide

;=== ** prerunnning
MsgBox,4,"myAHK","this::fujitsu13[SX]...standby",4
IfMsgBox, No
  return
ccRunAtFirst()
return

;=== ** mouse

RButton::Return
RButton Up::
  SendInput {RButton}
Return

RButton & WheelUp::
  SendInput {PgUp}
  Sleep 64
Return

RButton & WheelDown::
  SendInput {PgDn}
  Sleep 64
Return

+WheelUp::
  SendInput {WheelLeft}
Return

+WheelDown::
  SendInput {WheelRight}
Return

Mbutton::
WinGet, name, ProcessName, A
if(name == "Explorer.EXE" or name == "explorer.exe"){
  SendInput !{Up}
  Return
}
if(name == "iexplore.exe"){
  SendInput {Bs}
  return
}
if(name == "chrome.exe"){
  SendInput !{Left}
  return
}
if(name == "Explorer++.exe"){
  SendInput {Bs}
  return
}
return

;=== ** System

Insert::
  MsgBox,,,"Dont touch me!!",1
Return

Pause::
#P::
  Suspend
Return

ScrollLock::
  MsgBox, 4, ,"clickOkToExit"
  IfMsgBox No
    Return
  ExitApp
Return

;=== ** WIN

;=== ** WIN ** FK

#F1::
  MsgBox, 4, ,"Func1"
  IfMsgBox No
    Return
Return

#F2::
  FormatTime, lpTimeStamp,,_yyMMdd
  SendInput %lpTimeStamp%
Return

#F3::
  FormatTime, lpTimeStamp,,_yyMMddHHmm
  SendInput %lpTimeStamp%
Return

#F4::
  FormatTime, lpTimeStamp,,yy-MM-dd
  SendInput %lpTimeStamp%
Return

;=== ** WIN ** number

#1::
  MsgBox,,,empty,1
Return

#2::
  MsgBox,,,empty,1
Return

#3::
  MsgBox,,,empty,1
Return

#4::
  MsgBox,,,empty,1
Return

#5::
  MsgBox,,,empty,1
Return

;=== ** WIN ** alphabet

#e::
  Run C:\Users\2053.DOMAINH\Documents\KeypadHome\Stocker\11指図
Return

#s::
WinGet, name, ProcessName, A
if(name == "pythonw.exe"){
  MsgBox,,"myAHK","saveAndRun_from_pythonw.exe",1
  Sleep 100
  SendInput ^{s}
  Sleep 500
  SendInput {F5}
  Sleep 100
  Return
}
if(name == "mi.exe"){
  MsgBox,,"myAHK","saveAndRun_from_mi.exe",1
  Sleep 100
  SendInput ^{s}
  Sleep 500
  Reload
  Sleep 100
  Return
}
if(name == "SciTE.exe"){
  MsgBox,,"myAHK","saveAndRun_from_SciTE.exe",1
  Sleep 64
  SendInput ^{s}
  Sleep 64
  Reload
  Return
}
else{
  Suspend, on
  MsgBox,,"myAHK","autoSaveUnavailable",1
  Suspend, off
  Return
}
Return

#g::
  Gui, Show
return

#b::
  Gui, hide
return

;=== ** WIN ** single

#space::
  WinGet, pName, ProcessName, A
  MsgBox, ID "%pName%"
Return

#up::MouseMove,0,-8,2,R
#down::MouseMove,0,8,2,R
#left::MouseMove,-8,0,2,R
#right::MouseMove,8,0,2,R
+#up::MouseMove,0,-64,2,R
+#down::MouseMove,0,64,2,R
+#left::MouseMove,-64,0,2,R
+#right::MouseMove,64,0,2,R
#,::SendInput {LButton}
#.::SendInput {RButton}
return

;=== ** CTRL

;=== ** CTRL ** FK

;=== ** CTRL ** number

;=== ** CTRL ** alphabet

^e::
WinGet, name, ProcessName, A
if(name == "dwviewer.exe"){
  SendInput !{e}
  SendInput {d}
  Return
}else{
  Suspend, on
  SendInput ^{e}
  Suspend, off
  Return
}
Return

^w::
WinGet, name, ProcessName, A
if(name == "MrxFPLC.exe"){
  SendInput !{e}{m}
  Return
}else{
  Suspend, on
  SendInput ^{w}
  Suspend, off
  Return
}
Return

^t::
WinGet, name, ProcessName, A
if(name == "MrxFPLC.exe"){
  SendInput !{e}{a}
  Return
}else{
  Suspend, on
  SendInput ^{t}
  Suspend, off
  Return
}
Return

;=== ** CTRL ** single

;=== ** SHIFT

;=== ** SHIFT ** FK

+F3::
WinGet, name, ProcessName, A
if(name == "EXCEL.EXE"){
  SendInput {F3}
  Return
}
else{
  Suspend, on
  SendInput +{F3}
  Suspend, off
  Return
}
Return

;=== ** SHIFT ** number

;=== ** SHIFT ** alphabet

;=== ** SHIFT ** single

;=== ** ALT

;=== ** ALT ** FK

;=== ** ALT ** number

;=== ** ALT ** alphabet

;=== ** ALT ** single

;=== ** single

Esc::
WinGet, name, ProcessName, A
if(name == "caepad.exe"){
  SendInput {Alt}
  SendInput {Down}
  SendInput {x}
  Return
}else{
  Suspend, on
  Sleep 64
  SendInput {Esc}
  Sleep 64
  Suspend, off
  Return
}
Return

;=== ** single ** FK

F1::
WinGet, name, ProcessName, A
if(name == "caepad.exe"){
  SendInput {F12}
  Return
}
if(name == "dwviewer.exe" ){
  SendInput !{v}
  Sleep 64
  SendInput {k}
  Return
}
else{
  Suspend, on
  SendInput {Enter}
  Suspend, off
  Return
}

F2::
WinGet, name, ProcessName, A
if(name == "chrome.exe"){
  SendInput !{Left}
  return
}
if(name == "caepad.exe"){
  SendInput ^{v}
  Return
}
if(name == "dwviewer.exe"){
  SendInput !{v}
  Sleep 64
  SendInput {m}
  Return
}
else{
  Suspend, on
  SendInput {F2}
  Suspend, off
  Return
}

F3::
WinGet, name, ProcessName, A
if(name == "caepad.exe"){
  SendInput {BS}
  Return
}
if(name == "dwviewer.exe"){
  SendInput !{p}
  Sleep 64
  SendInput {n}
  Return
}
if(name == "dwdesk.exe"){
  SendInput !{v}
  SendInput {a}{n}
  Return
}
else{
  Suspend, on
  SendInput {F3}
  Suspend, off
  Return
}

F4::
WinGet, name, ProcessName, A
if(name == "caepad.exe"){
  SendInput {Del}
  Return
}
if(name == "AcroRd32.exe"){
  SendInput ^{p}
  Sleep 1000
  SendInput !{n}
  SendInput {d}
  SendInput !{r}
  SendInput {Down}{Down}
  Return
}
if(name == "EXCEL.EXE"){
  SendInput !{6}
  Sleep 200
  SendInput {y}
  Return
}
if(name == "dwviewer.exe"){
  SendInput !{e}
  Sleep 64
  SendInput {g}{m}
  Return
}
if(name == "dwdesk.exe"){
  SendInput !{e}
  Sleep 64
  SendInput {g}{r}
  Return
}
else{
  Suspend, on
  SendInput {F4}
  Suspend, off
  Return
}
Return

F5::
WinGet, name, ProcessName, A
if(name == "EXCEL.EXE"){
  SendInput !{7}
  Sleep 200
  Return
}
else{
  Suspend, on
  SendInput {F5}
  Suspend, off
  Return
}

F6::
WinGet, name, ProcessName, A
if(name == "EXCEL.EXE"){
  SendInput !{8}
  Sleep 200
  Return
}
else{
  Suspend, on
  SendInput {F6}
  Suspend, off
  Return
}

F7::
WinGet, name, ProcessName, A
if(name == "EXCEL.EXE"){
  SendInput !{9}
  Sleep 200
  Return
}
else{
  Suspend, on
  SendInput {F7}
  Suspend, off
  Return
}

F8::
WinGet, name, ProcessName, A
if(name == "EXCEL.EXE"){
  SendInput !{0}
  Sleep 64
  SendInput !{9}
  Sleep 200
  Return
}
else{
  Suspend, on
  SendInput {F8}
  Suspend, off
  Return
}

F9::
WinGet, name, ProcessName, A
if(name == "EXCEL.EXE"){
  SendInput !{0}
  Sleep 64
  SendInput !{8}
  Sleep 200
  Return
}
else{
  Suspend, on
  SendInput {F9}
  Suspend, off
  Return
}

F10::
WinGet, name, ProcessName, A
if(name == "EXCEL.EXE"){
  SendInput !{0}
  Sleep 64
  SendInput !{7}
  Sleep 200
  Return
}
else{
  Suspend, on
  SendInput {F10}
  Suspend, off
  Return
}

F11::
WinGet, name, ProcessName, A
if(name == "EXCEL.EXE"){
  SendInput !{0}
  Sleep 64
  SendInput !{6}
  Sleep 200
  Return
}
else{
  Suspend, on
  SendInput {F11}
  Suspend, off
  Return
}

F12::
WinGet, name, ProcessName, A
if(name == "EXCEL.EXE"){
  SendInput !{0}
  Sleep 64
  SendInput !{5}
  Sleep 200
  Return
}
else{
  Suspend, on
  SendInput {F12}
  Suspend, off
  Return
}

;=== ** single ** VK

vkFFsc070:: ; KANA or ROMAJI
  SendInput {U+0060}
Return

vkE2sc073:: ; hidden slash
  SendInput {_}
Return

~vkF0sc03A:: ; Tab Key
WinGet, name, ProcessName, A
if(name == "CAE2D.EXE"){
  SendInput {Del}
  Return
}
if(name == "netbeans.exe"){
  SendInput ^{Space}
  Return
}
else{
  MsgBox,,,"popopo",1
  Return
}

vk1Csc079:: ; JHennkann
WinGet, name, ProcessName, A
if(name == "EXCEL.EXE"){
  SendInput ^{PgDn}
  Return
}
else{
  Return
}

vk1Csc079 & Space:: ; JHennkann
WinGet, name, ProcessName, A
if(name == "EXCEL.EXE"){
  SendInput ^{PgUp}
  Return
}
else{
  Return
}

vk1Dsc07B & x:: ; JMuhennkann
  SendInput {Enter}
Return

;=== ** button click

ButtonP_DUMMY_A:
  MsgBox % "this is a dummy aha"
return

ButtonP_DUMMY_B:
  MsgBox % "this is a dummy again ! yeaaaah!"
return

ButtonP_COMMAND_RUN:
  Gui, Submit
  Gui, Show
  if(InputCommand=="uno"){
    MsgBox,,"testGuiPrompt","one!",1
    return
  }
  if(InputCommand=="dos"){
    MsgBox,,"testGuiPrompt","two!",1
    return
  }
  if(InputCommand=="tre"){
    MsgBox,,"testGuiPrompt","three!",1
    return
  }
  MsgBox,,"testGui","do recieve with <%InputCommand%> but not found",1
return

ButtonP_COMMAND_CLEAR:
  MsgBox,,"myAHK","cleaning text box",1
  InputCommand=""
return

;=== ** prompt

#f::
InputBox, lpInputString, SAXXXX, " enter strings `n as you know ", ,320,128 
if (ErrorLevel){
  MsgBox,,,"prompt_error",1
  Return
}else{
  lpInputStringLength:=StrLen(lpInputString)
  if(lpInputStringLength==0){
    MsgBox % "so_whatya_gonna_do_?"
    return
  }
  ;-- ** command action start from here
  if(lpInputString=="help"){
    lpHelpMesage := "[pow]open_start_menu...[bacc]...[sdd]shutdown...[srr]reboot...[SAXXXX]...`n"
    lpHelpMesage := "[]..`n"
    MsgBox % lpHelpMesage
    return
  }
  if(lpInputString=="test"){
    MsgBox % "test`n new line"
    return
  }
  ;~ ;-
  if(lpInputString=="ping"){
    Run, %comspec% /c ping 192.168.1.6
    return
  }
  ;--
  if(lpInputString=="edit"){
    Edit
    return
  }
  ;--
  if(lpInputString=="pow"){
    SendInput {Rwin}
    Sleep 64
    SendInput {Tab 2}
    return
  }
  ;--
  if(lpInputString=="bacc"){
    lpSource := "X:\AP制御設計\工程\AP_上海制御工程.xlsx"
    NowD:=A_Now
    stringMid, NowD, NowD, 3, 10
    lpTarget := "C:\Users\2053.DOMAINH\Desktop\AP_上海制御工程_" . NowD . ".xlsx"
    lpFlag := "-- can not reach backup file"
    ;--
    IfExist, %lpSource%
      lpFlag := "OK"
    ;--
    if(lpFlag == "OK"){
      MsgBox,,,"-- copy backupfile from server",1
      FileCopy,%lpSource%,%lpTarget%,1
    }else{
      MsgBox % lpFlag
    }
    return
  }
  ;--
  if(lpInputString=="shutdown" or lpInputString=="sdd"){
    Shutdown, 9
    return
  }
  ;--
  if(lpInputString=="reboot" or lpInputString=="srr"){
    Shutdown, 2
    return
  }
  ;--
  ;--
  lpMatch:=RegExMatch(lpInputString,"^[0-9]{4}$")
  if(lpMatch==1){
    lpPath=\\192.168.1.6\gijyutsu\AP制御設計\向先\SA3000～SA3999
    if (InStr(FileExist(lpPath), "D")){
      Run %lpPath%
      WinWait \\192.168.1.6\gijyutsu\AP制御設計\向先\SA3000～SA3999
      Sleep 64
      SendInput, {up}
      SendInput, {Down}
      SendInput, {s}
      SendInput, {a}
      SendInput, %lpInputString%
      Sleep 512
      SendInput, {Enter}
      return
    }else{
      MsgBox,,,"directory_path_error",1
      return
    }
  }
  ;--** no command matched operation
  SendInput #{r}
  Sleep 64
  SendInput %lpInputString%
  Sleep 64
  SendInput {Enter}
} ;++
Return

;=== ** subroutine

ccRunAtFirst(){
  run C:\Program Files\Google\Chrome\Application\chrome.exe
  run C:\Program Files\Notepad++\notepad++.exe
  SendInput #{e}
  return 0
}

ccArchiveFunctionTest(){
  lpStampTD:=A_Now
  stringMid, lpStampTD, lpStampTD, 3, 10
  lpStamp=_sony11_
  lpStamp:=lpStamp lpStampTD
  RunWait, %comspec% /c cd c:\Next_AP && start winrar a arch%lpStamp% Database
  Sleep 999
  
  lpTarget=arch
  lpTarget:=lpTarget lpStamp
  lpTarget:=lpTarget ".rar"
  
  FileMove,c:\Next_AP\%lpTarget%,d:\
  
  MsgBox,%lpTarget%
  return 0
}

;EOF
