
lpInputString="</>"


if(lpInputString=="test"){
  MsgBox % "test`n new line"
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
  return
}

return