#NoEnv  ; Recommended for performance and compatibility with future AutoHotkey releases.
; #Warn  ; Enable warnings to assist with detecting common errors.
SendMode Input  ; Recommended for new scripts due to its superior speed and reliability.
SetWorkingDir %A_ScriptDir%  ; Ensures a consistent starting directory.

#SingleInstance Force

;--init

MsgBox,,,"system_standy",2
MsgBox, 4, ,"press_no_for_emulate"
  IfMsgBox Yes
  goto #m

lpPromptInfo:="input_command`n [sww]sleep,[sdd]shutDown,[srr]reboot,[],,`n"
lpPromptInfo:= % lpPromptInfo "[ps2cfg],[pow],[edit],[setting],[],,,,`n"
lpPromptInfo:= % lpPromptInfo "[help],[test],[],[],[],,,,`n"

return

;--mouse



;--single
vkADsc120::
  Sleep 32
Return


;--control



^j::
WinGet, name, ProcessName, A
if(name == "dwviewer.exe"){
  SendInput !{v}
  Sleep 64
  SendInput {k}
  Return
}else{
  Suspend, on
  SendInput ^{j}
  Suspend, off
  Return
}
Return


^k::
WinGet, name, ProcessName, A
  if(name == "dwviewer.exe"){
    SendInput !{v}
    Sleep 64
    SendInput {m}
    Return
  }else{
    Suspend, on
    SendInput ^{k}
    Suspend, off
    Return
  }
Return


^l::
  WinGet, name, ProcessName, A
  if(name == "dwviewer.exe"){
    SendInput !{p}
  Sleep 64
  SendInput {n}
    Return
  }else{
    Suspend, on
    SendInput ^{l}
    Suspend, off
    Return
  }
Return

;--shift


;--alt


;--Windows
;--[[ virtual mouse

#up::MouseMove,0,-8,2,R
#down::MouseMove,0,8,2,R
#left::MouseMove,-8,0,2,R
#right::MouseMove,8,0,2,R
+#up::MouseMove,0,-64,2,R
+#down::MouseMove,0,64,2,R
+#left::MouseMove,-64,0,2,R
+#right::MouseMove,64,0,2,R
#-::SendInput {LButton}
#=::SendInput {RButton}
return

;--[[ extra


#h::
  Run, taskmgr.exe
  WinWait, タスク マネージャー
  WinMove, 520, 545
  Sleep 64
  SendInput #{.}
  Sleep 1024
  Run c:\ccmd\ps2.lnk
return


ScrollLock::
#m::
  Run c:\ccmd\_j2m.ahk
return


;--[[ system



#2::
lpTimeStamp:=A_Now
stringMid, lpTimeStamp, lpTimeStamp, 3, 6
SendInput _%lpTimeStamp%
Return


#3::
lpTimeStamp:=A_Now
stringMid, lpTimeStamp, lpTimeStamp, 3, 10
SendInput _%lpTimeStamp%
Return


#4::
lpTimeStamp:=A_Now
stringMid, lpTimeStampYY, lpTimeStamp, 3, 2
stringMid, lpTimeStampMM, lpTimeStamp, 5, 2
stringMid, lpTimeStampDD, lpTimeStamp, 7, 2
SendInput %lpTimeStampYY%
SendInput -%lpTimeStampMM%
SendInput -%lpTimeStampDD%
Return


#e::
  Run C:\keypadhome
Return


#Space::
  WinGet, name, ProcessName, A
  MsgBox %name%
Return


Insert::
#q::
  MsgBox, 4, ,"press_YES_to_ramain"
  IfMsgBox Yes
    Return
  ExitApp
Return


Pause::
#p::
  Suspend
Return

#s::
  MsgBox, "Save_and_Reload"
  SendInput ^s
  Reload
Return


;--[[ prompt
#f::
InputBox,lpPromptString,ahk_custom_command,%lpPromptInfo%
if(ErrorLevel){
  MsgBox, "prompt_error"
  return
}
if(lpPromptString=="ps2cfg"){
  lpExePath=C:\Program Files\Notepad++\notepad++.exe
  lpFilePath=C:\Users\KIC00\Documents\PCSX2\inis_1.4.0\PCSX2_vm.ini
  Run,%lpExePath% "%lpFilePath%"
  Sleep 500
  SendInput, {Down 55}
  SendInput, {End}
  return
}else if(lpPromptString=="pow"){
  MouseMove, 32,32
  SendInput, {RWin}
  Sleep 128
  SendInput, {Down}
  Sleep 128
  SendInput, {Down 8}
  Sleep 128
  SendInput, {Enter}  
  return
}else if(lpPromptString=="shutdown" or lpPromptString=="sdd"){
  Shutdown, 1
  return
}else if(lpPromptString=="ping"){
  SendInput #{r}
  Sleep 64
  SendInput cmd
  Sleep 64
  return
  SendInput {Enter}
  Sleep 64
 ; SendInput ping 192.168.1.6
  Sleep 64
  SendInput {Enter}
  return
}else if(lpPromptString=="reboot" or lpPromptString=="srr"){
  Shutdown, 2
  return
}else if(lpPromptString=="sleep" or lpPromptString=="slp" or lpPromptString=="sww"){
  SendInput #{.}
  Sleep 2048
  SendMessage, 0x112, 0xF170, 2,, Program Manager
  return
}else if(lpPromptString=="edit"){
  Edit
  return
}else if(lpPromptString=="setting"){
  MouseMove, 32,32
  SendInput, {RWin}
  Sleep 128
  SendInput, {Down}
  Sleep 128
  SendInput, {Down 4}
  Sleep 128
  SendInput, {Enter}  
  return
}
else if(lpPromptString=="test"  or lpPromptString=="tt"){
  MsgBox % "test"
}
else if(lpPromptString=="help"){
  MsgBox % "help..ps2cfg"
}else{
  SendInput #{r}
  Sleep 64
  SendInput %lpPromptString%
  Sleep 64
  SendInput {Enter}
}
return


;EOF