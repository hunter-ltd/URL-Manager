package com.hunterltd.URLManager.Utilities;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class UserData {
    private static Map<String, String>[] userDataMap;
    private static final File dataFile;
    private static final Path dataPath;
    static  {
        String osName = System.getProperty("os.name"),
                home = System.getProperty("user.home"),
                appName = "URL Manager";
        if (osName.contains("Windows")) {
            dataPath = Paths.get(home,
                    "AppData",
                    "Local",
                    appName);
        } else if (osName.contains("Linux")) {
            dataPath = Paths.get(home,
                    ".config",
                    appName);
        }
        else {
            dataPath = Paths.get(home,
                    "Library",
                    "Application Support",
                    appName);
        }
        dataFile = new File(Paths.get(dataPath.toString(), "userData.json").toString());
        if (!(new File(dataPath.toString()).mkdirs())) {
            System.out.println(dataPath.toString() + " could not be created or already exists");
        }

        try {
            if (!dataFile.createNewFile()) {
                readUserData();
            } else {
                userDataMap = new Map[1];
                userDataMap[0] = new HashMap<>();
                userDataMap[0].put("name", "Example");
                userDataMap[0].put("url", "https://dablenparty.github.io");

                JSONArray jsonDataArray = new JSONArray();
                jsonDataArray.add(userDataMap[0]);

                writeUserData(jsonDataArray);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private static void readUserData() throws IOException, ParseException {
        JSONArray jsonDataArray = (JSONArray) new JSONParser().parse(new FileReader(dataFile));
        userDataMap = new Map[jsonDataArray.size()];
        for (int i = 0; i < jsonDataArray.size(); i++) {
            userDataMap[i] = (Map<String, String>) jsonDataArray.get(i);
        }
        Arrays.sort(userDataMap, Comparator.comparing((Map<String, String> obj) -> obj.get("name").toLowerCase()));
    }

    private static void writeUserData(JSONArray data) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(dataFile);
        writer.write(data.toJSONString());
        writer.flush();
        writer.close();
    }

    private static boolean urlIsValid(String urlString) {
        try {
            URL newURL = new URL(urlString);
            newURL.toURI();
            return true;
        } catch (MalformedURLException | URISyntaxException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Map<String, String>[] getUserDataMap() {
        return userDataMap;
    }

    public static String[] getNames() {
        String[] names = new String[userDataMap.length];
        for (int i = 0; i < userDataMap.length; i++) {
            names[i] = userDataMap[i].get("name");
        }
        return names;
    }

    public static URL[] getURLs() {
        URL[] urls = new URL[userDataMap.length];
        for (int i = 0; i < userDataMap.length; i++) {
            try {
                urls[i] = new URL(userDataMap[i].get("url"));
            } catch (MalformedURLException ignored) {}
        }
        return urls;
    }

    public static boolean addNewItem(String newName, String newURLString) {
        try {
            if (!urlIsValid(newURLString)) {
                return true;
            }

            Map<String, String> newMap = new HashMap<>();
            newMap.put("name", newName);
            newMap.put("url", newURLString);

            JSONArray jsonDataArray = (JSONArray) new JSONParser().parse(new FileReader(dataFile));
            jsonDataArray.add(newMap);

            writeUserData(jsonDataArray);
            readUserData();

            return false;
        } catch (IOException | ParseException e) {
            e.printStackTrace();
            return true;
        }
    }

    public static void removeItem(int[] indices) {
        try {
            JSONArray newJSONData = new JSONArray();
            for (int i = 0; i < userDataMap.length; i++) {
                if (Arrays.binarySearch(indices, i) < 0) {
                    newJSONData.add(userDataMap[i]);
                }
            }
            writeUserData(newJSONData);
            readUserData();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

}
