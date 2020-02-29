package perfolation.unit

sealed trait ShowUnit

object ShowUnit {
  case object None extends ShowUnit
  case object Abbreviation extends ShowUnit
  case object Full extends ShowUnit
}