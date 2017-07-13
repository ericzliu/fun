# -*- coding: utf-8
"""
Created on Thu Jul 13 22:28:19 2017

@author: DIAMIAO
"""

import unittest

def partition(numbers, l, r):
    length = r - l
    if length < 2:
        return
    pivot = numbers[l]
    i, j, k = l, l, l
    # i last index for numbers less than pivot
    # k last index for numbers equals to pivot
    # j last index for numbers greater than pivot
    for q in range(l + 1, r):
        num = numbers[q]
        if num > pivot:
            j += 1
        elif num == pivot:
            if j > k:
                numbers[k + 1], numbers[q] = numbers[q], numbers[k + 1]
            k += 1
            j += 1
        else:
            if k > i:
                numbers[i + 1], numbers[q] = numbers[q], numbers[i + 1]
            if j > k:
                numbers[k + 1], numbers[q] = numbers[q], numbers[k + 1]
            i += 1
            k += 1
            j += 1
    if i > l:
        numbers[i], numbers[l] = numbers[l], numbers[i]
    return i, k, j

def _quick_sort(numbers, l, r):
    if r - l < 2:
        return
    i, k, j = partition(numbers, l, r)
    _quick_sort(numbers, l, i)
    _quick_sort(numbers, k + 1, r)

def quick_sort(numbers):
    _quick_sort(numbers, 0, len(numbers))

class TestPartition(unittest.TestCase):
    def test_simple_sorted(self):
        nums = [1, 2]
        i, k, j = partition(nums, 0, len(nums))
        self.assertEqual(i, 0)
        self.assertEqual(k, 0)
        self.assertEqual(j, 1)
        self.assertEqual([1, 2], nums)

    def test_simple_rev(self):
        nums = [2, 1]
        i, k, j = partition(nums, 0, len(nums))
        self.assertEqual(i, 1)
        self.assertEqual(k, 1)
        self.assertEqual(j, 1)
        self.assertEqual([1, 2], nums)

    def test_equal(self):
        nums = [2, 2]
        i, k, j = partition(nums, 0, len(nums))
        self.assertEqual(i, 0)
        self.assertEqual(k, 1)
        self.assertEqual(j, 1)
        self.assertEqual([2, 2], nums)

    def test_complex(self):
        nums = [2, 1, 3, 4]
        i, k, j = partition(nums, 0, len(nums))
        self.assertEqual(i, 1)
        self.assertEqual(j, 3)
        self.assertEqual(k, 1)
        self.assertEqual([1, 2, 3, 4], nums)

    def test_complex_equal(self):
        nums = [2, 1, 2, 3, 4]
        i, k, j = partition(nums, 0, len(nums))
        self.assertEqual(i, 1)
        self.assertEqual(j, 4)
        self.assertEqual(k, 2)
        self.assertEqual([1, 2, 2, 3, 4], nums)

    def test_complex_middle(self):
        nums = [0, 2, 1, 2, 3, 4, 5]
        i, k, j = partition(nums, 1, len(nums) - 1)
        self.assertEqual(i, 2)
        self.assertEqual(j, 5)
        self.assertEqual(k, 3)
        self.assertEqual([0, 1, 2, 2, 3, 4, 5], nums)

    def test_sort(self):
        nums = [6, 4, 2, 1, 3, 5, 7]
        quick_sort(nums)
        self.assertEqual([1, 2, 3, 4, 5, 6, 7], nums)

if __name__ == '__main__':
    unittest.main()