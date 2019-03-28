import static org.junit.Assert.*;

import org.junit.Test;

import st.Parser;

import org.junit.Before;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class Task3_TDD_N {
    private Parser parser;

    @Before
    public void set_up() {
        parser = new Parser();
    }

    @Test
    public void basicTest() {
        parser.add("list", "l", Parser.STRING);
        parser.parse("--list=1,2,3");
        List<Integer> testList = Arrays.asList(1, 2, 3);
        assertEquals(parser.getIntegerList("l"), testList);
    }

    @Test
    public void negativeTest() {
        parser.add("list", "l", Parser.STRING);
        parser.parse("--list=-1,2,-3");
        List<Integer> testList = Arrays.asList(-3, -1, 2);
        assertEquals(parser.getIntegerList("l"), testList);
    }

    @Test
    public void emptyTest() {
        parser.add("list", "l", Parser.STRING);
        assertTrue(parser.getIntegerList("a").isEmpty());
    }

    @Test
    public void nonNumberTest() {
        parser.add("list", "l", Parser.STRING);
        parser.parse("--list=aaa");
        assertTrue(parser.getIntegerList("l").isEmpty());
    }

    @Test
    public void specialCharsTest() {
        parser.add("list", "l", Parser.STRING);
        List<Integer> testList = Arrays.asList(1, 2, 3);
        parser.parse("--list=1b2'3");
        assertEquals(parser.getIntegerList("l"), testList);
    }

    @Test
    public void uselessTest() {
        assertEquals(1, 1);
    }
}
