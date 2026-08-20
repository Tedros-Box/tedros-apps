package org.tedros.it.tools.redmine.ai.function;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.tedros.ai.function.TFunction;
import org.tedros.ai.function.ToolCallResult;
import org.tedros.integration.redmine.ai.model.FilterCondition;
import org.tedros.integration.redmine.ai.model.RedmineFilterField;
import org.tedros.integration.redmine.ai.model.RedmineIssueFilter;
import org.tedros.integration.redmine.api.model.TIssueEvidenceInfo;
import org.tedros.integration.redmine.gateway.RedmineApiGateway;
import org.tedros.util.TLoggerUtil;

public class RedmineIssueSearchAiFunction extends TFunction<RedmineIssueFilter>  {
	
	private static final Logger LOGGER = TLoggerUtil.getLogger(RedmineIssueSearchAiFunction.class);
	
	public static final String NAME = "filter_redmine_issues";
	public static final String DESCRIPTION = """
			You are an assistant that builds Redmine issue filters in JSON format.

			Your task is to return only a valid JSON object describing how to filter Redmine issues, based on user criteria.

			Each key in the JSON represents a Redmine field name, and its value defines the filter condition:
			{
			  "field_name": {
			    "op": "<operator>",
			    "value": "<value or structure>"
			  }
			}

			Supported operators:
			=   equals
			!=  not equal
			><  between two values (date or numeric range)
			~   contains (for text)
			!~  does not contain
			*   has any value
			!*  has no value
			o   open issues
			c   closed issues

			For ranges, use:
			"due_date": { "op": "><", "value": { "from": "2025-01-01", "to": "2025-12-31" } }

			Redmine Filterable Fields:

			Core issue fields:
			status_id - Situação (or issue.status_id)
			project_id - Projeto
			subproject_id - Subprojeto
			tracker_id - Tipo (or issue.tracker_id)
			priority_id - Prioridade
			author_id - Autor
			assigned_to_id - Atribuído para
			fixed_version_id - Versão (or issue.fixed_version_id)
			category_id - Categoria (or issue.category_id)
			subject - Título
			description - Descrição
			notes - Notas
			comments - Comentário
			done_ratio - % Terminado
			is_private - Privado
			watcher_id - Observador
			updated_by - Atualizado por
			last_updated_by - Última atualização por
			issue_id - Tarefa
			tags - Tags (or issue_tags)

			Atribuição (responsável):
			member_of_group - Responsável pelo grupo
			assigned_to_role - Papel do responsável
			user_id - Usuário

			Datas:
			created_on - Criado em
			updated_on - Alterado em
			closed_on - Concluído
			start_date - Início
			due_date - Data prevista
			spent_on - Data

			Registro de horas:
			estimated_hours - Tempo estimado
			spent_time - Tempo gasto
			hours - Horas
			activity_id - Atividade

			Arquivos:
			attachment - Arquivo
			attachment_description - Descrição do arquivo

			Projeto:
			project.status - Situação do projeto

			Relações entre tarefas:
			relates - Relacionado a
			duplicates - Duplica
			duplicated - Duplicado por
			blocks - Bloqueia
			blocked - Bloqueado por
			precedes - Precede
			follows - Segue
			copied_to - Copiada
			copied_from - Copiado
			parent_id - Tarefa mãe
			child_id - Subtarefas

			Campos personalizados (custom fields):
			
			[Tarefa]
			issue.cf_1 - Tarefa Área
			issue.cf_4 - Tarefa Nº SEI!
			issue.cf_12 - Tarefa Quantidade
			issue.cf_58 - Tarefa Chamado GLPI / 4Biz / Nº SEI
			issue.cf_83 - Tarefa Fase
			issue.cf_96 - Tarefa Serviço (Memora)
			issue.cf_100 - Tarefa Entregável (Memora)
			issue.cf_109 - Tarefa OS (Memora)
			issue.cf_113 - Tarefa Story Points
			issue.cf_114 - Tarefa Classificação da demanda
			
			[Projeto]
			project.cf_78 - Projeto Área gestora
			project.cf_84 - Projeto Gestor / Dono do Produto
			project.cf_85 - Projeto Tipo de produto
			project.cf_87 - Projeto Status / Fase atual
			project.cf_88 - Projeto Bibliotecas Internas
			project.cf_89 - Projeto Integrações com API internas
			project.cf_91 - Projeto Criticidade
			project.cf_92 - Projeto Linguagem / Tecnologia
			project.cf_106 - Projeto Gerente de Projeto
			project.cf_107 - Projeto Áreas da 
			
			[Usuário]
			user.cf_3 - Usuário Unidade Administrativa
			user.cf_11 - Usuário CPF

			Examples:

			Example 1 - Filter issues assigned to user 509 and not in status 2:
			{
			  "status_id": { "op": "!=", "value": "2" },
			  "assigned_to_id": { "op": "=", "value": "509" }
			}

			Example 2 - Filter by custom field "issue.cf_114" equal to "Manutenção" and due between 2025-01-01 and 2025-12-31:
			{
			  "issue.cf_114": { "op": "=", "value": "Manutenção" },
			  "due_date": { "op": "><", "value": { "from": "2025-01-01", "to": "2025-12-31" } }
			}

			Example 3 - Filter all issues created by author 105 in project 22 that contain “erro” in the description:
			{
			  "author_id": { "op": "=", "value": "105" },
			  "project_id": { "op": "=", "value": "22" },
			  "description": { "op": "~", "value": "erro" }
			}

			Output Rule:
			Return only the JSON structure, no explanations or text.
			Output must be a valid JSON object parsable by Java Map<String, FilterCondition>.
			""";


	public RedmineIssueSearchAiFunction() {
		super(NAME, DESCRIPTION, RedmineIssueFilter.class, 
			v -> {
				try {
					
					LOGGER.info("Received filter request: {}", v);
					
					Map<String, FilterCondition> filters;
					filters = RedmineFilterField.fromObject(v);
					
					RedmineApiPropertyUtil propertyUtil = RedmineApiPropertyUtil.getInstance();
			        RedmineApiGateway gateway = new RedmineApiGateway(propertyUtil.getRedmineUrl(), propertyUtil.getRedmineKey());
			        
			        List<TIssueEvidenceInfo> issues = gateway.getIssuesByFilters(filters);
					
			        LOGGER.info("Result found {} issues", issues!=null?issues.size():0);
			        
					return ToolCallResult.builder()
							.message("Redmine issues filtered successfully.")
							.result(Map.of(
				                    STATUS, SUCCESS,
				                    ACTION, "redmine_issues_filtered",
				                    SYSTEM_INSTRUCTION, "Issues filtered successfully. "
				                    		+ "Do not retry again. Proceed with the user's request.",
				                    "issues", issues
				                ))
							.build();
					
				} catch (Exception e) {
					LOGGER.error("Error filtering Redmine issues: {}", e.getMessage(), e);
					return ToolCallResult.builder()
							.message("Error filtering Redmine issues: " + e.getMessage())
							.result(Map.of(
				                    STATUS, ERROR,
				                    ACTION, "redmine_issues_filter_error",
				                    ERROR_MESSAGE, e.getMessage()
				                ))
							.build();
				}				  
			});
	}

}
