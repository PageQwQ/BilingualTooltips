package pageqwq.bilingualtooltips;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.List;

final class TooltipAppender {
    private TooltipAppender() {
    }

    static void append(ItemStack stack, List<Component> lines) {
        BilingualTooltipsConfig config = BilingualTooltips.config();
        boolean shiftDown = Minecraft.getInstance().hasShiftDown();
        if (!ShouldShowPolicy.shouldShow(config, shiftDown) || lines.isEmpty()) {
            return;
        }

        String key = stack.getItem().getDescriptionId();
        String secondary = TranslationHolder.get().get(key);
        if (secondary == null || secondary.isEmpty()) {
            return;
        }

        String primary = lines.get(0).getString();
        if (config.onlyWhenDifferent && primary.equals(secondary)) {
            return;
        }

        Component line = Component.literal(secondary).withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC);
        if (config.position == BilingualTooltipsConfig.Position.BELOW_NAME) {
            lines.add(1, line);
        } else {
            lines.add(line);
        }
    }
}
