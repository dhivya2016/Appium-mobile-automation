package pageobjects;

import io.DesiredCapabilitiesReader;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DriverRegistry {
    private static final String logFilePath = "/tmp/appium-tests.log";
    //private final static String logFilePath = System.getenv("logFilePath");
    private static final String nodeBinaryPath = System.getenv("NODE_BINARY_PATH");
    private static final String appiumBinaryPath = System.getenv("APPIUM_BINARY_PATH");
    private static final String platform = System.getenv("APPIUM_TESTS_APP_PLATFORM");
    private static final String absolutePathToApp = System.getenv("APPIUM_TESTS_APP_PATH");
    private static final String host = System.getenv("APPIUM_TESTS_HOST");
    //private final static int port = Integer.parseInt(System.getenv("APPIUM_TESTS_PORT"));
    private static final int port = 4723;

    private static DesiredCapabilities desiredCapabilities;
    private static AppiumDriverLocalService service;
    private static AppiumDriver<MobileElement> driver;

    public static void startService() {
        getService().start();
    }

    public static void stopService() {
        getService().stop();
    }

    private static AppiumDriverLocalService getService() {
        if(service == null) {
            service = buildService();
        }
        return service;
    }

    public static AppiumDriver<MobileElement> getDriver() {
        if(driver == null || driver.getSessionId() == null) {
            driver = createDriver(service);
        }
        return driver;
    }

    private static AppiumDriverLocalService buildService() {
        if(getPlatform().equals("android") && getListOfConnectedDevices().size()>0){
            return new AppiumServiceBuilder()
                    .withIPAddress(host)
                    .usingPort(port)
                    .withLogFile(new File(logFilePath))
                    .withArgument(GeneralServerFlag.LOG_LEVEL, "info")
                    .withArgument(GeneralServerFlag.LOG_TIMESTAMP)
                    .withArgument(GeneralServerFlag.LOCAL_TIMEZONE)
                    .usingDriverExecutable(new File(nodeBinaryPath))
                    .withAppiumJS(new File(appiumBinaryPath))
                    .build();
        } else{
            return new AppiumServiceBuilder()
                    .withIPAddress(host)
                    .usingPort(port)
                    .withLogFile(new File(logFilePath))
                    .withArgument(GeneralServerFlag.LOG_LEVEL, "info")
                    .withArgument(GeneralServerFlag.LOG_TIMESTAMP)
                    .withArgument(GeneralServerFlag.LOCAL_TIMEZONE)
                    .usingDriverExecutable(new File(nodeBinaryPath))
                    .withAppiumJS(new File(appiumBinaryPath))
                    .build();
        }
    }

    public static List<String> getListOfConnectedDevices() {
        List<String> listOfDevices = new ArrayList<>();

        try {
            Process proc = Runtime.getRuntime().exec("sh list_devices.sh");
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    proc.getInputStream()));
            String line;

            try {
                proc.waitFor();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }

            Pattern pattern = Pattern.compile("^([a-zA-Z0-9\\-]+)(\\s+)(device)");
            Matcher matcher;
            while ((line = in.readLine()) != null) {
                if (line.matches(pattern.pattern())) {
                    matcher = pattern.matcher(line);
                    if (matcher.find()) {
                        listOfDevices.add(matcher.group(1));
                    }
                }
            }
            return listOfDevices;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return Collections.emptyList();
    }

    public static String getPlatform() {
        return platform;
    }

    public static String getAbsolutePathToApp() { return absolutePathToApp; }

    private static AppiumDriver<MobileElement> createDriver(AppiumDriverLocalService service) {
        if(getPlatform().equals("android")) {
            return new AndroidDriver<>(service, getDesiredCapabilities());
        } else {
            return new IOSDriver<>(service, getDesiredCapabilities());
        }
    }

    public static DesiredCapabilities getDesiredCapabilities() {
        if(desiredCapabilities == null) {
            desiredCapabilities = new DesiredCapabilitiesReader(platform, absolutePathToApp).getDesiredCapabilities();
        }
        return desiredCapabilities;
    }
}
