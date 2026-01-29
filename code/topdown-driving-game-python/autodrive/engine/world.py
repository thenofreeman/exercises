import pygame
import sys

class World:
    def __init__(self, filename: str, tile_size: int = 150):
        self.tile_size = tile_size
        self.walls = []
        self.player_start_pos = None
        self._parse_map(filename)

    def _parse_map(self, filename: str):
        try:
            with open(filename, 'r') as f:
                for y, line in enumerate(f):
                    cleaned_line = line.replace('.', ' ')

                    for x, char in enumerate(cleaned_line):
                        px, py = x * self.tile_size, y * self.tile_size
                        
                        if char == '%':
                            self.walls.append(pygame.Rect(px, py, self.tile_size, self.tile_size))
                        elif char == 'P':
                            center_x = px + self.tile_size // 2
                            center_y = py + self.tile_size // 2
                            self.player_start_pos = (center_x, center_y)
        except FileNotFoundError:
            print(f"Error: Map file not found at '{filename}'")
            sys.exit(1)
        except Exception as e:
            print(f"An error occurred while parsing the map file: {e}")
            sys.exit(1)

    def draw(self, surface, offset):
        for wr in self.walls:
            wall_rect = (wr[0] - offset[0], wr[1] - offset[1], wr[2], wr[3])
            
            pygame.draw.rect(surface, (130, 130, 130), wall_rect)