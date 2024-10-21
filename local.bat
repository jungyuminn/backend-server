@echo off
setlocal enabledelayedexpansion

:: 서비스 목록 정의 (필요에 따라 폴더명 추가)
set services=club-service api-gateway

:: 각 서비스에 대해 빌드 실행
for %%s in (%services%) do (
    echo Building image for %%s...

    :: 서비스별로 이미지 이름을 "서비스명-local" 형식으로 설정
    set IMAGE_NAME=%%s-local
    set IMAGE_TAG=latest

    :: 서비스 디렉토리로 이동
    cd %%s || (
        echo Failed to enter directory %%s
        exit /b 1
    )

    :: Jib 빌드 실행
    call gradlew jibDockerBuild --image=!IMAGE_NAME!:!IMAGE_TAG!

    :: 빌드 결과 확인
    if !errorlevel! equ 0 (
        echo %%s build succeeded.
    ) else (
        echo %%s build failed.
        exit /b 1
    )

    :: 원래 디렉토리로 복귀
    cd ..
)

echo All builds completed.