package com.wuppy.peacefulpackmod.block;

import com.wuppy.peacefulpackmod.item.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import java.util.List;
import java.util.Random;

public class BlockRottenPlant extends BlockBush implements IGrowable
{
	public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 1);

	private final AxisAlignedBB bounds = new AxisAlignedBB(0F, 0.0F, 0F, 1F, 0.25F, 1F);

	public BlockRottenPlant()
	{
		setSoundType(SoundType.GROUND);
		setHardness(0.0F);
		disableStats();
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
		return this.getDefaultState().withProperty(AGE, meta);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return (Integer) state.getValue(AGE);
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, AGE);
	}

	@Override
	public boolean canPlaceBlockAt(World world, BlockPos pos) {
		return world.getBlockState(pos.down()).getBlock() == Blocks.NETHERRACK;
	}

	/**
	 * Ticks the block if it's been scheduled
	 */
	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
	{
		super.updateTick(worldIn, pos, state, rand);

		if (worldIn.getLightFromNeighbors(pos.up()) < 6)
		{
			final int age = state.getValue(AGE);

			if (age < 1)
			{
				final float growthChance = getGrowthChance(this, worldIn, pos);

				if (rand.nextInt((int) (25.0F / growthChance) + 1) == 0)
				{
					worldIn.setBlockState(pos, state.withProperty(AGE, age + 1), 2);
				}
			}
		}
	}

	protected static float getGrowthChance(Block blockIn, World worldIn, BlockPos pos)
	{
		float f = 1.0F;
		final BlockPos underneathPos = pos.down();

		for (int i = -1; i <= 1; ++i)
		{
			for (int j = -1; j <= 1; ++j)
			{
				float f1 = 0.0F;
				final IBlockState blockStateBeneath = worldIn.getBlockState(underneathPos.add(i, 0, j));

				if (blockStateBeneath.getBlock()
						.canSustainPlant(
								blockStateBeneath,
								worldIn,
								underneathPos.add(i, 0, j),
								EnumFacing.UP,
								(IPlantable) blockIn)
						)
				{
					f1 = 1.0F;

					if (blockStateBeneath.getBlock().isFertile(worldIn, underneathPos.add(i, 0, j)))
					{
						f1 = 3.0F;
					}
				}

				if (i != 0 || j != 0)
				{
					f1 /= 4.0F;
				}

				f += f1;
			}
		}

		BlockPos northPos = pos.north();
		BlockPos southPos = pos.south();
		BlockPos westPos = pos.west();
		BlockPos eastPos = pos.east();
		boolean flag = blockIn == worldIn.getBlockState(westPos).getBlock() ||
				blockIn == worldIn.getBlockState(eastPos).getBlock();
		boolean flag1 = blockIn == worldIn.getBlockState(northPos).getBlock() ||
				blockIn == worldIn.getBlockState(southPos).getBlock();

		if (flag && flag1)
		{
			f /= 2.0F;
		} else
		{
			boolean flag2 = blockIn == worldIn.getBlockState(westPos.north()).getBlock() || blockIn == worldIn.getBlockState(eastPos.north()).getBlock() || blockIn == worldIn.getBlockState(eastPos.south()).getBlock() || blockIn == worldIn.getBlockState(westPos.south()).getBlock();

			if (flag2)
			{
				f /= 2.0F;
			}
		}

		return f;
	}

	@Override
	@Deprecated
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
	{
		final List<ItemStack> ret = super.getDrops(world, pos, state, fortune);

		int age = state.getValue(AGE);
		Random rand = world instanceof World ? ((World) world).rand : new Random();

		if (age > 0)
		{
			ret.add(new ItemStack(Items.ROTTEN_FLESH));
		}
		if (rand.nextInt(2) == 0)
		{
			ret.add(new ItemStack(ModItems.rotten_seed));
		}

		return ret;
	}

	@Override
	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient)
	{
		return (Integer) state.getValue(AGE) < 2;
	}

	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state)
	{
		return true;
	}

	@Override
	public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state)
	{
		worldIn.setBlockState(pos, state.withProperty(AGE, 1), 2);
	}
}