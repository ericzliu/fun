# -*- coding: utf-8 -*-
"""
Created on Thu Jul 27 23:10:23 2017

@author: DIAMIAO
"""
import random

class Node(object):
    def __init__(self):
        self.id = 0
        self.arcs = []
        self.node_set = set()

class Arc(object):
    def __init__(self, node1, node2):
        self.first = node1
        self.second = node2

class MinCut(object):
    def __init__(self):
        self.nodes = []
        self.arcs = []
        self.edges = set()

    def _get_node(self, nid):
        for node in self.nodes:
            if node.id == nid:
                return node
        new_node = Node()
        new_node.id = nid
        new_node.node_set.add(nid)
        self.nodes.append(new_node)
        return new_node

    def _read_single(self, node_list):
        if len(node_list) > 1:
            nid1 = node_list[0]
            node1 = self._get_node(nid1)
            for nid2 in node_list:
                if nid2 == nid1:
                    continue
                node2 = self._get_node(nid2)
                arc = Arc(node1, node2)
                edge = (nid1, nid2) if nid1 < nid2 else (nid2, nid1)
                self.edges.add(edge)
                self.arcs.append(arc)
                node1.arcs.append(arc)
                node2.arcs.append(arc)

    def read_file(self, name):
        with open(name) as file:
            for line in file:
                node_list = [int(x) for x in line.split()]
                self._read_single(node_list)

    def min_cut(self):
        while len(self.nodes) > 2:
            pass
        if len(self.nodes) == 2:
            return self.count_cut()
        return 0

    def _fuse(self):
        random.randint(0, len(self.arcs) - 1)

    def _count_cut(self):
        a = self.nodes[0].node_set
        b = self.nodes[1].node_set
        num_cut = 0
        for n1 in a:
            for n2 in b:
                e = (n1, n2) if n1 < n2 else (n2, n1)
                if e in self.edges:
                    num_cut += 1
        return num_cut

if __name__=='__main__':
    pass