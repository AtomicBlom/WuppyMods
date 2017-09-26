package com.wuppy.peacefulpackmod.block;

import com.wuppy.peacefulpackmod.PeacefulPack;
import com.wuppy.peacefulpackmod.item.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import java.util.Random;

public class BlockPeacefulOres extends Block
{
	public static final PropertyEnum<OreType> VARIANT = PropertyEnum.create("variant", OreType.class);

	public BlockPeacefulOres()
	{
		super(Material.ROCK);
		setSoundType(SoundType.STONE);
		setHardness(3.0F);
		setResistance(1.0F);

		setCreativeTab(PeacefulPack.ppBlocksTab);
	}

	@Override
	@Deprecated
	public IBlockState getStateFromMeta(int meta)
	{
		return getDefaultState().withProperty(VARIANT, OreType.byMetadata(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(VARIANT).getMetadata();
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, VARIANT);
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		if (state.getValue(VARIANT) == OreType.SULPHER || state.getValue(VARIANT) == OreType.NITER)
		{
			return ModItems.peaceful_material;
		}
		else
			return Items.BONE;
	}
	
	@Override
	public int damageDropped(IBlockState state)
	{
		if(state.getValue(VARIANT) == OreType.NITER)
			return 1;
		else
			return 0;
	}

	@Override
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items)
	{
		for (int var4 = 0; var4 < OreType.values().length; ++var4)
		{
			items.add(new ItemStack(this, 1, var4));
		}
	}

	public enum OreType implements IStringSerializable
	{
		SULPHER(0, "sulphur"),
		NITER(1, "niter"),
		FOSSIL1(2, "fossil1"),
		FOSSIL2(3, "fossil2"),
		FOSSIL3(4, "fossil3");

		private final int metadata;
		private final String name;

		OreType(int metadata, String name)
		{
			this.metadata = metadata;
			this.name = name;
		}

		static
		{
			final OreType[] values = values();

			for (final OreType type : values)
			{
				values()[type.getMetadata()] = type;
			}
		}

		public int getMetadata()
		{
			return metadata;
		}

		@Override
		public String getName()
		{
			return name;
		}

		public static OreType byMetadata(int metadata)
		{
			if (metadata < 0 || metadata >= values().length)
			{
				metadata = 0;
			}

			return values()[metadata];
		}


	}
}