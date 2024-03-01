package com.cashier.system.skecobe.validations;

import com.cashier.system.skecobe.enums.TourCode;

import java.util.ArrayList;
import java.util.List;

public class Ttest {
    public static void main(String[] args) {
        TourCode[] tourCodes = TourCode.values();
        List<String> tourCodeList = new ArrayList<>();

        String searchTourCode = "BOTH";

        for (TourCode tourCode : tourCodes) {
            tourCodeList.add(tourCode.name());
            if (tourCode.name().equals(searchTourCode)) {
                System.out.println("Valid tour code");
            }
        }

//        if (!tourCodeList.contains(searchTourCode)) {
//            System.out.println("Invalid tour code");
//        } else {
//            System.out.println("Valid tour code");
//        }

    }
}
