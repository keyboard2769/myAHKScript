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

;@prompt


#f::
InputBox, lpInputString, SAXXXX, "enterString`nasyouknow", ,320,128 
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
  ;--
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









;eof
