package net.naoya.module;

import net.minecraft.client.MinecraftClient;

public class FullbrightModule extends Module {
    private double oldGamma = 1.0;

    public FullbrightModule() { super("Fullbright"); }

    @Override
    public void onEnable() {
        MinecraftClient mc = MinecraftClient.getInstance();
        oldGamma = mc.options.getGamma().getValue();
        mc.options.getGamma().setValue(16.0);
    }

    @Override
    public void onDisable() {
        MinecraftClient.getInstance().options.getGamma().setValue(oldGamma);
    }
}
