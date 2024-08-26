package com.nookure.inv.api.database;


import io.ebean.Model;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseModel extends Model {
  public static final String DATABASE_NAME = "nkinventory";

  public BaseModel() {
    super(DATABASE_NAME);
  }

  @Id
  Long id;

  /**
   * Get the id of the model.
   * @return the id of the model.
   */
  public Long id() {
    return id;
  }
}
