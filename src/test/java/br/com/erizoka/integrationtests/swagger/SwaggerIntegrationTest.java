package br.com.erizoka.integrationtests.swagger;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import br.com.erizoka.Startup;
import br.com.erizoka.integrationtests.testcontainers.AbstractIntegrationTest;
import io.restassured.RestAssured;

@SpringBootTest(classes = Startup.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class SwaggerIntegrationTest extends AbstractIntegrationTest {
 
    @LocalServerPort
    private String port;
 
    @BeforeEach
    void setUp() {
        RestAssured.port = Integer.parseInt(port);
    }
    
    @Test
    void connectionEstablished() {
        assertTrue(Initializer.postgres.isCreated());
        assertTrue(Initializer.postgres.isRunning());
    }
 
    @Test
    void shouldDisplaySwaggerUiPage() {
        var content = given()
        		.basePath("/swagger-ui/index.html")
        		.log().all()
                .get()
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();
        assertTrue(content.contains("Swagger UI"));
    }
 
}