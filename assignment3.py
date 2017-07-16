# -*- coding: utf-8 -*-
"""
Created on Sat Jul 15 00:26:08 2017

@author: DIAMIAO
"""

import unittest

def partition(A, l, r):
    p = A[l]
    i = l + 1
    for j in range(l+1, r+1):
        if A[j] < p:
            A[j], A[i] = A[i], A[j]
            i += 1
    A[l], A[i - 1] = A[i - 1], A[l]
    return i-1

def _select_first(A, l, r):
    pass

def _select_last(A, l, r):
    A[l], A[r] = A[r], A[l]

def _median_(A, l, r):
    m = l + (r - l) // 2
    _, pivot, _ = sorted([A[l], A[r], A[m]])
    if pivot == A[m]:
        A[m], A[l] = A[l], A[m]
    elif pivot == A[r]:
        A[l], A[r] = A[r], A[l]

def _base_case(numbers, l, r):
    return r - l + 1 < 2

def _quick_sort(numbers, l, r, choose_pivot):
    comp_num = 0
    if r - l + 1 < 2:
        return max(0, r - l)
    choose_pivot(numbers, l, r)
    i = partition(numbers, l, r)
    comp_num = r - l
    if i > l:
        comp_num += _quick_sort(numbers, l, i - 1, choose_pivot)
    if r > i:
        comp_num += _quick_sort(numbers, i + 1, r, choose_pivot)
    return comp_num

def quick_sort(numbers, choose_pivot):
    return _quick_sort(numbers, 0, len(numbers) - 1, choose_pivot)

class TestAssignment3(unittest.TestCase):
    def test_simple(self):
        nums = [1, 2, 3]
        comp_num = quick_sort(nums, _select_first)
        self.assertEqual([1,2,3], nums)
        self.assertEqual(3, comp_num) # when 1 is pivot, compare 2. when 2 is pivot, compare 1. total = 3.

    def test_median(self):
        nums = [8, 2, 4, 5, 7, 1]
        comp = quick_sort(nums, _median_)
        self.assertEqual([1, 2, 4, 5, 7, 8], nums)
        self.assertEqual(8, comp)

if __name__=='__main__':
#    unittest.main()

    numbers = []
    with open('QuickSort.txt', 'r') as f:
        for line in f:
            numbers.append(int(line))
    num1 = numbers[:]
    comp1 = quick_sort(num1, _select_first)
    num2 = numbers[:]
    comp2 = quick_sort(num2, _select_last)
    num3 = numbers[:]
    comp3 = quick_sort(num3, _median_)
    print(comp1)
    print(comp2)
    print(comp3)
