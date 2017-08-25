#include <iostream>
#include <fstream>
#include <sstream>
#include <string>
#include <cassert>
#include <algorithm>
#include <unordered_set>
#include <chrono>
using namespace std;

const char * filename = "algo1-programming_prob-2sum.txt";

string number_to_string(long long number)
{
   ostringstream oss;
   oss << number;
   return oss.str();
}

void read_file_test()
{
   long long number = 0;
   ifstream infile(filename);
   string line;
   while (getline(infile, line))
   {
      line.erase(remove_if(begin(line), end(line), isspace), end(line));
      istringstream iss(line);
      iss >> number;
      auto back = number_to_string(number);
      assert(back == line);
   }
}

unordered_set<long long> read_set()
{
   unordered_set<long long> res;
   long long number = 0;
   ifstream infile(filename);
   string line;
   while (getline(infile, line))
   {
      line.erase(remove_if(begin(line), end(line), isspace), end(line));
      istringstream iss(line);
      iss >> number;
      res.insert(number);
   }
   return res;
}

bool prob_2sum(unordered_set<long long> const& numbers, long long sum) {
   for (const auto& el : numbers)
   {
      auto c = sum - el;
      if (c == el)
      {
         continue;
      }
      if (numbers.find(c) != numbers.cend())
      {
         return true;
      }
   }
   return false;
}

long long count(unordered_set<long long> const& numbers, long long sum_begin, long long sum_end) {
   long long res = 0;
   for (long long sum = sum_begin; sum <= sum_end; sum++)
   {
      if (prob_2sum(numbers, sum))
      {
         res += 1;
      }
   }
   return res;
}

void simple_test() {
   unordered_set<long long> test_nums = { 1, 2, 3, 4, 5 };
   assert(prob_2sum(test_nums, 3));
   assert(prob_2sum(test_nums, 4));
   assert(!prob_2sum(test_nums, 10));
}

int main(int argc, char ** argv) {
   chrono::steady_clock::time_point begin = std::chrono::steady_clock::now();
   auto numbers = read_set();
   chrono::steady_clock::time_point end = std::chrono::steady_clock::now();
   cout << "Time difference = " << chrono::duration_cast<chrono::milliseconds> (end - begin).count() << endl;
   long long total_num = 0;
   for (long long sum = -10000; sum <= 10000; sum++)
   {
      begin = std::chrono::steady_clock::now();
      total_num += count(numbers, sum, sum);
      cout << "Progress" << sum << ":" << total_num << endl;
      end = std::chrono::steady_clock::now();
      cout << "Time difference = " << chrono::duration_cast<chrono::milliseconds> (end - begin).count() << endl;
   }
   return 0;
}
