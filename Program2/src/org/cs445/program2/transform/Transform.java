/**
 * *************************************************************
 * file: Transform.java
 * author: Michael Tran <michaeltran1@cpp.edu>
 * class: CS 445 – Computer Graphics
 *
 * assignment: Program 2 
 * date last modified: 10/17/16 2:07 PM
 *
 * purpose: Object to hold transform information
 *
 ***************************************************************
 */
package org.cs445.program2.transform;

public class Transform<T> {
    
    public enum Type {Rotate, Scale, Translate}
    
    private final Type type;
    private final T attributes;
    
    public Transform(Type type, T attributes) {
        this.type = type;
        this.attributes = attributes;
    }
    
    // method: getType
    // purpose: Returns the type of the transform (rotate, scale, translate)
    public Type getType() {
        return type;
    }
    
    // method: getAttributes
    // purpose: Returns the attributes of the transform represented in a vector
    public T getAttributes() {
        return attributes;
    }
}
