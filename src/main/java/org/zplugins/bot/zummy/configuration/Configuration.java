package org.zplugins.bot.zummy.configuration;

import lombok.SneakyThrows;
import org.simpleyaml.configuration.file.YamlFile;

import java.util.function.Consumer;

public class Configuration extends YamlFile {

    public Configuration() {
        super("configuration/config.yml");
    }

    @SneakyThrows
    public Configuration buildIfNotExists() {
        if (!this.exists()) this.createNewFile(false);
        else this.load();
        return this;
    }

    @SneakyThrows
    public Configuration addDefaults(Consumer<YamlFile> consumer) {
        consumer.accept(this);
        this.save();
        return this;
    }
}
