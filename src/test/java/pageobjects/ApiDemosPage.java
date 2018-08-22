package pageobjects;


import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSFindBy;
import datamodel.UserProfileReader;

import static org.junit.Assert.assertTrue;

public class ApiDemosPage extends Page{

    @AndroidFindBy(id = "test")
    @iOSFindBy(id="test")
    public MobileElement test;


    public void validateTest(String type){

        Page.waitForElement(test);
        assertTrue(test.isDisplayed());
    }
}
