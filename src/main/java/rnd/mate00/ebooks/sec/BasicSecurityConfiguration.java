package rnd.mate00.ebooks.sec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity // <- needed to app startup
public class BasicSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final String AUTHORITIES_QUERY =
            "select username, role_name " +
                    "from users, users_roles, role " +
                    "where users.id = users_id and roles_id = role.id and username = ?";

    private DataSource dataSource;
    private BCryptPasswordEncoder encoder;

    @Autowired
    public BasicSecurityConfiguration(@Qualifier("security.datasource") DataSource dataSource, BCryptPasswordEncoder encoder) {
        this.dataSource = dataSource;
        this.encoder = encoder;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
//                .authorizeRequests().antMatchers("/").permitAll()
//                .and()
                .authorizeRequests().antMatchers("/h2-console/**").permitAll()
                .and()
                .authorizeRequests().antMatchers("/themes").permitAll()
                .and()
                .authorizeRequests().antMatchers("/shops").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().and().httpBasic()
                .and()
                .csrf().disable()
                .headers().frameOptions().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
//                .inMemoryAuthentication()
//                .withUser("reader")
//                .password("{noop}pass")
//                .roles("READER", "ADMIN");
        .jdbcAuthentication()
                .authoritiesByUsernameQuery(AUTHORITIES_QUERY)
                .dataSource(dataSource)
                .passwordEncoder(encoder);
    }
}
