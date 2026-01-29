# OS Project 1

This is used to run an encryption program and log the actions taken. It utilizes pipes to communicate between processes between the main program and the programs that handle encryption and logging respectively.

## Running the program

Simply invole python with the main driver file.

`python driver.py <somefile-for-output>`

eg. if the output file is log.txt:

`python driver.py log.txt`

## Files

- driver.py
- logger.py
- encrypt.py

`driver.py` is the glue between the three programs, it creates subprocesses of the other files to handle logging and ecryption and handles all of the communication between the other two.

`logger.py` simply receives input from stdin as commands and writes them to a file.

`encrypt.py` sections off the encryption logic from the main program. It handles the encryption and decryption from commands given via stdin and produces a result to stdout.
