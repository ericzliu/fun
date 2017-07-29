# -*- coding: utf-8 -*-
"""
Created on Thu Jul 27 23:10:23 2017

@author: DIAMIAO
"""
import random
import unittest

class Node(object):
    def __init__(self):
        self.id = 0
        self.arcs = []
        self.node_set = set()
    def __str__(self):
        return 'Node (' + str(self.id) + ')'

class Arc(object):
    def __init__(self, node1, node2):
        self.first = node1
        self.second = node2
    def __str__(self):
        return 'Arc (' + str(self.first.id) + ', ' + str(self.second.id) + ')'

class Random(object):
    def __init__(self, seed):
        random.seed(seed)

    def randint(self, first, last):
        return random.randint(first, last)

class AlwaysFirst(object):
    def __init__(self):
        pass

    def randint(self, first, last):
        return first

class AlwaysLast(object):
    def __init__(self):
        pass

    def randint(self, first, last):
        return last

class MinCut(object):
    def __init__(self, rand):
        self._nodes = []
        self._arcs = []
        self._edges = set()
        self._rand = rand

    def _get_node(self, nid):
        for node in self._nodes:
            if node.id == nid:
                return node
        new_node = Node()
        new_node.id = nid
        new_node.node_set.add(nid)
        self._nodes.append(new_node)
        return new_node

    def _get_arc(self, node1, node2):
        for arc in self._arcs:
            if arc.first.id == node1.id and arc.second.id == node2.id:
                return arc
        arc = Arc(node1, node2)
        self._arcs.append(arc)
        return arc

    def _read_single(self, node_list):
        if len(node_list) > 1:
            nid = node_list[0]
            node = self._get_node(nid)
            for nid2 in node_list:
                if nid2 == nid:
                    continue
                node2 = self._get_node(nid2)
                nid1 = nid
                node1 = node
                if nid1 > nid2:
                    node1, node2 = node2, node1
                    nid1, nid2 = nid2, nid1
                arc = self._get_arc(node1, node2)
                node.arcs.append(arc)
                self._edges.add((nid1, nid2))

    def read_file(self, name):
        with open(name) as file:
            for line in file:
                node_list = [int(x) for x in line.split()]
                self._read_single(node_list)

    def min_cut(self):
        #self._print_graph()
        while len(self._nodes) > 2 and len(self._arcs) > 0:
            self._contraction()
        if len(self._nodes) == 2:
            return self._count_cut()
        return 0

    def _contraction(self):
        ind = self._rand.randint(0, len(self._arcs) - 1)
        arc_to_shrink = self._arcs[ind]
        node_to_keep = arc_to_shrink.first
        node_to_delete = arc_to_shrink.second
        if node_to_keep.id > node_to_delete.id:
            node_to_keep, node_to_delete = node_to_delete, node_to_keep
        self._update_graph(node_to_keep, node_to_delete)
        #self._print_graph()

    @staticmethod
    def _remove_self_arcs(_arcs):
        j = len(_arcs) - 1
        while j >= 0:
            arc = _arcs[j]
            if arc.first.id == arc.second.id:
                j -= 1
            else:
                break

        i = 0
        while i < j:
            arc = _arcs[i]
            if arc.first.id == arc.second.id:
                _arcs[i], _arcs[j] = _arcs[j], _arcs[i]
                j -= 1
            else:
                i += 1

        while len(_arcs) > 0:
            last = _arcs[len(_arcs) - 1]
            if last.first.id == last.second.id:
                _arcs.pop()
            else:
                break

    def _update_graph(self, first, second):

        # Replace the second node by first node in edges
        for arc in second.arcs:
            if arc.first.id == second.id:
                arc.first = first
            if arc.second.id == second.id:
                arc.second = first
        # edges pointed by second node now should be pointed by first point
        # invariant: each node contains a list of all the in and out edges
        first.arcs.extend(second.arcs)

        MinCut._remove_self_arcs(first.arcs)
        MinCut._remove_self_arcs(self._arcs)
        first.node_set.update(second.node_set)
        self._nodes.remove(second)

    def _count_cut(self):
        return len(self._arcs)

    def _print_graph(self):
        print('==============================================================')
        for node in self._nodes:
            print(node)
        for arc in self._arcs:
            print(arc)

class TestMinCut(unittest.TestCase):
    def test_complete_4_nodes(self):
        rand = AlwaysFirst()
        mincut = MinCut(rand)
        mincut.read_file('complete_4.txt')
        self.assertEqual(4, mincut.min_cut())

        rand = AlwaysLast()
        mincut = MinCut(rand)
        mincut.read_file('complete_4.txt')
        self.assertEqual(3, mincut.min_cut())

    def test_link_complete(self):
        rand = AlwaysFirst()
        mincut = MinCut(rand)
        mincut.read_file('link_complete_4.txt')
        self.assertEqual(4, mincut.min_cut())

    def test_link_complete2(self):
        rand = AlwaysLast()
        mincut = MinCut(rand)
        mincut.read_file('link_complete_4.txt')
        self.assertEqual(3, mincut.min_cut())

    def test_link_complete_ran(self):
        # seeds = [178303, 385231, 881411, 152779, 879170, 291781, 665125, 343977]
        rand = Random(291781)
        mincut = MinCut(rand)
        mincut.read_file('link_complete_4.txt')
        self.assertEqual(2, mincut.min_cut())


if __name__=='__main__':
#    unittest.main()
    seeds = [random.randint(0, 99999) for i in range(200)]
    res = []
    for seed in seeds:
        rand = Random(seed)
        mincut = MinCut(rand)
        mincut.read_file('kargerMinCut.txt')
        res.append(mincut.min_cut())
    print(min(res))
