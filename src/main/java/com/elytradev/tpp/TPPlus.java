package com.elytradev.tpp;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

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

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event){ }
    @Mod.EventHandler
    public void init(FMLInitializationEvent event){ }
    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event){ }
}
