package pageqwq.bilingualtooltips;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.function.Consumer;

public final class BilingualConfigScreen {
    private BilingualConfigScreen() {
    }

    public static Screen create(Screen parent, BilingualTooltipsConfig config, Consumer<BilingualTooltipsConfig> onSave) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Component.translatable("bilingualtooltips.config.title"))
                .setSavingRunnable(() -> onSave.accept(config));

        ConfigEntryBuilder entries = builder.entryBuilder();
        ConfigCategory general = builder.getOrCreateCategory(Component.translatable("bilingualtooltips.config.category.general"));

        general.addEntry(entries.startBooleanToggle(Component.translatable("bilingualtooltips.config.enabled"), config.enabled)
                .setDefaultValue(true)
                .setSaveConsumer(value -> config.enabled = value)
                .build());

        general.addEntry(entries.startStrField(Component.translatable("bilingualtooltips.config.secondary_language"), config.secondaryLanguage)
                .setDefaultValue(BilingualConstants.LANG_AUTO)
                .setTooltip(Component.translatable("bilingualtooltips.config.secondary_language.tooltip"))
                .setSaveConsumer(value -> config.secondaryLanguage = value)
                .build());

        general.addEntry(entries.startEnumSelector(Component.translatable("bilingualtooltips.config.show_mode"),
                        BilingualTooltipsConfig.ShowMode.class, config.showMode)
                .setDefaultValue(BilingualTooltipsConfig.ShowMode.ALWAYS)
                .setEnumNameProvider(value -> Component.translatable("bilingualtooltips.config.show_mode." + value.name().toLowerCase()))
                .setSaveConsumer(value -> config.showMode = value)
                .build());

        general.addEntry(entries.startEnumSelector(Component.translatable("bilingualtooltips.config.position"),
                        BilingualTooltipsConfig.Position.class, config.position)
                .setDefaultValue(BilingualTooltipsConfig.Position.BELOW_NAME)
                .setEnumNameProvider(value -> Component.translatable("bilingualtooltips.config.position." + value.name().toLowerCase()))
                .setSaveConsumer(value -> config.position = value)
                .build());

        general.addEntry(entries.startBooleanToggle(Component.translatable("bilingualtooltips.config.only_when_different"), config.onlyWhenDifferent)
                .setDefaultValue(true)
                .setTooltip(Component.translatable("bilingualtooltips.config.only_when_different.tooltip"))
                .setSaveConsumer(value -> config.onlyWhenDifferent = value)
                .build());

        return builder.build();
    }
}
