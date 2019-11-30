set -x
$(aws ecr get-login --no-include-email --region eu-west-2)
./gradlew clean release --no-daemon --info