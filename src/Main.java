import java.util.Scanner;

public class Main {

    public static String[] datos_postulantesBD;
    public static String[] datos_centroMedico;

    public static void main(String[] args) {

        System.out.println("------------------------------------");
        System.out.println("SISTEMA DE REGISTRO DE POSTULANTES");
        System.out.println("------------------------------------");

        String cerrarApp = "S";

        Scanner scanner = new Scanner(System.in);

        while (!cerrarApp.equals("N")) {

            System.out.println("\nIngrese a tarea a realizar:");
            System.out.println("-------------------------------");
            System.out.println("Registrar postulante: 1");
            System.out.println("Actualizar resultado Exámen médico: 2");
            System.out.println("Buscar registro: 3");

            int opcion = scanner.nextInt();

            switch (opcion) {

                case 1:
                    registraPostulante();
                    break;
                case 2:
                    actualizarResultados();
                    break;
                case 3:
                    buscaPostulante();
                    break;
                default:
                    System.out.println("Opción no válida");
                    break;

            }

            System.out.println("Desear realizar otra tarea?: Continuar(S) - Salir(N)");
            cerrarApp = scanner.next();

        }

    }


    // Responsable Yoel
    static void registraPostulante() {
    }

    // Responsable Yoel
    static String validaRequisitos() {
        return "";
    }

    // Dayer
    static void actualizarResultados(){

    }

    // Harumy
    static void buscaPostulante() {
    }
    //QueryDB método
    static void queryDB(String type, String[] data) {
        switch (type){
            case "insert" : //table_postulantes
                /**
                 * CENTRO MEDICO DE SALUD: nombreCeMed, direccionDepCeMed
                 * POSTULANTE:  nombres, apellidos, numDni, edad, direccionDep,
                 * TRAMITE: tramite, categoria, fecha, hora, resultado, NumExpedienteMTC
                 * BUSQUEDA:  numDNI, fecha
                 * */
                break;
            case "select" : break;
            //case "update" : break;
            //case "delete" : break;
        }
    }
    static void init_datos() {
        //datos_postulantes = new Array datos_centroMedico
    }
}