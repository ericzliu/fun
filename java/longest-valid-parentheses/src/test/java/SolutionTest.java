import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class SolutionTest {
    @Test
    public void rightToLeft() throws Exception {
        String test = "(()";
        int l = new Solution().rightToLeft(test);
        Assert.assertEquals(2, l);
    }

    @Test
    public void leftToRight() throws Exception {
        String test = "())";
        int l = new Solution().leftToRight(test);
        Assert.assertEquals(2, l);
    }

    @Test
    public void longestValidParentheses() throws Exception {
        String test = "";
        int l = new Solution().longestValidParentheses(test);
        Assert.assertEquals(0, l);
        test = "(";
        l = new Solution().longestValidParentheses(test);
        Assert.assertEquals(0, l);
        test = ")";
        l = new Solution().longestValidParentheses(test);
        Assert.assertEquals(0, l);
        test = ")()())";
        l = new Solution().longestValidParentheses(test);
        Assert.assertEquals(4, l);
    }

}