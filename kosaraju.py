"""
Implementation of kosaraju strongly connected component algorithm
"""
import fileinput

class Component(object):
    """
    A connected component
    """
    def __init__(self, leader):
        self.leader = leader
        self.size = 0

class Node(object):
    """
    Model a node in the graph
    """

    def __init__(self):
        self.node_id = 0
        self.out_links = []
        self.enter_links = []
        self.leader = 0
        self.finish_time = 0
        self.explored = False

    def get_neighbors(self, reverse, is_explored=False):
        """
        Retrieve the neighbor nodes
        """
        neighbors = []
        links = self.enter_links if reverse else self.out_links
        for link in links:
            if reverse:
                if link.tail.explored == is_explored:
                    neighbors.append(link.tail)
            else:
                if link.head.explored == is_explored:
                    neighbors.append(link.head)
        return neighbors

    def __str__(self):
        info = 'id ' + str(self.node_id)
        info += ', leader ' + str(self.leader)
        info += ', finishTime ' + str(self.finish_time)
        return info

class Edge(object):
    """
    Model an directed edge
    """

    def __init__(self, tail, head):
        self.tail = tail
        self.head = head

    def __str__(self):
        return 'edge ' + str(self.tail.node_id) + ', ' + str(self.head.node_id)

class Graph(object):
    """
    Model a directed graph
    """

    def __init__(self):
        self.time = 0
        self.current_component = None
        self.vertice = []
        self.edges = []

    def __str__(self):
        details = []
        for vertex in self.vertice:
            details.append(vertex.__str__())
        for edge in self.edges:
            details.append(edge.__str__())
        return '\n'.join(details)

    def dfs_non_recursive(self, vertex, is_reverse):
        """
        non recursive depth first search
        """
        if not vertex.explored:
            node_stack = []
            vertex.explored = True
            if self.current_component:
                self.current_component.size += 1
            node_stack.append(vertex)

            while len(node_stack) > 0:
                top_node = node_stack[-1]
                neighbor_nodes = top_node.get_neighbors(is_reverse, False)
                if len(neighbor_nodes) == 0:
                    node_stack.pop()
                    self.time += 1
                    top_node.finish_time = self.time
                else:
                    for neighbor in neighbor_nodes:
                        neighbor.explored = True
                        if self.current_component:
                            self.current_component.size += 1
                        node_stack.append(neighbor)


    def sort_nodes(self):
        """
        sort nodes by their finishing time
        """

        for i in range(len(self.vertice)):
            vertex = self.vertice[i]
            loc = vertex.finish_time - 1
            if not loc == i:
                tmp = vertex
                loc = tmp.finish_time - 1
                while not tmp.node_id == self.vertice[loc].node_id:
                    self.vertice[loc], tmp = tmp, self.vertice[loc]
                    loc = tmp.finish_time - 1

    def kosaraju(self):
        """
        Return strongly connected compoment's leaders
        """

        for node in self.vertice:
            if not node.explored:
                self.dfs_non_recursive(node, True)
        self.sort_nodes()
        for node in self.vertice:
            node.explored = False
        large_components = [0] * 5
        for node in self.vertice[::-1]:
            if not node.explored:
                self.current_component = Component(node.node_id)
                self.dfs_non_recursive(node, False)
                if self.current_component.size > min(large_components):
                    ind = large_components.index(min(large_components))
                    large_components[ind] = self.current_component.size
        return large_components

def read_graph(file, num_node):
    """
    create graph from file
    """
    target_graph = Graph()
    target_graph.vertice = [None] * num_node
    for i in range(num_node):
        target_graph.vertice[i] = Node()
        target_graph.vertice[i].node_id = i + 1

    for line in fileinput.input(file):
        tail, head = [int(n) for n in line.split()]
        tail_node = target_graph.vertice[tail - 1]
        head_node = target_graph.vertice[head - 1]
        edge = Edge(tail_node, head_node)
        tail_node.out_links.append(edge)
        head_node.enter_links.append(edge)
        target_graph.edges.append(edge)
    return target_graph

if __name__ == '__main__':
    NEW_GRAPH = read_graph('SCC.txt', 875714)
    COMPONENTS = NEW_GRAPH.kosaraju()
    print(','.join([str(size) for size in sorted(COMPONENTS, reverse=True)]))
