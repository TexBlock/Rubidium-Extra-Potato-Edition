package vice.rubidium_extras.mixins.BorderlessFullscreen;

import net.minecraft.client.MainWindow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(MainWindow.class)
public interface MainWindowAccessor
{
    @Accessor("dirty")
    public void setDirty(boolean value);
}