package org.tedros.it.tools.employeeactivity.ai.function;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DailyActivitySummary {

    @JsonProperty("date")
    private String date; // Formatado em YYYY-MM-DD para a IA
    
    @JsonProperty("total_mouse_events_day")
    private long totalMouseEvents;
    
    @JsonProperty("total_keyboard_events_day")
    private long totalKeyboardEvents;
    
    @JsonProperty("applications_used")
    private List<AppUsageSummary> appUsages;
}
