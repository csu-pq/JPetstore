package org.csu.mypetstore.persistence;

import org.csu.mypetstore.domain.Supplier;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierMapper {
    Supplier getSupplierByUsername(String username);

    Supplier getSupplierByUsernameAndPassword(Supplier supplier);

    void insertSupplier(Supplier supplier);

    void insertSignon(Supplier supplier);

    void updateSupplier(Supplier supplier);

    void updateSignon(Supplier supplier);
}