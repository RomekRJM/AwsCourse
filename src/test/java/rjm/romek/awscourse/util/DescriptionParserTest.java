package rjm.romek.awscourse.util;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized.Parameters;

@RunWith(org.junit.runners.Parameterized.class)
public class DescriptionParserTest {

    private String toParse;
    private int number;
    private DescriptionFragment fragment;

    public DescriptionParserTest(String toParse, int number, DescriptionFragment fragment) {
        this.toParse = toParse;
        this.number = number;
        this.fragment = fragment;
    }

    public static final String S1 = "Do something";
    public static final String S2 = "{*key}";
    public static final String S3 = "Create S3 bucket named {*bucket} and place file {key} in it.";

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {S1, 0, new DescriptionFragment("Do something", Boolean.FALSE) },
                {S2, 0, new DescriptionFragment("key", Boolean.TRUE, Boolean.FALSE) },
                {S3, 0, new DescriptionFragment("Create S3 bucket named ", Boolean.FALSE) },
                {S3, 1, new DescriptionFragment("bucket", Boolean.TRUE, Boolean.FALSE) },
                {S3, 2, new DescriptionFragment(" and place file ", Boolean.FALSE) },
                {S3, 3, new DescriptionFragment("key", Boolean.TRUE, Boolean.TRUE) },
                {S3, 4, new DescriptionFragment(" in it.", Boolean.FALSE) },
        });
    }

    @Test
    public void testSingle() {
        List<DescriptionFragment> fragments = DescriptionParser.parseDescription(toParse);
        assertEquals(fragments.get(number), fragment);
    }
}