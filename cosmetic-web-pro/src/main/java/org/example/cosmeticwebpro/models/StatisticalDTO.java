package org.example.cosmeticwebpro.models;


public interface StatisticalDTO {
  Integer getTotalOrders();
  Integer getCanceledOrders();
  Integer getOrderSuccessful();
  Integer getPendingOrders();
  double getTotalRevenueOfJanuary();
  double getTotalRevenueOfFebruary();
  double getTotalRevenueOfMarch();
  double getTotalRevenueOfApril();
  double getTotalRevenueOfMay();
  double getTotalRevenueOfJune();
  double getTotalRevenueOfJuly();
  double getTotalRevenueOfAugust();
  double getTotalRevenueOfSeptember();
  double getTotalRevenueOfOctober();
  double getTotalRevenueOfNovember();
  double getTotalRevenueOfDecember();
}
