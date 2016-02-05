package com.eleme.domain.mart.seller;

import java.io.Serializable;

/**
 * 商户列表页面显示封装类.
 *
 * @author penglau
 */
public class Seller implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 5756889974273445815L;

    // 商户id
    private String seller_id;
    // 商户名称
    private String seller_name;
    // 商户账号
    private String seller_account;
    // napos oid
    private String napos_res_oid;
    // 城市id
    private String city_id;
    // 城市名称
    private String city_name;
    // 区域类型
    private String type_code;
    // 添加时间
    private String created_at;
    // 持有者姓名
    private String keeper_name;
    // 持有者联系电话
    private String keeper_phone;
    // 地址
    private String address_text;
    // 描述
    private String description;
    // 是否有效
    private String is_valid;

    public String getIs_valid() {
        return is_valid;
    }

    public void setIs_valid(String is_valid) {
        this.is_valid = is_valid;
    }

    public String getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(String seller_id) {
        this.seller_id = seller_id;
    }

    public String getSeller_name() {
        return seller_name;
    }

    public void setSeller_name(String seller_name) {
        this.seller_name = seller_name;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getType_code() {
        return type_code;
    }

    public void setType_code(String type_code) {
        this.type_code = type_code;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getKeeper_name() {
        return keeper_name;
    }

    public void setKeeper_name(String keeper_name) {
        this.keeper_name = keeper_name;
    }

    public String getKeeper_phone() {
        return keeper_phone;
    }

    public void setKeeper_phone(String keeper_phone) {
        this.keeper_phone = keeper_phone;
    }

    public String getNapos_res_oid() {
        return napos_res_oid;
    }

    public void setNapos_res_oid(String napos_res_oid) {
        this.napos_res_oid = napos_res_oid;
    }

    public String getAddress_text() {
        return address_text;
    }

    public void setAddress_text(String address_text) {
        this.address_text = address_text;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSeller_account() {
        return seller_account;
    }

    public void setSeller_account(String seller_account) {
        this.seller_account = seller_account;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }
}
