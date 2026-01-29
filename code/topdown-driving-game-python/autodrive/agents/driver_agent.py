from autodrive.entities.car import Car
from autodrive.agents import Agent

class DriverAgent(Agent):
    def __init__(self):
        super().__init__()

        self.car = Car()
