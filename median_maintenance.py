import heapq
import unittest

class MedianMaint(object):
    def __init__(self):
        self.low_heap = []
        self.high_heap = []

    def process(self, number):
        try:
            low_max = -self.low_heap[0]
        except IndexError:
            low_max = None

        if low_max is None or number <= low_max:
            heapq.heappush(self.low_heap, -number)
        else:
            heapq.heappush(self.high_heap, number)

        low_size = len(self.low_heap)
        high_size = len(self.high_heap)
        if low_size > (high_size + 1):
            low_max = 0 - heapq.heappop(self.low_heap)
            heapq.heappush(self.high_heap, low_max)
        elif high_size > low_size:
            high_min = heapq.heappop(self.high_heap)
            heapq.heappush(self.low_heap, -high_min)

        return -self.low_heap[0]

class TestMedianMaint(unittest.TestCase):
    def test_tc1(self):
        algo = MedianMaint()
        median = algo.process(1)
        self.assertEqual(1, median)
    
    def test_tc2(self):
        algo = MedianMaint()
        median = algo.process(1)
        median = algo.process(2)
        self.assertEqual(1, median)

    def test_tc3(self):
        algo = MedianMaint()
        median = algo.process(1)
        median = algo.process(2)
        median = algo.process(3)
        self.assertEqual(2, median)

if __name__=='__main__':
    # unittest.main()
    median_maint = MedianMaint()
    residue = 0
    with open('Median.txt') as fin:
        for line in fin:
            try:
                residue = (residue + median_maint.process(int(line.strip()))) % 10000
            except ValueError:
                pass
    print(residue)
