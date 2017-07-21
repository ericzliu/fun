# -*- coding: utf-8 -*-
"""
Created on Fri Jul 21 14:20:15 2017

@author: zliu
"""

import math

def base_case(A, order):
    if len(A) <= order:
        raise AssertionError('index out of range!')
    return sorted(A)[order]

def median_ind(A):
    return (len(A) - 1) // 2

def partition(A, begin, end, pivot):
    i = -1
    for j in range(end - begin):
        if A[begin + j] < pivot:
            A[begin + i + 1], A[begin + j] = A[begin + j], A[begin + i + 1]
        elif A[begin + j] > pivot:
            pass
    A[begin + i + 1] = pivot
    return begin + i + 1

def select(A, begin, end, order):
    in_size = len(A)
    if in_size <= 5:
        return base_case(A[begin: end], order)
    c = [0] * math.ceil(in_size / 5)
    for ind in range(len(c)):
        sub_begin = ind * 5
        sub_end = min(sub_begin + 5, end)
        sub = A[sub_begin: sub_end]
        c[ind] = base_case(sub, median_ind(sub))
    pivot = select(c, 0, len(c), median_ind(c))
    j = partition(A, begin, end, pivot)
    if j == order:
        return pivot
    elif j < order:
        return select(A, j+1, end, order - j - 1)
    else:
        return select(A, begin, j, order)
