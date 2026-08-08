package pageqwq.bilingualtooltips;

public class BilingualTooltipsConfig {
    public boolean enabled = true;
    public String secondaryLanguage = BilingualConstants.LANG_AUTO;
    public ShowMode showMode = ShowMode.ALWAYS;
    public Position position = Position.BELOW_NAME;
    public boolean onlyWhenDifferent = true;

    public enum ShowMode {
        ALWAYS,
        HOLD_SHIFT
    }

    public enum Position {
        BELOW_NAME,
        END
    }

    public BilingualTooltipsConfig copy() {
        BilingualTooltipsConfig copy = new BilingualTooltipsConfig();
        copy.enabled = this.enabled;
        copy.secondaryLanguage = this.secondaryLanguage;
        copy.showMode = this.showMode;
        copy.position = this.position;
        copy.onlyWhenDifferent = this.onlyWhenDifferent;
        return copy;
    }
}
