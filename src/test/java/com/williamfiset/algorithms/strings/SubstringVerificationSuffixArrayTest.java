package com.williamfiset.algorithms.strings;

import static com.google.common.truth.Truth.assertThat;
import org.junit.Test;


public class SubstringVerificationSuffixArrayTest {
    @Test
    public void testIfStringContainsSubstring() {
        String pattern = "hello world";
        String text = "hello lemon Lennon wallet world tree cabbage hello world teapot calculator";
        SubstringVerificationSuffixArray.SuffixArray sa = new SubstringVerificationSuffixArray.SuffixArray(text);
        assertThat(sa.contains(pattern)).isTrue();
    }

    @Test
    public void testStringDoesNotContainSubString() {
        String pattern = "wallet tree";
        String text = "hello lemon Lennon wallet world tree cabbage hello world teapot calculator";
        SubstringVerificationSuffixArray.SuffixArray sa = new SubstringVerificationSuffixArray.SuffixArray(text);
        assertThat(sa.contains(pattern)).isFalse();
    }
}
