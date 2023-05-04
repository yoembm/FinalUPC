
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import java.io.InputStreamReader;

import java.util.Scanner;

public class Main {


    public static  arrayDb myQuery = new arrayDb(); //Variable Global base de datos.


    public static void main(String[] args) {

        System.out.println("-------------------------------------------------------------------------------");
        System.out.println("                     SISTEMA DE REGISTRO DE POSTULANTES                        ");
        System.out.println("     validación de trámites, registro postulantes, búsqueda de expedientes     ");
        System.out.println("                         Version 1.0   |   2023                                ");
        System.out.println("-------------------------------------------------------------------------------");


        String interruptorAPP = "open";

        Scanner scanner = new Scanner(System.in);

        //Selector de opciones
        do{
            System.out.println("\n\n                                MENU PRINCIPAL"+"\n");
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
                    buscaExpedientes();
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

        String[] newPostulante = new String[10];

        Scanner scanner = new Scanner(System.in);

        System.out.println("\n     Ingrese datos para registro de postulante:");
        System.out.println("    ----------------------------------------------------");

        System.out.print("      Nombre: ");
        newPostulante[0] = scanner.next();

        System.out.print("      Apellidos: ");
        newPostulante[1] = scanner.next();

        System.out.print("      Documento de identidad: ");
        newPostulante[2] = scanner.next();

        System.out.print("      Fecha de vencimiento DNI (dd/mmm/yyyy): ");
        newPostulante[3] = scanner.next();

        System.out.print("      Edad: ");
        newPostulante[4] = scanner.next();

        System.out.print("      Departamento: ");
        newPostulante[5] = scanner.next();

        System.out.print("      Categoría a la que postula? (A1/A2A/A2B/A3A/A3B/A3C): ");
        newPostulante[6] = scanner.next();

        if (!newPostulante[6].equals("A1")){

            System.out.println("Categoría actual: ");
            newPostulante[7] = scanner.next();

            System.out.println("Fecha de emisión de brevete: dd/mm/yyyy ");
            newPostulante[8] = scanner.next();

            System.out.println("Fecha de vencimiento de brevete: dd/mm/yyyy ");
            newPostulante[9] = scanner.next();

            System.out.println("Numero de papeletas?: ");
            newPostulante[10] = scanner.next();

        }else {
            newPostulante[7] = "NA";
            newPostulante[8] = "NA";
            newPostulante[9] = "NA";
        }


        // Valida si el postulante cumple con los requisitos
        boolean validPostulante = registroPostValidarRequisitos(newPostulante);

        if (validPostulante) {
            arrayDb.nuevoPostulante(newPostulante);
            System.out.println("      Se ha ingresado el postulante: \n      DNI: " + newPostulante[2] + "\n      Nombre: " + newPostulante[0] + " " + newPostulante[1] + "\n      Edad: " + newPostulante[3] + "\n      Departamento: " + newPostulante[4]);
        } else {
            System.out.println("\n    El postulante no cumple con los requisitos de acuerdo con los reglamentos del MTC");
        }

    }

    // Responsable Yoel
    static boolean registroPostValidarRequisitos(String[] datosPostulante) {
        boolean valido = true;
        String categoriaNueva = datosPostulante[6];
        String categoriaBusqueda = datosPostulante[7]+"-"+ datosPostulante[6]; //"Ejem. A1-A2A"

        // validación departamento
        if (!datosPostulante[5].toUpperCase().equals("LIMA")){
            valido = false;
            System.out.println("\nNo es posible registrar postulante.El domicilio ingresado no esta habilitado para registro en Lima");
        }

        // validación DNI vigente dd/mm/yyyy
        LocalDate fechaHoy = LocalDate.now();
        LocalDate fechaDNI = LocalDate.of (Integer.parseInt(datosPostulante[3].substring(6)), Integer.parseInt(datosPostulante[3].substring(3, 5)), Integer.parseInt(datosPostulante[3].substring(0, 2)));
        long diasVencimientoDni = ChronoUnit.DAYS.between(fechaHoy,fechaDNI);

        if (valido && !(diasVencimientoDni > 0)){
            valido = false;
            System.out.println("\nNo es posible registrar postulante.Documento de identidad vencido");
        }



        // tabla  de validación de recategorización
        String[][] tablaValidacion = new String[][]{
                {"NA-A1","A1-A2A","A1-A2B","A2A-A2B","A2B-A3A","A2B-A3B","A2B-A3C","A3A-A3C","A3B-A3C"}, // categoría
                {"18","21","21","21","24","24","27","27","27"}, // edad mínima
                {"0","2","3","1","2","2","4","1","1"} // antiguedad de licencia
        };

        int posCategoria = 0;

        for (int i =0; i < 7; i++){
            String cat = tablaValidacion[0][i];
            if (categoriaBusqueda.equals(cat)){
                posCategoria = i;
            }
        }

        // Validación edad
        int edad = Integer.parseInt(datosPostulante[4]);
        int edadMinima = Integer.parseInt(tablaValidacion[1][posCategoria]);

        if (valido &&  !(edad >= edadMinima)) {
            valido = false;
            System.out.println("\nNo es posible registrar postulante.No se cumple con la edad minima de "+ edadMinima + " años para la categoría "+categoriaNueva);
        }

        if (!categoriaNueva.equals("A1")){
            // numero de papeletas
            if (valido && Integer.parseInt(datosPostulante[10]) > 0){
                valido = false;
                System.out.println("\nNo es posible registrar postulante.Usted cuenta con papeletas pendientes.");
            }

            // Verificar Brevete vigente
            LocalDate fechaBrevete = LocalDate.of(Integer.parseInt(datosPostulante[9].substring(6)), Integer.parseInt(datosPostulante[9].substring(3, 5)), Integer.parseInt(datosPostulante[9].substring(0, 2)));
            long diasVencBrevete = ChronoUnit.DAYS.between(fechaHoy,fechaBrevete);

            if (valido && !(diasVencBrevete >= -180 && diasVencBrevete <= 180)) {
                valido = false;
                System.out.println("\nNo es posible registrar postulante.La fecha de vencimiento de brevete se encuentra fuera de rango de recategorización");
            }

            // validación antiguedad de licencia actual
            LocalDate fechaEmision = LocalDate.of(Integer.parseInt(datosPostulante[8].substring(6)), Integer.parseInt(datosPostulante[8].substring(3, 5)), Integer.parseInt(datosPostulante[8].substring(0, 2)));
            long diasAntiguedad = ChronoUnit.DAYS.between(fechaEmision,fechaHoy);
            int diasAntMinima = Integer.parseInt(tablaValidacion[2][posCategoria])*365;

            if (valido &&  !(diasAntiguedad >= diasAntMinima)) {
                valido = false;
                System.out.println("\nNo es posible registrar postulante.No se cumple con la antiguedad minima de licencia "+ tablaValidacion[2][posCategoria] + " años para la categoría "+categoriaNueva);
            }
        }
        return valido;
    }

    // Harumy
    static void actualizarResultados(){

        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el numero de DNI");
        String dni= scanner.nextLine();

        String posicionPostulante = verificar_usuario(dni);

        if (!posicionPostulante.equals("")){

            System.out.println("\nEl postulante fue verificado.\n");
            //Ejemplo actualizar. columnas del tramite : tramite, fecha, resultado, NumExpedienteMTC
            String[] evaluacion = new String[5];
            Scanner postulante = new Scanner(System.in);
            System.out.println("Ingrese los datos del postulante");
            System.out.println("________________________________\n");
            System.out.println("Trámite (A1/A2A/A2B/A3A/A3B/A3C): ");

            evaluacion[0]= scanner.nextLine();
            System.out.println("Fecha (dd/mmm/yyyy): ");
            evaluacion[1]= scanner.nextLine();
            System.out.println("Resultado (APTO/NO APTO): ");
            evaluacion[2]= scanner.nextLine();
            System.out.println("NumExpedienteMTC (Debe contener 11 dígitos): ");
            evaluacion[3]= scanner.nextLine();



            //mostrar resultados en pantalla;

            myQuery.actualizarPostulante(dni, evaluacion);

            System.out.println( "\nTrámite Solicitado: \n\n" + evaluacion[0] +
                    " Fecha: \n" + evaluacion[1]+
                    " Resultado: \n" + evaluacion[2]+
                    " N° de expediente: "+evaluacion[3]);
            System.out.println("El postulante fue registrado");
        }else{
            System.out.println("\nUsuario no registrado.");
        }

    }

    static String verificar_usuario (String dni){ // Devuelve posicion de postulante
        boolean resultado = false;
        String posicion = "";
        String [][] resultado_busqueda=myQuery.buscaPostulantes("dni",dni); //actualmente dos variables
        if (resultado_busqueda.length > 0){
            posicion = resultado_busqueda[0][0];
        }
        return posicion;

    }

    // Dayer
    static void buscaExpedientes() {
        String buscar = "open";
        do{
            System.out.print("\n"+"    [3] BUSQUEDA EXPEDIENTES | Ingrese DNI del postulante: ");
            Scanner scanner = new Scanner(System.in);
            String DNI = scanner.next();

            String[][] encontrados= myQuery.buscaPostulantes("dni", DNI);

            int e, e1;
            System.out.println("  Nombres |  Apellidos   |    Dni   |  Edad  | Dept | Tramite | Categoría |"
                    + "Fecha y hora | Resultado | Expediente");
            for (e = 0; e< encontrados.length; e++){


                        //columnas del tramite : tramite, categoria, fecha, hora, resultado, NumExpedienteMTC
                System.out.println(encontrados[e][0] +" | "+ encontrados[e][1]+" | "+encontrados[e][2]+"| "+
                        encontrados[e][3]+" |   "+  encontrados[e][4]+" | "+encontrados[e][5]+"  | "+
                        encontrados[e][6]+" | "+  encontrados[e][7]+" |  "+encontrados[e][8]+" | "+
                        encontrados[e][9]+"  ");

            }
            System.out.println("    Se han encontrado "+ e +" expediente(s) "+"\n");
            System.out.println("    [3] BUSQUEDA EXPEDIENTES | ¿Desea hacer otra busqueda?");

            System.out.print("    Ingrese S para continuar y N para salir al menú principal: ");
            String respuesta = scanner.next();
            if(respuesta.equals("S")||respuesta.equals("s")){
                buscar = "open";
            }else{
                buscar = "close";
            }
        }while(buscar.equals("open"));

    }



}
 class arrayDb {
    //Tabla "tbPostulantes" (Simulando una BD).
    //columnas del postulante : ID, nombres, apellidos, numDni, edad, direccionDep,
    //columnas del tramite : tramite, categoria, fecha, hora, resultado, NumExpedienteMTC
    public static String[][] tbPostulantes = new String[25][10];

    public static String[][] tbPostulantes_resultados = new String[25][11];;


    //result
    //public static String[] resultados;
    public static int numFilasUsadas;
    public static String fila_idUltimaModificada;

    public static String message;


    public arrayDb() {

        //insertando contenido demo
        String demo1[] = {"Juan  A. ","Guzman Flores","40700000","35","Lima","Nuevo","A1 ","01/05/2023","Apto","20235000-001"}; tbPostulantes[0]= demo1;
        String demo2[] = {"Manuel C.","Romani Juarez","35632422","37","Lima","Recat","A3C","02/05/2023","Apto","20235000-002"}; tbPostulantes[1]= demo2;
        String demo3[] = {"Mercedes ","Gonzales Soto","33256565","23","Lima","Recat","A2B","02/05/2023","Apto","20235000-003"}; tbPostulantes[2]= demo3;
        String demo4[] = {"Maria Ana","Venegas Gomez","31323333","42","Lima","Recat","A2B","02/05/2023","Apto","20235000-004"}; tbPostulantes[3]= demo4;
        String demo5[] = {"Jaime Ney","Rogriguez Rio","48304545","18","Lima","Nuevo","A3C","02/05/2023","Apto","20235000-005"}; tbPostulantes[4]= demo5;
        String demo6[] = {"Eduardo A","Fausto  Sotil","47526532","42","Lima","Recat","A2B","03/05/2023","Apto","20235000-006"}; tbPostulantes[5]= demo6;
        String demo7[] = {"Ricardo C","Figueroa Diaz","38659562","18","Lima","Nuevo","A3C","03/05/2023","Apto","20235000-007"}; tbPostulantes[6]= demo7;
        String demo8[] = {"Ana Maria","Rojas  Perez ","36363252","42","Lima","Recat","A2B","03/05/2023","Apto","20235000-008"}; tbPostulantes[7]= demo8;
        String demo9[] = {"Rosa Elva","Carbajal Rio ","35262532","18","Lima","Nuevo","A3C","03/05/2023","Apto","20235000-009"}; tbPostulantes[8]= demo9;

        numFilasUsadas = 9;
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
                       if (!valor.equals("todos")  && !valor.matches("-?\\d+")) {
                            String [][] arrayError = new String[1][11];
                            arrayError[0][0] = "error";
                            return arrayError;
                        }
                        columnaAbuscar=2;
                break;
        }
        int f =0;

        for(int i=0; i<25; i++){
            //limpieza de busquedas anteriores
            tbPostulantes_resultados[i] = new String[11];

            //nuevos resultados
             if(valor.equals("todos") || valor.equals(tbPostulantes[i][columnaAbuscar])  ) { //si existe
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
     static boolean actualizarPostulante(String dbDni, String[] data){
         //int n = numFilasUsadas;
         //System.out.println(numFilasUsadas);

         for (int i=9; i < numFilasUsadas; i++) {
             if(tbPostulantes[i][2].equals(dbDni) ){
                 tbPostulantes[i][6] = data[0];
                 tbPostulantes[i][7] = data[1];
                 tbPostulantes[i][8] = data[2];
                 tbPostulantes[i][9] = data[3];
             }

         }
         //fila_idUltimaModificada = id;
         return true;
     }
}
