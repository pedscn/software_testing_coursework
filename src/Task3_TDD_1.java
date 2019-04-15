import org.junit.Before;
import org.junit.Test;
import st.OptionMap;
import st.Parser;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class Task3_TDD_1 {
    private Parser parser;
    private OptionMap optionmap;

    @Before
    public void set_up() {
        parser = new Parser();
        optionmap = new OptionMap();

    }

    //Basic tests
    @Test
    public void intlist_BASIC() {
        parser.add("option", "o", Parser.STRING);
        parser.parse("--option 1,2,3");
        System.out.println();
        assertEquals(parser.getIntegerList("option"), new ArrayList<Integer>(Arrays.asList(1, 2, 3)));
    }

    @Test
    public void intlist_BASIC_sort() {//TODO try sorting with negative numbers
        parser.add("option", "o", Parser.STRING);
        parser.parse("--option 3,2,1");
        System.out.println();
        assertEquals(parser.getIntegerList("option"), new ArrayList<Integer>(Arrays.asList(1, 2, 3)));
    }

    @Test
    public void intlist_BASIC_sort_duplicates() {
        parser.add("option", "o", Parser.STRING);
        parser.parse("--option 2,1,3,5,8,3,3");
        System.out.println();
        assertEquals(parser.getIntegerList("option"), new ArrayList<Integer>(Arrays.asList(1, 2, 3, 3, 3, 5, 8)));
    }

    @Test
    public void intlist_BASIC_long_sort() {
        parser.add("option", "o", Parser.STRING);
        parser.parse("--option 2,1,3,5,8,3,3");
        System.out.println();
        assertEquals(parser.getIntegerList("option"), new ArrayList<Integer>(Arrays.asList(1, 2, 3, 3, 3, 5, 8)));
    }

    //Spec 1
    @Test
    public void intlisttest_shortcut_same_as_full_name() {
        parser.add("option", "o", Parser.STRING);
        parser.add("o", "a", Parser.STRING);
        parser.parse("--o 1,2,3");
        parser.parse("-o 1,2,3,4");
        assertEquals(parser.getIntegerList("o"), new ArrayList<Integer>(Arrays.asList(1, 2, 3)));
    }

    //Spec 2
    @Test
    public void intlist_Empty() {
        parser.add("option", "o", Parser.STRING);
        parser.parse("--option");
        System.out.println();
        assertEquals(parser.getIntegerList("option"), new ArrayList<>());
    }

    //Spec 3
    @Test
    public void intlist_non_numeric_separator_1() {
        parser.add("option", "o", Parser.STRING);
        parser.parse("--option=1.2.3.4");
        System.out.println();
        assertEquals(parser.getIntegerList("option"), new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4)));
    }

    @Test
    public void intlist_non_numeric_separator_2() {
        parser.add("option", "o", Parser.STRING);
        parser.parse("--option=1a2b3C4");
        System.out.println();
        assertEquals(parser.getIntegerList("option"), new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4)));
    }

    @Test
    public void intlist_non_numeric_separator_3() {
        parser.add("option", "o", Parser.STRING);
        parser.parse("--option=a1++7[8~9[]");
        System.out.println();
        assertEquals(parser.getIntegerList("option"), new ArrayList<Integer>(Arrays.asList(1, 7, 8, 9)));
    }

    //Spec 4
    @Test
    public void intlist_range_regular() {
        parser.add("option", "o", Parser.STRING);
        parser.parse("--option=3-8");
        System.out.println();
        assertEquals(parser.getIntegerList("option"), new ArrayList<Integer>(Arrays.asList(3, 4, 5, 6, 7, 8)));
    }

    @Test
    public void intlist_range_reversed() {
        parser.add("option", "o", Parser.STRING);
        parser.parse("--option=8-3");
        System.out.println();
        assertEquals(parser.getIntegerList("option"), new ArrayList<Integer>(Arrays.asList(3, 4, 5, 6, 7, 8)));
    }

    @Test
    public void intlist_range_3() {
        parser.add("option", "o", Parser.STRING);
        parser.parse("--option=,1,8-3,2");
        System.out.println();
        assertEquals(parser.getIntegerList("option"), new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8)));
    }

    //Spec 5
    @Test
    public void intlist_negative() {
        parser.add("option", "o", Parser.STRING);
        parser.parse("--option=-3,-1,2");
        System.out.println();
        assertEquals(parser.getIntegerList("option"), new ArrayList<Integer>(Arrays.asList(-3, -1, 2)));
    }

    @Test
    public void intlist_negative_sort_with_zero() {
        parser.add("option", "o", Parser.STRING);
        parser.parse("--option=1,-2,3,0");
        System.out.println();
        assertEquals(parser.getIntegerList("option"), new ArrayList<Integer>(Arrays.asList(-2, 0, 1, 3)));
    }

    @Test
    public void intlist_negative_() {
        parser.add("option", "o", Parser.STRING);
        parser.parse("--option=1,-2,3,0");
        System.out.println();
        assertEquals(parser.getIntegerList("option"), new ArrayList<Integer>(Arrays.asList(-2, 0, 1, 3)));
    }

    @Test
    public void intlist_negative_neg_neg_range() {
        parser.add("option", "o", Parser.STRING);
        parser.parse("--option=-5--2");
        System.out.println();
        assertEquals(parser.getIntegerList("option"), new ArrayList<Integer>(Arrays.asList(-5, -4, -3, -2)));
    }

    @Test
    public void intlist_negative_neg_pos_range() {
        parser.add("option", "o", Parser.STRING);
        parser.parse("--option=-4-3");
        System.out.println();
        assertEquals(parser.getIntegerList("option"), new ArrayList<Integer>(Arrays.asList(-4, -3, -2, -1, 0, 1, 2, 3)));
    }

    @Test
    public void intlist_negative_pos_neg_range() {
        parser.add("option", "o", Parser.STRING);
        parser.parse("--option=3--4");
        System.out.println();
        assertEquals(parser.getIntegerList("option"), new ArrayList<Integer>(Arrays.asList(-4, -3, -2, -1, 0, 1, 2, 3)));
    }

    //Spec 6
    @Test
    public void intlist_suffix_delete_later() {
        parser.add("option", "o", Parser.STRING);
        parser.parse("--option= a");
        System.out.println();
        assertEquals(parser.getIntegerList("option"), new ArrayList<>());
    }

    @Test
    public void intlist_suffix_hyphen() {

        parser.add("option", "o", Parser.STRING);
        parser.parse("--option= 3-");
        System.out.println();
        assertEquals(parser.getIntegerList("option"), new ArrayList<>());
    }

    @Test
    public void intlist_suffix_hyphen_2() {
        parser.add("option", "o", Parser.STRING);
        parser.parse("--option= 3-,3");
        System.out.println();
        assertEquals(parser.getIntegerList("option"), new ArrayList<>());
    }
}
