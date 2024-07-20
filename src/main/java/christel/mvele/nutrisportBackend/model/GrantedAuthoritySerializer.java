package christel.mvele.nutrisportBackend.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.security.core.GrantedAuthority;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GrantedAuthoritySerializer extends JsonSerializer<List<GrantedAuthority>> {

    /**
     * Serializes a list of {@link GrantedAuthority} objects into a list of strings, where each string represents a role.
     *
     * @param authorities  The list of {@link GrantedAuthority} objects to be serialized.
     * @param jsonGenerator The {@link JsonGenerator} instance to write the serialized data to.
     * @param serializerProvider The {@link SerializerProvider} instance to provide additional serialization context.
     * @throws IOException If an error occurs during serialization.
     */
    @Override
    public void serialize(List<GrantedAuthority> authorities, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        List<String> roles = new ArrayList<>();
        for (GrantedAuthority authority : authorities) {
            roles.add(authority.getAuthority());
        }
        jsonGenerator.writeObject(roles);
    }
}
