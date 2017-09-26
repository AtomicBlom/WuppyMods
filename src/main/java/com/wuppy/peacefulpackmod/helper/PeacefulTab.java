package com.wuppy.peacefulpackmod.helper;

import com.wuppy.peacefulpackmod.block.ModBlocks;
import com.wuppy.peacefulpackmod.item.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PeacefulTab extends CreativeTabs
{
	String name;

	public PeacefulTab(int par1, String par2Str)
	{
		super(par1, par2Str);
		name = par2Str;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ItemStack getTabIconItem()
	{
		if (name.equals("pp_blocks"))
			return new ItemStack(ModBlocks.ore_block);
		else if (name.equals("pp_materials"))
			return new ItemStack(ModItems.peaceful_material);
		else
		{
			System.out.println("Error getting CreativeTab");
			return null;
		}
	}
}