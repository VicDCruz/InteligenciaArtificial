/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package damas;

/**
 *
 * @author daniel
 */
public class LugarOcupadoException extends RuntimeException {

    /**
     * Creates a new instance of <code>LugarOcupadoException</code> without
     * detail message.
     */
    public LugarOcupadoException() {
    }

    /**
     * Constructs an instance of <code>LugarOcupadoException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public LugarOcupadoException(String msg) {
        super(msg);
    }
}
