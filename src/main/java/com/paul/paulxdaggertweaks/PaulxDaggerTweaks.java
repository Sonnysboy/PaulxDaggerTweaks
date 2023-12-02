package com.paul.paulxdaggertweaks;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = "paulxdaggertweaks", useMetadata = true)
public class PaulxDaggerTweaks {

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {

	System.out.println("preInit");
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {

	System.out.println("init");
    }
}
