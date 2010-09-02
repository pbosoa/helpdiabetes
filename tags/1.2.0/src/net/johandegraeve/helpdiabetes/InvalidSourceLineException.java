/*  
 *  Copyright (C) 2009  Johan Degraeve
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/gpl.txt>.
 *    
 *  Please contact Johan Degraeve at johan.degraeve@johandegraeve.net if you need
 *  additional information or have any questions.
 */
package net.johandegraeve.helpdiabetes;

/**
 * InvalidSourceLine.java) Version 1.0 6 nov 2009 
 *
 * copyright information
 */

/**
 * SourceLine is a typical character sequence or string read from a foodfile; This exception
 * can be used when the SourceLine structure or content is invalid.
 *
 * @version 1.0
 * @author Johan Degraeve
 *
 */
public class InvalidSourceLineException extends Exception {
    
    /**
     * defines the position in the string or character sequence where the error is located
     */
    private int position;

    /**
     * don't know what this is for
     */
    private static final long serialVersionUID = 2303354976115571872L;

    /**
     * This constructor should not be used, hence private
     */
    @SuppressWarnings("unused")
    private InvalidSourceLineException() {
		
    }
    
    /**
     * the sourceLine that generated the error
     */
    private byte[] sourceLine;
    
    /**
     * Constructs a new exception with the specified detail message.
     * @param msg String that details the message, saved for later retrieval by the {@link Throwable#getMessage()} method.
     * @param position specifies where in the string or character sequence the error has occurred
     * @param sourceLine the sourceLine that caused the Exception
     */
    public InvalidSourceLineException(String msg, int position, byte[] sourceLine) {
	super(msg);
	this.position = position;
	this.sourceLine = (new String(sourceLine)).getBytes();
    }

    /**
     * @return the position
     */
    public int getPosition() {
        return position;
    }
    
    /**
     * @return the sourceLine
     */
    public String getSourceLine() {
	return new String(sourceLine);
    }
}
