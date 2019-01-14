package rjm.romek.awscourse.util;

public class InputFragment extends DescriptionFragment {
    public enum InputType implements DescriptionFragmentType {
        TEXT, HIDDEN, PASSWORD
    }

    public InputFragment(DescriptionFragmentType inputType, String text) {
        super(inputType, text, null);
    }

    public InputFragment(DescriptionFragmentType inputType, String text, String value) {
        super(inputType, text, value);
    }
}
