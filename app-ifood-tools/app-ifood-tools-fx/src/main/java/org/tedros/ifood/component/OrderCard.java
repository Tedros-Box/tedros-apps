package org.tedros.ifood.component;

import org.tedros.ifood.api.model.OrderDetail;
import org.tedros.ifood.api.model.PollingEvent;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * A UI component representing a single iFood order card.
 */
public class OrderCard extends VBox {

    private final OrderEntry entry;

    public OrderCard(OrderEntry entry) {
        this.entry = entry;
        setupUI();
    }

    private void setupUI() {
        setPadding(new Insets(10));
        setSpacing(5);
        setPrefSize(180, 150);

        // Styling based on code/status
        String status = entry.getEvent() != null ? entry.getEvent().getFullCode() : "UNKNOWN";
        applyStatusStyle(status);

        Label lblCode = new Label("Code: " + (entry.getEvent() != null ? entry.getEvent().getFullCode() : "N/A"));
        lblCode.setFont(Font.font("System", FontWeight.BOLD, 12));

        Label lblSalesChannel = new Label(
                "Channel: " + (entry.getEvent() != null ? entry.getEvent().getSalesChannel() : "N/A"));

        OrderDetail detail = entry.getDetail();
        Label lblDisplayId = new Label("ID: " + (detail != null ? detail.getDisplayId() : "N/A"));
        Label lblOrderType = new Label("Type: " + (detail != null ? detail.getOrderType() : "N/A"));
        Label lblCustomer = new Label("Customer: "
                + (detail != null && detail.getCustomer() != null ? detail.getCustomer().getName() : "N/A"));
        lblCustomer.setWrapText(true);

        getChildren().addAll(lblCode, lblSalesChannel, lblDisplayId, lblOrderType, lblCustomer);

        // Hover effect
        setOnMouseEntered(e -> setScaleX(1.05));
        setOnMouseEntered(e -> {
            setScaleX(1.05);
            setScaleY(1.05);
        });
        setOnMouseExited(e -> {
            setScaleX(1.0);
            setScaleY(1.0);
        });
    }

    private void applyStatusStyle(String status) {
        String baseStyle = "-fx-border-radius: 5; -fx-background-radius: 5; -fx-border-width: 2; -fx-border-color: #333333; ";
        String bgColor;

        if (status == null) {
            bgColor = "#FFFFFF";
        } else if (status.contains("CANCELLED")) {
            bgColor = "#FFEBEE"; // Light Red
            baseStyle += "-fx-border-color: #D32F2F;";
        } else if (status.contains("CONCLUDED")) {
            bgColor = "#E8F5E9"; // Light Green
            baseStyle += "-fx-border-color: #388E3C;";
        } else if (status.contains("PLACED") || status.contains("NEW")) {
            bgColor = "#FFFDE7"; // Light Yellow
            baseStyle += "-fx-border-color: #FBC02D;";
        } else {
            bgColor = "#F5F5F5";
        }

        setStyle(baseStyle + "-fx-background-color: " + bgColor + ";");
    }

    public OrderEntry getEntry() {
        return entry;
    }
}
