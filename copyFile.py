import sys
import os
import shutil


def main(argv):
    print("\nSTARTING PYTHON COPY SCRIPT.")
    file_to_copy = ''
    if len(argv) > 1:
        file_to_copy = argv[1]
    else:
        print("Usage:")
        print("copyFile.py <DestinationOfFileToCopy>")
        sys.exit(2)

    print("Attempting to copy provided file.")
    try:
        shutil.copyfile(file_to_copy, os.path.dirname(os.path.realpath(__file__)) + os.path.sep + "runnable" + os.path.sep + "MorseGUI.jar")
        print("File copied successfully")
    except IOError:
        print("Error occured in copying of file. Check command line arguments.")


if __name__ == '__main__':
    main(sys.argv)