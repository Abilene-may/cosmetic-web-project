package org.example.cosmeticwebpro.services;

import org.example.cosmeticwebpro.exceptions.CosmeticException;
import org.example.cosmeticwebpro.models.DisplayStatisticalDTO;

public interface StatisticalService {

  DisplayStatisticalDTO displayStatistical(Integer year) throws CosmeticException;
}
