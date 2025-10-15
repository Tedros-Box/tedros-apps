package org.tedros.redminetools.poc;

import com.taskadapter.redmineapi.RedmineManager;
import com.taskadapter.redmineapi.RedmineManagerFactory;

// ... other imports
public class RedmineExampleWithPassword {
    public static void main(String[] args) {
        String redmineURI = "https://your-redmine-instance.com";
        String login = "your_username";
        String password = "your_password";

        RedmineManager mgr = RedmineManagerFactory.createWithUserAuth(redmineURI, login, password);
        // ... now you can use 'mgr' to perform actions
    }
}

