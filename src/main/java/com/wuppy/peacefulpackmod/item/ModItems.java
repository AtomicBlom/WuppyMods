package com.wuppy.peacefulpackmod.item;

import com.wuppy.peacefulpackmod.PeacefulPack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

@SuppressWarnings("AssignmentToNull")
@ObjectHolder(PeacefulPack.modid)
public class ModItems
{
	public static final Item peaceful_material;

	public static final Item flax_seed;
	public static final Item cooked_flesh;
	public static final Item rotten_seed;

	public static final ItemBlock ore_block;
	public static final ItemBlock flax;
	public static final ItemBlock slime_slab;
	public static final ItemBlock rotten_plant;
	public static final ItemBlock blaze_log;
	public static final ItemBlock blaze_leaves;
	public static final ItemBlock ghast_ore;
	public static final ItemBlock ender_clam;
	public static final ItemBlock blaze_sapling;
	public static final ItemBlock remains;

	//Trick IntelliJ into thinking the fields above might not be null;
	static {
		peaceful_material = null;
		flax_seed = null;
		cooked_flesh = null;
		rotten_seed = null;

		ore_block = null;
		flax = null;
		slime_slab = null;
		rotten_plant = null;
		blaze_log = null;
		blaze_leaves = null;
		ghast_ore = null;
		ender_clam = null;
		blaze_sapling = null;
		remains = null;
	}

	/*
	public static void init(FMLPreInitializationEvent event)
	{

		
		if(event.getSide() == Side.CLIENT)
		{
			ItemPeacefulMaterial.registerVariants();
		}
	}

	public static void postInit(FMLPostInitializationEvent event)
	{
		if (event.getSide() == Side.CLIENT)
		{
			RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();

			renderItem.getItemModelMesher().register(peacefulMaterial, 0, new ModelResourceLocation(PeacefulPack.modid + ":" + ((ItemPeacefulMaterial) peacefulMaterial).getNameFromDamage(0), "inventory"));
			renderItem.getItemModelMesher().register(peacefulMaterial, 1, new ModelResourceLocation(PeacefulPack.modid + ":" + ((ItemPeacefulMaterial) peacefulMaterial).getNameFromDamage(1), "inventory"));
			renderItem.getItemModelMesher().register(peacefulMaterial, 2, new ModelResourceLocation(PeacefulPack.modid + ":" + ((ItemPeacefulMaterial) peacefulMaterial).getNameFromDamage(2), "inventory"));
			renderItem.getItemModelMesher().register(peacefulMaterial, 3, new ModelResourceLocation(PeacefulPack.modid + ":" + ((ItemPeacefulMaterial) peacefulMaterial).getNameFromDamage(3), "inventory"));
			renderItem.getItemModelMesher().register(peacefulMaterial, 4, new ModelResourceLocation(PeacefulPack.modid + ":" + ((ItemPeacefulMaterial) peacefulMaterial).getNameFromDamage(4), "inventory"));

			renderItem.getItemModelMesher().register(flaxSeed, 0, new ModelResourceLocation(PeacefulPack.modid + ":" + ((ItemPeacefulSeed) flaxSeed).getName(), "inventory"));
			renderItem.getItemModelMesher().register(cookedFlesh, 0, new ModelResourceLocation(PeacefulPack.modid + ":" + ((ItemCookedFlesh) cookedFlesh).getName(), "inventory"));
			renderItem.getItemModelMesher().register(rottenSeed, 0, new ModelResourceLocation(PeacefulPack.modid + ":" + ((ItemPeacefulSeed) rottenSeed).getName(), "inventory"));
		}
	}*/
}