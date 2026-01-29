
import common

class Node:
    def __init__(self, filename: str, block_id: int, parent_id: int = 0) -> None:
        self.filename = filename
        self.block_id = block_id
        self.parent_id = parent_id
        self.count = 0
        self.keys = [0] * common.MAX_KEYS
        self.values = [0] * common.MAX_KEYS
        self.children = [0] * common.MAX_CHILDREN

    @classmethod
    def read(cls, filename: str, block_id: int):
        with open(filename, "rb") as f:
            f.seek(block_id * common.BLOCK_SIZE)
            data = f.read(common.BLOCK_SIZE)

            node = cls(filename, int.from_bytes(data[0:8], "big"), int.from_bytes(data[8:16], "big"))
            node.count = int.from_bytes(data[16:24], "big")

            node.keys = []
            offset = 24
            for _ in range(common.MAX_KEYS):
                key = int.from_bytes(data[offset:offset + 8], "big")
                node.keys.append(key)

                offset += 8

            node.values = []
            for _ in range(common.MAX_KEYS):
                value = int.from_bytes(data[offset:offset + 8], "big")
                node.values.append(value)

                offset += 8

            node.children = []
            for _ in range(common.MAX_CHILDREN):
                child = int.from_bytes(data[offset:offset + 8], "big")
                node.children.append(child)

                offset += 8

            return node

    def write(self) -> None:
        with open(self.filename, "r+b") as f:
            data = bytearray(common.BLOCK_SIZE)

            data[0:8] = self.block_id.to_bytes(8, "big")
            data[8:16] = self.parent_id.to_bytes(8, "big")
            data[16:24] = self.count.to_bytes(8, "big")

            offset = 24
            for i in range(common.MAX_KEYS):
                data[offset:offset + 8] = self.keys[i].to_bytes(8, "big")
                offset += 8

            for i in range(common.MAX_KEYS):
                data[offset:offset + 8] = self.values[i].to_bytes(8, "big")
                offset += 8

            for i in range(common.MAX_CHILDREN):
                data[offset:offset + 8] = self.children[i].to_bytes(8, "big")
                offset += 8

            f.seek(self.block_id * common.BLOCK_SIZE)
            f.write(bytes(data))
            f.flush()

    def leaf(self) -> bool:
        return self.children[0] == 0

    def full(self) -> bool:
        return self.count == common.MAX_KEYS
