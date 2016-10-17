/**
 * *************************************************************
 * file: RasterCircle.java
 * author: Michael Tran <michaeltran1@cpp.edu>
 * class: CS 445 – Computer Graphics
 *
 * assignment: Program 2 
 * date last modified: 9/30/16 4:57 PM
 *
 * purpose: A class that represents a 2D raster circle
 *
 ***************************************************************
 */
package org.cs445.program2.raster;

import org.lwjgl.util.vector.Vector3f;

public class RasterCircle extends RasterEllipse {

    public RasterCircle(RasterPoint center, float radius) {
        super(center, radius, radius);
        outlineColor = new Vector3f(0.0f, 0.0f, 1.0f);
    }
    
}
