package org.tedros.ifood.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpeningHours {
	
	private String id;
    
    private String dayOfWeek;
    
    private String start;

    private Integer duration;

}
