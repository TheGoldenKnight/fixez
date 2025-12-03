package tgk.fixez.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SculkBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public abstract class BlockMixin {
    @Unique
    private static BlockState block;

    @Inject(
            method = "popExperience",
            at = @At("HEAD"),
            cancellable = true
    )
    private void dropExperienceNoSkulk(ServerLevel serverLevel, BlockPos blockPos, int i, CallbackInfo ci) {
        BlockState state = serverLevel.getBlockState(blockPos);
        if (block.getBlock() instanceof SculkBlock) {
            if (serverLevel.getGameRules().getBoolean(GameRules.RULE_DOBLOCKDROPS)) {
                ExperienceOrb.award(serverLevel, Vec3.atCenterOf(blockPos), i);
            }
            ci.cancel();
        }
    }
    @Inject(
            method = "playerWillDestroy",
            at = @At("HEAD")
    )
    private void playerWillDestroy(Level level, BlockPos blockPos, BlockState blockState, Player player, CallbackInfoReturnable<BlockState> cir){
        block = blockState;
    }
}