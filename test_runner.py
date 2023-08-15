# test_runner.py

import os
import sys

# Commands
# build = ".././gradlew clean build --refresh-dependencies -x test > src/main/java/com/rumpus/rumpus/log/build.log"
# buildXlint = ".././gradlew clean build --refresh-dependencies -x test -Xlint:unchecked > src/main/java/com/rumpus/rumpus/log/build.log"
# buildDebug = ".././gradlew clean build --refresh-dependencies --debug -x test > src/main/java/com/rumpus/rumpus/log/build.log"
# buildTest = ".././gradlew clean build --refresh-dependencies > src/main/java/com/rumpus/rumpus/log/build.log"
# buildTestInfo = ".././gradlew clean build --info --refresh-dependencies > src/main/java/com/rumpus/rumpus/log/build.log"
# buildTestDebug = ".././gradlew clean build --debug --refresh-dependencies > src/main/java/com/rumpus/rumpus/log/build.log"
test = ".././gradlew test > src/test/java/com/rumpus/common/log/test.log"
# run = "./gradlew bootRun > src/main/java/com/rumpus/rumpus/log/spring.log"
# runDebug = "./gradlew bootRun --debug > src/main/java/com/rumpus/rumpus/log/spring.log"
# moveNodeModules = "mv -vf ./frontend/node_modules ./"
# movePackage = "mv -vf ./frontend/package-lock.json ./"

def commands():
    pass

if __name__ == '__main__':
    print(f"Arguments count: {len(sys.argv) - 1}")
    for i, arg in enumerate(sys.argv):
        if i == 0:
            continue
        print(f"{i:>6}: {arg}")

        if arg == "test":
            os.system(test)
        else:
            print("Error: bad argument")
        