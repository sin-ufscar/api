package br.ufscar.sin.api.config.database;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.flyway.FlywayDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;


@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "postgres2EntityManager",
        transactionManagerRef = "postgres2TransactionManager",
        basePackages = "br.ufscar.sin.api"
)
public class Postgres2DatabaseConfiguration {


    /**
     * PostgreSQL datasource definition.
     *
     * @return datasource.
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.producao-postgres.datasource")
    public DataSource postgresqlDataSource() {
        return DataSourceBuilder
                .create()
                .build();
    }

    /**
     * Entity manager definition.
     *
     * @param builder an EntityManagerFactoryBuilder.
     * @return LocalContainerEntityManagerFactoryBean.
     */
    @Bean(name = "postgres2EntityManager")
    public LocalContainerEntityManagerFactoryBean producaoPostgresEntityManager(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(postgresqlDataSource())
                .properties(hibernateProperties())
                .packages("br.ufscar.sin.api")
                .persistenceUnit("postgres2PU")
                .build();
    }

    @Bean(name = "postgres2TransactionManager")
    public PlatformTransactionManager producaoPostgresTransactionManager(@Qualifier("postgres2EntityManager") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    private Map<String, Object> hibernateProperties() {

        Resource resource = new ClassPathResource("hibernate.properties");

        try {
            Properties properties = PropertiesLoaderUtils.loadProperties(resource);
            return properties.entrySet().stream()
                    .collect(Collectors.toMap(
                            e -> e.getKey().toString(),
                            e -> e.getValue())
                    );
        } catch (IOException e) {
            return new HashMap<String, Object>();
        }
    }
}
