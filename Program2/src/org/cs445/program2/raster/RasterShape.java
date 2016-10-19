/**
 * *************************************************************
 * file: RasterShape.java
 * author: Michael Tran <michaeltran1@cpp.edu>
 * class: CS 445 – Computer Graphics
 *
 * assignment: Program 2 
 * date last modified: 10/17/16 11:02 AM
 *
 * purpose: A class that represents a raster shape
 *
 ***************************************************************
 */
package org.cs445.program2.raster;

import org.lwjgl.util.vector.Vector3f;

public abstract class RasterShape extends Raster {
    
    protected Vector3f outlineColor;
    
    protected Vector3f fillColor;
    
}
