package com.company.sportHubPortal.Database;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.SQLException;
import java.util.Calendar;

@Entity
@Table(name = "team", uniqueConstraints={@UniqueConstraint(columnNames = {"latitude", "longitude"})})
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    @NonNull
    private String name;
    private String location;
    private Double latitude;
    private Double longitude;
    private Date addedAt;
    @Lob
    private Blob icon;

    public Team(String name, String location, Double latitude, Double longitude, MultipartFile icon) throws IOException, SQLException {
        this.name = name;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.icon = new SerialBlob(icon.getBytes());
        setAddedAt();
    }

    public Team(String name, String location, Double latitude, Double longitude, Blob icon) {
        this.name = name;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.icon = icon;
        setAddedAt();
    }

    public Team() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public byte[] getIcon() throws SQLException, IOException {
        if (icon == null) return null;
        return icon.getBinaryStream().readAllBytes();
    }

    public void setIcon(MultipartFile icon) throws IOException, SQLException {
        this.icon = new SerialBlob(icon.getBytes());
    }

    public Date getAddedAt() {
        return addedAt;
    }

    public void setAddedAt() {
        Calendar today = Calendar.getInstance();
//        today.clear(Calendar.HOUR);
//        today.clear(Calendar.MINUTE);
//        today.clear(Calendar.SECOND);
        this.addedAt = today.getTime();
    }

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", addedAt=" + addedAt +
                ", icon=" + icon +
                '}';
    }
}
