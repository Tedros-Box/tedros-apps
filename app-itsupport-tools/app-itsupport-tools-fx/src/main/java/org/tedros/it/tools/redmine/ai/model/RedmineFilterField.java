package org.tedros.it.tools.redmine.ai.model;

import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Enum que representa todos os campos conhecidos que podem ser utilizados
 * como parâmetros de filtragem em consultas de issues do Redmine.
 *
 * <p>Inclui campos padrão, campos de data, numéricos, relacionais e suporte a
 * campos personalizados (cf_X).</p>
 *
 * <p>Baseado nos filtros disponíveis na interface web do Redmine.</p>
 *
 * @author
 *   Davis Gordon Dun
 */
/*
<select id="add_filter_select"><option value="">&nbsp;</option>
<option value="status_id" disabled="disabled">Situação</option>
<option value="project_id">Projeto</option>
<option value="tracker_id">Tipo</option>
<option value="priority_id">Prioridade</option>
<option value="author_id">Autor</option>
<option value="assigned_to_id" disabled="disabled">Atribuído para</option>
<option value="fixed_version_id">Versão</option>
<option value="subject">Título</option>
<option value="description">Descrição</option>
<option value="notes">Notas</option>
<option value="done_ratio">% Terminado</option>
<option value="is_private">Privado</option>
<option value="watcher_id">Observador</option>
<option value="updated_by">Atualizado por</option>
<option value="last_updated_by">Última atualização por</option>
<option value="cf_80">OS Globalweb</option>
<option value="cf_62">Origem</option>
<option value="cf_2">SEI</option>
<option value="cf_3">Ambiente</option>
<option value="cf_5">Janela</option>
<option value="cf_6">Número do protocolo</option>
<option value="cf_41">Observações</option>
<option value="cf_59">Perfil Exigido</option>
<option value="cf_60">Serviços</option>
<option value="cf_61">Qtd. Itens Serviço</option>
<option value="cf_75">HPA</option>
<option value="cf_71">Classificação</option>
<option value="cf_30">Relatório G4F (OS)</option>
<option value="issue_id">Tarefa</option>
<option value="tags">Tags</option><optgroup label="Atribuído para"><option value="member_of_group">Responsável pelo grupo</option>
<option value="assigned_to_role">Papel do responsável</option></optgroup><optgroup label="Versão"><option value="fixed_version.due_date">versão Data</option>
<option value="fixed_version.status">versão Situação</option></optgroup><optgroup label="Data"><option value="created_on">Criado em</option>
<option value="updated_on">Alterado em</option>
<option value="closed_on">Concluído</option>
<option value="start_date">Início</option>
<option value="due_date">Data prevista</option></optgroup><optgroup label="Registro de horas"><option value="estimated_hours">Tempo estimado</option>
<option value="spent_time">Tempo gasto</option></optgroup><optgroup label="Arquivo"><option value="attachment">Arquivo</option>
<option value="attachment_description">File description</option></optgroup><optgroup label="Projeto"><option value="project.status">Projeto Situação</option></optgroup><optgroup label="Relações"><option value="relates">relacionado a</option>
<option value="duplicates">duplica</option>
<option value="duplicated">duplicado por</option>
<option value="blocks">bloqueia</option>
<option value="blocked">bloqueado por</option>
<option value="precedes">precede</option>
<option value="follows">segue</option>
<option value="copied_to">copiada</option>
<option value="copied_from">copiado</option>
<option value="parent_id">Tarefa mãe</option>
<option value="child_id">Subtarefas</option></optgroup></select> 
*/
public enum RedmineFilterField {

    // ===============================================================
    // 🔹 CAMPOS DE STATUS E FLUXO
    // ===============================================================

    /** ID do status da issue (ex: Novo, Em andamento, Resolvido, etc.) */
    STATUS_ID("status_id", FilterType.NUMBER),

    /** ID da prioridade (ex: Alta, Normal, Baixa) */
    PRIORITY_ID("priority_id", FilterType.NUMBER),

    /** ID do rastreador (ex: Bug, Tarefa, Suporte, etc.) */
    TRACKER_ID("tracker_id", FilterType.NUMBER),

    /** ID da versão (milestone) associada à issue */
    FIXED_VERSION_ID("fixed_version_id", FilterType.NUMBER),

    /** ID da categoria da issue (se houver) */
    CATEGORY_ID("category_id", FilterType.NUMBER),

    /** Progresso da issue (0–100%) */
    DONE_RATIO("done_ratio", FilterType.NUMBER),

    // ===============================================================
    // 🔹 CAMPOS DE RELACIONAMENTO (USUÁRIOS / AUTORES)
    // ===============================================================

    /** ID do usuário a quem a issue está atribuída */
    ASSIGNED_TO_ID("assigned_to_id", FilterType.NUMBER),

    /** ID do autor (quem criou a issue) */
    AUTHOR_ID("author_id", FilterType.NUMBER),

    /** ID do usuário observador (watcher) */
    WATCHER_ID("watcher_id", FilterType.NUMBER),

    /** ID do usuário que atualizou por último a issue */
    UPDATED_BY_ID("updated_by_id", FilterType.NUMBER),

    // ===============================================================
    // 🔹 CAMPOS DE PROJETO E ORGANIZAÇÃO
    // ===============================================================

    /** ID do projeto ao qual a issue pertence */
    PROJECT_ID("project_id", FilterType.NUMBER),

    /** Nome do projeto */
    PROJECT("project", FilterType.TEXT),

    /** Nome do rastreador (Tracker) */
    TRACKER("tracker", FilterType.TEXT),

    /** Nome do status da issue */
    STATUS("status", FilterType.TEXT),

    /** Nome do usuário atribuído */
    ASSIGNED_TO("assigned_to", FilterType.TEXT),

    /** Identificador do autor */
    AUTHOR("author", FilterType.TEXT),

    // ===============================================================
    // 🔹 CAMPOS DE DATAS
    // ===============================================================

    /** Data de início planejada */
    START_DATE("start_date", FilterType.DATE),

    /** Data de vencimento */
    DUE_DATE("due_date", FilterType.DATE),

    /** Data de criação da issue */
    CREATED_ON("created_on", FilterType.DATE),

    /** Data da última atualização */
    UPDATED_ON("updated_on", FilterType.DATE),

    /** Data de fechamento da issue */
    CLOSED_ON("closed_on", FilterType.DATE),

    // ===============================================================
    // 🔹 CAMPOS DE DESCRIÇÃO E CONTEÚDO
    // ===============================================================

    /** Título/assunto da issue */
    SUBJECT("subject", FilterType.TEXT),

    /** Descrição da issue */
    DESCRIPTION("description", FilterType.TEXT),

    /** Notas (comentários) da issue */
    NOTES("notes", FilterType.TEXT),

    // ===============================================================
    // 🔹 CAMPOS DE TEMPO / ESTIMATIVAS
    // ===============================================================

    /** Horas estimadas para conclusão */
    ESTIMATED_HOURS("estimated_hours", FilterType.NUMBER),

    /** Horas gastas (registradas) */
    SPENT_HOURS("spent_hours", FilterType.NUMBER),

    // ===============================================================
    // 🔹 CAMPOS DE RELAÇÕES ENTRE ISSUES
    // ===============================================================

    /** ID da issue pai */
    PARENT_ID("parent_id", FilterType.NUMBER),

    /** Issues filhas (subtasks) */
    CHILDREN("children", FilterType.TEXT),

    /** Relações gerais entre issues (ex: bloqueia, duplicado, relacionado) */
    RELATIONS("relations", FilterType.TEXT),

    // ===============================================================
    // 🔹 OUTROS CAMPOS ÚTEIS
    // ===============================================================

    /** Número da issue */
    ISSUE_ID("issue_id", FilterType.NUMBER),

    /** Nome da versão associada */
    FIXED_VERSION("fixed_version", FilterType.TEXT),

    /** Nome da categoria */
    CATEGORY("category", FilterType.TEXT),

    // ===============================================================
    // 🔹 CAMPOS PERSONALIZADOS (Custom Fields)
    // ===============================================================

    /**
     * Campo genérico para campos personalizados.
     * <p>Exemplo: <code>cf_30</code>, <code>cf_41</code>.</p>
     * <p>O tipo será detectado dinamicamente via metadados carregados
     * com {@code loadCustomFieldMetadata()}.</p>
     */
    CUSTOM_FIELD("cf_", FilterType.TEXT);

    // ===============================================================
    // 🔹 ATRIBUTOS INTERNOS
    // ===============================================================

    private final String fieldName;
    private final FilterType type;
    private final static ObjectMapper mapper = new ObjectMapper();

    RedmineFilterField(String fieldName, FilterType type) {
        this.fieldName = fieldName;
        this.type = type;
    }

    /** Retorna o nome do campo como reconhecido pela API do Redmine */
    public String getFieldName() {
        return fieldName;
    }

    /** Retorna o tipo de dado (texto, número, data, booleano) */
    public FilterType getType() {
        return type;
    }

    // ===============================================================
    // 🔹 MÉTODOS AUXILIARES
    // ===============================================================

    /** Verifica se o nome representa um campo personalizado (cf_X). */
    public static boolean isCustomField(String fieldName) {
        return fieldName != null && fieldName.startsWith("cf_");
    }

    /** Tenta localizar um campo pelo nome interno. */
    public static RedmineFilterField fromFieldName(String name) {
        for (RedmineFilterField f : values()) {
            if (f.fieldName.equalsIgnoreCase(name))
                return f;
        }
        return null;
    }
    
    /**
     * Converte um mapa genérico de filtros (ex: vindo de JSON ou query params)
     * em um mapa tipado com {@link RedmineFilterField} e {@link FilterCondition}.
     *
     * <p>Exemplo de entrada:</p>
     * <pre>{@code
     * {
     *   "status_id": {"op": "!=", "value": "2"},
     *   "assigned_to_id": {"op": "=", "value": "509"},
     *   "cf_30": {"op": "=", "value": "Entregue"}
     * }
     * }</pre>
     *
     * @param rawFilters mapa genérico contendo filtros
     * @return mapa convertido para tipos seguros
     */
    public static Map<String, FilterCondition> fromRawMap(Map<String, Object> rawFilters) {
        Map<String, FilterCondition> result = new HashMap<>();
        if (rawFilters == null || rawFilters.isEmpty())
            return result;

        for (Map.Entry<String, Object> entry : rawFilters.entrySet()) {
            String fieldName = entry.getKey();
            Object value = entry.getValue();

            RedmineFilterField field = fromFieldName(fieldName);
            FilterType type = (field != null) ? field.getType() :
                    (isCustomField(fieldName) ? FilterType.TEXT : FilterType.TEXT);

            if (value instanceof Map<?, ?> mapValue) {
                Object opObj = mapValue.get("op");
                String op = opObj != null ? opObj.toString() : "=";

                Object val = mapValue.get("value");

                FilterCondition condition = null;

                if (val instanceof Boolean bVal) {
                    condition = FilterCondition.to(op, bVal);
                } else if (val instanceof String strVal) {
                    condition = FilterCondition.auto(type, op, strVal);
                } else if (val instanceof Number numVal) {
                    condition = FilterCondition.auto(type, op, numVal.toString());
                } else if (val instanceof Map<?, ?> dateRange
                        && type == FilterType.DATE
                        && dateRange.containsKey("from")
                        && dateRange.containsKey("to")) {
                    LocalDate from = LocalDate.parse(dateRange.get("from").toString());
                    LocalDate to = LocalDate.parse(dateRange.get("to").toString());
                    condition = FilterCondition.betweenDates(from, to);
                }

                if (condition != null) {
                    result.put(fieldName, condition);
                }

            } else {
                result.put(fieldName, FilterCondition.equalsTo(String.valueOf(value)));
            }
        }

        return result;
    }

    public static Map<String, FilterCondition> fromJSON(String json) throws IOException {
        // Converte o JSON para um Map<String, Object>
        Map<String, Object> raw = mapper.readValue(json, new TypeReference<>() {});
        
        // Converte para Map tipado com FilterCondition
        return RedmineFilterField.fromRawMap(raw);
    } 
     

    public static Map<String, FilterCondition> fromObject(Object obj) {
        Map<String, FilterCondition> map = new HashMap<>();
        if (obj == null) return map;

        Class<?> clazz = obj.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            try {
                field.setAccessible(true);
                Object value = field.get(obj);
                if (value != null && value instanceof FilterCondition fc) {
                    map.put(field.getName(), fc);
                }
            } catch (Exception ignored) {}
        }
        return map;
    }
    
    /*public static Map<String, FilterCondition> fromRecord(Object record) {
        Map<String, FilterCondition> map = new HashMap<>();
        if (record == null) return map;

        for (RecordComponent component : record.getClass().getRecordComponents()) {
            try {
                Object value = component.getAccessor().invoke(record);
                if (value != null && value instanceof FilterCondition fc) {
                    map.put(component.getName(), fc);
                }
            } catch (Exception ignored) {}
        }
        return map;
    }*/
    
}

