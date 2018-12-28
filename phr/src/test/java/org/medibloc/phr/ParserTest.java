package org.medibloc.phr;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ParserTest {
    @Test
    public void testParse() {
        assertEquals("Hello panacea!", Parser.parse());
    }
}
