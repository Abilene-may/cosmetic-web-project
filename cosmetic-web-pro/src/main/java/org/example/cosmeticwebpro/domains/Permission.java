package org.example.cosmeticwebpro.domains;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "permission")
public class Permission {
  @Id
  @SequenceGenerator(
      name = "permission_seq",
      sequenceName = "permission_seq",
      allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "permission_seq")
  @Column(name = "id")
  private Long id;

  @NonNull
  @Column(name = "permission_name")
  private String permissionName;

  @Column(name = "description")
  private String description;

  @Column(name = "create")
  private Boolean create;

  @Column(name = "view")
  private Boolean view;

  @Column(name = "update")
  private Boolean update;

  @Column(name = "delete")
  private Boolean delete;

  @OneToMany(mappedBy = "permission")
  private Set<RolePermission> rolePermissions = new HashSet<>();
}
