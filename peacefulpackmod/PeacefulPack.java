package peacefulpackmod;

import java.io.IOException;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import peacefulpackmod.block.BlockBlazeSapling;
import peacefulpackmod.block.BlockBlazeleaves;
import peacefulpackmod.block.BlockBlazelog;
import peacefulpackmod.block.BlockBuriedRemains;
import peacefulpackmod.block.BlockEnderclam;
import peacefulpackmod.block.BlockGhastOre;
import peacefulpackmod.block.BlockPeacefulOres;
import peacefulpackmod.block.BlockRottenPlant;
import peacefulpackmod.block.Blockflax;
import peacefulpackmod.block.Blockslime;
import peacefulpackmod.item.ItemDust;
import peacefulpackmod.item.ItemPeacefulFood;
import peacefulpackmod.item.ItemPeacefulOresblock;
import peacefulpackmod.item.Itemseed;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = PeacefulPack.modid, name = "Peacefulpack", version = "1.1.7.0")
public class PeacefulPack
{
	public static final String modid = "wuppy29_peacefulpack";

	public static final int VERSION = 3;
	public static String updates = "";
	public static boolean outdated = false;

	// generation config
	public static boolean spawnSulphurNiter = true;
	public static boolean spawnFossils = true;
	public static boolean spawnFlax = true;
	public static boolean spawnSlimePool = true;
	public static boolean spawnRottenPlants = true;
	public static boolean spawnBlazeTrees = true;
	public static boolean spawnGhastOre = true;
	public static boolean spawnEnderClams = true;
	public static boolean spawnRemains = true;
	public static boolean changeWeb = true;

	// blocks
	public static Block oreblock;
	public static Block flax;
	public static Block slimeslab;
	public static Block rottenplant;
	public static Block blazelog;
	public static Block blazeleaves;
	public static Block ghastore;
	public static Block enderclam;
	public static Block blazeSapling;
	public static Block remains;

	// items
	public static Item sulphDust;
	public static Item niterDust;
	public static Item flaxfibre;
	public static Item cloth;
	public static Item flaxseed;
	public static Item cookedflesh;
	public static Item chain;
	public static Item rottenseed;

	Random rand = new Random();

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		MinecraftForge.EVENT_BUS.register(new PeacefulEvent());

		Configuration config = new Configuration(event.getSuggestedConfigurationFile());

		config.load();

		// generation
		spawnSulphurNiter = config.get(Configuration.CATEGORY_GENERAL, "spawnSulphurNiter", true).getBoolean(true);
		spawnFossils = config.get(Configuration.CATEGORY_GENERAL, "spawnFossils", true).getBoolean(true);
		spawnFlax = config.get(Configuration.CATEGORY_GENERAL, "spawnFlax", true).getBoolean(true);
		spawnSlimePool = config.get(Configuration.CATEGORY_GENERAL, "spawnSlimeSlabs", true).getBoolean(true);
		spawnRottenPlants = config.get(Configuration.CATEGORY_GENERAL, "spawnRottenPlants", true).getBoolean(true);
		spawnBlazeTrees = config.get(Configuration.CATEGORY_GENERAL, "spawnBlazeTrees", true).getBoolean(true);
		spawnGhastOre = config.get(Configuration.CATEGORY_GENERAL, "spawnGhastOre", true).getBoolean(true);
		spawnEnderClams = config.get(Configuration.CATEGORY_GENERAL, "spawnEnderClams", true).getBoolean(true);
		spawnRemains = config.get(Configuration.CATEGORY_GENERAL, "spawnRemains", true).getBoolean(true);
		changeWeb = config.get(Configuration.CATEGORY_GENERAL, "changeWeb", true).getBoolean(true);

		config.save();

		try
		{
			UpdateChecker.checkForUpdates();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		// blocks
		oreblock = new BlockPeacefulOres().func_149672_a(Block.field_149769_e).func_149711_c(3F).func_149752_b(1.0F).func_149663_c("oreblock");
		flax = new Blockflax().func_149672_a(Block.field_149779_h).func_149711_c(0.0F).func_149663_c("flax");
		slimeslab = new Blockslime().func_149711_c(1F).func_149715_a(0.5F).func_149752_b(1.0F).func_149663_c("slime").func_149658_d(modid + ":" + "slime");
		rottenplant = new BlockRottenPlant().func_149672_a(Block.field_149779_h).func_149711_c(0.0F).func_149663_c("rottenplant");
		blazelog = new BlockBlazelog().func_149672_a(Block.field_149766_f).func_149711_c(2F).func_149752_b(1.0F).func_149663_c("blazelog");
		blazeleaves = new BlockBlazeleaves().func_149715_a(0.7F).func_149672_a(Block.field_149779_h).func_149711_c(0.2F).func_149713_g(1).func_149663_c("blazeleaves").func_149658_d(modid + ":" + "blazeleaves");
		ghastore = new BlockGhastOre().func_149672_a(Block.field_149769_e).func_149711_c(3F).func_149752_b(1.0F).func_149663_c("ghastore").func_149658_d(modid + ":" + "ghastore");
		enderclam = new BlockEnderclam().func_149672_a(Block.field_149769_e).func_149715_a(0.5F).func_149711_c(3F).func_149752_b(1.0F).func_149663_c("enderclam");
		blazeSapling = new BlockBlazeSapling().func_149672_a(Block.field_149779_h).func_149711_c(0.0F).func_149663_c("blazeSapling").func_149658_d(modid + ":" + "blazeSapling");
		remains = new BlockBuriedRemains().func_149672_a(Block.field_149767_g).func_149711_c(0.5F).func_149663_c("remains").func_149658_d(modid + ":" + "remains");

		// block register
		GameRegistry.registerBlock(oreblock, ItemPeacefulOresblock.class, "Wuppy29_OreBlocks");
		GameRegistry.registerBlock(flax, "Wuppy29_Flax");
		GameRegistry.registerBlock(slimeslab, "Wuppy29_SlimeSlab");
		GameRegistry.registerBlock(rottenplant, "Wuppy29_ RottenPlant");
		GameRegistry.registerBlock(blazelog, "Wuppy29_BlazeLog");
		GameRegistry.registerBlock(blazeleaves, "Wuppy29_BlazeLeaves");
		GameRegistry.registerBlock(ghastore, "Wuppy29_GhastOre");
		GameRegistry.registerBlock(enderclam, "Wuppy29_EnderClam");
		GameRegistry.registerBlock(blazeSapling, "Wuppy29_BlazeSapling");
		GameRegistry.registerBlock(remains, "Wuppy29_Remains");

		// items
		sulphDust = new ItemDust().setUnlocalizedName("sulphdust");
		niterDust = new ItemDust().setUnlocalizedName("niterdust");
		flaxfibre = new ItemDust().setUnlocalizedName("flaxfibre");
		cloth = new ItemDust().setUnlocalizedName("cloth");
		flaxseed = new Itemseed(flax, Blocks.grass).setUnlocalizedName("flaxseed");
		cookedflesh = new ItemPeacefulFood(4, 5, true).setUnlocalizedName("cookedflesh");
		chain = new ItemDust().setUnlocalizedName("chain");
		rottenseed = new Itemseed(rottenplant, Blocks.netherrack).setUnlocalizedName("rottenseed");

		// item register
		GameRegistry.registerItem(sulphDust, "Wuppy29_SulphDust");
		GameRegistry.registerItem(niterDust, "Wuppy29_NiterDust");
		GameRegistry.registerItem(flaxfibre, "Wuppy29_FlaxFibre");
		GameRegistry.registerItem(cloth, "Wuppy29_Cloth");
		GameRegistry.registerItem(flaxseed, "Wuppy29_FlaxSeed");
		GameRegistry.registerItem(cookedflesh, "Wuppy29_CookedFlesh");
		GameRegistry.registerItem(chain, "Wuppy29_Chain");
		GameRegistry.registerItem(rottenseed, "Wuppy29_RottenSeed");
	}

	@EventHandler
	public void load(FMLInitializationEvent event)
	{
		// fuel
		GameRegistry.registerFuelHandler(new PeacefulFuel());
		GameRegistry.registerWorldGenerator(new PeacefulGenerator(), 0);

		// smelting recipes
		GameRegistry.addSmelting(Items.rotten_flesh, new ItemStack(cookedflesh, 1), 1F);

		// recipes
		GameRegistry.addRecipe(new ItemStack(slimeslab, 1), new Object[] { "XX", "XX", 'X', Items.slime_ball });
		GameRegistry.addShapelessRecipe(new ItemStack(Items.gunpowder, 2), new Object[] { new ItemStack(sulphDust), new ItemStack(niterDust), new ItemStack(Items.coal) });
		GameRegistry.addShapelessRecipe(new ItemStack(Items.gunpowder, 2), new Object[] { new ItemStack(sulphDust), new ItemStack(niterDust), new ItemStack(Items.coal, 1) });
		GameRegistry.addRecipe(new ItemStack(Blocks.torch, 4), new Object[] { "X", "Z", 'X', sulphDust, 'Z', Items.stick });
		GameRegistry.addShapelessRecipe(new ItemStack(Items.dye, 2, 15), new Object[] { new ItemStack(Items.dye, 1, 15), new ItemStack(niterDust) });
		GameRegistry.addShapelessRecipe(new ItemStack(Items.string, 2), new Object[] { new ItemStack(flaxfibre), new ItemStack(flaxfibre) });
		GameRegistry.addShapelessRecipe(new ItemStack(flaxseed, 3), new Object[] { new ItemStack(flaxfibre) });
		GameRegistry.addShapelessRecipe(new ItemStack(Blocks.wool, 1, 0), new Object[] { new ItemStack(cloth), new ItemStack(cloth) });
		GameRegistry.addShapelessRecipe(new ItemStack(cloth, 1), new Object[] { new ItemStack(Items.string), new ItemStack(Items.string) });
		GameRegistry.addRecipe(new ItemStack(chain, 6), new Object[] { "XXX", "XXX", "XXX", 'X', Blocks.iron_bars });
		GameRegistry.addShapelessRecipe(new ItemStack(Items.book), new Object[] { new ItemStack(Items.paper), new ItemStack(Items.paper), new ItemStack(Items.paper), new ItemStack(cloth) });

		// leather armor
		GameRegistry.addRecipe(new ItemStack(Items.leather_helmet, 1), new Object[] { "XXX", "X X", 'X', cloth });
		GameRegistry.addRecipe(new ItemStack(Items.leather_chestplate, 1), new Object[] { "X X", "XXX", "XXX", 'X', cloth });
		GameRegistry.addRecipe(new ItemStack(Items.leather_boots, 1), new Object[] { "X X", "X X", 'X', cloth });
		GameRegistry.addRecipe(new ItemStack(Items.leather_leggings, 1), new Object[] { "XXX", "X X", "X X", 'X', cloth });

		// chain armor
		GameRegistry.addRecipe(new ItemStack(Items.chainmail_helmet, 1), new Object[] { "XXX", "X X", 'X', chain });
		GameRegistry.addRecipe(new ItemStack(Items.chainmail_chestplate, 1), new Object[] { "X X", "XXX", "XXX", 'X', chain });
		GameRegistry.addRecipe(new ItemStack(Items.chainmail_boots, 1), new Object[] { "X X", "X X", 'X', chain });
		GameRegistry.addRecipe(new ItemStack(Items.chainmail_leggings, 1), new Object[] { "XXX", "X X", "X X", 'X', chain });

		GameRegistry.addRecipe(new ItemStack(Items.ghast_tear, 4), new Object[] { "XXX", "XZX", "XXX", 'Z', Items.lava_bucket, 'X', ghastore });
		GameRegistry.addShapelessRecipe(new ItemStack(Items.leather), new Object[] { new ItemStack(cookedflesh), new ItemStack(cookedflesh) });
		GameRegistry.addShapelessRecipe(new ItemStack(Items.writable_book, 1), new Object[] { new ItemStack(Items.book), new ItemStack(niterDust), new ItemStack(Items.feather) });
	}
}
