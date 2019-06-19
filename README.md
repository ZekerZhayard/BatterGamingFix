# BatterGamingFix

修复网易`Minecraft`花雨庭服务器`battergaming`两个问题

#### >>>>由于`.minecraft`文件夹路径或者`.minecraft`下的jar包文件名中含有`#`、`%`等`URL`特殊字符引起的崩溃
```
java.lang.IllegalArgumentException
	at sun.net.www.ParseUtil.decode(Unknown Source)
	at sun.net.www.protocol.file.Handler.openConnection(Unknown Source)
	at sun.net.www.protocol.file.Handler.openConnection(Unknown Source)
	at java.net.URL.openConnection(Unknown Source)
	at sun.net.www.protocol.jar.JarURLConnection.<init>(Unknown Source)
	at sun.net.www.protocol.jar.Handler.openConnection(Unknown Source)
	at java.net.URL.openConnection(Unknown Source)
	at java.net.URL.openStream(Unknown Source)
	at com.netease.mc.mod.battergaming.iiiiIIIIII.ALLATORIxDEMO(y:184)
	at com.netease.mc.mod.battergaming.iiiiIIIIII.ALLATORIxDEMO(y:188)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(Unknown Source)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(Unknown Source)
	at java.lang.reflect.Method.invoke(Unknown Source)
	at net.minecraftforge.fml.common.FMLModContainer.handleModStateEvent(FMLModContainer.java:560)
	at sun.reflect.GeneratedMethodAccessor3.invoke(Unknown Source)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(Unknown Source)
	at java.lang.reflect.Method.invoke(Unknown Source)
	at com.google.common.eventbus.EventSubscriber.handleEvent(EventSubscriber.java:74)
	at com.google.common.eventbus.SynchronizedEventSubscriber.handleEvent(SynchronizedEventSubscriber.java:47)
	at com.google.common.eventbus.EventBus.dispatch(EventBus.java:322)
	at com.google.common.eventbus.EventBus.dispatchQueuedEvents(EventBus.java:304)
	at com.google.common.eventbus.EventBus.post(EventBus.java:275)
	at net.minecraftforge.fml.common.LoadController.sendEventToModContainer(LoadController.java:211)
	at net.minecraftforge.fml.common.LoadController.propogateStateMessage(LoadController.java:189)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(Unknown Source)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(Unknown Source)
	at java.lang.reflect.Method.invoke(Unknown Source)
	at com.google.common.eventbus.EventSubscriber.handleEvent(EventSubscriber.java:74)
	at com.google.common.eventbus.SynchronizedEventSubscriber.handleEvent(SynchronizedEventSubscriber.java:47)
	at com.google.common.eventbus.EventBus.dispatch(EventBus.java:322)
	at com.google.common.eventbus.EventBus.dispatchQueuedEvents(EventBus.java:304)
	at com.google.common.eventbus.EventBus.post(EventBus.java:275)
	at net.minecraftforge.fml.common.LoadController.distributeStateMessage(LoadController.java:118)
	at net.minecraftforge.fml.common.Loader.initializeMods(Loader.java:742)
	at net.minecraftforge.fml.client.FMLClientHandler.finishMinecraftLoading(FMLClientHandler.java:310)
	at net.minecraft.client.Minecraft.func_71384_a(Minecraft.java:497)
	at net.minecraft.client.Minecraft.func_99999_d(Minecraft.java:331)
	at net.minecraft.client.main.Main.main(SourceFile:124)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(Unknown Source)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(Unknown Source)
	at java.lang.reflect.Method.invoke(Unknown Source)
	at net.minecraft.launchwrapper.Launch.launch(Launch.java:146)
	at net.minecraft.launchwrapper.Launch.main(Launch.java:25)
```

反编译反混淆相关的代码：（`com/netease/mc/mod/battergaming/BatterGaming.java`）
```java
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
```

只需要作出以下修改即可：
```diff
    private void checkLiquidBounce(String subPath, JsonObject jsonObject) {
        if (subPath.endsWith(".jar")) {
            try {
                String str = "mixins.mcwrapper.json";
-                URL url = new URL("jar:file:/" + subPath + "!/" + str);
+                URL url = new URL("jar:file:/" + java.net.URLEncode.encode(subPath, "UTF-8") + "!/" + str);
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
```
  
  
#### >>>>由于`Minecraft.getMinecraft().thePlayer`还未初始化引起的崩溃
```
java.lang.NullPointerException: Unexpected error
	at com.netease.mc.mod.battergaming.iIIIiiIIIi.run(r:73)
	at com.netease.mc.mod.battergaming.iiiiiiIIIi.ALLATORIxDEMO(j:271)
	at com.netease.mc.mod.battergaming.iiiiiiIIIi.ALLATORIxDEMO(j:182)
	at net.minecraftforge.fml.common.eventhandler.ASMEventHandler_40_iiiiiiIIIi_ALLATORIxDEMO_ClientTickEvent.invoke(.dynamic)
	at net.minecraftforge.fml.common.eventhandler.ASMEventHandler.invoke(ASMEventHandler.java:55)
	at net.minecraftforge.fml.common.eventhandler.EventBus.post(EventBus.java:140)
	at net.minecraftforge.fml.common.FMLCommonHandler.onPreClientTick(FMLCommonHandler.java:331)
	at net.minecraft.client.Minecraft.func_71407_l(Minecraft.java:1617)
	at net.minecraft.client.Minecraft.func_71411_J(Minecraft.java:1017)
	at net.minecraft.client.Minecraft.func_99999_d(Minecraft.java:351)
	at net.minecraft.client.main.Main.main(SourceFile:124)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(Unknown Source)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(Unknown Source)
	at java.lang.reflect.Method.invoke(Unknown Source)
	at net.minecraft.launchwrapper.Launch.launch(Launch.java:146)
	at net.minecraft.launchwrapper.Launch.main(Launch.java:25)
```

反编译反混淆相关代码：（`com/netease/mc/mod/battergaming/EventHandler.java`）
```java
    public void run() {
        BatterGaming.getInstance().getJsonObject().addProperty("player", Minecraft.getMinecraft().thePlayer.getName());                 
    }
```

由于`Minecraft.getMinecraft().getSession().getUserName()`也能获取玩家的用户名，并且`net.minecraft.client.Minecraft.session`在游戏开始运行时就已经初始化，所以作出如下修改：
```diff
    public void run() {
-        BatterGaming.getInstance().getJsonObject().addProperty("player", Minecraft.getMinecraft().thePlayer.getName());       
+        BatterGaming.getInstance().getJsonObject().addProperty("player", Minecraft.getMinecraft().getSession().getUserName());
    }
```

#### 最后，因为`battergaming`会对自身进行MD5校验，所以使用`CoreMod`进行修改