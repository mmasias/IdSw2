# OCP06 - el contrato

El sistema está en producción. Cinco tipos de alumno, cuatro evaluadores, cero `instanceof`. El cliente está satisfecho.

Y entonces nos piden algo inesperado: el contrato formal de `Alumno`.

## El encargo

> "Necesitamos la especificación completa del sistema. No el código - el contrato. Qué garantiza cada operación. Qué asume. Qué promete."

Es una petición razonable. Cualquier equipo que vaya a extender el sistema necesita saber qué puede dar por sentado.

El método central del diseño es `solicitarBeca(EvaluadorBecas evaluador)`, presente en `Alumno` y sobreescrito en cada subclase. Su contrato:

## Contrato de `Alumno.solicitarBeca(EvaluadorBecas evaluador)`

**Precondiciones** (lo que el método asume antes de ejecutarse):

- `evaluador` no es nulo
- `evaluador` implementa correctamente la interfaz `EvaluadorBecas`

**Postcondiciones** (lo que el método garantiza al terminar):

- Se ha invocado exactamente una vez algún `evaluador.evaluar(...)` con el tipo concreto del alumno receptor
- El objeto `evaluador` ha recibido toda la información necesaria para tomar su decisión
- El estado del alumno no ha sido modificado

**Invariante de la jerarquía** (lo que vale para cualquier subclase de `Alumno`):

- Cualquier `Alumno` - independientemente de su subtipo concreto - puede ser pasado a `procesarSolicitudBeca(Alumno, EvaluadorBecas)` y el sistema funciona sin condiciones adicionales

Este invariante no es una convención. Es la condición bajo la cual `Universidad` puede depender únicamente de `Alumno` sin saber nada de sus subtipos.

Este mecanismo - una jerarquía que delega la operación en otra mediante doble despacho - tiene nombre: **Visitor**. El contrato que acabamos de definir es exactamente lo que el patrón exige a sus elementos. Artículo propio pendiente.

> Sigue en [OCP07](../OCP07/README.md)
