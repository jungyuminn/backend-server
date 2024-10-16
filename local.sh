#!/bin/bash

# 서비스 목록 정의 (필요에 따라 폴더명 추가)
services=("club-service" "api-gateway")

# 각 서비스에 대해 빌드 실행
for service in "${services[@]}"; do
  echo "Building image for $service..."

  # 서비스별로 이미지 이름을 "서비스명-local" 형식으로 설정
  IMAGE_NAME="${service}-local"
  IMAGE_TAG="latest"
  
  # 서비스 디렉토리로 이동
  cd "$service" || { echo "Failed to enter directory $service"; exit 1; }
  
  # Jib 빌드 실행
  IMAGE_NAME="$IMAGE_NAME" IMAGE_TAG="$IMAGE_TAG" ./gradlew jibDockerBuild --image="$IMAGE_NAME:$IMAGE_TAG"
  
  # 빌드 결과 확인
  if [ $? -eq 0 ]; then
    echo "$service build succeeded."
  else
    echo "$service build failed."
    exit 1
  fi
  
  # 원래 디렉토리로 복귀
  cd ..
done

echo "All builds completed."
