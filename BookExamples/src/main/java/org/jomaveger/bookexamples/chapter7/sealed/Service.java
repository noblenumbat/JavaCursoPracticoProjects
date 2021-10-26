package org.jomaveger.bookexamples.chapter7.sealed;

public sealed interface Service permits Car, Truck {

    int getMaxServiceIntervalInMonths();

    default int getMaxDistanceBetweenServicesInKilometers() {
    	return 100000;
    }
}