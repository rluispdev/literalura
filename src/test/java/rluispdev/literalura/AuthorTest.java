package rluispdev.literalura;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import rluispdev.literalura.model.Author;

import static org.junit.jupiter.api.Assertions.*;

public class AuthorTest {

    @Test
    void testDeserialization() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        // JSON de teste para desserializar
        String json = "{" +
                "  \"name\": \"Jane Doe\"," +
                "  \"birth_year\": 1980," +
                "  \"death_year\": 2023" +
                "}";

        // Desserializa o JSON para um objeto Author
        Author author = mapper.readValue(json, Author.class);

        // Verifica se a desserialização foi bem-sucedida
        assertEquals("Jane Doe", author.getName());
        assertEquals(1980, author.getBirth_year());
        assertEquals(2023, author.getDeath_year());

        // Adiciona um colaborador para testar a desserialização de uma lista
        Author collaborator = new Author();
        collaborator.setName("John Smith");
        author.getCollaborators().add(collaborator);

        // Serializa o objeto Author de volta para JSON
        String serializedJson = mapper.writeValueAsString(author);

        // Desserializa o JSON de volta para um objeto Author
        Author deserializedAuthor = mapper.readValue(serializedJson, Author.class);

        // Verifica se a desserialização da lista de colaboradores foi bem-sucedida
        assertEquals(1, deserializedAuthor.getCollaborators().size());
        assertEquals("John Smith", deserializedAuthor.getCollaborators().get(0).getName());
    }
}