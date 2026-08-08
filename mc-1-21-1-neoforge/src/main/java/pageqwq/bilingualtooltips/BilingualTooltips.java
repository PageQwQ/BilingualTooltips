package pageqwq.bilingualtooltips;

import net.minecraft.client.Minecraft;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.function.Supplier;

@Mod(value = BilingualConstants.MOD_ID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = BilingualConstants.MOD_ID, value = Dist.CLIENT)
public class BilingualTooltips {
    public static final Logger LOGGER = LoggerFactory.getLogger(BilingualConstants.MOD_ID);

    private static final Path CONFIG_PATH = FMLPaths.CONFIGDIR.get().resolve(BilingualConstants.CONFIG_FILE);
    private static BilingualTooltipsConfig config;

    public BilingualTooltips(ModContainer container) {
        config = ConfigCodec.load(CONFIG_PATH);
        container.registerExtensionPoint(IConfigScreenFactory.class,
                (Supplier<IConfigScreenFactory>) () -> (modContainer, parent) -> BilingualConfigScreen.create(parent, config, cfg -> saveConfig()));
    }

    public static BilingualTooltipsConfig config() {
        return config;
    }

    private static void saveConfig() {
        ConfigCodec.save(CONFIG_PATH, config);
        TranslationHolder.forceReload(Minecraft.getInstance().getResourceManager());
    }

    @SubscribeEvent
    static void onItemTooltip(ItemTooltipEvent event) {
        TooltipAppender.append(event.getItemStack(), event.getToolTip());
    }

    @SubscribeEvent
    static void onRegisterClientReloadListeners(RegisterClientReloadListenersEvent event) {
        event.registerReloadListener((ResourceManagerReloadListener) TranslationHolder::reload);
    }
}
