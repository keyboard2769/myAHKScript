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
;@function

Mode:=0

#Esc::
InputBox, iptMode, ahk, in_Put_The_Function_Mode_For, ,255,128 
if (ErrorLevel){
	Return
}else{
	StringLen, len, iptMode
	if(len > 1){
		MsgBox, inputIlleagle
		Return
	}
	
	if(iptMode="0"){
		Mode:=0
		ToolTip
		Return
	}

	if(iptMode="1"){
		Mode:=1
		ToolTip, %Mode%, 1160, 650
		Return
	}
	
	if(iptMode="2"){
		Mode:=2
		ToolTip, %Mode%, 1160, 650
		Return
	}
	
	MsgBox notFound
Return
}

XButton1::
MsgBox mouse1
Return

XButton2::
MsgBox mouse2
Return

#F1::
MsgBox, 4, ,"Func1"
IfMsgBox No
	Return
Return

#F2::
NowD:=A_Now
stringMid, NowD, NowD, 3, 6
SendInput _
SendInput %NowD%
;MsgBox, 4, ,"Func2"
;IfMsgBox No
;	Return
Return

#F3::
NowD:=A_Now
stringMid, NowD, NowD, 3, 10
SendInput _
SendInput %NowD%
;MsgBox, 4, ,"Func3"
;IfMsgBox No
;	Return
Return


;________________
#F4::
NowD:=A_Now
stringMid, NowD, NowD, 3, 2
SendInput %NowD%

NowD:=A_Now
stringMid, NowD, NowD, 5, 2
SendInput -%NowD%

NowD:=A_Now
stringMid, NowD, NowD, 7, 2
SendInput -%NowD%

;MsgBox, 4, ,"Func4"
;IfMsgBox No
;	Return
Return