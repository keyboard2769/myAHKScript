#NoEnv
#SingleInstance force

SendMode Input

; Recommended for performance and compatibility
;with future AutoHotkey releases.
; #Warn
; Enable warnings to assist with detecting common errors.


MsgBox,,,"this_fujitsu13_aka[SX]_standby",1

pbRunModeFlag := "normal"

if(pbRunModeFlag == "normal"){
  SendInput #{e}
  run C:\Program Files\Google\Chrome\Application\chrome.exe
}

SX:="Std.exe"
MF:="MrxFPLC.exe"
DW:="dwviewer.exe"
ST:="Stirling.exe"
EX:="EXCEL.EXE"
AC:="acad.exe"
MP:="mspaint.exe"

SetKeyDelay, 64
; Recommended for new scripts
;due to its superior speed and reliability.
SetWorkingDir %A_ScriptDir%
; Ensures a consistent starting directory.


#Include C:\Users\2053.DOMAINH\Documents\KeypadHome\Production\AHK\libCommon.ahk ; basically mouse action only
#Include C:\Users\2053.DOMAINH\Documents\KeypadHome\Production\AHK\libAhk.ahk ; [Suspend],[ScrLock],
#Include C:\Users\2053.DOMAINH\Documents\KeypadHome\Production\AHK\libPrompt.ahk ; [#F],


; natrue flow------



; flag --->> F0 key <<---


F1::
WinGet, name, ProcessName, A
if(name == "caepad.exe"){
  SendInput {F12}
  Return
}
if(name == DW ){
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
if(name == MP){
  SendInput !{h}{r}{o}{v}
  Return
}
if(name == DW){
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
;if(name == EX){
;  SendInput !{5}
;  Return
;}
if(name == MP){
  SendInput !{h}{r}{o}{h}
  Return
}
if(name == DW){
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
if(name == EX){
  SendInput !{6}
  Sleep 200
  SendInput {y}
  Return
}
if(name == DW){
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
if(name == "caepad.exe"){
  SendInput {End}
  Return
}
if(name == EX){
  SendInput !{7}
  Return
}
if(name == DW){
  SendInput !{e}
  Sleep 64
        SendInput {q}{g}
  Return
}
else{
  Suspend, on
  SendInput {F5}
  Suspend, off
  Return
}
Return


F6::
WinGet, name, ProcessName, A
if(name == "caepad.exe"){
  SendInput {Left}
  Return
}
if(name == DW){
  SendInput !{e}
  Sleep 64
        SendInput {q}{u}
  Return
}
if(name == EX){
  SendInput !{8}
  Return
}
else{
  Suspend, on
  SendInput {F6}
  Suspend, off
  Return
}
Return

F7::
WinGet, name, ProcessName, A
if(name == "caepad.exe"){
  SendInput {Right}
  Return
}
if(name == EX){
  SendInput !{9}
  Return
}
if(name == DW){
  SendInput !{e}
  Sleep 64
        SendInput {i}{c}
  Return
}
else{
  Suspend, on
  SendInput {F7}
  Suspend, off
  Return
}
Return


F8::
WinGet, name, ProcessName, A
if(name == "caepad.exe"){
  SendInput {Home}
  Return
}
if(name == EX){
  SendInput !{0}
        Sleep 10
        SendInput {9}
  Return
}
if(name == DW){
  SendInput !{e}
  Sleep 64
        SendInput {j}{v}
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
if(name == EX){
  SendInput !{0}
        Sleep 44
        SendInput {8}
  Return
}
if(name == ST){
  Sleep 50
  SendInput {Enter}
  Sleep 50
  Send 0xCD
  Sleep 50
  SendInput {Enter}
  Sleep 50
  SendInput {Down}
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
if(name == EX){
  SendInput !{0}
        Sleep 44
        SendInput {7}
  Return
}
if(name == DW){
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
if(name == EX){
  SendInput !{0}
        Sleep 44
        SendInput {6}
  Return
}
if(name == DW){
  SendInput !{e}
  Sleep 64
        SendInput {j}{h}
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
if(name == EX){
  SendInput !{0}
        Sleep 44
        SendInput {5}
  Return
}if(name == SX){
  SendInput !{o}
  SendInput {l}
  Return
}else{
  Suspend, on
  SendInput {F12}
  Suspend, off
  Return
}





; flag --->> CTRL + key <<---




^a::
WinGet, name, ProcessName, A
if(name == MF){
  SendInput !{a}
  SendInput {t}
  Return
}else{
  Suspend, on
  SendInput ^{a}
  Suspend, off
  Return
}
Return


;b
;c
;d




^e::
WinGet, name, ProcessName, A
if(name == DW){
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



^f::
WinGet, name, ProcessName, A
if(name == MF){
  SendInput !{a}
  SendInput {r}
  Return
}else{
  Suspend, on
  SendInput ^{f}
  Suspend, off
  Return
}
Return

;g

;h
;i



^J::
WinGet, name, ProcessName, A
if(name == MF){
  SendInput !{p}{a}
  Sleep 20
  SendInput {m}
  Return
}else{
  Suspend, on
  SendInput ^{j}
  Suspend, off
  Return
}
Return




^K::
WinGet, name, ProcessName, A
if(name == MF){
  SendInput !{p}{a}
  Sleep 20
  SendInput {n}
  Return
}else{
  Suspend, on
  SendInput ^{k}
  Suspend, off
  Return
}
Return

;l
;m
;n
;o
;p



^q::
WinGet, name, ProcessName, A
if(name == MF){
  SendInput !{a}{u}
  Return
}else{
  Suspend, on
  SendInput ^{q}
  Suspend, off
  Return
}
Return

;r


^r::
WinGet, name, ProcessName, A
if(name == MF){
  SendInput !{o}{e}
  Sleep 200
  SendInput !{c}
  Sleep 20
  SendInput {Tab}
  Sleep 20
  SendInput {Down}{Down}{Down}{Down}{Down}{Down}
  Sleep 20
  SendInput {Tab}
  Return
}else{
  Suspend, on
  SendInput ^{r}
  Suspend, off
  Return
}
Return


;s


^t::
WinGet, name, ProcessName, A
if(name == MF){
  SendInput !{e}{a}
  Return
}else{
  Suspend, on
  SendInput ^{t}
  Suspend, off
  Return
}
Return


;u
;v

^W::
WinGet, name, ProcessName, A
if(name == MF){
  SendInput !{e}{m}
  Return
}else{
  Suspend, on
  SendInput ^{w}
  Suspend, off
  Return
}
Return


;x
;y
;z









; flag --->> ALT + key <<---


!a::
WinGet, name, ProcessName, A
if(name == MP){
  SendInput !{h}{s}{h}
  SendInput {Enter}
  Return
}
else{
  Suspend, on
  SendEvent !a
  Suspend, off
  Return
}


;b


!c::
WinGet, name, ProcessName, A
if(name == MP){
  SendInput !{h}{d}
  Return
}
else{
  Suspend, on
  SendEvent !{c}
  Suspend, off
  Return
}


!d::
WinGet, name, ProcessName, A
if(name == MP){
  SendInput !{h}{p}
  Return
}
else{
  Suspend, on
  SendEvent !{d}
  Suspend, off
  Return
}


!e::
WinGet, name, ProcessName, A
if(name == MP){
  SendInput !{h}{e}{r}
  Return
}
else{
  Suspend, on
  SendEvent !{e}
  Suspend, off
  Return
}


!f::
WinGet, name, ProcessName, A
if(name == MP){
  SendInput !{h}{k}
  Return
}
else{
  Suspend, on
  SendEvent !{f}
  Suspend, off
  Return
}


;g
;h
;i
;j
;k
;l
;m
;n
;o
;p


!q::
WinGet, name, ProcessName, A
if(name == MP){
  SendInput !{h}{s}{e}{r}
  Return
}
else{
  Suspend, on
  SendEvent !{q}
  Suspend, off
  Return
}


!r::
WinGet, name, ProcessName, A
if(name == MP){
  SendInput !{h}{r}{o}{r}
  Return
}
else{
  Suspend, on
  SendEvent !{r}
  Suspend, off
  Return
}


!s::
WinGet, name, ProcessName, A
if(name == MP){
  SendInput !{h}{b}
  SendInput {Enter}
  Return
}
else{
  Suspend, on
  SendEvent !{s}
  Suspend, off
  Return
}


!t::
WinGet, name, ProcessName, A
if(name == MP){
  SendInput !{h}{t}
  Return
}
else{
  Suspend, on
  SendEvent !{t}
  Suspend, off
  Return
}


;u
;v


!w::
WinGet, name, ProcessName, A
if(name == MP){
  SendInput !{h}{s}{e}{t}
  Return
}
else{
  Suspend, on
  SendEvent !{w}
  Suspend, off
  Return
}


!x::
WinGet, name, ProcessName, A
if(name == MP){
  SendInput !{h}{2}
  Return
}
else{
  Suspend, on
  SendEvent !{x}
  Suspend, off
  Return
}


;y


!z::
WinGet, name, ProcessName, A
if(name == MP){
  SendInput !{h}{1}
  Return
}
else{
  Suspend, on
  SendEvent !{z}
  Suspend, off
  Return
}


!2::
WinGet, name, ProcessName, A
if(name == DW){
  SendInput {Del}
  Return
}else{
  Suspend, on
  SendEvent !{2}
  Suspend, off
  Return
}
Return



!F12::
WinGet, name, ProcessName, A
if(name == MF){
  MsgBox, getready??!!
  Loop, 16{
    Click
    Sleep 50
    SendInput, {Enter}
    Sleep 50
  }  
  Return
}
else{
  Suspend, on
  SendEvent ^{F12}
  Suspend, off
  Return
}


!LButton::
WinGet, name, ProcessName, A
if(name == EX){
  SLeep 250
  Return
}
Return


; flag --->> SHIFT + key <<---



+F3::
WinGet, name, ProcessName, A
if(name == EX){
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





; flag --->> CTRL + key <<---


^u::
WinGet, name, ProcessName, A
if(name == EX){
  SendInput !{y}{1}
  Sleep 8
  SendInput {y}{z}
  Sleep 8
  Return
}
else{
  Suspend, on
  SendInput ^{u}
  Suspend, off
  Return
}
Return

^i::
WinGet, name, ProcessName, A
if(name == EX){
  SendInput !{y}{1}
  Sleep 8
  SendInput {y}{v}
  Sleep 8
  Return
}
else{
  Suspend, on
  SendInput ^{i}
  Suspend, off
  Return
}
Return



^F11::
WinGet, name, ProcessName, A
if(name == EX){
  SendInput !{y}{1}
  Sleep 8
  SendInput {y}{9}
  Sleep 8
  Return
}
else{
  Suspend, on
  SendInput ^{F11}
  Suspend, off
  Return
}
Return





^F12::
WinGet, name, ProcessName, A
if(name == EX){
  SendInput !{y}{1}
  Sleep 8
  SendInput {y}{c}
  Sleep 8
  Return
}
else{
  Suspend, on
  SendInput ^{F12}
  Suspend, off
  Return
}
Return







; flag --->> CTRL + SHIFT + key <<---



^+i::
WinGet, name, ProcessName, A
if(name == EX){
  MsgBox, empty
  Return
}
else{
  Suspend, on
  SendInput ^+{i}
  Suspend, off
  Return
}
Return


; flag --->> CTRL + ALT + key <<---


^!i::
WinGet, name, ProcessName, A
if(name == EX){
  SendInput !{y}{1}
  Sleep 8
  SendInput {y}{w}
  Sleep 8
  Return
}
else{
  Suspend, on
  SendInput ^!{i}
  Suspend, off
  Return
}
Return



^!u::
WinGet, name, ProcessName, A
if(name == EX){
  SendInput !{y}{1}
  Sleep 8
  SendInput {y}{y}{1}
  Sleep 8
  Return
}
else{
  Suspend, on
  SendInput ^!{u}
  Suspend, off
  Return
}
Return




; flag --->> WINDOWS + key <<---

#1::
MsgBox, empty
Return

#2::
MsgBox, empty
Return

#3::
MsgBox, empty
Return

#4::
MsgBox, empty
Return

#5::
MsgBox, empty
Return

#6::
MsgBox, empty
Return

;not in use anymore
#w::
MsgBox, empty
Return

#e::
  Run C:\Users\2053.DOMAINH\Documents\KeypadHome\Stocker\11指図
Return

#P Up::
SendMessage, 0x112, 0xF140, 0,, Program Manager
BlockInput On
Sleep 3000
BlockInput Off
Return


#s::
WinGet, name, ProcessName, A
if(name == "pythonw.exe"){
  MsgBox, "sar_from_pythonw.exe"
  Sleep 100
  SendInput ^{s}
  Sleep 500
  SendInput {F5}
  Sleep 100
  Return
}
if(name == "mi.exe"){
  MsgBox, "sar_from_mi.exe"
  Sleep 100
  SendInput ^{s}
  Sleep 500
  Reload
  Sleep 100
  Return
}
if(name == "SciTE.exe"){
  MsgBox, "sar_from_SciTE.exe"
  Sleep 64
  SendInput ^{s}
  Sleep 64
  Reload
  Return
}
else{
  Suspend, on
  MsgBox, "autoSaveUnavailable"
  Suspend, off
  Return
}
Return


#space::
  WinGet, pName, ProcessName, A
  MsgBox, ID "%pName%"
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
  ;  Return
Return


#F3::
  NowD:=A_Now
  stringMid, NowD, NowD, 3, 10
  SendInput _
  SendInput %NowD%
  ;MsgBox, 4, ,"Func3"
  ;IfMsgBox No
  ;  Return
Return


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
  ;  Return
Return

#Y::
#Up::
  MouseMove, 0, -8, 2, R
Return


#H::
#Down::
  MouseMove, 0, 8, 2, R
Return

#N::
#Left::
  MouseMove, -8, 0, 2, R
Return

#M::
#Right::
  MouseMove, 8, 0, 2, R
Return

#J::
  SendInput {LButton}
Return

#K::
  SendInput {RButton}
Return

; flag --->> CustomCombinations of JCapsLock <<---

~vkF0sc03A:: ; Tab Key
WinGet, name, ProcessName, A
if(name == "netbeans.exe"){
  SendInput ^{Space}
  Return
}
else{
  MsgBox % "popopo"
  Return
}



; flag --->> CustomCombinations of JHennkann <<---


vk1Csc079::
WinGet, name, ProcessName, A
if(name == EX){
  SendInput ^{PgDn}
  Return
}
else{
  Return
}


vk1Csc079 & Space::
WinGet, name, ProcessName, A
if(name == EX){
  SendInput ^{PgUp}
  Return
}
else{
  Return
}




; flag --->> CustomCombinations of JMuhennkann <<---
vk1Dsc07B & w::
  SendInput {Up}
Return

vk1Dsc07B & s::
  SendInput {Down}
Return

vk1Dsc07B & a::
  SendInput {Left}
Return

vk1Dsc07B & d::
  SendInput {Right}
Return

vk1Dsc07B & q::
  SendInput {Home}
Return

vk1Dsc07B & e::
  SendInput {End}
Return

vk1Dsc07B & r::
  SendInput {PgUp}
Return

vk1Dsc07B & f::
  SendInput {PgDn}
Return


vk1Dsc07B & z::
  SendInput {Bs}
Return



vk1Dsc07B & x::
  SendInput {Enter}
Return

vk1Dsc07B & c::
  SendInput {Del}
Return


vk1Dsc07B & 1::
  SendInput {.}
Return

vk1Dsc07B & 2::
  SendInput {7}
Return

vk1Dsc07B & 3::
  SendInput {8}
Return

vk1Dsc07B & 4::
  SendInput {9}
Return

vk1Dsc07B & 5::
  SendInput {0}
Return


; flag --->> BASIC KEY STROKE <<---


vkE2sc073::
SendInput {_}
Return


+NumpadEnter::
+Enter::
Sleep 128
SendInput {vkF3sc029}
Sleep 128
Return




Insert::
  MsgBox, "Dont touch me!!"
Return


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


 ; eof
