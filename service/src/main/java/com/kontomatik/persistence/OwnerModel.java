package com.kontomatik.persistence;

import com.kontomatik.usecases.entity.Owner;
import org.hibernate.annotations.Cascade;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

import static org.hibernate.annotations.CascadeType.ALL;

@Entity(name = "owner")
@Table(name = "owners")
public class OwnerModel {

  @Id
  String id;

  @OneToMany(mappedBy = "owner")
  @Cascade(ALL)
  List<OwnerTargetModel> ownerTargets;

  public static OwnerModel from(Owner domain) {
    var created = new OwnerModel();
    created.id = domain.id;
    created.ownerTargets = domain.targets
      .stream()
      .map(target -> OwnerTargetModel.from(target, created))
      .toList();
    return created;
  }

  public Owner toDomain() {
    return new Owner(
      id,
      ownerTargets.stream()
        .map(OwnerTargetModel::toDomain)
        .toList()
    );
  }

}
