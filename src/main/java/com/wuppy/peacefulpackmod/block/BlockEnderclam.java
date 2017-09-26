package com.wuppy.peacefulpackmod.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import java.util.Random;

public class BlockEnderclam extends Block
{
	public BlockEnderclam()
	{
		super(Material.ROCK);

		setSoundType(SoundType.STONE);
		setLightLevel(0.8F);
		setHardness(3F);
		setResistance(1.0F);
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return Items.ENDER_PEARL;
	}

	@Override
	public int getExpDrop(IBlockState state, IBlockAccess world, BlockPos pos, int fortune)
	{
		final Random rand = world instanceof World ? ((World) world).rand : new Random();

		return MathHelper.getInt(rand, 3, 7);
	}
}