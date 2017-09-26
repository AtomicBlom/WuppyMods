package com.wuppy.peacefulpackmod.item;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;

public class ItemPeacefulSeed extends Item implements IPlantable
{
	/**
	 * the type of block this seed turns into (wheat or pumpkin stems for instance)
	 */
	private Block blockType;

	/**
	 * BlockID of the block the seeds can be planted on.
	 */
	private Block soilBlock;

	public ItemPeacefulSeed(Block block, Block soil)
	{
		blockType = block;
		soilBlock = soil;
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		final ItemStack heldItem = player.getHeldItem(hand);

		if (facing != EnumFacing.UP)
		{
			return EnumActionResult.PASS;
		}
		else if (!player.canPlayerEdit(pos.offset(facing), facing, heldItem))
		{
			return EnumActionResult.PASS;
		}
		else if (worldIn.getBlockState(pos).getBlock() == soilBlock && worldIn.isAirBlock(pos.up()))
		{
			worldIn.setBlockState(pos.up(), this.blockType.getDefaultState());
			heldItem.shrink(1);
			return EnumActionResult.SUCCESS;
		}
		else
		{
			return EnumActionResult.PASS;
		}
	}

	@Override
	public IBlockState getPlant(IBlockAccess world, BlockPos pos)
	{
		return blockType.getDefaultState();
	}

	@Override
	public EnumPlantType getPlantType(IBlockAccess world, BlockPos pos)
	{
		return EnumPlantType.Crop;
	}
}
