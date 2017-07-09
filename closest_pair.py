# -*- coding: utf-8 -*-
"""
"""

import unittest
import math

class Points(object):
    def __init__(self):
        self.x = []
        self.y = []

    def add(self, x, y):
        self.x.append(x)
        self.y.append(y)

def split(points, P, Q):
    length = len(P)
    LP = P[0:length//2]
    RP = P[length//2:]
    sp = points.x[RP[0]]
    LQ = []
    RQ = []
    for i in Q:
        if points.x[i] < sp:
            LQ.append(i)
        else:
            RQ.append(i)
    return LP, LQ, RP, RQ

def dist(points, i, j):
    xi = points.x[i]
    yi = points.y[i]
    xj = points.x[j]
    yj = points.y[j]
    return math.sqrt(math.pow(xj - xi, 2) + math.pow(yj - yi, 2))

def base_case(points, P):
    length = len(P)
    if length < 2:
        raise Exception("Number of points doesn't suffice.")
    min_dist = None
    min_i = None
    min_j = None
    for i in range(length - 1):
        for j in range(1, length - i):
            m = P[i]
            n = P[i + j]
            d = dist(points, m, n)
            if not min_dist or d < min_dist:
                min_dist = d
                min_i = m
                min_j = n
    return min_i, min_j

def closest_split_pair(points, P, Q, d):
    S = []
    mid = points.x[P[len(P)//2]]
    l = mid - d
    h = mid + d
    for p in Q:
        if l <= points.x[p] and points.x[p] <= h:
            S.append(p)
    if len(S) < 2:
        return None, None
    min_dist = None
    min_i = None
    min_j = None
    for i in range(len(S) - 1):
        for j in range(1, min(7, len(S) - i - 1) + 1):
            m = S[i]
            n = S[i + j]
            d = dist(points, m, n)
            if not min_dist or d < min_dist:
                min_dist = d
                min_i = m
                min_j = n
    return min_i, min_j

def closest_pair(points, P, Q):
    length = len(P)
    if length < 4:
        return base_case(points, P)
    LP, LQ, RP, RQ = split(points, P, Q)
    l1, l2 = closest_pair(points, LP, LQ)
    r1, r2 = closest_pair(points, RP, RQ)
    d1 = dist(points, l1, l2)
    d2 = dist(points, r1, r2)
    d = min(d1, d2)
    s1, s2 = closest_split_pair(points, P, Q, d)
    if s1 is None and s2 is None:
        if d1 <= d2:
            return l1, l2
        else:
            return r1, r2
    else:
        d3 = dist(points, s1, s2)
        if d3 <= d:
            return s1, s2
        elif d1 <= d2:
            return l1, l2
        else:
            return r1, r2

def sort_points(points):
    P = [i[0] for i in sorted(enumerate(points.x), key=lambda x:x[1])]
    Q = [i[0] for i in sorted(enumerate(points.y), key=lambda x:x[1])]
    return P, Q

class TestClosestPair(unittest.TestCase):
    def test_sort_points(self):
        points = Points()
        points.add(3, 0)
        points.add(2, 2)
        points.add(7, 1)
        points.add(5, 3)
        P, Q = sort_points(points)
        self.assertEqual([1, 0, 3, 2], P)
        self.assertEqual([0, 2, 1, 3], Q)

    def test_split(self):
        points = Points()
        points.add(3, 0)
        points.add(2, 2)
        points.add(7, 1)
        points.add(5, 3)
        P, Q = sort_points(points)
        LP, LQ, RP, RQ = split(points, P, Q)
        self.assertEqual([1, 0], LP)
        self.assertEqual([0, 1], LQ)
        self.assertEqual([3, 2], RP)
        self.assertEqual([2, 3], RQ)

    def test_base_case(self):
        points = Points()
        points.add(3, 0)
        points.add(2, 2)
        P, Q = sort_points(points)
        ind1, ind2 = closest_pair(points, P, Q)
        self.assertEqual([0, 1], sorted([ind1, ind2]))

    def test_left(self):
        points = Points()
        points.add(3, 0)
        points.add(2, 2)
        points.add(7, 1)
        points.add(5, 3)
        P, Q = sort_points(points)
        ind1, ind2 = closest_pair(points, P, Q)
        self.assertEqual([0, 1], sorted([ind1, ind2]))

    def test_right(self):
        points = Points()
        points.add(3, 0)
        points.add(1, 2)
        points.add(6, 1)
        points.add(5, 3)
        P, Q = sort_points(points)
        ind1, ind2 = closest_pair(points, P, Q)
        self.assertEqual([2, 3], sorted([ind1, ind2]))

    def test_closest_split(self):
        points = Points()
        points.add(4, 0)
        points.add(1, 2)
        points.add(8, 1)
        points.add(5, 3)
        P, Q = sort_points(points)
        ind1, ind2 = closest_pair(points, P, Q)
        self.assertEqual([0, 3], sorted([ind1, ind2]))

if __name__ == '__main__':
    unittest.main()