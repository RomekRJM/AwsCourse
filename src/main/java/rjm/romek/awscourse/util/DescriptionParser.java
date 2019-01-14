package rjm.romek.awscourse.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DescriptionParser {

    private static final char CBEGIN = '(';
    private static final char CEND = ')';
    private static final char CINVISIBLE = '*';
    private static final char CSECRET = '!';
    private static final char CCODEBEGIN = '{';
    private static final char CCODEEND = '}';
    private static final String END = ")";

    private static final DescriptionFragmentFactory factory = new DescriptionFragmentFactory();

    public static List<DescriptionFragment> parseDescription(final String description) {
        StringBuilder sb = new StringBuilder();
        boolean inParamName = false;
        boolean append;
        List<DescriptionFragment> fragments = new ArrayList<>();
        DescriptionFragmentType type = TextFragment.TextType.TEXT;

        for (char c : description.toCharArray()) {
            append = false;

            if (c == CBEGIN) {
                addIfNotEmptyAndClearStringBuilder(fragments, sb, type);
                type = InputFragment.InputType.TEXT;
                inParamName = true;
            } else if (c == CEND) {
                addIfNotEmptyAndClearStringBuilder(fragments, sb, type);
                type = TextFragment.TextType.TEXT;
                inParamName = false;
            } else if (c == CCODEBEGIN) {
                addIfNotEmptyAndClearStringBuilder(fragments, sb, type);
                type = TextFragment.TextType.CODE;
            } else if (c == CCODEEND) {
                addIfNotEmptyAndClearStringBuilder(fragments, sb, type);
                type = TextFragment.TextType.TEXT;
            } else if (c == CINVISIBLE && inParamName) {
                type = InputFragment.InputType.HIDDEN;
            } else if (c == CSECRET && inParamName) {
                type = InputFragment.InputType.PASSWORD;
            } else {
                append = true;
            }

            if (append) {
                sb.append(c);
            }
        }

        type = sb.toString().endsWith(END) ? type : TextFragment.TextType.TEXT;
        addIfNotEmptyAndClearStringBuilder(fragments, sb, type);

        return fragments;
    }

    private static void addIfNotEmptyAndClearStringBuilder(List<DescriptionFragment> fragments, StringBuilder sb,
                                                           DescriptionFragmentType fragmentType) {
        if (sb.length() > 0) {
            DescriptionFragment fragment = factory.create(fragmentType, sb.toString());
            fragments.add(fragment);
        }

        sb.delete(0, sb.length());
    }

    public static Map<String, String> extractParameters(final String description) {
        Map<String, String> parameters = new LinkedHashMap();
        DescriptionParser.parseDescription(description).stream()
                .filter(x -> x instanceof InputFragment)
                .forEach(x ->parameters.put(x.getText(), x.getValue()));
        return parameters;
    }

}
