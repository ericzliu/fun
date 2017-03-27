public class Solution {

    int rightToLeft(final String s) {
        final int totalLength = s.length();
        int lastZeroIndex = totalLength;
        int lastStartIndex = totalLength - 1;
        int sum = 0;
        int maxLength = 0;
        for (int i = totalLength - 1; i >= 0; i--) {
            if (')' == s.charAt(i)) {
                sum += 1;
            }
            else {
                sum -= 1;
                if (sum < 0) {
                    sum = 0;
                    lastStartIndex = i - 1;
                }
                else if (sum == 0) {
                    lastZeroIndex = i;
                    int tmp = (lastStartIndex - lastZeroIndex + 1);
                    if (tmp > maxLength) {
                        maxLength = tmp;
                    }
                }
            }
        }
        return maxLength;
    }

    public int leftToRight(final String s) {
        final int totalLength = s.length();
        int lastZeroIndex = -1;
        int lastStartIndex = 0;
        int sum = 0;
        int maxLength = 0;
        for (int i = 0; i < totalLength; i++) {
            if ('(' == s.charAt(i)) {
                sum += 1;
            }
            else {
                sum -= 1;
                if (sum < 0) {
                    sum = 0;
                    lastStartIndex = i + 1;
                }
                else if (sum == 0) {
                    lastZeroIndex = i;
                    int tmp = (lastZeroIndex - lastStartIndex + 1);
                    if (tmp > maxLength) {
                        maxLength = tmp;
                    }
                }
            }
        }
        return maxLength;
    }

    public int longestValidParentheses(String s) {
        int l1 = leftToRight(s);
        int l2 = rightToLeft(s);
        if (l1 > l2)
            return l1;
        return l2;
    }
}