package com.ario.safarstan.model;

public class MessageModel {

  private int id;
  private String message;

  public int getId() {
    return id;
  }

  private String title;

  public void setTitle(String title) {
    this.title = title;
  }

  public String getTitle() {
    return title;
  }
  public String getMessage() {
    return message;
  }

  public void setId(int id) {
    this.id = id;
  }


  public void setMessage(String message) {
    this.message = message;
  }

}