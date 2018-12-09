package rjm.romek.awscourse.util;

import java.util.Collections;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;

public class StringMapper {

    public static final String SPLIT_ON = ",";
    public static final String SEPARATOR = "=";

    public static Map<String, String> toMap(String s) {
        if (StringUtils.isBlank(s)) {
            return Collections.emptyMap();
        }

        return Splitter.on(SPLIT_ON).withKeyValueSeparator(SEPARATOR).split(s);
    }

    public static String toString(Map<String, String> m) {
        return Joiner.on(SPLIT_ON).withKeyValueSeparator(SEPARATOR).join(m);
    }
}
