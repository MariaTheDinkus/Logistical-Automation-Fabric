package com.zundrel.logisticalautomation.api;

import net.minecraft.util.StringRepresentable;

public enum EnumConveyorType implements StringRepresentable {
    NORMAL("normal"),
    RAMP("ramp");

    private final String name;

    EnumConveyorType(String name)
    {
        this.name = name;
    }

    @Override
    public String asString() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
