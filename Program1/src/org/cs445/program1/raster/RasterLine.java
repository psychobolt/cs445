/**
 * *************************************************************
 * file: RasterLine.java
 * author: Michael Tran <michaeltran1@cpp.edu>
 * class: CS 445 – Computer Graphics
 *
 * assignment: Program 1 
 * date last modified: 9/30/16 3:51 PM
 *
 * purpose: A class that represents a 2D raster line 
 *
 ***************************************************************
 */
package org.cs445.program1.raster;

import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.util.vector.Vector3f;

public class RasterLine extends RasterShape {
    
    private final RasterPoint start;
    private final RasterPoint end;
    
    private final int dx;
    private final int dy;
    
    private final int incrementRight;
    private final int incrementUpRight;
    
    public RasterLine(RasterPoint start, RasterPoint end) {
        this.start = start;
        this.end = end;
        outlineColor = new Vector3f(1.0f, 0.0f, 0.0f);
        
        dx = end.getX() - start.getX();
        dy = end.getY() - start.getY();
        incrementRight = 2 * dy;
        incrementUpRight = 2 * (dy - dx);
    }
    
    // method: getStart
    // purpose: Returns the starting point of the line
    public RasterPoint getStart() {
        return start;
    }
    
    // method: getEnd
    // purpose: Returns the ending point of the line
    public RasterPoint getEnd() {
        return end;
    }

    // method: render
    // purpose: Renders the line from left to right based on Midpoint algorithm
    // Only lines with -1 < slope < 1 are handled
    @Override
    public void render() {
        glColor3f(outlineColor.x, outlineColor.y, outlineColor.z);
        int x = start.getX();
        int y = start.getY();
        int d = 2 * dy - dx;
        start.render();
        while (x < end.getX()) {
            x += 1;
            if (d > 0) {
                d += incrementUpRight;
                y += 1;
            } else {
                d += incrementRight;
            }
            new RasterPoint(x, y).render();
        }
    }
    
}
