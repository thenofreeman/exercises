import sys

from autodrive.engine import Game

def main():
    game = Game((1000, 1000))
    game.run()

    game.cleanup()
    sys.exit()

if __name__ == "__main__":
    main()