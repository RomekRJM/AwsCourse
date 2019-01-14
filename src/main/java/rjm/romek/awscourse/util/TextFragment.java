package rjm.romek.awscourse.util;

public class TextFragment extends DescriptionFragment {
    public enum TextType implements DescriptionFragmentType {
        TEXT, CODE
    }

    public TextFragment(DescriptionFragmentType textType, String text) {
        super(textType, text);
    }
}
