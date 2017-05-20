-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2017-05-11 02:50:33.274

-- tables
-- Table: empleado
CREATE TABLE "empleado" (
    "tipoempleado" varchar(10)  NOT NULL,
    "idempleado" int  NOT NULL,
    "cargo" varchar(15)  NOT NULL,
    "horario" varchar(15)  NOT NULL,
    "foto" text  NOT NULL,
    "direccion" varchar(20)  NOT NULL,
    "cedula" varchar(10)  NOT NULL,
    "telefono" varchar(20)  NOT NULL,
    "correo" varchar(15)  NOT NULL,
    "pin" varchar(20)  NOT NULL,
    CONSTRAINT "empleado_pk" PRIMARY KEY ("idempleado")
);

COMMENT ON TABLE "empleado" IS 'Tabla Empleado';

-- Table: factura
CREATE TABLE "factura" (
    "horapago" time  NOT NULL,
    "facturaid" int  NOT NULL,
    "tipopago" varchar(10)  NOT NULL,
    "fechafactura" date  NOT NULL,
    "totalfactura" int  NOT NULL,
    "cedulacliente" int  NOT NULL,
    "pedido_idpedido" int  NOT NULL,
    "iva" int  NOT NULL,
    CONSTRAINT "factura_pk" PRIMARY KEY ("facturaid")
);

-- Table: mesa
CREATE TABLE "mesa" (
    "numeromesa" int  NOT NULL,
    "puestos" int  NOT NULL,
    CONSTRAINT "mesa_pk" PRIMARY KEY ("numeromesa")
);

-- Table: pago
CREATE TABLE "pago" (
    "id_pago" int  NOT NULL,
    "nombre_pago" varchar(50)  NOT NULL,
    "valor_pago" int  NOT NULL,
    CONSTRAINT "pago_pk" PRIMARY KEY ("valor_pago")
);

-- Table: pago_factura
CREATE TABLE "pago_factura" (
    "pago_valor_pago" int  NOT NULL,
    "factura_facturaid" int  NOT NULL
);

-- Table: pedido
CREATE TABLE "pedido" (
    "tipopedido" varchar(10)  NOT NULL,
    "idpedido" int  NOT NULL,
    "horapedido" time  NOT NULL,
    "horaentrega" time  NOT NULL,
    "productoid" int  NOT NULL,
    "mesa_numeromesa" int  NOT NULL,
    "empleado_idempleado" int  NOT NULL,
    CONSTRAINT "pedido_pk" PRIMARY KEY ("idpedido")
);

-- Table: producto
CREATE TABLE "producto" (
    "nombreproducto" varchar(10)  NOT NULL,
    "foto" varchar  NOT NULL,
    "precio" int  NOT NULL,
    "descripcion" varchar(40)  NOT NULL,
    "categoria" varchar(15)  NOT NULL,
    "productoid" int  NOT NULL,
    CONSTRAINT "producto_pk" PRIMARY KEY ("productoid")
);

-- Table: producto_pedido
CREATE TABLE "producto_pedido" (
    "producto_productoid" int  NOT NULL,
    "pedido_idpedido" int  NOT NULL,
    "cantidad" int  NOT NULL,
    CONSTRAINT "producto_pedido_pk" PRIMARY KEY ("producto_productoid","pedido_idpedido")
);

-- foreign keys
-- Reference: Producto_Pedido_pedido (table: producto_pedido)
ALTER TABLE "producto_pedido" ADD CONSTRAINT "producto_pedido_pedido"
    FOREIGN KEY ("pedido_idpedido")
    REFERENCES "pedido" ("idpedido")  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: Producto_Pedido_producto (table: producto_pedido)
ALTER TABLE "producto_pedido" ADD CONSTRAINT "producto_pedido_producto"
    FOREIGN KEY ("producto_productoid")
    REFERENCES "producto" ("productoid")  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: factura_pedido (table: factura)
ALTER TABLE "factura" ADD CONSTRAINT "factura_pedido"
    FOREIGN KEY ("pedido_idpedido")
    REFERENCES "pedido" ("idpedido")  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: pago_factura_factura (table: pago_factura)
ALTER TABLE "pago_factura" ADD CONSTRAINT "pago_factura_factura"
    FOREIGN KEY ("factura_facturaid")
    REFERENCES "factura" ("facturaid")  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: pago_factura_pago (table: pago_factura)
ALTER TABLE "pago_factura" ADD CONSTRAINT "pago_factura_pago"
    FOREIGN KEY ("pago_valor_pago")
    REFERENCES "pago" ("valor_pago")  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: pedido_empleado (table: pedido)
ALTER TABLE "pedido" ADD CONSTRAINT "pedido_empleado"
    FOREIGN KEY ("empleado_idempleado")
    REFERENCES "empleado" ("idempleado")  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: pedido_mesa (table: pedido)
ALTER TABLE "pedido" ADD CONSTRAINT "pedido_mesa"
    FOREIGN KEY ("mesa_numeromesa")
    REFERENCES "mesa" ("numeromesa")  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- End of file.

