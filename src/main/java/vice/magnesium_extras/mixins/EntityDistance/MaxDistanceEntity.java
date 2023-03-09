package vice.magnesium_extras.mixins.EntityDistance;

import net.minecraft.client.renderer.culling.ClippingHelper;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import vice.magnesium_extras.config.EntityListConfig;
import vice.magnesium_extras.config.MagnesiumExtrasConfig;
import vice.magnesium_extras.util.DistanceUtility;

import java.util.Objects;

@Mixin(EntityRendererManager.class)
public class MaxDistanceEntity {
    @Inject(at = @At("HEAD"), method = "shouldRender", cancellable = true)
    public <E extends Entity> void shouldDoRender(E entity, ClippingHelper clippingHelper, double cameraX, double cameraY, double cameraZ, CallbackInfoReturnable<Boolean> cir) {
        if (!MagnesiumExtrasConfig.enableDistanceChecks.get() || EntityListConfig.ENTITY_LIST.get().stream().anyMatch(entry -> Objects.equals(entry, Objects.requireNonNull(entity.getType().getRegistryName()).toString()))) return;

        if (!DistanceUtility.isEntityWithinDistance(
                entity,
                cameraX,
                cameraY,
                cameraZ,
                MagnesiumExtrasConfig.maxEntityRenderDistanceY.get(),
                MagnesiumExtrasConfig.maxEntityRenderDistanceSquare.get()
        ))
        {
            cir.cancel();
        }
    }
}

