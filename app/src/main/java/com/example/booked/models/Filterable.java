package com.example.booked.models;

/**
 * This is the interface Filterable. It allows the user to make filtered searches on Showroom
 */
public interface Filterable {

    /**
     *
     * @param aUniversity
     * @return
     */
    Showroom filterByUniversity( String aUniversity);

    Showroom filterByCourse( String course);

    Showroom filterByPrice( int minPrice, int maxPrice);

    Showroom filterByWord( String word); // filterBySearchBar daha iyi gibi

    // fark etmez filterByWord() de uygun

}
