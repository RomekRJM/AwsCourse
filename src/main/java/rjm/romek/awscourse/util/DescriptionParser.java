package rjm.romek.awscourse.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class DescriptionParser {

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

            if (c == BEGIN.charAt(0)) {
                inParamName = true;
                addIfNotEmptyAndClearStringBuilder(fragments, sb, Boolean.FALSE, Boolean.TRUE);
                visible = true;
            } else if (c == END.charAt(0)) {
                inParamName = false;
                addIfNotEmptyAndClearStringBuilder(fragments, sb, Boolean.TRUE, visible);
            } else if (c == INVISIBLE.charAt(0) && inParamName) {
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

    private static Map<String, Boolean> extractParameterNamesAndVisibility(String s) {
        String [] params = StringUtils.substringsBetween(s, BEGIN, END);

        if(params == null) {
            return null;
        }

        Map<String, Boolean> parameters = new LinkedHashMap<>();

        for (String p : params) {
            if(p.startsWith(INVISIBLE)) {
                parameters.put(p.substring(1), Boolean.FALSE);
            } else {
                parameters.put(p, Boolean.TRUE);
            }
        }

        return parameters;
    }
}
