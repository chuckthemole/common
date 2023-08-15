# test_runner.py

import os
import sys

# Commands
test = ".././gradlew test > src/test/java/com/rumpus/common/log/test.log"

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
        