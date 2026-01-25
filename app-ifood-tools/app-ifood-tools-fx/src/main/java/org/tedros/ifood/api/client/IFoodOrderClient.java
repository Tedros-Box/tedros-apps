package org.tedros.ifood.api.client;

import org.tedros.ifood.api.model.OrderDetail;
import org.tedros.ifood.api.model.VirtualBag;

import feign.Headers;
import feign.Param;
import feign.RequestLine;

@Headers({ "Content-Type: application/json", "Accept: application/json" })
public interface IFoodOrderClient {

    /**
     * Get details for a given order from its id
     * 
     * @param id The UUID of the order
     * @return Full information on the order
     */
    @RequestLine("GET /orders/{id}")
    OrderDetail getOrderDetails(@Param("id") String id);

    /**
     * Get details for a given groceries order from its id
     * 
     * @param id The UUID of the order
     * @return Virtual bag details
     */
    @RequestLine("GET /orders/{id}/virtual-bag")
    VirtualBag getOrderVirtualBag(@Param("id") String id);

    /**
     * Confirm an order. It is mandatory to consult the order details before
     * confirming.
     * 
     * @param id The UUID of the order
     */
    @RequestLine("POST /orders/{id}/confirm")
    void confirmOrder(@Param("id") String id);

    /**
     * Inform the start of preparation of the order.
     * 
     * @param id The UUID of the order
     */
    @RequestLine("POST /orders/{id}/startPreparation")
    void startPreparation(@Param("id") String id);

}
