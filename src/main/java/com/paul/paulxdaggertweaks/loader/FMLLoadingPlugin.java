package com.paul.paulxdaggertweaks.loader;

import java.util.Map;

import com.paul.paulxdaggertweaks.asm.ClassTransformer;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.MCVersion;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.SortingIndex;

@MCVersion("1.12.2")
@SortingIndex(1001)
public class FMLLoadingPlugin implements IFMLLoadingPlugin {

    @Override
    public String[] getASMTransformerClass() {
	return new String[] { ClassTransformer.class.getName() };
    }

    @Override
    public String getModContainerClass() {
	return null;
    }

    @Override
    public String getSetupClass() {
	return null;
    }

    @Override
    public void injectData(Map<String, Object> data) {
    }

    @Override
    public String getAccessTransformerClass() {
	return null;
    }
}