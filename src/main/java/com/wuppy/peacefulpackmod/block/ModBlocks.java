package com.wuppy.peacefulpackmod.block;

import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.fml.relauncher.Side;

import com.wuppy.peacefulpackmod.PeacefulPack;

@SuppressWarnings("AssignmentToNull")
@ObjectHolder(PeacefulPack.modid)
public class ModBlocks
{
	public static final Block ore_block;
	public static final Block flax;
	public static final Block slime_slab;
	public static final Block rotten_plant;
	public static final Block blaze_log;
	public static final Block blaze_leaves;
	public static final Block ghast_ore;
	public static final Block ender_clam;
	public static final Block blaze_sapling;
	public static final Block remains;

	static {
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

	/*public static void postInit(FMLPostInitializationEvent event)
	{
		if (event.getSide() == Side.CLIENT)
		{
			RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();

			BlockPeacefulOres.OreType.registerRenders();
			
			renderItem.getItemModelMesher().register(Item.getItemFromBlock(flax), 0, new ModelResourceLocation(PeacefulPack.modid + ":" + ((BlockFlax) flax).getName(), "inventory"));

			renderItem.getItemModelMesher().register(Item.getItemFromBlock(slimeSlab), 0, new ModelResourceLocation(PeacefulPack.modid + ":" + ((BlockSlimeSlab) slimeSlab).getName(), "inventory"));
			renderItem.getItemModelMesher().register(Item.getItemFromBlock(rottenPlant), 0, new ModelResourceLocation(PeacefulPack.modid + ":" + ((BlockRottenPlant) rottenPlant).getName(), "inventory"));
			renderItem.getItemModelMesher().register(Item.getItemFromBlock(blazeLog), 0, new ModelResourceLocation(PeacefulPack.modid + ":" + ((BlockBlazeLog) blazeLog).getName(), "inventory"));

			renderItem.getItemModelMesher().register(Item.getItemFromBlock(blazeLeaves), 0, new ModelResourceLocation(PeacefulPack.modid + ":" + ((BlockBlazeLeaves) blazeLeaves).getName(), "inventory"));
			
			renderItem.getItemModelMesher().register(Item.getItemFromBlock(ghastOre), 0, new ModelResourceLocation(PeacefulPack.modid + ":" + ((BlockGhastOre) ghastOre).getName(), "inventory"));
			renderItem.getItemModelMesher().register(Item.getItemFromBlock(enderClam), 0, new ModelResourceLocation(PeacefulPack.modid + ":" + ((BlockEnderclam) enderClam).getName(), "inventory"));
			renderItem.getItemModelMesher().register(Item.getItemFromBlock(blazeSapling), 0, new ModelResourceLocation(PeacefulPack.modid + ":" + ((BlockBlazeSapling) blazeSapling).getName(), "inventory"));
			renderItem.getItemModelMesher().register(Item.getItemFromBlock(remains), 0, new ModelResourceLocation(PeacefulPack.modid + ":" + ((BlockRemains) remains).getName(), "inventory"));
		}
	}*/
}