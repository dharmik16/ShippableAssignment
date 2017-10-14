.PHONY: all build test clean

build:
	./gradlew clean build shadowJar
run:
	java -cp build/libs/GetOpenIssueStats-1.0-SNAPSHOT-all.jar OpenIssueUI