/**
 * *************************************************************
 * file: GeometryFileReader.java
 * author: Michael Tran <michaeltran1@cpp.edu>
 * class: CS 445 – Computer Graphics
 *
 * assignment: Program 1 
 * date last modified: 9/30/16 5:08PM
 *
 * purpose: Reads a file containing a list of geometry.
 *
 ***************************************************************
 */
package org.cs445.program1.io.file;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;
import org.cs445.program1.raster.*;

public class GeometryFileReader {
    
    private final String filepath;
    
    public GeometryFileReader(String filepath) {
        this.filepath = filepath;
    }
    
    // method: readShapes
    // purpose: Read and returns all raster shapes from the file
    public Collection<Raster> readShapes() {
        List<Raster> shapes = new LinkedList<>();
        try (Stream<String> stream = Files.lines(Paths.get(filepath))) {
            stream.forEach(line -> {
                String[] tokens = line.split("\\s+");
                String token = tokens[0];
                if ("l".equals(token)) {
                    String[] endpoint = tokens[1].split(",");
                    RasterPoint start = new RasterPoint(
                        Integer.parseInt(endpoint[0]), 
                        Integer.parseInt(endpoint[1]));
                    endpoint = tokens[2].split(",");
                    RasterPoint end = new RasterPoint(
                        Integer.parseInt(endpoint[0]), 
                        Integer.parseInt(endpoint[1]));                   
                    shapes.add(new RasterLine(start, end));
                } else if ("c".equals(token)) {
                    String[] point = tokens[1].split(",");
                    RasterPoint center = new RasterPoint(
                        Integer.parseInt(point[0]),
                        Integer.parseInt(point[1]));
                    int radius = Math.abs(Integer.parseInt(tokens[2]));
                    shapes.add(new RasterCircle(center, radius));
                } else if ("e".equals(token)) {
                    String[] point = tokens[1].split(",");
                    RasterPoint center = new RasterPoint(
                        Integer.parseInt(point[0]),
                        Integer.parseInt(point[1]));
                    String[] radii = tokens[2].split(",");
                    int radiusX = Math.abs(Integer.parseInt(radii[0]));
                    int radiusY = Math.abs(Integer.parseInt(radii[1]));
                    shapes.add(new RasterEllipse(center, radiusX, radiusY));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return shapes;
    }
    
}
