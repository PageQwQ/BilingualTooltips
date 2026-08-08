package pageqwq.bilingualtooltips;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;

import java.io.BufferedReader;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class NeoForgeTranslationLoader {
    private static final Gson GSON = new Gson();
    private static final Type MAP_TYPE = new TypeToken<Map<String, String>>() {
    }.getType();

    private NeoForgeTranslationLoader() {
    }

    public static TranslationTable load(ResourceManager resourceManager, String languageCode) {
        Map<String, String> base = readLanguage(resourceManager, BilingualConstants.LANG_EN_US);
        Map<String, String> override = BilingualConstants.LANG_EN_US.equals(languageCode)
                ? Map.of()
                : readLanguage(resourceManager, languageCode);
        return TranslationMerger.merge(base, override);
    }

    private static Map<String, String> readLanguage(ResourceManager resourceManager, String code) {
        Map<String, String> result = new HashMap<>();
        for (String namespace : resourceManager.getNamespaces()) {
            ResourceLocation id = ResourceLocation.fromNamespaceAndPath(namespace, "lang/" + code + ".json");
            List<Resource> stack = resourceManager.getResourceStack(id);
            // apply lowest-priority pack first so higher-priority packs override
            for (int i = stack.size() - 1; i >= 0; i--) {
                Resource resource = stack.get(i);
                try (BufferedReader reader = resource.openAsReader()) {
                    Map<String, String> parsed = GSON.fromJson(reader, MAP_TYPE);
                    if (parsed != null) {
                        result.putAll(parsed);
                    }
                } catch (Exception e) {
                    BilingualTooltips.LOGGER.warn("Failed to parse language file {}", id, e);
                }
            }
        }
        return result;
    }
}
