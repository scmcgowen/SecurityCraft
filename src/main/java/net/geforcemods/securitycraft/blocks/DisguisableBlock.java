package net.geforcemods.securitycraft.blocks;

import net.geforcemods.securitycraft.compat.IOverlayDisplay;
import net.geforcemods.securitycraft.items.ModuleItem;
import net.geforcemods.securitycraft.misc.ModuleType;
import net.geforcemods.securitycraft.tileentity.DisguisableTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public abstract class DisguisableBlock extends OwnableBlock implements IOverlayDisplay
{
	public DisguisableBlock(Block.Properties properties)
	{
		super(properties);
	}

	public static boolean isNormalCube(BlockState state, IBlockReader world, BlockPos pos)
	{
		if(state.getBlock() instanceof DisguisableBlock) //should not happen, but just to be safe
		{
			BlockState disguisedState = ((DisguisableBlock)state.getBlock()).getDisguisedStateOrDefault(state, world, pos);

			if(disguisedState.getBlock() != state.getBlock())
				return disguisedState.isNormalCube(world, pos);
		}

		return state.getMaterial().isOpaque() && state.hasOpaqueCollisionShape(world, pos);
	}

	public static boolean isSuffocating(BlockState state, IBlockReader world, BlockPos pos)
	{
		if(state.getBlock() instanceof DisguisableBlock) //should not happen, but just to be safe
		{
			BlockState disguisedState = ((DisguisableBlock)state.getBlock()).getDisguisedStateOrDefault(state, world, pos);

			if(disguisedState.getBlock() != state.getBlock())
				return disguisedState.isSuffocating(world, pos);
		}

		return state.getMaterial().blocksMovement() && state.hasOpaqueCollisionShape(world, pos);
	}

	@Override
	public SoundType getSoundType(BlockState state, IWorldReader world, BlockPos pos, Entity entity)
	{
		BlockState disguisedState = getDisguisedStateOrDefault(state, world, pos);

		if(disguisedState.getBlock() != this)
			return disguisedState.getSoundType(world, pos, entity);
		else return super.getSoundType(state, world, pos, entity);
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext ctx)
	{
		BlockState disguisedState = getDisguisedStateOrDefault(state, world, pos);

		if(disguisedState.getBlock() != this)
			return disguisedState.getShape(world, pos, ctx);
		else return super.getShape(state, world, pos, ctx);
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext ctx)
	{
		BlockState disguisedState = getDisguisedStateOrDefault(state, world, pos);

		if(disguisedState.getBlock() != this)
			return disguisedState.getCollisionShape(world, pos, ctx);
		else return super.getCollisionShape(state, world, pos, ctx);
	}

	@Override
	public VoxelShape getRenderShape(BlockState state, IBlockReader world, BlockPos pos)
	{
		BlockState disguisedState = getDisguisedStateOrDefault(state, world, pos);

		if(disguisedState.getBlock() != this)
			return disguisedState.getRenderShape(world, pos);
		else return super.getRenderShape(state, world, pos);
	}

	@Override
	public float getAmbientOcclusionLightValue(BlockState state, IBlockReader world, BlockPos pos)
	{
		BlockState disguisedState = getDisguisedStateOrDefault(state, world, pos);

		if(disguisedState.getBlock() != this)
			return disguisedState.getAmbientOcclusionLightValue(world, pos);
		else return super.getAmbientOcclusionLightValue(state, world, pos);
	}

	public final BlockState getDisguisedStateOrDefault(BlockState state, IBlockReader world, BlockPos pos)
	{
		BlockState disguisedState = getDisguisedBlockState(world, pos);

		return disguisedState != null ? disguisedState : state;
	}

	public BlockState getDisguisedBlockState(IBlockReader world, BlockPos pos)
	{
		if(world.getTileEntity(pos) instanceof DisguisableTileEntity)
		{
			DisguisableTileEntity te = (DisguisableTileEntity) world.getTileEntity(pos);
			ItemStack module = te.hasModule(ModuleType.DISGUISE) ? te.getModule(ModuleType.DISGUISE) : ItemStack.EMPTY;

			if(!module.isEmpty() && !((ModuleItem) module.getItem()).getBlockAddons(module.getTag()).isEmpty())
				return ((ModuleItem) module.getItem()).getBlockAddons(module.getTag()).get(0).getDefaultState();
		}

		return null;
	}

	public ItemStack getDisguisedStack(IBlockReader world, BlockPos pos)
	{
		if(world != null && world.getTileEntity(pos) instanceof DisguisableTileEntity)
		{
			DisguisableTileEntity te = (DisguisableTileEntity) world.getTileEntity(pos);
			ItemStack stack = te.hasModule(ModuleType.DISGUISE) ? te.getModule(ModuleType.DISGUISE) : ItemStack.EMPTY;

			if(!stack.isEmpty() && !((ModuleItem) stack.getItem()).getBlockAddons(stack.getTag()).isEmpty())
			{
				ItemStack disguisedStack = ((ModuleItem) stack.getItem()).getAddons(stack.getTag()).get(0);

				if(Block.getBlockFromItem(disguisedStack.getItem()) != this)
					return disguisedStack;
			}
		}

		return new ItemStack(this);
	}

	@Override
	public ItemStack getDisplayStack(World world, BlockState state, BlockPos pos)
	{
		return getDisguisedStack(world, pos);
	}

	@Override
	public boolean shouldShowSCInfo(World world, BlockState state, BlockPos pos)
	{
		return getDisguisedStack(world, pos).getItem() == asItem();
	}

	@Override
	public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, PlayerEntity player)
	{
		return getDisguisedStack(world, pos);
	}
}
