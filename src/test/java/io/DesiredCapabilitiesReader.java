package io;

import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DesiredCapabilitiesReader {
    private final String platform;
    private final String absolutePathToApp;

    public DesiredCapabilitiesReader(String platform, String absolutePathToApp) {
        this.platform = platform;
        this.absolutePathToApp = absolutePathToApp;
    }

    public DesiredCapabilities getDesiredCapabilities() {
        final Properties properties = readProperties("desired_capabilities_" + platform + ".properties");
        final DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, properties.getProperty("automation.instrumentation"));
        desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, properties.getProperty(platform));
        desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, properties.getProperty("platform.version"));
        desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, properties.getProperty("device.name"));
        desiredCapabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, properties.getProperty("new.command.timeout"));
        desiredCapabilities.setCapability(MobileCapabilityType.APP, absolutePathToApp);
        desiredCapabilities.setCapability(MobileCapabilityType.BROWSER_NAME, properties.getProperty("browser.name"));
        desiredCapabilities.setCapability(MobileCapabilityType.UDID, properties.getProperty("device.udid"));
        desiredCapabilities.setCapability(MobileCapabilityType.LANGUAGE, properties.getProperty("language"));
        desiredCapabilities.setCapability(MobileCapabilityType.LOCALE, properties.getProperty("locale"));
        desiredCapabilities.setCapability(MobileCapabilityType.ORIENTATION, properties.getProperty("orientation"));
        desiredCapabilities.setCapability(MobileCapabilityType.AUTO_WEBVIEW, properties.getProperty("auto.webview"));
        desiredCapabilities.setCapability(MobileCapabilityType.FULL_RESET, properties.getProperty("full.reset"));
        desiredCapabilities.setCapability(MobileCapabilityType.NO_RESET, properties.getProperty("no.reset"));
        desiredCapabilities.setCapability(IOSMobileCapabilityType.AUTO_ACCEPT_ALERTS, properties.getProperty("auto.accept.alerts"));
        desiredCapabilities.setCapability("xcodeOrgId", "9NPUEMB2MW");
        desiredCapabilities.setCapability("xcodeSigningId", "iPhone Developer");
        return desiredCapabilities;
    }

    private Properties readProperties(String fileName) {
        final Properties properties = new Properties();
        try {
            final InputStream inputStream = ClassLoader.getSystemResource(fileName).openStream();
            properties.load(inputStream);
            inputStream.close();
        } catch(IOException e) {
            System.err.println("Could not read desired capabilities file: " +  fileName);
        }
        return properties;
    }
}
