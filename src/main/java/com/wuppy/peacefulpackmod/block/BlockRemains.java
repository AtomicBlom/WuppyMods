package com.wuppy.peacefulpackmod.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import java.util.Random;

public class BlockRemains extends Block
{
	public BlockRemains()
	{
		super(Material.GROUND);

		setSoundType(SoundType.GROUND);
		setHardness(0.5F);

	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return Items.ROTTEN_FLESH;
	}
}