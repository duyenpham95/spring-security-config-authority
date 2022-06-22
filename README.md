# spring-security-config-authority
This is my spring security learning udemy project notes 

### Create Controller with simple api
- create 1 controller, Then added spring-security dep into pom

### Changing the default Security Configuration
- Since `WebSecurityConfigurerAdapter` deprecated, Create `SecurityConfiguration` using a `SecurityFilterChain` Bean to configure HttpSecurity or a WebSecurityCustomizer Bean to configure WebSecurity instead

### Modifying SecurityConfiguration as per our custom requirements
- using antMatcher("/not-secured").permitAll(): tested with no auth in postman return 200
- using antMatcher("/secured").authenticated(): tested with no auth in postman return 401
- using .antMatchers("/read-write").hasAuthority("WRITE"): require "WRITE" auth to pass or else return 403
- using .antMatchers("/read-only").hasAuthority("READ"): require "READ" auth to pass or else return 403

### Create tables and mapping entities
- add postgres driver, jdbc api dependency
- create datasource
- Instead of default user table as JDBC of spring security using, create custom_user table instead
- application.properties
- Using JPA to mapping and query entity from DB
- Create `CustomUser` & `Authority` entities mapping to tables in DB

### Implement custom AuthenticationProvider & config Authority 
- Create `CustomAuthenticationProvider` implements `AuthenticationProvider`
- `CustomAuthenticationProvider` override `authenticate` and `supports` method
  - `authenticate`: implements logic to authenticate
  - `supports`: indicates which type of Authentication classes use for this `CustomAuthenticationProvider`

- Note that: we can have a number of `AuthenticationProvider` and `ProviderManger` (get list of all providers) will take responsibility to call each of provider to we get successful authentication
- `AuthenticationProvider` leverages `Authentication` and `Pricipal` interfaces instead of `UserDetails` in `UserDetailsService`
- When testing in Postman using basic auth with username and password values in DB (find values in `db.sql` in source code)
- Look into the pdf again for knowledge revision that: Spring Security flow goes to `AuthenticationProvider` before going to `UserDetailsService`

### Config Role
Role:
- group of authorities
- stored in authorities table also
- prefix in DB with prefix: ROLE_ and use name of role in code only cuz spring security already mapping for us

Config with hasRole(roleName) for according api

### Custom filter: Add filter to filter email contains `test` word
- create a custom filter implements Filter interface, override doFilter Method
- add addFilterBefore in security config 
- add `@EnableWebSecurity(debug = true)` (notice this is for debug mode that the filter works without the `@EnableWebSecurity`) property `logging.level.org.springframework.security.web.FilterChainProxy=DEBUG` to log debug when test the order of filters