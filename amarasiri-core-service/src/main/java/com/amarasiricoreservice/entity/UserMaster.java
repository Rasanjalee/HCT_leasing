package com.amarasiricoreservice.entity;

import lombok.Data;
import org.springframework.data.relational.core.sql.In;

import javax.persistence.*;
@Data
@Entity
@Table(name = "usermaster")
public class UserMaster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userkey")
    private Integer userKey;

    @Column(name = "firstname")
    private String firstName;

    @Column(name = "lastname")
    private String lastName;

    @Column(name = "mobilenumber")
    private String mobileNumber;

    @Column(name= "usertypekey")
    private Integer userTypeKey;

    @Column(name = "usergroupkey")
    private Integer userGroupKey;

    @Column(name = "profileimagepath")
    private String progileImagePath;

    @Column(name = "loginid")
    private String lognId;

    @Column(name = "password")
    private String password;

    @Column(name = "isenable")
    private Integer isEnable;

    @Column(name = "ishidden")
    private Integer isHidden;

    @Column(name = "address")
    private String address;

    @Column(name = "identificataionnumber")
    private String identificationNumber;

    @Column(name = "homenumber")
    private String homeNumber;

    @Column(name = "email")
    private String email;



}
