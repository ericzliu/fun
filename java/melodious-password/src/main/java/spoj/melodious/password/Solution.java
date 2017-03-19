/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spoj.melodious.password;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Solution {

    static final int[] VOWELS = {'a', 'e', 'i', 'o', 'u'};
    static final int[] CONSONANTS = {'w', 't', 'v', 'g', 'l', 'h', 'x', 'q', 'j', 'r', 'k', 'p', 'm', 'd', 'z', 'c', 'n', 'b', 'f', 's'};

    static {
        Arrays.sort(VOWELS);
        Arrays.sort(CONSONANTS);
    }

    static boolean isVowel(char c) {
        return 0 <= Arrays.binarySearch(VOWELS, (int)c);
    }

    static boolean isConsonant(char c) {
        return 0 <= Arrays.binarySearch(CONSONANTS, (int)c);
    }

    static List<String> backtracking(int N, int l, char[] ps) {
        if (l == N) {
            if (ps.length > 0)
                return Arrays.asList(String.valueOf(ps));
            return Collections.emptyList();
        } else {
            List<String> results = new ArrayList<>();
            if (l == 0 || isConsonant(ps[l - 1])) {
                Arrays.stream(VOWELS).forEach((value) -> {
                    ps[l] = (char) value;
                    results.addAll(backtracking(N, l + 1, ps));
                });
            }
            if (l == 0 || isVowel(ps[l - 1])) {
                Arrays.stream(CONSONANTS).forEach((value) -> {
                    ps[l] = (char) value;
                    results.addAll(backtracking(N, l + 1, ps));
                });
            }
            return results;
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        char[] ps = new char[n];
        Arrays.fill(ps, '\0');
        List<String> results = backtracking(n, 0, ps);
        results.forEach((result) -> {
            System.out.println(result);
        });
    }
}
