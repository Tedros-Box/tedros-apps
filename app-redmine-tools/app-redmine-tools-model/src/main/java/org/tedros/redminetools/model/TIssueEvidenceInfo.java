package org.tedros.redminetools.model;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import java.util.List;

@JsonClassDescription("Informações detalhadas de uma issue (tarefa) do Redmine, incluindo metadados, " +
                     "responsáveis, prazos, horas trabalhadas e campos personalizados do projeto.")
public class TIssueEvidenceInfo {

    @JsonPropertyDescription("Identificador único da issue no Redmine")
    private Long id;

    @JsonPropertyDescription("Título ou assunto principal da tarefa")
    private String subject;

    @JsonPropertyDescription("Data de início planejada da issue")
    private String startDate;

    @JsonPropertyDescription("Data de vencimento ou prazo final da issue")
    private String dueDate;

    @JsonPropertyDescription("Data e hora de criação da issue")
    private String createdOn;

    @JsonPropertyDescription("Data e hora da última atualização da issue")
    private String updatedOn;

    @JsonPropertyDescription("Percentual de conclusão da issue (0 a 100)")
    private Integer doneRatio;

    @JsonPropertyDescription("Data de fechamento da issue (nulo se ainda aberta)")
    private String closedOn;

    @JsonPropertyDescription("Total de horas estimadas para conclusão da tarefa")
    private Float estimatedHours;

    @JsonPropertyDescription("Total de horas já registradas/gastas na tarefa")
    private Float spentHours;

    @JsonPropertyDescription("ID do usuário atribuído como responsável pela issue")
    private Integer assigneeId;

    @JsonPropertyDescription("Nome completo do responsável pela issue")
    private String assigneeName;

    @JsonPropertyDescription("Texto da prioridade (ex: 'Alta', 'Urgente', 'Baixa')")
    private String priorityText;

    @JsonPropertyDescription("Nome do projeto ao qual a issue pertence")
    private String projectName;

    @JsonPropertyDescription("ID do usuário que criou a issue")
    private Integer authorId;

    @JsonPropertyDescription("Nome completo do autor/criador da issue")
    private String authorName;

    @JsonPropertyDescription("Descrição detalhada da issue")
    private String description;

    @JsonPropertyDescription("Nome do status atual da issue (ex: 'Nova', 'Em Andamento', 'Fechada')")
    private String statusName;

    @JsonPropertyDescription("Entregável esperado ao final da issue (ex: relatório, funcionalidade)")
    private String deliverable;

    @JsonPropertyDescription("Campo personalizado: HPA - 'Horas Por Atividade'")
    private String hpa;

    @JsonPropertyDescription("Perfil profissional requerido para executar a tarefa (ex: 'Desenvolvedor Fullstack')")
    private String requiredProfile;

    @JsonPropertyDescription("Tipo de serviço associado à issue com a quantidade de horas esperada para execução (ex: 'Definição de Arquitetura da Solução (Baixa: 22H) Por Projeto/Módulo de Solução de TI')")
    private String serviceType;

    @JsonPropertyDescription("Lista de notas e comentários registrados na issue (em ordem cronológica)")
    private List<String> notes;

    // === GETTERS E SETTERS ===

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Integer getDoneRatio() {
        return doneRatio;
    }

    public void setDoneRatio(Integer doneRatio) {
        this.doneRatio = doneRatio;
    }

    public String getClosedOn() {
        return closedOn;
    }

    public void setClosedOn(String closedOn) {
        this.closedOn = closedOn;
    }

    public Float getEstimatedHours() {
        return estimatedHours;
    }

    public void setEstimatedHours(Float estimatedHours) {
        this.estimatedHours = estimatedHours;
    }

    public Float getSpentHours() {
        return spentHours;
    }

    public void setSpentHours(Float spentHours) {
        this.spentHours = spentHours;
    }

    public Integer getAssigneeId() {
        return assigneeId;
    }

    public void setAssigneeId(Integer assigneeId) {
        this.assigneeId = assigneeId;
    }

    public String getAssigneeName() {
        return assigneeName;
    }

    public void setAssigneeName(String assigneeName) {
        this.assigneeName = assigneeName;
    }

    public String getPriorityText() {
        return priorityText;
    }

    public void setPriorityText(String priorityText) {
        this.priorityText = priorityText;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getDeliverable() {
        return deliverable;
    }

    public void setDeliverable(String deliverable) {
        this.deliverable = deliverable;
    }

    public String getHpa() {
        return hpa;
    }

    public void setHpa(String hpa) {
        this.hpa = hpa;
    }

    public String getRequiredProfile() {
        return requiredProfile;
    }

    public void setRequiredProfile(String requiredProfile) {
        this.requiredProfile = requiredProfile;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public List<String> getNotes() {
        return notes;
    }

    public void setNotes(List<String> notes) {
        this.notes = notes;
    }
}