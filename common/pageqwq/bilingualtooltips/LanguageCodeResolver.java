package pageqwq.bilingualtooltips;

public final class LanguageCodeResolver {
    private LanguageCodeResolver() {
    }

    /**
     * Resolves the effective secondary language code. "auto" (or blank) picks the
     * "other" of Chinese/English relative to the client's current language.
     */
    public static String resolve(String configuredCode, String currentLanguage) {
        if (configuredCode == null || configuredCode.isBlank()
                || BilingualConstants.LANG_AUTO.equalsIgnoreCase(configuredCode.trim())) {
            String current = currentLanguage == null ? "" : currentLanguage.toLowerCase();
            return current.startsWith("zh") ? BilingualConstants.LANG_EN_US : BilingualConstants.LANG_ZH_CN;
        }
        return configuredCode.trim();
    }
}
