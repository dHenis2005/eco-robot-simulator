package entities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Data;

@Data
public class Entity {
    private String name;
    private double mass;
    private String type;

    public Entity(final String name, final double mass, final String type) {
        this.name = name;
        this.mass = mass;
        this.type = type;
    }

    /**
     * Returns the entity as a JSON object.
     */
    public ObjectNode toJSON(final ObjectMapper mapper) {
        ObjectNode node = mapper.createObjectNode();
        node.put("type", type);
        node.put("name", name);
        node.put("mass", mass);
        return node;
    }

}
