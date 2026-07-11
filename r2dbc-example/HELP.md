# R2DBC Database Connection

## 範例專案引用套件

- spring-boot-starter 4.1.0
- spring-boot-starter-test 4.1.0
- spring-boot-h2console 4.1.0
- h2-database 2.1.214
- **spring-boot-starter-webflux 4.1.0**
- **spring-boot-starter-data-r2dbc 4.1.0**
- **r2dbc-h2 0.9.1.RELEASE**
- spring-boot-starter-webmvc 4.1.0
- spring-boot-starter-webmvc-test 4.1.0
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
spring:
  r2dbc:
    url: r2dbc:h2:mem:///r2dbc-example-db?options=DB_CLOSE_DELAY=-1
    driver-class-name: org.h2.Driver
```
- resources/schema.sql
```sql
-- 初始化資料表
CREATE TABLE IF NOT EXISTS USERS (
    ID INT PRIMARY KEY AUTO_INCREMENT,
    NAME VARCHAR(50),
    GENDER VARCHAR(10),
    PHONE_NUMBER VARCHAR(15),
    EMAIL VARCHAR(100),
    ADDRESS VARCHAR(50)
);

-- 初始化資料
INSERT INTO USERS (NAME, GENDER, PHONE_NUMBER, EMAIL, ADDRESS)
VALUES ('John Doe', 'MALE', '123-456-7890', 'john@dccompany.com', '123 Main St');
```
- Entity (需要使用 Annotation 標記)
```java
@Entity
@Table(name = "USERS")
public class User {

    @Id
    private Integer id;
    
    // ...
}
```

- Repository (實作 ReactiveCrudRepository)

```java

@Repository
public interface UserRepository extends ReactiveCrudRepository<User, Integer> {

    // 響應式查詢
    Flux<User> findByIdBetween(Integer start, Integer end);
}
```

- Service (使用 Flux 和 Mono)

```java
@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // CREATE
    public Mono<User> createUser(User user) {
        return userRepository.save(user);
    }

    // SELECT
    public Mono<User> getUserById(Integer id) {
        return userRepository.findById(id);
    }

    public Flux<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Flux<User> getUserByIdRange(Integer start, Integer end) {
        return userRepository.findByIdBetween(start, end);
    }

    // UPDATE
    public Mono<User> updateUser(User user) {
        return userRepository.findById(user.getId())
                .flatMap(existingUser -> {
                    existingUser.setName(user.getName());
                    existingUser.setGender(user.getGender());
                    existingUser.setPhoneNumber(user.getPhoneNumber());
                    existingUser.setEmail(user.getEmail());
                    existingUser.setAddress(user.getAddress());
                    return userRepository.save(existingUser);
                });
    }

    // DELETE
    public Mono<Void> deleteUserById(Integer id) {
        return userRepository.deleteById(id);
    }
}
```
- Controller (Flux, Mono 轉成 ResponseEntity)
```java
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    @Tag(name = "新增成員服務", description = "新增一個新成員")
    public Mono<User> createUser(@RequestBody CreateUserRequest createUserRequest) {
        User user = transfer(createUserRequest);
        return userService.createUser(user);
    }

    @GetMapping("/all")
    @Tag(name = "取得所有成員服務", description = "取得所有成員資料")
    public Flux<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/get")
    @Tag(name = "依據 ID 取得成員服務", description = "依據 ID 取得特定成員資料")
    public Mono<ResponseEntity<User>> getUserById(@RequestParam Integer id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/getRange")
    @Tag(name = "依據 ID 範圍取得成員服務", description = "依據 ID 範圍取得區間段成員資料")
    public Flux<User> getUserByIdRange(@RequestParam Integer start, @RequestParam Integer end) {
        return userService.getUserByIdRange(start, end);
    }

    @PutMapping("/update")
    @Tag(name = "更新成員服務", description = "更新特定成員資料")
    public Mono<ResponseEntity<User>> updateUser(@RequestBody UpdateUserRequest updateUserRequest) {
        User user = transfer(updateUserRequest);
        return userService.updateUser(user)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/delete")
    @Tag(name = "刪除成員服務", description = "依據 ID 刪除特定成員資料")
    public Mono<ResponseEntity<Void>> deleteUserById(@RequestParam Integer id) {
        return userService.getUserById(id)
                .flatMap(existingUser -> userService.deleteUserById(id)
                        .then(Mono.just(ResponseEntity.noContent().<Void>build())))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
```