package com.wuppy.frozen.generation;

import com.wuppy.frozen.blocks.ModBlocks;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldGenHardIce extends WorldGenerator {
	@Override
	public boolean generate(World par1World, Random par2Random, BlockPos pos) {
		for (int l = 0; l < 40; ++l) {
			int x1 = pos.getX() + par2Random.nextInt(8) - par2Random.nextInt(8);
			int y1 = pos.getY() + par2Random.nextInt(4) - par2Random.nextInt(4);
			int z1 = pos.getZ() + par2Random.nextInt(8) - par2Random.nextInt(8);

			if (par1World.getBlockState(new BlockPos(x1, y1, z1)).getBlock() == Blocks.ice) {
				par1World.setBlockState(new BlockPos(x1, y1, z1), ModBlocks.hardIce.getDefaultState());
			}
		}

		return true;
	}
}