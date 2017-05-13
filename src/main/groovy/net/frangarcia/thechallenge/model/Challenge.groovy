package net.frangarcia.thechallenge.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.validation.constraints.NotNull

@Entity
class Challenge {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  Long id

  @NotNull
  String name


  Date startDate

  Date endDate
}
