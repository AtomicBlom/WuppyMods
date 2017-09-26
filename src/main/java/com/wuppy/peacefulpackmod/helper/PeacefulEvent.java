package com.wuppy.peacefulpackmod.helper;

import com.wuppy.peacefulpackmod.PeacefulPack;
import com.wuppy.peacefulpackmod.config.Config;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

public class PeacefulEvent
{
	@SubscribeEvent
	public void checkUpdate(PlayerEvent.PlayerLoggedInEvent event)
	{
		if (PeacefulPack.outdated)
		{
			event.player.sendMessage(new TextComponentString("Peacefulpack is outdated."));
			event.player.sendMessage(new TextComponentString("Changelog: "));
			event.player.sendMessage(new TextComponentString(PeacefulPack.updates));
		}
	}

	@SubscribeEvent
	public void dropSpiderEyes(HarvestDropsEvent event)
	{
		if (Config.changeWeb)
		{
			if (event.getState().getBlock() == Blocks.WEB)
			{
				event.getDrops().add(new ItemStack(Items.SPIDER_EYE));
			}
		}
	}
}