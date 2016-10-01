/***************************************************************
* file: Main.java
* author: Michael Tran <michaeltran1@cpp.edu>
* class: CS 445 – Computer Graphics
*
* assignment: Program 1
* date last modified: 9/30/2016 5:08PM
*
* purpose: This class loads any models provided, creates the main window, and 
* renders to it.
* 
* Usage e.g.,
* java -jar Program1.jar /home/user/root/workspace/geo.assets ./coordinates.txt
* Passing no arguments will load default models
*
****************************************************************/ 
package org.cs445.program1;

import java.io.File;
import org.cs445.program1.manager.ObjectManager;
import org.cs445.program1.raster.Rasterizer;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;

public class Main {

    // Public Main Window constants
    public static final int WINDOW_WIDTH = 640;
    public static final int WINDOW_HEIGHT = 480;
    public static final String WINDOW_TITLE = "Program 1";
    
    // method: main
    // purpose: Initialize and calls the start
    public static void main(String[] args) {
        processArgs(args);
        Main main = new Main();
        main.start();
    }
    
    // method: processArgs
    // purpose: Process command arguments
    public static void processArgs(String[] args) {
        if (args.length > 0) {
            for (String filepath : args) {
                ObjectManager.getInstance().readModelsFromFile(filepath);
            }
        } else {
            ObjectManager.getInstance().readModelsFromFile(
                new File("src/org/cs445/program1/coordinates.txt")
                    .getAbsolutePath());
        }
    }
    
    // method: start
    // purpose: Create a window context with graphics capabilities and render 
    // the scene
    public void start() {
        try {
            createWindow();
            initGL();
            render();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // method: createWindow
    // purpose: Creates a window with GL context
    // Default: 640 x 480, window mode
    private void createWindow() throws Exception {
        Display.setFullscreen(false);
        Display.setDisplayMode(new DisplayMode(WINDOW_WIDTH, WINDOW_HEIGHT));
        Display.setTitle(WINDOW_TITLE);
        Display.create();
    }
    
    // method: initGL
    // purpose: Initialize a OpenGL context
    private void initGL() {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        // Setup an orthographic matrix with the window's resolution size
        // Clipping distance between -1 to 1
        glOrtho(0, WINDOW_WIDTH, 0, WINDOW_HEIGHT, 1, -1);
        glMatrixMode(GL_MODELVIEW);
        glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
    }
    
    // method: render
    // purpose: Render a scene to the GL context within a render loop
    private void render() {
        while(!Display.isCloseRequested()) {
            try {
                glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
                glLoadIdentity();
                
                // render all drawable models
                Rasterizer.getInstance().getRasterModels().forEach(model -> {
                    model.render();
                });
                
                Display.update();
                Display.sync(60);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Display.destroy();
    }
}
