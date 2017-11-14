package com.elytradev.tpp;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(modid=TPPlus.modId, name=TPPlus.name, version=TPPlus.version)
public class TPPlus {
    static final String modId = "tpp";
    static final String name = "TP+";
    static final String version = "1";

    @Mod.Instance(modId)
    public static TPPlus instance;

    @Mod.EventHandler
    public void serverStart(FMLServerStartingEvent event){
        event.registerServerCommand(new CommandTPP());
    }
}
