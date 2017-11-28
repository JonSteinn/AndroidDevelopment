package is.ru.droid.testapp.util;

public final class BinaryConverter {
    private BinaryConverter() { }

    public static int toInt(String str) {
        if (isOfLength8(str) && isBinaryStrength(str)) {
            int sum = 0;
            for (int i = 0; i < 8; i++) {
                sum += numericCharToInt(str.charAt(7 - i)) * (1 << i);
            }
            return sum;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public static int numericCharToInt(char c) {
        return c - '0';
    }

    public static boolean isOfLength8(String str) {
        return str != null && str.length() == 8;
    }

    public static boolean isBinaryStrength(String str) {
        return str.matches("[01]+");
    }
}
