import pygame

from autodrive.entities import Car
from .world import World
import autodrive.common.graphics as graphics
from autodrive.common import color

class Scene:
    def __init__(self, surface):
        self.offset = pygame.math.Vector2()

        self.car = Car((500, 500), (25, 50), "./autodrive/resources/red-car.png")
        self.world = World(filename="./autodrive/resources/pacman.lay", tile_size=150)

    def handle_input(self):
        keys = pygame.key.get_pressed()
        
        if keys[pygame.K_UP]:
            self.car.accelerate(1.0)
        else:
            self.car.accelerate(0.0)
            
        if keys[pygame.K_DOWN]:
            self.car.brake(1.0)
        else:
            self.car.brake(0.0)
        
        if keys[pygame.K_LEFT]:
            self.car.steer(1.0)
        elif keys[pygame.K_RIGHT]:
            self.car.steer(-1.0)
        else:
            self.car.steer(0.0)

    def update(self, dt):
        self.car.update(dt)

        self.car.speed

    def draw(self, surface):
        self.offset.x = self.car.body.topleft[0] - surface.get_size()[0] // 2
        self.offset.y = self.car.body.topleft[1] - surface.get_size()[1] // 2

        self.car.draw(surface, self.offset)
        self.world.draw(surface, self.offset)

        surface.blit(graphics.font.render(f"{self.car.speed / 10} mph", False, color.WHITE), (10, 10))