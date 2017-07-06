# -*- coding: utf-8 -*-
"""
Created on Thu Jul  6 11:42:07 2017

@author: zliu
"""

import unittest

def _merge_and_count(num1, begin1, end1, num2, begin2, end2, num, begin):
    i = begin1
    j = begin2
    k = begin
    inversion = 0
    while i < end1 and j < end2:
        if num1[i] <= num2[j]:
            num[k] = num1[i]
            i += 1
        else:
            num[k] = num2[j]
            j += 1
            inversion += end1 - i
        k += 1
    while i < end1:
        num[k] = num1[i]
        i += 1
        k += 1
    while j < end2:
        num[k] = num2[j]
        j += 1
        k += 1
    return inversion

def _sort_and_count(numbers, begin, end):
    length = end - begin
    if length <= 1:
        return 0
    mid = length // 2
    copie = numbers[begin:end]
    left = _sort_and_count(copie, 0, mid)
    right = _sort_and_count(copie, mid, length)
    cross = _merge_and_count(copie, 0, mid, copie, mid, length, numbers, begin)
    return left + right + cross


def count_inversion(numbers):
    return _sort_and_count(numbers, 0, len(numbers))

class TestCountInversion(unittest.TestCase):
    def test_empty(self):
        numbers = []
        self.assertEqual(0, count_inversion(numbers))

    def test_single(self):
        numbers = [1, ]
        self.assertEqual(0, count_inversion(numbers))

    def test_pair(self):
        numbers = [1, 2]
        self.assertEqual(0, count_inversion(numbers))

        numbers = [3, 2]
        self.assertEqual(1, count_inversion(numbers))

    def test_reverse_sorted(self):
        numbers = [6, 5, 4, 3, 2, 1]
        self.assertEqual(15, count_inversion(numbers))

if __name__=='__main__':
    unittest.main()