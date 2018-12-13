package rjm.romek.awscourse.util;

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.Map;

import org.junit.Test;

import com.google.common.collect.ImmutableMap;

public class StringMapperTest {

    private final static Map<String, String> MAP = ImmutableMap.of("k1", "v1", "k2", "v2");
    private final static String TEST_STRING="k1=v1,k2=v2";

    @Test
    public void toMapShouldReturnEmptyMapOnBlankString() {
        assertEquals(Collections.emptyMap(), StringMapper.toMap(null));
        assertEquals(Collections.emptyMap(), StringMapper.toMap(""));
    }

    @Test
    public void toMapShouldReturnValidMapOnCorrectString() {
        Map<String, String> actual = StringMapper.toMap(TEST_STRING);
        assertEquals("v1", actual.get("k1"));
        assertEquals("v2", actual.get("k2"));
    }

    @Test
    public void toStringShouldReturnValidMap() {
        String actual = StringMapper.toString(MAP);
        assertEquals(TEST_STRING, actual);
    }
}