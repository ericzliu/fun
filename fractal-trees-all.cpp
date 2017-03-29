#include <iostream>
#include <fstream>
#include <vector>
#include <utility>
using namespace std;

class Matrix
{
   const int NROW = 63;
   const int NCOLUMN = 100;
public:
   Matrix() {
      _data = vector<vector<char> >(NROW, vector<char>(NCOLUMN, '_'));
   }

   vector< pair< int, int > > fill_y(const int H, const int x, const int y)
   {
      vector< pair< int, int > > results;
      for (int i = 1; i <= H; ++i)
      {
         _data[y + i][x] = '1';
      }
      for (int i = 1; i <= H; ++i)
      {
         _data[y + H + i][x - i] = '1';
         _data[y + H + i][x + i] = '1';
      }
      results.push_back(make_pair(x - H, y + H + H));
      results.push_back(make_pair(x + H, y + H + H));
      return results;
   }

   template<typename T>
   void add_all(vector<T> & dest, vector<T> const & source) {
      dest.insert(dest.end(), source.begin(), source.end());
   }

   void fill(const int N) {
      int H = 16;
      vector< pair< int, int > > points;
      points.push_back(make_pair(NCOLUMN / 2, -1));
      for (int i = 1; i <= N; ++i)
      {
         if (H < 1)
            return;
         vector< pair< int, int > > new_points;
         for (auto && point : points)
         {
            add_all(new_points, fill_y(H, point.first, point.second));
         }
         points = new_points;
         H = H / 2;
      }
   }

   void print(ostream & os) {
      for (int i = _data.size() - 1; i >= 0; --i)
      {
         auto const& line = _data[i];
         for (auto c : line)
         {
            os << c;
         }
         os << '\n';
      }
   }

private:
   vector<vector<char> > _data;
};

int main(int argc, char** argv) {
   Matrix matrix;
   matrix.fill(5);
   fstream fout;
   fout.open("art.txt", fstream::out);
   matrix.print(fout);
   fout.close();
   return 0;
}
