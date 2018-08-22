package stepdefs;

import cucumber.api.java8.En;
import datamodel.UserProfileReader;
import pageobjects.ApiDemosPage;

public class ApiDemosStepdefs implements En {
    public ApiDemosPage apiDemosPage;
    public UserProfileReader userProfileReader;

    public ApiDemosStepdefs(ApiDemosPage apiDemosPage, UserProfileReader userProfileReader) {
        this.apiDemosPage = apiDemosPage;
        this.userProfileReader = userProfileReader;
        {


            Given("^I launch api demos app$", () -> {

                System.out.println("App launched successfully");

            });
        }

    }
}