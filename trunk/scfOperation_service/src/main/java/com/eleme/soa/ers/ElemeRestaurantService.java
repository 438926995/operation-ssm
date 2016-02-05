package com.eleme.soa.ers;

import java.util.List;

public interface ElemeRestaurantService {
  /**
   * @param restaurant_ids
   * @return
   * @throws ERSUserException
   * @throws ERSSystemException
   * @throws ERSUnknownException
   */
  List<TRestaurant> mget(List<Integer> restaurant_ids)
      throws ERSUserException, ERSSystemException, ERSUnknownException;

  /**
   * @param restaurant_oid
   * @return
   * @throws ERSUserException
   * @throws ERSSystemException
   * @throws ERSUnknownException
   */
  TRestaurant get_by_oid(Integer restaurant_oid)
      throws ERSUserException, ERSSystemException, ERSUnknownException;

  /**
   * @param query_struct
   * @return
   * @throws ERSUserException
   * @throws ERSSystemException
   * @throws ERSUnknownException
   */
  List<TRestaurant> query_restaurant(TRestaurantQuery query_struct)
      throws ERSUserException, ERSSystemException, ERSUnknownException;

  /**
   * @param restaurant_id
   * @return
   * @throws ERSUserException
   * @throws ERSSystemException
   * @throws ERSUnknownException
   */
  TRestaurantAdmin get_restaurant_admin(Integer restaurant_id)
      throws ERSUserException, ERSSystemException, ERSUnknownException;
}
