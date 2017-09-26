package com.wuppy.peacefulpackmod.helper;

import com.wuppy.peacefulpackmod.block.ModBlocks;
import com.wuppy.peacefulpackmod.item.ModItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public final class PeacefulFuel
{
	@SubscribeEvent
	public static void getBurnTime(FurnaceFuelBurnTimeEvent event)
	{
		final ItemStack fuelStack = event.getItemStack();
		final Item fuel = fuelStack.getItem();
		if (fuel == ModItems.peaceful_material && fuelStack.getMetadata() == 0)
			event.setBurnTime(1000);
		if (fuel == Item.getItemFromBlock(ModBlocks.blaze_log))
			event.setBurnTime(1600);
		else
			event.setBurnTime(0);
	}
}
