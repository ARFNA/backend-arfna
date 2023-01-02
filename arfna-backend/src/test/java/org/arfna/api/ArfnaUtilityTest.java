package org.arfna.api;

import org.arfna.method.common.MethodResponse;
import org.arfna.util.gson.GsonHelper;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArfnaUtilityTest {

    @Test
    @Ignore
    public void testDummyResponse() {
        ArfnaUtility client = new ArfnaUtility();
        MethodResponse response = client.getDummyResponse("{\n" +
                "  \"version\": \"V1\",\n" +
                "  \"inputMessage\": \"Hi, this is my message\"\n" +
                "}");
        String expected = "{\"originalMessage\":\"Hi, this is my message\",\"messageInPigLatin\":\"i,Hay isthay isay my essagemay\"}";
        assertEquals(expected, GsonHelper.getGson().toJson(response));
    }

}
