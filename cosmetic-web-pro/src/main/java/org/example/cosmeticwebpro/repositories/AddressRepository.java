package org.example.cosmeticwebpro.repositories;

import java.util.List;
import org.example.cosmeticwebpro.domains.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AddressRepository
    extends JpaRepository<Address, Long>, JpaSpecificationExecutor<Long> {
  List<Address> findAddressByUserId(Long userId);
}
