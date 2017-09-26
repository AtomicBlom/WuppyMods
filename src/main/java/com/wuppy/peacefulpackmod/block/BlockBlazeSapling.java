package com.wuppy.peacefulpackmod.block;

import com.wuppy.peacefulpackmod.worldgen.WorldGenBlazeTrees;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import java.util.Random;

public class BlockBlazeSapling extends BlockBush implements IGrowable
{
	public static final PropertyInteger STAGE = PropertyInteger.create("stage", 0, 1);
	private final AxisAlignedBB bounds = new AxisAlignedBB(0.1F, 0.0F, 0.1F, 0.9F, 0.8F, 0.9F);

	public BlockBlazeSapling()
	{
		super(Material.PLANTS);

		setSoundType(SoundType.GROUND);
		setHardness(0.0F);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return bounds;
	}

	@Override
	@Deprecated
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(STAGE, meta);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return (Integer) state.getValue(STAGE);
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, STAGE);
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean canPlaceBlockAt(World world, BlockPos pos) {
		return world.getBlockState(pos.down()).getBlock() == Blocks.NETHERRACK;
	}


	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random random)
	{
		super.updateTick(world, pos, state, random);

		if (world.getLightFromNeighbors(pos.up()) < 7 && random.nextInt(7) == 0)
		{
			if ((Integer) state.getValue(STAGE) == 0)
			{
				world.setBlockState(pos, state.cycleProperty(STAGE), 4);
			} else
			{
				growTree(world, pos, random);
			}
		}
	}

	public void growTree(World world, BlockPos pos, Random random)
	{
		world.setBlockState(pos, Blocks.AIR.getDefaultState());

		WorldGenBlazeTrees generator = new WorldGenBlazeTrees();
		generator.generate(world, random, pos);
	}

	@Override
	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient)
	{
		return true;
	}

	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state)
	{
		return rand.nextInt(3) == 0;
	}

	@Override
	public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state)
	{
		growTree(worldIn, pos, rand);
	}
}