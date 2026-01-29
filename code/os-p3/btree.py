from typing import Optional

import common
from node import Node

class BTree:
    def __init__(self, filename: str, header) -> None:
        self.filename = filename
        self.header = header

    def insert(self, key: int, value: int) -> None:
        existing_node_id, existing_idx = self._search_for_node(key)

        if existing_node_id is not None:
            node = Node.read(self.filename, existing_node_id)
            node.values[existing_idx] = value
            node.write()

            return

        if self.header.root_id == 0:
            root = Node(self.filename, self.header.next_block_id)
            self.header.next_block_id += 1
            root.keys[0] = key
            root.values[0] = value
            root.count = 1

            root.write()
            self.header.root_id = root.block_id
            self.header.write()

            return

        root = Node.read(self.filename, self.header.root_id)
        if root.full():
            new_root = Node(self.filename, self.header.next_block_id)
            self.header.next_block_id += 1

            new_root.children[0] = root.block_id
            new_root.write()

            self._split_tree(new_root, 0)

            self.header.root_id = new_root.block_id
            self.header.write()
            root = new_root

        self._insert_general(root.block_id, key, value)

    def _split_tree(self, parent, child_idx: int) -> None:
        child_id = parent.children[child_idx]
        child = Node.read(self.filename, child_id)

        new_node = Node(self.filename, self.header.next_block_id, parent.block_id)
        self.header.next_block_id += 1

        mid = common.MIN_DEGREE - 1

        new_node.count = common.MIN_DEGREE - 1
        for i in range(common.MIN_DEGREE - 1):
            new_node.keys[i] = child.keys[mid + 1 + i]
            new_node.values[i] = child.values[mid + 1 + i]

        if not child.leaf():
            for i in range(common.MIN_DEGREE):
                new_node.children[i] = child.children[mid + 1 + i]

                if new_node.children[i] != 0:
                    child_node = Node.read(self.filename, new_node.children[i])
                    child_node.parent_id = new_node.block_id
                    child_node.write()

        child.count = common.MIN_DEGREE - 1

        for i in range(mid + 1, common.MAX_KEYS):
            child.keys[i] = 0
            child.values[i] = 0

        for i in range(mid + 1, common.MAX_CHILDREN):
            child.children[i] = 0

        for i in range(parent.count, child_idx, -1):
            parent.keys[i] = parent.keys[i - 1]
            parent.values[i] = parent.values[i - 1]

        for i in range(parent.count + 1, child_idx + 1, -1):
            parent.children[i] = parent.children[i - 1]

        parent.keys[child_idx] = child.keys[mid]
        parent.values[child_idx] = child.values[mid]
        parent.children[child_idx + 1] = new_node.block_id
        parent.count += 1

        child.keys[mid] = 0
        child.values[mid] = 0

        child.write()
        new_node.write()
        parent.write()
        self.header.write()

    def _insert_general(self, node_id, key: int, value: int) -> None:
        node = Node.read(self.filename, node_id)

        if node.leaf():
            i = node.count - 1
            while i >= 0 and key < node.keys[i]:
                node.keys[i + 1] = node.keys[i]
                node.values[i + 1] = node.values[i]
                i -= 1

            node.keys[i + 1] = key
            node.values[i + 1] = value
            node.count += 1
            node.write()
        else:
            i = node.count - 1
            while i >= 0 and key < node.keys[i]:
                i -= 1
            i += 1

            child_id = node.children[i]
            child = Node.read(self.filename, child_id)
            if child.full():
                self._split_tree(node, i)
                node = Node.read(self.filename, node_id)
                if key > node.keys[i]:
                    child_id = node.children[i+1]
            
            self._insert_general(child_id, key, value)

    def search(self, key: int) -> Optional[tuple[int, int]]:
        node_id, idx = self._search_for_node(key)

        if node_id is None:
            return None

        node = Node.read(self.filename, node_id)

        return (node.keys[idx], node.values[idx])

    def _search_for_node(self, key: int) -> Optional[tuple[int, int]]:
        if self.header.root_id == 0:
            return None, None

        curr_id = self.header.root_id
        while curr_id != 0:
            node = Node.read(self.filename, curr_id)

            if not node:
                return None, None

            i = 0
            while i < node.count and key > node.keys[i]:
                i += 1

            if i < node.count and key == node.keys[i]:
                return curr_id, i

            if node.leaf():
                return None, None

            curr_id = node.children[i]

        return None, None

    def traverse(self) -> list[tuple[int, int]]:
        results = []

        self._do_traverse(self.header.root_id, results)

        return results

    def _do_traverse(self, node_id, results) -> None:
        if node_id == 0:
            return None

        node = Node.read(self.filename, node_id)

        if not node:
            return None

        for i in range(node.count):
            if node.children[i] != 0:
                self._do_traverse(node.children[i], results)

            results.append((node.keys[i], node.values[i]))

        if node.children[node.count] != 0:
            self._do_traverse(node.children[node.count], results)
