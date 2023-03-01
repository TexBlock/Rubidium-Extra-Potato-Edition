package vice.magnesium_extras.mixins.FrameCounter;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Minecraft.class)
public interface FpsAccessorMixin
{
    @Accessor("fps")
    static int getFPS()
    {
        return 0;
    }
}