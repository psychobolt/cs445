/**
 * *************************************************************
 * file: RasterPoint.java
 * author: Michael Tran <michaeltran1@cpp.edu>
 * class: CS 445 – Computer Graphics
 *
 * assignment: Program 2 
 * date last modified: 10/21/16 19:15 AM
 *
 * purpose: A class that represents a 2D point in raster space
 *
 ***************************************************************
 */
package org.cs445.program2.raster;

import static org.lwjgl.opengl.GL11.*;

public class RasterPoint extends Raster {
    
    private int x;
    private int y;
    
    public RasterPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    // method: getX
    // purpose: Returns the x value
    public int getX() {
        return x;
    }
    
    // method: getY
    // purpose: Returns the y value
    public int getY() {
        return y;
    }

    // method: render
    // purpose: Renders the point
    @Override
    public void render() {
        glBegin(GL_POINTS);
            glVertex2i(x, y);
        glEnd();
    }

    @Override
    public RasterPoint transform() {
        return transform(this);
    }
}
