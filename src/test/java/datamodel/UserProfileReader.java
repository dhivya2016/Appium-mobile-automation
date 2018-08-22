package datamodel;

import gherkin.deps.com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;

public class UserProfileReader {
    private static final Gson GSON = new Gson();
    public UserProfile getUserProfile(String type) {
        try {
            return GSON.fromJson(new FileReader(readFile(type)), UserProfile.class);
        }
        catch(FileNotFoundException | URISyntaxException | NullPointerException e) {
            e.printStackTrace();
        }

        return new UserProfile("", "");
    }

    private File readFile(String type) throws FileNotFoundException,
                                                        URISyntaxException,
                                                        NullPointerException {
        final String fileName = "datamodel" + File.separator + type.replaceAll(" ", "_").toString() + ".json";
        final String path = this.getClass().getClassLoader().getResource(fileName).getPath();
        return new File(path);
    }
}
