package io.alienhead.kronik.core

import com.cronutils.model.Cron
import com.cronutils.model.CronType
import com.cronutils.model.definition.CronDefinition
import com.cronutils.model.definition.CronDefinitionBuilder
import com.cronutils.model.time.ExecutionTime
import com.cronutils.parser.CronParser
import java.time.Duration
import java.time.ZoneId
import java.time.ZonedDateTime

/**
 * Get the time of the next execution for a cron since a certain date.
 *
 * @param cronExpression the [Cron] expression to check against.
 * @param since the UTC [ZonedDateTime] of the date since.
 */
fun nextRun(cronExpression: Cron, since: ZonedDateTime): ZonedDateTime =
  ExecutionTime
    .forCron(cronExpression)
    .nextExecution(since)
    .get()

/**
 * Parse a cron expression.
 *
 * @param expression the string cron expression to parse.
 * @param definition the [CronDefinition] used to parse the expression.
 *
 * @throws [IllegalArgumentException] if the expression is not valid for the [CronDefinition].
 */
fun parse(
  expression: String,
  definition: CronDefinition = CronDefinitionBuilder.instanceDefinitionFor(CronType.UNIX)
): Cron {
  val parser = CronParser(definition)
  val cron = parser.parse(expression)

  return cron.validate()
}

/**
 * Calculates a delay time in milliseconds from a date to another.
 *
 * @param since [ZonedDateTime] in UTC.
 * @param until [ZonedDateTime] in UTC to delay until.
 */
fun calculateDelay(
  since: ZonedDateTime = ZonedDateTime.now(ZoneId.of("Z")),
  until: ZonedDateTime
): Long {
  return Duration.between(since, until).toMillis()
}
