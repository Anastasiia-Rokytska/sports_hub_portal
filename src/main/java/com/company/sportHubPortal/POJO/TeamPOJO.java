package com.company.sportHubPortal.POJO;

import java.io.IOException;
import java.sql.SQLException;
import org.springframework.web.multipart.MultipartFile;


public class TeamPOJO {

  private String name;
  private String location;
  private Double latitude;
  private Double longitude;
  private MultipartFile icon;

  public TeamPOJO(String name, String location, Double latitude, Double longitude,
                  MultipartFile icon) throws IOException, SQLException {
    this.name = name;
    this.location = location;
    this.latitude = latitude;
    this.longitude = longitude;
    this.icon = icon;
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

  public MultipartFile getIcon() {
    return icon;
  }

  public void setIcon(MultipartFile icon) {
    this.icon = icon;
  }
}
