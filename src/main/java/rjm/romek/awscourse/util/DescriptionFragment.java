package rjm.romek.awscourse.util;

public class DescriptionFragment {

    private final String text;
    private final String value;
    private final Boolean input;
    private final Boolean visible;

    public DescriptionFragment(String text, Boolean input) {
        this(text, input, Boolean.TRUE);
    }

    public DescriptionFragment(String text, Boolean input, Boolean visible) {
        this(text, input, visible, null);
    }

    public DescriptionFragment(String text, Boolean input, Boolean visible, String value) {
        this.text = text;
        this.input = input;
        this.visible = visible;
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public Boolean getInput() {
        return input;
    }

    public String getValue() {
        return value;
    }

    public Boolean isVisible() {
        return visible;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DescriptionFragment that = (DescriptionFragment) o;

        if (text != null ? !text.equals(that.getText()) : that.getText() != null) return false;
        if (input != null ? !input.equals(that.getInput()) : that.getInput() != null) return false;
        if (value != null ? !value.equals(that.getValue()) : that.getValue() != null) return false;
        return visible != null ? visible.equals(that.isVisible()) : that.isVisible() == null;
    }
}
