package org.tedros.it.tools.model;

import java.io.Serializable;

public record ActivitySummaryDTO(
        String windowTitle,
        long activeCount,
        long inactiveCount) implements Serializable {
}
