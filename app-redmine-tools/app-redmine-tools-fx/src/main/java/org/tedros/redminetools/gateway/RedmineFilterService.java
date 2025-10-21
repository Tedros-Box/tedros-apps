package org.tedros.redminetools.gateway;

import java.io.IOException;
import java.util.Map;

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

    // 🔹 Exemplo de uso isolado
    public static void main(String[] args) throws Exception {
        String json = """
        {
          "status_id": { "op": "!=", "value": "2" },
          "assigned_to_id": { "op": "=", "value": "509" },
          "cf_30": { "op": "=", "value": "Entregue" },
          "due_date": { "value": { "from": "2025-01-01", "to": "2025-12-31" } }
        }
        """;

        RedmineFilterService service = new RedmineFilterService();
        Params params = service.parseFilterJsonToParams(json);
        
        params.getList().stream()
        .forEach(p->System.out.println(p.getName() +" = "+ p.getValue() ));

        
    }
}

