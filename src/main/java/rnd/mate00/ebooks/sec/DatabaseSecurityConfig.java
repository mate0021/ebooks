package rnd.mate00.ebooks.sec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
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
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
