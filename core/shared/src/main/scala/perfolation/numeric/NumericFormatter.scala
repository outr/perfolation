package perfolation.numeric

case class NumericFormatter(minimumIntegerDigits: Int,
                            maximumIntegerDigits: Int,
                            minimumFractionDigits: Int,
                            maximumFractionDigits: Int,
                            grouping: Grouping,
                            roundingMode: RoundingMode) {
  def format(value: BigDecimal): String = NumericFormatter.format(
    value = value,
    minimumIntegerDigits = minimumIntegerDigits,
    maximumIntegerDigits = maximumIntegerDigits,
    minimumFractionDigits = minimumFractionDigits,
    maximumFractionDigits = maximumFractionDigits,
    grouping = grouping,
    roundingMode = roundingMode
  )
}

object NumericFormatter {
  def format(value: BigDecimal,
             minimumIntegerDigits: Int,
             maximumIntegerDigits: Int,
             minimumFractionDigits: Int,
             maximumFractionDigits: Int,
             grouping: Grouping,
             roundingMode: RoundingMode): String = {
    FastNumber(value) { fn =>
      if (maximumIntegerDigits != -1) fn.setMaximumIntegerDigits(maximumIntegerDigits)
      if (minimumIntegerDigits != -1) fn.setMinimumIntegerDigits(minimumIntegerDigits)
      if (maximumFractionDigits != -1) fn.setMaximumFractionDigits(maximumFractionDigits, roundingMode)
      if (minimumFractionDigits != -1) fn.setMinimumFractionDigits(minimumFractionDigits)
      fn.group(grouping)
      fn.toString
    }
  }
}
