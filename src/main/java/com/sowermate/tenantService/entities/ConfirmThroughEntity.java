package com.sowermate.tenantService.entities;

import com.sowermate.tenantService.entities.value.ConfirmThroughValue;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;
@Entity
@Getter
@Setter
@ToString(callSuper = false)
@Table(name = "confirm_through")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder(builderMethodName = "newBuilder", toBuilder = true)
public class ConfirmThroughEntity {

    private static final long serialVersionUID = -241370177952331642L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer confirmThroughId;

    @Column(name="uuid", unique=true,nullable=false, updatable=false)
    private String confirmThroughUuid;

    @Column(name = "name")
    private String name;

    @OneToOne(mappedBy="confirmThroughEntity",cascade=CascadeType.ALL)
    private ProFormaInvoiceEntity proFormaInvoiceEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="tenant_id")
    private TenantEntity tenantEntity;

    public ConfirmThroughValue toDTO() {
        return ConfirmThroughValue.newBuilder()
                .confirmThroughId(getConfirmThroughId())
                .confirmThroughUuid(getConfirmThroughUuid())
                .name(getName())
                .tenantValue(getTenantEntity().toDTO())
                .proFormaInvoice(getProFormaInvoiceEntity().toDTO())
                .build();
    }

}
