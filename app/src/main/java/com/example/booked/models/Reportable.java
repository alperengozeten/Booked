package com.example.booked.models;

public interface Reportable {

    /**This method reports a object.
     * @param description
     * @param catagory
     * @param owner
     * */
    void report( String description, int catagory, User owner);
}
