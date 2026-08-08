package pageqwq.bilingualtooltips;

public final class ShouldShowPolicy {
    private ShouldShowPolicy() {
    }

    public static boolean shouldShow(BilingualTooltipsConfig config, boolean shiftDown) {
        if (config == null || !config.enabled) {
            return false;
        }
        return config.showMode == BilingualTooltipsConfig.ShowMode.ALWAYS || shiftDown;
    }
}
