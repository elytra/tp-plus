package com.elytradev.tpp;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

// copied essentially verbatim from RFTools

public class RfToolsTeleporter extends Teleporter {
    private final WorldServer worldServerInstance;

    private double x;
    private double y;
    private double z;


    public RfToolsTeleporter(WorldServer world, double x, double y, double z) {
        super(world);
        this.worldServerInstance = world;
        this.x = x;
        this.y = y;
        this.z = z;

    }

    @Override
    public void placeInPortal(Entity pEntity, float rotationYaw) {
        this.worldServerInstance.getBlockState(new BlockPos((int) this.x, (int) this.y, (int) this.z));   //dummy load to maybe gen chunk

        pEntity.setPosition(this.x, this.y, this.z);
        pEntity.motionX = 0.0f;
        pEntity.motionY = 0.0f;
        pEntity.motionZ = 0.0f;
    }

}