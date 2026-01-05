package org.tedros.ifood.api.client;

import java.util.List;

import org.tedros.ifood.api.model.PollingEvent;

import feign.Headers;
import feign.RequestLine;

@Headers({ "Content-Type: application/json", "Accept: application/json" })
public interface IFoodEventsClient {

    @RequestLine("GET /events:polling")
    List<PollingEvent> getOrderEvents();

    @RequestLine("POST /events/acknowledgment")
    void acknowledgeEvents(List<PollingEvent> events);
}
