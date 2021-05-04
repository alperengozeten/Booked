package com.example.booked.models;

/**
 * This is the interface Filterable. It allows the user to make filtered searches on Showroom
 */
public interface Filterable {

    /**This filter posts according to their university property.
     * @param aUniversity
     * @return
     */
    Showroom filterByUniversity( String aUniversity);

    /**This filter posts according to their course property.
     * @param course
     * @return
     */
    Showroom filterByCourse( String course);

    /**This filter posts according to its price and boundaries given.
     * @param minPrice
     * @param maxPrice
     * @return
     */
    Showroom filterByPrice( int minPrice, int maxPrice);

    /**This filter posts according to given word
     * @param word
     * @return
     */
    Showroom filterByWord( String word);

}
