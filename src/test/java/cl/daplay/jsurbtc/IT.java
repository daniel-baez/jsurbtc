package cl.daplay.jsurbtc;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Optional;
import java.util.Properties;
import java.util.function.LongSupplier;

abstract class IT {

    /**
     * @return get gradle configuration file $HOME/.gradle/gradle.properties
     */
    final Optional<Properties> getGradleProperties() {
        return Optional.ofNullable(System.getProperty("user.home"))
                .filter(string -> !string.isEmpty())
                .map(string -> new File(string))
                .filter(userHome -> userHome.exists() && userHome.isDirectory())
                .map(userHome -> new File(userHome, ".gradle/"))
                .filter(gradleHome -> gradleHome.exists() && gradleHome.isDirectory())
                .map(gradleHome -> new File(gradleHome, "gradle.properties"))
                .filter(gradleProperties -> gradleProperties.exists() && gradleProperties.isFile())
                .map(gradleProperties -> {
                    try {
                        final Properties properties = new Properties();
                        properties.load(new FileReader(gradleProperties));
                        return properties;
                    } catch (IOException e) {
                        return null;
                    }
                });
    }

    /**
     *
     * @return
     */
    final JSurbtc newClient() {
        final Optional<Properties> maybeGradleProperties = getGradleProperties();

        if (!maybeGradleProperties.isPresent()) {
            final String message = "Please provide properties `jsurbtc.key`, and `jsurbtc.secret` in file $HOME/.gradle/gradle.properties";
            throw new IllegalStateException(message);
        }

        final Properties properties = maybeGradleProperties.get();

        final String key = properties.getProperty("jsurbtc.key", "");
        final String secret = properties.getProperty("jsurbtc.secret", "");

        if (key.isEmpty() || secret.isEmpty()) {
            final String message = "Please provide properties `jsurbtc.key`, and `jsurbtc.secret` in file $HOME/.gradle/gradle.properties";
            throw new IllegalStateException(message);
        }

        final String proxyHost = properties.getProperty("jsurbtc.proxy.host", "");
        final String proxyPort = properties.getProperty("jsurbtc.proxy.port", "");

        InetSocketAddress proxy = null;

        if (!proxyHost.isEmpty() && !proxyPort.isEmpty() && proxyPort.matches("^\\d+$")) {
            proxy = new InetSocketAddress(proxyHost, Integer.parseInt(proxyPort));
        }

        final LongSupplier delegate = JSurbtc.newNonce();
        final LongSupplier nonce = () -> {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return delegate.getAsLong();
        };


        return new JSurbtcImpl(key, secret, nonce, proxy);
    }
}
