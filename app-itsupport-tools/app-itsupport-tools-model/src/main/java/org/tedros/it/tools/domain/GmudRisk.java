package org.tedros.it.tools.domain;

public enum GmudRisk {
    LOW("Baixo"),
    MODERATE("Moderado"),
    HIGH("Alto"),
    CRITICAL("Crítico");

    private String description;

    GmudRisk(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
