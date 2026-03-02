package org.tedros.it.tools.employeeactivity.ai.function;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppUsageSummary {
    
    @JsonProperty("application_window")
    private String appName;
    
    @JsonProperty("mouse_events")
    private long mouseEvents;
    
    @JsonProperty("keyboard_events")
    private long keyboardEvents;
    
    @JsonProperty("interaction_logs_count")
    private int logEntries; // Ajuda a IA a estimar o tempo. Ex: se captura a cada 1 min, 60 logs = 1 hora.
}
