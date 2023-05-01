public class Pruebas {


    public static void main(String[] args) {

        testValidacionRequisitos();

    }

    static void testValidacionRequisitos() {

        String[] newPostulante = new String[11];
        newPostulante[0] = "Yoel"; // Nombre
        newPostulante[1] = "Briceño"; // Apellid
        newPostulante[2] = "40740000"; // DNI
        newPostulante[3] = "05/06/2026"; // Fecha caducidad DNI
        newPostulante[4] = "30"; // Edad
        newPostulante[5] = "Lima"; //Departamento
        newPostulante[6] = "A3A"; // categoria Nueva
        newPostulante[7] = "A2B"; // Categoria actual
        newPostulante[8] = "01/02/2020"; // Fecha emision brevete
        newPostulante[9] = "02/12/2022"; // Fecha caducidad brevete
        newPostulante[10] = "0"; // Nro papeletas

        boolean resultado = Main.registroPostValidarRequisitos(newPostulante);

        System.out.println("resultado: " + resultado);
    }

}
