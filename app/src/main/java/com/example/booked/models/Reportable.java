package com.example.booked.models;

/**
 * This interface allows the object to be reportable by user.
 */
public interface Reportable {

    /**This method reports a object.
     * @param description
     * @param catagory
     * @param owner
     * */
    void report( String description, int catagory, User owner);
}
