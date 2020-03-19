rem "auto_lame_on_boardrate_64"
for %%i in (*.wav) do (
"lame" -b 64 "%%i"
)
pause
