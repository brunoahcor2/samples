package br.com.bahcor.springbootauditsample.model.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@EntityListeners(AuditingEntityListener.class)
@Audited(withModifiedFlag = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private String email;
    private String contact;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedBy
    private String createdBy;

    @CreatedDate
//    @NotAudited
    private LocalDateTime createdDate;

    @LastModifiedBy
    private String lastModifiedBy;

    @LastModifiedDate
//    @NotAudited
    private LocalDateTime lastModifiedDate;

    @PrePersist
    public void onPrePersist() {
        // audit("INSERT");
        log.info("### PrePersist :: INSERT");
    }
     
    @PreUpdate
    public void onPreUpdate() {
        log.info("### PreUpdate :: UPDATE");
    }

    @PostUpdate
    public void onPosUpdate() {
        log.info("### PostUpdate :: UPDATE");
    }
    
}
