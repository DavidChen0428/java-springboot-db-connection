package com.david.project;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import java.util.Optional;

@SpringBootApplication
public class SpringDataJdbcApplication {

    private static final Logger logger = LoggerFactory.getLogger(SpringDataJdbcApplication.class);

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(SpringDataJdbcApplication.class);
        Environment env = app.run(args).getEnvironment();
        logApplicationStartup(env);
    }

    private static void logApplicationStartup(Environment env) {

        final String applicationName = env.getProperty("spring.application.name");
        final String serverPort = env.getProperty("server.port");
        final String protocol = Optional.ofNullable(env.getProperty("server.ssl.key-store")).map(key -> "https").orElse("http");
        final String contextPath = Optional.ofNullable(env.getProperty("server.servlet.context-path")).filter(path -> !path.isBlank()).orElse("/");
        final String baseUrl = protocol + "://localhost:" + serverPort + contextPath;

        logger.info("\n" +
                        "----------------------------------------------------------\n" +
                        "\tApplication '{}' is running! Access URLs:\n" +
                        "\tLocal:         {}\n" +
                        "\tH2 DB Console: {}h2-console\n" +
                        "\tSwaggerUI:     {}swagger-ui/index.html\n" +
                        "----------------------------------------------------------",
                applicationName, baseUrl, baseUrl, baseUrl);
    }

}
