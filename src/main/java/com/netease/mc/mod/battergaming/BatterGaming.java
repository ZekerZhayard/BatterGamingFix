package com.netease.mc.mod.battergaming;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonObject;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import org.lwjgl.opengl.Display;

@Mod(modid = "battergaming", name = "battergaming", version = "0.0.1", acceptedMinecraftVersions = "[1.8.8,1.8.9]")
public class BatterGaming {
    private static BatterGaming antiCheat;
    private static Minecraft minecraft = Minecraft.getMinecraft();
    
    public List<String> modsList = new ArrayList<String>();
    public JsonObject jsonObject;
    private List<String> fileName = new ArrayList<String>();
    private String path = BatterGaming.class.getProtectionDomain().getCodeSource().getLocation().getFile();

    public static BatterGaming getInstance() {
        return BatterGaming.antiCheat;
    }

    public static Minecraft getMinecraft() {
        return BatterGaming.minecraft;
    }

    public BatterGaming() {
        BatterGaming.antiCheat = this;
        this.fileName.add("assets");
        this.fileName.add("config");
        this.fileName.add("cachedImages");
        this.fileName.add("crash-reports");
        this.fileName.add("libraries");
        this.fileName.add("logs");
        this.fileName.add("resourcepacks");
        this.fileName.add("saves");
        this.fileName.add("shaderpacks");
        this.fileName.add("versions");
    }

    public JsonObject getJsonObject() {
        return this.jsonObject;
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new EventHandler());
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) throws IOException {
        JsonObject jsonObject = new JsonObject();
        try {
            this.path = URLDecoder.decode(this.path, "UTF-8");
            this.path = this.path.split("!/")[0];
            this.path = this.path.substring(6, this.path.length());
            String selfHash = HashUtils.fileName2Hash(this.path);
            jsonObject.addProperty("base64", selfHash);
        } catch (UnsupportedEncodingException e) {
        }
        jsonObject.addProperty("cltitle", Display.getTitle());
        jsonObject.addProperty("isLiquidbounce", false);
        File rootFile = new File("");
        try {
            String rootPath = rootFile.getCanonicalPath();
            if (this.readFile(rootPath)) {
                for (String subPath : this.modsList) {
                    this.checkLiquidBounce(subPath, jsonObject);
                }
            }
        } catch (IOException ex) {
        }
        this.jsonObject = jsonObject;
    }

    public boolean readFile(String filePath) throws IOException {
        try {
            File file = new File(filePath);
            if (!file.isDirectory()) {
                if (file.getName().endsWith(".jar")) {
                    this.modsList.add(file.getAbsolutePath());
                }
            } else {
                String[] listFiles = file.list();
                for (int i = 0; i < listFiles.length; ++i) {
                    File subFile = new File(filePath + "\\" + listFiles[i]);
                    if (!subFile.isDirectory()) {
                        if (subFile.getName().endsWith(".jar")) {
                            this.modsList.add(subFile.getAbsolutePath());
                        }
                    } else if (subFile.isDirectory() && !this.fileName.contains(subFile.getName())) {
                        this.readFile(filePath + "\\" + listFiles[i]);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("readfile()   Exception:" + e.getMessage());
        }
        return true;
    }

    private void checkLiquidBounce(String subPath, JsonObject jsonObject) {
        if (subPath.endsWith(".jar")) {
            try {
                String str = "mixins.mcwrapper.json";
                URL url = new URL("jar:file:/" + subPath + "!/" + str);
                if (!jsonObject.has("path")) {
                    jsonObject.addProperty("path", str);
                }
                if (url.openStream() != null) {
                    jsonObject.addProperty("isLiquidbounce", true);
                    this.jsonObject = jsonObject;
                }
            } catch (MalformedURLException ex) {
            } catch (IOException ex2) {
            }
        }
    }
}
