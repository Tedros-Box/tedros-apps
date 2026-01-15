package org.tedros.it.tools.redmine.component;

import org.tedros.it.tools.redmine.gateway.RedmineApiGateway;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RedmineViewTestArgs extends Application {
	
	private static String token = System.getenv("REDMINE_TOKEN"); 
	private static String url = "https://redmine.detran.go.gov.br/";
	private static RedmineApiGateway gateway = new RedmineApiGateway(url, token);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            
            RedmineIssueSearchComponent component = new RedmineIssueSearchComponent(gateway);

            Scene scene = new Scene(component, 1000, 600);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Redmine Issue Search Component Test");
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
