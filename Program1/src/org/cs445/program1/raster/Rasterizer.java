/**
 * *************************************************************
 * file: Rasterizer.java
 * author: Michael Tran <michaeltran1@cpp.edu>
 * class: CS 445 – Computer Graphics
 *
 * assignment: Program 1 
 * date last modified: 9/30/16 1:40 PM
 *
 * purpose: A singleton class that provides a reference of raster models
 *
 ***************************************************************
 */
package org.cs445.program1.raster;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import org.cs445.program1.io.file.GeometryFileReader;
import org.cs445.program1.model.ModelRenderer;

public class Rasterizer {
    
    private static Rasterizer instance;
    
    private final List<ModelRenderer> rasterModels;
    
    private Rasterizer() {
        rasterModels = new LinkedList<>();
    }
    
    // method: getInstance
    // purpose: Get the Rasterizer instance
    public static synchronized Rasterizer getInstance() {
        if (instance == null) {
            instance = new Rasterizer();
        }
        return instance;
    }
    
    // method: readGraphicsFromFile
    // purpose: Read Raster Graphics from a file
    public void readGraphicsFromFile(String filepath) {
        rasterModels.addAll(new GeometryFileReader(filepath).readShapes());
    }
    
    // method: getRasterModels
    // purpose: Returns a list of raster models
    public Collection<ModelRenderer> getRasterModels() {
        return rasterModels;
    }
    
}
