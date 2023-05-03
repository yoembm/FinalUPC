import java.io.InputStreamReader;
import java.util.Scanner;

public class Main {


    public static  arrayDb myQuery = new arrayDb(); //Variable Global base de datos.


    public static void main(String[] args) {

        System.out.println("-------------------------------------------------------------------------------");
        System.out.println("                     SISTEMA DE REGISTRO DE POSTULANTES                        ");
        System.out.println("     validación de trámites, registro postulantes, búsqueda de expedientes     ");
        System.out.println("                         Version 1.0   |   2023                                ");
        System.out.println("-------------------------------------------------------------------------------"+"\n");
    ;

        String interruptorAPP = "open";

        Scanner scanner = new Scanner(System.in);

        //Selector de opciones
        do{
            System.out.println("                                MENU PRINCIPAL"+"\n");
            System.out.println("        [1]. Registrar un nuevo Postulante");
            System.out.println("        [2]. Ingresar resultados de evaluación");
            System.out.println("        [3]. Buscar expedientes médicos");
            System.out.println("        [4]. Salir" +"\n" );

            System.out.print("    Elija una opción : " );
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
                case 4: interruptorAPP = "end";

                    break;
                default:
                    System.out.println("Opción no válida");
                    break;

            }



        }while(interruptorAPP.equals("open"));

        //System.out.println("-------------------------------------------------------------------------------");
        System.out.println("\n"+"                    Gracias por usar nuestro programa                          ");
        System.out.println("                Grupo 3 | Fundamentos de programacion 1 |  UPC                        ");
        //System.out.println("-------------------------------------------------------------------------------" );





    }


    // Responsable Yoel
    static void registraPostulante() {

        //Ejemplo nuevo: nombres, apellidos, numDni, edad, direccionDep
        String[] newPostulante = new String[6];
        newPostulante[0] = "01";
        newPostulante[1] = "Frank C.";
        newPostulante[2] = "Valle Sanchez";
        newPostulante[3] = "40740000";
        newPostulante[4] = "31";
        newPostulante[5] = "Lima";
        myQuery.nuevoPostulante( newPostulante );

        System.out.println("    Se ha ingresado el postulante "+ newPostulante[0]+"\n");

    }

    // Responsable Yoel
    static String validaRequisitos() {
        return "";
    }

    // Harumy
    static void actualizarResultados(){

        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el numero de DNI");
        String dni= scanner.nextLine();
        Boolean Verificado = verificar_usuario(dni);
        if (Verificado == true){
            System.out.println("el postulante fue verificado");
            //Ejemplo actualizar. columnas del tramite : tramite, fecha, resultado, NumExpedienteMTC
            String[] evaluacion = new String[5];
            Scanner postulante = new Scanner(System.in);
            System.out.println("Ingrese los datos del postulante");
            System.out.println("Trámite: ");
            evaluacion[0]= scanner.nextLine();
            System.out.println("Fecha: ");
            evaluacion[1]= scanner.nextLine();
            System.out.println("Resultado: ");
            evaluacion[2]= scanner.nextLine();
            System.out.println("NumExpedienteMTC: ");
            evaluacion[3]= scanner.nextLine();

            evaluacion[0] = "recat A2B";
            evaluacion[1] = "10/12/2022";
            evaluacion[2] = "APTO";
            evaluacion[3] = "20230250003";
            //mostrar resultados en pantalla;
            myQuery.actualizarPostulante("01", evaluacion);
            System.out.println( " Trámite: recat A2B\n" +
                    " Fecha: 10/12/2022\n" +
                    " Resultado: APTO\n" +
                    " N° de expediente: 20230250003");
            System.out.println("El postulante fue registrado");
        }

    }

    static boolean verificar_usuario (String dni){
        boolean resultado = false;
        String [][] resultado_busqueda=myQuery.buscaPostulantes("dni",dni); //actualmente dos variables
                  if (resultado_busqueda.length > 0){
                      resultado=true;
                  }
        return resultado;


    }

    // Dayer
    static void buscaPostulante() {
        //Ejemplo de buscar
        String[][] encontrados= myQuery.buscaPostulantes("dni", "40740000");
        System.out.println("Encontrados:");
        int e, e1;
        for (e = 0; e< encontrados.length; e++){
            //System.out.println("Encontrados:");
            System.out.println(" datos fila" +e + ": "+
                    encontrados[e][0] +", "+ encontrados[e][1]+", "+encontrados[e][2]+", "+
                    encontrados[e][3]+", "+  encontrados[e][4]+", "+encontrados[e][5]+", "+
                    encontrados[e][6]+", "+  encontrados[e][7]+", "+encontrados[e][8]+", "+
                    encontrados[e][9]+", "+  encontrados[e][10]+" ");

        }
    }



}
 class arrayDb {
    //Tabla "tbPostulantes" (Simulando una BD).
    //columnas del postulante : ID, nombres, apellidos, numDni, edad, direccionDep,
    //columnas del tramite : tramite, categoria, fecha, hora, resultado, NumExpedienteMTC
    public static String[][] tbPostulantes = new String[25][11];

    public static String[][] tbPostulantes_resultados = new String[25][11];;


    //result
    //public static String[] resultados;
    public static int numFilasUsadas;
    public static String fila_idUltimaModificada;

    public static String message;


    public arrayDb() {
        //String[][] tbPostulantes = new String[25][11];
        //String[][] resultados = new String[25][11];
        //insertando contenido demo
        String demo1[] = {"11","2","3","41","5","6","7","8","9","10","11"}; tbPostulantes[0]= demo1;
        String demo2[] = {"12","2","3","42","5","6","7","8","9","10","11"}; tbPostulantes[1]= demo2;
        String demo3[] = {"13","2","3","43","5","6","7","8","9","10","11"}; tbPostulantes[2]= demo3;
        String demo4[] = {"14","2","3","44","5","6","7","8","9","10","11"}; tbPostulantes[3]= demo4;
        String demo5[] = {"15","2","3","45","5","6","7","8","9","10","11"}; tbPostulantes[4]= demo5;

        numFilasUsadas = 10;
       // ultimafilaModificada = 10;
    }

    static String[][] buscaPostulantes(String tipo, String valor) { //"fecha", "dni"
        int columnaAbuscar = 0;
        //validar que el dato ingresado es correcto
        switch (tipo) {
            case "fecha":
                        columnaAbuscar=8;
                break;
            case "dni": //Si no es numerico
                        if (!valor.matches("-?\\d+")) {
                            String [][] arrayError = new String[1][11];
                            arrayError[0][0] = "error";
                            return arrayError;
                        }
                        columnaAbuscar=3;
                break;
        }
        int f =0;
        for(int i=0; i<25; i++){
            //limpieza de busquedas anteriores
            tbPostulantes_resultados[i] = new String[11];

            //nuevos resultados
            if (valor.equals(tbPostulantes[i][columnaAbuscar])) { //si existe
                tbPostulantes_resultados[f] = tbPostulantes[i];
                f++;
            }
        }
        //separar solo resultados encontrados
        String [][] resultadosEncontrados = new String[f][11];
        for(int i=0; i<25; i++){

            if( ! (tbPostulantes_resultados[i][0] == null) ){ //validar si existe
                resultadosEncontrados[i]= tbPostulantes_resultados[i];
                f++;
            }
        }




        return resultadosEncontrados;
    }

    //Método nuevoPostulante . recibe como parametro un Array con datos String
    //   orden de columnas: ID, nombres, apellidos, numDni, edad, direccionDep.
    static boolean nuevoPostulante(String[] data){
        int n = numFilasUsadas;
        for (int i=0; i < data.length; i++) {
            tbPostulantes[n][i] = data[i];
        }
        numFilasUsadas++;
        //fila_idUltimaModificada = n;
        return true;
    }

     //Método actualizarPostulante . recibe como parametro un String identificador y un Array con datos String
     //     columnas del tramite : tramite, categoria, fecha, resultado, NumExpedienteMTC
     static boolean actualizarPostulante(String id, String[] data){
         //int n = numFilasUsadas;
         for (int i=10; i < numFilasUsadas; i++) {
             if(tbPostulantes[i][0].equals(id) ){
                 tbPostulantes[i][6] = data[0];
                 tbPostulantes[i][7] = data[1];
                 tbPostulantes[i][8] = data[2];
                 tbPostulantes[i][9] = data[3];
                 tbPostulantes[i][10] = data[4];
             }

         }
         //fila_idUltimaModificada = id;
         return true;
     }
}
