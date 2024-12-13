package shop.app;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.sql.DataSource;

@Testcontainers
@ContextConfiguration(classes = AbstractIntegrationTest.TestConfig.class)
public class AbstractIntegrationTest {
    @Container
    private static PostgreSQLContainer<?> POSTGRESQL_CONTAINER = new PostgreSQLContainer<>("postgres:12.10")
            .withDatabaseName("postgres-master")
            .withUsername("user")
            .withPassword("password");

    static class TestConfig {
        @Bean
        @Primary
        public DataSource dataSource() {
            var hikariConfig = new HikariConfig();
            hikariConfig.setJdbcUrl(POSTGRESQL_CONTAINER.getJdbcUrl());
            hikariConfig.setUsername(POSTGRESQL_CONTAINER.getUsername());
            hikariConfig.setPassword(POSTGRESQL_CONTAINER.getPassword());
            hikariConfig.setTransactionIsolation("TRANSACTION_READ_UNCOMMITTED");

            return new HikariDataSource(hikariConfig);
        }
    }
}
