package ua.synkulych.sort_it.entity;

public class Response<T> {
  private boolean isOK;
  private String title, description;
  private T value;

  public Response() {

  }

  public boolean isOK() {
    return isOK;
  }

  public String getTitle() {
    return title;
  }

  public String getDescription() {
    return description;
  }

  public T getValue() {
    return value;
  }

  public void setOK(boolean OK) {
    isOK = OK;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setValue(T value) {
    this.value = value;
  }
}
