package interfazUsuario.cli;

import interfazUsuario.ObservadorContenido;
import interfazUsuario.ObservadorOrden;

import java.util.Scanner;

public class CLI implements ObservadorOrden, ObservadorContenido {


    public enum Menu {
        DOCUMENTO_VARIOS,
        DOCUMENTO_UNICO,
        PRINCIPAL,
        SALIR,
        EXPEDIENTE_VARIOS,
        CREAR_DOCUMENTO
    }



    private Menu estadoMenu = Menu.PRINCIPAL;
    private int opcion;
    private Scanner scanner = new Scanner(System.in);


    @Override
    public void actualizarContenido() {

    }

    @Override
    public void actualizarOrden() {

    }


    public void mostrarMenuPrincipal(){
        System.out.println("""
                ///////////////////////// Sistema de Gestión de Documentos /////////////////////////
                * Seleccione una Opción:
                    1. Mostrar Todos Los Documentos.
                    2. Mostrar Todos Los Expedientes.
                    3. Crear Documento.
                    4. Salir"""
        );

        int selecionUsuario = scanner.nextInt();
        switch (selecionUsuario){
            case 1:
                estadoMenu = Menu.DOCUMENTO_VARIOS;
                break;
            case 2:
                estadoMenu = Menu.EXPEDIENTE_VARIOS;
                break;
            case 3:
                estadoMenu = Menu.CREAR_DOCUMENTO;
        }
    }

    public void lanzar(){
        while (!estadoMenu.equals(Menu.SALIR)){

        }

    }

    public void mostrarDespedida(){
        System.out.println("""
                Bye!""");
    }

    public static void main(String[] args) {
        var cli = new CLI();
        cli.mostrarMenuPrincipal();
    }


}
