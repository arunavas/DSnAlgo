package ds.common;

import java.util.List;
import java.util.function.BiFunction;

public class ListUtil {

    public static <A, B> B fold(List<A> l, B initalValue, BiFunction<A, B, B> folder) {
        B acc = initalValue;
        for (A a : l) {
            acc = folder.apply(a, acc);
        }
        return acc;
    }
}
