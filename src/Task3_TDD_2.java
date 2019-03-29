import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import st.OptionMap;
import st.Parser;

public class Task3_TDD_2 {
    private Parser parser;
    private OptionMap optionmap;
    @Before
    public void set_up() {
        parser = new Parser();
        optionmap = new OptionMap();

    }
    @Test
    public void intlist_double_digits() {
        parser.add("option","o", Parser.STRING);
        parser.parse("--option 11,22,33");
        //System.out.println();
        assertEquals(parser.getIntegerList("option"),new ArrayList<Integer>(Arrays.asList(11,22,33)));
    }
    @Test
    public void intlist_null() {
        parser.add("option","o", Parser.STRING);
        parser.parse("--option 1,2,3");
        //System.out.println();
        assertEquals(parser.getIntegerList(null),new ArrayList<Integer>(Arrays.asList()));
    }
    @Test
    public void intlist_no_added_option() {
        //parser.add("option","o", Parser.STRING);
        parser.parse("--option 1,2,3");
        //System.out.println();
        assertEquals(parser.getIntegerList("option"),new ArrayList<Integer>());
    }
    @Test
    public void intlist_no_parsed_option() {
        parser.add("option","o", Parser.STRING);
        //parser.parse("--option 1,2,3");
        //System.out.println();
        assertEquals(parser.getIntegerList("option"),new ArrayList<Integer>());
    }
    @Test
    public void intlist_no_option() {
        //parser.add("option","o", Parser.STRING);
        //parser.parse("--option 1,2,3");
        //System.out.println();
        assertEquals(parser.getIntegerList("option"),new ArrayList<Integer>());
    }
    @Test
    public void intlist_extra_non_numeric() {
        parser.add("option","o", Parser.STRING);
        parser.parse("--option 1.");
        //System.out.println();
        assertEquals(parser.getIntegerList("option"),new ArrayList<Integer>(Arrays.asList(1)));
    }
    @Test
    public void intlist_single_number() {
        parser.add("option","o", Parser.STRING);
        parser.parse("--option 1");
        //System.out.println();
        assertEquals(parser.getIntegerList("option"),new ArrayList<Integer>(Arrays.asList(1)));
    }
    @Test
    public void intlist_comma_followed_by_nothing() {
        parser.add("option","o", Parser.STRING);
        parser.parse("--option 1,");
        //System.out.println();
        assertEquals(parser.getIntegerList("option"),new ArrayList<Integer>(Arrays.asList(1)));
    }
    @Test
    public void intlist_parser_char() {
        parser.add("option","o", Parser.CHAR);
        parser.parse("--option 1,2,3");
        //System.out.println();
        assertEquals(parser.getIntegerList("option"),new ArrayList<Integer>(Arrays.asList(1,2,3)));
    }
    @Test
    public void intlist_parser_integer() {
        parser.add("option","o", Parser.INTEGER);
        parser.parse("--option 1,2,3");
        //System.out.println();
        assertEquals(parser.getIntegerList("option"),new ArrayList<Integer>(Arrays.asList(1,2,3)));
    }
    @Test
    public void intlist_parser_bool() {
        parser.add("option","o", Parser.BOOLEAN);
        parser.parse("--option 1,2,3");
        //System.out.println();
        assertEquals(parser.getIntegerList("option"),new ArrayList<Integer>(Arrays.asList(1,2,3)));
    }
    @Test
    public void intlist_parse_two_at_a_time() {
        parser.add("option","o", Parser.STRING);
        parser.add("option2","a", Parser.STRING);
        parser.parse("--option 1,2 --option2 2,3,4");
        //System.out.println();
        assertEquals(parser.getIntegerList("option2"),new ArrayList<Integer>(Arrays.asList(2,3,4)));
    }

    @Test
    public void intlist_act_on_shortcut() {
        parser.add("option","o", Parser.STRING);
        parser.add("option2","a", Parser.STRING);
        parser.parse("--option 1,2");
        //System.out.println();
        assertEquals(parser.getIntegerList("o"),new ArrayList<Integer>(Arrays.asList(1,2)));
    }
    @Test
    public void intlist_empty_input() {
        parser.add("option","o", Parser.STRING);
        parser.add("option2","a", Parser.STRING);
        parser.parse("--option 1,2 --option2");
        //System.out.println();
        assertEquals(parser.getIntegerList(""),new ArrayList<Integer>(Arrays.asList()));
    }
}