package st;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class Parser {
    public static final int INTEGER = 1;
    public static final int BOOLEAN = 2;
    public static final int STRING = 3;
    public static final int CHAR = 4;

    private OptionMap optionMap;

    public Parser() {
        optionMap = new OptionMap();
    }

    public void add(String option_name, String shortcut, int value_type) {
        optionMap.store(option_name, shortcut, value_type);
    }

    public void add(String option_name, int value_type) {
        optionMap.store(option_name, "", value_type);
    }

    public int getInteger(String option) {
        String value = getString(option);
        int type = getType(option);
        int result;
        switch (type) {
            case INTEGER:
                try {
                    result = Integer.parseInt(value);
                } catch (Exception e) {
                    try {
                        new BigInteger(value);
                    } catch (Exception e1) {
                        result = 0;
                    }
                    result = 0;
                }
                break;
            case BOOLEAN:
                if (getBoolean(option) == false) {
                    result = 0;
                } else {
                    result = 1;
                }
                break;
            case STRING:
                int power = 1;
                result = 0;
                char c;
                for (int i = value.length() - 1; i >= 0; --i) {
                    c = value.charAt(i);
                    if (!Character.isDigit(c)) return 0;
                    result = result + power * (c - '0');
                    power *= 10;
                }
                break;
            case CHAR:
                result = (int) getChar(option);
                break;
            default:
                result = 0;
        }
        return result;
    }

    public boolean getBoolean(String option) {
        String value = getString(option);
        boolean result;
        if (value.toLowerCase().equals("false") || value.equals("0") || value.equals("")) {
            result = false;
        } else {
            result = true;
        }
        return result;
    }

    public String getString(String option) {
        String result = optionMap.getValue(option);
        return result;
    }

    public char getChar(String option) {
        String value = getString(option);
        char result;
        if (value.equals("")) {
            result = '\0';
        } else {
            result = value.charAt(0);
        }
        return result;
    }

    public int parse(String command_line_options) {
        if (command_line_options == null) {
            return -1;
        }
        int length = command_line_options.length();
        if (length == 0) {
            return -2;
        }

        int char_index = 0;
        while (char_index < length) {
            char current_char = command_line_options.charAt(char_index);

            while (char_index < length) {
                current_char = command_line_options.charAt(char_index);
                if (current_char != ' ') {
                    break;
                }
                char_index++;
            }

            boolean isShortcut = true;
            String option_name = "";
            String option_value = "";
            if (current_char == '-') {
                char_index++;
                current_char = command_line_options.charAt(char_index);
                if (current_char == '-') {
                    isShortcut = false;
                    char_index++;
                }
            } else {
                return -3;
            }
            current_char = command_line_options.charAt(char_index);

            while (char_index < length) {
                current_char = command_line_options.charAt(char_index);
                if (Character.isLetterOrDigit(current_char) || current_char == '_') {
                    option_name += current_char;
                    char_index++;
                } else {
                    break;
                }
            }

            boolean hasSpace = false;
            if (current_char == ' ') {
                hasSpace = true;
                while (char_index < length) {
                    current_char = command_line_options.charAt(char_index);
                    if (current_char != ' ') {
                        break;
                    }
                    char_index++;
                }
            }

            if (current_char == '=') {
                char_index++;
                current_char = command_line_options.charAt(char_index);
            }
            if ((current_char == '-' && hasSpace == true) || char_index == length) {
                if (getType(option_name) == BOOLEAN) {
                    option_value = "true";
                    if (isShortcut) {
                        optionMap.setValueWithOptioShortcut(option_name, option_value);
                    } else {
                        optionMap.setValueWithOptionName(option_name, option_value);
                    }
                } else {
                    return -3;
                }
                continue;
            } else {
                char end_symbol = ' ';
                current_char = command_line_options.charAt(char_index);
                if (current_char == '\'' || current_char == '\"') {
                    end_symbol = current_char;
                    char_index++;
                }
                while (char_index < length) {
                    current_char = command_line_options.charAt(char_index);
                    if (current_char != end_symbol) {
                        option_value = option_value + current_char;
                        char_index++;
                    } else {
                        break;
                    }
                }
            }

            if (isShortcut) {
                optionMap.setValueWithOptioShortcut(option_name, option_value);
            } else {
                optionMap.setValueWithOptionName(option_name, option_value);
            }
            char_index++;
        }
        return 0;
    }

    private int getType(String option) {
        int type = optionMap.getType(option);
        return type;
    }

    @Override
    public String toString() {
        return optionMap.toString();
    }

    public List<Integer> getIntegerList(String option) {
        List<Integer> result = new ArrayList<>();
        String values = getString(option);

        if (option == null || option.length() == 0) {
            return result;
        }

        String[] optionList = values.trim().split("[^0-9|^-]\\s*");

        for (String optionValue : optionList) {
            try {
                result.add(Integer.parseInt(optionValue));
            } catch (Exception e) {
                result.addAll(parseList(optionValue));
            }
        }
        Collections.sort(result);
        return result;
    }

    private List<Integer> parseList(String value) {
        List<Integer> result = new ArrayList<>();
        int length = value.length();
        if (value.length() == 0) return result;

        if (Pattern.matches("[0-9]*-[0-9]*", value)) {
            System.out.print("The value: " + value + " matches []-[]" + "\n");
            String[] valueList = value.split("-");
            int firstValue = Integer.parseInt(valueList[0]);
            int secondValue = Integer.parseInt(valueList[1]);
            for (int i = Math.min(firstValue, secondValue); i <= Math.max(firstValue, secondValue); i++) {
                result.add(i);
            }

        } else if (Pattern.matches("-[0-9]*-[0-9]*", value)) {
            System.out.print("The value: " + value + " matches -[]-[]" + "\n");
            int numberOfHyphens = 0;
            int i = 0;
            for (i = 0; i < length && numberOfHyphens != 2; i++) {
                if (value.charAt(i) == '-') {
                    numberOfHyphens++;
                }
            }
            int firstParsedValue = Integer.parseInt(value.substring(0, i - 1));
            int secondParsedValue = Integer.parseInt(value.substring(i, length));
            System.out.println("First: " + firstParsedValue + " and Second: " + secondParsedValue);

            for (i = Math.min(firstParsedValue, secondParsedValue); i <= Math.max(firstParsedValue, secondParsedValue); i++) {
                result.add(i);
            }


        } else if (Pattern.matches("[0-9]*--[0-9]*", value)) {
            System.out.print("The value: " + value + " matches []--[]" + "\n");
            int numberOfHyphens = 0;
            int i = 0;
            for (i = 0; i < length && numberOfHyphens != 1; i++) {
                if (value.charAt(i) == '-') {
                    numberOfHyphens++;
                }
            }
            int firstParsedValue = Integer.parseInt(value.substring(0, i - 1));
            int secondParsedValue = Integer.parseInt(value.substring(i, length));
            System.out.println("First: " + firstParsedValue + " and Second: " + secondParsedValue);

            for (i = Math.min(firstParsedValue, secondParsedValue); i <= Math.max(firstParsedValue, secondParsedValue); i++) {
                result.add(i);
            }

        } else if (Pattern.matches("-[0-9]*--[0-9]*", value)) {
            System.out.print("The value: " + value + " matches -[]--[]" + "\n");
            int numberOfHyphens = 0;
            int i = 0;
            for (i = 0; i < length && numberOfHyphens != 2; i++) {
                if (value.charAt(i) == '-') {
                    numberOfHyphens++;
                }
            }
            int firstParsedValue = Integer.parseInt(value.substring(0, i - 1));
            int secondParsedValue = Integer.parseInt(value.substring(i, length));
            System.out.println("First: " + firstParsedValue + " and Second: " + secondParsedValue);
            for (i = Math.min(firstParsedValue, secondParsedValue); i <= Math.max(firstParsedValue, secondParsedValue); i++) {
                result.add(i);
            }

        } else {
            System.out.print("No valid match was found for:" + value + "\n");

        }
        return result;
    }
}

