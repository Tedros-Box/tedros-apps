package org.tedros.it.tools.domain;

/**
 * Representa os estados possíveis de uma GMUD no sistema Tedros.
 */
public enum GmudItemStatus {
    
    /** Aguardando parecer técnico ou aprovação do CAB. */
    PENDING("Pendente"),
    
    /** Mudança em processo de implementação técnica. */
    EXECUTING("Em Execução"),
    
    /** Finalizada com sucesso ou sucesso parcial. */
    FINISHED("Concluída"),
    
    /** Implementada mas apresentou falhas, possivelmente exigindo Rollback. */
    FAILED("Falha na Execução");

    private String description;

    GmudItemStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
