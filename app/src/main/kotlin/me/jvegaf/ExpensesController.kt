package me.jvegaf

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.HttpStatus

@Controller("/expenses")
class ExpensesController {
    @Get(uri="/", produces=["text/plain"])
    fun index(): String {
        return "Example Response"
    }
}
