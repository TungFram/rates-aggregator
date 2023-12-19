package ru.shtybcompany.ratesaggregator.enities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Value
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@Builder(builderClassName = "TariffBuilder",
        builderMethodName = "createBuilder",
        toBuilder = true,
        access = AccessLevel.PUBLIC,
        setterPrefix = "with")
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "tariffs")
public class TariffEntity implements DomainEntity {

    @Id
    @GeneratedValue
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    @Column(name = "tariff_id", nullable = false, updatable = false, unique = true)
    UUID tariffID;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    LocalDateTime updatedAt;

    @Column(name = "title", length = 32, nullable = false)
    String title;

    @Column(name = "description", length = 1024)
    String description;

    @Column(name = "url", length = 256, nullable = false)
    String url;

    @Column(name = "sms", nullable = false)
    int sms;

    @Column(name = "gigabytes", nullable = false)
    int gbs;

    @Column(name = "minutes", nullable = false)
    int minutes;

    @Column(name = "is_archived", nullable = false, updatable = true)
    Boolean isArchived;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "tariff", orphanRemoval = true, fetch = FetchType.LAZY)
    @Singular
    List<TariffPriceEntity> prices;

}
