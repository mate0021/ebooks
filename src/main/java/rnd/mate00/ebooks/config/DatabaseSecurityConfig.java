package rnd.mate00.ebooks.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import rnd.mate00.ebooks.sec.RoleRepository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(
        basePackageClasses = RoleRepository.class,
        entityManagerFactoryRef = "securityEntityManagerFactory",
        transactionManagerRef = "securityTransactionManager"
)
public class DatabaseSecurityConfig {

    @Value("${security.datasource.driver-class-name}")
    private String driver;

    @Value("${security.datasource.url}")
    private String url;

    @Value("${security.datasource.username}")
    private String user;

    @Value("${security.datasource.password}")
    private String pass;


    @Bean(name = "security.datasource")
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(pass);

        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean securityEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(dataSource())
                .packages(RoleRepository.class)
                .persistenceUnit("securityPU")
                .properties(getHibernateProperties())
                .build();
    }

    @Bean
    public JpaTransactionManager securityTransactionManager(EntityManagerFactoryBuilder builder) {
        JpaTransactionManager manager = new JpaTransactionManager();
        manager.setDataSource(dataSource());
        manager.setEntityManagerFactory(securityEntityManagerFactory(builder).getObject());

        return manager;
    }

    private Map<String, String> getHibernateProperties() {
        Map<String, String> properties = new HashMap<>();
        properties.put("hibernate.id.new_generator_mappings", "false");
        properties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");

        return properties;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
