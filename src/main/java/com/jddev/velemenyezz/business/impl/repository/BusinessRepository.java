package com.jddev.velemenyezz.business.impl.repository;

import com.jddev.velemenyezz.business.impl.model.Business;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface BusinessRepository extends JpaRepository<Business, Long> {
    Optional<Business> findByIdAndOwnerEmail(@NonNull Long id, @NonNull String ownerEmail);

    List<Business> findByOwnerEmail(String ownerEmail);


}
