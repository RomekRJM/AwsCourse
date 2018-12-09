package rjm.romek.awscourse.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class DescriptionParser {

    private static final char CBEGIN = '{';
    private static final char CEND = '}';
    private static final char CINVISIBLE = '*';
    private static final String BEGIN = "{";
    private static final String END = "}";
    private static final String INVISIBLE = "*";

    public static List<DescriptionFragment> parseDescription(final String description) {
        StringBuilder sb = new StringBuilder();
        boolean inParamName = false;
        boolean visible = true;
        boolean append;
        List<DescriptionFragment> fragments = new ArrayList<>();

        for(char c : description.toCharArray()) {
            append = false;

            if (c == CBEGIN) {
                inParamName = true;
                addIfNotEmptyAndClearStringBuilder(fragments, sb, Boolean.FALSE, Boolean.TRUE);
                visible = true;
            } else if (c == CEND) {
                inParamName = false;
                addIfNotEmptyAndClearStringBuilder(fragments, sb, Boolean.TRUE, visible);
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

    private static void addIfNotEmptyAndClearStringBuilder(List<DescriptionFragment> fragments, StringBuilder sb, Boolean input, Boolean visible) {
        if (sb.length() > 0) {
            fragments.add(new DescriptionFragment(sb.toString(), input, visible));
        }

        sb.delete(0, sb.length());
    }

    public static String [] extractParameterNames(String s) {
        return StringUtils.substringsBetween(s, BEGIN, END);
    }
}
