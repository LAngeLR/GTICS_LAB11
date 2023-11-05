package edu.pucp.gtics.lab11_gtics_20232.config;

import edu.pucp.gtics.lab11_gtics_20232.dao.UsuarioDao;
import edu.pucp.gtics.lab11_gtics_20232.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

@Configuration
public class WebSecurityConfig  {

    @Autowired
    private UsuarioDao usuarioDao;
    final DataSource dataSource;

    public WebSecurityConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource){
        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
        String sql1 = "SELECT correo, password, enabled FROM usuarios WHERE correo = ?";
        String sql2 = "SELECT correo, autorizacion FROM usuarios WHERE correo = ? and enabled = 1";
        users.setUsersByUsernameQuery(sql1);
        users.setAuthoritiesByUsernameQuery(sql2);

        return users;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http.formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/loginProcess")
                .successHandler((request, response, authentication) -> {

                    DefaultSavedRequest defaultSavedRequest =
                            (DefaultSavedRequest) request.getSession().getAttribute("SPRING_SECURITY_SAVED_REQUEST");

                    HttpSession session = request.getSession();
                    User usuarioActivo = usuarioDao.obtenerUsuarioPorCorreo(authentication.getName());
                    session.setAttribute("usuario", usuarioActivo);



                    if(defaultSavedRequest != null){
                        String targetUrl = defaultSavedRequest.getRedirectUrl();
                        new DefaultRedirectStrategy().sendRedirect(request, response, targetUrl);
                    }
                    else {

                        String rol = "";
                        for (GrantedAuthority role : authentication.getAuthorities()) {
                            rol = role.getAuthority();
                        }
                        if (rol.equals("ADMIN")) {
                            response.sendRedirect("/juegos/lista");
                        } else if (rol.equals("USER")) {
                            response.sendRedirect("/");
                        } else {
                            response.sendRedirect("/login");
                        }
                    }
                });
/*                .failureUrl("/login?error=bad_credentials")
                .and().exceptionHandling().accessDeniedPage("/accessDenied");*/



        http.authorizeRequests()
                .antMatchers("/juegos/misJuegos").hasAnyAuthority("USER")
                .antMatchers("/juegos/**").hasAnyAuthority("ADMIN")
                .antMatchers("/distribuidora/**").hasAnyAuthority("ADMIN")
                .antMatchers("/usuarios/**").hasAnyAuthority("ADMIN", "USER")
                .anyRequest().permitAll();

        http.logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .clearAuthentication(true);

        return http.build();
    }


}
