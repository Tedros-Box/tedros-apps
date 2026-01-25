package org.tedros.it.tools.redmine.gateway;

import java.io.IOException;
import java.util.Map;

import org.tedros.it.tools.redmine.ai.model.FilterCondition;
import org.tedros.it.tools.redmine.ai.model.RedmineFilterField;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taskadapter.redmineapi.Params;

public class RedmineFilterService {

    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * Recebe um JSON de filtros vindo do front-end e converte em parâmetros
     * compatíveis com a API do Redmine.
     */
    public Params parseFilterJsonToParams(String json) throws IOException {
        // Converte o JSON para um Map<String, Object>
        Map<String, Object> raw = mapper.readValue(json, new TypeReference<>() {});
        
        // Converte para Map tipado com FilterCondition
        Map<String, FilterCondition> filters = RedmineFilterField.fromRawMap(raw);
        
        // Cria os parâmetros que serão enviados ao Redmine
        Params params = new Params().add("set_filter", "1");

        // Monta dinamicamente os parâmetros conforme a estrutura do Redmine
        for (Map.Entry<String, FilterCondition> entry : filters.entrySet()) {
            String field = entry.getKey();
            FilterCondition condition = entry.getValue();

            params.add("f[]", field);
            params.add("op[" + field + "]", condition.getOperator());

            String[] values = condition.getValues();
            if (values != null) {
                for (String v : values) {
                    params.add("v[" + field + "][]", v);
                }
            }
        }

        // Adiciona ordenação padrão (opcional)
        params.add("sort", "id:desc");

        return params;
    }

}

