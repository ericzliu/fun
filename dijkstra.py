"""
Dijkstra single source shortest path algorithm
Use heap to make the complexity O(m log n)
"""

class Node(object):
    """
    The node
    """
    def __init__(self, node_id):
        self.node_id = node_id
        self.edges = []
        self.heap_ind = -1
        self.heap_key = float('inf')
        self.explored = False
        self.processed = False

    def reset(self):
        self.heap_ind = -1
        self.heap_key = float('inf')
        self.explored = False
        self.processed = False

class Edge(object):
    """
    An edge
    """
    def __init__(self, tail, head, length):
        self.tail = tail
        self.head = head
        self.length = length

class Heap(object):
    """
    A node heap
    """
    def __init__(self):
        self.underlying = []
        self.size = 0

    def _increment_size(self):
        if self.size < len(self.underlying):
            return
        self.size += 1
        while len(self.underlying) < self.size:
            self.underlying.append(None)

    def _parent_ind(self, child_ind):
        if child_ind > 0:
            return (child_ind - 1) // 2
        return None

    def _sift_up(self, node_ind):
        parent_ind = self._parent_ind(node_ind)
        if parent_ind:
            u = self.underlying
            if u[parent_ind].heap_key > u[node_ind].heap_key:
                u[parent_ind], u[node_ind] = u[node_ind], u[parent_ind]
                u[parent_ind].heap_ind = parent_ind
                u[node_ind].heap_ind = node_ind
                self._sift_up(parent_ind)

    def insert(self, node):
        """
        insert node to the heap
        """
        self._increment_size()
        self.underlying[self.size - 1] = node
        node.heap_ind = self.size - 1
        self._sift_up(self.size - 1)

    def _left_ind(self, parent_ind):
        ind = 2 * parent_ind + 1
        if ind < self.size:
            return ind
        return None

    def _right_ind(self, parent_ind):
        ind = 2 * parent_ind + 2
        if ind < self.size:
            return ind
        return None

    def _sift_down(self, parent_ind):
        left_ind = self._left_ind(parent_ind)
        right_ind = self._right_ind(parent_ind)
        min_ind = parent_ind
        u = self.underlying
        if left_ind and u[left_ind].heap_key < u[min_ind].heap_key:
            min_ind = left_ind
        if right_ind and u[right_ind].heap_key < u[min_ind].heap_key:
            min_ind = right_ind
        if not min_ind == parent_ind:
            u[parent_ind], u[min_ind] = u[min_ind], u[parent_ind]
            u[parent_ind].heap_ind = parent_ind
            u[min_ind].heap_ind = min_ind
            self._sift_down(min_ind)

    def extract(self):
        """
        Extract the node with smallest heap key
        """
        if self.size == 0:
            return None
        u = self.underlying
        min_node = u[0]
        min_node.heap_ind = -1
        u[0] = u[self.size - 1]
        u[0].heap_ind = 0
        u[self.size - 1] = None
        self.size -= 1
        self._sift_down(0)
        return min_node

    def delete(self, heap_ind):
        """
        delete element at heap_ind
        """
        if heap_ind >= self.size:
            return None
        u = self.underlying
        node = u[heap_ind]
        node.heap_ind = -1
        u[heap_ind] = u[self.size - 1]
        u[heap_ind].heap_ind = heap_ind
        u[self.size - 1] = None
        self.size -= 1

        parent_ind = self._parent_ind(heap_ind)
        if parent_ind and u[heap_ind].heap_key < u[parent_ind].heap_key:
            self._sift_up(heap_ind)
        else:
            self._sift_down(heap_ind)

class Dijkstra(object):
    def __init__(self):
        self.nodes = []
        self.edges = []

    def run(self, source_node):
        node_heap = Heap()
        source_node.heap_key = 0
        source_node.explored = True
        node_heap.insert(source_node)
        while node_heap.size > 0:
            node = node_heap.extract()
            node.processed = True
            nodes_to_delete = []
            nodes_to_add = []
            for edge in node.edges:
                neighbor = edge.tail
                if neighbor.heap_ind != -1:
                    nodes_to_delete.append(neighbor)
                if not neighbor.processed:
                    neighbor.heap_key = min(neighbor.heap_key, node.heap_key + edge.length)
                    nodes_to_add.append(neighbor)
            for node_to_delete in nodes_to_delete:
                node_heap.delete(node_to_delete.heap_ind)
            for node_to_add in nodes_to_add:
                node_to_add.explored = True
                node_heap.insert(node_to_add)

    def reset(self):
        for node in self.nodes:
            node.reset()

    def result(self):
        id_dist = []
        for node in self.nodes:
            if node.processed:
                id_dist.append((node.node_id, node.heap_key))
        return id_dist
