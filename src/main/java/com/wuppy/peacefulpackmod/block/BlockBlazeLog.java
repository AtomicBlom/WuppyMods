package com.wuppy.peacefulpackmod.block;

import net.minecraft.block.BlockLog;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockBlazeLog extends BlockLog
{
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		switch (meta)
		{
		case 0:
			return getDefaultState().withProperty(LOG_AXIS, EnumAxis.X);
		case 1:
			return getDefaultState().withProperty(LOG_AXIS, EnumAxis.Y);
		case 2:
			return getDefaultState().withProperty(LOG_AXIS, EnumAxis.Z);
		default:
			return getDefaultState().withProperty(LOG_AXIS, EnumAxis.NONE);
		}
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		switch (AXIS_LOOKUP[state.getValue(LOG_AXIS).ordinal()])
		{
		case 0:
			return 1;
		case 1:
			return 0;
		case 2:
			return 2;
		default:
			return 3;
		}
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, LOG_AXIS);
	}

	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		return super.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer).withProperty(LOG_AXIS, EnumAxis.fromFacingAxis(facing.getAxis()));
	}

	@Override
	public int damageDropped(IBlockState state)
	{
		return 0;
	}

	static final int[] AXIS_LOOKUP = new int[EnumAxis.values().length];

	static
	{
		AXIS_LOOKUP[EnumAxis.X.ordinal()] = 1;
		AXIS_LOOKUP[EnumAxis.Z.ordinal()] = 2;
		AXIS_LOOKUP[EnumAxis.NONE.ordinal()] = 3;
	}
}