package com.tienda.cincomenos.domain.producto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository <Producto, Long> {

    boolean existsByCodigoDeBarras(String codigoBarras);

    Page<Producto> findByProductoActivoTrue(Pageable paginacion);

}
