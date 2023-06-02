package net.mommymarlow.marlowclient.utils;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.RespawnAnchorBlock;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

import static net.mommymarlow.marlowclient.client.MarlowClient.mc;

/**
 * Client block utils
 */
public abstract class BlockUtils {

    /**
     * Interact a block
     * @param vec 3d vector
     * @return action result
     */
    public static ActionResult interact(Vec3d vec) {
        return interact(vec,Direction.UP);
    }

    /**
     * Interact a block
     *
     * @param pos block position
     * @param dir block face direction
     */
    public static void interact(BlockPos pos, Direction dir) {
        Vec3d vec = new Vec3d(pos.getX(), pos.getY(), pos.getZ());
        interact(vec, dir);
    }


    /**
     * Interact a block
     * @param vec3d 3d vector
     * @param dir block face direction
     * @return action result
     */
    public static ActionResult interact(Vec3d vec3d, Direction dir) {
        Vec3i vec3i = new Vec3i((int) vec3d.x, (int) vec3d.y, (int) vec3d.z);
        BlockPos pos = new BlockPos(vec3i);
        BlockHitResult result = new BlockHitResult(vec3d, dir,pos,false);
        assert mc.interactionManager != null;
        assert mc.player != null;
        return mc.interactionManager.interactBlock(mc.player,mc.player.getActiveHand(),result);
    }

    public static boolean isBlock(Block block, BlockPos pos) {
        return getBlockState(pos).getBlock() == block;
    }

    public static BlockState getBlockState(BlockPos pos) {
        return mc.world.getBlockState(pos);
    }


    public static BlockHitResult clientRaycastBlock(BlockPos pos)
    {
        return mc.world.raycastBlock(RenderUtils.getEyesPos(), RenderUtils.getClientLookVec().multiply(6.0).add(RenderUtils.getEyesPos()), pos, getBlockState(pos).getOutlineShape(mc.world, pos), getBlockState(pos));
    }



    /**
     * Interact a block
     * @param result block ray-cast hit result
     * @return action result
     */
    public static ActionResult interact(BlockHitResult result) {
        return mc.interactionManager.interactBlock(mc.player,mc.player.getActiveHand(),result);
    }

    /**
     * If the cross-hair targeted block matches the provided type
     * @param block block type
     * @return match
     */
    public static boolean matchTargetBlock(Block block) {
        Vec3d vec3d = mc.crosshairTarget.getPos();
        Vec3i vec3i = new Vec3i((int) vec3d.x, (int) vec3d.y, (int) vec3d.z);
        BlockPos pos = new BlockPos(vec3i);
        return matchBlock(pos,block);
    }

    /**
     * If the provided block position matches the provided type
     * @param pos block position
     * @param block block type
     * @return match
     */
    public static boolean matchBlock(BlockPos pos, Block block) {
        if (pos == null) return false;
        World world = mc.player.getWorld();
        BlockState state = world.getBlockState(pos);
        return state != null && state.isOf(block);
    }

    public static boolean isCrystallabe(BlockPos pos) {
        return matchBlock(pos,Blocks.OBSIDIAN) || matchBlock(pos,Blocks.BEDROCK);
    }


    public static boolean isAnchorCharged(BlockPos anchor)
    {
        if (!isBlock(Blocks.RESPAWN_ANCHOR, anchor))
            return false;
        try
        {
            return BlockUtils.getBlockState(anchor).get(RespawnAnchorBlock.CHARGES) != 0;
        }
        catch (IllegalArgumentException e)
        {
            return false;
        }
    }
}
