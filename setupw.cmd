@echo off

set REPOSITORY_NAME=look-secret
rmdir /s /q %REPOSITORY_NAME%
rmdir /s /q tokens

git clone git@github.com:edu-look/%REPOSITORY_NAME%.git

if %errorlevel% neq 0 (
  echo Configure o acesso a SSH na sua conta Github
  exit /b 1
)

xcopy %REPOSITORY_NAME%\* .\src\main\resources /s /y

rmdir /s /q %REPOSITORY_NAME%

if "%JAVA_HOME%" == "" (
  echo JAVA_HOME não está definido
  exit /b 1
)

.\mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=dev
