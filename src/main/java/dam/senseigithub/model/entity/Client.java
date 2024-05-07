package dam.senseigithub.model.entity;

public class Client {
    private int idClient;
    private String dnie;
    private String name;
    private String email;
    private String phone;

    public Client(int idClient, String dnie, String name, String email, String phone) {
        this.idClient = idClient;
        this.dnie = dnie;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }


    public Client() {
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public String getDnie() {
        return dnie;
    }

    public void setDnie(String dnie) {
        this.dnie = dnie;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Client{" +
                "idClient=" + idClient +
                ", dnie='" + dnie + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
