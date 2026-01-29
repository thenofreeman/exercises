import sys
from subprocess import Popen, PIPE

history = []

def display_menu():
    print()
    print("---------- MENU ----------")
    print("Available Commands:")
    print(" - password")
    print(" - encrypt")
    print(" - decrypt")
    print(" - history")
    print(" - quit")
    print("-----------------------------")

def display_history():
    print()
    if len(history) == 0:
        print("No history to display.")
        return False

    print("---------- HISTORY ----------")
    for i, item in enumerate(history):
        print(f"{i+1} - {item}")
    print(f"0 - DONT USE HISTORY")
    print("-----------------------------")

    return True

def history_prompt():
    while True:
        print()
        yn = input("Use History? (y/N): ")

        if (yn.upper() == 'Y'):
            while True:
                if display_history():
                    try:
                        sel = int(input("Selection: "))
                    except ValueError:
                        print("Make a valid selection.")
                        continue

                    if sel == 0:
                        return None
                    elif sel > 0 and sel < len(history) + 1:
                        return sel - 1
                    else:
                        print("Make a valid selection.")
                        continue
                else:
                    return None
        elif (yn.upper() == 'N' or yn.upper() == ''):
            return None
        else:
            print("Make a valid selection.")

def set_password(e, l):
    sel = history_prompt()

    if sel is not None:
        pw = history[sel]
    else:
        pw = input("Enter a password: ")

    if not pw.isalpha():
        print("Password must contain only alphabetic characters.")
        return

    command = f"PASS {pw}\n"

    e.stdin.write(command)
    e.stdin.flush()
    l.stdin.write(command)
    l.stdin.flush()

def do_encryption(e, l, decrypt=False):
    action = 'decrypt' if decrypt else 'encrypt'

    sel = history_prompt()

    if sel is not None:
        input_str = history[sel]
    else:
        input_str = input(f"Enter a string to {action}: ")

    if not input_str.isalpha():
        print(f"Input string must contain only alphabetic characters.")
        return

    command = f"{action.upper()} {input_str}\n"

    e.stdin.write(command)
    e.stdin.flush()
    l.stdin.write(command)
    l.stdin.flush()

    resp = e.stdout.readline().rstrip()

    if resp.startswith("RESULT"):
        print(f"{action.capitalize()}ed {input_str} as {resp.split(' ')[1]}.")
        if input_str.lower() not in history:
            history.append(input_str.lower())
    else:
        print(f"Failed to {action}.")

def main():
    nargs = len(sys.argv)

    if nargs <= 1:
        print("[ERROR] Driver needs to be passed a filename.")
        exit(1)
    elif nargs > 2:
        print("[ERROR] Too many arguments given.")
        exit(1)

    logger = Popen(['python3', 'logger.py', sys.argv[1]], stdout=PIPE, stdin=PIPE, encoding='utf8')
    encryptor = Popen(['python3', 'encrypt.py'], stdout=PIPE, stdin=PIPE, encoding='utf8')

    try:
        logger.stdin.write("START Logging started.\n")
        logger.stdin.flush()

        while True:
            display_menu()
            command = input("Enter a command: ")

            if command.lower() == "password":
                set_password(encryptor, logger)
            elif command.lower() == "encrypt":
                do_encryption(encryptor, logger)
            elif command.lower() == "decrypt":
                do_encryption(encryptor, logger, decrypt=True)
            elif command.lower() == "history":
                display_history()
            elif command.lower() == "quit":
                break
            else:
                print()
                print("Not a valid command.")

    except Exception as e:
        print(f"[ERROR] {e}")

    finally:
        encryptor.stdin.write("QUIT\n")
        encryptor.stdin.flush()
        encryptor.wait()

        logger.stdin.write("QUIT\n")
        logger.stdin.flush()
        logger.wait()

if __name__ == '__main__':
    main()