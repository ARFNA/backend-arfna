package org.arfna.api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArfnaUtilityTest {

    @Test
    public void testDummyResponse() {
        ArfnaUtility client = new ArfnaUtility();
        String response = client.getDummyResponse("{\n" +
                "  \"version\": \"V1\",\n" +
                "  \"inputMessage\": \"Hi, this is my message\"\n" +
                "}");
        String expected = "{\"originalMessage\":\"Hi, this is my message\",\"messageInPigLatin\":\"i,Hay isthay isay my essagemay\"}";
        assertEquals(expected, response);
    }

}
