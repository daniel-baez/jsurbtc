package cl.daplay.jsurbtc;

import java.io.IOException;
import java.util.Properties;
import java.util.function.Supplier;

enum VersionSupplier implements Supplier<String> {
    INSTANCE;

    private static Properties getProperties() {
        final Properties properties = new Properties();

        try {
            properties.load(VersionSupplier.class.getResourceAsStream("/version.properties"));
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }

        return properties;
    }

    private static String getVersion(final Properties properties) {
        final String version = properties.getProperty("version", null);
        if (version == null || version.isEmpty()) {
            throw new RuntimeException("Invalid version, check generation for file /version.properties");
        }

        return version;
    }

    private final String version;

    VersionSupplier() {
        version = getVersion(getProperties());
    }

    @Override
    public String get() {
        return version;
    }

}
