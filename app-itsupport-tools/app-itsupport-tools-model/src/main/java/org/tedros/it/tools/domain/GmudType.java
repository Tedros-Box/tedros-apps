package org.tedros.it.tools.domain;

/**
 * Define os tipos de GMUD conforme padrões ITIL.
 */
public enum GmudType {
    
    /** Mudanças que seguem o fluxo completo de planejamento e aprovação. */
    NORMAL("Normal"),
    
    /** Mudanças de baixo risco, recorrentes e pré-autorizadas. */
    STANDARD("Padrão"),
    
    /** Mudanças que precisam de implementação imediata para resolver incidentes críticos. */
    EMERGENCY("Emergencial");

    private String description;

    GmudType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
