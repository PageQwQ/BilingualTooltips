package pageqwq.bilingualtooltips;

import net.minecraft.client.Minecraft;
import net.minecraft.server.packs.resources.ResourceManager;

public final class TranslationHolder {
    private static volatile TranslationTable table = TranslationTable.empty();
    private static volatile String loadedCode = null;

    private TranslationHolder() {
    }

    public static TranslationTable get() {
        return table;
    }

    public static void reload(ResourceManager resourceManager) {
        BilingualTooltipsConfig config = BilingualTooltips.config();
        String current = Minecraft.getInstance().options.languageCode;
        String target = LanguageCodeResolver.resolve(config.secondaryLanguage, current);
        if (target.equals(loadedCode)) {
            return;
        }
        table = NeoForgeTranslationLoader.load(resourceManager, target);
        loadedCode = target;
    }

    public static void forceReload(ResourceManager resourceManager) {
        loadedCode = null;
        reload(resourceManager);
    }
}
