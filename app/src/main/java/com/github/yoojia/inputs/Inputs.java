package com.github.yoojia.inputs;

/**
 * @author Yoojia Chen (yoojiachen@gmail.com)
 * @since 1.3
 */
public class Inputs {

    public static Input fromString(final String fixedValue) {
        return new Input() {
            @Override
            public String getValue() {
                return fixedValue;
            }
        };
    }

    public static Input fromInt(int fixedValue) {
        return fromString(String.valueOf(fixedValue));
    }

    public static Input fromLong(long fixedValue) {
        return fromString(String.valueOf(fixedValue));
    }

    public static Input fromFloat(float fixedValue) {
        return fromString(String.valueOf(fixedValue));
    }

    public static Input fromDouble(double fixedValue) {
        return fromString(String.valueOf(fixedValue));
    }

    public static Input fromBool(boolean fixedValue) {
        return fromString(String.valueOf(fixedValue));
    }
}
