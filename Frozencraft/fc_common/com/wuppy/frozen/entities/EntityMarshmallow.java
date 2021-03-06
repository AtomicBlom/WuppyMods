package com.wuppy.frozen.entities;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class EntityMarshmallow extends EntityMob implements IRangedAttackMob
{
	public EntityMarshmallow(World world)
	{
		super(world);
		setSize(2F, 3F);
		getNavigator().setBreakDoors(true);
        tasks.addTask(0, new EntityAISwimming(this));
        tasks.addTask(1, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0D, false));
        tasks.addTask(2, new EntityAIArrowAttack(this, 1.0D, 20, 60, 15.0F));
        tasks.addTask(3, new EntityAIMoveTowardsRestriction(this, 1.0D));
        tasks.addTask(4, new EntityAIWander(this, 1.0D));
        tasks.addTask(5, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        tasks.addTask(5, new EntityAILookIdle(this));
        targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
        targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
	}
	
	@Override
	public boolean isAIEnabled()
    {
        return true;
    }
	
	@Override
	protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.35D);
        getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(8D);
        getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(40.0D);
    }
	
	@Override
	protected void dropFewItems(boolean par1, int par2)
	{
		dropItem(Item.getItemFromBlock(Blocks.snow), 10);
		dropItem(Items.diamond, 2);
	}

	@Override
	public void attackEntityWithRangedAttack(EntityLivingBase entity, float var2)
	{
		if (!worldObj.isRemote)
			worldObj.spawnEntityInWorld(new EntitySnowball(worldObj, entity));
	}
}