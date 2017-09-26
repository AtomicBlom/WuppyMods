package com.wuppy.peacefulpackmod.worldgen;

import com.wuppy.peacefulpackmod.block.BlockPeacefulOres;
import com.wuppy.peacefulpackmod.block.BlockPeacefulOres.OreType;
import com.wuppy.peacefulpackmod.block.ModBlocks;
import com.wuppy.peacefulpackmod.config.Config;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;

import java.util.Random;

import static com.wuppy.peacefulpackmod.block.BlockPeacefulOres.VARIANT;

public class PeacefulGenerator implements IWorldGenerator
{
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
	{
		switch (world.provider.getDimension())
		{
		case -1:
			generateNether(world, random, chunkX * 16, chunkZ * 16);
			break;
		case 0:
			generateSurface(world, random, chunkX * 16, chunkZ * 16);
			break;
		}
	}

	public static void generateSurface(World world, Random random, int blockX, int blockZ)
	{
		if (Config.spawnSulphurNiter)
		{
			new WorldGenMinable(
					ModBlocks.ore_block.getDefaultState().withProperty(VARIANT, OreType.SULPHER),
					10,
					BlockMatcher.forBlock(Blocks.STONE)
			).generate(
					world,
					random,
					new BlockPos(
							blockX + random.nextInt(16),
							random.nextInt(60),
							blockZ + random.nextInt(16)
					)
			);

			new WorldGenMinable(
					ModBlocks.ore_block.getDefaultState().withProperty(VARIANT, OreType.NITER),
					10,
					BlockMatcher.forBlock(Blocks.STONE)
			).generate(
					world,
					random,
					new BlockPos(
							blockX + random.nextInt(16),
							random.nextInt(60),
							blockZ + random.nextInt(16)
					)
			);
		}

		if (Config.spawnFossils)
		{
			new WorldGenMinableFossil(
					ModBlocks.ore_block.getDefaultState().withProperty(VARIANT, OreType.FOSSIL1),
					15,
					BlockMatcher.forBlock(Blocks.STONE)
			).generate(
					world,
					random,
					new BlockPos(
							blockX + random.nextInt(16),
							random.nextInt(60),
							blockZ + random.nextInt(16)
					)
			);
		}

		if (Config.spawnFlax)
		{
			new WorldGenFlax().generate(
					world,
					random,
					new BlockPos(
							blockX + random.nextInt(16),
							random.nextInt(80),
							blockZ + random.nextInt(16)
					)
			);
		}

		if (Config.spawnSlimePool)
		{
			new WorldGenSlimepool().generate(
					world,
					random,
					new BlockPos(
							blockX + random.nextInt(16),
							random.nextInt(45),
							blockZ + random.nextInt(16)
					)
			);
		}

		if (Config.spawnEnderClams)
		{
			for (int i = 0; i < 5; i++)
			{
				new WorldGenEnderclam(ModBlocks.ender_clam)
						.generate(
								world,
								random,
								new BlockPos(
										blockX + random.nextInt(16),
										40 + random.nextInt(40),
										blockZ + random.nextInt(16)
								)
						);
			}
		}

		if (random.nextInt(2) == 0 && Config.spawnRemains)
		{
			new WorldGenRemains().generate(
					world,
					random,
					new BlockPos(
							blockX + random.nextInt(16),
							55 + random.nextInt(30),
							blockZ + random.nextInt(16)
					)
			);
		}
	}

	public static void generateNether(World world, Random random, int blockX, int blockZ)
	{
		if (Config.spawnRottenPlants)
		{
			new WorldGenRottenplant().generate(
					world,
					random,
					new BlockPos(
							blockX + random.nextInt(16),
							20 + random.nextInt(120),
							blockZ + random.nextInt(16)
					)
			);
		}

		if (Config.spawnBlazeTrees)
		{
			final WorldGenBlazeTrees worldGenBlazeTrees = new WorldGenBlazeTrees();
			for (int i = 0; i < 2; i++)
			{
				worldGenBlazeTrees.generate(
						world,
						random,
						new BlockPos(
								blockX + random.nextInt(16),
								20 + random.nextInt(120),
								blockZ + random.nextInt(16)
						)
				);
			}
		}

		if (Config.spawnGhastOre)
		{
			new WorldGenMinable(
					ModBlocks.ghast_ore.getDefaultState(),
					15,
					BlockMatcher.forBlock(Blocks.NETHERRACK)
			).generate(
					world,
					random,
					new BlockPos(
							blockX + random.nextInt(16),
							20 + random.nextInt(128),
							blockZ + random.nextInt(16)
					)
			);
		}
	}
}