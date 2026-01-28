package org.tedros.it.tools.domain;

/**
 * Representa os estados possíveis de uma Review de GMUD.
 */
public enum GmudReviewStatus {
    
    /** Pendente */
    PENDING("Pendente"),
    
    /** Mudança autorizada pelo revisor. */
    APPROVED("Aprovada"),
    
    /** Reprovada */
    REJECTED("Rejeitada");

    private String description;

    GmudReviewStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
