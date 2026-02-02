package org.tedros.it.tools.gmud.ai.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GmudItemModel {

	private Integer stepOrder;

    private String action;

    private PersonModel responsible;

    private String status;
}
