package com.wuppy.goblinsgiants.dimension.savannah;

import static net.minecraftforge.event.terraingen.InitMapGenEvent.EventType.CAVE;
import static net.minecraftforge.event.terraingen.InitMapGenEvent.EventType.MINESHAFT;
import static net.minecraftforge.event.terraingen.InitMapGenEvent.EventType.RAVINE;
import static net.minecraftforge.event.terraingen.PopulateChunkEvent.Populate.EventType.LAVA;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.SpawnerAnimals;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.gen.NoiseGenerator;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraft.world.gen.NoiseGeneratorPerlin;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.terraingen.ChunkProviderEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.event.terraingen.TerrainGen;

import com.wuppy.goblinsgiants.blocks.ModBlocks;
import com.wuppy.goblinsgiants.worldgen.WorldGenLavaTrap;
import com.wuppy.goblinsgiants.worldgen.WorldGenMinableSandstone;
import com.wuppy.goblinsgiants.worldgen.WorldGenRedCactus;
import com.wuppy.goblinsgiants.worldgen.savannah.WorldGenEmptyTemple;
import com.wuppy.goblinsgiants.worldgen.savannah.WorldGenLavaLakesSavannah;
import com.wuppy.goblinsgiants.worldgen.savannah.WorldGenSavWell;
import com.wuppy.goblinsgiants.worldgen.savannah.WorldGenSavannahTree;

import cpw.mods.fml.common.eventhandler.Event.Result;

public class ChunkProviderSavannah implements IChunkProvider
{
    /**
     * RNG.
     */
    private Random rand;
    private NoiseGeneratorOctaves field_147431_j;
    private NoiseGeneratorOctaves field_147432_k;
    private NoiseGeneratorOctaves field_147429_l;
    private NoiseGeneratorPerlin field_147430_m;
    /**
     * A NoiseGeneratorOctaves used in generating terrain
     */
    public NoiseGeneratorOctaves noiseGen5;
    /**
     * A NoiseGeneratorOctaves used in generating terrain
     */
    public NoiseGeneratorOctaves noiseGen6;
    public NoiseGeneratorOctaves mobSpawnerNoise;
    /**
     * Reference to the World object.
     */
    private World worldObj;
    /**
     * are map structures going to be generated (e.g. strongholds)
     */
    private final boolean mapFeaturesEnabled;
    private WorldType field_147435_p;
    private final double[] field_147434_q;
    private final float[] parabolicField;
    private double[] stoneNoise = new double[256];
    private MapGenBase caveGenerator = new MapGenCavesSavannah();
    private MapGenMineshaftSavannah mineshaftGenerator = new MapGenMineshaftSavannah();
    private MapGenBase ravineGenerator = new MapGenRavineSavannah();
    /**
     * The biomes that are used to generate the chunk
     */
    private BiomeGenBase[] biomesForGeneration;
    double[] field_147427_d;
    double[] field_147428_e;
    double[] field_147425_f;
    double[] field_147426_g;
    int[][] field_73219_j = new int[32][32];

    {
        caveGenerator = TerrainGen.getModdedMapGen(caveGenerator, CAVE);
        mineshaftGenerator = (MapGenMineshaftSavannah) TerrainGen.getModdedMapGen(mineshaftGenerator, MINESHAFT);
        ravineGenerator = TerrainGen.getModdedMapGen(ravineGenerator, RAVINE);
    }    

    public ChunkProviderSavannah(World par1World, long par2, boolean par4)
    {
        this.worldObj = par1World;
        this.mapFeaturesEnabled = par4;
        this.field_147435_p = par1World.getWorldInfo().getTerrainType();
        this.rand = new Random(par2);
        this.field_147431_j = new NoiseGeneratorOctaves(this.rand, 16);
        this.field_147432_k = new NoiseGeneratorOctaves(this.rand, 16);
        this.field_147429_l = new NoiseGeneratorOctaves(this.rand, 8);
        this.field_147430_m = new NoiseGeneratorPerlin(this.rand, 4);
        this.noiseGen5 = new NoiseGeneratorOctaves(this.rand, 8);
        this.noiseGen6 = new NoiseGeneratorOctaves(this.rand, 16);
        this.mobSpawnerNoise = new NoiseGeneratorOctaves(this.rand, 8);
        this.field_147434_q = new double[825];
        this.parabolicField = new float[25];

        for (int j = -2; j <= 2; ++j)
        {
            for (int k = -2; k <= 2; ++k)
            {
                float f = 10.0F / MathHelper.sqrt_float((float)(j * j + k * k) + 0.2F);
                this.parabolicField[j + 2 + (k + 2) * 5] = f;
            }
        }

        NoiseGenerator[] noiseGens = {field_147431_j, field_147432_k, field_147429_l, field_147430_m, noiseGen5, noiseGen6, mobSpawnerNoise};
        noiseGens = TerrainGen.getModdedNoiseGenerators(par1World, this.rand, noiseGens);
        this.field_147431_j = (NoiseGeneratorOctaves)noiseGens[0];
        this.field_147432_k = (NoiseGeneratorOctaves)noiseGens[1];
        this.field_147429_l = (NoiseGeneratorOctaves)noiseGens[2];
        this.field_147430_m = (NoiseGeneratorPerlin)noiseGens[3];
        this.noiseGen5 = (NoiseGeneratorOctaves)noiseGens[4];
        this.noiseGen6 = (NoiseGeneratorOctaves)noiseGens[5];
        this.mobSpawnerNoise = (NoiseGeneratorOctaves)noiseGens[6];
    }

    public void generateTerrain(int par1, int par2, Block[] blocks)
    {
        byte b0 = 63;
        this.biomesForGeneration = this.worldObj.getWorldChunkManager().getBiomesForGeneration(this.biomesForGeneration, par1 * 4 - 2, par2 * 4 - 2, 10, 10);
        this.func_147423_a(par1 * 4, 0, par2 * 4);

        for (int k = 0; k < 4; ++k)
        {
            int l = k * 5;
            int i1 = (k + 1) * 5;

            for (int j1 = 0; j1 < 4; ++j1)
            {
                int k1 = (l + j1) * 33;
                int l1 = (l + j1 + 1) * 33;
                int i2 = (i1 + j1) * 33;
                int j2 = (i1 + j1 + 1) * 33;

                for (int k2 = 0; k2 < 32; ++k2)
                {
                    double d0 = 0.125D;
                    double d1 = this.field_147434_q[k1 + k2];
                    double d2 = this.field_147434_q[l1 + k2];
                    double d3 = this.field_147434_q[i2 + k2];
                    double d4 = this.field_147434_q[j2 + k2];
                    double d5 = (this.field_147434_q[k1 + k2 + 1] - d1) * d0;
                    double d6 = (this.field_147434_q[l1 + k2 + 1] - d2) * d0;
                    double d7 = (this.field_147434_q[i2 + k2 + 1] - d3) * d0;
                    double d8 = (this.field_147434_q[j2 + k2 + 1] - d4) * d0;

                    for (int l2 = 0; l2 < 8; ++l2)
                    {
                        double d9 = 0.25D;
                        double d10 = d1;
                        double d11 = d2;
                        double d12 = (d3 - d1) * d9;
                        double d13 = (d4 - d2) * d9;

                        for (int i3 = 0; i3 < 4; ++i3)
                        {
                            int j3 = i3 + k * 4 << 12 | 0 + j1 * 4 << 8 | k2 * 8 + l2;
                            short short1 = 256;
                            j3 -= short1;
                            double d14 = 0.25D;
                            double d16 = (d11 - d10) * d14;
                            double d15 = d10 - d16;

                            for (int k3 = 0; k3 < 4; ++k3)
                            {
                            	if ((d15 += d16) > 0.0D)
								{
									blocks[j3 += short1] = Blocks.sandstone;
								}
								else if (k2 * 8 + l2 < b0)
								{
									blocks[j3 += short1] = Blocks.sandstone;
								}
								else
								{
									blocks[j3 += short1] = Blocks.air;
								}

                            }

                            d10 += d12;
                            d11 += d13;
                        }

                        d1 += d5;
                        d2 += d6;
                        d3 += d7;
                        d4 += d8;
                    }
                }
            }
        }
    }

    public void replaceBlocksForBiome(int p_147422_1_, int p_147422_2_, Block[] p_147422_3_, byte[] p_147422_4_, BiomeGenBase[] p_147422_5_)
    {
        ChunkProviderEvent.ReplaceBiomeBlocks event = new ChunkProviderEvent.ReplaceBiomeBlocks(this, p_147422_1_, p_147422_2_, p_147422_3_, p_147422_5_);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.getResult() == Result.DENY) return;

        double d0 = 0.03125D;
        this.stoneNoise = this.field_147430_m.func_151599_a(this.stoneNoise, (double)(p_147422_1_ * 16), (double)(p_147422_2_ * 16), 16, 16, d0 * 2.0D, d0 * 2.0D, 1.0D);

        for (int k = 0; k < 16; ++k)
        {
            for (int l = 0; l < 16; ++l)
            {
                BiomeGenBase biomegenbase = p_147422_5_[l + k * 16];
                biomegenbase.genTerrainBlocks(this.worldObj, this.rand, p_147422_3_, p_147422_4_, p_147422_1_ * 16 + k, p_147422_2_ * 16 + l, this.stoneNoise[l + k * 16]);
            }
        }
    }

    /**
     * loads or generates the chunk at the chunk location specified
     */
    public Chunk loadChunk(int par1, int par2)
    {
        return this.provideChunk(par1, par2);
    }

    /**
     * Will return back a chunk, if it doesn't exist and its not a MP client it will generates all the blocks for the
     * specified chunk from the map seed and chunk seed
     */
    public Chunk provideChunk(int par1, int par2)
    {
        this.rand.setSeed((long)par1 * 341873128712L + (long)par2 * 132897987541L);
        Block[] ablock = new Block[65536];
        byte[] abyte = new byte[65536];
        this.generateTerrain(par1, par2, ablock);
        this.biomesForGeneration = this.worldObj.getWorldChunkManager().loadBlockGeneratorData(this.biomesForGeneration, par1 * 16, par2 * 16, 16, 16);
        this.replaceBlocksForBiome(par1, par2, ablock, abyte, this.biomesForGeneration);
        this.caveGenerator.func_151539_a(this, this.worldObj, par1, par2, ablock);
        this.ravineGenerator.func_151539_a(this, this.worldObj, par1, par2, ablock);

        if (this.mapFeaturesEnabled)
        {
            this.mineshaftGenerator.func_151539_a(this, this.worldObj, par1, par2, ablock);
        }

        Chunk chunk = new Chunk(this.worldObj, ablock, abyte, par1, par2);
        byte[] abyte1 = chunk.getBiomeArray();

        for (int k = 0; k < abyte1.length; ++k)
        {
            abyte1[k] = (byte)this.biomesForGeneration[k].biomeID;
        }

        chunk.generateSkylightMap();
        return chunk;
    }

    private void func_147423_a(int p_147423_1_, int p_147423_2_, int p_147423_3_)
    {
        this.field_147426_g = this.noiseGen6.generateNoiseOctaves(this.field_147426_g, p_147423_1_, p_147423_3_, 5, 5, 200.0D, 200.0D, 0.5D);
        this.field_147427_d = this.field_147429_l.generateNoiseOctaves(this.field_147427_d, p_147423_1_, p_147423_2_, p_147423_3_, 5, 33, 5, 8.555150000000001D, 4.277575000000001D, 8.555150000000001D);
        this.field_147428_e = this.field_147431_j.generateNoiseOctaves(this.field_147428_e, p_147423_1_, p_147423_2_, p_147423_3_, 5, 33, 5, 684.412D, 684.412D, 684.412D);
        this.field_147425_f = this.field_147432_k.generateNoiseOctaves(this.field_147425_f, p_147423_1_, p_147423_2_, p_147423_3_, 5, 33, 5, 684.412D, 684.412D, 684.412D);
        int l = 0;
        int i1 = 0;

        for (int j1 = 0; j1 < 5; ++j1)
        {
            for (int k1 = 0; k1 < 5; ++k1)
            {
                float f = 0.0F;
                float f1 = 0.0F;
                float f2 = 0.0F;
                byte b0 = 2;
                BiomeGenBase biomegenbase = this.biomesForGeneration[j1 + 2 + (k1 + 2) * 10];

                for (int l1 = -b0; l1 <= b0; ++l1)
                {
                    for (int i2 = -b0; i2 <= b0; ++i2)
                    {
                        BiomeGenBase biomegenbase1 = this.biomesForGeneration[j1 + l1 + 2 + (k1 + i2 + 2) * 10];
                        float f3 = biomegenbase1.rootHeight;
                        float f4 = biomegenbase1.heightVariation;

                        if (this.field_147435_p == WorldType.AMPLIFIED && f3 > 0.0F)
                        {
                            f3 = 1.0F + f3 * 2.0F;
                            f4 = 1.0F + f4 * 4.0F;
                        }

                        float f5 = this.parabolicField[l1 + 2 + (i2 + 2) * 5] / (f3 + 2.0F);

                        if (biomegenbase1.rootHeight > biomegenbase.rootHeight)
                        {
                            f5 /= 2.0F;
                        }

                        f += f4 * f5;
                        f1 += f3 * f5;
                        f2 += f5;
                    }
                }

                f /= f2;
                f1 /= f2;
                f = f * 0.9F + 0.1F;
                f1 = (f1 * 4.0F - 1.0F) / 8.0F;
                double d13 = this.field_147426_g[i1] / 8000.0D;

                if (d13 < 0.0D)
                {
                    d13 = -d13 * 0.3D;
                }

                d13 = d13 * 3.0D - 2.0D;

                if (d13 < 0.0D)
                {
                    d13 /= 2.0D;

                    if (d13 < -1.0D)
                    {
                        d13 = -1.0D;
                    }

                    d13 /= 1.4D;
                    d13 /= 2.0D;
                }
                else
                {
                    if (d13 > 1.0D)
                    {
                        d13 = 1.0D;
                    }

                    d13 /= 8.0D;
                }

                ++i1;
                double d12 = (double)f1;
                double d14 = (double)f;
                d12 += d13 * 0.2D;
                d12 = d12 * 8.5D / 8.0D;
                double d5 = 8.5D + d12 * 4.0D;

                for (int j2 = 0; j2 < 33; ++j2)
                {
                    double d6 = ((double)j2 - d5) * 12.0D * 128.0D / 256.0D / d14;

                    if (d6 < 0.0D)
                    {
                        d6 *= 4.0D;
                    }

                    double d7 = this.field_147428_e[l] / 250.0D;
                    double d8 = this.field_147425_f[l] / 152.0D;
                    double d9 = (this.field_147427_d[l] / 10.0D + 1.0D) / 2.0D;
                    double d10 = MathHelper.denormalizeClamp(d7, d8, d9) - d6;

                    if (j2 > 29)
                    {
                        double d11 = (double)((float)(j2 - 29) / 3.0F);
                        d10 = d10 * (1.0D - d11) + -10.0D * d11;
                    }

                    this.field_147434_q[l] = d10;
                    ++l;
                }
            }
        }
    }

    /**
     * Checks to see if a chunk exists at x, y
     */
    public boolean chunkExists(int par1, int par2)
    {
        return true;
    }

    /**
     * Populates chunk with ores etc etc
     */
    public void populate(IChunkProvider par1IChunkProvider, int par2, int par3)
    {
        BlockFalling.fallInstantly = true;
        int var4 = par2 * 16;
        int var5 = par3 * 16;
        BiomeGenBase biomegenbase = this.worldObj.getBiomeGenForCoords(var4 + 16, var5 + 16);
        this.rand.setSeed(this.worldObj.getSeed());
        long i1 = this.rand.nextLong() / 2L * 2L + 1L;
        long j1 = this.rand.nextLong() / 2L * 2L + 1L;
        this.rand.setSeed((long)par2 * i1 + (long)par3 * j1 ^ this.worldObj.getSeed());
        boolean var11 = false;

        MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Pre(par1IChunkProvider, worldObj, rand, par2, par3, var11));

        if (this.mapFeaturesEnabled)
        {
            this.mineshaftGenerator.generateStructuresInChunk(this.worldObj, this.rand, par2, par3);
        }

    	int var12;
		int var13;
		int var14;

        if (TerrainGen.populate(par1IChunkProvider, worldObj, rand, par2, par3, var11, LAVA) && !var11 && this.rand.nextInt(4) == 0)
		{
			var12 = var4 + this.rand.nextInt(16) + 8;
			var13 = this.rand.nextInt(this.rand.nextInt(120) + 8);
			var14 = var5 + this.rand.nextInt(16) + 8;

			if (var13 < 63 || this.rand.nextInt(10) == 0)
			{
				(new WorldGenLavaLakesSavannah(Blocks.lava)).generate(this.worldObj, this.rand, var12, var13, var14);
			}
		}

		for (int k = 0; k < 1; k++)
		{
			int RandPosX = var4 + rand.nextInt(5);
			int RandPosY = rand.nextInt(128);
			int RandPosZ = var5 + rand.nextInt(5);
			(new WorldGenRedCactus()).generate(worldObj, rand, RandPosX, RandPosY, RandPosZ);
		}

		for (int k = 0; k < 14; k++)
		{
			int RandPosX = var4 + rand.nextInt(16);
			int RandPosY = rand.nextInt(64);
			int RandPosZ = var5 + rand.nextInt(16);
			(new WorldGenMinableSandstone(Blocks.sand, 0, 20)).generate(worldObj, rand, RandPosX, RandPosY, RandPosZ);
		}
		/*
		 * for(int k = 0; k < 1; k++) { int RandPosX = var4 + rand.nextInt(16); int RandPosY = rand.nextInt(128); int RandPosZ = var5 + rand.nextInt(16); (new WorldGenCreeperTemple()).generate(worldObj, rand, RandPosX, RandPosY, RandPosZ); }
		 */

		for (int k = 0; k < 2; k++)
		{
			int RandPosX = var4 + rand.nextInt(16);
			int RandPosY = rand.nextInt(64);
			int RandPosZ = var5 + rand.nextInt(16);
			(new WorldGenMinableSandstone(ModBlocks.customstone, 2, 22)).generate(worldObj, rand, RandPosX, RandPosY, RandPosZ);
		}

		for (int k = 0; k < 8; k++)
		{
			int RandPosX = var4 + rand.nextInt(16);
			int RandPosY = rand.nextInt(64);
			int RandPosZ = var5 + rand.nextInt(16);
			(new WorldGenMinableSandstone(ModBlocks.customstone, 1, 14)).generate(worldObj, rand, RandPosX, RandPosY, RandPosZ);
		}

		for (int k = 0; k < 2; k++)
		{
			int RandPosX = var4 + rand.nextInt(16);
			int RandPosY = rand.nextInt(32);
			int RandPosZ = var5 + rand.nextInt(16);
			(new WorldGenMinableSandstone(ModBlocks.customstone, 0, 8)).generate(worldObj, rand, RandPosX, RandPosY, RandPosZ);
		}

		for (int k = 0; k < 8; k++)
		{
			int RandPosX = var4 + rand.nextInt(16);
			int RandPosY = rand.nextInt(25);
			int RandPosZ = var5 + rand.nextInt(16);
			(new WorldGenMinableSandstone(ModBlocks.customstone, 4, 8)).generate(worldObj, rand, RandPosX, RandPosY, RandPosZ);
		}

		for (int k = 0; k < 1; k++)
		{
			int RandPosX = var4 + rand.nextInt(16);
			int RandPosY = rand.nextInt(16);
			int RandPosZ = var5 + rand.nextInt(16);
			(new WorldGenMinableSandstone(ModBlocks.customstone, 3, 6)).generate(worldObj, rand, RandPosX, RandPosY, RandPosZ);
		}

		for (int k = 0; k < 1; k++)
		{
			int RandPosX = var4 + rand.nextInt(16);
			int RandPosY = rand.nextInt(25);
			int RandPosZ = var5 + rand.nextInt(16);
			(new WorldGenMinableSandstone(ModBlocks.customstone, 5, 6)).generate(worldObj, rand, RandPosX, RandPosY, RandPosZ);
		}

		for (int k = 0; k < 1; k++)
		{
			int RandPosX = var4 + rand.nextInt(16);
			int RandPosY = rand.nextInt(25);
			int RandPosZ = var5 + rand.nextInt(16);
			(new WorldGenMinableSandstone(ModBlocks.oreblock, 1, 1)).generate(worldObj, rand, RandPosX, RandPosY, RandPosZ);
		}

		for (int k = 0; k < 1; k++)
		{
			int RandPosX = var4 + rand.nextInt(16);
			int RandPosY = 55 + rand.nextInt(40);
			int RandPosZ = var5 + rand.nextInt(16);
			(new WorldGenSavannahTree()).generate(worldObj, rand, RandPosX, RandPosY, RandPosZ);
		}
		
		for (int k = 0; k < 1; k++)
		{
			int RandPosX = var4 + rand.nextInt(16);
			int RandPosY = 55 + rand.nextInt(40);
			int RandPosZ = var5 + rand.nextInt(16);
			(new WorldGenEmptyTemple()).generate(worldObj, rand, RandPosX, RandPosY, RandPosZ);
		}
		
		for (int k = 0; k < 17; k++)
		{
			int RandPosX = var4 + rand.nextInt(16);
			int RandPosY = rand.nextInt(128);
			int RandPosZ = var5 + rand.nextInt(16);
			(new WorldGenLavaTrap()).generate(worldObj, rand, RandPosX, RandPosY, RandPosZ);
		}
		
		for (int k = 0; k < 1; k++)
		{
			int RandPosX = var4 + rand.nextInt(16);
			int RandPosY = 55 + rand.nextInt(40);
			int RandPosZ = var5 + rand.nextInt(16);
			(new WorldGenSavWell()).generate(worldObj, rand, RandPosX, RandPosY, RandPosZ);
		}


        biomegenbase.decorate(this.worldObj, this.rand, var4, var5);
        SpawnerAnimals.performWorldGenSpawning(this.worldObj, biomegenbase, var4 + 8, var5 + 8, 16, 16, this.rand);
        var4 += 8;
        var5 += 8;

        MinecraftForge.EVENT_BUS.post(new PopulateChunkEvent.Post(par1IChunkProvider, worldObj, rand, par2, par3, var11));

        BlockFalling.fallInstantly = false;
    }

    /**
     * Two modes of operation: if passed true, save all Chunks in one go.  If passed false, save up to two chunks.
     * Return true if all chunks have been saved.
     */
    public boolean saveChunks(boolean par1, IProgressUpdate par2IProgressUpdate)
    {
        return true;
    }

    /**
     * Save extra data not associated with any Chunk.  Not saved during autosave, only during world unload.  Currently
     * unimplemented.
     */
    public void saveExtraData() {}

    /**
     * Unloads chunks that are marked to be unloaded. This is not guaranteed to unload every such chunk.
     */
    public boolean unloadQueuedChunks()
    {
        return false;
    }

    /**
     * Returns if the IChunkProvider supports saving.
     */
    public boolean canSave()
    {
        return true;
    }

    /**
     * Converts the instance data to a readable string.
     */
    public String makeString()
    {
        return "RandomLevelSource";
    }

    /**
     * Returns a list of creatures of the specified type that can spawn at the given location.
     */
	@SuppressWarnings("rawtypes")
	public List getPossibleCreatures(EnumCreatureType par1EnumCreatureType, int par2, int par3, int par4)
	{
		BiomeGenBase var5 = this.worldObj.getBiomeGenForCoords(par2, par4);
		return var5.getSpawnableList(par1EnumCreatureType);
	}

    public ChunkPosition func_147416_a(World p_147416_1_, String p_147416_2_, int p_147416_3_, int p_147416_4_, int p_147416_5_)
    {
    	return null;
    }

    public int getLoadedChunkCount()
    {
        return 0;
    }

    public void recreateStructures(int par1, int par2)
    {
        if (this.mapFeaturesEnabled)
        {
            this.mineshaftGenerator.func_151539_a(this, this.worldObj, par1, par2, (Block[])null);
        }
    }
}