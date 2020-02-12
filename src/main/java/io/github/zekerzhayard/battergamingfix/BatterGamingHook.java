package io.github.zekerzhayard.battergamingfix;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class BatterGamingHook {
    public static URL encodeURL(URL url) throws Exception {
        return new URI(url.getProtocol(), url.getHost(), "/" + url.getPath(), url.getQuery(), null).toURL();
    }

    public static String hookPacketBuffer(String str) {
        System.out.println("BEFORE | " + str);
        Path configPath = Paths.get("config", "battergaming.json");
        if (Files.exists(configPath)) {
            try {
                JsonObject oldJsonObject = new JsonParser().parse(str).getAsJsonObject();
                JsonObject newJsonObject = new JsonParser().parse(String.join("", Files.readAllLines(configPath))).getAsJsonObject();
                newJsonObject.entrySet().forEach(e -> oldJsonObject.add(e.getKey(), e.getValue()));
                str = oldJsonObject.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("AFTER  | " + str);
        return str;
    }
}
