import java.io.File;
import java.io.IOException;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Info {
    boolean isVip;
    String phone;
    String comment;

    public Info() {
    }

    @JsonCreator
    public Info(@JsonProperty("isVip") boolean isVip, @JsonProperty("phone") String phone, @JsonProperty("comment") String comment) {
        this.isVip = isVip;
        this.phone = phone;
        this.comment = comment;
    }

    public static List<Info> readData() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            List<Info> clients = objectMapper.readValue(new File("callers.json"), objectMapper.getTypeFactory().constructCollectionType(List.class, Info.class));
            return clients;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
