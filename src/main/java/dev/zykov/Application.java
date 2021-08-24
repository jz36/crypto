package dev.zykov;

import io.micronaut.runtime.Micronaut;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import jakarta.inject.Singleton;

public class Application {

    public static void main(String[] args) {
        Micronaut.run(Application.class, args);
    }

    @Singleton
    public ConnectionFactory connectionFactory() {
        ConnectionFactory connectionFactory = ConnectionFactories.get(ConnectionFactoryOptions.builder()
                .option(ConnectionFactoryOptions.HOST, "localhost")
                .option(ConnectionFactoryOptions.PORT, 5432)
                .option(ConnectionFactoryOptions.USER, "crypto")
                .option(ConnectionFactoryOptions.PASSWORD, "crypto")
                .option(ConnectionFactoryOptions.PROTOCOL, "postgresql")
                .option(ConnectionFactoryOptions.DRIVER, "pool")
                .build());
        return connectionFactory;
    }
}
