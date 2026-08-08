package pageqwq.bilingualtooltips;

import java.util.HashMap;
import java.util.Map;

public final class TranslationMerger {
    private TranslationMerger() {
    }

    /**
     * Merges a base layer (en_us) with an override layer (the target language);
     * override entries win on key conflicts.
     */
    public static TranslationTable merge(Map<String, String> base, Map<String, String> override) {
        Map<String, String> merged = new HashMap<>();
        if (base != null) {
            merged.putAll(base);
        }
        if (override != null) {
            merged.putAll(override);
        }
        return TranslationTable.of(merged);
    }
}
