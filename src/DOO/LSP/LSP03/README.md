# Procesador de pedidos

## `ProcesadorPedido`

Calcula el importe final de un pedido aplicando el descuento comercial estandar.

**Contrato:**

- Entrada: `importe > 0`
- Salida: el importe tras aplicar un 10 % de descuento
- Garantia: el resultado siempre es menor que el importe original y nunca
  inferior al 50 % de este

```java
public double procesar(double importe) {
    return importe * 0.90;
}
```

## `ProcesadorPremium`

Variante para clientes con tarifa premium. Aplica un descuento mayor.

**Contrato:**

- Entrada: `importe > 0` (igual que la clase base)
- Salida: el importe tras aplicar un 40 % de descuento
- Garantia: el resultado es menor que el importe original; el descuento
  es mayor que el garantizado por la clase base

```java
@Override
public double procesar(double importe) {
    return importe * 0.60;
}
```

## `ProcesadorPromocion`

Variante que acepta articulos promocionales gratuitos. Abre la precondicion de la clase base: ademas de `importe > 0`, tambien acepta `importe == 0`.

**Contrato:**

- Entrada: `importe >= 0` (mas abierta que la clase base)
- Salida: 0 para pedidos gratuitos; descuento estandar para el resto
- Garantia: misma que la clase base para importes positivos

```java
@Override
public double procesar(double importe) {
    if (importe == 0) {
        return 0;
    }
    return super.procesar(importe);
}
```

## `ProcesadorDeudores`

Variante para clientes con deuda pendiente. Aplica un recargo por morosidad.

**Contrato:**

- Entrada: `importe > 0` (igual que la clase base)
- Salida: el importe ajustado con el recargo por morosidad (20 %)
- Garantia: devuelve siempre un valor numerico positivo para cualquier
  entrada valida

```java
@Override
public double procesar(double importe) {
    return importe * 1.20;
}
```

## `ProcesadorMayorista`

Variante para pedidos al por mayor. Exige un minimo de 75 EUR por pedido. Pedidos inferiores al minimo se descartan silenciosamente (devuelve 0).

**Contrato:**

- Entrada: `importe >= 75` (mas cerrada que la clase base)
- Salida: descuento estandar para pedidos validos; 0 para pedidos por debajo
  del minimo
- Garantia: para pedidos >= 75, se cumplen las garantias de la clase base.
  Para pedidos < 75, devuelve 0 (violacion: 0 < importe * 0.50)

```java
@Override
public double procesar(double importe) {
    if (importe < MINIMO) {
        return 0;
    }
    return super.procesar(importe);
}
```

## Ejecucion

El cliente `cerrarCaja()` recibe cualquier `ProcesadorPedido` y acumula el importe procesado de cada pedido. No conoce el subtipo concreto.

```java
static double cerrarCaja(ProcesadorPedido procesador, double[] pedidos) {
    double total = 0;
    for (double importe : pedidos) {
        total += procesador.procesar(importe);
    }
    return total;
}
```

Con los pedidos `[100, 200, 50, 300]` (suma bruta: 650 EUR):

```
Cierre de caja

Base:       585 EUR
Premium:    390 EUR
Deudores:   780 EUR
Mayorista:  540 EUR
Promocion:  585 EUR

Caja cerrada. Buen dia.
```

```
Auditoria

Suma bruta: 650 EUR

  [Premium]   OK  - post mas cerrada: garantiza mas descuento que la base
  [Promocion] OK  - pre  mas abierta: acepta importes que la base rechazaria
  [Deudores]  MAL - post abierta:     devuelve fuera del rango garantizado
               120 EUR > 100 EUR
               240 EUR > 200 EUR
               60 EUR > 50 EUR
               360 EUR > 300 EUR
  [Mayorista] MAL - pre  cerrada:     rechaza pedidos que la base aceptaria
               0 EUR < 25 EUR (50% de 50)
```

`cerrarCaja()` no lanza ninguna excepcion con ningun procesador. Compila, ejecuta y devuelve numeros. Los errores solo aparecen cuando el Auditor verifica pedido a pedido contra las garantias del contrato base.

El patron es visible en la salida: los casos correctos abren la precondicion o cierran la postcondicion; los incorrectos hacen lo contrario.

- `Deudores` abre la postcondicion: devuelve fuera del rango garantizado (por encima del importe original).
- `Mayorista` cierra la precondicion: rechaza pedidos que la base aceptaria y devuelve 0, rompiendo la garantia del 50 %.

Ambos fallan de forma silenciosa. Eso es exactamente lo que prohibe el Principio de Sustitucion de Liskov: los subtipos pueden cambiar el comportamiento interno, pero no pueden traicionar las garantias del contrato de la clase base.

## Cuadrante LSP

| | Pre abierta o igual (acepta igual o mas) | Pre cerrada (acepta menos) |
|---|---|---|
| **Post cerrada o igual** (garantiza igual o mas) | Premium, Promocion | - |
| **Post abierta** (garantiza menos) | Deudores | Mayorista |
