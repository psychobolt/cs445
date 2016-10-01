/**
 * *************************************************************
 * file: Raster.java
 * author: Michael Tran <michaeltran1@cpp.edu>
 * class: CS 445 – Computer Graphics
 *
 * assignment: Program 1 
 * date last modified: 9/30/16 10:40 AM
 *
 * purpose: A abstract class for drawable 2D raster shapes
 *
 ***************************************************************
 */
package org.cs445.program1.raster;

import org.cs445.program1.model.ModelRenderer;

public abstract class Raster implements ModelRenderer {
    
    // method: render
    // purpose: All Raster models should implement this method
    @Override
    public abstract void render();
    
}
