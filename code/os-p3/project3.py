#!/usr/bin/env python3

import argparse
import sys

import command

def main() -> None:
    parser = build_parser()
    args = parser.parse_args()

    if args.command is None:
        parser.print_help()
        sys.exit(1)

    if args.command == "create":
        command.create(args.filename)
    elif args.command == "insert":
        if args.key < 0 or args.value < 0:
            print("Error: Key and value must be non-negative integers.", file=sys.stderr)
            sys.exit(1)

        command.insert(args.filename, args.key, args.value)
    elif args.command == "search":
        if args.key < 0:
            print("Error: Key must be a non-negative integer.", file=sys.stderr)
            sys.exit(1)

        command.search(args.filename, args.key)
    elif args.command == "load":
        command.load(args.filename, args.csv_filename)
    elif args.command == "print":
        command.printcmd(args.filename)
    elif args.command == "extract":
        command.extract(args.filename, args.output_filename)

def build_parser() -> argparse.ArgumentParser:
    parser = argparse.ArgumentParser(
        description="Index File Manager",
        prog="project3"
    )

    subparsers = parser.add_subparsers(dest="command", help="Commands")

    create_parser = subparsers.add_parser("create", help="Create a new index file")
    create_parser.add_argument("filename", help="Name of the index file to create")

    insert_parser = subparsers.add_parser("insert", help="Insert a key/value pair")
    insert_parser.add_argument("filename", help="Name of the index file")
    insert_parser.add_argument("key", type=int, help="Key (uint)")
    insert_parser.add_argument("value", type=int, help="Value (uint)")

    search_parser = subparsers.add_parser("search", help="Search for a key")
    search_parser.add_argument("filename", help="Name of the index file")
    search_parser.add_argument("key", type=int, help="Key to search for")

    load_parser = subparsers.add_parser("load", help="Load key/value pairs from CSV")
    load_parser.add_argument("filename", help="Name of the index file")
    load_parser.add_argument("csv_filename", help="Name of the CSV file to load")

    print_parser = subparsers.add_parser("print", help="Print all key/value pairs")
    print_parser.add_argument("filename", help="Name of the index file")

    extract_parser = subparsers.add_parser("extract", help="Extract key/value pairs to CSV")
    extract_parser.add_argument("filename", help="Name of the index file")
    extract_parser.add_argument("output_filename", help="Name of the output CSV file")

    return parser

if __name__ == "__main__":
    main()
