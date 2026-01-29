from autodrive.agents import SatNav, DriverAgent

class AutonomousDriverAgent(DriverAgent):
    def __init__(self):
        super().__init__()

        self.navigation = SatNav()

    def refine_route(self):
        """
        Simulated Annealing
        """
        pass

    def opmtimize_steering(self):
        """
        Hill-Climbing Search
        """
        pass

    def opmtimize_throttle(self):
        """
        Hill-Climbing Search
        """
        pass

