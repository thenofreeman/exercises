import pygame
import math

from autodrive.common.types.bounded import Bounded
import autodrive.common.graphics as graphics

class Car(pygame.sprite.Sprite):
    def __init__(self, pos, dim, img_path):
        super().__init__()

        self.image = graphics.load_image(img_path, dim)
        self.body = self.image.get_rect()
        self.body.topleft = pos
        self.x, self.y = self.body.topleft    

        self.rotation = 0
        self.rotation_speed = 240
        
        self._speed = Bounded(0.0, min=0.0, max=400)
        self._steer_angle = Bounded(0.0, min=-1.0, max=1.0)
        self._throttle = Bounded(0.0, min=0.0, max=1.0)
        self._brake = Bounded(0.0, min=0.0, max=1.0)
        self._gear = Gear.DRIVE

        self._max_accel = 200
        self._max_decel = 400

    def steer(self, angle):
        self._steer_angle.value = angle

    def accelerate(self, pressure):
        self._throttle.value = pressure

    def brake(self, pressure):
        self._brake.value = pressure

    def update(self, dt):
        resistance = 1.0 - (self._speed / self._speed.max)
        accel = (self._throttle * self._max_accel) * max(0.0, resistance)

        if accel == 0.0:
            natural_decel = self._max_accel * max(0.0, resistance)
        else:
            natural_decel = 0

        brake_decel = (self._brake * self._max_decel)
        decel = natural_decel + brake_decel

        if self._gear == Gear.PARK:
            self._speed.value = 0
        elif self._gear == Gear.REVERSE:
            self._speed += accel * dt
        elif self._gear == Gear.NEUTRAL:
            pass
        elif self._gear == Gear.DRIVE:
            self._speed += (accel - decel) * dt
            
            self.rotation += self._steer_angle * self.rotation_speed * (self._speed / self._speed.max) * dt

            self.x -= self._speed * dt * math.sin(math.radians(self.rotation))
            self.y -= self._speed * dt * math.cos(math.radians(self.rotation))

        self.body.topleft = (self.x, self.y)

    def draw(self, surface, offset):
        graphics.blit_image(surface, self.image, self.body.topleft - offset, self.rotation)

    @property
    def speed(self):
        return self._speed

from enum import Enum

class Gear(Enum):
    PARK=0
    REVERSE=1
    NEUTRAL=2
    DRIVE=3