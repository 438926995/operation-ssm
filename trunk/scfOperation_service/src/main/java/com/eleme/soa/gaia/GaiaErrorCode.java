package com.eleme.soa.gaia;

public enum GaiaErrorCode {
  UNKNOWN_ERROR(0), DATABASE_ERROR(1), GEOHASH_NOT_VALID(2), POI_NAME_EMPTY(3), POI_CITY_EMPTY(
      4), POI_LOC_EMPTY(5), CRAWL_POI_ERROR(6), POI_NOT_FOUND(7), INVALID_LAT_LON(
          8), NO_PGUID_IN_POI(9), GEOHASH_NAME_NOT_FOUND(10), QUERY_AREA_TOO_LARGE(
              11), AMENDED_POI_NOT_FOUND(12), INVALID_FIELD_VALUE(13), CITY_NOT_FOUND(
                  14), DISTRICT_NOT_FOUND(15), ZONE_NOT_FOUND(16), QQMAP_ERROR(17), INVALID_PARAM(
                      18), UTP_AREA_NOT_FOUND(19);

  private final int value;

  private GaiaErrorCode(int value) {
    this.value = value;
  }

  /**
   * convert to int
   */
  public int getValue() {
    return value;
  }

  /**
   * convert from int
   */
  public static GaiaErrorCode get(int value) {
    for (GaiaErrorCode e : values()) {
      if (e.getValue() == value) {
        return e;
      }
    }
    return null;
  }
}
