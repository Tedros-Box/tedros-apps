package org.tedros.redminetools.gateway;

public class CustomFieldMetadata {
    private int id;
    private String name;
    private FilterType type;

    public CustomFieldMetadata(int id, String name, FilterType type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public FilterType getType() { return type; }
}

