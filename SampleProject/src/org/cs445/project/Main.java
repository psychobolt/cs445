/***************************************************************
* file: Main.java
* author: 
* class: CS 445 – Computer Graphics
*
* assignment: 
* date last modified: 
*
* purpose: 
*
****************************************************************/ 
package org.cs445.project;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;

public class Main {

    public static final int winWidth = 640;
    public static final int winHeight = 480;
    
    // method: main
    // purpose: Initialize and calls the start
    public static void main(String[] args) {
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
        Display.setDisplayMode(new DisplayMode(winWidth, winHeight));
        Display.setTitle("New LWJGL Window");
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
        glOrtho(0, winWidth, 0, winHeight, 1, -1);
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
                
                // Render two yellow points
                glColor3f(1.0f, 1.0f, 0.0f);
                glPointSize(10);
                
                glBegin(GL_POINTS);
                    glVertex2f(350.0f, 150.0f);
                    glVertex2f(50.0f, 50.0f);
                glEnd();
                
                Display.update();
                Display.sync(60);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Display.destroy();
    }
}
