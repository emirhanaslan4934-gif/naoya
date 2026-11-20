package net.naoya.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.Direction;
import net.naoya.NaoyaClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public class BlockMixin {
    @Inject(method = "shouldDrawSide", at = @At("RETURN"), cancellable = true)
    private static void naoya$xray(BlockState state, BlockState otherState, Direction side,
                                   CallbackInfoReturnable<Boolean> cir) {
        if (NaoyaClient.XRAY != null && NaoyaClient.XRAY.isEnabled()) {
            cir.setReturnValue(NaoyaClient.XRAY.shouldRender(state));
        }
    }
}
