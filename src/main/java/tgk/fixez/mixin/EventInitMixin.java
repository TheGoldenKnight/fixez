package tgk.fixez.mixin;


import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.levelz.access.LevelManagerAccess;
import net.levelz.init.ConfigInit;
import net.levelz.init.EventInit;
import net.levelz.level.LevelManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EventInit.class)
public class EventInitMixin {
	@Inject(method = "init" ,at = @At(value = "TAIL"))
    private static void tgk_FixezRedistributeStartLevelzAfterRespawn(CallbackInfo ci) {
		ServerPlayerEvents.AFTER_RESPAWN.register((ServerPlayerEvents.AfterRespawn)(oldPlayer, newPlayer, alive) -> {
			if (ConfigInit.CONFIG.hardMode) {
				LevelManager levelManager = ((LevelManagerAccess)newPlayer).getLevelManager();
				levelManager.setSkillPoints(ConfigInit.CONFIG.startPoints);
			}
		});
	}
}