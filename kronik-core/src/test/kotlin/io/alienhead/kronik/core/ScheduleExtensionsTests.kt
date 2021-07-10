package io.alienhead.kronik.core

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import java.time.ZoneId
import java.time.ZonedDateTime

class ScheduleExtensionsTests : DescribeSpec({

  describe("ScheduleExtensions") {

    describe("nextRun()") {
      it("should return next run of cron expression") {
        val expectedExpression = parse("0 0 1 * *")
        val expectedRun = ZonedDateTime.of(
          2021,
          8,
          1,
          0,
          0,
          0,
          0,
          ZoneId.of("Z")
        )

        val lastRun = ZonedDateTime.of(
          2021,
          7,
          1,
          0,
          0,
          0,
          0,
          ZoneId.of("Z")
        )

        val nextRun = nextRun(expectedExpression, lastRun)

        nextRun shouldBe expectedRun
      }
    }

    describe("parse()") {
      it("should return valid cron expression") {
        val expectedExpression = "* 8 1 12 3"
        val parse = parse(expectedExpression)

        parse.asString() shouldBe expectedExpression
      }

      it("throws error if expression invalid") {
        val expectedExpression = "* 9999 1 12 3"

        shouldThrow<IllegalArgumentException> {
          parse(expectedExpression)
        }
      }
    }

    describe("calculateDelay()") {
      it("returns the needed delay value between two times") {
        val now = ZonedDateTime.now(ZoneId.of("Z"))
        val nextExecution = now.plusSeconds(10)
        val expectedDelay = 10000L

        calculateDelay(now, nextExecution) shouldBe expectedDelay
      }
    }
  }
})
