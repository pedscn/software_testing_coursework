import org.junit.Before;
import org.junit.Test;
import st.OptionMap;
import st.Parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Task2_Coverage {
    private Parser parser;
    private OptionMap optionmap;

    @Before
    public void set_up() {
        parser = new Parser();
        optionmap = new OptionMap();
    }

    @Test
    public void test_parse_correct() {
        parser.add("option", "o", Parser.STRING);
        assertEquals(parser.parse("--option=output.txt"), 0);
    }

    @Test
    public void test_parse_length_0() {
        parser.add("option", "o", Parser.STRING);
        assertEquals(parser.parse(""), -2);
    }

    @Test
    public void test_parse_null() {
        parser.add("option", "o", Parser.STRING);
        assertEquals(parser.parse(null), -1);
    }

    @Test
    public void test_parse_minus_3() {
        parser.add("option", "o", Parser.STRING);
        assertEquals(parser.parse("not a valid command string"), -3);
    }

    @Test
    public void test_parse_justSpace() {
        parser.add("option", "o", Parser.STRING);
        parser.parse(" ");
        assertEquals(parser.getString("option"), "");
    }

    @Test
    public void test_parse_mutlipleSpaces() {
        parser.add("option", "o", Parser.STRING);
        parser.parse("   ");
        assertEquals(parser.getString("option"), "");
    }

    @Test
    public void test_parse_valid_option_name_but_invalid_end() {
        parser.add("option", "o", Parser.STRING);
        parser.parse("-option    ");
        assertEquals(parser.getString("option"), "");
    }

    @Test
    public void test_parse_random() {
        parser.add("option", "o", Parser.STRING);
        parser.parse("--option    ");
        assertEquals(parser.getString("option"), "");
    }

    @Test
    public void test_parse_valid_option_characters_end() {
        parser.add("option", "o", Parser.STRING);
        parser.parse("--optionqfsf");
        assertEquals(parser.getString("optionqfsf"), "");
    }

    @Test
    public void test_parse_boolean() {
        parser.add("option", "o", Parser.BOOLEAN);
        parser.parse("--option");
        assertEquals(parser.getBoolean("option"), true);
    }

    @Test
    public void test_parse_extra() {
        parser.add("option_1", "a", Parser.BOOLEAN);
        parser.add("option_2", "b", Parser.BOOLEAN);
        parser.add("option_3", "c", Parser.BOOLEAN);
        parser.parse("--option_1 --option_2 -c");
        assertEquals(parser.getBoolean("option_1"), true);
        assertEquals(parser.getBoolean("option_2"), true);
        assertEquals(parser.getBoolean("option_3"), true);
    }

    @Test
    public void test_parse_nonsense() {
        parser.add("option", "o", Parser.STRING);
        parser.parse("yeah no");
        assertEquals(parser.getString("option"), "");
    }

    @Test
    public void test_getInteger_String() {
        parser.add("option", "o", Parser.STRING);
        parser.parse("--option =output.txt");
        assertEquals(parser.getInteger("option"), 0);
    }

    @Test
    public void test_getInteger_Char() {
        parser.add("option", "o", Parser.CHAR);
        parser.parse("--option =output.txt");
        assertEquals(parser.getInteger("option"), 111);
    }

    @Test
    public void test_getInteger_Int() {
        parser.add("option", "o", Parser.INTEGER);
        parser.parse("--option =1");
        assertEquals(parser.getInteger("option"), 1);
    }

    @Test
    public void test_getInteger_BigInt() {
        parser.add("option", "o", Parser.INTEGER);
        parser.parse("--option =12124114152");
        assertEquals(parser.getInteger("option"), 0);
    }

    @Test
    public void test_getInteger_BiggerInt() {
        parser.add("option", "o", Parser.INTEGER);
        parser.parse("--option =999999999999999999999999999999999999999999999999");
        assertEquals(parser.getInteger("option"), 0);
    }

    @Test
    public void test_getInteger_Bool_Again() {
        parser.add("option", "o", Parser.BOOLEAN);
        parser.parse("--option=true");
        System.out.println(parser.getInteger("option"));
        assertEquals(parser.getInteger("option"), 1);
    }

    @Test
    public void test_getInteger_Bool_Again_2() {
        parser.add("option", "o", Parser.BOOLEAN);
        parser.parse("--option=false");
        System.out.println(parser.getInteger("option"));
        assertEquals(parser.getInteger("option"), 0);
    }

    @Test
    public void test_getInteger_String_nums() {
        parser.add("option", "o", Parser.STRING);
        parser.parse("--option=1234");
        System.out.println(parser.getInteger("option"));
        assertEquals(parser.getInteger("option"), 1234);
    }

    @Test
    public void test_getInteger_toString_Test() {
        parser.add("option", "o", Parser.STRING);
        parser.parse("--option=output.txt");
        System.out.println(parser.toString());
        String expected_result = "OptionMap [options=\n\t{name=option, shortcut=o, type=3, value=output.txt}\n]";
        assertTrue(parser.toString().equals(expected_result));
    }

    @Test(expected = RuntimeException.class)
    public void OptionMapTest_1() {
        parser.add(null, Parser.STRING);
    }

    @Test(expected = RuntimeException.class)
    public void OptionMapTest_2() {
        parser.add(null, null, Parser.STRING);
    }

    @Test(expected = RuntimeException.class)
    public void OptionMapTest_3() {
        parser.add("option", null, Parser.STRING);
    }

    @Test(expected = RuntimeException.class)
    public void OptionMapTest_4() {
        parser.add(null, "option", Parser.STRING);
    }

    @Test(expected = RuntimeException.class)
    public void OptionMapTest_5() {
        parser.add("option", "o", -8);
    }

    @Test(expected = RuntimeException.class)
    public void OptionMapTest_6() {
        parser.add("option", "o", 10);
    }

    @Test(expected = RuntimeException.class)
    public void OptionMapTest_7() {
        parser.add("", "o", 1);
    }

    @Test(expected = RuntimeException.class)
    public void OptionMapTest_8() {
        parser.add("", "", 1);
    }

    //Task 1 Tests
    @Test
    public void example_test() {
        parser.add("output", "o", Parser.STRING);
        parser.parse("--output=output.txt");
        assertEquals(parser.getString("o"), "output.txt");
    }

    @Test
    public void example_test_2() {
        parser.add("test_option", "h", Parser.STRING);
        parser.parse("-h=output.txt");
        parser.parse("--test_option=output.txt");
        assertEquals(parser.getString("test_option"), "output.txt");
        assertEquals(parser.getString("h"), "output.txt");
    }

    //3.1
    @Test
    public void add_test_sameName() {
        parser.add("test_option_1", "a", Parser.STRING);
        parser.parse("--test_option_1=output.txt");
        parser.add("test_option_1", "a", Parser.STRING);
        assertEquals(parser.getString("test_option_1"), "");
    }

    @Test
    public void add_test_sameNameFail() {
        parser.add("test_option_1", "a", Parser.STRING);
        parser.parse("--test_option_1=output.txt");
        parser.add("test_option_1", "a", Parser.STRING);
        assertEquals(parser.getString("test_option_1"), "");
    }

    //3.2
    @Test
    public void add_test_numAlphaUnderscore_success() {
        parser.add("_test_55", "_t55", Parser.STRING);
        parser.parse("--_test_55=output.txt");
        System.out.println(parser.getString("_test_55"));
        assertEquals(parser.getString("_test_55"), "output.txt");
    }

    @Test
    public void add_test_numAlphaUnderscore_success_2() {
        parser.add("_test_55", "_t55_", Parser.STRING);
        parser.parse("--_test_55=output.txt");
        System.out.println(parser.getString("_test_55"));
        assertEquals(parser.getString("_t55_"), "output.txt");
    }

    @Test
    public void add_test_numAlphaUnderscore_success_3() {
        parser.add("_test_55", "_____t___5__5____", Parser.STRING);
        parser.parse("--_test_55=output.txt");
        System.out.println(parser.getString("_test_55"));
        assertEquals(parser.getString("_____t___5__5____"), "output.txt");
    }

    @Test(expected = RuntimeException.class)
    public void add_test_runtimeExceptionFirstNum() {
        parser.add("1_test_option", "a", Parser.STRING);
    }

    @Test(expected = RuntimeException.class)
    public void add_test_runtimeExceptionFirstNumShortcut() {
        parser.add("test_option", "1_a", Parser.STRING);
    }

    @Test(expected = RuntimeException.class)
    public void add_test_runtimeExceptionNonAlphaChar() {
        parser.add("test_option.", "a", Parser.STRING);
    }

    @Test(expected = RuntimeException.class)
    public void add_test_runtimeExceptionNonAlphaChar_2() {
        parser.add("test_option$", "a", Parser.STRING);
    }

    @Test(expected = RuntimeException.class)
    public void add_test_runtimeExceptionNonAlphaChar_3() {
        parser.add("test_op@tion", "a", Parser.STRING);
    }

    @Test(expected = RuntimeException.class)
    public void add_test_runtimeExceptionNonAlphaCharShortcut() {
        parser.add("test_option", "a.", Parser.STRING);
    }

    @Test(expected = RuntimeException.class)
    public void add_test_runtimeExceptionNonAlphaCharShortcut_2() {
        parser.add("test_option", "a&", Parser.STRING);
    }

    @Test(expected = RuntimeException.class)
    public void add_test_runtimeExceptionNonAlphaCharShortcut_3() {
        parser.add("test_option", "(a", Parser.STRING);
    }

    //3.3
    @Test
    public void add_test_caseSensitivity_1() {
        parser.add("TEST_OPTION", "a", Parser.STRING);
        parser.parse("--TEST_OPTION=output.txt");
        assertEquals(parser.getString("test_option"), "");
    }

    @Test
    public void add_test_caseSensitivity_2() {
        parser.add("test_option", "a", Parser.STRING);
        parser.parse("--TEST_OPTION=output.txt");
        assertEquals(parser.getString("test_option"), "");
    }

    @Test
    public void add_test_caseSensitivity_3() {
        parser.add("test_option", "a", Parser.STRING);
        parser.parse("--TEST_OPTION=output.txt");
        assertEquals(parser.getString("TEST_OPTION"), "");
    }

    @Test
    public void add_test_caseSensitivity_4() {
        parser.add("TEST_OPTION", "a", Parser.STRING);
        parser.parse("--TEST_OPTION=output.txt");
        assertEquals(parser.getString("TEST_OPTION"), "output.txt");
    }

    @Test
    public void add_test_caseSensitivity_5() {
        parser.add("TeSt_OpTIoN", "a", Parser.STRING);
        parser.parse("--TeSt_OpTIoN=output.txt");
        assertEquals(parser.getString("TeSt_OpTIoN"), "output.txt");
    }

    @Test
    public void add_test_caseSensitivity_1_Shortcut() {
        parser.add("test_option", "A", Parser.STRING);
        parser.parse("-A=output.txt");
        assertEquals(parser.getString("a"), "");
    }

    @Test
    public void add_test_caseSensitivity_2_Shortcut() {
        parser.add("test_option", "a", Parser.STRING);
        parser.parse("-A=output.txt");
        assertEquals(parser.getString("a"), "");
    }

    @Test
    public void add_test_caseSensitivity_3_Shortcut() {
        parser.add("test_option", "a", Parser.STRING);
        parser.parse("-A=output.txt");
        assertEquals(parser.getString("A"), "");
    }

    @Test
    public void add_test_caseSensitivity_4_Shortcut() {
        parser.add("test_option", "A", Parser.STRING);
        parser.parse("-A=output.txt");
        assertEquals(parser.getString("A"), "output.txt");
    }

    @Test
    public void add_test_caseSensitivity_5_Shortcut() {
        parser.add("test_option", "aA", Parser.STRING);
        parser.parse("-aA=output.txt");
        assertEquals(parser.getString("aA"), "output.txt");
    }

    //3.4
    @Test
    public void add_test_optionEqualsShortcut_1() {
        parser.add("output", "o", Parser.STRING);
        parser.add("o", "a", Parser.STRING);
        parser.parse("--output=output.txt");
        assertEquals(parser.getString("output"), "output.txt");
    }

    @Test
    public void add_test_optionEqualsShortcut_2() {
        parser.add("output", "o", Parser.STRING);
        parser.add("o", "a", Parser.STRING);
        parser.parse("--o=output.txt");
        parser.parse("-o=output2.txt");
        assertEquals(parser.getString("o"), "output.txt");
    }

    @Test
    public void add_test_optionEqualsShortcut_3() {
        parser.add("output", "o", Parser.STRING);
        parser.add("o", "a", Parser.STRING);
        parser.parse("-o=output.txt");
        assertEquals(parser.getString("output"), "output.txt");
    }

    //3.5
    @Test
    public void add_test_BoolFalse_1() {
        parser.add("optimise", "o", Parser.BOOLEAN);
        parser.parse("--optimise=false");
        assertEquals(parser.getBoolean("optimise"), false);
    }

    @Test
    public void add_test_BoolFalse_2() {
        parser.add("optimise", "o", Parser.STRING);
        parser.parse("--optimise=0");
        assertEquals(parser.getBoolean("optim" + "ise"), false);
    }

    @Test
    public void add_test_BoolTrue_1() {
        parser.add("optimise", "O", Parser.BOOLEAN);
        parser.parse("-O");
        assertEquals(parser.getBoolean("optimise"), true);
    }

    @Test
    public void add_test_BoolTrue_2() {
        parser.add("optimise", "O", Parser.STRING);
        parser.parse("-O=true");
        assertEquals(parser.getBoolean("optimise"), true);
    }

    @Test
    public void add_test_BoolTrue_3() {
        parser.add("optimise", "O", Parser.BOOLEAN);
        parser.parse("-O 100");
        assertEquals(parser.getBoolean("O"), true);
    }

    @Test
    public void add_test_BoolTrue_4() {
        parser.add("optimise", "O", Parser.BOOLEAN);
        parser.parse("-O =1");
        assertEquals(parser.getBoolean("O"), true);
    }

    //4.1
    @Test
    public void add_test_no_short_sameName() {
        parser.add("test_option_1", Parser.STRING);
        parser.parse("--test_option_1=output.txt");
        parser.add("test_option_1", Parser.STRING);
        assertEquals(parser.getString("test_option_1"), "");
    }

    @Test
    public void add_test_no_short_sameNameFail() {
        parser.add("test_option_1", Parser.STRING);
        parser.parse("--test_option_1=output.txt");
        parser.add("test_option_1", Parser.STRING);
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
    public void add_test_no_short_runtimeExceptionFirstNum() {
        parser.add("1_test_option", Parser.STRING);
    }

    @Test(expected = RuntimeException.class)
    public void add_test_no_short_runtimeExceptionNonAlphaChar() {
        parser.add("test_option.", Parser.STRING);
    }

    //4.3
    @Test
    public void add_test_no_short_caseSensitivity_1() {
        parser.add("TEST_OPTION", Parser.STRING);
        parser.parse("--TEST_OPTION=output.txt");
        assertEquals(parser.getString("test_option"), "");
    }

    @Test
    public void add_test_no_short_caseSensitivity_2() {
        parser.add("test_option", Parser.STRING);
        parser.parse("--TEST_OPTION=output.txt");
        assertEquals(parser.getString("test_option"), "");
    }

    @Test
    public void add_test_no_short_caseSensitivity_3() {
        parser.add("test_option", Parser.STRING);
        parser.parse("--TEST_OPTION=output.txt");
        assertEquals(parser.getString("TEST_OPTION"), "");
    }

    @Test
    public void add_test_no_short_caseSensitivity_4() {
        parser.add("TEST_OPTION", Parser.STRING);
        parser.parse("--TEST_OPTION=output.txt");
        assertEquals(parser.getString("TEST_OPTION"), "output.txt");
    }

    //4.5
    @Test
    public void add_test_no_short_BoolFalse_1() {
        parser.add("optimise", Parser.BOOLEAN);
        parser.parse("--optimise=false");
        assertEquals(parser.getBoolean("optimise"), false);
    }

    @Test
    public void add_test_no_short_BoolFalse_2() {
        parser.add("optimise", Parser.BOOLEAN);
        parser.parse("--optimise=0");
        assertEquals(parser.getBoolean("optim" + "ise"), false);
    }

    @Test
    public void add_test_no_short_BoolTrue_1() {
        parser.add("optimise", Parser.BOOLEAN);
        parser.parse("--optimise");
        assertEquals(parser.getBoolean("optimise"), true);
    }

    @Test
    public void add_test_no_short_BoolTrue_2() {
        parser.add("optimise", Parser.BOOLEAN);
        parser.parse("--optimise=true");
        assertEquals(parser.getBoolean("optimise"), true);
    }

    @Test
    public void add_test_no_short_BoolTrue_3() {
        parser.add("optimise", Parser.BOOLEAN);
        parser.parse("--optimise 100");
        assertEquals(parser.getBoolean("optimise"), true);
    }

    @Test
    public void add_test_no_short_BoolTrue_4() {
        parser.add("optimise", Parser.BOOLEAN);
        parser.parse("--optimise =1");
        assertEquals(parser.getBoolean("optimise"), true);
    }

    //5.1 + 5.2 done already tested
    @Test
    public void parse_test_overwriting_option_by_reassigning_shortcut() {
        parser.add("input", "i", Parser.STRING);
        parser.parse("--input 1.txt");
        parser.parse("-i 2.txt");
        System.out.println(parser.toString());
        assertEquals(parser.getString("input"), "2.txt");
    }

    @Test
    public void parse_test_() {
        parser.add("input", Parser.STRING);
        parser.add("output", Parser.STRING);
        parser.parse("--input 1.txt --output=2.txt");
        assertEquals(parser.getString("input"), "1.txt");
    }

    //5.3
    @Test
    public void parse_test_equalsSpace_1() {
        parser.add("input", Parser.STRING);
        parser.parse("--input 1.txt");
        assertEquals(parser.getString("input"), "1.txt");
    }

    @Test
    public void parse_test_equalsSpace_2() {
        parser.add("input", Parser.STRING);
        parser.parse("--input=1.txt");
        assertEquals(parser.getString("input"), "1.txt");
    }

    //5.4
    @Test
    public void parse_test_quotes_1() {
        parser.add("input", Parser.STRING);
        parser.parse("--input='1.txt'");
        assertEquals(parser.getString("input"), "1.txt");
    }

    @Test
    public void parse_test_quotes_2() {
        parser.add("input", Parser.STRING);
        parser.parse("--input=\"1.txt\"");
        assertEquals(parser.getString("input"), "1.txt");
    }

    @Test
    public void parse_test_quotes_3() {
        parser.add("input", Parser.STRING);
        parser.parse("--input=1.txt");
        assertEquals(parser.getString("input"), "1.txt");
    }

    //5.5
    @Test
    public void parse_test_decorativequotes_1() {
        parser.add("option", Parser.STRING);
        parser.parse("--option='value=\"abc\"'");
        assertEquals(parser.getString("option"), "value=\"abc\"");
    }

    @Test
    public void parse_test_decorativequotes_2() {
        parser.add("option", Parser.STRING);
        parser.parse("--option=\"value='abc'\"");
        assertEquals(parser.getString("option"), "value='abc'");
    }

    //5.6
    @Test
    public void parse_test_multipleParseAssignments() {
        parser.add("option", Parser.STRING);
        parser.parse("--option=1.txt");
        parser.parse("--option=2.txt");
        assertEquals(parser.getString("option"), "2.txt");
    }

    @Test
    public void parse_test_multipleParseAssignments_2() {
        parser.add("option", Parser.STRING);
        parser.parse("--option=1.txt --option=3.txt --option=4.txt --option=5.txt --option=6.txt");
        assertEquals(parser.getString("option"), "6.txt");
    }

    //5.7
    @Test
    public void parse_test_noAssignment() {
        parser.add("option", Parser.STRING);
        assertEquals(parser.getString("option"), "");
    }

    //5.8
    @Test
    public void parse_test_multipleParses() {
        parser.add("option_1", Parser.STRING);
        parser.add("option_2", Parser.STRING);
        parser.add("option_3", Parser.STRING);
        parser.add("option_4", Parser.STRING);
        parser.add("option_5", Parser.STRING);
        parser.add("option_6", Parser.STRING);
        parser.parse("--option_1=1.txt --option_2=2.txt");
        parser.parse("--option_3=3.txt");
        parser.parse("--option_4=4.txt");
        parser.parse("--option_5=5.txt");
        parser.parse("--option_6=6.txt");
        assertEquals(parser.getString("option_2"), "2.txt");
    }

    //6.1
    @Test
    public void parse_test_nameFirstThenShortcut() {
        parser.add("option_1", "o", Parser.STRING);
        parser.add("option_2", "option_1", Parser.STRING);
        parser.parse("--option_1=Name_took_value");
        parser.parse("-option_1=Shortcut_took_value");
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
