package com.eleme.soa.eus;

public interface ElemeUserService {
  /**
   * @param user_id
   * @return
   * @throws EUSUserException
   * @throws EUSSystemException
   * @throws EUSUnknownException
   */
  TUserProfile get_profile(Integer user_id)
      throws EUSUserException, EUSSystemException, EUSUnknownException;
}
