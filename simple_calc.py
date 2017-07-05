# -*- coding: utf-8 -*-
"""
Created on Wed Jul  5 18:15:53 2017

@author: zliu
"""

def is_number(c):
    return c <= "9" and c >= "0"

def is_sym(c):
    return c in ["+", "-", "*", "/"]

def transform(n1, s, n2):
    num1 = int(n1)
    num2 = int(n2)
    op = {
    "+": lambda x, y: x + y,
    "-": lambda x, y: x - y,
    "*": lambda x, y: x * y,
    "/": lambda x, y: x / y
    }
    return str(op[s](num1, num2))

def _calc(exp, begin, end):
    li = []
    ls = []
    i = begin
    while i < end:
        t = exp[i]
        if is_number(t):
            li.append(t)
            if len(ls) > 0:
                s = ls.pop()
                n2 = li.pop()
                n1 = li.pop()
                li.append(transform(n1, s, n2))
            i += 1
        elif is_sym(t):
            ls.append(t)
            i += 1
        elif t == "(":
            j = i + 1
            o = 1
            while j < end:
                t = exp[j]
                if t == "(":
                    o += 1
                elif t == ")":
                    o -= 1
                if o == 0:
                    li.append(_calc(exp, i + 1, j))
                    break
                j += 1
            i = j + 1
    return li[0]

def calc(exp):
    return _calc(exp, 0, len(exp))

if __name__ == "__main__":
    
    exp = '3'
    print(calc(exp))
    
    exp = '(4)'
    print(calc(exp))

    
    exp = '2*3'
    print(calc(exp))
    
    exp = '(2*3)'
    print(calc(exp))    
    
    exp = "(2*3)+((1+2)*7)"
    print(calc(exp))
