import static org.junit.Assert.*;

import org.junit.Test;

import st.OptionMap;//take out later
import st.Parser;

import org.junit.Before;
import static org.junit.Assert.assertEquals;

public class Task1_Functional {
    private Parser parser;
    private OptionMap optionmap;
    @Before
    public void set_up() {
        parser = new Parser();
        optionmap = new OptionMap();

    }
    @Test
    public void example_test() {
        parser.add("output", "o", Parser.STRING);
        parser.parse("--output=output.txt");
        assertEquals(parser.getString("o"), "output.txt");
    }


    @Test
    public void example_test_2() {
        parser.add("test_option","h", Parser.STRING);
        parser.parse("-h=output.txt");
        parser.parse("--test_option=output.txt");//only one of this or line above needed.
        assertEquals(parser.getString("test_option"), "output.txt");
        assertEquals(parser.getString("h"), "output.txt");
    }

//------------------------------------------------------------------------------------Functional Tests

//Add options with a shortcut

    //3.1
    @Test
    public void add_test_sameName() {
        parser.add("test_option_1","a", Parser.STRING);
        parser.parse("--test_option_1=output.txt");
        parser.add("test_option_1","a",Parser.STRING);//Overrides previous option, removing value output.txt
        //System.out.println(parser.toString());
        assertEquals(parser.getString("test_option_1"), "");
    }
    @Test
    public void add_test_sameNameFail() {
        parser.add("test_option_1","a", Parser.STRING);
        parser.parse("--test_option_1=output.txt");
        parser.add("test_option_1","a",Parser.STRING);//Overrides previous option, removing value output.txt
        //System.out.println(parser.toString());
        assertEquals(parser.getString("test_option_1"), "");
    }


    //3.2

    @Test
    public void add_test_numAlphaUnderscore_success() {
        parser.add("_test_55","_t55", Parser.STRING);
        parser.parse("--_test_55=output.txt");
        System.out.println(parser.getString("_test_55"));
        assertEquals(parser.getString("_test_55"), "output.txt");
    }
    @Test(expected = RuntimeException.class)
    public void add_test_runtimeExceptionFirstNum() { //Makes sure runtime exception is thrown
        parser.add("1_test_option","a", Parser.STRING);
    }
    @Test(expected = RuntimeException.class)
    public void add_test_runtimeExceptionFirstNumShortcut() { //Makes sure runtime exception is thrown
        parser.add("test_option","1_a", Parser.STRING);
    }
    @Test(expected = RuntimeException.class)
    public void add_test_runtimeExceptionNonAlphaChar() { //Makes sure runtime exception is thrown
        parser.add("test_option.","a", Parser.STRING);
    }
    @Test(expected = RuntimeException.class)
    public void add_test_runtimeExceptionNonAlphaCharShortcut() { //Makes sure runtime exception is thrown
        parser.add("test_option","a.", Parser.STRING);
    }
    //3.3
    @Test
    public void add_test_caseSensitivity_1() {
        parser.add("TEST_OPTION","a", Parser.STRING);
        parser.parse("--TEST_OPTION=output.txt");
        //System.out.println(parser.toString());
        assertEquals(parser.getString("test_option"), ""); //Shows nothing because not correct case
    }
    @Test
    public void add_test_caseSensitivity_2() {
        parser.add("test_option","a", Parser.STRING);
        parser.parse("--TEST_OPTION=output.txt");
        //System.out.println(parser.toString());
        assertEquals(parser.getString("test_option"), "");
    }
    @Test
    public void add_test_caseSensitivity_3() {
        parser.add("test_option","a", Parser.STRING);
        parser.parse("--TEST_OPTION=output.txt");
        //System.out.println(parser.toString());
        assertEquals(parser.getString("TEST_OPTION"), ""); //Does not give the option value even though it was parsed - because it was not added to optionmap before parsing due to case sensitivity
    }
    @Test
    public void add_test_caseSensitivity_4() {
        parser.add("TEST_OPTION","a", Parser.STRING);
        parser.parse("--TEST_OPTION=output.txt");
        //System.out.println(parser.toString());
        assertEquals(parser.getString("TEST_OPTION"), "output.txt");
    }
    @Test
    public void add_test_caseSensitivity_1_Shortcut() {
        parser.add("test_option","A", Parser.STRING);
        parser.parse("-A=output.txt");
        //System.out.println(parser.toString());
        assertEquals(parser.getString("a"), "");
    }
    @Test
    public void add_test_caseSensitivity_2_Shortcut() {
        parser.add("test_option","a", Parser.STRING);
        parser.parse("-A=output.txt");
        //System.out.println(parser.toString());
        assertEquals(parser.getString("a"), "");
    }
    @Test
    public void add_test_caseSensitivity_3_Shortcut() {
        parser.add("test_option","a", Parser.STRING);
        parser.parse("-A=output.txt");
        //System.out.println(parser.toString());
        assertEquals(parser.getString("A"), "");
    }
    @Test
    public void add_test_caseSensitivity_4_Shortcut() {
        parser.add("test_option","A", Parser.STRING);
        parser.parse("-A=output.txt");
        //System.out.println(parser.toString());
        assertEquals(parser.getString("A"), "output.txt");
    }
    //3.4
    @Test
    public void add_test_optionEqualsShortcut_1() {
        parser.add("output","o", Parser.STRING);
        parser.add("o","a", Parser.STRING);
        parser.parse("--output=output.txt");
        //System.out.println(parser.toString());
        assertEquals(parser.getString("output"), "output.txt"); //Shows nothing because not correct case
    }
    @Test
    public void add_test_optionEqualsShortcut_2() {
        parser.add("output","o", Parser.STRING);
        parser.add("o","a", Parser.STRING);
        parser.parse("--o=output.txt");
        //System.out.println(parser.toString());
        assertEquals(parser.getString("a"), "output.txt");
    }
    @Test
    public void add_test_optionEqualsShortcut_3() {
        parser.add("output","o", Parser.STRING);
        parser.add("o","a", Parser.STRING);
        parser.parse("-o=output.txt");
        //System.out.println(parser.toString());
        assertEquals(parser.getString("output"), "output.txt");
    }
    //3.5
    @Test
    public void add_test_BoolFalse_1() {
        parser.add("optimise","o", Parser.BOOLEAN);
        parser.parse("--optimise=false");
        //System.out.println(parser.toString());
        assertEquals(parser.getBoolean("optimise"), false);
    }
    @Test
    public void add_test_BoolFalse_2() {
        parser.add("optimise","o", Parser.BOOLEAN);
        parser.parse("--optimise=0");
        //System.out.println(parser.toString());
        assertEquals(parser.getBoolean("optim"
                + "ise"), false);
    }
    @Test
    public void add_test_BoolTrue_1() {
        parser.add("optimise","O", Parser.BOOLEAN);
        parser.parse("-O");
        //System.out.println(parser.toString());
        assertEquals(parser.getBoolean("optimise"), true);
    }
    @Test
    public void add_test_BoolTrue_2() {
        parser.add("optimise","O", Parser.BOOLEAN);
        parser.parse("-O=true");
        //System.out.println(parser.toString());
        assertEquals(parser.getBoolean("optimise"), true);
    }
    @Test
    public void add_test_BoolTrue_3() {
        parser.add("optimise","O", Parser.BOOLEAN);
        parser.parse("-O 100");
        //System.out.println(parser.toString());
        assertEquals(parser.getBoolean("O"), true);
    }
    @Test
    public void add_test_BoolTrue_4() {
        parser.add("optimise","O", Parser.BOOLEAN);
        parser.parse("-O =1");
        //System.out.println(parser.toString());
        assertEquals(parser.getBoolean("O"), true);
    }
    //4.1
    @Test
    public void add_test_no_short_sameName() {
        parser.add("test_option_1", Parser.STRING);
        parser.parse("--test_option_1=output.txt");
        parser.add("test_option_1",Parser.STRING);//Overrides previous option, removing value output.txt
        //System.out.println(parser.toString());
        assertEquals(parser.getString("test_option_1"), "");
    }
    @Test
    public void add_test_no_short_sameNameFail() {
        parser.add("test_option_1", Parser.STRING);
        parser.parse("--test_option_1=output.txt");
        parser.add("test_option_1",Parser.STRING);//Overrides previous option, removing value output.txt
        //System.out.println(parser.toString());
        assertEquals(parser.getString("test_option_1"), "");
    }
    //4.2

    @Test
    public void add_test_no_short_numAlphaUnderscore_success() {
        parser.add("_test_55", Parser.STRING);
        parser.parse("--_test_55=output.txt");
        System.out.println(parser.getString("_test_55"));
        assertEquals(parser.getString("_test_55"), "output.txt");
    }
    @Test(expected = RuntimeException.class)
    public void add_test_no_short_runtimeExceptionFirstNum() { //Makes sure runtime exception is thrown
        parser.add("1_test_option", Parser.STRING);
    }
    @Test(expected = RuntimeException.class)
    public void add_test_no_short_runtimeExceptionNonAlphaChar() { //Makes sure runtime exception is thrown
        parser.add("test_option.", Parser.STRING);
    }

    //4.3

    @Test
    public void add_test_no_short_caseSensitivity_1() {
        parser.add("TEST_OPTION", Parser.STRING);
        parser.parse("--TEST_OPTION=output.txt");
        //System.out.println(parser.toString());
        assertEquals(parser.getString("test_option"), ""); //Shows nothing because not correct case
    }
    @Test
    public void add_test_no_short_caseSensitivity_2() {
        parser.add("test_option", Parser.STRING);
        parser.parse("--TEST_OPTION=output.txt");
        //System.out.println(parser.toString());
        assertEquals(parser.getString("test_option"), "");
    }
    @Test
    public void add_test_no_short_caseSensitivity_3() {
        parser.add("test_option", Parser.STRING);
        parser.parse("--TEST_OPTION=output.txt");
        //System.out.println(parser.toString());
        assertEquals(parser.getString("TEST_OPTION"), ""); //Does not give the option value even though it was parsed - because it was not added to optionmap before parsing due to case sensitivity
    }
    @Test
    public void add_test_no_short_caseSensitivity_4() {
        parser.add("TEST_OPTION", Parser.STRING);
        parser.parse("--TEST_OPTION=output.txt");
        //System.out.println(parser.toString());
        assertEquals(parser.getString("TEST_OPTION"), "output.txt");
    }

    //4.4 //there is no shortcut so option name can't equal shortcut

    //4.5
    @Test
    public void add_test_no_short_BoolFalse_1() {
        parser.add("optimise", Parser.BOOLEAN);
        parser.parse("--optimise=false");
        //System.out.println(parser.toString());
        assertEquals(parser.getBoolean("optimise"), false);
    }
    @Test
    public void add_test_no_short_BoolFalse_2() {
        parser.add("optimise", Parser.BOOLEAN);
        parser.parse("--optimise=0");
        //System.out.println(parser.toString());
        assertEquals(parser.getBoolean("optim"
                + "ise"), false);
    }
    @Test
    public void add_test_no_short_BoolTrue_1() {
        parser.add("optimise", Parser.BOOLEAN);
        parser.parse("--optimise");
        //System.out.println(parser.toString());
        assertEquals(parser.getBoolean("optimise"), true);
    }
    @Test
    public void add_test_no_short_BoolTrue_2() {
        parser.add("optimise", Parser.BOOLEAN);
        parser.parse("--optimise=true");
        //System.out.println(parser.toString());
        assertEquals(parser.getBoolean("optimise"), true);
    }
    @Test
    public void add_test_no_short_BoolTrue_3() {
        parser.add("optimise", Parser.BOOLEAN);
        parser.parse("--optimise 100");
        //System.out.println(parser.toString());
        assertEquals(parser.getBoolean("optimise"), true);
    }
    @Test
    public void add_test_no_short_BoolTrue_4() {
        parser.add("optimise", Parser.BOOLEAN);
        parser.parse("--optimise =1");
        //System.out.println(parser.toString());
        assertEquals(parser.getBoolean("optimise"), true);
    }

    //5.1 + 5.2 done already tested
    @Test
    public void parse_test_overwriting_option_by_reassigning_shortcut() {
        parser.add("input","i",Parser.STRING);
        parser.parse("--input 1.txt" );
        parser.parse("-i 2.txt");
        System.out.println(parser.toString());
        assertEquals(parser.getString("input"), "2.txt");
    }

    @Test
    public void parse_test_() {
        parser.add("input",Parser.STRING);
        parser.add("output", Parser.STRING);
        parser.parse("--input 1.txt --output=2.txt" );
        //System.out.println(parser.toString());
        assertEquals(parser.getString("input"), "1.txt");
    }

    //5.3
    @Test
    public void parse_test_equalsSpace_1() {
        parser.add("input",Parser.STRING);
        parser.parse("--input 1.txt");
        //System.out.println(parser.toString());
        assertEquals(parser.getString("input"), "1.txt");
    }
    @Test
    public void parse_test_equalsSpace_2() {
        parser.add("input",Parser.STRING);
        parser.parse("--input=1.txt");
        //System.out.println(parser.toString());
        assertEquals(parser.getString("input"), "1.txt");
    }
    //5.4
    @Test
    public void parse_test_quotes_1() {
        parser.add("input",Parser.STRING);
        parser.parse("--input='1.txt'");
        //System.out.println(parser.toString());
        assertEquals(parser.getString("input"), "1.txt");
    }
    @Test
    public void parse_test_quotes_2() {
        parser.add("input",Parser.STRING);
        parser.parse("--input=\"1.txt\"");
        //System.out.println(parser.toString());
        assertEquals(parser.getString("input"), "1.txt");
    }
    @Test
    public void parse_test_quotes_3() {
        parser.add("input",Parser.STRING);
        parser.parse("--input=1.txt");
        //System.out.println(parser.toString());
        assertEquals(parser.getString("input"), "1.txt");
    }

    //5.5
    @Test
    public void parse_test_decorativequotes_1() {
        parser.add("option",Parser.STRING);
        parser.parse("--option='value=\"abc\"'");
        //System.out.println(parser.toString());
        assertEquals(parser.getString("option"), "value=\"abc\"");
    }
    @Test
    public void parse_test_decorativequotes_2() {
        parser.add("option",Parser.STRING);
        parser.parse("--option=\"value='abc'\"");
        //System.out.println(parser.toString());
        assertEquals(parser.getString("option"), "value='abc'");
    }
    //5.6
    @Test
    public void parse_test_multipleParseAssignments() {
        parser.add("option",Parser.STRING);
        parser.parse("--option=1.txt");
        parser.parse("--option=2.txt");
        //System.out.println(parser.toString());
        assertEquals(parser.getString("option"), "2.txt");
    }
    //5.7
    @Test
    public void parse_test_noAssignment() {
        parser.add("option",Parser.STRING);
        //System.out.println(parser.toString());
        assertEquals(parser.getString("option"), "");
    }
    //5.8
    @Test
    public void parse_test_multipleParses() {
        parser.add("option_1",Parser.STRING);
        parser.add("option_2",Parser.STRING);
        parser.add("option_3",Parser.STRING);
        parser.add("option_4",Parser.STRING);
        parser.add("option_5",Parser.STRING);
        parser.add("option_6",Parser.STRING);
        parser.parse("--option_1=1.txt --option_2=2.txt");
        parser.parse("--option_3=3.txt");
        parser.parse("--option_4=4.txt");
        parser.parse("--option_5=5.txt");
        parser.parse("--option_6=6.txt");
        //System.out.println(parser.toString());
        assertEquals(parser.getString("option_2"), "2.txt");
    }
    //6.1
    @Test
    public void parse_test_nameFirstThenShortcut() {
        parser.add("option_1","o",Parser.STRING);
        parser.add("option_2","option_1", Parser.STRING);
        parser.parse("--option_1=Name_took_value");
        parser.parse("-option_1=Shortcut_took_value");
        //System.out.println(parser.toString());
        assertEquals(parser.getString("option_1"), "Name_took_value");
    }
    //6.2
    @Test
    public void parse_test_intNotProvided() {
        assertEquals(parser.getInteger("option_1"), 0);
    }
    @Test
    public void parse_test_boolNotProvided() {
        assertEquals(parser.getBoolean("option_1"), false);
    }
    @Test
    public void parse_test_stringNotProvided() {
        assertEquals(parser.getString("option_1"), "");
    }
    @Test
    public void parse_test_charNotProvided() {
        assertEquals(parser.getChar("option_1"), '\0');
    }









}