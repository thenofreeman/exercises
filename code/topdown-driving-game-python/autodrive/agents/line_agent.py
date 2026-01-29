from autodrive.agents import DriverAgent

class LineDriverAgent(DriverAgent):
    """
    LineDriverAgent follows a consistent path.

    Types of paths:
    - route: a hande-made mapped out route
    - stayleft: always takes the left path
    - stayright: always takes the right path
    - leftright: alternates moves left, right
    - random: each decision is randomized (left, right, forward)
    """
    def __init__(self):
        super().__init__()