/**
 * *************************************************************
 * file: RasterPolygon.java
 * author: Michael Tran <michaeltran1@cpp.edu>
 * class: CS 445 – Computer Graphics
 *
 * assignment: Program 2 
 * date last modified: 10/17/16 3:42 PM
 *
 * purpose: A class that represents a 2D polygon
 *
 ***************************************************************
 */
package org.cs445.program2.raster;

import java.util.Collection;
import org.cs445.program2.transform.Transform;
import org.lwjgl.util.vector.Vector3f;

public class RasterPolygon extends RasterShape {

    private Collection<RasterPoint> vertices;
    private Collection<Transform<?>> transforms;
    
    public RasterPolygon(
            Vector3f fillColor, 
            Collection<RasterPoint> vertices, 
            Collection<Transform<?>> transforms) {
        this.fillColor = fillColor;
        this.vertices = vertices;
        this.transforms = transforms;
    }
    
    // method: render
    // purpose: Renders the polygon
    @Override
    public void render() {
        
    }
    
}
