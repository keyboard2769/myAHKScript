for %%i in (*.pdf) do (
"java" -jar pdfbox-app-2.0.14.jar PrintPDF -silentPrint "%%i"
)
pause