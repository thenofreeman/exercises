import os
import sys

from header import Header
from btree import BTree

def fatal_error(message: str) -> None:
    print(f"Error: {message}.", file=sys.stderr)
    sys.exit(1)

def assert_file(filename: str, exists: bool = True) -> None:
    if os.path.exists(filename) != exists:
        fatal_error(f"File '{filename}' {'exists' if not exists else 'doesn\'t exist'}")

def assert_header(header, filename: str) -> None:
    if header is None:
        fatal_error(f"{filename}' is not an index file")

def create(filename: str) -> None:
    assert_file(filename, exists=False)

    # Create an empty file first
    with open(filename, "wb"):
        pass

    header = Header(filename)
    assert_header(header, filename)

    header.write()

    print(f"Created index file: {filename}")

def insert(filename: str, key: int, value: int) -> None:
    assert_file(filename, exists=True)

    header = Header.read(filename)
    assert_header(header, filename)

    btree = BTree(filename, header)
    btree.insert(key, value)

    print(f"Inserted: ({key}, {value})")

def search(filename: str, key: int) -> None:
    assert_file(filename, exists=True)

    header = Header.read(filename)
    assert_header(header, filename)

    btree = BTree(filename, header)
    result = btree.search(key)

    if not result:
        fatal_error(f"Error: Key {key} not found")

    print(f"Found: ({result[0]}, {result[1]})")

def load(filename: str, csv_filename: str) -> None:
    assert_file(filename, exists=True)
    assert_file(csv_filename, exists=True)

    header = Header.read(filename)
    assert_header(header, filename)

    btree = BTree(filename, header)
    count = 0
    with open(csv_filename, "r") as csvf:
        for line in csvf:
            line = line.strip()

            if not line: # ignoring empty lines
                continue

            parts = line.split(",")
            if len(parts) != 2:
                fatal_error(f"Invalid CSV format: not key/value pairs")

            try:
                key = int(parts[0].strip())
                value = int(parts[1].strip())

                if key < 0 or value < 0:
                    raise ValueError("negative key/value entries")

                btree.insert(key, value)

                count += 1
            except ValueError as e:
                fatal_error(f"Invalid CSV format: {e}")

    print(f"Loaded {count} key/value pairs from '{csv_filename}'")

def printcmd(filename: str) -> None:
    assert_file(filename, exists=True)

    header = Header.read(filename)
    assert_header(header, filename)

    if header.root_id == 0:
        print("Index is empty.")
        return

    btree = BTree(filename, header)
    results = btree.traverse()

    for key, value in results:
        print(f"({key}, {value})")

def extract(filename: str, out_filename: str) -> None:
    assert_file(filename, exists=True)
    assert_file(out_filename, exists=False)

    header = Header.read(filename)
    assert_header(header, filename)

    results = []

    if header.root_id != 0:
        btree = BTree(filename, header)
        results = btree.traverse()

    with open(out_filename, "w") as outf:
        for key, value in results:
            outf.write(f"{key},{value}\n")

    print(f"Extracted {len(results)} key/value pairs to '{out_filename}'")
