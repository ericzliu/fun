# -*- coding: utf-8 -*-
class Node(object):
    def __init__(self):
        self.id = 0
        self.outLinks = []
        self.enterLinks = []
        self.leader = 0
        self.finishTime = 0
        self.explored = False

    def get_neighbors(self, reverse):
        neighbors = []
        links = self.enterLinks if reverse else self.outLinks
        for link in links:
            if reverse:
                neighbors.append(link.tail)
            else:
                neighbors.append(link.head)
        return neighbors

    def get_neighbor_ids(self, reverse):
        neighbors = self.get_neighbors(reverse)
        return [n.id for n in neighbors]

    def __str__(self):
        info = 'id ' + str(self.id) + ', leader ' + str(self.leader) + ', finishTime ' + str(self.finishTime)
        return info

class Edge(object):
    def __init__(self, tail, head):
        self.tail = tail
        self.head = head

    def __str__(self):
        return 'edge ' + str(self.tail.id) + ', ' + str(self.head.id)

class Graph(object):

    def __init__(self):
        self.t = 0;
        self.s = 0;
        self.vertice = []
        self.edges = []

    def __str__(self):
        details = []
        for vertex in self.vertice:
            details.append(vertex.__str__())
        for edge in self.edges:
            details.append(edge.__str__())
        return '\n'.join(details)

    def dfs(self, v, isReverse):
        if not v.explored:
            v.explored = True
            v.leader = self.s
            neighbor_nodes = v.get_neighbors(isReverse)
            for neighbor in neighbor_nodes:
                if not neighbor.explored:
                    self.dfs(neighbor, isReverse)
            self.t += 1
            v.finishTime = self.t

    def sort_nodes(self):
        for i in range(len(self.vertice)):
            vertex = self.vertice[i]
            fv = vertex.finishTime
            if not fv == (i + 1):
                tmp = vertex
                while not tmp.finishTime == self.vertice[tmp.finishTime - 1].finishTime:
                    loc = tmp.finishTime - 1
                    self.vertice[loc], tmp = tmp, self.vertice[loc]

    def kosaraju(self):
        for node in self.vertice:
            if not node.explored:
                self.dfs(node, True)
        self.sort_nodes()
        for node in self.vertice:
            node.explored = False
        leaders = []
        for node in self.vertice[::-1]:
            if not node.explored:
                self.s = node.id
                leaders.append(self.s)
                self.dfs(node, False)
        return leaders

def read_graph(file, num_node):
    graph = Graph()
    graph.vertice = [None] * num_node
    for i in range(num_node):
        graph.vertice[i] = Node()
        graph.vertice[i].id = i + 1
    with open(file) as input:
        for line in input:
            tail, head = [int(n) for n in line.split()]
            tail_node = graph.vertice[tail - 1]
            head_node = graph.vertice[head - 1]
            edge = Edge(tail_node, head_node)
            tail_node.outLinks.append(edge)
            head_node.enterLinks.append(edge)
            graph.edges.append(edge)
    return graph

if __name__ == '__main__':
    graph = read_graph('test.txt', 9)
    components = graph.kosaraju()
    print(components)
