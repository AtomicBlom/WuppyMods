package com.wuppy.peacefulpackmod.block;

import com.google.common.collect.Lists;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPlanks.EnumType;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import java.util.List;
import java.util.Random;

public class BlockBlazeLeaves extends BlockLeaves
{
	public BlockBlazeLeaves()
	{
		setLightLevel(0.7F);
	}

	@SideOnly(Side.CLIENT)
    public int getBlockColor()
    {
        return 16777215;
    }

    @SideOnly(Side.CLIENT)
    public int getRenderColor(IBlockState state)
    {
        return 16777215;
    }

    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess worldIn, BlockPos pos, int renderPass)
    {
        return 16777215;
    }

	@Override
	@Deprecated
	public IBlockState getStateFromMeta(int meta)
	{
		final IBlockState state = getDefaultState();

		if (meta < 2)
			state.withProperty(DECAYABLE, true);
		else
			state.withProperty(DECAYABLE, false);

		if (meta == 0 || meta == 2)
			state.withProperty(CHECK_DECAY, true);
		else
			state.withProperty(CHECK_DECAY, false);

		return state;
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		if (state.getValue(DECAYABLE))
		{
			return state.getValue(CHECK_DECAY) ? 0 : 1;
		} else
		{
			return state.getValue(CHECK_DECAY) ? 2 : 3;
		}
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, DECAYABLE, CHECK_DECAY);
	}

	@Override
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
	{
		super.randomDisplayTick(stateIn, worldIn, pos, rand);

		if (rand.nextInt(2) == 0)
		{
			final double pX = pos.getX() + worldIn.rand.nextFloat();
			final double pY = pos.getY() + worldIn.rand.nextFloat();
			final double pZ = pos.getZ() + worldIn.rand.nextFloat();
			worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pX, pY, pZ, 0.0D, 0.0D, 0.0D);
			worldIn.spawnParticle(EnumParticleTypes.FLAME, pX, pY, pZ, 0.0D, 0.0D, 0.0D);
		}
	}

	@Override
	@Deprecated
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
	{
		final List<ItemStack> ret = super.getDrops(world, pos, state, fortune);

		final Random rand = world instanceof World ? ((World) world).rand : new Random();

		if (rand.nextInt(10) == 0)
		{
			ret.add(new ItemStack(ModBlocks.blaze_sapling));
		}
		if (rand.nextInt(5) == 0)
		{
			ret.add(new ItemStack(Items.BLAZE_ROD));
		}

		return ret;
	}

	@Override
	public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune)
	{
		final List<ItemStack> ret = Lists.newArrayList();
		ret.add(new ItemStack(this, 1));
		return ret;
	}

	@Override
	public EnumType getWoodType(int meta)
	{
		return null;
	}
}