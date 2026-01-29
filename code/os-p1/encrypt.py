import sys

class CypherError(Exception):
    pass

class Cypher:
    def __init__(self):
        self.passkey = None

    def set_passkey(self, key):
        self.passkey = key.upper()

    def encrypt(self, value):
        if self.passkey is None:
            raise CypherError("Password not set")

        result = ''
        offset = 65

        for i, c in enumerate(value.upper()):
            if not c.isalpha():
                raise CypherError("String to encrypt must be alphabetical.")

            relative_input = ord(c) - offset
            key_offset = ord(self.passkey[i % len(self.passkey)]) - offset

            relative_output = (relative_input + key_offset) % 26

            result += chr(relative_output + offset)

        return result.upper()

    def decrypt(self, value):
        if self.passkey is None:
            raise CypherError("Password not set")

        result = ''
        offset = 65

        for i, c in enumerate(value.upper()):
            if not c.isalpha():
                raise CypherError("String to encrypt must be alphabetical.")

            relative_input = ord(c) - offset
            key_offset = ord(self.passkey[i % len(self.passkey)]) - offset

            relative_output = (relative_input - key_offset) % 26

            result += chr(relative_output + offset)

        return result.upper()

def parse_command(line):
    line_pieces = line.split(' ', 1)
    if len(line_pieces) != 2:
        raise CypherError("Malformatted command. Unable to parse.")

    [command, argument] = line_pieces

    return (command, argument)

def main():
    nargs = len(sys.argv)

    if nargs > 1:
        sys.stdout.write("ERROR Too many arguments given.\n")
        sys.stdout.flush()
        exit(1)

    input_str = input()

    cypher = Cypher()

    while input_str.upper() != 'QUIT':
        try:
            command, argument = parse_command(input_str.lower())

            result = ''

            if command.upper() == 'PASS':
                cypher.set_passkey(argument)
            elif command.upper() == 'ENCRYPT':
                result = cypher.encrypt(argument)
            elif command.upper() == 'DECRYPT':
                result = cypher.decrypt(argument)

            if result:
                sys.stdout.write("RESULT " + result + '\n')
        except CypherError as e:
            sys.stdout.write("ERROR " + str(e) + '\n')

        sys.stdout.flush()

        input_str = input()

if __name__ == '__main__':
    main()
