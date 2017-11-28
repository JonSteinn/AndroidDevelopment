package is.ru.droid.testapp.util;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Jonni on 11/28/2017.
 */
public class BinaryConverterTest {

    @Test
    public void foo() {
        assertEquals((1<<8)-1, BinaryConverter.toInt("11111111"));
    }

    @Test
    public void foo2() {
        assertFalse(BinaryConverter.isOfLength8("1001001"));
        assertTrue(BinaryConverter.isOfLength8("00010010"));
        assertFalse(BinaryConverter.isOfLength8("100100111"));
    }

    @Test
    public void foo3() {
        assertTrue(BinaryConverter.isBinaryStrength("1010101010100000100"));
        assertFalse(BinaryConverter.isBinaryStrength("123"));
    }

}