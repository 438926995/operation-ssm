package com.eleme.soa.fuss;

import java.util.List;

public interface FussService {
  /**
   * @return
   */
  boolean ping();

  /**
   * @param fuss_file
   * @return
   * @throws FussException
   */
  String file_upload(FussFile fuss_file) throws FussException;

  /**
   * @param fuss_file
   * @param size
   * @return
   * @throws FussException
   */
  String file_upload_sized(FussFile fuss_file, List<List<Integer>> size) throws FussException;

  /**
   * @param fuss_file
   * @param size
   * @return
   * @throws FussException
   */
  String file_upload_sized_with_watermarker(FussFile fuss_file, List<List<Integer>> size)
      throws FussException;

  /**
   * @param hash
   * @param size
   * @return
   * @throws FussException
   */
  String file_get_sized(String hash, String size) throws FussException;

  /**
   * @param hash
   * @return
   * @throws FussException
   */
  String file_get(String hash) throws FussException;

  /**
   * @param hash
   * @return
   * @throws FussException
   */
  void file_delete(String hash) throws FussException;

  /**
   * @param hash
   * @param size
   * @return
   * @throws FussException
   */
  void file_delete_sized(String hash, String size) throws FussException;

  /**
   * @param hash
   * @return
   * @throws FussException
   */
  void file_delete_all(String hash) throws FussException;

  /**
   * @param hash
   * @return
   * @throws FussException
   */
  void produce_food_images(String hash) throws FussException;

  /**
   * @param fuss_file
   * @return
   * @throws FussException
   */
  String avatar_upload(FussFile fuss_file) throws FussException;
}
