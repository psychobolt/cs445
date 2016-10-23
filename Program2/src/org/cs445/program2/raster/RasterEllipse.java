/**
 * *************************************************************
 * file: RasterEllipse.java
 * author: Michael Tran <michaeltran1@cpp.edu>
 * class: CS 445 – Computer Graphics
 *
 * assignment: Program 2 
 * date last modified: 10/21/16 10:12 AM
 *
 * purpose: A class that represents a 2D raster ellipse
 *
 ***************************************************************
 */
package org.cs445.program2.raster;

import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.util.vector.Vector3f;

public class RasterEllipse extends Raster {
    
    private RasterPoint center;
    private final float radiusX;
    private final float radiusY;
    private static final double CIRCUMFERENCE = 2 * Math.PI;
    
    public RasterEllipse(RasterPoint center, float radiusX, float radiusY) {
        this.center = center;
        this.radiusX = radiusX;
        this.radiusY = radiusY;
        outlineColor = new Vector3f(0.0f, 1.0f, 0.0f);
    }

    // method: render
    // purpse: Renders the ellipse
    @Override
    public void render() {
        glColor3f(outlineColor.x, outlineColor.y, outlineColor.z);
        glBegin(GL_POINTS);
        for (double theta = 0; theta <= CIRCUMFERENCE; theta += 0.001) {
            double x = radiusX * Math.cos(theta);
            double y = radiusY * Math.sin(theta);
            glVertex2d(center.getX() + x, center.getY() + y);
        }
        glEnd();
    }

    // method: transform
    // purpose: Apply any transformations on the center
    @Override
    public RasterEllipse transform() {
        return new RasterEllipse(transform(center), radiusX, radiusY);
    }
    
}
