package org.tedros.ifood.component;

import org.tedros.ifood.api.model.OrderDetail;
import org.tedros.ifood.api.model.PollingEvent;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A simple wrapper to hold both the polling event and the full order details.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntry {
    private PollingEvent event;
    private OrderDetail detail;
}
