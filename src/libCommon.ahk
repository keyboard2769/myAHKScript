#NoEnv
; Recommended for performance and compatibility
;with future AutoHotkey releases.
; #Warn
; Enable warnings to assist with detecting common errors.
SendMode Input
; Recommended for new scripts
;due to its superior speed and reliability.
SetWorkingDir %A_ScriptDir%
; Ensures a consistent starting directory.
;@common cut




;-------mouse

;---natural

Mbutton::
WinGet, name, ProcessName, A
if(name == "Explorer.EXE"){
  SendInput !{Up}
  Return
}
if(name == "chrome.exe"){
  SendInput !{Left}
  return
}
return

RButton::Return
RButton Up::
SendInput {RButton}
Return


;---rightTrigger

;RButton & LButton Up::
;SendInput #e
;Return

;RButton & MButton::
;SendInput {Bs}
;Return

RButton & WheelUp::
SendInput {PgUp}
Sleep 64
Return

RButton & WheelDown::
SendInput {PgDn}
Sleep 64
Return

;---middleTrigger
;MButton & LButton::
;SendInput ^z
;Return

;MButton & WheelUp::
;SendInput {Home}
;Sleep 64
;Return

;MButton & WheelDown::
;SendInput {End}
;Sleep 64
;Return
;---shift--

+WheelUp::
SendInput {WheelLeft}
Return

+WheelDown::
SendInput {WheelRight}
Return

;------------------------

;eof
