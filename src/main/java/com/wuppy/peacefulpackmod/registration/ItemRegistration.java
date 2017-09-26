package com.wuppy.peacefulpackmod.registration;

import com.wuppy.peacefulpackmod.PeacefulPack;
import com.wuppy.peacefulpackmod.Reference;
import com.wuppy.peacefulpackmod.block.ModBlocks;
import com.wuppy.peacefulpackmod.item.ItemCookedFlesh;
import com.wuppy.peacefulpackmod.item.ItemPeacefulMaterial;
import com.wuppy.peacefulpackmod.item.ItemPeacefulOresblock;
import com.wuppy.peacefulpackmod.item.ItemPeacefulSeed;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

@EventBusSubscriber
public final class ItemRegistration
{
	@SubscribeEvent
	public static void onItemRegistration(Register<Item> event) {
		final IForgeRegistry<Item> registry = event.getRegistry();

		registry.register(configure(new ItemPeacefulMaterial(), Reference.Item.peaceful_material, PeacefulPack.ppMaterialTab));
		registry.register(configure(new ItemCookedFlesh(4, 5, true), Reference.Item.cooked_flesh, PeacefulPack.ppMaterialTab));
		registry.register(configure(new ItemPeacefulSeed(ModBlocks.flax, Blocks.GRASS), Reference.Item.flax_seed, PeacefulPack.ppMaterialTab));
		registry.register(configure(new ItemPeacefulSeed(ModBlocks.rotten_plant, Blocks.NETHERRACK), Reference.Item.rotten_seed, PeacefulPack.ppMaterialTab));

		registry.register(configure(ModBlocks.slime_slab));
		registry.register(configure(ModBlocks.rotten_plant));
		registry.register(configure(ModBlocks.blaze_log));
		registry.register(configure(ModBlocks.flax));
		registry.register(configure(ModBlocks.blaze_leaves));
		registry.register(configure(ModBlocks.blaze_sapling));
		registry.register(configure(ModBlocks.ender_clam));
		registry.register(configure(ModBlocks.ghast_ore));
		registry.register(configure(ModBlocks.remains));

		registry.register(configureOreBlock(ModBlocks.ore_block));
	}

	private static <B extends Block> Item configure(B block) {
		return new ItemBlock(block)
				.setRegistryName(block.getRegistryName())
				.setUnlocalizedName(block.getRegistryName().toString());
	}

	private static <B extends Block> Item configureOreBlock(B block) {
		return new ItemPeacefulOresblock(block)
				.setRegistryName(block.getRegistryName())
				.setUnlocalizedName(block.getRegistryName().toString());
	}

	private static <B extends Item> B configure(B item, ResourceLocation registryName, CreativeTabs tab) {
		item.setRegistryName(registryName)
				.setUnlocalizedName(registryName.toString());

		if (tab != null)
		{
			item.setCreativeTab(tab);
		}

		return item;
	}
}
