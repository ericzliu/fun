# -*- coding: utf-8 -*-
"""
Spyder Editor

This is a temporary script file.
"""

import unittest

def listify(num):
    length = len(num)
    l = [0] * length
    for i in range(length):
        l[i] = int(num[length - 1 - i])
    return l

def stringify(lnum):
    lnum.reverse()
    b = 0
    while b < len(lnum) and lnum[b] == 0:
        b += 1

    if b == len(lnum):
        return '0'

    for i in range(b, len(lnum)):
        lnum[i] = str(lnum[i])

    return ''.join(lnum[b:len(lnum)])

def align(num1, num2):
    while len(num1) < len(num2):
        num1.append(0)
    while len(num2) < len(num1):
        num2.append(0)

def add(num1, num2):
    align(num1, num2)
    length1 = len(num1)
    length2 = len(num2)
    res = []
    carry = 0
    for i in range(min(length1, length2)):
        tmp = num1[i] + num2[i] + carry
        res.append(tmp % 10)
        carry = tmp // 10

    for i in range(min(length1, length2), length1):
        tmp = num1[i] + carry
        res.append(tmp % 10)
        carry = tmp // 10

    for i in range(min(length1, length2), length2):
        tmp = num2[i] + carry
        res.append(tmp % 10)
        carry = tmp // 10

    while carry > 0:
        tmp = carry
        res.append(tmp % 10)
        carry = tmp // 10

    return res

def base_case(num1, num2):
    length1 = len(num1)
    res = []
    if length1 == 0:
        res.append(1)
    else:
        tmp = num1[0] * num2[0]
        while tmp > 0:
            res.append(tmp % 10)
            tmp = tmp // 10
    return res

def minus(num1, num2):
    align(num1, num2)
    l = len(num1)
    res = [0] * l
    carry = 0
    for i in range(l):
        tmp = num1[i] + carry
        if tmp < num2[i]:
            tmp += 10
            carry = -1
        else:
            carry = 0
        res[i] = tmp - num2[i]
    return res

def multiply(num1, num2):
    align(num1, num2)
    len1 = len(num1)
    if len1 <= 1:
        return base_case(num1, num2)

    mid1 = len1 // 2
    a = num1[mid1:len1]
    b = num1[0:mid1]
    c = num2[mid1:len1]
    d = num2[0:mid1]
    ac = multiply(a, c)
    bd = multiply(b, d)
    e = add(a, b)
    f = add(c, d)
    ef = multiply(e, f)
    g = minus(ef, ac)
    g = minus(g, bd)
    h = [0] * (mid1 + mid1) + ac
    i = [0] * mid1 + g
    return add(add(h, i), bd)

def mul(num1, num2):
    num1 = listify(num1)
    num2 = listify(num2)
    res = multiply(num1, num2)
    return stringify(res)

class TestMultiply(unittest.TestCase):
    def test_single(self):
        self.assertEqual('2', mul('1', '2'))

    def test_pair(self):
        self.assertEqual(324, int(mul('18', '18')))
        self.assertEqual('9801', mul('99', '99'))

    def test_triple(self):
        self.assertEqual('56088', mul('123', '456'))

if __name__ == '__main__':
    unittest.main()