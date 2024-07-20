package christel.mvele.nutrisportBackend.model;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GrantedAuthorityDeserializer extends JsonDeserializer<List<GrantedAuthority>> {

    /**
     * Deserializes a JSON string containing a list of roles into a list of {@link GrantedAuthority} objects.
     *
     * @param jsonParser  The JSON parser used to read the input JSON string.
     * @param deserializationContext  The deserialization context containing information about the deserialization process.
     * @return  A list of {@link GrantedAuthority} objects representing the roles in the input JSON string.
     * @throws IOException  If an I/O error occurs during the deserialization process.
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<GrantedAuthority> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        List<String> roles = jsonParser.readValueAs(List.class);
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }
}
