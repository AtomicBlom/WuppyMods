package com.wuppy.peacefulpackmod.worldgen;

import com.wuppy.peacefulpackmod.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLog.EnumAxis;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

import java.util.Random;

import static net.minecraft.block.BlockLog.LOG_AXIS;

public class WorldGenBlazeTrees extends WorldGenAbstractTree
{
	private final int minTreeHeight;
	private final boolean vinesGrow;
	private final IBlockState woodState;
	private final IBlockState leavesState;
	private static final String __OBFID = "CL_00000438";

	public WorldGenBlazeTrees()
	{
		this(true,
				4,
				ModBlocks.blaze_log.getDefaultState().withProperty(LOG_AXIS, EnumAxis.Y),
				ModBlocks.blaze_leaves.getDefaultState(),
				false);
	}

	public WorldGenBlazeTrees(boolean notify,
	                          int minimumTreeHeight,
	                          IBlockState metaWood,
	                          IBlockState metaLeaves,
	                          boolean shouldVinesGrow)
	{
		super(notify);
		minTreeHeight = minimumTreeHeight;
		this.woodState = metaWood;
		this.leavesState = metaLeaves;
		vinesGrow = shouldVinesGrow;
	}

	@Override
	public boolean generate(World worldIn, Random random, BlockPos pos)
	{
		int i = random.nextInt(3) + minTreeHeight;
		boolean flag = true;

		if (pos.getY() >= 1 && pos.getY() + i + 1 <= 256)
		{
			byte b0;
			int l;

			for (int j = pos.getY(); j <= pos.getY() + 1 + i; ++j)
			{
				b0 = 1;

				if (j == pos.getY())
				{
					b0 = 0;
				}

				if (j >= pos.getY() + 1 + i - 2)
				{
					b0 = 2;
				}

				for (int k = pos.getX() - b0; k <= pos.getX() + b0 && flag; ++k)
				{
					for (l = pos.getZ() - b0; l <= pos.getZ() + b0 && flag; ++l)
					{
						if (j >= 0 && j < 256)
						{
							if (!isReplaceable(worldIn, new BlockPos(k, j, l)))
							{
								flag = false;
							}
						} else
						{
							flag = false;
						}
					}
				}
			}

			if (!flag)
			{
				return false;
			} else
			{
				BlockPos down = pos.down();
				final IBlockState blockUnderneath = worldIn.getBlockState(down);
				Block block1 = blockUnderneath.getBlock();

				boolean goodSoil = false;
				if (block1 == Blocks.NETHERRACK)
					goodSoil = true;

				if (goodSoil && pos.getY() < 256 - i - 1)
				{
					block1.onPlantGrow(blockUnderneath, worldIn, down, pos);
					b0 = 3;
					byte b1 = 0;
					int i1;
					int j1;
					int k1;
					int l1;
					BlockPos blockpos1;

					for (l = pos.getY() - b0 + i; l <= pos.getY() + i; ++l)
					{
						i1 = l - (pos.getY() + i);
						j1 = b1 + 1 - i1 / 2;

						for (k1 = pos.getX() - j1; k1 <= pos.getX() + j1; ++k1)
						{
							l1 = k1 - pos.getX();

							for (int i2 = pos.getZ() - j1; i2 <= pos.getZ() + j1; ++i2)
							{
								int j2 = i2 - pos.getZ();

								if (Math.abs(l1) != j1 || Math.abs(j2) != j1 || random.nextInt(2) != 0 && i1 != 0)
								{
									blockpos1 = new BlockPos(k1, l, i2);
									final IBlockState blockState = worldIn.getBlockState(blockpos1);
									Block block = blockState.getBlock();

									if (block.isAir(blockState, worldIn, blockpos1) || block.isLeaves(blockState, worldIn, blockpos1) || blockState.getMaterial() == Material.VINE)
									{
										//ModBlocks.blaze_leaves
										setBlockAndNotifyAdequately(
												worldIn,
												blockpos1,
												leavesState
										);
									}
								}
							}
						}
					}

					for (l = 0; l < i; ++l)
					{
						BlockPos upN = pos.up(l);
						final IBlockState blockAboveN = worldIn.getBlockState(upN);
						Block block2 = blockAboveN.getBlock();
						if (block2.isAir(blockAboveN, worldIn, upN) ||
								block2.isLeaves(blockAboveN, worldIn, upN) ||
								blockAboveN.getMaterial() == Material.VINE)
						{
							//ModBlocks.blaze_log
							setBlockAndNotifyAdequately(
									worldIn,
									pos.up(l),
									woodState);
						}
					}

					return true;
				} else
				{
					return false;
				}
			}
		} else
		{
			return false;
		}
	}
}