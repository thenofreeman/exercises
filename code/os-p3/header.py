from typing import Optional
import common

class Header:
    def __init__(self, filename: str, root_id: int = 0, next_block_id: int = 1) -> None:
        self.filename = filename
        self.root_id = root_id
        self.next_block_id = next_block_id

    @classmethod
    def read(cls, filename: str):
        with open(filename, "rb") as f:
            data = f.read(common.BLOCK_SIZE)

            if len(data) < 24:
                return None

            magic_number = data[0:8]
            if magic_number != common.MAGIC_NUMBER:
                return None

            root_id = int.from_bytes(data[8:16], "big")
            next_block_id = int.from_bytes(data[16:24], "big")

            return cls(filename, root_id, next_block_id)

    def write(self) -> None:
        with open(self.filename, "r+b") as f:
            data = bytearray(common.BLOCK_SIZE)

            data[0:8] = common.MAGIC_NUMBER
            data[8:16] = self.root_id.to_bytes(8, "big")
            data[16:24] = self.next_block_id.to_bytes(8, "big")

            f.seek(0)
            f.write(bytes(data))
            f.flush()
