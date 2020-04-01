#NoEnv  ; Recommended for performance and compatibility with future AutoHotkey releases.
; #Warn  ; Enable warnings to assist with detecting common errors.
SendMode Input  ; Recommended for new scripts due to its superior speed and reliability.
SetWorkingDir %A_ScriptDir%  ; Ensures a consistent starting directory.


;-- base[ORG]
MsgBox,,"sys","vmxp_jp_mark_three_activated",1

;-- mouse

MButton::
  SendInput {Bs}
return

RButton::
return

RButton Up::
  SendInput {RButton}
return

RButton & WheelUp::
  SendInput {PgUp}
  Sleep 64
return

RButton & WheelDown::
  SendInput {PgDn}
  Sleep 64
return

XButton1::
WinGet, lpName, ProcessName, A
if(lpName == "CAE2D.EXE"){
  SendInput {Enter}
  return
}
if(lpName == "dwviewer.exe"){
  SendInput ^{WheelUp 4}
  return
}
else{
  Suspend, on
  MsgBox % "Unattached $ X1"
  Suspend, off
  return
}
return

XButton2::
WinGet, lpName, ProcessName, A
if(lpName == "CAE2D.EXE"){
  SendInput {Esc}
  return
}
if(lpName == "dwviewer.exe"){
  SendInput ^{WheelDown 4}
  return
}
else{
  Suspend, on
  MsgBox % "Unattached $ X2"
  Suspend, off
  return
}
return

;-- windows

#F12::
  WinGet, lpName, ProcessName, A
  MsgBox, ID "%lpName%"
return

#p::
pause::
  Suspend
return

#q::
  MsgBox, 4, ,"press_YES_to_ramain"
  IfMsgBox Yes
    return
  ExitApp
return

#s::
  MsgBox,,"sys","save_and_reload",1
  WinGet, lpName, ProcessName, A
  if(lpName == "SciTE.exe"){
    SendInput ^{s}
    Reload
    return
  }
  else{
    MsgBox % "you_are_at_the_wrong_place"
    return
  }
return

#f::
  sysShowPrompt()
return

#F1::
WinGet, lpName, ProcessName, A
  if(lpName == "CAE2D.EXE"){
    Sleep 64
    SendInput {Bs}
    Sleep 64
    SendInput {Bs}
    Sleep 64
    SendInput {Bs}
    Sleep 64
    SendInput {Bs}
    return
  }
  else{
    Suspend, on
    MsgBox % "not_yet"
    Suspend, off
    return
  }
return

#F2::
  FormatTime, lpTimeStamp,%A_NOW%,_yyMMdd
  Send % lpTimeStamp
return

#F3::
  FormatTime, lpTimeStamp,%A_NOW%,_yyMMddHHmm
  Send % lpTimeStamp
return

#F4::
  FormatTime, lpTimeStamp,%A_NOW%,yy-MM-dd
  Send % lpTimeStamp
return

#F5::
  SendInput {Del}
return

;-- single

ScrollLock::
Insert::
  MsgBox % "abandoned"
return

Esc::
  WinGet, lpName, ProcessName, A
  if(lpName == "dwviewer.exe"){
    MsgBox,1,"ahk_sys","abandoned for dxw file!!",1
    Sleep 64
    return
  }
  else{
    Suspend, on
    SendInput {Esc}
    Suspend, off
    return
  }
return

F1::
WinGet, lpName, ProcessName, A
  if(lpName == "caepad.exe"){
    SendInput ^{e}
    return
  }
  else{
    Suspend, on
    SendInput {Enter}
    Suspend, off
    return
  }
return

F2::
  WinGet, lpName, ProcessName, A
  if(lpName == "dwviewer.exe"){
    SendInput !{v}
    Sleep 64
    SendInput {k}
    return
  }
  else{
    Suspend, on
    SendInput {F2}
    Suspend, off
    return
  }
return

F3::
  WinGet, lpName, ProcessName, A
  if(lpName == "dwviewer.exe"){
    SendInput !{v}
    Sleep 64
    SendInput {m}
    return
  }
  else{
    Suspend, on
    SendInput {F3}
    Suspend, off
    return
  }
return

F4::
  WinGet, lpName, ProcessName, A
  if(lpName == "dwviewer.exe"){
    SendInput !{e}
    Sleep 64
    SendInput {g}{m}
    return
  }
  if(lpName == "dwdesk.exe"){
    SendInput !{e}
    Sleep 64
    SendInput {g}{r}
    return
  }
  else{
    Suspend, on
    SendInput {F4}
    Suspend, off
    return
  }
return

F5::
  WinGet, lpName, ProcessName, A
  if(lpName == "EXCEL.EXE"){
    SendInput !{o}
    Sleep 64
    SendInput {c}{a}
    return
  }
  else{
    Suspend, on
    SendInput {F5}
    Suspend, off
    return
  }
return

;-- CTRL

^e::
  WinGet, lpName, ProcessName, A
  if(lpName == "dwviewer.exe"){
    SendInput !{e}
    Sleep 64
    SendInput {d}
    return
  }
  else{
    Suspend, on
    SendInput ^{e}
    Suspend, off
    return
  }
return

^g::
  WinGet, lpName, ProcessName, A
  if(lpName == "dwviewer.exe"){
    SendInput {AppsKey}
    Sleep 64
    SendInput {o}{o}
    return
  }
  else{
    Suspend, on
    SendInput ^{g}
    Suspend, off
    return
  }
return

;-- ALT

!Up::
  WinGet, lpName, ProcessName, A
  if(lpName == "Explorer.EXE"){
    SendInput {Bs}
    return
  }
  else{
    Suspend, on
    SendInput !{Up}
    Suspend, off
    return
  }
return

!1::
  WinGet, lpName, ProcessName, A
  if(lpName == "mspaint.exe"){
    SendInput ^{PgUp}
    return
  }
  else{
    Suspend, on
    SendInput !{1}
    Suspend, off
    return
  }
return

!2::
  WinGet, lpName, ProcessName, A
  if(lpName == "mspaint.exe"){
    SendInput ^{PgDn}
    return
  }
  else{
    Suspend, on
    SendInput !{2}
    Suspend, off
    return
  }
return

!3::
  WinGet, lpName, ProcessName, A
  if(lpName == "mspaint.exe"){
    Sleep 128
    SendInput !{v}
    Sleep 64
    SendInput {z}{u}
    return
  }
  else{
    Suspend, on
    SendInput !{3}
    Suspend, off
    return
  }
return

!4::
  WinGet, lpName, ProcessName, A
  if(lpName == "mspaint.exe"){
    Sleep 128
    SendInput !{i}
    Sleep 64
    SendInput {d}
    return
  }
  else{
    Suspend, on
    SendInput !{4}
    Suspend, off
    return
  }
return

!F1::
  WinGet, lpName, ProcessName, A
  if(lpName == "EXCEL.EXE"){
    MsgBox,,"ahk_sys","abandoned for graph generate!!",1
    return
  }
  if(lpName == "CAE2D.EXE"){
    Suspend, on
    SendInput !{F1}
    Suspend, off
    return
  }
  else{
    Suspend, on
    MsgBox,,"ahk_sys","we_abandon_any_F1_usage",1
    Suspend, off
    return
  }
return

;-- SHIFT

+F1::
  SendInput +{Enter}
return

;-- EXT

;-- prompt

sysShowPrompt(){
  InputBox, lpPromptString, "my_custom_command","well"
  if(ErrorLevel){
    MsgBox,,"sys","input_error",1
    return
  }
  if(lpPromptString == "help"){
    MsgBox % "document_unavailable"
    return
  }
  MsgBox % lpPromptString
  return
}

;***eof