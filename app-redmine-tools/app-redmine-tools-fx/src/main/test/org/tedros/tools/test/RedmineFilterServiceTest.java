package org.tedros.tools.test;

import org.tedros.it.tools.redmine.gateway.RedmineFilterService;

import com.taskadapter.redmineapi.Params;

public class RedmineFilterServiceTest {
   
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

