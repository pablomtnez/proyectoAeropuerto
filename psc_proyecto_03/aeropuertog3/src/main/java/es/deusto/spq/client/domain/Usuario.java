package es.deusto.spq.client.domain;

public class Usuario {

    private String username;
    private String nombre;
    private String email;
    private String dni;

  
    public Usuario() {
        
    }


    public Usuario(String username, String nombre, String email, String dni) {
        this.username = username;
        this.nombre = nombre;
        this.email = email;
        this.dni = dni;
    }

    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "username='" + username + '\'' +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", dni='" + dni + '\'' +
                '}';
    }
}
