package io.alienhead.kronik.core

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class HelloWorldTests : DescribeSpec({

  describe("hello") {
    it("world") {
      helloWorld() shouldBe "Hello World!"
    }
  }
})
