/**
 * *************************************************************
 * file: RasterPolygon.java
 * author: Michael Tran <michaeltran1@cpp.edu>
 * class: CS 445 – Computer Graphics
 *
 * assignment: Program 2 
 * date last modified: 10/19/16 9:38 AM
 *
 * purpose: A class that represents a 2D polygon
 *
 ***************************************************************
 */
package org.cs445.program2.raster;

import static org.lwjgl.opengl.GL11.*;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import org.cs445.program2.transform.Transform;
import org.lwjgl.util.vector.Vector3f;

public class RasterPolygon extends RasterShape {

    private final List<RasterPoint> vertices;
    private final Collection<Transform<?>> transforms;
    private final List<Edge> allEdges;
    private final Set<Edge> globalEdges;
    
    public RasterPolygon(
            Vector3f fillColor, 
            List<RasterPoint> vertices, 
            Collection<Transform<?>> transforms) {
        this.fillColor = fillColor;
        this.vertices = vertices;
        this.transforms = transforms;
        allEdges = new LinkedList<>();
        for (int i = 0; i < vertices.size() - 1; i++) {
            addEdge(vertices.get(i), vertices.get(i + 1));
        }
        addEdge(vertices.get(vertices.size() - 1), vertices.get(0));
        globalEdges = new TreeSet<>(allEdges.stream()
            .filter(edge -> edge.slope != 0)
            .collect(Collectors.toList()));
    }
    
    // method: addEdge
    // purpose: Adds an edge to the all edges table
    private void addEdge(RasterPoint start, RasterPoint end) {
        float yMin;
        float yMax;
        float xVal;
        float dx = end.getX() - start.getX();
        float dy = end.getY() - start.getY();
        float slope = dx == 0 ? Float.POSITIVE_INFINITY : dy / dx;
        if (start.getY() == end.getY()) {
            yMin = yMax = start.getY();
            xVal = Math.min(start.getX(), end.getX());
        } else if (start.getY() < end.getY()) {
            yMin = start.getY();
            yMax = end.getY();
            xVal = start.getX();
        } else {
            yMin = end.getY();
            yMax = start.getY();
            xVal = end.getX();
        }
        allEdges.add(new Edge(yMin, yMax, xVal, slope, false));
    }
    
    // method: render
    // purpose: Renders the polygon using the scanline fill algorithm
    @Override
    public void render() {
        TreeSet<Edge> globalEdges = new TreeSet<>(this.globalEdges);
        Set<Edge> activeEdges = new TreeSet<>();
        float scanLine = globalEdges.first().yMin;
        float yMax = globalEdges.last().yMax;
        glColor3f(fillColor.x, fillColor.y, fillColor.z);
        glBegin(GL_POINTS);
        while(scanLine < yMax){
            // remove any active edges where Y-Max equals scan-line
            Iterator<Edge> iter = activeEdges.iterator();
            while(iter.hasNext()) {
                Edge edge = iter.next();
                if (edge.yMax == scanLine) {
                    iter.remove();
                }
            }
            // store any edges where Y-Min equals scan-line
            iter = globalEdges.iterator();
            while(iter.hasNext()) {
                Edge edge = iter.next();
                if (edge.yMin == scanLine) {
                    iter.remove();
                    activeEdges.add(new Edge(
                        edge.yMin, edge.yMax, edge.xVal, edge.slope, true
                    ));
                }
            }
            // fill based on edges' x-val
            float x = 0.0f; // = Float.POSITIVE_INFINITY;
            short parity = 0; // even if 0, else odd
            for (Edge activeEdge : activeEdges) {
                if (parity == 1) { // render pixel if parity is odd
                    if (x == activeEdge.xVal) { // special parity case
                        glVertex2f(x, scanLine);
                    } else {
                        for (; x < activeEdge.xVal; x++) {
                            glVertex2f(x, scanLine);
                        }
                    }
                    parity = 0;
                } else if (parity == 0) {
                    x = activeEdge.xVal;
                    parity = 1;
                }
            }
            // increment edges X-val
            activeEdges = activeEdges.stream().map(edge -> {
                return new Edge(
                    edge.yMin, edge.yMax, edge.xVal + edge.mInverse, edge.slope, true
                );
            }).collect(Collectors.toSet());
            scanLine++;
        }
        glEnd();
    }
    
    private static class Edge implements Comparable<Edge> {
        
        public final float yMin;
        public final float yMax;
        public final float xVal;
        public final float slope;
        public final float mInverse;
        
        private final boolean active;
        
        public Edge(float yMin, float yMax, float xVal, float slope, boolean active) {
            this.yMin = yMin;
            this.yMax = yMax;
            this.xVal = xVal;
            this.slope = slope;
            this.active = active;
            mInverse = slope == Float.POSITIVE_INFINITY ? 0 : 1 / slope;
        }
        
        // method: compareTo
        // purpose: Checks whether the edge is same, lesser, or greater.
        @Override
        public int compareTo(Edge e) {
            if (this == e) {
                return 0;
            }
            if (active && xVal < e.xVal) {
                return -1;
            } else if (yMin < e.yMin) {
                return -1;
            } else if (yMin == e.yMin) {
                if (xVal < e.xVal) {
                    return -1;
                } else if (xVal == e.xVal) {
                    if (yMax < e.yMax) {
                        return -1;
                    }
                }
            }
            return 1;
        }
    }
    
}
