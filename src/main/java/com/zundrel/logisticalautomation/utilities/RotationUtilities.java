package com.zundrel.logisticalautomation.utilities;

import net.minecraft.util.math.BoundingBox;
import net.minecraft.util.math.Direction;

public class RotationUtilities {
	public static BoundingBox getRotatedBoundingBox(BoundingBox def, Direction facing) {
	    facing = facing.getOpposite();
		def.offset(-0.5, -0.5, -0.5);
		switch (facing) {
			case SOUTH:
				def = new BoundingBox(def.minZ, def.minY, (def.maxX * -1) + 1, def.maxZ, def.maxY, (def.minX * -1) + 1);
			case WEST:
				def = new BoundingBox((def.maxX * -1) + 1, def.minY, (def.maxZ * -1) + 1, (def.minX * -1) + 1, def.maxY, (def.minZ * -1) + 1);
			case EAST:
				def = new BoundingBox((def.maxZ * -1) + 1, def.minY, def.minX, (def.minZ * -1) + 1, def.maxY, def.maxX);
			default:

		}
		def.offset(0.5, 0.5, 0.5);
		return def;
	}
}