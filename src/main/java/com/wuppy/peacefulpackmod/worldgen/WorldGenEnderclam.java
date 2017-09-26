package com.wuppy.peacefulpackmod.worldgen;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGenEnderclam extends WorldGenerator
{
	private Block enderclam;

	public WorldGenEnderclam(Block par1)
	{
		enderclam = par1;
	}

	@Override
	public boolean generate(World worldIn, Random rand, BlockPos pos)
	{
		if (worldIn.getBlockState(new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ())).getBlock() == Blocks.SAND || worldIn.getBlockState(new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ())).getBlock() == Blocks.DIRT || worldIn.getBlockState(new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ())).getBlock() == Blocks.CLAY)
		{
			if (worldIn.getBlockState(new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ())).getBlock() == Blocks.WATER || worldIn.getBlockState(new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ())).getBlock() == Blocks.FLOWING_WATER)
			{
				worldIn.setBlockState(pos, enderclam.getDefaultState(), 2);
			}
		}
		return true;
	}
}