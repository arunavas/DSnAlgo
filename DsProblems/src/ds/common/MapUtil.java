package ds.common;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

public class MapUtil {

    public static <K, V, V_> Map<K, V_> mapValues(Map<K, V> map, Function<V, V_> valueMapper) {
        return map(map, Function.identity(), valueMapper);
    }

    public static <K, V, K_> Map<K_, V> mapKeys(Map<K, V> map, Function<K, K_> keyMapper) {
        return map(map, keyMapper, Function.identity());
    }

    public static <K, V, K_, V_> Map<K_, V_> map(Map<K, V> val, Function<K, K_> keyMapper, Function<V, V_> valueMapper) {
        return fold(val, Collections.emptyMap(), (e, m) -> {
            m.put(keyMapper.apply(e.getKey()), valueMapper.apply(e.getValue()));
            return m;
        });
    }

    public static <K, V> Map<K, V> filter(Map<K, V> map, Function<Map.Entry<K, V>, Boolean> condition) {
        return fold(map, Collections.emptyMap(), (e, m) -> {
            if (condition.apply(e)) {
                m.put(e.getKey(), e.getValue());
            }
            return m;
        });
    }

    public static <K, V, R> R fold(Map<K, V> map, R defaultValue, BiFunction<Map.Entry<K, V>, R, R> accF) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            defaultValue = accF.apply(entry, defaultValue);
        }
        return defaultValue;
    }

    public static <K, V> boolean any(Map<K, V> map, Function<Map.Entry<K, V>, Boolean> cond) {
        boolean res = false;
        Iterator<Map.Entry<K, V>> iter = map.entrySet().iterator();
        Map.Entry<K, V> entry;
        while (iter.hasNext() && !res) {
            entry = iter.next();
            res = cond.apply(entry);
        }
        return res;
    }

    public static <K, V> boolean all(Map<K, V> map, Function<Map.Entry<K, V>, Boolean> cond) {
        return any(map, cond.andThen(x -> !x));
    }

}
