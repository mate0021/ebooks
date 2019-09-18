package rnd.mate00.ebooks.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import rnd.mate00.ebooks.model.Book;
import rnd.mate00.ebooks.repository.BookRepository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackageClasses = { Book.class, BookRepository.class},
        entityManagerFactoryRef = "bookEntityManagerFactory",
        transactionManagerRef = "bookTransactionManager"
)
public class DatabaseConfiguration {

    @Value("${spring.datasource.driver-class-name}")
    private String driver;

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String user;

    @Value("${spring.datasource.password}")
    private String pass;

    @Bean(name = "books.datasource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(pass);

        return dataSource;
    }

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean bookEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(dataSource())
                .packages(Book.class, BookRepository.class)
                .persistenceUnit("booksPU")
                .properties(getHibernateProperties())
                .build();
    }

    private Map<String, String> getHibernateProperties() {
        Map<String, String> properties = new HashMap<>();
        properties.put("hibernate.id.new_generator_mappings", "false");
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");

        return properties;
    }


    @Bean
    @Primary
    public JpaTransactionManager bookTransactionManager(EntityManagerFactoryBuilder builder) {
        JpaTransactionManager manager = new JpaTransactionManager();
        manager.setDataSource(dataSource());
        manager.setEntityManagerFactory(bookEntityManagerFactory(builder).getObject());

        return manager;
    }
}
