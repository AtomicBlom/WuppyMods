package com.wuppy.peacefulpackmod.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import java.util.Random;

public class BlockSlimeSlab extends Block
{
	private final AxisAlignedBB bounds = new AxisAlignedBB(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);

	public BlockSlimeSlab()
	{
		super(Material.GROUND);
		setHardness(1.0F);
		setLightLevel(0.5F);
		setResistance(1.0F);
		setHardness(2.0F);

		setTickRandomly(true);
	}

	@Deprecated
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return bounds;
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return Items.SLIME_BALL;
	}

	@Override
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)
	{
		super.randomDisplayTick(stateIn, worldIn, pos, rand);

		if (rand.nextInt(2) == 0)
		{
			final double dX = pos.getX() + worldIn.rand.nextFloat();
			final double dY = pos.getY() + worldIn.rand.nextFloat();
			final double dZ = pos.getZ() + worldIn.rand.nextFloat();
			worldIn.spawnParticle(EnumParticleTypes.SLIME, dX, dY, dZ, 0.0D, 0.0D, 0.0D);
		}
	}

	@Override
	@Deprecated
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	@Override
	@Deprecated
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entity)
	{
		entity.motionX *= 0.00001D;
		entity.motionY *= 0.0D;
		entity.motionZ *= 0.00001D;
	}
}
