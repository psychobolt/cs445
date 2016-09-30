/***************************************************************
* file: Main.java
* author: Michael Tran <michaeltran1@cpp.edu>
* class: CS 445 – Computer Graphics
*
* assignment: Program 1
* date last modified: 9/29/2016 10:20PM
*
* purpose: 
*
****************************************************************/ 
package org.cs445.program1;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;

public class Main {

    public static final int WINDOW_WIDTH = 640;
    public static final int WINDOW_HEIGHT = 480;
    public static final String WINDOW_TITLE = "Program 1";
    
    // method: main
    // purpose: Initialize and calls the start
    public static void main(String[] args) {
        String filepath = args.length == 1 ? args[0] : new File("src/org/cs445/program1/coordinates.txt").getAbsolutePath();
        try (Stream<String> stream = Files.lines(Paths.get(filepath))) {
            stream.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        Main main = new Main();
        main.start();
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
                
                // Render shapes
                renderShapes();
                
                Display.update();
                Display.sync(60);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Display.destroy();
    }
    
    private void renderShapes() {
        //  TODO
    }
}
