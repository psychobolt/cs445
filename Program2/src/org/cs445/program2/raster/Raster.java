/**
 * *************************************************************
 * file: Raster.java
 * author: Michael Tran <michaeltran1@cpp.edu>
 * class: CS 445 – Computer Graphics
 *
 * assignment: Program 2 
 * date last modified: 10/21/16 10:35 PM
 *
 * purpose: A abstract class for drawable 2D raster shapes
 *
 ***************************************************************
 */
package org.cs445.program2.raster;

import java.util.LinkedList;
import java.util.List;
import org.cs445.program2.model.ModelRenderer;
import org.lwjgl.util.vector.Matrix3f;
import org.lwjgl.util.vector.Vector3f;


public abstract class Raster<T extends Raster> implements ModelRenderer {
    
    protected Vector3f outlineColor;
    
    protected Vector3f fillColor;
    
    protected final List<Matrix3f> transforms = new LinkedList<>();
    
    // method: render
    // purpose: All Raster models should implement this method
    @Override
    public abstract void render();
    
    // method: getTransforms
    // purpose: Returns a list of transformations
    public List<Matrix3f> getTransforms() {
        return transforms;
    }
    
    // method: transform
    // purpose: All Raster models should implement this method
    public abstract T transform();
    
    // method: transform
    // purpose: Transform a RasterPoint
    protected RasterPoint transform(RasterPoint point) {
        if (transforms.isEmpty()) {
            return point;
        }
        Vector3f vector = new Vector3f(point.getX(), point.getY(), 1.0f);
        Matrix3f composite = new Matrix3f();
        transforms.forEach(transform -> {
            Matrix3f.mul(transform, composite, composite);
        });
        Matrix3f.transform(composite, vector, vector);
        return new RasterPoint(Math.round(vector.x), Math.round(vector.y));
    }
    
}
