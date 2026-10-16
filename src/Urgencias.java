/* =====================================================================
 *  EXAMEN PARCIAL 1 - ESTRUCTURAS DE DATOS
 *  Escenario A - "Sala de Urgencias"
 *
 *  INSTRUCCIONES
 *  1. Copia este archivo dentro de "src/" del proyecto de Java
 *     de la estructura de datos que elegiste (uno de tus 5 laboratorios),
 *     junto a sus archivos (por ejemplo Queue.java, QueueInterface.java,
 *     QueueNode.java).
 *  2. Completa TODOS los bloques marcados con el comentario // TODO
 *  3. Ejecuta la clase Urgencias (metodo main) desde tu IDE.
 *
 *  RESTRICCIÓN: la sala de espera debe almacenarse en TU estructura de
 *  datos y sus métodos implementados.
 *  Se permite el uso de ArrayList, LinkedList, java.util.PriorityQueue,
 *  arreglos redimensionables de la libreria estandar,etc. En caso que su ED no esté
 *  terminada o no funcione bien, pero se califica sobre 3.5.
 *  Si se permite: Scanner, System.out, String y tipos primitivos.
 * =====================================================================
 *
 *  JUSTIFICACION DE LA ESTRUCTURA DE DATOS ELEGIDA
 *  (rellena aqui o en el archivo .md del enunciado)
 *
 *  Estructura elegida .....: Lista Simplemente Enlazada
 *  Laboratorio de origen ..: 01
 *
 *  Por que esta estructura y no otra?
 *  Elegí la lista simple porque permite mantener a los pacientes ordenados por prioridad
 *  de forma sencilla.
 *  Además, respeta el orden de llegada entre pacientes con la misma prioridad.
 *  Aunque insertar un paciente requiere recorrer la lista y toma O(N), retirar
 *  al paciente más prioritario es rápido, con un costo de O(1), lo cual es importante
 *  en un sistema de atención médica.
 *
 * ===================================================================== */

import java.util.Iterator;
import java.util.Scanner;

public class Urgencias {

    // =================================================================
    // COMO SE REPRESENTA UN PACIENTE
    //
    // Cada paciente se guarda como UN TEXTO con 3 datos separados por ';'
    //
    //        nombre ; prioridad ; ordenDeLlegada
    //
    // Ejemplos:   "Ana;3;0"      "Luis;1;1"      "Marta;3;2"
    //
    // Usa las funciones de ayuda de mas abajo para leer cada parte:
    //        nombreDe("Ana;3;0")     ->  "Ana"
    //        prioridadDe("Ana;3;0")  ->  3
    //        ordenDe("Ana;3;0")      ->  0
    //
    // 'ordenDeLlegada' es 0, 1, 2, ... segun el orden en que llegaron.
    // Sirve para desempatar: a IGUAL prioridad, primero el numero menor.
    // =================================================================
    static final String SEP = ";";

    /**
     * Contador de llegadas: 0 para el primer paciente, 1 para el segundo, ...
     */
    int contadorLlegadas = 0;

    // =================================================================
    // PASO 1 - Conecta AQUI tu estructura de datos.
    // =================================================================
    MiLista sala = new MiLista(); // Lab 01 - Lista simple

    // -----------------------------------------------------------------
    // OPERACION 1 - Llega un paciente  (no produce salida)
    // -----------------------------------------------------------------
    public void llega(String nombre, int prioridad) {
        String paciente = nombre + SEP + prioridad + SEP + contadorLlegadas;
        contadorLlegadas++;

    //Cuando la sala esta vacia
        if (sala.isEmpty()) {
            sala.insertHead(paciente);
            return;
        }
    // Iterador para recorrer lista
        Iterator<ListNode> iter = sala.iterator();
        ListNode anterior = null;
        ListNode actual = null;
        boolean insertado = false;
        while (iter.hasNext()) {
            actual = iter.next();
            String pacienteActual = (String) actual.getDato();
            int prioridadActual = prioridadDe(pacienteActual);
            if (prioridad < prioridadActual) {
                if (anterior == null) {
                    sala.insertHead(paciente);
                } else {
                    sala.insert(anterior, paciente);
                }
                insertado = true;
                break;
            }
            anterior = actual;
        }
        if (!insertado) {
            sala.insertTail(paciente);
        }
    }

    // -----------------------------------------------------------------
    // OPERACION 2 - Ver estado de la sala
    // -----------------------------------------------------------------
    public String estado() {
        if (sala.isEmpty()) {
            return "SALA_VACIA";
        }
        StringBuilder sb = new StringBuilder("ESPERANDO: ");
        Iterator<ListNode> iter = sala.iterator();
        while (iter.hasNext()) {
            String p = (String) iter.next().getDato();
            sb.append(nombreDe(p)).append("(p").append(prioridadDe(p)).append(")");

            if (iter.hasNext()) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }
    // -----------------------------------------------------------------
    // OPERACION 3 - Atender al siguiente paciente (Retirar de la sala)
    // -----------------------------------------------------------------
    public String atender() {
        if (sala.isEmpty()) {
            return "SIN_PACIENTES";
        }
        //Paciente a atender en cabeza
        String p = (String) sala.getHead();
        Iterator<ListNode> iter = sala.iterator();
        if(iter.hasNext()){
            sala.remove(iter.next());
        }
        return "ATENDIDO: " + nombreDe(p) + " (prioridad " + prioridadDe(p) + ")";
    }

    // =================================================================
    // FUNCIONES DE AYUDA para leer un paciente guardado como texto.
    // No necesitas modificarlas.
    // =================================================================
    /**
     * De "Ana;3;0" devuelve "Ana".
     */
    static String nombreDe(String paciente) {
        return paciente.split(SEP)[0];
    }

    /**
     * De "Ana;3;0" devuelve 3.
     */
    static int prioridadDe(String paciente) {
        return Integer.parseInt(paciente.split(SEP)[1]);
    }

    /**
     * De "Ana;3;0" devuelve 0.
     */
    static int ordenDe(String paciente) {
        return Integer.parseInt(paciente.split(SEP)[2]);
    }

    // =================================================================
    // Bucle de menu. NO deberias necesitar cambiar nada de aqui abajo,
    // salvo que quieras mejorar los mensajes.
    // =================================================================
    public static void main(String[] args) {
        Urgencias sistema = new Urgencias();
        Scanner sc = new Scanner(System.in);
        boolean salir = false;

        while (!salir) {
            System.out.println();
            System.out.println("===== SALA DE URGENCIAS =====");
            System.out.println("1. Llega un paciente");
            System.out.println("2. Ver estado de la sala");
            System.out.println("3. Atender al siguiente paciente");
            System.out.println("0. Salir");
            System.out.print("Opcion: ");

            String opcion = sc.hasNextLine() ? sc.nextLine().trim() : "0";

            switch (opcion) {
                case "1": {
                    System.out.print("Nombre: ");
                    String nombre = sc.hasNextLine() ? sc.nextLine().trim() : "";
                    System.out.print("Prioridad (1-5): ");
                    String raw = sc.hasNextLine() ? sc.nextLine().trim() : "";

                    int prioridad;
                    try {
                        prioridad = Integer.parseInt(raw);
                    } catch (NumberFormatException e) {
                        System.out.println("ENTRADA_INVALIDA");
                        break;
                    }
                    if (nombre.isEmpty() || nombre.contains(SEP)
                            || prioridad < 1 || prioridad > 5) {
                        System.out.println("ENTRADA_INVALIDA");
                        break;
                    }
                    sistema.llega(nombre, prioridad);
                    break;
                }
                case "2":
                    System.out.println(sistema.estado());
                    break;
                case "3":
                    System.out.println(sistema.atender());
                    break;
                case "0":
                    salir = true;
                    break;
                default:
                    System.out.println("OPCION_INVALIDA");
            }
        }

        sc.close();
    }
}