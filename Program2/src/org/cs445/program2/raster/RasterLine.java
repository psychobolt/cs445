/**
 * *************************************************************
 * file: RasterLine.java
 * author: Michael Tran <michaeltran1@cpp.edu>
 * class: CS 445 – Computer Graphics
 *
 * assignment: Program 2 
 * date last modified: 10/21/16 10:10 AM
 *
 * purpose: A class that represents a 2D raster line 
 *
 ***************************************************************
 */
package org.cs445.program2.raster;

import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.util.vector.Vector3f;

public class RasterLine extends Raster {
    
    private RasterPoint start;
    private RasterPoint end;
    private final int dx;
    private final int dy;
    private final float slope;
    private final int startX;
    private final int startY;
    private final int endX;
    private final int endY;
    
    public RasterLine(RasterPoint start, RasterPoint end) {
        this.start = start;
        this.end = end;
        fillColor = new Vector3f(1.0f, 0.0f, 0.0f);
        dx = end.getX() - start.getX();
        dy = end.getY() - start.getY();
        slope = dx == 0 ? Float.POSITIVE_INFINITY : ((float) dy) / dx;
        startX = Math.min(start.getX(), end.getX());
        startY = slope >= 0 ? Math.min(start.getY(), end.getY()) :
                            Math.max(start.getY(), end.getY());
        endX = Math.max(start.getX(), end.getX());
        endY = Math.max(start.getY(), end.getY());
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

    // method: renderDiagonalLine
    // purpose: Renders the line from left to right based on Midpoint algorithm
    @Override
    public void render() {
        glColor3f(fillColor.x, fillColor.y, fillColor.z);
        start.render();
        if (slope == 0) {
            renderHorizontalLine();
        } else if (slope == Float.POSITIVE_INFINITY) {
            renderVerticalLine();
        } else {
            renderDiagonalLine();
        }
        end.render();
    }
    
    // method: renderHorizontalLine
    // purpose: Renders the horizontal line
    private void renderHorizontalLine() {
        int x = Math.min(start.getX(), end.getX());
        int y = startY;
        while (x < endX) {
            new RasterPoint(x, y).render();
            x++;
        }
    }
    
    // method: renderVerticalLine
    // purpose: Renders the vertical line
    private void renderVerticalLine() {
        int x = startX;
        int y = Math.min(start.getY(), end.getY());
        while (y < endY) {
            new RasterPoint(x, y).render();
            y++;
        }
    }
    
    // method: renderDiagonalLine
    // purpose: Renders the line with a diagonal incline/decline slope.
    private void renderDiagonalLine() {
        //TODO optimize calculations
        boolean moveY = Math.abs(slope) > 1;
        int dx = Math.abs(this.dx);
        int dy = Math.abs(this.dy);
        int increment = moveY ? 2 * dx : 2 * dy;
        int incrementXY = moveY ? 2 * (dx - dy) : 2 * (dy - dx);
        int x = startX;
        int y = startY;
        int d = moveY ? 2 * dx - dy : 2 * dy - dx;
        int yOffset = slope > 0 ? 1 : -1;
        while (moveY ? y < endY : x < endX) {
            if (moveY) {
                y += yOffset;
            } else {
                x++;
            }
            if (d > 0) {
                d += incrementXY;
                if (moveY) {
                    x++;
                } else {
                    y += yOffset;
                }
            } else {
                d += increment;
            }
            new RasterPoint(x, y).render();
        }
    }

    @Override
    public RasterLine transform() {
        return new RasterLine(transform(start), transform(end));
    }
}
