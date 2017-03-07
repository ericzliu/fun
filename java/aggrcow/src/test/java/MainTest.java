/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import org.junit.Test;
import org.junit.Assert;

/**
 *
 * @author DIAMIAO
 */
public class MainTest {
    
    public MainTest() {
    }

    @Test
    public void sample() {
        InputStream sample = Main.class.getResourceAsStream("sample.txt");
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        Main.solve(sample, output);
        Assert.assertEquals("3" + System.lineSeparator(), output.toString());
    }

    @Test
    public void simplest() {
        InputStream sample = Main.class.getResourceAsStream("simplest.txt");
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        Main.solve(sample, output);
        Assert.assertEquals("500000000" + System.lineSeparator(), output.toString());
    }

    @Test
    public void repetitive() {
        InputStream sample = Main.class.getResourceAsStream("repetitive.txt");
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        Main.solve(sample, output);
        Assert.assertEquals("3" + System.lineSeparator(), output.toString());
    }    
    
    @Test
    public void two_cases() {
        InputStream sample = Main.class.getResourceAsStream("two-cases.txt");
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        Main.solve(sample, output);
        Assert.assertEquals("500000000" + System.lineSeparator() + "3" + System.lineSeparator(), output.toString());
    }        
}
