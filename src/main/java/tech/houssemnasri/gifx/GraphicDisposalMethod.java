package tech.houssemnasri.gifx;

import java.util.Arrays;

public enum GraphicDisposalMethod {
    NO_DISPOSAL_SPECIFIED(0),
    DO_NOT_DISPOSE(1),
    RESTORE_TO_BACKGROUND_COLOR(2),
    RESTORE_YO_PREVIOUS(3);

    private final int value;

    GraphicDisposalMethod(int value) {
        this.value = value;
    }

    public static GraphicDisposalMethod parse(int disposalMethodValue) {
        return Arrays.stream(values())
                     .filter(disposalMethod -> disposalMethod.value == disposalMethodValue)
                     .findAny()
                     .orElseThrow(() -> new IllegalArgumentException("Invalid disposal method: " + disposalMethodValue));
    }
}
