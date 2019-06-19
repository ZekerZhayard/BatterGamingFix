package com.netease.mc.mod.battergaming;

import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

public class EventHandler {
    private NetworkManager networkManager;
    private TickDelay tickDelay;

    public void setNetworkManager(NetworkManager networkManager) {
        this.networkManager = networkManager;
    }

    @SubscribeEvent
    public void onClientConnected(FMLNetworkEvent.ClientConnectedToServerEvent event) {
        this.setNetworkManager(event.manager);
        if (BatterGaming.getInstance().getJsonObject().get("player") == null) {
            new TickDelay(new Runnable() {
                @Override
                public void run() {
                    BatterGaming.getInstance().getJsonObject().addProperty("player", Minecraft.getMinecraft().thePlayer.getName());                 
                }
            });
        }
    }
    
    @SubscribeEvent
    public void onWorldLoaded(WorldEvent.Load event) {
        if (this.tickDelay == null) {
            this.tickDelay = new TickDelay(new Runnable() {
                @Override
                public void run() {
                    EventHandler.this.sendPacket(BatterGaming.getInstance().getJsonObject().toString());
                    EventHandler.this.tickDelay = null;
                }
            }, 25);
        }
    }
    
    public NetworkManager getNetworkManager() {
        return this.networkManager;
    }

    private void sendPacket(String str) {
        PacketBuffer buffer = new PacketBuffer(Unpooled.buffer());
        buffer.writeString(str);
        C17PacketCustomPayload c17Packet = new C17PacketCustomPayload("AntiCheat", buffer);
        this.networkManager.sendPacket(c17Packet);
    }
}
