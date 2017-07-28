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
        self._nodes = []
        self._arcs = set()
        self._edges = set()

    def _get_node(self, nid):
        for node in self._nodes:
            if node.id == nid:
                return node
        new_node = Node()
        new_node.id = nid
        new_node.node_set.add(nid)
        self._nodes.append(new_node)
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
                self._edges.add(edge)
                self._arcs.add(arc)
                node1.arcs.add(arc)
                node2.arcs.add(arc)

    def read_file(self, name):
        with open(name) as file:
            for line in file:
                node_list = [int(x) for x in line.split()]
                self._read_single(node_list)

    def min_cut(self):
        while len(self._nodes) > 2 and len(self._arcs) > 0:
            self._contraction()
        if len(self._nodes) == 2:
            return self.count_cut()
        return 0

    def _contraction(self):
        ind = random.randint(0, len(self._arcs) - 1)
        arc_to_shrink = self._arcs[ind]
        node_to_keep = arc_to_shrink.first
        node_to_delete = arc_to_shrink.second
        if node_to_keep.id > node_to_delete.id:
            node_to_keep, node_to_delete = node_to_delete, node_to_keep
        arcs_to_delete = []
        self._update_arcs(node_to_keep, node_to_delete, arcs_to_delete)
        node_to_keep.node_set.update(node_to_delete.node_set)
        self._arcs.difference_update(arcs_to_delete)
        self._nodes.remove(node_to_delete)

    def _update_arcs(self, first, second, arcs_to_delete):
        for arc in second.arcs:
            if arc.first.id == second.id:
                arc.first = first
            elif arc.second.id == second.id:
                arc.second = first
            if arc.first.id == arc.second.id:
                arcs_to_delete.append(arc)

    def _count_cut(self):
        a = self._nodes[0].node_set
        b = self._nodes[1].node_set
        num_cut = 0
        for n1 in a:
            for n2 in b:
                e = (n1, n2) if n1 < n2 else (n2, n1)
                if e in self._edges:
                    num_cut += 1
        return num_cut

if __name__=='__main__':
    pass