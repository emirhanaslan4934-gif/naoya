package net.naoya.module;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;

import java.util.Set;

public class XrayModule extends Module {
    private static final Set<Block> TARGETS = Set.of(
        Blocks.COAL_ORE, Blocks.DEEPSLATE_COAL_ORE,
        Blocks.IRON_ORE, Blocks.DEEPSLATE_IRON_ORE,
        Blocks.COPPER_ORE, Blocks.DEEPSLATE_COPPER_ORE,
        Blocks.GOLD_ORE, Blocks.DEEPSLATE_GOLD_ORE, Blocks.NETHER_GOLD_ORE,
        Blocks.REDSTONE_ORE, Blocks.DEEPSLATE_REDSTONE_ORE,
        Blocks.LAPIS_ORE, Blocks.DEEPSLATE_LAPIS_ORE,
        Blocks.DIAMOND_ORE, Blocks.DEEPSLATE_DIAMOND_ORE,
        Blocks.EMERALD_ORE, Blocks.DEEPSLATE_EMERALD_ORE,
        Blocks.NETHER_QUARTZ_ORE, Blocks.ANCIENT_DEBRIS
    );

    private double oldGamma = 1.0;

    public XrayModule() { super("Xray"); }

    public boolean shouldRender(BlockState state) {
        return TARGETS.contains(state.getBlock());
    }

    @Override
    public void onEnable() {
        MinecraftClient mc = MinecraftClient.getInstance();
        oldGamma = mc.options.getGamma().getValue();
        mc.options.getGamma().setValue(16.0); // fullbright
        reload(mc);
    }

    @Override
    public void onDisable() {
        MinecraftClient mc = MinecraftClient.getInstance();
        mc.options.getGamma().setValue(oldGamma);
        reload(mc);
    }

    private void reload(MinecraftClient mc) {
        if (mc.worldRenderer != null) mc.worldRenderer.reload();
    }
}
