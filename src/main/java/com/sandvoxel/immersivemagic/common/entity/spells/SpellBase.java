package com.sandvoxel.immersivemagic.common.entity.spells;

import com.sandvoxel.immersivemagic.ImmersiveMagic;
import com.sandvoxel.immersivemagic.Refrence;
import com.sandvoxel.immersivemagic.api.magic.IAffinities;
import com.sandvoxel.immersivemagic.common.magicdata.AffinitiesProvider;
import com.sandvoxel.immersivemagic.common.magicdata.AffinityObject;
import com.sandvoxel.immersivemagic.common.magicdata.AffinityTypes;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class SpellBase extends EntityThrowable {
    public SpellBase(World worldIn) {
        super(worldIn);
        noClip = false;


    }

    public SpellBase(World world, EntityLivingBase entityLivingBase){
        super(world,entityLivingBase);
        noClip = false;
        setNoGravity(true);
    }


    @Override
    protected void onImpact(RayTraceResult result) {

        //this.isDead = true;
        //world.setBlockToAir(result.getBlockPos());
        if (!world.isRemote && result.getBlockPos()!=null){
            IAffinities affinities = getThrower().getCapability(AffinitiesProvider.AFFINITIES_CAPABILITY,null);


            for(AffinityObject object : affinities.getPlayerAffinities()){
                if(object.getAffinityType() == AffinityTypes.FIRE){
                    world.createExplosion(this, result.getBlockPos().getX(),result.getBlockPos().getY(),result.getBlockPos().getZ(),(float) object.getAffinityPower(),true);
                }
            }
        }
        this.isDead = true;
    }

    public static void addSpellToRegistry(){
        ResourceLocation resourceLocation = new ResourceLocation(Refrence.MOD_ID+":yesr");
        EntityRegistry.registerModEntity(resourceLocation,SpellBase.class,"spell",32184985,Refrence.MOD_ID,64,10,true);
    }

}