#/usr/bin/env sh

REPOSITORY_NAME=look-secret
rm -rf $REPOSITORY_NAME

git clone git@github.com:edu-look/$REPOSITORY_NAME.git

if [ $? != 0 ] ; then
  echo -e "\033[31mConfigure o acesso a SSH na sua conta Github\033[0m"
  exit 1
fi

cp $REPOSITORY_NAME/application-dev.yaml ./src/main/resources


rm -rf $REPOSITORY_NAME

if [ -z $JAVA_HOME ] ; then
  echo "JAVA_HOME não está definido"
  exit 1
fi

./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
