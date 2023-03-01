package vice.magnesium_extras.util.chunks;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IChunkClaimProvider
{
    boolean isInClaimedChunk(World world, BlockPos pos);
}
