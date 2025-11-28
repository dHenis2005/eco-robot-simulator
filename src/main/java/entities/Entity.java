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

    /**
     * Basic round method
     */
    public double round(final double value, final double value2) {
        double normalized = Math.max(0, Math.min(value, value2));
        return Math.round(normalized * 100) / 100.0;
    }

}
