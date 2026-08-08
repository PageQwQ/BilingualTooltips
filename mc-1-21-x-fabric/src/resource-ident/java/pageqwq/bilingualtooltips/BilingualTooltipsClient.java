package pageqwq.bilingualtooltips;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

public class BilingualTooltipsClient implements ClientModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger(BilingualConstants.MOD_ID);

    private static BilingualTooltipsClient instance;

    private final Path configPath = FabricLoader.getInstance().getConfigDir().resolve(BilingualConstants.CONFIG_FILE);
    private BilingualTooltipsConfig config;

    @Override
    public void onInitializeClient() {
        instance = this;
        this.config = ConfigCodec.load(this.configPath);

        ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener(new SimpleSynchronousResourceReloadListener() {
            @Override
            public Identifier getFabricId() {
                return Identifier.fromNamespaceAndPath(BilingualConstants.MOD_ID, "translations");
            }

            @Override
            public void onResourceManagerReload(ResourceManager resourceManager) {
                TranslationHolder.reload(resourceManager);
            }
        });

        ItemTooltipCallback.EVENT.register((stack, context, flag, lines) -> TooltipAppender.append(stack, lines));
    }

    public static BilingualTooltipsConfig config() {
        return instance.config;
    }

    public static void saveConfig() {
        ConfigCodec.save(instance.configPath, instance.config);
        TranslationHolder.forceReload(Minecraft.getInstance().getResourceManager());
    }
}
