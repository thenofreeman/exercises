import sys
from datetime import datetime

# line -> (action, message, time)
def parse_command(line):
    line_pieces = line.split(' ', 1)
    if len(line_pieces) != 2:
        sys.stdout.write("ERROR Malformatted command. Unable to parse.\n")
        sys.stdout.flush()
        exit(1)

    [action, message] = line_pieces

    return (action, message, datetime.now())

# command, filename -> void
# YYYY-MM-DD HH:MM [ACTION] MESSAGE
def write_command_to_file(command, f):
    action, message, time = command

    formatted_time = time.strftime("%Y-%m-%d %H:%M")

    file_output = f"{formatted_time} [{action}] {message}\n"

    f.write(file_output)
    f.flush()

# cliargs = 
def main():
    nargs = len(sys.argv)

    if nargs <= 1:
        sys.stdout.write("ERROR Logger needs to be passed a filename.\n")
        sys.stdout.flush()
        exit(1)
    elif nargs > 2:
        sys.stdout.write("ERROR Too many arguments given.\n")
        sys.stdout.flush()
        exit(1)

    output_filename = sys.argv[1]

    try:
        with open(output_filename, 'a') as file:
            command_str = input()

            while command_str.upper() != 'QUIT':
                command = parse_command(command_str)
                write_command_to_file(command, file)
                command_str = input()

            command = parse_command(command_str)
            write_command_to_file(command, file)

    except OSError as e:
        sys.stdout.write(f"ERROR Unable to open log file: {e}\n")
        sys.stdout.flush()
        exit(1)

if __name__ == '__main__':
    main()
