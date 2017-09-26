package com.wuppy.peacefulpackmod;

import net.minecraft.util.ResourceLocation;

public class Reference {
	public static class Block {
		public static final ResourceLocation blaze_leaves = resource("blaze_leaves");
		public static final ResourceLocation blaze_log = resource("blaze_log");
		public static final ResourceLocation blaze_sapling = resource("blaze_sapling");
		public static final ResourceLocation ender_clam = resource("ender_clam");
		public static final ResourceLocation flax = resource("flax");
		public static final ResourceLocation ghast_ore = resource("ghast_ore");
		public static final ResourceLocation slime_slab = resource("slime_slab");
		public static final ResourceLocation rotten_plant = resource("rotten_plant");
		public static final ResourceLocation remains = resource("remains");
		public static final ResourceLocation ore_block = resource("ore_block");


		private Block() {}
	}

	public static class Item {
		public static final ResourceLocation peaceful_material = resource("peaceful_material");
		public static final ResourceLocation cooked_flesh = resource("cooked_flesh");
		public static final ResourceLocation flax_seed = resource("flax_seed");
		public static final ResourceLocation rotten_seed = resource("rotten_seed");

		public static final String[] material_variants = {"sulphdust", "niterdust", "flax_fibre", "cloth", "chain"};
		private Item() {}
	}


	private static ResourceLocation resource(String name) {
		return new ResourceLocation(PeacefulPack.modid, name);
	}

	private Reference() {}


}