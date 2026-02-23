package org.tedros.it.tools.ejb.controller;

import java.util.List;

import org.tedros.it.tools.domain.DomainApp;
import org.tedros.it.tools.domain.GmudItemStatus;
import org.tedros.it.tools.domain.GmudReviewStatus;
import org.tedros.it.tools.domain.GmudStatus;
import org.tedros.it.tools.ejb.service.GmudService;
import org.tedros.it.tools.entity.Gmud;
import org.tedros.it.tools.entity.GmudItem;
import org.tedros.it.tools.entity.GmudReview;
import org.tedros.server.ejb.controller.ITSecurityController;
import org.tedros.server.ejb.controller.TSecureEjbController;
import org.tedros.server.result.TResult;
import org.tedros.server.security.ITSecurity;
import org.tedros.server.security.TAccessPolicie;
import org.tedros.server.security.TAccessToken;
import org.tedros.server.security.TBeanPolicie;
import org.tedros.server.security.TBeanSecurity;
import org.tedros.server.security.TSecurityInterceptor;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;

@TSecurityInterceptor
@Stateless(name="IGmudController")
@TBeanSecurity({@TBeanPolicie(id = DomainApp.CHANGE_MANAGER_GMUD_EDIT_FORM_ID, 
policie = { TAccessPolicie.APP_ACCESS, TAccessPolicie.VIEW_ACCESS })})
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
public class GmudController extends TSecureEjbController<Gmud> implements IGmudController, ITSecurity  {

    @EJB
    private GmudService serv;
    
    @EJB
    private ITSecurityController securityController;
    
    @Override
    public GmudService getService() {
        return serv;
    }
    
    @Override
    public ITSecurityController getSecurityController() {
        return securityController;
    }
    
    @Override
    public TResult<Gmud> save(TAccessToken token, Gmud entity) {
        try {
            // 1. Verificação de Regra de Negócio: Imutabilidade
            // Precisamos checar o estado ATUAL no banco para saber se pode ser editado.
            // O findById aqui serve apenas para leitura de regra, não para merge.
            if (!entity.isNew()) {
                Gmud currentDb = serv.findById(entity);
                
                if (currentDb == null) {
                    return new TResult<>(TResult.TState.ERROR, "GMUD não encontrada no banco de dados.");
                }

                // Se no banco já está FINISHED ou FAILED, bloqueia edições (a menos que seja uma reabertura explicita)
                // Se o usuário estiver tentando salvar algo em cima de uma GMUD fechada, avisamos.
                if (isFinalStatus(currentDb.getStatus())) {
                    return new TResult<>(TResult.TState.WARNING, 
                            "Esta GMUD já foi finalizada (" + currentDb.getStatus() + ") e não pode ser alterada.");
                }
            } else {
                // Se é nova, status inicial padrão
                if (entity.getStatus() == null) 
                	entity.setStatus(GmudStatus.DRAFT.getDescription());
            }

            // 2. Recálculo de Status (Coerência)
            // Antes de salvar, garantimos que o status da GMUD reflete a realidade dos itens/reviews que ESTÃO vindo da tela.
            recalculateGmudStatus(entity);
            
            // 3. Persistência com Optimistic Lock
            // O super.save chamará o EntityManager.merge(entity).
            // Se entity.version < banco.version (seja na Gmud ou em qualquer Item), o JPA lançará OptimisticLockException.
            return super.save(token, entity);
            
        } catch (Exception e) {
            // O processException padrão do Tedros deve tratar OptimisticLockException 
            // e retornar uma mensagem amigável para o usuário.
            return super.processException(token, entity, e);
        }
    }

    /**
     * Atualiza o status da GMUD baseando-se estritamente nas listas contidas no objeto.
     */
    private void recalculateGmudStatus(Gmud gmud) {
        // Se a GMUD já está em fase de execução (ou aprovada para tal), olhamos o Plano de Execução
        if (isExecutionPhase(gmud.getStatus())) {
            updateStatusFromExecutionPlan(gmud);
        } 
        // Caso contrário, olhamos as Aprovações (Reviews)
        else {
            updateStatusFromReviews(gmud);
        }
    }

    private void updateStatusFromExecutionPlan(Gmud gmud) {
        List<GmudItem> items = gmud.getExecutionPlan();
        if (items == null || items.isEmpty()) return;

        boolean hasFailure = items.stream()
                .anyMatch(i -> GmudItemStatus.FAILED.getDescription().equals(i.getStatus()));
        
        // Consideramos "Executando" se tiver itens em progresso ou já finalizados (mas não todos)
        boolean hasActivity = items.stream()
                .anyMatch(i -> GmudItemStatus.EXECUTING.getDescription().equals(i.getStatus()) || 
                               GmudItemStatus.FINISHED.getDescription().equals(i.getStatus()));
        
        boolean allFinished = items.stream()
                .allMatch(i -> GmudItemStatus.FINISHED.getDescription().equals(i.getStatus()));

        if (hasFailure) {
            gmud.setStatus(GmudStatus.FAILED.getDescription());
        } else if (allFinished) {
            gmud.setStatus(GmudStatus.FINISHED.getDescription());
        } else if (hasActivity) {
            gmud.setStatus(GmudStatus.EXECUTING.getDescription());
        } else {
            // Se ninguém começou nada, mantém APPROVED
            gmud.setStatus(GmudStatus.APPROVED.getDescription());
        }
    }

    private void updateStatusFromReviews(Gmud gmud) {
        List<GmudReview> reviews = gmud.getReviews();
        // Se não tem reviews, mantém o status que está (provavelmente DRAFT ou ANALYSIS)
        if (reviews == null || reviews.isEmpty()) return;

        boolean hasRejection = reviews.stream()
                .anyMatch(r -> GmudReviewStatus.REJECTED.getDescription().equals(r.getStatus()));
        
        boolean allApproved = reviews.stream()
                .allMatch(r -> GmudReviewStatus.APPROVED.getDescription().equals(r.getStatus()));

        if (hasRejection) {
            gmud.setStatus(GmudStatus.REJECTED.getDescription());
        } else if (allApproved) {
            gmud.setStatus(GmudStatus.APPROVED.getDescription());
        } else {
            // Se tem pendências ou aprovações parciais, fica em análise
            gmud.setStatus(GmudStatus.ANALYSIS.getDescription());
        }
    }

    private boolean isExecutionPhase(String status) {
        return GmudStatus.APPROVED.getDescription().equals(status) ||
               GmudStatus.EXECUTING.getDescription().equals(status) ||
               GmudStatus.FINISHED.getDescription().equals(status) ||
               GmudStatus.FAILED.getDescription().equals(status);
    }
    
    private boolean isFinalStatus(String status) {
        return GmudStatus.FINISHED.getDescription().equals(status) ||
               GmudStatus.FAILED.getDescription().equals(status) ||
               GmudStatus.REJECTED.getDescription().equals(status);
    }
    
    @Override
    public TResult<Gmud> remove(TAccessToken token, Gmud entity) {
        try {
            Gmud gmud = serv.findById(entity); // Busca para checar status real
            
            if (gmud == null) {
                 return new TResult<>(TResult.TState.ERROR, "Registro não encontrado.");
            }

            // Não permite deletar se já começou a execução ou terminou
            if(GmudStatus.EXECUTING.getDescription().equals(gmud.getStatus()) || 
               GmudStatus.FINISHED.getDescription().equals(gmud.getStatus())) {
                return new TResult<>(TResult.TState.WARNING, 
                        "GMUD com status " + gmud.getStatus() + " não pode ser excluída.");
            }
            
            // Passa a entity original (com versão) para o remove garantir Optimistic Lock na deleção também
            return super.remove(token, entity);
            
        } catch (Exception e) {
            return super.processException(token, entity, e);
        }
    }
}