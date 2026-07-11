# JDBC Database Connection

## 範例專案引用套件

- spring-boot-starter 4.1.0
- spring-boot-starter-test 4.1.0
- spring-boot-h2console 4.1.0
- h2-database 2.1.214
- spring-boot-starter-flyway 4.1.0
- **spring-boot-starter-data-jdbc 4.1.0**
- **spring-boot-starter-data-jdbc-test 4.1.0**
- spring-boot-starter-webmvc 4.1.0
- spring-boot-starter-webmvc-test 4.1.0
- spring-boot-devtools 4.1.0
- springdoc-openapi-starter-webmvc-ui 2.5.0

## Getting Start

執行 Application.java <br>

```java

@SpringBootApplication
public class Application {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Application.class);
        Environment env = app.run(args).getEnvironment();
        logApplicationStartup(env);
    }
}
```

執行成功，系統成功運行會顯示以下資訊 : <br>

```text
----------------------------------------------------------
    Application '{applicationName}' is running! Access URLs:
    Local:         http://localhost:{port}/
    H2 DB Console: http://localhost:{port}/h2-console
    SwaggerUI:     http://localhost:{port}/swagger-ui/index.html
----------------------------------------------------------
```

## Difference
- Repository
```java
@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    // 使用純 SQL 執行
    // jdbcTemplate.update(); -> 異動
    // jdbcTemplate.query(); -> 查詢多筆資料
    // jdbcTemplate.queryForObject(); -> 查詢單筆資料
}
```