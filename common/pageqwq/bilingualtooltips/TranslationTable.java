package pageqwq.bilingualtooltips;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class TranslationTable {
    private static final TranslationTable EMPTY = new TranslationTable(Collections.emptyMap());

    private final Map<String, String> map;

    private TranslationTable(Map<String, String> map) {
        this.map = map;
    }

    public static TranslationTable empty() {
        return EMPTY;
    }

    public static TranslationTable of(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return EMPTY;
        }
        return new TranslationTable(Collections.unmodifiableMap(new HashMap<>(map)));
    }

    public String get(String key) {
        return map.get(key);
    }

    public boolean isEmpty() {
        return map.isEmpty();
    }

    public int size() {
        return map.size();
    }
}
