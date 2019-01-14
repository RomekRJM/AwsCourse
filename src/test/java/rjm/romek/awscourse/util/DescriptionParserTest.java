package rjm.romek.awscourse.util;

import static org.junit.Assert.assertEquals;
import static rjm.romek.awscourse.util.InputFragment.InputType;
import static rjm.romek.awscourse.util.TextFragment.TextType;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized.Parameters;

@RunWith(org.junit.runners.Parameterized.class)
public class DescriptionParserTest {

    private String toParse;
    private int number;
    private DescriptionFragment fragment;
    private String[] parameterNames;

    public DescriptionParserTest(String toParse, int number, DescriptionFragment fragment, String[] parameterNames) {
        this.toParse = toParse;
        this.number = number;
        this.fragment = fragment;
        this.parameterNames = parameterNames;
    }

    public static final String S1 = "Do something";
    public static final String S2 = "(!password)";
    public static final String S3 = "Create S3 bucket named (*bucket) and place file (key) in it.";
    public static final String S4 = "(*standard_storage=30)";
    public static final String S5 = "{this is code}";

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {S1, 0, new TextFragment(TextType.TEXT, "Do something"), new String[0]},
                {S2, 0, new InputFragment(InputType.PASSWORD, "password"), new String[]{"password"}},
                {S3, 0, new TextFragment(TextType.TEXT, "Create S3 bucket named "), new String[]{"bucket", "key"}},
                {S3, 1, new InputFragment(InputType.HIDDEN, "bucket"), new String[]{"bucket", "key"}},
                {S3, 2, new TextFragment(TextType.TEXT, " and place file "), new String[]{"bucket", "key"}},
                {S3, 3, new InputFragment(InputType.TEXT, "key"), new String[]{"bucket", "key"}},
                {S3, 4, new TextFragment(TextType.TEXT, " in it."), new String[]{"bucket", "key"}},
                {S4, 0, new InputFragment(InputType.HIDDEN,"standard_storage", "30"), new String[]{"standard_storage"}},
                {S5, 0, new TextFragment(TextType.CODE, "this is code"), new String[0]},
        });
    }

    @Test
    public void testSingle() {
        List<DescriptionFragment> fragments = DescriptionParser.parseDescription(toParse);
        assertEquals(fragments.get(number), fragment);
    }

    @Test
    public void extractParametersShouldWork() {
        Map<String, String> parameters = DescriptionParser.extractParameters(toParse);
        assertEquals(parameterNames.length,
                Arrays.stream(parameterNames).filter(x -> parameters.containsKey(x)).count());
    }
}