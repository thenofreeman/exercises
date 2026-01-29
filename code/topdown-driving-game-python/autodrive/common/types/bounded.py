class Bounded:
    def __init__(self, initial, *, min=0.0, max=1.0):
        self._min = min
        self._max = max

        self._assign_inbounds(initial)

    @property
    def min(self):
        return self._min

    @property
    def max(self):
        return self._max

    @property
    def value(self):
        return self._value

    @value.setter
    def value(self, new_value):
        self._assign_inbounds(new_value)

    def __iadd__(self, other):
        self._assign_inbounds(self._value + other)
        return self

    def __isub__(self, other):
        self._assign_inbounds(self._value - other)
        return self

    def __imul__(self, other):
        self._assign_inbounds(self._value * other)
        return self

    def __itruediv__(self, other):
        self._assign_inbounds(self._value / other)
        return self

    def __ifloordiv__(self, other):
        self._assign_inbounds(self._value // other)
        return self

    def __imod__(self, other):
        self._assign_inbounds(self._value % other)
        return self

    def __ipow__(self, other):
        self._assign_inbounds(self._value ** other)
        return self

    def __add__(self, other):
        return self._value + other

    def __sub__(self, other):
        return self._value - other

    def __mul__(self, other):
        return self._value * other

    def __truediv__(self, other):
        return self._value / other

    def __floordiv__(self, other):
        return self._value // other

    def __mod__(self, other):
        return self._value % other

    def __pow__(self, other):
        return self._value ** other

    def __radd__(self, other):
        return other + self._value

    def __rsub__(self, other):
        return other - self._value

    def __rmul__(self, other):
        return other * self._value

    def __rtruediv__(self, other):
        return other / self._value

    def __rfloordiv__(self, other):
        return other // self._value

    def __rmod__(self, other):
        return other % self._value

    def __rpow__(self, other):
        return other ** self._value

    def __lt__(self, other):
        return self._value < other

    def __le__(self, other):
        return self._value <= other

    def __gt__(self, other):
        return self._value > other

    def __ge__(self, other):
        return self._value >= other

    def __eq__(self, other):
        return self._value == other

    def __ne__(self, other):
        return self._value != other

    def __pos__(self):
        return self._value

    def __neg__(self):
        return -self._value

    def __abs__(self):
        return abs(self._value)

    def __repr__(self):
        return f"Bounded({self._min} <= {self._value} <= {self._max_val})"

    def __str__(self):
        return str(self._value)

    def __int__(self):
        return int(self._value)
    
    def __float__(self):
        return float(self._value)
    
    def __bool__(self):
        return bool(self._value)

    def _assign_inbounds(self, new_value):
        self._value = max(self._min, min(new_value, self._max))