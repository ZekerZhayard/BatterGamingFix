package com.netease.mc.mod.battergaming;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class TickDelay {
    public Runnable runnable;
    public int count;

    public TickDelay(Runnable runnable) {
        this(runnable, 20);
    }
    
    public TickDelay(Runnable runnable, int count) {
        this.runnable = runnable;
        this.count = count;
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (this.count < 1) {
            this.runTask();
            this.unregister();
        }
        --this.count;
    }
    
    public void runTask() {
        this.runnable.run();
    }

    public void unregister() {
        MinecraftForge.EVENT_BUS.unregister(this);
    }
}
