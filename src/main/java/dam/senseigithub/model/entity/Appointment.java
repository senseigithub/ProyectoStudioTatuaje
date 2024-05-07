package dam.senseigithub.model.entity;

import java.time.LocalDateTime;

public class Appointment {
    private int idAppointment;
    private Client client;
    private LocalDateTime date;

    public Appointment(int idAppointment, Client client, LocalDateTime date) {
        this.idAppointment = idAppointment;
        this.client = client;
        this.date = date;
    }

    public Appointment() {
    }

    public void setDate(String date) {
        this.date = LocalDateTime.parse(date);
    }
    public int getIdAppointment() {
        return idAppointment;
    }

    public void setIdAppointment(int idAppointment) {
        this.idAppointment = idAppointment;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "idAppointment=" + idAppointment +
                ", client=" + client +
                ", date=" + date +
                '}';
    }
}
