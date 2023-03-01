package vice.magnesium_extras.mixins.FadeInChunks;

import me.jellysquid.mods.sodium.client.render.chunk.backends.multidraw.ChunkDrawParamsVector;
import org.lwjgl.system.MemoryUtil;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import sun.misc.Unsafe;
import vice.magnesium_extras.features.FadeInChunks.ChunkDrawParamsVectorExt;

@Mixin(value = ChunkDrawParamsVector.UnsafeChunkDrawCallVector.class, remap = false)
public abstract class UnsafeChunkDrawCallVectorMixin extends ChunkDrawParamsVector implements ChunkDrawParamsVectorExt
{

    @Shadow private long writePointer;

    protected UnsafeChunkDrawCallVectorMixin(int capacity) {
        super(capacity);
    }

    @Override
    public void pushChunkDrawParamFadeIn(float progress) {
        MemoryUtil.memPutFloat(this.writePointer - 4, progress);
    }
}
