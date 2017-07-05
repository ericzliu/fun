# -*- coding: utf-8 -*-
"""
Created on Wed Jul  5 17:51:40 2017

@author: zliu
"""
from pprint import pprint

def merge(li1, begin1, end1, li2, begin2, end2, li, begin):
    i = begin1
    j = begin2
    k = begin
    while i < end1 and j < end2:
        if li1[i] <= li2[j]:
            li[k] = li1[i]
            i += 1
        else:
            li[k] = li2[j]
            j += 1
        k += 1
    while i < end1:
        li[k] = li1[i]
        i += 1
        k += 1
    while j < end2:
        li[k] = li2[j]
        j += 1
        k += 1

def _trier(numbers, begin, end):
    length = end - begin
    if length <= 1:
        return
    half = length // 2
    copie = numbers[begin:end]
    _trier(copie, 0, half)
    _trier(copie, half, length)
    merge(copie, 0, half, copie, half, length, numbers, begin)

def trier(numbers):
    _trier(numbers, 0, len(numbers))
    
if __name__ == "__main__":
    numbers = [1]
    trier(numbers)
    pprint(numbers)
    
    numbers = [2, 1]
    trier(numbers)
    pprint(numbers)
    
    numbers = [5, 2, 4, 1, 3]
    trier(numbers)
    pprint(numbers)    
    
    