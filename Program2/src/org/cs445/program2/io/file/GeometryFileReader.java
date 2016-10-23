/**
 * *************************************************************
 * file: GeometryFileReader.java
 * author: Michael Tran <michaeltran1@cpp.edu>
 * class: CS 445 – Computer Graphics
 *
 * assignment: Program 2 
 * date last modified: 10/23/16 9:44AM
 *
 * purpose: Reads a file containing a list of geometry.
 *
 ***************************************************************
 */
package org.cs445.program2.io.file;

import java.io.File;
import org.cs445.program2.raster.RasterEllipse;
import org.cs445.program2.raster.RasterCircle;
import org.cs445.program2.raster.Raster;
import org.cs445.program2.raster.RasterLine;
import org.cs445.program2.raster.RasterPoint;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.cs445.program2.raster.RasterPolygon;
import org.lwjgl.util.vector.Matrix3f;
import org.lwjgl.util.vector.Vector3f;

public class GeometryFileReader {
    
    private final Logger LOGGER = 
            Logger.getLogger(GeometryFileReader.class.getSimpleName());
    private final String filepath;
    
    public GeometryFileReader(String filepath) {
        this.filepath = filepath;
    }
    
    // method: readShapes
    // purpose: Read and returns all raster shapes from the file
    public Collection<Raster> readShapes() {
        List<Raster> shapes = new LinkedList<>();
        try (Scanner scanner = new Scanner(new File(filepath))) {
            while(scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] tokens = line.split("\\s+");
                String token = tokens[0];
                while (true) {
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
                        break;
                    } else if ("c".equals(token)) {
                        String[] point = tokens[1].split(",");
                        RasterPoint center = new RasterPoint(
                            Integer.parseInt(point[0]),
                            Integer.parseInt(point[1]));
                        int radius = Math.abs(Integer.parseInt(tokens[2]));
                        shapes.add(new RasterCircle(center, radius));
                        break;
                    } else if ("e".equals(token)) {
                        String[] point = tokens[1].split(",");
                        RasterPoint center = new RasterPoint(
                            Integer.parseInt(point[0]),
                            Integer.parseInt(point[1]));
                        String[] radii = tokens[2].split(",");
                        int radiusX = Math.abs(Integer.parseInt(radii[0]));
                        int radiusY = Math.abs(Integer.parseInt(radii[1]));
                        shapes.add(new RasterEllipse(center, radiusX, radiusY));
                        break;
                    } else if ("P".equals(token)) {
                        float r = Float.parseFloat(tokens[1]);
                        float g = Float.parseFloat(tokens[2]);
                        float b = Float.parseFloat(tokens[3]);
                        Vector3f color = new Vector3f(r, g, b);
                        List<RasterPoint> vertices = new LinkedList<>();
                        List<Matrix3f> transforms = new LinkedList<>();
                        // get vertices
                        while (scanner.hasNext()) {
                            line = scanner.nextLine();
                            if (line.startsWith("//")) {
                                continue;
                            }
                            tokens = line.split("\\s+");
                            if ("T".equals(tokens[0])) {
                                // get transforms
                                while (scanner.hasNext()) {
                                    line = scanner.nextLine();
                                    tokens = line.split("\\s+");
                                    Matrix3f transform = new Matrix3f();
                                    if ("r".equals(tokens[0])) {
                                        float delta = Float.parseFloat(tokens[1]);
                                        float x = Float.parseFloat(tokens[2]);
                                        float y = Float.parseFloat(tokens[3]);
                                        float sin = (float) Math.sin(delta);
                                        float cos = (float) Math.cos(delta);
                                        transform.m00 = cos;
                                        transform.m01 = sin;
                                        transform.m10 = -sin;
                                        transform.m11 = cos;
                                        transform.m20 = x * (1 - cos) + y * sin;
                                        transform.m21 = y * (1 - cos) + x * sin;
                                    } else if ("s".equals(tokens[0])) {
                                        transform.m00 = Float.parseFloat(tokens[1]);
                                        transform.m11 = Float.parseFloat(tokens[2]);
                                        transform.m20 = (1 - transform.m00) * Float.parseFloat(tokens[3]);
                                        transform.m21 = (1 - transform.m11) * Float.parseFloat(tokens[4]);
                                    } else if ("t".equals(tokens[0])) {
                                        transform.m20 = Float.parseFloat(tokens[1]);
                                        transform.m21 = Float.parseFloat(tokens[2]);
                                    } else if (tokens[0].startsWith("//")) {
                                        continue;
                                    } else {
                                        break;
                                    }
                                    transforms.add(transform);
                                }
                                token = tokens[0];
                                break;
                            }
                            try {
                                vertices.add(new RasterPoint(
                                    Integer.parseInt(tokens[0]), 
                                    Integer.parseInt(tokens[1])
                                ));
                            } catch (NumberFormatException e) {
                                token = tokens[0];
                                break;
                            }
                        }
                        RasterPolygon polygon = new RasterPolygon(color, vertices);
                        polygon.getTransforms().addAll(transforms);
                        shapes.add(polygon);
                    } else {
                        break;
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Invalid file format", e);
        }
        return shapes;
    }
    
}
