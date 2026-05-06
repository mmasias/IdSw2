# Diseño orientado a objetos

## ¿Por qué?

El desarrollo de software sufre frecuentemente de inefectividad:

- **Mala economía del proyecto**: incumplimiento de ámbito, tiempo y coste establecidos.
- **Calidad deficiente**: problemas de fiabilidad, usabilidad, interoperabilidad y seguridad.
- **Pobre mantenibilidad**: software viscoso (difícil de entender), rígido (difícil de cambiar), frágil (propenso a errores) e inmóvil (difícil de reutilizar).
- **Complejidad arbitraria**: sistemas innecesariamente complicados que superan la capacidad cognitiva humana.

El [diseño](../01-diseño/README.md) y el [diseño modular](../02-diseñoModular/README.md) ofrecieron una respuesta parcial: cohesión, acoplamiento y tamaño como métricas para organizar módulos. Funcionan para estructuras planas. Pero cuando los módulos son clases que se especializan mediante herencia, aparece una pregunta que el diseño modular no responde: **¿qué garantías hereda un subtipo? ¿Qué contratos está obligado a respetar?**

Una jerarquía mal construida puede parecer correcta, compilar sin errores y aun así romper el sistema en silencio cuando se extiende.

## ¿Qué?

El Diseño Orientado a Objetos organiza el software mediante:

| Abstracción y encapsulación | Polimorfismo | Herencia |
|-|-|-|
| Ocultando detalles de implementación y exponiendo solo lo esencial. | Permitiendo el enlace dinámico de operaciones a distintas implementaciones. | Facilitando la reutilización a través de la especialización y extensión. |

Sobre estos mecanismos, el diseño OO tiene dos capas:

**Lo familiar** - los principios de cohesión y acoplamiento del diseño modular, re-aplicados a jerarquías de clases. Cuatro de los cinco principios SOLID son exactamente esto. Son vocabulario útil para nombrar instancias de diseño deficiente en contexto OO; no son conceptos nuevos.

**Lo genuinamente nuevo** - la sustitución. Cuando un subtipo reemplaza a su base en un sistema en marcha, ¿qué puede salir mal? Liskov y Wing (1994) formalizaron la respuesta: un subtipo es correcto si y solo si puede sustituir a su base sin cambiar el comportamiento observable del sistema. Este es el único principio que OO aporta que no existía en diseño modular.

## ¿Para qué?

Para escribir jerarquías que se pueden extender sin romper, donde el polimorfismo funciona sin sorpresas y el compilador es un aliado, no un adversario. Y así conseguir software con:

- **Efectividad**: cumpliendo ámbito, tiempo y coste planificados.
- **Calidad técnica**: logrando fiabilidad, usabilidad, interoperabilidad y seguridad adecuadas.
- **Mantenibilidad**: creando sistemas fluidos (fáciles de entender), flexibles (fáciles de cambiar), fuertes (fáciles de probar) y reusables (fáciles de reutilizar).
- **Complejidad inherente**: limitada a la complejidad genuina del problema, no a la introducida por un diseño deficiente.

## ¿Cómo?

### SOLID como vocabulario

| Principio | Lo que dice | Equivalente en tema 02 | Artículo |
|---|---|---|---|
| SRP | Una clase, una razón para cambiar | Alta cohesión funcional | [SOLID_S.md](SOLID_S.md) |
| OCP | Abierto a extensión, cerrado a modificación | Bajo acoplamiento via abstracción | [SOLID_O.md](SOLID_O.md) |
| **LSP** | **Un subtipo debe respetar el contrato de su base** | **Sin equivalente - aportación formal propia** | [SOLID_L.md](LSP.md) |
| ISP | No dependas de lo que no usas | Cohesión de interfaces | [SOLID_I.md](SOLID_I.md) |
| DIP | Depende de abstracciones, no de concreciones | Bajo acoplamiento | [SOLID_D.md](SOLID_D.md) |

> <sub>*ISP y DIP son los más prescindibles del conjunto: reformulan a nivel de interfaz y de dependencia lo que SRP y OCP ya establecen a nivel de clase.*</sub>

### El principio de sustitución de Liskov

LSP no es cohesión ni acoplamiento rebautizados. Es la condición formal que hace posible el polimorfismo correcto: sin ella, los cuatro principios anteriores construyen sistemas que parecen bien diseñados pero se rompen en silencio cuando se extienden.

> [Desarrollo](SOLID_L.md) - [Laboratorio: fLiskov](https://github.com/mmasias/fLiskov)

### Sesión de diseño: un sistema de evaluación de becas

El arco OCP muestra en un sistema real la progresión completa:

diseño modular -> sus límites -> Visitor como solución -> las limitaciones del Visitor -> Liskov como diagnóstico -> composición como resolución

> [src/DOO/OCP/](../../src/DOO/OCP/README.md)

### Técnicas

| Técnica | Propósito | Artículo |
|---|---|---|
| Visitor / doble despacho | Polimorfismo sin instanceof | [dobleDespacho.md](dobleDespacho.md) |
| Composición > herencia | Extensión sin violar contratos | [herenciaComposicion.md](herenciaComposicion.md) |
| Inyección de dependencias | Desacoplar módulos de sus dependencias concretas | [SOLID_D.md](SOLID_D.md) |
| Patrón método plantilla | Definir el esqueleto de un algoritmo en la base, diferir los detalles a las subclases | [SOLID_O.md](SOLID_O.md) |
| Ley de Demeter | Acotar el grafo de dependencias | [demeter.md](demeter.md) |

### SOLID en perspectiva

SOLID no son cinco principios independientes con igual peso formal. Son cinco proyecciones de los mismos principios de diseño modular, aplicados a jerarquías de clases. LSP es la excepción: tiene fundamento matemático propio y verifica una propiedad que los demás no cubren.

| Principio de responsabilidad única | Principio abierto/cerrado | Principio de sustitución de Liskov |
|-|-|-|
| Es una aplicación directa de la **alta cohesión** funcional | Implementa mecanismos para lograr **bajo acoplamiento** entre componentes | Asegura que el **polimorfismo** funcione correctamente |
| Permite crear clases enfocadas en una única función o propósito | Promueve la reutilización mediante extensión sin modificación | Garantiza que las clases derivadas puedan sustituir a las base sin afectar el comportamiento del programa |
| Facilita la comprensión, modificación y reutilización | Utiliza técnicas como el patrón método plantilla, factorías, y la inversión de control | Evita violaciones como el uso excesivo de instanceof, métodos vacíos o lanzamiento inesperado de excepciones |
| **Principio de segregación de interfaces** | **Principio de inversión de dependencias** | |
| Refuerza la **cohesión** a nivel de interfaces | Minimiza el **acoplamiento** entre módulos | |
| Evita interfaces "gordas" con métodos no relacionados | Desacopla componentes de alto nivel de los de bajo nivel | |
| Facilita la creación de clientes que solo conocen lo que necesitan | Introduce abstracciones que actúan como contratos estables | |

Aplicarlos como vocabulario - para nombrar y comunicar problemas de diseño - es útil. Aplicarlos como dogma es la fuente más común de sobre-ingeniería.

> *#2Think*: [Luis Fernandez acerca de SOLID](https://youtu.be/YqSV_RuWRV0?si=n3q4XM6_aElYRB9s&t=6)

<br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
