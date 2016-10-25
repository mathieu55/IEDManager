del target\*.dll
ikvmc.exe -target:library -out:target/%1-.dll target/%1.jar
xcopy %USERPROFILE%\.m2\repository\org\ububiGroup\* release\org\ububiGroup\* /q /y /s /i