package com.example.booked.models;

public interface Filterable {

    Showroom filterByUniversity( String aUniversity);

    Showroom filterByCourse( String course);

    Showroom filterByPrice( int minPrice, int maxPrice);

    Showroom filterByWord( String word); // filterBySearchBar daha iyi gibi

    // fark etmez filterByWord() de uygun

}
