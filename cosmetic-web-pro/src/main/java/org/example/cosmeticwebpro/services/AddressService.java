package org.example.cosmeticwebpro.services;

import java.util.List;
import org.example.cosmeticwebpro.domains.Address;
import org.example.cosmeticwebpro.exceptions.CosmeticException;
import org.example.cosmeticwebpro.models.request.AddressReqDTO;

public interface AddressService {
  Address createAddress(AddressReqDTO addressReqDTO) throws CosmeticException;

  void updateAddress(Address addressReq) throws CosmeticException;

  List<Address> getAllAddress(Long userId) throws CosmeticException;

  Address getAddressById(Long addressId) throws CosmeticException;

  void deleteAddress(Long addressId) throws CosmeticException;
}
