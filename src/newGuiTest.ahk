#NoEnv  ; Recommended for performance and compatibility with future AutoHotkey releases.
; #Warn  ; Enable warnings to assist with detecting common errors.
SendMode Input  ; Recommended for new scripts due to its superior speed and reliability.
SetWorkingDir %A_ScriptDir%  ; Ensures a consistent starting directory.

MsgBox,,"script on","standby",1

; gui
Gui, Add, Text,, "a test example"
Gui, Add, Button, w128 h20, P_CLOSE
Gui, Add, Button, w128 h20, P_RESET
Gui, Add, Button, w128 h20, P_SEND
Gui, Add, Edit, vInputCommand
Gui, Add, Button,, P_RECV
Gui +AlwaysOnTop 
Gui, Show, x100 y100 w320 h240 
 ; Gui, hide
return

;-- gui button

ButtonP_RESET:
  MsgBox,,"testGui","do reset!",1
  InputCommand=""
return

ButtonP_RECV:
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

ButtonP_SEND:
  MsgBox,,"testGui","not sending anything!",1
 ; ControlSend, Edit1, "メモ帳へ送っちゃうけど", 無題
return

;-- system

#g::
  Gui, Show
return

#b::
  Gui, hide
return

#Space::
  MsgBox % "not getting process ID!"
return

ButtonP_CLOSE:
#q::
  MsgBox,,"sys","exit?",1
  IfMsgBox,No
    return
  ExitApp
return

;** eof