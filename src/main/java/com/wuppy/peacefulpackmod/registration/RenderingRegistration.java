package com.wuppy.peacefulpackmod.registration;

import com.wuppy.peacefulpackmod.PeacefulPack;
import com.wuppy.peacefulpackmod.Reference;
import com.wuppy.peacefulpackmod.block.BlockPeacefulOres;
import com.wuppy.peacefulpackmod.block.BlockPeacefulOres.OreType;
import com.wuppy.peacefulpackmod.item.ModItems;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public final class RenderingRegistration
{
	@SubscribeEvent
	public static void onRenderingReady(ModelRegistryEvent event) {
		setItemModel(ModItems.peaceful_material);
		setItemModel(ModItems.flax_seed);
		setItemModel(ModItems.rotten_seed);
		setItemModel(ModItems.cooked_flesh);

		setItemModel(ModItems.flax);
		setItemModel(ModItems.slime_slab);
		setItemModel(ModItems.rotten_plant);
		setItemModel(ModItems.blaze_log);
		setItemModel(ModItems.blaze_leaves);
		setItemModel(ModItems.ghast_ore);
		setItemModel(ModItems.ender_clam);
		setItemModel(ModItems.blaze_sapling);
		setItemModel(ModItems.remains);

		for (final OreType oreType : OreType.values())
		{
			ModelLoader.setCustomModelResourceLocation(
					ModItems.ore_block,
					oreType.getMetadata(),
					new ModelResourceLocation(ModItems.ore_block.getRegistryName(), "variant=" + oreType.getName())
			);
		}


		for (int i = 0; i < Reference.Item.material_variants.length; i++)
		{
			ModelLoader.setCustomModelResourceLocation(
					ModItems.peaceful_material,
					i,
					new ModelResourceLocation(new ResourceLocation(PeacefulPack.modid, Reference.Item.material_variants[i]), "normal")
			);
		}
	}

	private static void setItemModel(Item item)
	{
		ModelLoader.setCustomModelResourceLocation(
				item,
				0,
				new ModelResourceLocation(item.getRegistryName(), "normal")
		);
	}

	/*public static void registerVariants()
	{
		ResourceLocation[] variants = new ResourceLocation[values().length];

		for (int i = 0; i < values().length; i++)
		{
			variants[i] = values()[i].getResouceLocation();
		}

		ModelBakery.registerItemVariants(Item.getItemFromBlock(ModBlocks.ore_block), variants);
	}

	public static void registerRenders()
	{
		RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();

		for (int i = 0; i < values().length; i++)
		{
			renderItem.getItemModelMesher().register(Item.getItemFromBlock(ModBlocks.ore_block), i, new ModelResourceLocation(values()[i].getResouceLocation(), "inventory"));
		}
	}*/

}
