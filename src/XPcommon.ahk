#NoEnv  ; Recommended for performance and compatibility with future AutoHotkey releases.
; #Warn  ; Enable warnings to assist with detecting common errors.
SendMode Input  ; Recommended for new scripts due to its superior speed and reliability.
SetWorkingDir %A_ScriptDir%  ; Ensures a consistent starting directory.









;-Mouse------
;--natural----
Mbutton::
SendInput {Bs}
Return

RButton::Return
RButton Up::
SendInput {RButton}
Return
;--rightTrigger----
RButton & LButton Up::
SendInput #e
Return

RButton & MButton::
SendInput ^z
Return

RButton & WheelUp::
SendInput {PgUp}
Sleep 64
Return

RButton & WheelDown::
SendInput {PgDn}
Sleep 64
Return
;--middleTrigger----
MButton & LButton::
SendInput !{Up}
Return

MButton & WheelUp::
SendInput {Home}
Sleep 64
Return

MButton & WheelDown::
SendInput {End}
Sleep 64
Return






;------
;FN------
F1::
SendInput {Enter}
Return



;------
;------
;------
;Ctrl------
;----
;Shift------
;----
;----
;Alt------
;----
;----
;------


;-system------



Insert::
#q::
MsgBox, 4, ,"notQuitting"
IfMsgBox Yes
	Return
ExitApp
Return


Pause::
#a::
Suspend
Return

ScrollLock::
#s::
MsgBox, "Runnning"
Return



;-eof----
