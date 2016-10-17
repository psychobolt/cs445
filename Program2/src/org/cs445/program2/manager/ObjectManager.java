/**
 * *************************************************************
 * file: ObjectManager.java
 * author: Michael Tran <michaeltran1@cpp.edu>
 * class: CS 445 – Computer Graphics
 *
 * assignment: Program 2 
 * date last modified: 9/30/16 4:12 PM
 *
 * purpose: A singleton that provides a reference to models
 *
 ***************************************************************
 */
package org.cs445.program2.manager;

import org.cs445.program2.raster.Rasterizer;

public class ObjectManager {
    
    public static ObjectManager instance;
    
    private ObjectManager() {
        
    }
    
    // method: getInstance
    // purpose: Get the ObjectManager instance
    public static synchronized ObjectManager getInstance() {
        if (instance == null) {
            instance = new ObjectManager();
        }
        return instance;
    }
    
    // method: readModelsFromFile
    // purpose: Read Models from a file
    public void readModelsFromFile(String filepath) {
        Rasterizer.getInstance().readGraphicsFromFile(filepath);
    }
}
