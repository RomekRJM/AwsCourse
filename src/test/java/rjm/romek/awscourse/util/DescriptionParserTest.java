package rjm.romek.awscourse.util;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

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
    public static final String S2 = "(*key)";
    public static final String S3 = "Create S3 bucket named (*bucket) and place file (key) in it.";
    public static final String S4 = "(*standard_storage=30)";

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {S1, 0, new DescriptionFragment("Do something", Boolean.FALSE), new String[0]},
                {S2, 0, new DescriptionFragment("key", Boolean.TRUE, Boolean.FALSE), new String[]{"key"}},
                {S3, 0, new DescriptionFragment("Create S3 bucket named ", Boolean.FALSE), new String[]{"bucket", "key"}},
                {S3, 1, new DescriptionFragment("bucket", Boolean.TRUE, Boolean.FALSE), new String[]{"bucket", "key"}},
                {S3, 2, new DescriptionFragment(" and place file ", Boolean.FALSE), new String[]{"bucket", "key"}},
                {S3, 3, new DescriptionFragment("key", Boolean.TRUE, Boolean.TRUE), new String[]{"bucket", "key"}},
                {S3, 4, new DescriptionFragment(" in it.", Boolean.FALSE), new String[]{"bucket", "key"}},
                {S4, 0, new DescriptionFragment("standard_storage", Boolean.TRUE, Boolean.FALSE, "30"), new String[]{"standard_storage"}},
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

    @Test
    public void extractParameterNamesShouldWork() {
        String[] actual = DescriptionParser.extractParameterNames(toParse);
        assertArrayEquals(parameterNames, actual);
    }
}