rem the attr list well anyway
rem コントロールパネルのフォルダ:%none%
rem システムのプロパティ：sysdm
rem 画面のプロパティ:desk
rem アプリケーションの追加と削除のプロパティ:appwiz
rem パスワードのプロパティ：password
rem インターネットのプロパティ：inetcpl
rem ネットワーク：netcpl
rem 地域のプロパティ：intl
rem マウスのプロパティ：main.cpl @0
rem キーボードのプロパティ：main.cpl @1
rem プリンタのフォルダ：main.cpl @2
rem フォントのフォルダ：@3
rundll32 shell32,Control_RunDLL desk.cpl
pause
