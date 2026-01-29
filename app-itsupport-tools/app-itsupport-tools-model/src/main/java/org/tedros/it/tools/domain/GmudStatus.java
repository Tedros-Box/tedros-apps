package org.tedros.it.tools.domain;

/**
 * Representa os estados possíveis de uma GMUD no sistema Tedros.
 */
public enum GmudStatus {
	
	/** Aberta pelo solicitante. */
    OPENED("Aberta"),
    
    /** Em fase de rascunho/elaboração pelo solicitante. */
    DRAFT("Rascunho"),
    
    /** Aguardando parecer técnico ou aprovação do CAB. */
    ANALYSIS("Em Análise"),
    
    /** Mudança autorizada para execução. */
    APPROVED("Aprovada"),
    
    /** Mudança em processo de implementação técnica. */
    EXECUTING("Em Execução"),
    
    /** Finalizada com sucesso ou sucesso parcial. */
    FINISHED("Concluída"),
    
    /** Reprovada pelo comitê ou cancelada. */
    REJECTED("Rejeitada"),
    
    /** Implementada mas apresentou falhas, possivelmente exigindo Rollback. */
    FAILED("Falha na Execução");

    private String description;

    GmudStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
