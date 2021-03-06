package BD;

import Entidades.Cursadas;
import Entidades.Materia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConectorBD {
    private static  ConectorBD conectorBD;
    private Connection connection=null;

    private ConectorBD(){

    }

    public static ConectorBD getInstance(){
        if(conectorBD==null){
            conectorBD=new ConectorBD();
        }
        return conectorBD;
    }

    public void createAll(){
        this.clear();
        this.createBD();
        this.createTables();
    }

    public Connection Conectar() {
        if(connection==null){
            try {
                String driverName = "com.mysql.jdbc.Driver";
                Class.forName(driverName); // here is the ClassNotFoundException

                String serverName = "127.0.0.1";
                //String mydatabase = "instricpcion";
                String url = "jdbc:mysql://" + serverName; //+ "/" + mydatabase;

                String username = "root";
                String password = "";
                connection = DriverManager.getConnection(url, username, password);
            }catch (SQLException e){
                System.out.println(e.getMessage());
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }

        return connection;
    }

    public void desconectar(){
        try {
            if(connection!=null){
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void clear() {
        Connection cn = this.Conectar();
        try{
            PreparedStatement pstat = cn.prepareStatement("DROP DATABASE IF EXISTS inscripcion;");
            pstat.execute();

        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    }

    public void createBD(){
        Connection cn = this.Conectar();
        try{
            PreparedStatement pstat = cn.prepareStatement("CREATE DATABASE inscripcion;");
            pstat.execute();
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }

    }

    public Connection ConectarBD() {
        try {
            String driverName = "com.mysql.jdbc.Driver";
            Class.forName(driverName); // here is the ClassNotFoundException

            String serverName = "127.0.0.1";
            String mydatabase = "inscripcion";
            String url = "jdbc:mysql://" + serverName + "/" + mydatabase;

            String username = "root";
            String password = "";
            connection = DriverManager.getConnection(url, username, password);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return connection;
    }

    public void createTables(){
        this.createTableAlumno();
        this.createTableProfesor();
        this.createTableCursadas();
        this.createTableInscripcion();
        this.createTableMateria();
        this.createTableHistorial();
        this.createTableCorrelativas();

    }


    public void createTableAlumno(){
        Connection cn = this.ConectarBD();
        try{
            PreparedStatement pstat = cn.prepareStatement("DROP TABLE IF EXISTS alumno;");
            pstat.execute();
            PreparedStatement pstat1 = cn.prepareStatement("CREATE TABLE `alumno` (\n" +
                    "  `id` INT(11) NOT NULL AUTO_INCREMENT,\n" +
                    "  `nombre` VARCHAR(50) NOT NULL,\n" +
                    "  `apellido` VARCHAR(50) NOT NULL,\n" +
                    "  `legajo` INT(11) NOT NULL,\n" +
                    "  `regular` INT(11) NOT NULL,\n" +
                    "  `mail` VARCHAR(45) NOT NULL,\n" +
                    "  `password` VARCHAR(45) NOT NULL,\n" +
                    "  PRIMARY KEY (`id`)\n" +
                    ") ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=0 ;");
            pstat1.execute();
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }

    }

    public void createTableCursadas(){
        Connection cn = this.ConectarBD();
        try{
            PreparedStatement pstat = cn.prepareStatement("DROP TABLE IF EXISTS cursadas;");
            pstat.execute();
            PreparedStatement pstat1 = cn.prepareStatement("CREATE TABLE `cursadas` (\n" +
                    "  `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
                    "  `dias_y_horarios` varchar(150) NOT NULL,\n" +
                    "  `materia_id` int(11) NOT NULL,\n" +
                    "  `nro_aula` varchar(45) NOT NULL,\n" +
                    "  `cupo` int(11) NOT NULL,\n" +
                    "  `profesor_id` int(11),\n" +
                    "\n" +
                    "  PRIMARY KEY (`id`),\n" +
                    "  KEY `fk_cursadas_materia1_idx` (`materia_id`)\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=0 ;");
            pstat1.execute();
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }

    }

    public void createTableInscripcion(){
        Connection cn = this.ConectarBD();
        try{
            PreparedStatement pstat = cn.prepareStatement("DROP TABLE IF EXISTS inscripciones;");
            pstat.execute();
            PreparedStatement pstat1 = cn.prepareStatement("CREATE TABLE `inscripciones` (\n" +
                    "  `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
                    "  `usuario_id` int(11) NOT NULL,\n" +
                    "  `cursadas_id` int(11) NOT NULL,\n" +
                    "  PRIMARY KEY (`id`),\n" +
                    "  KEY `fk_inscripciones_usuario1_idx` (`usuario_id`),\n" +
                    "  KEY `fk_inscripciones_cursadas1_idx` (`cursadas_id`)\n" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=0 ;");
            pstat1.execute();
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }

    }

    public void createTableMateria(){
        Connection cn = this.ConectarBD();
        try{
            PreparedStatement pstat = cn.prepareStatement("DROP TABLE IF EXISTS materia;");
            pstat.execute();
            PreparedStatement pstat1 = cn.prepareStatement("CREATE TABLE `materia`(\n" +
                    "  `id` int(11) NOT NULL AUTO_INCREMENT,\n" +
                    "  `nombre` varchar(50) DEFAULT NULL,\n" +
                    "  `carga_horaria` int(11) DEFAULT NULL,\n" +
                    "  PRIMARY KEY (`id`)\n" +
                    ") ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=0  ;");
            pstat1.execute();
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }

    }

    public void createTableProfesor(){
        Connection cn = this.ConectarBD();
        try{
            PreparedStatement pstat = cn.prepareStatement("DROP TABLE IF EXISTS profesor;");
            pstat.execute();
            PreparedStatement pstat1 = cn.prepareStatement("CREATE TABLE `profesor`(\n" +
                    "  `id` INT(11) NOT NULL AUTO_INCREMENT,\n" +
                    "  `nombre` VARCHAR(45) NOT NULL,\n" +
                    "  `apellido` VARCHAR(45) NOT NULL,\n" +
                    "  `legajo` INT(11) NOT NULL,\n" +
                    "  `mail` VARCHAR(45) NOT NULL,\n" +
                    "  `password` VARCHAR(45) NOT NULL,\n" +
                    "  PRIMARY KEY (`id`)\n" +
                    ") ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=0  ;");
            pstat1.execute();
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }

    }

    private void createTableHistorial() {
        Connection cn = this.ConectarBD();
        try{
            PreparedStatement pstat = cn.prepareStatement("DROP TABLE IF EXISTS historial;");
            pstat.execute();
            PreparedStatement pstat1 = cn.prepareStatement("CREATE TABLE `historial`(\n" +
                    "  `id` INT(11) NOT NULL AUTO_INCREMENT,\n" +
                    "  `alumno_id` int(11) NOT NULL,\n" +
                    "  `cursada_id` int(11) NOT NULL,\n" +
                    "  `promedio` int(11) NOT NULL,\n" +
                    "  `fecha` varchar(45) NOT NULL,\n" +
                    "  PRIMARY KEY (`id`)\n" +
                    ") ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=0  ;");
            pstat1.execute();
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }

    }


    private void createTableCorrelativas() {
        Connection cn = this.ConectarBD();
        try{
            PreparedStatement pstat = cn.prepareStatement("DROP TABLE IF EXISTS correlativas;");
            pstat.execute();
            PreparedStatement pstat1 = cn.prepareStatement("CREATE TABLE `correlativas`(\n" +
                    "  `id` INT(11) NOT NULL AUTO_INCREMENT,\n" +
                    "  `materia_id` int(11) NOT NULL,\n" +
                    "  `materia_correlativa_id` int(11) NOT NULL,\n" +
                    "  PRIMARY KEY (`id`)\n" +
                    ") ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=0  ;");
            pstat1.execute();
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }

    }


}
