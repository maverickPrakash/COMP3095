package ca.gbc.userservice;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
public abstract class AbstractBaseContainerTest {



 static final PostgreSQLContainer<?> POSTGRESQL_CONTAINER;

 static {
  POSTGRESQL_CONTAINER = new PostgreSQLContainer<>("postgres:latest")
          .withDatabaseName("testdb")
          .withUsername("testuser")
          .withPassword("testpassword");
  POSTGRESQL_CONTAINER.start();
 }

 @DynamicPropertySource
 static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
  dynamicPropertyRegistry.add("spring.datasource.url", POSTGRESQL_CONTAINER::getJdbcUrl);
  dynamicPropertyRegistry.add("spring.datasource.username", POSTGRESQL_CONTAINER::getUsername);
  dynamicPropertyRegistry.add("spring.datasource.password", POSTGRESQL_CONTAINER::getPassword);
 }

}