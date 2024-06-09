package org.example.cosmeticwebpro.services.Impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.cosmeticwebpro.domains.Address;
import org.example.cosmeticwebpro.exceptions.CosmeticException;
import org.example.cosmeticwebpro.exceptions.ExceptionUtils;
import org.example.cosmeticwebpro.models.request.AddressReqDTO;
import org.example.cosmeticwebpro.repositories.AddressRepository;
import org.example.cosmeticwebpro.services.AddressService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
  private final AddressRepository addressRepository;

  /** create an address for user */
  @Override
  public Address createAddress(AddressReqDTO addressReqDTO) throws CosmeticException {
    // check required fields
    checkNullField(addressReqDTO.getFullName(), addressReqDTO.getPhoneNumber(),
        addressReqDTO.getProvinceName(), addressReqDTO.getDistrictName(),
        addressReqDTO.getWardName(), addressReqDTO.getUserId());
    Address address =
        Address.builder()
            .fullName(addressReqDTO.getFullName())
            .phoneNumber(addressReqDTO.getPhoneNumber())
            .provinceName(addressReqDTO.getProvinceName())
            .districtName(addressReqDTO.getDistrictName())
            .wardName(addressReqDTO.getWardName())
            .addressDetail(addressReqDTO.getAddressDetail())
            .userId(addressReqDTO.getUserId())
            .build();
    return addressRepository.save(address);
  }

  // function check required fields
  public void checkRequiredField(String item, String messageError) throws CosmeticException {
    if (item.isBlank()) {
      throw new CosmeticException(messageError, ExceptionUtils.messages.get(messageError));
    }
  }

  /*
  update an address
   */
  @Override
  public void updateAddress(Address addressReq) throws CosmeticException {
    // check the address has to exist
    var address = this.getAddressById(addressReq.getId());
    // check required fields
    checkNullField(addressReq.getFullName(), addressReq.getPhoneNumber(),
        addressReq.getProvinceName(), addressReq.getDistrictName(), addressReq.getWardName(),
        addressReq.getUserId());

  }

  private void checkNullField(String fullName, String phoneNumber, String provinceName,
      String districtName, String wardName, Long userId)
      throws CosmeticException {
    this.checkRequiredField(fullName, ExceptionUtils.ADDRESS_ERROR_1);
    this.checkRequiredField(phoneNumber, ExceptionUtils.ADDRESS_ERROR_2);
    this.checkRequiredField(provinceName, ExceptionUtils.ADDRESS_ERROR_3);
    this.checkRequiredField(districtName, ExceptionUtils.ADDRESS_ERROR_4);
    this.checkRequiredField(wardName, ExceptionUtils.ADDRESS_ERROR_5);
    if (userId == null) {
      throw new CosmeticException(
          ExceptionUtils.USER_ID_IS_NOT_EMPTY,
          ExceptionUtils.messages.get(ExceptionUtils.USER_ID_IS_NOT_EMPTY));
    }
  }

  @Override
  public List<Address> getAllAddress(Long userId) throws CosmeticException {
    return addressRepository.findAddressByUserId(userId);
  }

  @Override
  public Address getAddressById(Long addressId) throws CosmeticException {
    var address = addressRepository.findById(addressId);
    if (address.isEmpty()) {
      throw new CosmeticException(
          ExceptionUtils.ADDRESS_IS_NOT_FOUND,
          ExceptionUtils.messages.get(ExceptionUtils.ADDRESS_IS_NOT_FOUND));
    }
    return address.get();
  }

  @Override
  public void deleteAddress(Long addressId) throws CosmeticException {}
}
