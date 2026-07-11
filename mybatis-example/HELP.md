# Mybatis Database Connection

## 範例專案引用套件

- spring-boot-starter 4.1.0
- spring-boot-starter-test 4.1.0
- spring-boot-h2console 4.1.0
- h2-database 2.1.214
- spring-boot-starter-flyway 4.1.0
- spring-boot-starter-flyway-test 4.1.0
- **mybatis-spring-boot-starter 4.0.1**
- **mybatis-spring-boot-starter-test 4.0.1**
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

- application.yaml

```yaml
mybatis:
  mapper-locations: classpath:mapper/*.xml
  type-aliases-package: com.david.project.entity
  configuration:
    map-underscore-to-camel-case: true
```

- Mapper

```java

@Mapper
public interface UserMapper {
    List<User> findAll();

    User findById(Integer id);

    int insert(User user);

    int update(User user);

    int deleteById(Integer id);
}
```

- resources/mapper/Mapper.xml

```xml
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "https://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.david.project.mapper.UserMapper">

    <!-- CREATE -->
    <insert id="insert" parameterType="com.david.project.entity.User" useGeneratedKeys="true" keyProperty="id">
        INSERT INTO USERS(NAME, GENDER, PHONE_NUMBER, EMAIL, ADDRESS) values (#{name}, #{gender}, #{phoneNumber},
        #{email}, #{address})
    </insert>

    <!-- SELECT -->
    <select id="findAll" resultType="com.david.project.entity.User">
        SELECT * FROM USERS
    </select>

    <select id="findById" resultType="com.david.project.entity.User" parameterType="Integer">
        SELECT * FROM USERS WHERE ID = #{id}
    </select>

    <!-- UPDATE -->
    <update id="update" parameterType="com.david.project.entity.User">
        UPDATE USERS SET NAME = #{name}, GENDER = #{gender}, PHONE_NUMBER = #{phoneNumber}, EMAIL = #{email}, ADDRESS =
        #{address} WHERE ID = #{id}
    </update>

    <!-- DELETE -->
    <delete id="deleteById" parameterType="Integer">
        DELETE FROM USERS WHERE ID = #{id}
    </delete>

</mapper>
```