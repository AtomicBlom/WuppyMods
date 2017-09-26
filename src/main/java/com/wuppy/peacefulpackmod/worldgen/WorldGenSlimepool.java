package com.wuppy.peacefulpackmod.worldgen;

import com.wuppy.peacefulpackmod.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGenSlimepool extends WorldGenerator
{
	public WorldGenSlimepool()
	{
	}

	@Override
	public boolean generate(World world, Random rand, BlockPos pos)
	{
		if (pos.getY() > 45 || pos.getY() < 3)
		{
			return false;
		}

		Block block = ModBlocks.slime_slab;
		if (world.getBlockState(pos).getMaterial().isSolid() && rand.nextInt(4) == 1)
		{
			world.setBlockState(pos, block.getDefaultState(), 2);

			BlockPos[] positions = {
					pos.west(),
					pos.east(),
					pos.north(),
					pos.south(),
					pos.east().south(),
					pos.west().south(),
					pos.east().north(),
					pos.west().north()
			};

			for (final BlockPos position : positions)
			{
				if (world.getBlockState(position).getMaterial().isSolid())
				{
					world.setBlockState(position, block.getDefaultState(), 2);
				}
			}

			final BlockPos twoBlocksOver = pos.west(2);

			if (world.getBlockState(twoBlocksOver).getMaterial().isSolid() &&
					world.getBlockState(pos.west()).getBlock() == block)
			{
				world.setBlockState(twoBlocksOver, block.getDefaultState(), 2);
			}
			return true;
		} else
			return false;
	}
}
