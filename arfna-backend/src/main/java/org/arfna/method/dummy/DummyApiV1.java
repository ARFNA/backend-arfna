package org.arfna.method.dummy;

import org.arfna.util.gson.GsonHelper;

import java.util.Arrays;
import java.util.stream.Collectors;

public class DummyApiV1 implements IDummyApi {

    private static final String VOWELS = "AEIOUaeiou";
    private static final String SPACE = " ";

    @Override
    public DummyResponse getResponse(String jsonPayload) {
        DummyPayload input = GsonHelper.getGson().fromJson(jsonPayload, DummyPayload.class);
        String originalMessage = input.getInputMessage();
        String pigLatin = generatePigLatinPhrase(originalMessage);
        return new DummyResponse()
                .withOriginalMessage(originalMessage)
                .withMessageInPigLatin(pigLatin);
    }

    private String generatePigLatinPhrase(String originalMessage) {
        String[] words = originalMessage.split(SPACE);
        return Arrays.stream(words).map(this::getPigLatinOfWord).collect(Collectors.joining(SPACE));
    }

    private String getPigLatinOfWord(String originalWord) {
        int indexOfFirstVowel = getIndexOfFirstVowel(originalWord);
        if (indexOfFirstVowel == -1) {
            return originalWord;
        }
        return originalWord.substring(indexOfFirstVowel) + originalWord.substring(0, indexOfFirstVowel) + "ay";

    }

    private int getIndexOfFirstVowel(String word) {
        int indexOfFirstVowel = -1;
        for (int i = 0; i < word.length(); i++) {
            if (isVowel(word.charAt(i))) {
                indexOfFirstVowel = i;
                break;
            }
        }
        return indexOfFirstVowel;
    }

    private boolean isVowel(char c) {
        return VOWELS.indexOf(c) != -1;
    }

}
