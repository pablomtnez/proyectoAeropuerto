package es.deusto.spq.pojo;

public class UserData {

    private String nombre;
    private String apellido;
    private String dni;
    private String mail;
    private String password;

    public UserData() {
        // required by serialization
    }

    public String getMail() {
        return this.mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String toString() {
        return "[nombre=" + nombre + ", apellido=" + apellido + ", dni="+ dni + ", mail=" + mail + ", password=" + password + "]";
    }
}