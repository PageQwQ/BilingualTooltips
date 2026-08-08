package pageqwq.bilingualtooltips;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

public final class ConfigCodec {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private ConfigCodec() {
    }

    public static BilingualTooltipsConfig load(Path file) {
        if (Files.isRegularFile(file)) {
            try (Reader reader = Files.newBufferedReader(file)) {
                BilingualTooltipsConfig config = GSON.fromJson(reader, BilingualTooltipsConfig.class);
                if (config != null) {
                    return config;
                }
            } catch (Exception ignored) {
                // corrupt file: fall through to defaults
            }
        }
        BilingualTooltipsConfig config = new BilingualTooltipsConfig();
        save(file, config);
        return config;
    }

    public static void save(Path file, BilingualTooltipsConfig config) {
        try {
            if (file.getParent() != null) {
                Files.createDirectories(file.getParent());
            }
            try (Writer writer = Files.newBufferedWriter(file)) {
                GSON.toJson(config, writer);
            }
        } catch (IOException ignored) {
        }
    }
}
