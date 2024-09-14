package com.org.capstone.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "cust_acct_role")
@Data
@NoArgsConstructor
public class CustAccRole {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "customer_id")
    private String customerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accountId", referencedColumnName = "accountId")
    @JsonBackReference
    private Account accountFields;


    @Column(name = "role_title_cd", nullable = false)
    private String roleTitleCd;

    @Column(name = "update_ts", nullable = false, updatable = false)
    private Timestamp updateTs;



    // Automatically set the update timestamp before persisting the entity
    @PrePersist
    protected void onCreate() {
        updateTs = Timestamp.valueOf(LocalDateTime.now().withNano(0)); // Removes the nanoseconds
    }


//    // Custom setter with validation for acctId
//    public void setAcctId(long acctId) {
//        if (String.valueOf(acctId).length() != 10) {
//            throw new IllegalArgumentException("Account ID must be 10 digits long");
//        }
//        this.acctId = acctId;
//    }

//    // Custom setter with validation for roleTitleCd
//    public void setRoleTitleCd(String roleTitleCd) {
//        if (roleTitleCd.length() > 5) {
//            throw new IllegalArgumentException("Role Title Code cannot be longer than 5 characters");
//        }
//        this.roleTitleCd = roleTitleCd;
//    }
}