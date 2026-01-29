import pygame

from autodrive.common import meta, color
from .scene import Scene

class Game:
    def __init__(self, dim):
        pygame.init()

        self.surface = pygame.display.set_mode(dim)
        pygame.display.set_caption(meta.title)

        self.fps = 60
        self.clock = pygame.time.Clock()

        self.running = True

        self.scene = Scene(self.surface)

    def run(self):
        dt = 0

        while self.running:
            self.clear()

            self.process_events()
            self.handle_input()
            self.update(dt)
            self.draw(self.surface)

            dt = self.clock.tick(self.fps) / 1000.0

    def cleanup(self):
        pygame.quit()

    def clear(self):
        self.surface.fill(color.BLACK)

    def process_events(self):
        for evt in pygame.event.get():
            if evt.type == pygame.QUIT:
                self.running = False
                break

    def handle_input(self):
        self.scene.handle_input()

    def update(self, dt):
        self.scene.update(dt)

    def draw(self, surface):
        self.scene.draw(surface)

        pygame.display.update()
        # pygame.display.flip()