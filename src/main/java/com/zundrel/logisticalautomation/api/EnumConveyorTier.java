package com.zundrel.logisticalautomation.api;

import net.minecraft.util.StringRepresentable;

public enum EnumConveyorTier implements StringRepresentable {
    NORMAL("normal", 0.125F),
    FAST("fast", 0.25F),
    EXPRESS("express", 0.375F);

    private final String name;
    private final float speed;

    EnumConveyorTier(String name, float speed)
    {
        this.name = name;
        this.speed = speed;
    }

    @Override
    public String asString() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    public float getSpeed() {
        return speed;
    }
}
