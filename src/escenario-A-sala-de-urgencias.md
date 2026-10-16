# Examen Parcial 1 — Estructuras de Datos

## Escenario A — "Sala de Urgencias"

> Este documento cubre el **Escenario A**.
> Debes resolver el ejercicio **reutilizando una de las estructuras de datos que
> implementaste en los laboratorios 01–05**. Parte de la nota depende de que
> **justifiques** por qué esa estructura es la adecuada (ver última sección).

---

## 1. Contexto

Un hospital necesita un sistema para gestionar la **sala de espera de urgencias**.
Los pacientes llegan en cualquier momento y cada uno trae un **nivel de gravedad
de 1 a 5**:

| Prioridad | Significado             |
| :-------: | ----------------------- |
|     1     | Más grave / reanimación |
|     2     | Muy urgente             |
|     3     | Urgente                 |
|     4     | Poco urgente            |
|     5     | Sin urgencia            |

El personal médico **siempre atiende primero al paciente más grave** que esté
esperando (es decir, el de **menor número** de prioridad). Si dos o más pacientes
tienen **la misma** gravedad, se atiende primero **al que llegó antes** (FIFO
dentro de un mismo nivel de prioridad).

Debes diseñar e implementar la estructura que gestiona la sala de espera y un
programa que la use.

---

## 2. Qué debes entregar

1. El archivo **`Urgencias.java`** completado, dentro del proyecto del laboratorio (estructura) que elegiste, junto a los archivos de esa estructura (por ejemplo `../../../Downloads/Queue.java`, `QueueInterface.java`, `QueueNode.java`).
2. La **justificación de la estructura elegida** como comentario al inicio de `Urgencias.java`).

---

## 3. El programa: menú interactivo

`Urgencias` es una clase **ejecutable** (tiene `main`). Al correr, muestra un
menú en un bucle hasta que se elige `0. Salir`:

```
===== SALA DE URGENCIAS =====
1. Llega un paciente
2. Ver estado de la sala
3. Atender al siguiente paciente
0. Salir
Opcion:
```

### Opción 1 — Llega un paciente

Pide `Nombre` y `Prioridad (1-5)` y registra al paciente en la sala.
**No produce salida** (simplemente vuelve a mostrar el menú).
Si el nombre está vacío o la prioridad no está entre 1 y 5, el paciente **no se
registra** y se imprime:

```
ENTRADA_INVALIDA
```

### Opción 2 — Ver estado de la sala

Lista a los pacientes que esperan, **en el mismo orden en que serían atendidos**:

- Con pacientes:
  `ESPERANDO: <nombre1>(p<prioridad1>), <nombre2>(p<prioridad2>), ...`
- Sin pacientes:
  `SALA_VACIA`

### Opción 3 — Atender al siguiente paciente

**Retira** y atiende al siguiente paciente según las reglas del dominio
(menor prioridad primero; a igual prioridad, el que llegó antes):

- Con paciente:
  `ATENDIDO: <nombre> (prioridad <prioridad>)`
- Sin pacientes:
  `SIN_PACIENTES`

### Opción 0 — Salir

Termina el programa.

---

## 4. Contrato de salida (exacto)

Respeta **mayúsculas, espacios, comas y paréntesis**. Diferencias de formato se
consideran incorrectas.

| Situación                    | Salida exacta                             |
| ---------------------------- | ----------------------------------------- |
| Estado con pacientes         | `ESPERANDO: Luis(p1), Ana(p3), Marta(p3)` |
| Estado sin pacientes         | `SALA_VACIA`                              |
| Atender con paciente en sala | `ATENDIDO: Luis (prioridad 1)`            |
| Atender con sala vacía       | `SIN_PACIENTES`                           |
| Entrada inválida en opción 1 | `ENTRADA_INVALIDA`                        |

Observa:

- En el estado: `Luis(p1)` **sin espacios**, y los pacientes se separan con
  `, ` (coma + espacio).
- Al atender: `ATENDIDO: Luis (prioridad 1)` **con** espacio antes del paréntesis.

---

## 5. Ejemplo de sesión

Lo que teclea el usuario aparece después de `Opcion:`, `Nombre:` y
`Prioridad (1-5):`. Se omiten las repeticiones del menú para abreviar.

```
Opcion: 1
Nombre: Ana
Prioridad (1-5): 3

Opcion: 1
Nombre: Luis
Prioridad (1-5): 1

Opcion: 1
Nombre: Marta
Prioridad (1-5): 3

Opcion: 2
ESPERANDO: Luis(p1), Ana(p3), Marta(p3)

Opcion: 3
ATENDIDO: Luis (prioridad 1)

Opcion: 2
ESPERANDO: Ana(p3), Marta(p3)

Opcion: 0
```

Ana llegó antes que Marta y ambas tienen prioridad 3, por eso Ana aparece
(y sería atendida) antes que Marta. Luis, con prioridad 1, va primero que ambas.

---

## 6. Cómo trabajar

1. **Elige la estructura de datos** (uno de tus 5 laboratorios) que consideres
   más adecuada para este problema.
2. **Copia `Urgencias.java`** dentro de `src/main/java/` del proyecto de esa
   estructura, junto a sus archivos.
3. **Completa los `TODO`** de `Urgencias.java`:
   - declara el atributo con tu estructura,
   - implementa `llega(...)`, `estado()` y `atender()`.
   - Cada paciente se guarda como un **texto** con el formato
     `nombre;prioridad;ordenDeLlegada` (p. ej. `"Ana;3;0"`). La plantilla
     trae funciones de ayuda ya hechas: `nombreDe(...)`, `prioridadDe(...)`,
     `ordenDe(...)` y `comparar(a, b)` (decide el orden de atención y el
     desempate por llegada). **No se usan clases propias.**
4. **Ejecuta** `Urgencias` desde tu IDE (Run → `main`)
5. Prueba con el **ejemplo de sesión** de la sección 5
   y con tus propios casos. Asegúrate de que la salida sea **exacta**.

---

## 7. Restricciones

- La sala de espera **debe almacenarse en tu estructura de datos**.
- Se permite el uso de ArrayList, LinkedList, java.util.PriorityQueue, arreglos redimensionables de la libreria estandar, etc. En caso que su ED no esté terminada o no funcione bien, pero será penalizado en la rúbrica.
- **Sí** se permiten: `Scanner`, `System.out`, `String` y tipos primitivos.
- El desempate por orden de llegada debe seguir siendo correcto **aunque haya
  muchos pacientes con la misma prioridad**.

---

## 8. Justificación de la estructura de datos elegida

> Justifica tu elección en el comentario al inicio de `Urgencias.java`.
> Se evalúa la calidad del razonamiento, no solo la elección.

**Estructura elegida:**
_(p. ej. cola de prioridad, lista simplemente enlazada mantenida ordenada, pila, …)_

**Laboratorio de origen:** _( 01 / 02 / 03 / 04 / 05 )_

**¿Por qué esta estructura y no otra?** _(mínimo 3 líneas)_

Apóyate en estas preguntas:

- ¿Cuál es la operación más frecuente o crítica del problema y cómo la resuelve tu estructura?
- ¿Cuál es el costo de complejidad de `llega`, `atender` y `estado` con tu elección?
- ¿Cómo garantizas el desempate FIFO entre pacientes de igual prioridad?
- ¿Qué desventaja aceptas al elegir esta estructura y por qué es tolerable en este escenario?

---

## 9. Criterios de evaluación (rúbrica)

| Criterio                                                                                              | Peso |
| ----------------------------------------------------------------------------------------------------- | :--: |
| Correctitud de las 3 operaciones y del contrato de salida (incluyendo el manejo de casos borde)       | 50 % |
| Uso correcto y coherente de su propia estructura de datos (implementación propia no libreria externa) | 40 % |
| Justificación de la estructura                                                                        | 10 % |

Si hace todo perfecto pero utiliza una estructura de datos no propia (libreria externa) completa el 60% de la nota, es decir, la nota máxima será 3.
