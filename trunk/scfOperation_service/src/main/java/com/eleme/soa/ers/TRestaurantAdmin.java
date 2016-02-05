package com.eleme.soa.ers;

import me.ele.contract.annotation.Index;

public class TRestaurantAdmin {
  private @Index(1) int restaurant_id;
  private @Index(2) int user_id;

  public int getRestaurant_id() {
    return restaurant_id;
  }

  public void setRestaurant_id(int restaurant_id) {
    this.restaurant_id = restaurant_id;
  }

  public int getUser_id() {
    return user_id;
  }

  public void setUser_id(int user_id) {
    this.user_id = user_id;
  }
}
