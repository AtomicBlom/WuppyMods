package com.wuppy.peacefulpackmod.block;

import com.google.common.collect.Lists;
import com.wuppy.peacefulpackmod.config.Config;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import java.util.List;
import java.util.Random;

public class BlockGhastOre extends Block
{
	public BlockGhastOre()
	{
		super(Material.ROCK);

		setSoundType(SoundType.STONE);
		setHardness(3F);
		setResistance(1.0F);
	}

	//TODO: WTF, why is state in Block?
	private boolean brokenByPlayer = false;
	private boolean lavaAround = false;

	@Override
	public void onBlockDestroyedByPlayer(World world, BlockPos pos, IBlockState state)
	{
		brokenByPlayer = true;
		final IBlockState[] surroundingBlocks = {
				world.getBlockState(pos.west()),
				world.getBlockState(pos.east()),
				world.getBlockState(pos.south()),
				world.getBlockState(pos.north()),
				world.getBlockState(pos.up()),
				world.getBlockState(pos.down())
		};

		for (final IBlockState blockState : surroundingBlocks)
		{
			if (blockState.getMaterial() == Material.LAVA)
			{
				lavaAround = true;

				int var8 = MathHelper.getInt(world.rand, 3, 7);

				this.dropXpOnBlockBreak(world, pos, var8);
			}
		}
	}

	@Override
	@Deprecated
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
	{
		final List<ItemStack> ret = Lists.newArrayList();

		final Random rand = world instanceof World ? ((World) world).rand : RANDOM;
		if (brokenByPlayer)
		{
			if(lavaAround || !Config.lavaForGhastOres)
			{
				int i;
				
				if(fortune > 0)
					i = rand.nextInt(fortune);
				else
					i = 0;
				
				ret.add(new ItemStack(Items.GHAST_TEAR, 1 + i));
			}
			else
				ret.add(new ItemStack(this));
		}

		return ret;
	}
}