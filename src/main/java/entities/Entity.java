package entities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Entity{
    public String name;
    public double mass;
    public String type;
    public Entity(String name, double mass,  String type) {
        this.name = name;
        this.mass = mass;
        this.type = type;
    }
    public ObjectNode toJSON(ObjectMapper mapper) {
        ObjectNode node = mapper.createObjectNode();
        node.put("type", type);
        node.put("name", name);
        node.put("mass", mass);
        return node;
    }

}
