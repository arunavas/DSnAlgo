package com.relcache.core.pocs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiFunction;

public class ListUtil {
    public static <A, B> B fold(List<A> list, B defaultValue, BiFunction<A, B, B> acc) {
        for (A a : list) {
            defaultValue = acc.apply(a, defaultValue);
        }
        return defaultValue;
    }

    public static void main(String[] args) {
        List<String> l = Arrays.asList("a", "ab", "abc");
        System.out.println(l);
        System.out.println(fold(l, new HashMap<>(), (a, b) -> {
            b.put(a.length(), a);
            return b;
        }));
    }
}
