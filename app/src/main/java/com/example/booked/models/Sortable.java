package com.example.booked.models;

public interface Sortable {


    /**
     * This method sorts a list according to prices.
     * @param isLowToHigh if it is true list will be sorted from the lowest price to the highest
     *               if it is false list will be sorted from the highest  price to the lowest
     * */
    void sortByPrice( boolean isLowToHigh);


    /**
     * This method sorts a list alphabetically.
     * @param isAToZ if it is true lis will be sorted alphabetically from a to z
     *               if it is false list will be sorted alphabetically from z to a
     * */
    void sortByLetter( boolean isAToZ);
}