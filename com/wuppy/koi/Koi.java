package com.wuppy.koi;

import java.io.IOException;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityList.EntityEggInfo;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.biome.BiomeGenBase;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = Koi.modid, name = "Koi", version = "1.11.0")
public class Koi
{
	public static final String modid = "wuppy29_koi";
	
	public static final int VERSION = 2;
	public static String updates = "";
	public static boolean outdated = false;
	
	@SidedProxy(clientSide = "com.wuppy.koi.ClientProxyKoi", serverSide = "com.wuppy.koi.CommonProxyKoi")
	public static CommonProxyKoi proxy;
	
	static int startEntityId = 300;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		FMLCommonHandler.instance().bus().register(this);
		
		try
		{
			UpdateChecker.checkForUpdates();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		proxy.registerRenderThings();
		
		EntityRegistry.registerModEntity(Entitykoi.class, "Koi", 44, this, 40, 1, true);
		EntityRegistry.addSpawn(Entitykoi.class, 10, 2, 4, EnumCreatureType.waterCreature, BiomeGenBase.beach, BiomeGenBase.extremeHills, BiomeGenBase.extremeHillsEdge, BiomeGenBase.forest, BiomeGenBase.forestHills, BiomeGenBase.jungle, BiomeGenBase.jungleHills, BiomeGenBase.mushroomIsland, BiomeGenBase.mushroomIslandShore, BiomeGenBase.ocean, BiomeGenBase.plains, BiomeGenBase.river, BiomeGenBase.swampland);
		
		registerEntityEgg(Entitykoi.class, 0x4A8FFF, 0xFFDB4A);
	}
	
	public static int getUniqueEntityId()
	{
		do
		{
			startEntityId++;
		} while (EntityList.getStringFromID(startEntityId) != null);
		
		return startEntityId;
	}
	
	public static void registerEntityEgg(Class<? extends Entity> entity, int primaryColor, int secondaryColor)
	{
		int id = getUniqueEntityId();
		EntityList.addMapping(Entitykoi.class, "Koi", id, primaryColor, secondaryColor);
	}
	
	@SubscribeEvent
	public void checkUpdate(PlayerEvent.PlayerLoggedInEvent event)
	{
		if(outdated)
		{
			event.player.func_146105_b(new ChatComponentText("Koi is outdated."));
			event.player.func_146105_b(new ChatComponentText("Changelog: "));
			event.player.func_146105_b(new ChatComponentText(updates));
		}
	}
}