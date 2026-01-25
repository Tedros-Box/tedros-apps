package org.tedros.ifood.component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.tedros.ifood.api.client.IFoodEventsClient;
import org.tedros.ifood.api.client.IFoodOrderClient;
import org.tedros.ifood.api.model.OrderDetail;
import org.tedros.ifood.api.model.PollingEvent;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.util.Duration;

/**
 * Main dashboard component for monitoring iFood orders.
 */
public class IFoodOrderDashboard extends BorderPane {

    private final IFoodEventsClient eventsClient;
    private final IFoodOrderClient orderClient;

    private final FlowPane cardsPane;
    private final VBox detailSidebar;
    private final WebView webView;
    private final WebEngine webEngine;
    private final ObservableList<OrderEntry> orders = FXCollections.observableArrayList();
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private PollingService pollingService;

    public IFoodOrderDashboard(IFoodEventsClient eventsClient, IFoodOrderClient orderClient) {
        this.eventsClient = eventsClient;
        this.orderClient = orderClient;

        this.cardsPane = new FlowPane();
        this.detailSidebar = new VBox();
        this.webView = new WebView();
        this.webEngine = webView.getEngine();

        setupUI();
        startPolling();

        // Listen to changes in orders list to refresh UI
        orders.addListener((javafx.collections.ListChangeListener.Change<? extends OrderEntry> c) -> {
            Platform.runLater(this::refreshCards);
        });
    }

    private void setupUI() {
        // Main area with cards
        cardsPane.setPadding(new Insets(15));
        cardsPane.setHgap(15);
        cardsPane.setVgap(15);
        cardsPane.setAlignment(Pos.TOP_LEFT);

        ScrollPane scrollPane = new ScrollPane(cardsPane);
        scrollPane.setFitToWidth(true);
        setCenter(scrollPane);

        // Sidebar for details
        detailSidebar.setPrefWidth(450);
        detailSidebar.setPadding(new Insets(0));
        detailSidebar.setSpacing(0);
        detailSidebar.setStyle("-fx-background-color: #FAFAFA; -fx-border-color: #DDDDDD; -fx-border-width: 0 0 0 1;");

        webView.setContextMenuEnabled(false);
        VBox.setVgrow(webView, Priority.ALWAYS);
        webEngine.load(getClass().getResource("OrderViewer.html").toExternalForm());

        detailSidebar.getChildren().add(webView);

        setRight(detailSidebar);

        // Hide sidebar if empty initially
        detailSidebar.setVisible(false);
        detailSidebar.setManaged(false);
    }

    private void startPolling() {
        pollingService = new PollingService();
        pollingService.setPeriod(Duration.minutes(10));
        pollingService.setRestartOnFailure(true);
        pollingService.setOnSucceeded(e -> {
            List<OrderEntry> newOrders = pollingService.getValue();
            if (newOrders != null) {
                orders.setAll(newOrders);
            }
        });
        pollingService.setOnFailed(e -> {
            Throwable ex = pollingService.getException();
            System.err.println("Polling failed: " + ex.getMessage());
            // Optionally show error in UI
        });
        pollingService.start();
    }

    private void refreshCards() {
        cardsPane.getChildren().clear();
        for (OrderEntry entry : orders) {
            OrderCard card = new OrderCard(entry);
            card.setOnMouseClicked(e -> showDetails(entry));
            cardsPane.getChildren().add(card);
        }
    }

    private void showDetails(OrderEntry entry) {
        if (entry == null || entry.getDetail() == null) {
            detailSidebar.setVisible(false);
            detailSidebar.setManaged(false);
            return;
        }

        detailSidebar.setVisible(true);
        detailSidebar.setManaged(true);

        String json = gson.toJson(entry.getDetail());
        webEngine.executeScript("updateDetailView(" + json + ")");
    }

    /**
     * Service to fetch events and details in background.
     */
    private class PollingService extends ScheduledService<List<OrderEntry>> {
        @Override
        protected Task<List<OrderEntry>> createTask() {
            return new Task<>() {
                @Override
                protected List<OrderEntry> call() throws Exception {
                    List<PollingEvent> events = eventsClient.getOrderEvents();
                    if (events == null || events.isEmpty()) {
                        return new ArrayList<>();
                    }

                    return events.stream().map(event -> {
                        try {
                            OrderDetail detail = orderClient.getOrderDetails(event.getOrderId());
                            return new OrderEntry(event, detail);
                        } catch (Exception e) {
                            System.err.println(
                                    "Error fetching details for order " + event.getOrderId() + ": " + e.getMessage());
                            return new OrderEntry(event, null);
                        }
                    }).collect(Collectors.toList());
                }
            };
        }
    }

    public void stopPolling() {
        if (pollingService != null) {
            pollingService.cancel();
        }
    }
}
