package rjm.romek.awscourse.util;

import org.apache.commons.lang3.StringUtils;

public class DescriptionFragment {

    private final String text;
    private final String value;
    private final DescriptionFragmentType type;

    public DescriptionFragment(DescriptionFragmentType type, String text) {
        this(type, text, null);
    }

    public DescriptionFragment(DescriptionFragmentType type, String text, String value) {
        this.text = text;
        this.value = value;
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public String getValue() {
        return value;
    }

    public String getTypeName() {
        return StringUtils.lowerCase(type.toString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DescriptionFragment that = (DescriptionFragment) o;

        if (text != null ? !text.equals(that.getText()) : that.getText() != null) return false;
        if (value != null ? !value.equals(that.getValue()) : that.getValue() != null) return false;
        return getTypeName() != null ? getTypeName().equals(that.getTypeName()) : that.getTypeName() == null;
    }
}
