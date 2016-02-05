package com.eleme.soa.gaia;

import java.util.List;

public interface GaiaService {
  /**
   * @param query_struct
   * @return
   * @throws GaiaUserException
   * @throws GaiaSystemException
   * @throws GaiaUnknownException
   */
  List<TCity> query_city(TCityQuery query_struct)
      throws GaiaUserException, GaiaSystemException, GaiaUnknownException;
}
