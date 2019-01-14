package rjm.romek.awscourse.util;

public class DescriptionFragmentFactory {
    private static final String VALUE = "=";

    public DescriptionFragment create(DescriptionFragmentType fragmentType, String text) {
        if (fragmentType instanceof InputFragment.InputType) {

            if (text.contains(VALUE)) {
                String[] pair = text.split(VALUE);
                return new InputFragment(fragmentType, pair[0], pair[1]);
            } else {
                return new InputFragment(fragmentType, text);
            }

        }

        return new TextFragment(fragmentType, text);
    }
}
