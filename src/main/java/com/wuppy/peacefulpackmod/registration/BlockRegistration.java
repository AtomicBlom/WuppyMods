package com.wuppy.peacefulpackmod.registration;

import com.wuppy.peacefulpackmod.PeacefulPack;
import com.wuppy.peacefulpackmod.Reference;
import com.wuppy.peacefulpackmod.block.*;
import net.minecraft.block.Block;
import net.minecraft.block.BlockOre;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber
public class BlockRegistration
{
	@SubscribeEvent
	public static void OnBlockRegister(RegistryEvent.Register<Block> event) {
		final IForgeRegistry<Block> registry = event.getRegistry();
		registry.register(configure(new BlockBlazeLeaves(), Reference.Block.blaze_leaves, PeacefulPack.ppBlocksTab));
		registry.register(configure(new BlockBlazeLog(), Reference.Block.blaze_log, PeacefulPack.ppBlocksTab));
		registry.register(configure(new BlockBlazeSapling(), Reference.Block.blaze_sapling, PeacefulPack.ppBlocksTab));
		registry.register(configure(new BlockEnderclam(), Reference.Block.ender_clam, PeacefulPack.ppBlocksTab));
		registry.register(configure(new BlockFlax(), Reference.Block.flax, null));
		registry.register(configure(new BlockGhastOre(), Reference.Block.ghast_ore, PeacefulPack.ppBlocksTab));
		registry.register(configure(new BlockSlimeSlab(), Reference.Block.slime_slab, PeacefulPack.ppBlocksTab));
		registry.register(configure(new BlockRottenPlant(), Reference.Block.rotten_plant, null));
		registry.register(configure(new BlockRemains(), Reference.Block.remains, PeacefulPack.ppBlocksTab));
		registry.register(configure(new BlockPeacefulOres(), Reference.Block.ore_block, PeacefulPack.ppBlocksTab));
	}

	static <B extends Block> B configure(B block, ResourceLocation registryName, CreativeTabs tab) {
		block.setRegistryName(registryName)
				.setUnlocalizedName(registryName.toString());

		if (tab != null)
		{
			block.setCreativeTab(tab);
		}

		return block;
	}
}
