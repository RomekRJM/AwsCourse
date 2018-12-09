package rjm.romek.awscourse.util;

public class DescriptionFragment {

    private final String text;
    private final Boolean input;
    private final Boolean visible;

    public DescriptionFragment(String text, Boolean input) {
        this(text, input, Boolean.TRUE);
    }

    public DescriptionFragment(String text, Boolean input, Boolean visible) {
        this.text = text;
        this.input = input;
        this.visible = visible;
    }

    public String getText() {
        return text;
    }

    public Boolean getInput() {
        return input;
    }

    public Boolean isVisible() {
        return visible;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DescriptionFragment that = (DescriptionFragment) o;

        if (text != null ? !text.equals(that.text) : that.text != null) return false;
        if (input != null ? !input.equals(that.input) : that.input != null) return false;
        return visible != null ? visible.equals(that.visible) : that.visible == null;
    }
}
