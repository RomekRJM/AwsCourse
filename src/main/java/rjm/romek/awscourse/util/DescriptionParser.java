package rjm.romek.awscourse.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DescriptionParser {

    private static final char CBEGIN = '(';
    private static final char CEND = ')';
    private static final char CINVISIBLE = '*';
    private static final String END = ")";
    private static final String VALUE = "=";

    public static List<DescriptionFragment> parseDescription(final String description) {
        StringBuilder sb = new StringBuilder();
        boolean inParamName = false;
        boolean visible = true;
        boolean append;
        List<DescriptionFragment> fragments = new ArrayList<>();

        for (char c : description.toCharArray()) {
            append = false;

            if (c == CBEGIN) {
                addIfNotEmptyAndClearStringBuilder(fragments, sb, Boolean.FALSE, Boolean.TRUE);
                inParamName = true;
                visible = true;
            } else if (c == CEND) {
                addIfNotEmptyAndClearStringBuilder(fragments, sb, Boolean.TRUE, visible);
                inParamName = false;
                visible = true;
            } else if (c == CINVISIBLE && inParamName) {
                visible = false;
            } else {
                append = true;
            }

            if (append) {
                sb.append(c);
            }
        }

        addIfNotEmptyAndClearStringBuilder(fragments, sb, sb.toString().endsWith(END), visible);

        return fragments;
    }

    private static void addIfNotEmptyAndClearStringBuilder(List<DescriptionFragment> fragments,
                                                           StringBuilder sb, Boolean input, Boolean visible) {
        if (sb.length() > 0) {
            String str = sb.toString();

            if (input && str.contains(VALUE)) {
                String[] keyValue = str.split(VALUE);
                fragments.add(new DescriptionFragment(keyValue[0], input, visible, keyValue[1]));
            } else {
                fragments.add(new DescriptionFragment(str, input, visible));
            }

        }

        sb.delete(0, sb.length());
    }

    public static Map<String, String> extractParameters(final String description) {
        Map<String, String> parameters = new LinkedHashMap();
        DescriptionParser.parseDescription(description).stream()
                .filter(x -> x.getInput())
                .forEach(x ->parameters.put(x.getText(), x.getValue()));
        return parameters;
    }

}
