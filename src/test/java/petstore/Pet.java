// 1 - Pacote
package petstore;

// 2 - Bibliotecas

import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;

// 3 - Classes
public class Pet {
    // 3.1 Atributos
    String uri = "https://petstore.swagger.io/v2/pet"; // Endereço da entidade PET

    // 3.1 Métodos e funções
    public String lerJson(String caminhoJson) throws IOException {
        return new String(Files.readAllBytes(Paths.get(caminhoJson)));
    }

    // Incluir - Create - Post
    @Test(priority = 1)   // Identifica o método ou função como um teste para o TestNG
    public void incluirPet() throws IOException {
        String jsonBody = lerJson("db/pet1.json");

        // Sintaxe Gherkin
        // Dado - Quando - Então
        // Given - When - Then
        given()
                .contentType("application/json") // comum em API REST - antigas era "text/xml"
                .log().all()
                .body(jsonBody)
        .when()
                .post(uri)
        .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Aquiles"))
                .body("status", is("available"))
                .body("category.name", is("AXXXRE330"))
                .body("tags.name", contains("data"))
        ;
    }
    @Test(priority = 3)
    public void consultarPet(){
        String petId = "1983040723";
        String token =
        // Sintaxe Gherkin
        // Dado - Quando - Então
        // Given - When - Then
        given()
                .contentType("application/json") // comum em API REST - antigas era "text/xml"
                .log().all()
        .when()
                .get(uri + "/" + petId)
        .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Aquiles"))
                .body("status", is("available"))
                .body("category.name", is("AXXXRE330"))
                .body("tags.name", contains("data"))
        .extract()
                .path("category.name")
        ;
        System.out.println("O token gerado é:" + token);
    }

    @Test(priority = 2)
    public void alterarPet() throws IOException {
        String jsonBody = lerJson("db/pet2.json");

        given()
                .contentType("application/json") // comum em API REST - antigas era "text/xml"
                .log().all()
                .body(jsonBody)
        .when()
                .put(uri)
        .then()
                .log().all()
                .statusCode(200)
                .body("name", is("Aquiles"))
                .body("status", is("sold"))
        ;
    }



}
