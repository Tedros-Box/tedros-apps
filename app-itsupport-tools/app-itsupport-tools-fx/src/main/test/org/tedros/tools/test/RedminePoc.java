package org.tedros.tools.test;

import org.tedros.it.tools.redmine.gateway.RedmineApiGateway;

// ... other imports
public class RedminePoc {
    public static void main(String[] args) {
    	
    	String token = System.getenv("REDMINE_TOKEN");
    	String url = "https://redmine.detran.go.gov.br/";
    	
    	RedmineApiGateway gateway = new RedmineApiGateway(url, token);
    	gateway.addIssueJournal(509, 216199);
    	//System.out.println(gateway.getTIssueEvidenceInfo(214840));

    }
}

