package org.example.cosmeticwebpro.domains;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
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
@Table(name = "permissions")
public class Permission {
  @Id
  @SequenceGenerator(
      name = "permissions_seq",
      sequenceName = "permissions_seq",
      allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "permissions_seq")
  @Column(name = "id")
  private Long id;

  @Column(name = "permission_name")
  private String permissionName;

  @Column(name = "description")
  private String description;

  @JsonIgnore
  @ManyToMany(mappedBy = "permissions", fetch = FetchType.LAZY)
  private Set<Role> roles = new HashSet<>();
}
