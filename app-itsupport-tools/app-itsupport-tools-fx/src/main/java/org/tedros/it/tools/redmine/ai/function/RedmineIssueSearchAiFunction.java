package org.tedros.it.tools.redmine.ai.function;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.tedros.ai.function.TFunction;
import org.tedros.ai.function.model.Response;
import org.tedros.it.tools.redmine.ai.model.FilterCondition;
import org.tedros.it.tools.redmine.ai.model.RedmineFilterField;
import org.tedros.it.tools.redmine.ai.model.RedmineIssueFilter;
import org.tedros.it.tools.redmine.api.model.TIssue;
import org.tedros.it.tools.redmine.gateway.RedmineApiGateway;
import org.tedros.util.TLoggerUtil;

import com.fasterxml.jackson.databind.ObjectMapper;

public class RedmineIssueSearchAiFunction /* extends TFunction<RedmineIssueFilter> */ {
	/*
	private static final Logger LOGGER = TLoggerUtil.getLogger(RedmineIssueSearchAiFunction.class);
	
	private static final String PROMPT = """
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
			status_id - Situação
			project_id - Projeto
			tracker_id - Tipo
			priority_id - Prioridade
			author_id - Autor
			assigned_to_id - Atribuído para
			fixed_version_id - Versão
			subject - Título
			description - Descrição
			notes - Notas
			done_ratio - % Terminado
			is_private - Privado
			watcher_id - Observador
			updated_by - Atualizado por
			last_updated_by - Última atualização por
			issue_id - Tarefa
			tags - Tags

			Atribuição (responsável):
			member_of_group - Responsável pelo grupo
			assigned_to_role - Papel do responsável

			Datas:
			created_on - Criado em
			updated_on - Alterado em
			closed_on - Concluído
			start_date - Início
			due_date - Data prevista

			Registro de horas:
			estimated_hours - Tempo estimado
			spent_time - Tempo gasto

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
			cf_80 - OS Globalweb
			cf_62 - Origem
			cf_2 - SEI
			cf_3 - Ambiente
			cf_5 - Janela
			cf_6 - Número do protocolo
			cf_41 - Observações
			cf_59 - Perfil Exigido
			cf_60 - Serviços
			cf_61 - Qtd. Itens Serviço
			cf_75 - HPA
			cf_71 - Classificação
			cf_30 - Relatório G4F (OS)

			Examples:

			Example 1 - Filter issues assigned to user 509 and not in status 2:
			{
			  "status_id": { "op": "!=", "value": "2" },
			  "assigned_to_id": { "op": "=", "value": "509" }
			}

			Example 2 - Filter by custom field "cf_30" equal to "Entregue" and due between 2025-01-01 and 2025-12-31:
			{
			  "cf_30": { "op": "=", "value": "Entregue" },
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
		super("filter_redmine_issues", PROMPT, RedmineIssueFilter.class, 
			v -> {
				try {
					
					ObjectMapper mapper = new ObjectMapper();
					
					LOGGER.info("Filtros recebidos: {}", mapper.writeValueAsString(v));
					
					Map<String, FilterCondition> filters;
					filters = RedmineFilterField.fromObject(v);
					
					RedmineApiPropertyUtil propertyUtil = RedmineApiPropertyUtil.getInstance();
			        RedmineApiGateway gateway = new RedmineApiGateway(propertyUtil.getRedmineUrl(), propertyUtil.getRedmineKey());
			        
			        List<TIssue> issues = gateway.getIssuesByFilters(filters);
					
			        LOGGER.info("Resultado da pesquisa: {}", mapper.writeValueAsString(issues));
			        
					return new Response("Result list", issues);
					
				} catch (Exception e) {
					return new Response("An error occurred: "+e.getMessage());
				}
				  
			});
		
		
		
	}
*/
}
