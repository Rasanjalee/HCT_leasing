package com.amarasiricoreservice.entity;

import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "Leaseemaster")
public class LeaseeMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="Leaseekey")
    private Integer leaseeId;

    @Column(name="Firstname")
    private String firstName;

    @Column(name="Lastname")
    private String lastName;

    @Column(name="Identificationnumber")
    private String nic;

    @Column(name="Mobilenumber")
    private String mobileNumber;

    @Column(name="Homenumber")
    private String homeNumber;

    @Column(name="Address")
    private String address;

    @Column(name="Googlecoordinates")
    private String googleCords;

    @Column(name="Loyaltystatus")
    private int loyaltyStatus;

    @Column(name="Leaseeremarks")
    private String remarks;

    @Column(name="Profileimagepath")
    private String profimagePath;

    @Column(name="Rating")
    private Float rating;

    @Column(name="Email")
    private String email;

    @Column(name="Ishidden")
    private int isHidden;

//    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
//    @Fetch(FetchMode.SUBSELECT)
//    @JoinColumn(name = "Leaseekey")
//    private List<Vehicle> vehicles;

//    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
//    @Fetch(FetchMode.SUBSELECT)
//    @JoinColumn(name = "Leaseekey")
//    private List<LeaseMaster> leaseMasters;

    }
